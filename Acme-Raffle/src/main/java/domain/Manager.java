
package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	private Boolean	isDebtor;


	public Manager() {
		super();
	}


	//	Relationships ---------------------------

	private Collection<Raffle>	raffles;
	private CreditCard			CreditCard;


	@OneToMany(mappedBy = "manager")
	public Collection<Raffle> getRaffles() {
		return this.raffles;
	}

	public void setRaffles(final Collection<Raffle> raffles) {
		this.raffles = raffles;
	}

	//	DerivatedAttributtes	------

	public Boolean getIsDebtor() {

		boolean resul = false;
		int count = 0;
		final HashSet<Bill> bills = new HashSet<>();

		for (final Raffle raffle : this.getRaffles())
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
		return this.CreditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.CreditCard = creditCard;
	}

}
