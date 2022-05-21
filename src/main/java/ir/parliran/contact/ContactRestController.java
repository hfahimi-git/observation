package ir.parliran.contact;

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

@RestController ()
@RequestMapping("/contact-rest")
public class ContactRestController {

    private final ContactService contactService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public ContactRestController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Page<Contact> index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "lu_contact_type", required = false) String luContactType,
            @RequestParam(value = "page", required = false, defaultValue = "1") String pageNo
            ) {
        return contactService.findAll(keyword, luContactType, Utils.getInt(pageNo, 1) , pageSize);
    }

}