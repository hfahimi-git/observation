package ir.parliran.pm;

import ir.parliran.global.Labels;
import ir.parliran.global.LookupGroupKey;
import ir.parliran.lookup.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

@Controller()
@RequestMapping("/parliament-member-period")
public class ParliamentMemberPeriodController {
    private final ParliamentMemberService parliamentMemberService;
    private final ParliamentMemberPeriodService parliamentMemberPeriodService;
    private final LookupService lookupService;
    private final Labels labels;

    @Autowired
    public ParliamentMemberPeriodController(ParliamentMemberService parliamentMemberService,
                                            ParliamentMemberPeriodService parliamentMemberPeriodService,
                                            LookupService lookupService,
                                            Labels labels) {
        this.parliamentMemberService = parliamentMemberService;
        this.parliamentMemberPeriodService = parliamentMemberPeriodService;
        this.lookupService = lookupService;
        this.labels = labels;
    }

    @ModelAttribute()
    private void setLookups(@RequestParam("pm_oid") long pmOid, Model model) throws Exception {
        model.addAttribute("provinceCityStructure", lookupService.findByGroupKey2Level(LookupGroupKey.province.name()));
        model.addAttribute("yesNoItems", lookupService.findByGroupKeyOrderBy(LookupGroupKey.yes_no.name(), "orderby desc"));
        model.addAttribute("periodItems", lookupService.findByGroupKeyOrderBy(LookupGroupKey.parliament_period.name(), "orderby desc"));
        model.addAttribute("languageItems", lookupService.findByGroupKeyOrderBy(LookupGroupKey.site_langs.name(), "orderby"));
        ParliamentMember pmItem = parliamentMemberService.findById(pmOid).orElse(null);
        if (pmItem == null)
            throw new Exception("invalid parliament member");

        model.addAttribute("pmItem", pmItem);

    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView mav = new ModelAndView("parliament-member/period.index");
        mav.addObject("pmPeriodItems", parliamentMemberPeriodService.findAll
                (
                    ((ParliamentMember) Objects.requireNonNull(model.getAttribute("pmItem"))).getOid()
                )
        );
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/parliament-member/period.add-edit", "pmPeriodItem", new ParliamentMemberPeriod());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("pmPeriodItem") ParliamentMemberPeriod pmPeriodItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/parliament-member/period.add-edit", bindingResult.getModel());
        }

        try {
            parliamentMemberPeriodService.add(pmPeriodItem);
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("pmPeriodItem", ex.getMessage(), labels.get("lu_period_no_duplicated")));
            return new ModelAndView("/parliament-member/period.add-edit", bindingResult.getModel());
        }

        return new ModelAndView("redirect:/parliament-member-period?pm_oid=" + pmPeriodItem.getFkParliamentMemberOid());
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {
        ParliamentMemberPeriod pm = parliamentMemberPeriodService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        return new ModelAndView("/parliament-member/period.add-edit", "pmPeriodItem", pm);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("oid") long oid,
                             @Valid @ModelAttribute("pmPeriodItem") ParliamentMemberPeriod pmPeriodItem,
                             BindingResult bindingResult) {

        ParliamentMemberPeriod pmp = parliamentMemberPeriodService.findById(oid).orElse(null);
        if (pmp == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/parliament-member/period.add-edit", bindingResult.getModel());
        }

        try {
            parliamentMemberPeriodService.edit(pmPeriodItem);
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("pmPeriodItem", ex.getMessage(), labels.get("lu_period_no_duplicated")));
            return new ModelAndView("/parliament-member/period.add-edit", bindingResult.getModel());
        }

        return new ModelAndView("redirect:/parliament-member-period?pm_oid=" + pmp.getFkParliamentMemberOid());
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        ParliamentMemberPeriod pmp = parliamentMemberPeriodService.findById(oid).orElse(null);
        if (pmp == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/parliament-member/period.remove", "pmPeriodItem", pmp);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid, Model model) {
        parliamentMemberPeriodService.remove(oid);
        return new ModelAndView("redirect:/parliament-member-period?pm_oid=" +
                ((ParliamentMember) Objects.requireNonNull(model.getAttribute("pmItem"))).getOid()
        );
    }


}
