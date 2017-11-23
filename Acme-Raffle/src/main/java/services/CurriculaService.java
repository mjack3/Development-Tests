
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
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Curricula curricula = new Curricula();
		curricula.setName(new String());
		curricula.setName(auditor.getName() + " " + auditor.getSurname());
		curricula.setEducationsRecords(new ArrayList<EducationRecord>());
		curricula.setSpecialities(new ArrayList<Speciality>());
		curricula.setWorkRecords(new ArrayList<WorkRecord>());

		return curricula;

	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return curriculaRepository.exists(id);
	}

	public List<Curricula> findAll() {
		return curriculaRepository.findAll();
	}

	public Curricula save(Curricula entity) {

		Curricula curricula = new Curricula();

		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (curriculaRepository.exists(entity.getId())) {
			curricula = curriculaRepository.findOne(entity.getId());
			curricula.setName(entity.getName());
			curricula.setEducationsRecords(entity.getEducationsRecords());
			curricula.setSpecialities(entity.getSpecialities());
			curricula.setWorkRecords(entity.getWorkRecords());
			return curriculaRepository.save(curricula);
		} else {
			entity.setName(auditor.getName() + " " + auditor.getSurname());
			entity.setEducationsRecords(new ArrayList<EducationRecord>());
			entity.setSpecialities(new ArrayList<Speciality>());
			entity.setWorkRecords(new ArrayList<WorkRecord>());
			curriculaRepository.save(entity);
			auditor.setCurricula(curricula);
			auditorService.update(auditor);
			return curriculaRepository.save(entity);
		}
	}

	public Curricula findOne(Integer id) {
		Assert.notNull(id);
		return curriculaRepository.findOne(id);
	}

	public Curricula generate(Curricula curricula) {
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(auditor.getCurricula() == null);
		Curricula aux = new Curricula();
		aux = curriculaRepository.save(curricula);
		auditor.setCurricula(aux);
		auditorService.update(auditor);
		return aux;

	}

}
