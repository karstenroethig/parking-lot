package karstenroethig.parkinglot.webapp.service;

import java.util.Collection;

import karstenroethig.parkinglot.webapp.dto.OfferPositionDto;
import karstenroethig.parkinglot.webapp.dto.info.DashboardInfoDto;

public interface OfferPositionService
{
	public OfferPositionDto newOfferPosition();

	public OfferPositionDto saveOfferPosition( OfferPositionDto offerPositionDto );

	public Boolean deleteOfferPosition( Long offerPositionId );

	public OfferPositionDto editOfferPosition( OfferPositionDto offerPositionDto );

	public OfferPositionDto findOfferPosition( Long offerPositionId );

	public Collection<OfferPositionDto> getAllOfferPositions();

	public DashboardInfoDto createDashboradStatistics();
}
