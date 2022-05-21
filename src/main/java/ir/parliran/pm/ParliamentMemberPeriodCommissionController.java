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
@RequestMapping("/parliament-member-commission")
public class ParliamentMemberPeriodCommissionController {
    private final ParliamentMemberService pmService;
    private final ParliamentMemberPeriodService pmpService;
    private final ParliamentMemberPeriodCommissionService pmpcService;
    private final LookupService lookupService;
    private final Labels labels;

    @Autowired
    public ParliamentMemberPeriodCommissionController(ParliamentMemberService pmService,
                                                      ParliamentMemberPeriodService pmpService,
                                                      ParliamentMemberPeriodCommissionService pmpcService,
                                                      LookupService lookupService,
                                                      Labels labels) {
        this.pmService = pmService;
        this.pmpService = pmpService;
        this.pmpcService = pmpcService;
        this.lookupService = lookupService;
        this.labels = labels;
    }

    @ModelAttribute()
    private void setLookups(@RequestParam("pm_oid") long pmOid,
                            @RequestParam("pmp_oid") long pmpOid, Model model) throws Exception {
        model.addAttribute("commissionItems", lookupService.findByGroupKeyOrderBy(LookupGroupKey.commission.name(), "value"));
        model.addAttribute("commissionRoleItems", lookupService.findByGroupKeyOrderBy(LookupGroupKey.commission_role.name(), "orderby"));
        model.addAttribute("yearNoItems", lookupService.findByGroupKey(LookupGroupKey.parliament_year.name()));
        ParliamentMember pmItem = pmService.findById(pmOid).orElse(null);
        ParliamentMemberPeriod pmpItem = pmpService.findById(pmpOid).orElse(null);
        if (pmItem == null)
            throw new Exception("invalid parliament member");

        if (pmpItem == null)
            throw new Exception("invalid parliament member period");

        model.addAttribute("pmItem", pmItem);
        model.addAttribute("pmpItem", pmpItem);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView mav = new ModelAndView("parliament-member/commission.index");
        mav.addObject("pmCommissionItems", pmpcService.findAll
                (
                    ((ParliamentMember) Objects.requireNonNull(model.getAttribute("pmItem"))).getOid(),
                    ((ParliamentMemberPeriod) Objects.requireNonNull(model.getAttribute("pmpItem"))).getLuPeriodNo()
                )
        );
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/parliament-member/commission.add-edit", "pmCommissionItem",
                new ParliamentMemberPeriodCommission());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("pmCommissionItem") ParliamentMemberPeriodCommission pmCommissionItem,
                            @RequestParam("pmp_oid") long pmpOid,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/parliament-member/commission.add-edit", bindingResult.getModel());
        }

        try {
            pmpcService.add(pmCommissionItem);
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("pmCommissionItem", ex.getMessage(),
                    labels.get("lu_commission_no_duplicated")));
            return new ModelAndView("/parliament-member/commission.add-edit", bindingResult.getModel());
        }

        return new ModelAndView("redirect:/parliament-member-commission?pm_oid=" +
                pmCommissionItem.getFkParliamentMemberOid() + "&pmp_oid=" +pmpOid );
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {
        ParliamentMemberPeriodCommission pm = pmpcService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        return new ModelAndView("/parliament-member/commission.add-edit", "pmCommissionItem", pm);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("oid") long oid,
                             @Valid @ModelAttribute("pmCommissionItem") ParliamentMemberPeriodCommission pmCommissionItem,
                             @RequestParam("pmp_oid") long pmpOid,
                             BindingResult bindingResult) {

        ParliamentMemberPeriodCommission pmpc = pmpcService.findById(oid).orElse(null);
        if (pmpc == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/parliament-member/commission.add-edit", bindingResult.getModel());
        }

        try {
            pmpcService.edit(pmCommissionItem);
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("pmCommissionItem", ex.getMessage(),
                    labels.get("lu_commission_no_duplicated")));
            return new ModelAndView("/parliament-member/period.add-edit", bindingResult.getModel());
        }

        return new ModelAndView("redirect:/parliament-member-commission?pm_oid=" +
                pmCommissionItem.getFkParliamentMemberOid() + "&pmp_oid=" +pmpOid );
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        ParliamentMemberPeriodCommission pmp = pmpcService.findById(oid).orElse(null);
        if (pmp == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/parliament-member/commission.remove", "pmCommissionItem", pmp);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid, Model model) {
        pmpcService.remove(oid);
        return new ModelAndView("redirect:/parliament-member-commission?pm_oid=" +
                ((ParliamentMember) Objects.requireNonNull(model.getAttribute("pmItem"))).getOid() +
                "&pmp_oid=" + ((ParliamentMemberPeriod) Objects.requireNonNull(model.getAttribute("pmpItem"))).getOid()
        );
    }


}
