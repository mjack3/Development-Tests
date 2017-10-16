
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Bill extends DomainEntity {

	public Bill() {
		super();
	}


	private String	ticket;
	private Money	money;
	private Date	moment;


	@Pattern(regexp = "#\\w{9}")
	public String getTicket() {
		return this.ticket;
	}

	public void setTicket(final String ticket) {
		this.ticket = ticket;
	}

	@NotNull
	public Money getMoney() {
		return this.money;
	}

	public void setMoney(final Money money) {
		this.money = money;
	}

	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	//	Relationships	--------

	private Raffle	raffle;
	private Payment	payment;


	@NotNull
	@ManyToOne(optional = false)
	public Raffle getRaffle() {
		return this.raffle;
	}

	public void setRaffle(final Raffle raffle) {
		this.raffle = raffle;
	}

	@OneToOne
	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(final Payment payment) {
		this.payment = payment;
	}

}
