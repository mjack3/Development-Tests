
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCard extends DomainEntity {

	public CreditCard() {
		super();
	}


	private String	brandName;
	private String	number;
	private Integer	CVV;
	private Integer	month;
	private Integer	year;


	@CreditCardNumber
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 100, max = 999)
	public Integer getCVV() {
		return this.CVV;
	}

	public void setCVV(final Integer cVV) {
		this.CVV = cVV;
	}

	@Range(min = 1, max = 12)
	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(final Integer month) {
		this.month = month;
	}

	@Range(min = 17, max = 99)
	public Integer getYear() {
		return this.year;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	@Pattern(regexp = "VISA|MASTERCARD|DISCOVER|DINNERS|AMEX")
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

}
