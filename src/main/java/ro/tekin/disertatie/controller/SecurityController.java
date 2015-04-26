package ro.tekin.disertatie.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tekin.omer on 2/5/14.
 */
@Controller
public class SecurityController {
    private Logger logger = Logger.getLogger(SecurityController.class);

    @RequestMapping (value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied() {
        return "security/accessDenied";
    }

    @RequestMapping (value = "/login", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(value = "login_error", required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "security/login";
    }

}
