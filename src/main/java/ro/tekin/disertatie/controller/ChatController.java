package ro.tekin.disertatie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.tekin.disertatie.entity.*;
import ro.tekin.disertatie.service.TService;

import java.util.*;

/**
 * Created by tekin on 3/6/14.
 */
@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private TService service;

    @RequestMapping(value = "/default", method = RequestMethod.POST)
    @ResponseBody
    public void createDefaultChatRooms() {
        service.createDefaultChatRooms();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ChatRoom> getChatRooms() {
        List<ChatRoom> result = service.getAllChatRooms();
        return result;
    }
}
