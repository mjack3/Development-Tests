
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Auditor;
import domain.Speciality;
import repositories.SpecialityRepository;
import security.LoginService;

@Transactional
@Service
public class SpecialityService {

	@Autowired
	private SpecialityRepository	specialityRepository;

	@Autowired
	private LoginService			loginService;
	@Autowired
	private AuditorService			auditorService;


	public SpecialityService() {
		super();
	}

	public Speciality create() {
		Speciality speciality = new Speciality();
		speciality.setTitle(new String());
		speciality.setDescription(new String());

		return speciality;
	}

	public List<Speciality> findAll() {
		return specialityRepository.findAll();
	}

	public Speciality save(Speciality entity) {

		Speciality speciality = new Speciality();
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (specialityRepository.exists(entity.getId())) {
			speciality = specialityRepository.findOne(entity.getId());
			Assert.isTrue(auditor.getCurricula().getSpecialities().contains(speciality));
			speciality.setTitle(entity.getTitle());
			speciality.setDescription(entity.getDescription());

			return specialityRepository.save(speciality);

		} else {
			Speciality specialitynew = specialityRepository.save(entity);
			
			auditor.getCurricula().getSpecialities().add(specialitynew);
			auditorService.save(auditor);

			return specialitynew;

		}
	}
	
	public Speciality saveAF(Speciality entity) {

		Speciality speciality = new Speciality();
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (specialityRepository.exists(entity.getId())) {
			speciality = specialityRepository.findOne(entity.getId());
			Assert.isTrue(auditor.getCurricula().getSpecialities().contains(speciality));
			speciality.setTitle(entity.getTitle());
			speciality.setDescription(entity.getDescription());

			return specialityRepository.saveAndFlush(speciality);

		} else {
			Speciality specialitynew = specialityRepository.save(entity);
			
			auditor.getCurricula().getSpecialities().add(specialitynew);
			auditorService.save(auditor);

			return specialitynew;

		}
	}

	public Speciality findOne(Integer id) {
		Assert.notNull(id);
		Auditor auditor = (Auditor) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(auditor.getCurricula().getSpecialities().contains(specialityRepository.findOne(id)));

		return specialityRepository.findOne(id);
	}

}
