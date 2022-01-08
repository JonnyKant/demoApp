package com.modeling.demo.configs;

import com.modeling.demo.validation.EmailValidator;
import com.modeling.demo.validation.PasswordMatchesValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Locale;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/images/");
        registry.addResourceHandler("/styles/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/analysis/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/analysis/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//            registry.addInterceptor(localeChangeInterceptor()); //Устанавливаем новый перехватчик приложения
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
        registry.addRedirectViewController("/","/catalog");
    }
    @Bean
    @Description("Thymeleaf Template Resolver")
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }
//
    @Bean
    @Description("Thymeleaf Template Engine")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.setTemplateEngineMessageSource(messageSource());
        return templateEngine;
    }
//
    @Bean
    @Description("Thymeleaf View Resolver")
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Bean
    @Description("Spring Message Resolver")
    public ResourceBundleMessageSource messageSource() { //Указывает обработчик сообщений на основе языкового стандарта
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(false); //Вместо исключения код?
        messageSource.setBasename("templates/locale"); //Указываем директорию locale bundle
        return messageSource;
    }
//    @Bean
//    public LocaleResolver localeResolver() {  //Определяет текущий языковой стандарт, через этот бин мы можем его получить
//        SessionLocaleResolver slr = new SessionLocaleResolver();//Определяем откуда берем параметр для определения локали
//        slr.setDefaultLocale(Locale.FRENCH);
//        return slr;
//    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }


//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() { //Здесь мы получаем возможность изменить параметр с accept-lang на свой
//        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//        lci.setParamName("lang");
//        return lci;
//    }

    @Bean
    public EmailValidator usernameValidator() {
        return new EmailValidator();
    }

    @Bean
    public PasswordMatchesValidator passwordMatchesValidator() {
        return new PasswordMatchesValidator();
    }

}
