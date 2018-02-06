package karstenroethig.parkinglot.webapp.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(
	name = "Offer_Position",
	uniqueConstraints = {
		@UniqueConstraint( columnNames = { "id" } )
	}
)
public class OfferPosition
{
	@Column(
		length = 18,
		nullable = false,
		unique = true
	)
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Id
	private Long id;

	@JoinColumn( name = "vendor_id" )
	@ManyToOne( optional = false )
	private Company vendor;

	@Column(
		name = "name_offer",
		length = 255,
		nullable = false
	)
	private String nameOffer;

	@Column(
		name = "name_intern",
		length = 255,
		nullable = true
	)
	private String nameIntern;

	@Column(
		name = "packing_unit",
		nullable = false
	)
	private Integer packingUnit;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(
		name = "offer_number",
		length = 255,
		nullable = false
	)
	private String offerNumber;
}
