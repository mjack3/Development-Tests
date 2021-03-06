package forms;

import java.util.Collection;
import java.util.List;

import javax.persistence.ElementCollection;

import domain.Comment;
import domain.Prize;
import domain.Property;

public class PrizeForm extends Prize {

	private int      winners;
	private int      total;
	private int      raffleId;
	private int      prizeId;
	private Collection<Property> allProperties;
	private List<Comment>   comments;
	private Collection<Property> propertiesSelected;


	@Override
	public List<Comment> getComments() {
		return comments;
	}

	@Override
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getRaffleId() {
		return raffleId;
	}

	public void setRaffleId(int raffleId) {
		this.raffleId = raffleId;
	}

	public int getWinners() {
		return winners;
	}

	public void setWinners(int winners) {
		this.winners = winners;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(int prizeId) {
		this.prizeId = prizeId;
	}

	public Collection<Property> getAllProperties() {
		return allProperties;
	}

	public void setAllProperties(Collection<Property> allProperties) {
		this.allProperties = allProperties;
	}

	@ElementCollection
	public Collection<Property> getPropertiesSelected() {
		return propertiesSelected;
	}

	public void setPropertiesSelected(Collection<Property> propertiesSelected) {
		this.propertiesSelected = propertiesSelected;
	}

}