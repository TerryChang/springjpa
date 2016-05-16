package com.terry.springjpa.config.servlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.terry.springjpa.config.servlet.validate.ExistCheckValidator;

@Configuration
@Import({LocalConfig.class, ProductionConfig.class, DevConfig.class, SpringDataWebConfiguration.class})
@EnableWebMvc
@ComponentScan(
		basePackages="com.terry.springjpa",
		useDefaultFilters = false,
		includeFilters={
			@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Controller.class),
			@ComponentScan.Filter(type=FilterType.ANNOTATION, value=ControllerAdvice.class)
		}
)
public class ServletContextMain extends WebMvcConfigurerAdapter{

	@Override
	/**
	 * <mvc:default-servlet-handler /> 태그와 같은 역할을 한다
	 * 파라미터로 넘겨받은 configurer의 enable 메소드를 실행하면 된다
	 * 만약 default servlet name이 다른 경우(Spring은 세계적으로 유명한 WAS에 대해서는 default servlet name을 알고 있지만 Jeus 같이 국내에서만 사용되는
	 * WAS의 경우 default servlet name을 알지 못한다)
	 * enable(default-servlet-name) 을 주어서 그걸로 default servlet을 사용하도록 한다
	 */
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		configurer.enable();
	}
	
	@Bean
	public ViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(1);
		return viewResolver;
	}
	
	/*
	다른 view Resolver를 추가해야 할 경우 다음과 같은 형태로 viewResolver를 만든뒤에
	setOrder 메소드를 이용해서 order 순위를 재조정한다
	@Bean(name="excelViewResolver")
    public ViewResolver getXmlViewResolver() {
        XmlViewResolver resolver = new XmlViewResolver();
        resolver.setLocation(new ServletContextResource(servletContext,
                    "/WEB-INF/spring/spreadsheet-views.xml"));
        resolver.setOrder(1);
        return resolver;
    }
    */
	
	/**
	 * Locale Message 관련 Bean 생성
	 * @return
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() { 
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:com/terry/springjpa/resources/messages/validation_messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setCacheSeconds(1);
        return messageSource;
    }
	
	/**
	 * Validator 생성
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean validator(){
		LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
		lvfb.setValidationMessageSource(messageSource());
		return lvfb;
	}
	
	@Bean
	public ExistCheckValidator existCheckValidator(){
		return new ExistCheckValidator();
	}

	/**
	 * @Value 어노테이션에 ${}를 이용해서 값을 가져오도록 하기 위해 PropertySourcesPlaceholderConfigurer bean을 선언한다.
	 * 이 Bean 선언의 경우 두가지 주의점이 있다
	 * 1. 반드시 static 으로 선언할것(@Configuration 에서조차도 @Value 어노테이션을 이용해서 ${}를 사용하는 것이기 때문에 가장 먼저 로드가 되어야 하기 때문이다)
	 * 2. Root Context에 선언되어 있다 하더라도 Servlet Context 에서도 따로 선언해주어야 한다
	 * Root Context에 선언된 것은 Root Context의 @Configuration에서만 영향력을 갖지 Servlet Context가 이것을 끌어와서 사용하는 개념이 아니기 때문이다
	 * @return
	 */
	@Bean 
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return validator();
	}
	
	@Bean
	public PageableHandlerMethodArgumentResolver pageableResolver(){
		PageableHandlerMethodArgumentResolver phmar = new PageableHandlerMethodArgumentResolver(sortResolver());
		phmar.setOneIndexedParameters(true);
		phmar.setPageParameterName("pageNo");
		phmar.setSizeParameterName("pageSize");
		return phmar;
	}
	
	@Bean
	public SortHandlerMethodArgumentResolver sortResolver() {
		return new SortHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		argumentResolvers.add(pageableResolver());
		argumentResolvers.add(sortResolver());
		super.addArgumentResolvers(argumentResolvers);
	}
	
	
	
}
