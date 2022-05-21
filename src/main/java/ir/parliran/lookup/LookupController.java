package ir.parliran.lookup;

import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.Utils;
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
@RequestMapping("/lookup")
public class LookupController {

    private final LookupService lookupService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @ModelAttribute
    private void setLookups(Model model) {
        model.addAttribute("groupKeyItems", lookupService.findAllGroupKeys());
        model.addAttribute("sessionPeriodItems", lookupService.findByGroupKey(LookupGroupKey.session_period.name()));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "group_key", required = false) String groupKey,
            @RequestParam(value = "page", required = false, defaultValue = "1") String pageNo
    ) {

        ModelAndView mav = new ModelAndView("/lookup/index");
        mav.addObject("lookupItems",
                lookupService.findAll(keyword, groupKey, Utils.getInt(pageNo, 1), pageSize)
        );
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/lookup/add-edit", "lookupItem", new Lookup());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("lookupItem") Lookup lookupItem, BindingResult bindingResult) {
        if(Utils.isBlank(lookupItem.getGroupKey())) {
            lookupItem.setGroupKey(lookupItem.getGroupKeyTitle());
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/lookup/add-edit", bindingResult.getModel());
        }

        lookupService.add(lookupItem);

        return new ModelAndView("redirect:/lookup");
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {
        Lookup pm = lookupService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/lookup/add-edit", "lookupItem", pm);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "oid") long oid,
                             @ModelAttribute("lookupItem") Lookup lookupItem, BindingResult bindingResult) {

        Lookup pm = lookupService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if(Utils.isBlank(lookupItem.getGroupKey())) {
            lookupItem.setGroupKey(lookupItem.getGroupKeyTitle());
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/lookup/add-edit", bindingResult.getModel());
        }

        lookupService.edit(lookupItem);

        return new ModelAndView("redirect:/lookup");
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        Lookup lookup = lookupService.findById(oid).orElse(null);
        if (lookup == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/lookup/remove", "lookupItem", lookup);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid) {
        lookupService.remove(oid);
        return new ModelAndView("redirect:/lookup");
    }


}