package karstenroethig.parkinglot.webapp.dto;

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
public class CompanyDto
{
	private Long id;

	@NotNull
	@Size(
		min = 1,
		max = 255
	)
	private String name;

	@NotNull
	private Boolean vendor;

	@NotNull
	private Boolean archived;
}
