package ir.parliran.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@Controller()
@RequestMapping("/login")
public class LoginController {

    @ModelAttribute
    private void setLookups(Model model) {
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/security/user.login", "loginItem", new Login());
    }

}