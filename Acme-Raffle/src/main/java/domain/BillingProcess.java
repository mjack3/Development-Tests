
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class BillingProcess extends DomainEntity {

	public BillingProcess() {
		super();
	}


	private Date	moment;
	private boolean	sw;


	@DateTimeFormat(pattern = "MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	public boolean isSw() {
		return this.sw;
	}

	public void setSw(final boolean sw) {
		this.sw = sw;
	}

}
