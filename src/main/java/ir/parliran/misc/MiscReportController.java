package ir.parliran.misc;

import ir.parliran.board.BoardService;
import ir.parliran.global.DatePair;
import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.Utils;
import ir.parliran.global.upload.StorageService;
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
@RequestMapping("/misc-report")
public class MiscReportController {

    private final MiscReportService miscReportService;
    private final LookupService lookupService;
    private final BoardService boardService;
    private final StorageService storageService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public MiscReportController(MiscReportService miscReportService, LookupService lookupService,
                                BoardService boardService, StorageService storageService) {
        this.miscReportService = miscReportService;
        this.lookupService = lookupService;
        this.boardService = boardService;
        this.storageService = storageService;
    }

    @ModelAttribute
    private void setLookups(Model model) {
        model.addAttribute("periodItems", lookupService.findByGroupKeyOrderBy(LookupGroupKey.parliament_period.name(), "orderby desc"));
        model.addAttribute("boardItems", boardService.findAll());
        model.addAttribute("typeItems", lookupService.findByGroupKeyOrderBy(LookupGroupKey.misc_report_type.name(), "orderby desc"));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "fk_board_oid", required = false) Long fkPeriodOid,
            @RequestParam(value = "lu_period_no", required = false) Integer luPeriodNo,
            @RequestParam(value = "from_date", required = false) String fromDate,
            @RequestParam(value = "to_date", required = false) String toDate,
            @RequestParam(value = "lu_type", required = false) String luType,
            @RequestParam(value = "page", required = false, defaultValue = "1") String pageNo
    ) {

        ModelAndView mav = new ModelAndView("/misc/index");
        mav.addObject("miscReportItems",
                miscReportService.findAll(
                        SearchCredential.builder()
                                .keyword(keyword)
                                .fkBoardOid(fkPeriodOid)
                                .luPeriodNo(luPeriodNo)
                                .fromDate(new DatePair(fromDate))
                                .toDate(new DatePair(toDate))
                                .luType(luType)
                                .pageNo(Utils.getInt(pageNo, 1))
                                .pageSize(pageSize)
                                .build()
                ));
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/misc/add-edit", "miscReportItem", new MiscReport());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("miscReportItem") MiscReport miscReportItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/misc/add-edit", bindingResult.getModel());
        }

        try {
            if (!miscReportItem.getFile().isEmpty()) {
                String filename = storageService.store(miscReportItem.getFile());
                miscReportItem.setFilename(filename);
            }
            miscReportService.add(miscReportItem);
            return new ModelAndView("redirect:/misc-report");
        }
        catch (Exception e) {
            bindingResult.reject(e.getMessage(), e.getMessage());
            return new ModelAndView("/misc/add-edit", bindingResult.getModel());
        }
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {
        MiscReport item = miscReportService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/misc/add-edit", "miscReportItem", item);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "oid") long oid,
                             @Valid @ModelAttribute("miscReportItem") MiscReport miscReportItem,
                             BindingResult bindingResult) {

        MiscReport mr = miscReportService.findById(oid).orElse(null);
        if (mr == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/misc/add-edit", bindingResult.getModel());
        }

        try {
            if (!miscReportItem.getFile().isEmpty()) {
                String filename = storageService.store(miscReportItem.getFile());
                miscReportItem.setFilename(filename);
            }
            miscReportService.edit(miscReportItem);
            return new ModelAndView("redirect:/misc-report");
        }
        catch (Exception e) {
            bindingResult.reject(e.getMessage(), e.getMessage());
            return new ModelAndView("/misc/add-edit", bindingResult.getModel());
        }
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        MiscReport miscReport = miscReportService.findById(oid).orElse(null);
        if (miscReport == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/misc/remove", "miscReportItem", miscReport);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid) {
        miscReportService.remove(oid);
        return new ModelAndView("redirect:/misc-report");
    }

    @RequestMapping(value = "/detail/{oid}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable("oid") long oid) {
        MiscReport item = miscReportService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        ModelAndView mav = new ModelAndView("/misc/detail");

        mav.addObject("miscTypeItems", lookupService.findByGroupKey(LookupGroupKey.misc_report_type.name()));
        mav.addObject("miscReportItem", item);
        return mav;

    }

}