package ro.tekin.disertatie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ro.tekin.disertatie.entity.Phone;
import ro.tekin.disertatie.service.PhonesService;
import ro.tekin.disertatie.service.TService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by tekin.omer on 2/5/14.
 */
@Controller
public class HomeController {

    @Autowired
    private TService service;

    @Autowired
    private PhonesService phonesService;

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String showHome(Model model) {

        return "home";
    }

    @RequestMapping (value = "/reports/download", method = RequestMethod.GET)
    public void downloadReports(HttpServletResponse resp, Model model) throws IOException {
        byte[] bytes = service.generateReport();
        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            out.write(bytes);
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename=raport_venituri.xls");
            resp.setContentLength(bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
             if (out != null) {
                 try {
                     out.flush();
                     out.close();
                 } catch (IOException e) {}
             }
        }
    }

    @RequestMapping (value = "/sync-phones", method = RequestMethod.GET)
    public String syncPhones(HttpServletResponse resp, Model model) throws Exception {
        phonesService.getPhones();
        return "phones";
    }

    @RequestMapping (value = "/phones", method = RequestMethod.GET)
    public String phones(@RequestParam(value = "page", required = false) Integer page, Model model) throws Exception {
        if (page == null) {
            page = 0;
        }
        List<Phone> phoneList =  service.getPhones(page);
        model.addAttribute("phones", phoneList);
        model.addAttribute("page", page);
        model.addAttribute("max", service.countPhones());

        return "phones";
    }
}

