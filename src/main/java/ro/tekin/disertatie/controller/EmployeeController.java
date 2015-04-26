package ro.tekin.disertatie.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.entity.Employee;
import ro.tekin.disertatie.entity.User;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.TUtils;
import ro.tekin.disertatie.util.paging.TPager;
import ro.tekin.disertatie.util.wrapper.TBoolean;

import java.util.List;

/**
 * Created by tekin.omer on 2/5/14.
 */
@Controller
@RequestMapping (value = "/employee")
public class EmployeeController {

    private Logger logger = Logger.getLogger(EmployeeController.class);

    @Autowired
    private TService service;

    @RequestMapping (value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Employee getEmployee(@PathVariable("id") Integer id, Model model) {
        if (id == -1) {
            id = TUtils.getAuthenticatedUser().getEmployee();
        }
        Employee employee = service.getEmployee(id);

        return employee;
    }

    @RequestMapping (value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TPager getAllEmployeesForGrid(@RequestParam(value = "page", required =  false) Integer page,
                                             @RequestParam(value = "rows", required =  false) Integer perPage) {
        TPager pager = new TPager(page, perPage);
        List<Employee> employees = service.getEmployeesWithPager(pager);
        pager.setRows(employees);

        return pager;
    }

    @RequestMapping (method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Employee> getAllEmployees() {
        List<Employee> employees = service.getEmployeesForCompany();
        return employees;
    }

    @RequestMapping (method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Integer createEmployee(@RequestBody Employee employee) {
        service.saveEmployee(employee);

        return employee.getId();
    }

    @RequestMapping (method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public void updateEmployee(@RequestBody Employee employee) {
        service.updateEmployee(employee);
    }
    @RequestMapping (value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteEmployee(@PathVariable("id") Integer id) {
        service.deleteEmployee(id);
    }

    @RequestMapping (value = "/getCurrent", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Employee getCurrentEmployee() {
        return service.getCurrentEmployee();
    }
}
