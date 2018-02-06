package karstenroethig.parkinglot.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import karstenroethig.parkinglot.webapp.controller.exceptions.NotFoundException;
import karstenroethig.parkinglot.webapp.controller.util.UrlMappings;
import karstenroethig.parkinglot.webapp.controller.util.ViewEnum;
import karstenroethig.parkinglot.webapp.dto.CompanyDto;
import karstenroethig.parkinglot.webapp.service.CompanyService;
import karstenroethig.parkinglot.webapp.service.exceptions.CompanyAlreadyExistsException;
import karstenroethig.parkinglot.webapp.util.MessageKeyEnum;
import karstenroethig.parkinglot.webapp.util.Messages;

@ComponentScan
@Controller
@RequestMapping( UrlMappings.CONTROLLER_COMPANY )
public class CompanyController
{
	@Autowired
	CompanyService companyService;

	@RequestMapping(
		value = UrlMappings.ACTION_LIST,
		method = RequestMethod.GET
	)
	public String list( Model model )
	{
		model.addAttribute( "allCompanies", companyService.getAllCompanies() );

		return ViewEnum.COMPANY_LIST.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_CREATE,
		method = RequestMethod.GET
	)
	public String create( Model model )
	{
		model.addAttribute( "company", companyService.newCompany() );

		return ViewEnum.COMPANY_CREATE.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_EDIT,
		method = RequestMethod.GET
	)
	public String edit( @PathVariable( "id" ) Long companyId, Model model )
	{
		CompanyDto company = companyService.findCompany( companyId );

		if ( company == null )
		{
			throw new NotFoundException( String.valueOf( companyId ) );
		}

		model.addAttribute( "company", company );

		return ViewEnum.COMPANY_EDIT.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_DELETE,
		method = RequestMethod.GET
	)
	public String delete( @PathVariable( "id" ) Long companyId, final RedirectAttributes redirectAttributes, Model model )
	{
		CompanyDto company = companyService.findCompany( companyId );

		if ( company == null )
		{
			throw new NotFoundException( String.valueOf( companyId ) );
		}

		if ( companyService.deleteCompany( companyId ) )
		{
			redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
				Messages.createWithSuccess( MessageKeyEnum.COMPANY_DELETE_SUCCESS, company.getName() ) );
		}
		else
		{
			redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
				Messages.createWithError( MessageKeyEnum.COMPANY_DELETE_ERROR, company.getName() ) );
		}

		return UrlMappings.redirect( UrlMappings.CONTROLLER_COMPANY, UrlMappings.ACTION_LIST );
	}

	@RequestMapping(
		value = UrlMappings.ACTION_SAVE,
		method = RequestMethod.POST
	)
	public String save( @ModelAttribute( "company" ) @Valid CompanyDto company, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model )
	{
		if ( bindingResult.hasErrors() )
		{
			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.COMPANY_SAVE_INVALID ) );

			return ViewEnum.COMPANY_CREATE.getViewName();
		}

		try
		{
			if ( companyService.saveCompany( company ) != null )
			{
				redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess( MessageKeyEnum.COMPANY_SAVE_SUCCESS, company.getName() ) );

				return UrlMappings.redirect( UrlMappings.CONTROLLER_COMPANY, UrlMappings.ACTION_LIST );
			}
		}
		catch ( CompanyAlreadyExistsException ex )
		{
			bindingResult.rejectValue( "name", "company.error.exists" );

			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.COMPANY_SAVE_INVALID ) );

			return ViewEnum.COMPANY_CREATE.getViewName();
		}

		model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.COMPANY_SAVE_ERROR ) );

		return ViewEnum.COMPANY_CREATE.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_UPDATE,
		method = RequestMethod.POST
	)
	public String update( @ModelAttribute( "company" ) @Valid CompanyDto company, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model )
	{
		if ( bindingResult.hasErrors() )
		{
			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.COMPANY_UPDATE_INVALID ) );

			return ViewEnum.COMPANY_EDIT.getViewName();
		}

		try
		{
			if ( companyService.editCompany( company ) != null )
			{
				redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess( MessageKeyEnum.COMPANY_UPDATE_SUCCESS, company.getName() ) );

				return UrlMappings.redirect( UrlMappings.CONTROLLER_COMPANY, UrlMappings.ACTION_LIST );
			}
		}
		catch ( CompanyAlreadyExistsException ex )
		{
			bindingResult.rejectValue( "name", "company.error.exists" );

			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.COMPANY_UPDATE_INVALID ) );

			return ViewEnum.COMPANY_EDIT.getViewName();
		}

		model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.COMPANY_UPDATE_ERROR ) );

		return ViewEnum.COMPANY_EDIT.getViewName();
	}

	@ExceptionHandler( NotFoundException.class )
	void handleNotFoundException( HttpServletResponse response, NotFoundException ex ) throws IOException {
		response.sendError( HttpStatus.NOT_FOUND.value(), String.format( "Company %s does not exist.", ex.getMessage() ) );
	}
}
