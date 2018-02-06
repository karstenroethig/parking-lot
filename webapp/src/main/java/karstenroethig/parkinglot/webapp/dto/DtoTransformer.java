package karstenroethig.parkinglot.webapp.dto;

import karstenroethig.parkinglot.webapp.domain.Company;

public class DtoTransformer
{
	private DtoTransformer() {}

	/*
	 * =======
	 * Company
	 * =======
	 */

	public static Company merge( Company company, CompanyDto companyDto )
	{
		if ( ( company == null ) || ( companyDto == null ) )
		{
			return null;
		}

		company.setName( companyDto.getName() );
		company.setVendor( companyDto.getVendor() );
		company.setArchived( companyDto.getArchived() );

		return company;
	}

	public static CompanyDto transform( Company company )
	{
		if ( company == null )
		{
			return null;
		}

		CompanyDto companyDto = new CompanyDto();

		companyDto.setId( company.getId() );
		companyDto.setName( company.getName() );
		companyDto.setVendor( company.getVendor() );
		companyDto.setArchived( company.getArchived() );

		return companyDto;
	}
}
