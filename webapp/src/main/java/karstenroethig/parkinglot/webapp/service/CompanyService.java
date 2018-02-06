package karstenroethig.parkinglot.webapp.service;

import java.util.Collection;

import karstenroethig.parkinglot.webapp.dto.CompanyDto;
import karstenroethig.parkinglot.webapp.service.exceptions.CompanyAlreadyExistsException;

public interface CompanyService
{
	public CompanyDto newCompany();

	public CompanyDto saveCompany( CompanyDto companyDto ) throws CompanyAlreadyExistsException;

	public Boolean deleteCompany( Long companyId );

	public CompanyDto editCompany( CompanyDto companyDto ) throws CompanyAlreadyExistsException;

	public CompanyDto findCompany( Long companyId );

	public Collection<CompanyDto> getAllCompanies();

	public Collection<CompanyDto> getAllArchivedCompanies();

	public Collection<CompanyDto> getAllUnarchivedCompanies();

	public Collection<CompanyDto> getAllVendorCompanies();
}
