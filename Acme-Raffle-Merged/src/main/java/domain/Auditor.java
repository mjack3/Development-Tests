
package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	public Auditor() {
		super();
		this.reports = new HashSet<AuditReport>();
	}


	private Collection<AuditReport>	reports;
	private Curricula			curricula;


	@NotNull
	@OneToMany(mappedBy = "auditor")
	public Collection<AuditReport> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<AuditReport> reports) {
		this.reports = reports;
	}
	
	@OneToOne
	public Curricula getCurricula() {
		return curricula;
	}

	public void setCurricula(Curricula curricula) {
		this.curricula = curricula;
	}

}
