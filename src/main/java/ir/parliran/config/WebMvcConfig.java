package ir.parliran.config;

import ir.parliran.global.ControllerCommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * ©hFahimi.com @ 2019/12/16 11:35
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ControllerCommonInterceptor controllerCommonInterceptor;

    @Autowired
    public WebMvcConfig(ControllerCommonInterceptor controllerCommonInterceptor) {
        this.controllerCommonInterceptor = controllerCommonInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerCommonInterceptor);
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:labels_fa");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

/*
    @Bean
    public Configuration messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:labels_fa");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

*/

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

}