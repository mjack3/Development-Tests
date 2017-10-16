
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	public CreditCard() {
		super();
	}


	private Integer	number;
	private Integer	CVV;
	private Date	expiryDate;
	private String	brandName;


	@CreditCardNumber
	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(final Integer number) {
		this.number = number;
	}

	@Range(min = 100, max = 999)
	public Integer getCVV() {
		return this.CVV;
	}

	public void setCVV(final Integer cVV) {
		this.CVV = cVV;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/yyyy")
	@Future
	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(final Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Pattern(regexp = "VISA|MASTERCARD|DISCOVER|DINNERS|AMEX")
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

}
