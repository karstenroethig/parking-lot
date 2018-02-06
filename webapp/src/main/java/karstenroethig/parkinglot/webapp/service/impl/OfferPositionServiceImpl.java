package karstenroethig.parkinglot.webapp.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karstenroethig.parkinglot.webapp.domain.OfferPosition;
import karstenroethig.parkinglot.webapp.dto.DtoTransformer;
import karstenroethig.parkinglot.webapp.dto.OfferPositionDto;
import karstenroethig.parkinglot.webapp.dto.info.DashboardInfoDto;
import karstenroethig.parkinglot.webapp.repository.CompanyRepository;
import karstenroethig.parkinglot.webapp.repository.OfferPositionRepository;
import karstenroethig.parkinglot.webapp.service.OfferPositionService;

@Service
@Transactional
public class OfferPositionServiceImpl implements OfferPositionService
{
	@Autowired
	protected OfferPositionRepository offerPositionRepository;

	@Autowired
	protected CompanyRepository companyRepository;

	@Override
	public OfferPositionDto newOfferPosition()
	{
		OfferPositionDto offerPositionDto = new OfferPositionDto();

		return offerPositionDto;
	}

	@Override
	public OfferPositionDto saveOfferPosition( OfferPositionDto offerPositionDto )
	{
		OfferPosition offerPosition = new OfferPosition();

		offerPosition = merge( offerPosition, offerPositionDto );

		return transform( offerPositionRepository.save( offerPosition ) );
	}

	@Override
	public Boolean deleteOfferPosition( Long offerPositionId )
	{
		OfferPosition temp = offerPositionRepository.findOne( offerPositionId );

		if ( temp != null )
		{
			offerPositionRepository.delete( temp );

			return true;
		}

		return false;
	}

	@Override
	public OfferPositionDto editOfferPosition( OfferPositionDto offerPositionDto )
	{
		OfferPosition offerPosition = offerPositionRepository.findOne( offerPositionDto.getId() );

		offerPosition = merge( offerPosition, offerPositionDto );

		return transform( offerPositionRepository.save( offerPosition ) );
	}

	@Override
	public List<OfferPositionDto> getAllOfferPositions()
	{
		List<OfferPositionDto> offerPositions = StreamSupport
			.stream( offerPositionRepository.findAll().spliterator(), false )
			.map( ( offerPosition ) -> transform( offerPosition ) )
			.collect( Collectors.toList() );

		return offerPositions;
	}

	@Override
	public OfferPositionDto findOfferPosition( Long offerPositionId )
	{
		return transform( offerPositionRepository.findOne( offerPositionId ) );
	}

	private OfferPosition merge( OfferPosition offerPosition, OfferPositionDto offerPositionDto )
	{
		if ( ( offerPosition == null ) || ( offerPositionDto == null ) )
		{
			return null;
		}

		if ( offerPositionDto.getVendor() != null )
		{
			offerPosition.setVendor( companyRepository.findOne( offerPositionDto.getVendor().getId() ) );
		}

		offerPosition.setNameOffer( offerPositionDto.getNameOffer() );
		offerPosition.setNameIntern( offerPositionDto.getNameIntern() );
		offerPosition.setPackingUnit( offerPositionDto.getPackingUnit() );
		offerPosition.setPrice( offerPositionDto.getPrice() );
		offerPosition.setOfferNumber( offerPositionDto.getOfferNumber() );

		return offerPosition;
	}

	private OfferPositionDto transform( OfferPosition offerPosition )
	{
		if ( offerPosition == null )
		{
			return null;
		}

		OfferPositionDto offerPositionDto = new OfferPositionDto();

		offerPositionDto.setId( offerPosition.getId() );
		offerPositionDto.setVendor( DtoTransformer.transform( offerPosition.getVendor() ) );
		offerPositionDto.setNameOffer( offerPosition.getNameOffer() );
		offerPositionDto.setNameIntern( offerPosition.getNameIntern() );
		offerPositionDto.setPackingUnit( offerPosition.getPackingUnit() );
		offerPositionDto.setPrice( offerPosition.getPrice() );
		offerPositionDto.setOfferNumber( offerPosition.getOfferNumber() );

		return offerPositionDto;
	}

	@Override
	public DashboardInfoDto createDashboradStatistics()
	{
		int totalOfferPositions = 0;

		Iterator<OfferPosition> itr = offerPositionRepository.findAll().iterator();

		while (itr.hasNext())
		{
			itr.next();
			totalOfferPositions++;
		}

		DashboardInfoDto stats = new DashboardInfoDto();

		stats.setTotalOfferPositions( totalOfferPositions );

		return stats;
	}
}
