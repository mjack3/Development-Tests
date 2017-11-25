
package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	public Configuration() {
		super();
		this.historic = new HashSet<String>();

	}


	private Collection<String>	historic;
	private Integer				month;
	private Integer				year;

	private Double				fee;


	@Range(min = 1, max = 12)
	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(final Integer month) {
		this.month = month;
	}

	@Range(min = 1900)
	public Integer getYear() {
		return this.year;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	@NotNull
	@Range(min = 0)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getHistoric() {
		return this.historic;
	}

	public void setHistoric(final Collection<String> historic) {
		this.historic = historic;
	}

	//	Other business methods	----------

	public void addHistoric(final Integer month, final Integer year) {

		final String string = String.format("%02d", month) + "/" + year;
		this.historic.add(string);
	}
}
