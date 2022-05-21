package ir.parliran.security;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ModelAttribute
    private void setLookups(Model model) {
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(
            @RequestParam(value = "keyword", required = false) String keyword
    ) {

        ModelAndView mav = new ModelAndView("/security/role.index");
        mav.addObject("roleItems",
                roleService.findAll(keyword)
        );
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/security/role.add-edit", "roleItem", new Role());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("roleItem") Role roleItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/security/role.add-edit", bindingResult.getModel());
        }

        try {
            roleService.add(roleItem);
            return new ModelAndView("redirect:/role");
        } catch (Exception e) {
            bindingResult.reject(e.getMessage(), e.getMessage());
            return new ModelAndView("/security/role.add-edit", bindingResult.getModel());
        }
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {

        Role item = roleService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/security/role.add-edit", "roleItem", item);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "oid") long oid,
                             @Valid @ModelAttribute("roleItem") Role roleItem,
                             BindingResult bindingResult) {

        Role item = roleService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/security/role.add-edit", bindingResult.getModel());
        }

        try {
            roleService.edit(roleItem);
            return new ModelAndView("redirect:/role");
        }
        catch (Exception e) {
            bindingResult.reject(e.getMessage(), e.getMessage());
            return new ModelAndView("/security/role.add-edit", bindingResult.getModel());
        }

    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        Role role = roleService.findById(oid).orElse(null);
        if (role == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/security/role.remove", "roleItem", role);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid) {
        roleService.remove(oid);
        return new ModelAndView("redirect:/role");
    }

}