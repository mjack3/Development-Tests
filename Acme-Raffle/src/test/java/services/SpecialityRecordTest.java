/*
 * AbstractTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Speciality;
@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpecialityRecordTest extends AbstractTest {

	@Autowired
	private SpecialityService specialityService;
	
	// Tests
		// ====================================================

	
	protected void createAndSaveSpecialityTest(String username,String text, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			
			Speciality speciality = new Speciality();
			speciality.setDescription("description");
			speciality.setTitle(text);
			specialityService.saveAF(speciality);
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}
	
	@Test
	public void driverCreateAndSaveWorkRecordTest() {

		Object testingData[][] = {
			
			{
				"auditor1","descripcion", null
			},
			{
				"auditor1", null,ConstraintViolationException.class
			}, {
				null, "descripcion", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			createAndSaveSpecialityTest((String) testingData[i][0], (String) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}

}
