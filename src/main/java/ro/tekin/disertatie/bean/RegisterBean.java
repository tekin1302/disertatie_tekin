package ro.tekin.disertatie.bean;

import ro.tekin.disertatie.entity.Company;

/**
 * Created by diana on 4/16/14.
 */
public class RegisterBean {
    private Company company;
    private String captchaChallenge;
    private String captchaResp;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getCaptchaChallenge() {
        return captchaChallenge;
    }

    public void setCaptchaChallenge(String captchaChallenge) {
        this.captchaChallenge = captchaChallenge;
    }

    public String getCaptchaResp() {
        return captchaResp;
    }

    public void setCaptchaResp(String captchaResp) {
        this.captchaResp = captchaResp;
    }
}
