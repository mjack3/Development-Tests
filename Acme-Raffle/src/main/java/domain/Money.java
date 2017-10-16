
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Access(AccessType.PROPERTY)
@Embeddable
public class Money {

	public Money() {
		super();
	}


	//	Attributes	-------------

	private Double	amount;
	private String	currency;


	@Min(0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	@Pattern(regexp = "$|€")
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

}
