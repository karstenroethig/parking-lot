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
import karstenroethig.parkinglot.webapp.dto.OfferPositionDto;
import karstenroethig.parkinglot.webapp.service.CompanyService;
import karstenroethig.parkinglot.webapp.service.OfferPositionService;
import karstenroethig.parkinglot.webapp.util.MessageKeyEnum;
import karstenroethig.parkinglot.webapp.util.Messages;

@ComponentScan
@Controller
@RequestMapping( UrlMappings.CONTROLLER_OFFER_POSITION )
public class OfferPositionController
{
	@Autowired
	OfferPositionService offerPositionService;

	@Autowired
	CompanyService companyService;

	@RequestMapping(
		value = UrlMappings.ACTION_LIST,
		method = RequestMethod.GET
	)
	public String list( Model model )
	{
		model.addAttribute( "allOfferPositions", offerPositionService.getAllOfferPositions() );

		return ViewEnum.OFFER_POSITION_LIST.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_CREATE,
		method = RequestMethod.GET
	)
	public String create( Model model )
	{
		model.addAttribute( "offerPosition", offerPositionService.newOfferPosition() );
		model.addAttribute( "allUnarchivedCompanies", companyService.getAllUnarchivedCompanies() );

		return ViewEnum.OFFER_POSITION_CREATE.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_SHOW,
		method = RequestMethod.GET
	)
	public String show( @PathVariable( "id" ) Long offerPositionId, Model model )
	{
		OfferPositionDto offerPosition = offerPositionService.findOfferPosition( offerPositionId );

		if ( offerPosition == null )
		{
			throw new NotFoundException( String.valueOf( offerPositionId ) );
		}

		model.addAttribute( "offerPosition", offerPosition );

		return ViewEnum.OFFER_POSITION_SHOW.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_EDIT,
		method = RequestMethod.GET
	)
	public String edit( @PathVariable( "id" ) Long offerPositionId, Model model )
	{
		OfferPositionDto offerPosition = offerPositionService.findOfferPosition( offerPositionId );

		if ( offerPosition == null )
		{
			throw new NotFoundException( String.valueOf( offerPositionId ) );
		}

		model.addAttribute( "offerPosition", offerPosition );
		model.addAttribute( "allUnarchivedCompanies", companyService.getAllUnarchivedCompanies() );
		model.addAttribute( "allArchivedCompanies", companyService.getAllArchivedCompanies() );

		return ViewEnum.OFFER_POSITION_EDIT.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_DELETE,
		method = RequestMethod.GET
	)
	public String delete( @PathVariable( "id" ) Long offerPositionId, final RedirectAttributes redirectAttributes, Model model )
	{
		OfferPositionDto offerPosition = offerPositionService.findOfferPosition( offerPositionId );

		if ( offerPosition == null )
		{
			throw new NotFoundException( String.valueOf( offerPositionId ) );
		}

		if ( offerPositionService.deleteOfferPosition( offerPositionId ) )
		{
			redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
				Messages.createWithSuccess( MessageKeyEnum.OFFER_POSITION_DELETE_SUCCESS, offerPosition.getNameOffer() ) );
		}
		else
		{
			redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
				Messages.createWithError( MessageKeyEnum.OFFER_POSITION_DELETE_ERROR, offerPosition.getNameOffer() ) );
		}

		return UrlMappings.redirect( UrlMappings.CONTROLLER_OFFER_POSITION, UrlMappings.ACTION_LIST );
	}

	@RequestMapping(
		value = UrlMappings.ACTION_SAVE,
		method = RequestMethod.POST
	)
	public String save( @ModelAttribute( "offerPosition" ) @Valid OfferPositionDto offerPosition, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model )
	{
		if ( bindingResult.hasErrors() )
		{
			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.OFFER_POSITION_SAVE_INVALID ) );

			model.addAttribute( "allUnarchivedCompanies", companyService.getAllUnarchivedCompanies() );

			return ViewEnum.OFFER_POSITION_CREATE.getViewName();
		}

		if ( offerPositionService.saveOfferPosition( offerPosition ) != null )
		{
			redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
				Messages.createWithSuccess( MessageKeyEnum.OFFER_POSITION_SAVE_SUCCESS, offerPosition.getNameOffer() ) );

			return UrlMappings.redirect( UrlMappings.CONTROLLER_OFFER_POSITION, UrlMappings.ACTION_LIST );
		}

		model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.OFFER_POSITION_SAVE_ERROR ) );

		model.addAttribute( "allUnarchivedCompanies", companyService.getAllUnarchivedCompanies() );

		return ViewEnum.OFFER_POSITION_CREATE.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_UPDATE,
		method = RequestMethod.POST
	)
	public String update( @ModelAttribute( "offerPosition" ) @Valid OfferPositionDto offerPosition, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model )
	{
		if ( bindingResult.hasErrors() )
		{
			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.OFFER_POSITION_UPDATE_INVALID ) );

			model.addAttribute( "allUnarchivedCompanies", companyService.getAllUnarchivedCompanies() );
			model.addAttribute( "allArchivedCompanies", companyService.getAllArchivedCompanies() );

			return ViewEnum.OFFER_POSITION_EDIT.getViewName();
		}

		if ( offerPositionService.editOfferPosition( offerPosition ) != null )
		{
			redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
				Messages.createWithSuccess( MessageKeyEnum.OFFER_POSITION_UPDATE_SUCCESS, offerPosition.getNameOffer() ) );

			return UrlMappings.redirect( UrlMappings.CONTROLLER_OFFER_POSITION, UrlMappings.ACTION_LIST );
		}

		model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.OFFER_POSITION_UPDATE_ERROR ) );

		model.addAttribute( "allUnarchivedCompanies", companyService.getAllUnarchivedCompanies() );
		model.addAttribute( "allArchivedCompanies", companyService.getAllArchivedCompanies() );

		return ViewEnum.OFFER_POSITION_EDIT.getViewName();
	}

	@ExceptionHandler( NotFoundException.class )
	void handleNotFoundException( HttpServletResponse response, NotFoundException ex ) throws IOException
	{
		response.sendError( HttpStatus.NOT_FOUND.value(), String.format( "Offer position %s does not exist.", ex.getMessage() ) );
	}
}
