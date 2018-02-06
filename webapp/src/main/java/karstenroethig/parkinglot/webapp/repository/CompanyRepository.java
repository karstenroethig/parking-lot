package karstenroethig.parkinglot.webapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import karstenroethig.parkinglot.webapp.domain.Company;

public interface CompanyRepository extends CrudRepository<Company, Long>
{
	List<Company> findByNameIgnoreCase( String name );

	List<Company> findByArchived( boolean archived );

	List<Company> findByVendor( boolean vendor );
}
