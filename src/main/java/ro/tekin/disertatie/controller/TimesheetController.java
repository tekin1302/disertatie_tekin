package ro.tekin.disertatie.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.bean.TSStatisticsBean;
import ro.tekin.disertatie.entity.Timesheet;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.paging.TPager;

import java.util.List;

/**
 * Created by tekin on 3/6/14.
 */
@Controller
@RequestMapping("/timesheet")
public class TimesheetController {
    private Logger logger = Logger.getLogger(TimesheetController.class);

    @Autowired
    private TService service;

    @RequestMapping (value = "/list/{empActivityId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TPager getAllTimesheetsForGrid(@RequestParam(value = "page", required =  false) Integer page,
                                         @RequestParam(value = "rows", required =  false) Integer perPage,
                                         @PathVariable(value = "empActivityId") Integer empActivityId) {
        TPager pager = new TPager(page, perPage);
       if (empActivityId != null && empActivityId > 0) {
            List<Timesheet> timesheets = service.getTimesheetsWithPager(pager, empActivityId);
            pager.setRows(timesheets);
        }

        return pager;
    }

    @RequestMapping (method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void create(@RequestBody Timesheet timesheet) {
        service.saveTimesheet(timesheet);
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Integer id) {
        service.deleteTimesheet(id);
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Timesheet get(@PathVariable("id") Integer id) {
        return service.getTimesheet(id);
    }

    @RequestMapping (value = "/statistics/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<TSStatisticsBean> getstatistics(@PathVariable("id") Integer activityId) {
        return service.getTSStatistics(activityId);
    }
}
