package ir.parliran.session;

import ir.parliran.board.BoardCommissionService;
import ir.parliran.board.BoardObserverService;
import ir.parliran.global.DatePair;
import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.Utils;
import ir.parliran.lookup.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@Controller()
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;
    private final LookupService lookupService;
    private final BoardCommissionService boardCommissionService;
    private final BoardObserverService boardObserverService;
    private final SessionObserverReportService observerReportService;
    private final SessionCommissionReportService commissionReportService;
    private final SessionPresentService presentService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public SessionController(SessionService sessionService, LookupService lookupService,
                             BoardCommissionService boardCommissionService, BoardObserverService boardObserverService,
                             SessionObserverReportService observerReportService,
                             SessionCommissionReportService commissionReportService,
                             SessionPresentService presentService) {
        this.sessionService = sessionService;
        this.lookupService = lookupService;
        this.boardCommissionService = boardCommissionService;
        this.boardObserverService = boardObserverService;
        this.observerReportService = observerReportService;
        this.commissionReportService = commissionReportService;
        this.presentService = presentService;
    }

    @ModelAttribute
    private void setLookups(Model model) {
        model.addAttribute("periodItems", lookupService.findByGroupKeyOrderBy(LookupGroupKey.parliament_period.name(), "orderby desc"));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "lu_period_no", required = false) String luPeriodNo,
            @RequestParam(value = "no", required = false) String no,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "page", required = false, defaultValue = "1") String pageNo
    ) {

        ModelAndView mav = new ModelAndView("/session/index");
        mav.addObject("sessionItems",
                sessionService.findAll(
                        SearchCredential.builder()
                                .keyword(keyword)
                                .luPeriodNo(luPeriodNo)
                                .no(Utils.getInt(no, 0))
                                .fromDate(new DatePair(date))
                                .pageNo(Utils.getInt(pageNo, 1))
                                .pageSize(pageSize)
                                .build()
                ));
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/session/add-edit", "sessionItem", new Session());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("sessionItem") Session sessionItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/session/add-edit", bindingResult.getModel());
        }

        try {
            sessionService.add(sessionItem);
            return new ModelAndView("redirect:/session");
        }
        catch (Exception e) {
            bindingResult.reject(e.getMessage(), e.getMessage());
            return new ModelAndView("/session/add-edit", bindingResult.getModel());
        }
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {
        Session item = sessionService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/session/add-edit", "sessionItem", item);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "oid") long oid,
                             @Valid @ModelAttribute("sessionItem") Session sessionItem,
                             BindingResult bindingResult) {

        Session pm = sessionService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/session/add-edit", bindingResult.getModel());
        }

        try {
            sessionService.edit(sessionItem);
            return new ModelAndView("redirect:/session");
        }
        catch (Exception e) {
            bindingResult.reject(e.getMessage(), e.getMessage());
            return new ModelAndView("/session/add-edit", bindingResult.getModel());
        }
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        Session session = sessionService.findById(oid).orElse(null);
        if (session == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/session/remove", "sessionItem", session);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid) {
        sessionService.remove(oid);
        return new ModelAndView("redirect:/session");
    }

    @RequestMapping(value = "/detail/{oid}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable("oid") long oid) {
        Session item = sessionService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        ModelAndView mav = new ModelAndView("/session/detail");

        mav.addObject("yesNoItems", lookupService.findByGroupKey(LookupGroupKey.yes_no.name()));
        mav.addObject("absPresItems", lookupService.findByGroupKey(LookupGroupKey.abs_present.name()));
        mav.addObject("memberAbsPresItems", lookupService.findByGroupKey(LookupGroupKey.member_abs_pres.name()));
        mav.addObject("gradeItems", lookupService.findByGroupKey(LookupGroupKey.grade_status.name()));
        mav.addObject("commissionItems", boardCommissionService.findAll(item.getFkBoardPeriodOid()));
        mav.addObject("observerItems", boardObserverService.findAll(item.getDate(), item.getFkBoardPeriodOid()));
        mav.addObject("presentItems", presentService.findAll(item.getOid()));

        mav.addObject("sessionItem", item);
        mav.addObject("sessionInvitationItem", new SessionInvitation());
        mav.addObject("sessionAgendaItem", new SessionAgenda());
        mav.addObject("sessionBoardJustificationReportItem", new SessionBoardJustificationReport());
        mav.addObject("sessionDeputyJustificationReportItem", new SessionDeputyJustificationReport());
        mav.addObject("sessionObserverReportItem", new SessionObserverReport());
        mav.addObject("sessionBoardReportItem", new SessionBoardReport());
        mav.addObject("sessionCommissionReportItem", new SessionCommissionReport());
        mav.addObject("observerReportItems", observerReportService.findAll(item.getOid()));

        mav.addObject("sessionDeputyAnalyticalReportItem", new SessionDeputyAnalyticalReport());
        mav.addObject("commissionReportItems", commissionReportService.findAll(item.getOid()));

        return mav;

    }

}