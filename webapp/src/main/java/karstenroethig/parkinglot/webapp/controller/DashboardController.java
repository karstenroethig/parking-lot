package karstenroethig.parkinglot.webapp.controller;

import karstenroethig.parkinglot.webapp.controller.util.UrlMappings;
import karstenroethig.parkinglot.webapp.service.OfferPositionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@ComponentScan
@Controller
public class DashboardController
{
	@Autowired
	OfferPositionService offerPositionService;

	@RequestMapping(
		value = { UrlMappings.HOME, UrlMappings.DASHBOARD },
		method = RequestMethod.GET
	)
	public String dashborad( Model model )
	{
		model.addAttribute( "stats", offerPositionService.createDashboradStatistics() );
		
		return "views/dashboard";
	}
}
