package ro.tekin.disertatie.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.entity.Activity;
import ro.tekin.disertatie.entity.Employee;
import ro.tekin.disertatie.entity.EmployeeActivity;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.paging.TPager;

import java.util.List;

/**
 * Created by tekin on 3/6/14.
 */
@Controller
@RequestMapping("/employee-activity")
public class EmployeeActivityController {
    private Logger logger = Logger.getLogger(EmployeeActivityController.class);

    @Autowired
    private TService service;

    @RequestMapping (value = "/by-employee/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<EmployeeActivity> getEmployeeActivitiesByEmployeeId(@PathVariable("id") Integer employeeId) {
        List<EmployeeActivity> employeeActivities = service.getEmployeeActivitiesByEmployeeId(employeeId);

        return employeeActivities;
    }

    @RequestMapping (value = "/by-activity/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<EmployeeActivity> getEmployeeActivitiesByActivityId(@PathVariable("id") Integer activityId) {
        List<EmployeeActivity> employeeActivities = service.getEmployeeActivitiesByActivityId(activityId);

        return employeeActivities;
    }

    @RequestMapping (value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TPager getAllActivitiesForGrid(@RequestParam(value = "page", required =  false) Integer page,
                                         @RequestParam(value = "rows", required =  false) Integer perPage) {
        TPager pager = new TPager(page, perPage);
        List<EmployeeActivity> employeeActivitiesWithPager = service.getEmployeeActivitiesWithPager(pager);
        pager.setRows(employeeActivitiesWithPager);

        return pager;
    }

    @RequestMapping (method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void create(@RequestBody List<EmployeeActivity> employeeActivity) {
        service.saveEmployeeActivity(employeeActivity);
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Integer id) {
        service.deleteEmployeeActivity(id);
    }
}
