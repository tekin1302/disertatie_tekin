package ro.tekin.disertatie.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.entity.ChatRoom;
import ro.tekin.disertatie.entity.Activity;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.TUtils;
import ro.tekin.disertatie.util.paging.TPager;

import java.util.List;

/**
 * Created by tekin on 3/6/14.
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {
    private Logger logger = Logger.getLogger(ActivityController.class);

    @Autowired
    private TService service;

    @RequestMapping (value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Activity getActivity(@PathVariable("id") Integer id, Model model) {
        Activity activity = service.getActivity(id);

        return activity;
    }

    @RequestMapping (value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TPager getAllActivitiesForGrid(@RequestParam(value = "page", required =  false) Integer page,
                                         @RequestParam(value = "rows", required =  false) Integer perPage) {
        TPager pager = new TPager(page, perPage);
        List<Activity> activities = service.getActivitiesWithPager(pager);
        pager.setRows(activities);

        return pager;
    }

    @RequestMapping (method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Integer createActivity(@RequestBody Activity activity) {
        service.saveActivity(activity);

        return activity.getId();
    }

    @RequestMapping (method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public void updateActivity(@RequestBody Activity activity) {
        service.updateActivity(activity);
    }
    @RequestMapping (value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteActivity(@PathVariable("id") Integer id) {
        service.deleteActivity(id);
    }
}
