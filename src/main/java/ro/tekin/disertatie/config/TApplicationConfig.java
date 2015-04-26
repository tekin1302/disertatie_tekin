package ro.tekin.disertatie.config;

import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.Resources;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/**
 * Created by tekin.omer on 2/5/14.
 */
@Configuration
@ComponentScan(basePackages = {"ro.tekin.disertatie"})
@ImportResource( { "classpath*:META-INF/spring-security.xml", "classpath*:META-INF/schedule.xml"  } )
@EnableWebMvc
@Import({DataSourceConfig.class})
public class TApplicationConfig extends WebMvcConfigurerAdapter {

    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/pages/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public CustomExceptionHandler customExceptionHandler() {
        return new CustomExceptionHandler();
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }
    @Bean
    public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() {
        return new DefaultWebSecurityExpressionHandler();
    }
    /*
    @Bean
    public MappingJacksonHttpMessageConverter jacksonMessageConverter () {
        return new MappingJacksonHttpMessageConverter();
    }

    @Bean
    public AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter() {
        AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
        adapter.setMessageConverters(new HttpMessageConverter[] {jacksonMessageConverter()});
        return adapter;
    }*/
}
