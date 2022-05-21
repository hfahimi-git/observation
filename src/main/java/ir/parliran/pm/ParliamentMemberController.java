package ir.parliran.pm;

import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.Page;
import ir.parliran.global.Utils;
import ir.parliran.global.upload.StorageService;
import ir.parliran.lookup.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
@RequestMapping("/parliament-member")
public class ParliamentMemberController {

    private final ParliamentMemberService parliamentMemberService;
    private final StorageService storageService;
    private final LookupService lookupService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public ParliamentMemberController(ParliamentMemberService parliamentMemberService, StorageService storageService,
                                      LookupService lookupService) {
        this.parliamentMemberService = parliamentMemberService;
        this.storageService = storageService;
        this.lookupService = lookupService;
    }

    @ModelAttribute
    private void setLookups(Model model) {
        model.addAttribute("titleItems", lookupService.findByGroupKey(LookupGroupKey.person_contact_title.name()));
        model.addAttribute("genderItems", lookupService.findByGroupKey(LookupGroupKey.gender.name()));
        model.addAttribute("provinceCityStructure", lookupService.findByGroupKey2Level(LookupGroupKey.province.name()));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", required = false, defaultValue = "1") String pageNo) {

        ModelAndView mav = new ModelAndView("/parliament-member/index");
        mav.addObject("pmItems", parliamentMemberService.findAll(keyword,
                Utils.getInt(pageNo, 1) , pageSize)
        );
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/parliament-member/add-edit", "pmItem", new ParliamentMember());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("pmItem") ParliamentMember pmItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/parliament-member/add-edit", bindingResult.getModel());
        }

        if (!pmItem.getFile().isEmpty()) {
            String filename = storageService.store(pmItem.getFile());
            pmItem.setFilename(filename);
        }
        parliamentMemberService.add(pmItem);

        return new ModelAndView("redirect:/parliament-member");
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {
        ParliamentMember pm = parliamentMemberService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        return new ModelAndView("/parliament-member/add-edit", "pmItem", pm);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "oid") long oid,
                             @Valid @ModelAttribute("pmItem") ParliamentMember pmItem, BindingResult bindingResult) {

        ParliamentMember pm = parliamentMemberService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/parliament-member/add-edit", bindingResult.getModel());
        }

        if (!pmItem.getFile().isEmpty()) {
            String filename = storageService.store(pmItem.getFile());
            pmItem.setFilename(filename);
        }
        parliamentMemberService.edit(pmItem);

        return new ModelAndView("redirect:/parliament-member");
    }

    @RequestMapping(value = "/remove-image/{oid}", method = RequestMethod.GET)
    public ModelAndView removeImage(@PathVariable(value = "oid") long oid) {
        if (parliamentMemberService.editImage(oid, null) < 1) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("redirect:/parliament-member/edit/" + oid);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        ParliamentMember pm = parliamentMemberService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/parliament-member/remove", "pmItem", pm);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid) {
        parliamentMemberService.remove(oid);
        return new ModelAndView("redirect:/parliament-member");
    }


}