package ro.tekin.disertatie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.bean.MessBean;
import ro.tekin.disertatie.entity.Employee;
import ro.tekin.disertatie.entity.TMessage;
import ro.tekin.disertatie.service.TService;

import java.util.List;

/**
 * Created by tekin on 3/6/14.
 */
@Controller
@RequestMapping("/mess")
public class MessageController {
    @Autowired
    private TService service;

    @RequestMapping (method = RequestMethod.POST)
    @ResponseBody
    public void saveMessage(@RequestParam("id") Integer chatId, @RequestParam("m") String mess) {
        service.saveMessage(chatId, mess);
    }

    @RequestMapping (method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<MessBean> getMess(@RequestParam("id") Integer id, @RequestParam("last") Integer last) {
        return service.getMessage(id, last);
    }
}
