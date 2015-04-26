package ro.tekin.disertatie.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.bean.OrganigramBean;
import ro.tekin.disertatie.entity.Company;
import ro.tekin.disertatie.entity.Organigram;
import ro.tekin.disertatie.exception.OrganigramNotValidException;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.TUtils;
import ro.tekin.disertatie.util.wrapper.TString;

/**
 * Created by tekin.omer on 2/5/14.
 */
@Controller
@RequestMapping (value = "/organigram")
public class OrganigramController {

    private Logger logger = Logger.getLogger(OrganigramController.class);

    @Autowired
    private TService service;

    @RequestMapping (method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Integer saveOrganigram(@RequestBody OrganigramBean[] organigramBeans) {
        try {
            service.saveOrganigramElements(organigramBeans);
        } catch (OrganigramNotValidException e) {
            return 0;
        }
        return 1;
    }

    @RequestMapping (value= "/data", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Integer saveMoreData(@RequestBody String data) {
        Organigram organigram = service.getOrganigramForCurrentCompany();

        if (organigram == null) {
            organigram = new Organigram();
            organigram.setCompany(new Company(TUtils.getAuthenticatedUser().getCompany()));
        }
        organigram.setData(data);
        service.saveOrganigram(organigram);

        return organigram.getId();
    }

    @RequestMapping (value= "/data", method = RequestMethod.GET)
    @ResponseBody
    public String getData(@RequestBody String data) {
        Organigram organigram = service.getOrganigramForCurrentCompany();

        if (organigram != null) {
            return organigram.getData();
        }
        return null;
    }
}
