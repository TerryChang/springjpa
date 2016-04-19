package com.terry.springjpa.config.servlet;

import java.util.List;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.geo.format.DistanceFormatter;
import org.springframework.data.geo.format.PointFormatter;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ProxyingHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Spring Data를 Web에서 사용할 경우 @EnableSpringDataWebSupport를 사용해야 한다.
 * 이걸 사용하면 Domain Class Converter 기능과 페이징/정렬 기능을 사용할 수 있게 되는데..
 * 이를 커스터마이징 해야 할 경우 @EnableSpringDataWebSupport 어노테이션을 빼고 별도의 환경설정 클래스를 만들어서 
 * 이를 사용하도록한다(@EnableSpringDataWebSupport 어노테이션을 사용할 경우 ClassPath에서 Hateoas 관련 라이브러리가 있을 경우
 * org.springframework.data.web.config 패키지의 HateoasAwareSpringDataWebConfiguration 클래스를 로딩하게 되며
 * 그렇지 않을 경우 같은 패키지의 SpringDataWebConfiguration 클래스를 로딩하게 된다.
 * 현재 보고 있는 SpringDataWebConfiguration 클래스는 위에서 언급한 SpringDataWebConfiguration 클래스를 기반으로 작성한 것이다
 * org.springframework.data.web.config.SpringDataWebConfiguration 클래스를 사용하지 않은 이유는 페이징/정렬 기능과 관련된
 * PageableHandlerMethodArgumentResolver 와 SortHandlerMethodArgumentResolver 클래스 bean들을 커스터마이징 하기 위해서이다.
 * 
 * @author TerryChang
 *
 */
@Configuration
public class SpringDataWebConfiguration extends WebMvcConfigurerAdapter  {

	@Autowired private ApplicationContext context;
	@Autowired @Qualifier("mvcConversionService") ObjectFactory<ConversionService> conversionService;
	
	@Bean
	public PageableHandlerMethodArgumentResolver pageableResolver() {
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
	public void addFormatters(FormatterRegistry registry) {

		registry.addFormatter(DistanceFormatter.INSTANCE);
		registry.addFormatter(PointFormatter.INSTANCE);

		if (!(registry instanceof FormattingConversionService)) {
			return;
		}

		FormattingConversionService conversionService = (FormattingConversionService) registry;

		DomainClassConverter<FormattingConversionService> converter = new DomainClassConverter<FormattingConversionService>(
				conversionService);
		converter.setApplicationContext(context);
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

		argumentResolvers.add(sortResolver());
		argumentResolvers.add(pageableResolver());

		ProxyingHandlerMethodArgumentResolver resolver = new ProxyingHandlerMethodArgumentResolver(
				conversionService.getObject());
		resolver.setBeanFactory(context);
		resolver.setResourceLoader(context);

		argumentResolvers.add(resolver);
	}
}
