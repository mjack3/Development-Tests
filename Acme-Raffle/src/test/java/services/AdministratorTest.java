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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Manager;
import domain.User;
@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class AdministratorTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private UserService userService;
	@Autowired
	private ManagerService managerService;
	
	// Tests
		// ====================================================

		
	
	protected void banUserTest(String username,int userId, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			User user= userService.findOne(userId);
			
			administratorService.bannedUser(user);
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}
	
	@Test
	public void driverBanUser() {

		Object testingData[][] = {
			// El Administrator logueado todo correcto. -> true
			{
				"admin",1325, null
			},
			// Estamos autenticados pero el user es incorrecto -> false
			{
				"admin", 1111111,IllegalArgumentException.class
			}, {
				// Si no estamos autentificados como admin -> false
				null, 1325, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			banUserTest((String) testingData[i][0], (int) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}
	
	

	protected void banManagerTest(String username,int managerId, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			Manager manager= managerService.findOne(managerId);
			
			administratorService.bannedManager(manager);
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}
	
	@Test
	public void driverBanManager() {

		Object testingData[][] = {
			// El Administrator logueado todo correcto. -> true
			{
				"admin",1329, null
			},
			// Estamos autenticados pero el user es incorrecto -> false
			{
				"admin", 1111111,IllegalArgumentException.class
			}, {
				// Si no estamos autentificados como admin -> false
				null, 1329, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			banManagerTest((String) testingData[i][0], (int) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}
	@Test
	public void listDebtors(){
		authenticate("admin");
		
		
		managerService.managerDebtor();
		unauthenticate();
	}
}
