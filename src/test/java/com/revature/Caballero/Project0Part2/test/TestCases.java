
package com.revature.Caballero.Project0Part2.test;

import DaoImpl.AdminDaoImpl;
import DaoImpl.CustomerDaoImpl;
import MainMenu.MainMenu;
import beans.Customer;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestCases {
	
	// Temporary Objects for the purpose of Testing
	CustomerDaoImpl tempCus = new CustomerDaoImpl();	
	AdminDaoImpl tempAdmin = new AdminDaoImpl();
	
	
	/*
	 * Checking Deposit Function
	 */
	
	// When Deposit Runs Normally
	@Test
	void testDeposit() {
		CustomerDaoImpl temp = new CustomerDaoImpl();
		Assertions.assertEquals(100,temp.depositFunc(50,50));
	}

	// When Deposit is a negative number
	@Test
	void testNegDeposit() {
		CustomerDaoImpl temp = new CustomerDaoImpl();
		Assertions.assertEquals(100,temp.depositFunc(100, -10));
	}
	
	/*
	 * Checking Withdraw Function
	 */
	
	// WithDraw Function running normally
	@Test
	void testWithdraw() {
		CustomerDaoImpl temp = new CustomerDaoImpl();
		Assertions.assertEquals(100,temp.withdrawFunc(150,50));
	}
	
	// WithDraw Function when Overwithdraw happens
	@Test
	void testWithdrawOver() {
		CustomerDaoImpl temp = new CustomerDaoImpl();
		Assertions.assertEquals(100,temp.withdrawFunc(100,150));
	}
	
	/*
	 * Get Customer Information through UserName
	 * test will check by verifying the amount in Primary Account
	 * This means that connection to database is working
	 */
	@Test
	void tempCusCheck() throws SQLException {
		 AdminDaoImpl temp = new AdminDaoImpl();
		 Customer tempcus = temp.viewCustomerUsingUserName("caballeroaldo");
		 Assertions.assertEquals(733,tempcus.getAccount1());
		 
	}
	
	/*
	 * Testing Applying for a Second Account
	 * 		Testing for Creating a Customer
	 * Intended to check if apply account ran, but it also 
	 * unintentionally tested to see if Create Customer 
	 * also worked
	 */
	@Test
	void tempApplyAccount() throws SQLException {
		tempCus.createCustomer("Test", "Dummy", "testdummy", "password", 290);	
		Customer getTempCus = tempAdmin.viewCustomerUsingUserName("testdummy");
		tempCus.applyAccount(getTempCus, 100);
		Assertions.assertEquals(100,getTempCus.getAccount2());
	}

	/*
	 * Testing Deletions of Customers from Database
	 * 
	 */
	@Test
	void testDeleteAccount() throws SQLException {
		Customer getTempCus = tempAdmin.viewCustomerUsingUserName("testdummy");
		Assertions.assertEquals(1, tempAdmin.testDeleteIndividualCustomer(getTempCus));
	}
	
}
