
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Payment extends DomainEntity {

	private CreditCard creditcard;


	public Payment() {
		super();
	}

	//	Attributes	--------

	@NotNull
	@OneToOne(optional = false)
	public CreditCard getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(CreditCard creditcard) {
		this.creditcard = creditcard;
	}

}
