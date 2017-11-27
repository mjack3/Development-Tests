
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Auditor;
import domain.Curricula;
import domain.EducationRecord;
import domain.Speciality;
import domain.WorkRecord;
import repositories.CurriculaRepository;
import security.LoginService;

@Transactional
@Service
public class CurriculaService {

	@Autowired
	private CurriculaRepository	curriculaRepository;
	@Autowired
	private LoginService		loginService;
	@Autowired
	private AuditorService		auditorService;


	public CurriculaService() {
		super();
	}

	public Curricula create() {
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		final Curricula curricula = new Curricula();
		curricula.setName(new String());
		curricula.setName(auditor.getName() + " " + auditor.getSurname());
		curricula.setEducationsRecords(new ArrayList<EducationRecord>());
		curricula.setSpecialities(new ArrayList<Speciality>());
		curricula.setWorkRecords(new ArrayList<WorkRecord>());

		return curricula;

	}

	public boolean exists(final Integer id) {
		Assert.notNull(id);
		return this.curriculaRepository.exists(id);
	}

	public List<Curricula> findAll() {
		return this.curriculaRepository.findAll();
	}

	public Curricula save(final Curricula entity) {

		Curricula curricula = new Curricula();

		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (this.curriculaRepository.exists(entity.getId())) {
			curricula = this.curriculaRepository.findOne(entity.getId());
			Assert.isTrue(auditor.getCurricula().equals(curricula));
			curricula.setName(entity.getName());
			curricula.setEducationsRecords(entity.getEducationsRecords());
			curricula.setSpecialities(entity.getSpecialities());
			curricula.setWorkRecords(entity.getWorkRecords());
			return this.curriculaRepository.save(curricula);
		} else {
			entity.setName(auditor.getName() + " " + auditor.getSurname());
			entity.setEducationsRecords(new ArrayList<EducationRecord>());
			entity.setSpecialities(new ArrayList<Speciality>());
			entity.setWorkRecords(new ArrayList<WorkRecord>());
			this.curriculaRepository.save(entity);
			auditor.setCurricula(curricula);
			this.auditorService.update(auditor);
			return this.curriculaRepository.save(entity);
		}
	}

	public Curricula findOne(final Integer id) {
		Assert.notNull(id);
		return this.curriculaRepository.findOne(id);
	}

	public Curricula generate(final Curricula curricula) {
		final Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(auditor.getCurricula() == null);
		Curricula aux = new Curricula();
		aux = this.curriculaRepository.save(curricula);
		auditor.setCurricula(aux);
		this.auditorService.update(auditor);
		return aux;

	}

}
