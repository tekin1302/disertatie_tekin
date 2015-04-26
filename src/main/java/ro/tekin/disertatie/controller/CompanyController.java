package ro.tekin.disertatie.controller;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.bean.RegisterBean;
import ro.tekin.disertatie.entity.Company;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.TUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tekin on 3/6/14.
 */
@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private TService service;

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody Company company) {
        service.updateCompany(company);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Integer create(@RequestBody RegisterBean registerBean, HttpServletRequest req, HttpServletResponse response) {
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LfUkvQSAAAAAPelLqlNhGc8_-fNejJ3QfcgeB4o");

        ReCaptchaResponse captchaResponse = reCaptcha.checkAnswer(req.getRemoteAddr(), registerBean.getCaptchaChallenge(), registerBean.getCaptchaResp());

        if (!captchaResponse.isValid()) {
            return 0;
        }
        service.register(registerBean.getCompany());

        return 1;
    }


    /*
        @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "model", required = false) String companyJSONWithFile,
                         @RequestBody(required = false) String companyJSONNoFile,
                         FileUploadDTO fileUploadDTO) throws IOException {
        String result = null;
        String companyJSON = companyJSONWithFile == null ? companyJSONNoFile : companyJSONWithFile;

        Company company = Company.getInstance(companyJSON);
        if (fileUploadDTO.getFileData0() != null && fileUploadDTO.getFileData0().getSize() > 0) {
            TFile logo = new TFile();
            logo.setData(fileUploadDTO.getFileData0().getBytes());
            logo.setName(fileUploadDTO.getFileData0().getOriginalFilename());
            logo.setSize(fileUploadDTO.getFileData0().getSize());
            logo.setMimeType(fileUploadDTO.getFileData0().getContentType());

            company.setLogo(logo);
            result = TUtils.encode64(company.getLogo().getData(), logo.getMimeType());
        }
        service.updateCompany(company);

        return result;
    }
     */

    @RequestMapping (method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Company getRegisterPage() {

        Company company = service.getCompanyForUpdate(TUtils.getAuthenticatedUser().getUserId());

        return company;
    }
}
