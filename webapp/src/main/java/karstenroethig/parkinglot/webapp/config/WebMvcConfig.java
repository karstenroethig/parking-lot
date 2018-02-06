package karstenroethig.parkinglot.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import karstenroethig.parkinglot.webapp.controller.formatter.CompanyFormatter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
	@Override
	public void addFormatters(FormatterRegistry formatterRegistry)
	{
		formatterRegistry.addFormatter(new CompanyFormatter());
	}

	@Bean
	public LocaleResolver localeResolver()
	{
		SessionLocaleResolver slr = new SessionLocaleResolver();

		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor()
	{
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");

		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(localeChangeInterceptor());
	}
}
