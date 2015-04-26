package ro.tekin.disertatie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.entity.Company;
import ro.tekin.disertatie.entity.Employee;
import ro.tekin.disertatie.entity.User;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.wrapper.TBoolean;

/**
 * Created by tekin on 3/6/14.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private TService service;

    @RequestMapping (value = "/register", method = RequestMethod.GET)
    public String getRegisterPage(Model model) {

        return "security/register";
    }

    @RequestMapping (value = "/check-email", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TBoolean getRegisterPage(@RequestParam("email") String email) {
        User user = service.findUserByEmail(email);
        TBoolean b = new TBoolean(user != null);
        return b;
    }

}
