
package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	public Manager() {
		super();
		this.raffles = new HashSet<Raffle>();
	}


	//	Attributes	----------------

	private Boolean				isDebtor;

	//	Relationships ---------------------------

	private Collection<Raffle>	raffles;
	private CreditCard			creditCard;


	@OneToMany(mappedBy = "manager")
	public Collection<Raffle> getRaffles() {
		return this.raffles;
	}

	public void setRaffles(final Collection<Raffle> raffles) {
		this.raffles = raffles;
	}

	//	DerivatedAttributtes	------

	public Boolean getIsDebtor() {
		boolean sw = false;

		final Collection<Raffle> raffles = this.getRaffles();
		final Collection<Bill> bills = new HashSet<Bill>();

		for (final Raffle raffle : raffles)
			bills.addAll(raffle.getBills());

		int count = 0;

		for (final Bill bill : bills)
			if (bill.getPayment() == null) {
				count++;
				if (count == 3) {
					sw = true;
					break;
				}
			}

		return sw;
	}

	public void setIsDebtor(final Boolean isDebtor) {
		this.isDebtor = isDebtor;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
