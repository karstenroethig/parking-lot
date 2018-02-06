package karstenroethig.parkinglot.webapp.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class OfferPositionDto
{
	private Long id;

	@NotNull
	private CompanyDto vendor;

	@NotNull
	@Size(
		min = 1,
		max = 255
	)
	private String nameOffer;

	@Size(max = 255)
	private String nameIntern;

	@NotNull
	private Integer packingUnit;

	@NotNull
	@Digits(
		integer = 10,
		fraction = 2
	)
	private BigDecimal price;

	@NotNull
	@Size(
		min = 1,
		max = 255
	)
	private String offerNumber;
}
