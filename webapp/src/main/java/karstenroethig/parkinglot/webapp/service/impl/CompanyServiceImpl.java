package karstenroethig.parkinglot.webapp.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karstenroethig.parkinglot.webapp.domain.Company;
import karstenroethig.parkinglot.webapp.dto.CompanyDto;
import karstenroethig.parkinglot.webapp.dto.DtoTransformer;
import karstenroethig.parkinglot.webapp.repository.CompanyRepository;
import karstenroethig.parkinglot.webapp.service.CompanyService;
import karstenroethig.parkinglot.webapp.service.exceptions.CompanyAlreadyExistsException;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService
{
	@Autowired
	protected CompanyRepository companyRepository;

	@Override
	public CompanyDto newCompany()
	{
		CompanyDto companyDto = new CompanyDto();

		companyDto.setArchived( Boolean.FALSE );

		return companyDto;
	}

	@Override
	public CompanyDto saveCompany( CompanyDto companyDto ) throws CompanyAlreadyExistsException
	{
		List<Company> existingCountries = companyRepository.findByNameIgnoreCase(
			StringUtils.trim( companyDto.getName() ) );

		if ( existingCountries != null && existingCountries.isEmpty() == false )
		{
			throw new CompanyAlreadyExistsException();
		}

		Company company = new Company();

		company = DtoTransformer.merge( company, companyDto );

		return DtoTransformer.transform( companyRepository.save( company ) );
	}

	@Override
	public Boolean deleteCompany( Long companyId )
	{
		Company temp = companyRepository.findOne( companyId );

		if ( temp != null )
		{
			companyRepository.delete( temp );

			return true;
		}

		return false;
	}

	@Override
	public CompanyDto editCompany( CompanyDto companyDto ) throws CompanyAlreadyExistsException
	{
		List<Company> existingCountries = companyRepository.findByNameIgnoreCase(
			StringUtils.trim( companyDto.getName() ) );

		if ( existingCountries != null && existingCountries.isEmpty() == false
			&& existingCountries.get( 0 ).getId().equals( companyDto.getId() ) == false )
		{
			throw new CompanyAlreadyExistsException();
		}

		Company company = companyRepository.findOne( companyDto.getId() );

		company = DtoTransformer.merge( company, companyDto );

		return DtoTransformer.transform( companyRepository.save( company ) );
	}

	@Override
	public CompanyDto findCompany( Long companyId )
	{
		return DtoTransformer.transform( companyRepository.findOne( companyId ) );
	}

	@Override
	public List<CompanyDto> getAllCompanies()
	{
		return transformCompanies( companyRepository.findAll() );
	}

	@Override
	public List<CompanyDto> getAllArchivedCompanies()
	{
		return transformCountries( companyRepository.findByArchived( true ) );
	}

	@Override
	public List<CompanyDto> getAllUnarchivedCompanies()
	{
		return transformCompanies( companyRepository.findByArchived( false ) );
	}

	@Override
	public List<CompanyDto> getAllVendorCompanies()
	{
		return transformCompanies( companyRepository.findByVendor( true ) );
	}

	private List<CompanyDto> transformCountries( List<Company> companies )
	{
		return transformCompanies( companies.stream() );
	}

	private List<CompanyDto> transformCompanies( Iterable<Company> companies )
	{
		return transformCompanies( StreamSupport.stream( companies.spliterator(), false ) );
	}

	private List<CompanyDto> transformCompanies( Stream<Company> companiesStream )
	{
		List<CompanyDto> transformedCountries = companiesStream
			.map( DtoTransformer::transform )
			.sorted( Comparator.comparing( CompanyDto::getName ) )
			.collect( Collectors.toList() );

		return transformedCountries;
	}
}
