package ro.tekin.disertatie.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import ro.tekin.disertatie.entity.TException;
import ro.tekin.disertatie.service.TService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by tekin.omer on 2/4/14.
 */
public class CustomExceptionHandler implements HandlerExceptionResolver, Ordered {

    private Logger logger = Logger.getLogger(CustomExceptionHandler.class);

    @Autowired
    private TService service;

    public static final String EX_PATH_PARAM = "exceptions.path";

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        logger.error("Am prins eroarea global: ", e);
        TException tException = new TException();
        tException.setDate(new Date());
        tException.setStacktrace(ExceptionUtils.getStackTrace(e));

        service.saveException(tException);
        ModelMap model = new ModelMap();
        model.addAttribute("id", tException.getId());

        if ("XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"))) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ModelAndView("/errors/errorAjax", model);
        }
        return new ModelAndView("/errors/error", model);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
