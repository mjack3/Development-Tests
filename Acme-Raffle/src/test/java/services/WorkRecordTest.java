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

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.WorkRecord;
@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class WorkRecordTest extends AbstractTest {

	@Autowired
	private WorkRecordService workRecordService;
	
	// Tests
		// ====================================================

	
	protected void createAndSaveWorkRecordTest(String username,String text, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2011);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date startDate = cal.getTime();
			Calendar cal1 = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2012);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date endDate = cal1.getTime();
			
			WorkRecord workRecord = new WorkRecord();
			workRecord.setDescription("description");
			workRecord.setEndDate(endDate);
			workRecord.setStartDate(startDate);
			workRecord.setTitle(text);
			workRecordService.saveAF(workRecord);
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
			createAndSaveWorkRecordTest((String) testingData[i][0], (String) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}

}
