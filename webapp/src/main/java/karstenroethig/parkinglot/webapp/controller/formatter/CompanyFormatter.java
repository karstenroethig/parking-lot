package karstenroethig.parkinglot.webapp.controller.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import karstenroethig.parkinglot.webapp.dto.CompanyDto;

public class CompanyFormatter implements Formatter<CompanyDto>
{
	@Override
	public String print( CompanyDto company, Locale locale )
	{
		if ( ( company == null ) || ( company.getId() == null ) )
		{
			return StringUtils.EMPTY;
		}

		return company.getId().toString();
	}

	@Override
	public CompanyDto parse( String id, Locale locale ) throws ParseException
	{
		if ( StringUtils.isBlank( id ) && ( StringUtils.isNumeric( id ) == false ) )
		{
			return null;
		}

		CompanyDto company = new CompanyDto();

		company.setId( Long.parseLong( id ) );

		return company;
	}
}
