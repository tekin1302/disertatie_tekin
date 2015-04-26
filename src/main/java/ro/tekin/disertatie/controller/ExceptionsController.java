package ro.tekin.disertatie.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.entity.TException;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.wrapper.TString;
import ro.tekin.disertatie.util.paging.TPager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tekin.omer on 2/5/14.
 */
@Controller
@RequestMapping (value = "/exception")
public class ExceptionsController {

    private Logger logger = Logger.getLogger(ExceptionsController.class);

    @Autowired
    private TService service;

    @RequestMapping (value = "/{id}", method = RequestMethod.GET)
        public String getException(@PathVariable("id") Integer id, Model model) {
        TException e = service.getExceptionById(id);
        e.initImageString();
        model.addAttribute("ex", e);

        return "errors/exception";
    }

    @RequestMapping (method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TPager getAllExceptions(@RequestParam(value = "page", required =  false) Integer page,
                                   @RequestParam(value = "rows", required =  false) Integer perPage,
                                   @RequestParam(value = "sidx", required =  false) String sidx,
                                   @RequestParam(value = "sord", required =  false) String sord
                                    ) {
        TPager pager = new TPager(page, perPage);
        pager.setSidx(sidx);
        pager.setSord(sord);
        List<TException> exceptions = service.getExceptions(pager);

        pager.setRows(exceptions);

        return pager;
    }

    @RequestMapping (method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void createException(@RequestParam("id") Integer id,
                                  @RequestParam("img") String img) {

        try {
            TException tException = service.getExceptionById(id);
            tException.setImage(Base64.decode(img.replace("data:image/png;base64,", "").getBytes()));

            service.saveException(tException);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @RequestMapping (value="/img", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TString getImageForException(@RequestParam("id") Integer id, HttpServletRequest request) {
        TException ex = service.getExceptionById(id);
        ex.initImageString();
        TString x = new TString(ex.getImageString());

        return x;
    }
}
