
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Transactional
@Service
public class ConfigurationService {

	@Autowired
	ConfigurationRepository	repository;


	public ConfigurationService() {
		super();
	}

	//	Simple CRUDs mehots	----------

	public Configuration save(final Configuration configuration) {

		return this.repository.save(configuration);
	}

	public Configuration get() {
		return this.repository.findAll().get(0);
	}
}
