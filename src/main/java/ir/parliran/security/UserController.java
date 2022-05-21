package ir.parliran.security;

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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final LookupService lookupService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public UserController(UserService userService, RoleService roleService, LookupService lookupService) {
        this.userService = userService;
        this.roleService = roleService;
        this.lookupService = lookupService;
    }

    @ModelAttribute
    private void setLookups(Model model) {
        model.addAttribute("userStatusItems", lookupService.findByGroupKey(LookupGroupKey.user_status.name()));
        model.addAttribute("roleItems", roleService.findAll());
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "lu_status", required = false) String luStatus,
            @RequestParam(value = "page", required = false, defaultValue = "1") String pageNo
    ) {

        ModelAndView mav = new ModelAndView("/security/user.index");
        mav.addObject("userItems",
                userService.findAll(
                        SearchCredential.builder()
                                .keyword(keyword)
                                .luStatus(luStatus)
                                .pageNo(Utils.getInt(pageNo, 1))
                                .pageSize(pageSize)
                                .build()
                )
        );
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/security/user.add-edit", "userItem", new User());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("userItem") User userItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/security/user.add-edit", bindingResult.getModel());
        }

        try {
            userService.add(userItem);
            return new ModelAndView("redirect:/user");
        } catch (Exception e) {
            bindingResult.reject(e.getMessage(), e.getMessage());
            return new ModelAndView("/security/user.add-edit", bindingResult.getModel());
        }
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {

        User item = userService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/security/user.add-edit", "userItem", item);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "oid") long oid,
                             @Valid @ModelAttribute("userItem") User userItem,
                             BindingResult bindingResult) {

        User pm = userService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/security/user.add-edit", bindingResult.getModel());
        }

        try {
            userService.edit(userItem);
            return new ModelAndView("redirect:/user");
        }
        catch (Exception e) {
            bindingResult.reject(e.getMessage(), e.getMessage());
            return new ModelAndView("/security/user.add-edit", bindingResult.getModel());
        }

    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        User user = userService.findById(oid).orElse(null);
        if (user == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/security/user.remove", "userItem", user);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid) {
        userService.remove(oid);
        return new ModelAndView("redirect:/user");
    }

}