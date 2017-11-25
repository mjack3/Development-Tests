
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	public Manager() {
		super();
	}

	private Boolean isDebtor;
	private CreditCard   creditCard;

	//	Relationships ---------------------------

	private Collection<Raffle>	raffles;


	@OneToMany(mappedBy = "manager")
	public Collection<Raffle> getRaffles() {
		return this.raffles;
	}

	public void setRaffles(final Collection<Raffle> raffles) {
		this.raffles = raffles;
	}

	public Boolean getIsDebtor() {

		boolean resul = false;
		int count = 0;
		final HashSet<Bill> bills = new HashSet<>();

		Collection<Raffle> raffles = this.getRaffles()==null ? new ArrayList<Raffle>():this.getRaffles();
		for (final Raffle raffle : raffles)
			if (raffle.getBills() != null)
				bills.addAll(raffle.getBills());

		for (final Bill bill : bills)
			if (bill.getPayed() == null || bill.getCreditCard() == null) {
				count++;
				if (count >= 3) {
					resul = true;
					break;
				}
			}

		resul = count >= 3;

		return resul;
	}

	public void setIsDebtor(final Boolean isDebtor) {
		this.isDebtor = isDebtor;
	}

	@OneToOne
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
