package ir.parliran.contact;

import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.upload.StorageService;
import ir.parliran.lookup.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;
    private final StorageService storageService;
    private final LookupService lookupService;
    private final Environment env;

    @Autowired
    public ContactController(ContactService contactService, StorageService storageService,
                             LookupService lookupService, Environment env) {
        this.contactService = contactService;
        this.storageService = storageService;
        this.lookupService = lookupService;
        this.env = env;
    }

    @ModelAttribute
    private void setLookups(Model model) {
        model.addAttribute("genderItems", lookupService.findByGroupKey(LookupGroupKey.gender.name()));
        model.addAttribute("contactTypeItems", lookupService.findByGroupKey(LookupGroupKey.contact_type.name()));
        model.addAttribute("personContactTypeItems", lookupService.findByGroupKey(LookupGroupKey.person_contact_title.name()));
        model.addAttribute("companyContactTypeItems", lookupService.findByGroupKey(LookupGroupKey.company_contact_title.name()));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "lu_contact_type", required = false) String luContactType,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNo
            ) {

        ModelAndView mav = new ModelAndView("/contact/index");
        mav.addObject("contactItems", contactService.findAll(keyword, luContactType, pageNo,
                env.getProperty("paging.page-size", Integer.class)));
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/contact/add-edit", "contactItem", new Contact());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("contactItem") Contact contactItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/contact/add-edit", bindingResult.getModel());
        }

        if (!contactItem.getFile().isEmpty()) {
            String filename = storageService.store(contactItem.getFile());
            contactItem.setFilename(filename);
        }
        contactService.add(contactItem);

        return new ModelAndView("redirect:/contact");
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {
        Contact pm = contactService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        return new ModelAndView("/contact/add-edit", "contactItem", pm);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "oid") long oid,
                             @Valid @ModelAttribute("contactItem") Contact contactItem, BindingResult bindingResult) {

        Contact pm = contactService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/contact/add-edit", bindingResult.getModel());
        }

        if (!contactItem.getFile().isEmpty()) {
            String filename = storageService.store(contactItem.getFile());
            contactItem.setFilename(filename);
        }
        contactService.edit(contactItem);

        return new ModelAndView("redirect:/contact");
    }

    @RequestMapping(value = "/remove-image/{oid}", method = RequestMethod.GET)
    public ModelAndView removeImage(@PathVariable(value = "oid") long oid) {
        if (contactService.editImage(oid, null) < 1) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("redirect:/contact/edit/" + oid);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        Contact contact = contactService.findById(oid).orElse(null);
        if (contact == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/contact/remove", "contactItem", contact);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid) {
        contactService.remove(oid);
        return new ModelAndView("redirect:/contact");
    }


}