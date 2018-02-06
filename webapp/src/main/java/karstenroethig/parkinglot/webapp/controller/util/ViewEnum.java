package karstenroethig.parkinglot.webapp.controller.util;

import org.apache.commons.lang3.StringUtils;

public enum ViewEnum
{
	COMPANY_CREATE( ControllerEnum.company, ActionEnum.create ),
	COMPANY_EDIT( ControllerEnum.company, ActionEnum.edit ),
	COMPANY_LIST( ControllerEnum.company, ActionEnum.list ),

	OFFER_POSITION_CREATE( ControllerEnum.offerposition, ActionEnum.create ),
	OFFER_POSITION_SHOW( ControllerEnum.offerposition, ActionEnum.show ),
	OFFER_POSITION_EDIT( ControllerEnum.offerposition, ActionEnum.edit ),
	OFFER_POSITION_LIST( ControllerEnum.offerposition, ActionEnum.list ),

	ADMIN_SERVER_INFO( ControllerEnum.admin, "server-info" );

	private static final String VIEW_SUBDIRECTORY = "views";

	private String viewName = StringUtils.EMPTY;

	private enum ControllerEnum
	{
		admin, company, offerposition;
	}

	private enum ActionEnum
	{
		create, edit, list, show;
	}

	private ViewEnum( ControllerEnum controller, ActionEnum action )
	{
		this( controller, action.name() );
	}

	private ViewEnum( ControllerEnum controller, String action )
	{
		viewName += VIEW_SUBDIRECTORY;

		if ( StringUtils.isNoneBlank( viewName ) )
		{
			viewName += "/";
		}

		viewName += controller.name();
		viewName += "/";
		viewName += action;
	}

	public String getViewName()
	{
		return viewName;
	}
}
