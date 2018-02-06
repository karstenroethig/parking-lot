package karstenroethig.parkinglot.webapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	uniqueConstraints = {
		@UniqueConstraint( columnNames = {"id" } ),
		@UniqueConstraint( columnNames = {"name" } )
	}
)
public class Company
{
	@Column(
		length = 18,
		nullable = false,
		unique = true
	)
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Id
	private Long id;

	@Column(
		length = 255,
		nullable = false
	)
	private String name;

	@Column( nullable = false )
	private Boolean vendor;

	@Column( nullable = false )
	private Boolean archived;
}
