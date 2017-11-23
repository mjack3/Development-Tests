
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	private String name;


	public Curricula() {
		super();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//RelationShips

	private Collection<EducationRecord>	educationsRecords;
	private Collection<WorkRecord>		workRecords;
	private Collection<Speciality>		specialities;


	@NotNull
	@OneToMany
	public Collection<EducationRecord> getEducationsRecords() {
		return educationsRecords;
	}

	public void setEducationsRecords(Collection<EducationRecord> educationsRecords) {
		this.educationsRecords = educationsRecords;
	}

	@NotNull
	@OneToMany
	public Collection<WorkRecord> getWorkRecords() {
		return workRecords;
	}

	public void setWorkRecords(Collection<WorkRecord> workRecords) {
		this.workRecords = workRecords;
	}

	@NotNull
	@OneToMany
	public Collection<Speciality> getSpecialities() {
		return specialities;
	}

	public void setSpecialities(Collection<Speciality> specialities) {
		this.specialities = specialities;
	}

}
