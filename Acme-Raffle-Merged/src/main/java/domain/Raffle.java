
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Raffle extends DomainEntity {

	private String			logo;
	private String			title;
	private String			description;
	private Date			publicationTime;
	private Date			deadline;
	private List<Comment>	comments;


	public Raffle() {
		super();
		this.comments = new ArrayList<Comment>();
		this.publicationTime = new Date();
	}

	@NotNull
	@OneToMany(mappedBy = "raffle")
	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final List<Comment> comments) {
		this.comments = comments;
	}

	@NotBlank
	@URL
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(final String logo) {
		this.logo = logo;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPublicationTime() {
		return this.publicationTime;
	}

	public void setPublicationTime(final Date publicationTime) {
		this.publicationTime = publicationTime;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}


	//	Relationships -------------------

	private Collection<Prize>			prizes;
	private Collection<Participation>	participations;
	private Manager						manager;
	private Collection<Code>			codes;
	private Collection<Bill>			bills;


	@OneToMany(mappedBy = "raffle")
	public Collection<Prize> getPrizes() {
		return this.prizes;
	}

	public void setPrizes(final Collection<Prize> prizes) {
		this.prizes = prizes;
	}

	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@OneToMany(mappedBy = "raffle")
	public Collection<Participation> getParticipations() {
		return this.participations;
	}

	public void setParticipations(final Collection<Participation> participations) {
		this.participations = participations;
	}

	@OneToMany(mappedBy = "raffle", cascade = CascadeType.ALL)
	public Collection<Code> getCodes() {
		return this.codes;
	}

	public void setCodes(final Collection<Code> codes) {
		this.codes = codes;
	}
	
	@OneToMany(mappedBy = "raffle", cascade = CascadeType.ALL)
	public Collection<Bill> getBills() {
		return bills;
	}

	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}

}
