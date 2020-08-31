package Dao;

import java.sql.SQLException;
import java.util.Scanner;

import beans.Customer;

public interface CustomerDao {
	
	//Get Customer from DataBase
	public Customer getCustomerUsingUserName(String username) throws SQLException;
	
	// Create Customer Login and Account Information
	public void createCustomer(String LastName,String FirstName,String userName,String password, float Account1) throws SQLException;

	
	// Deposit Screen
	/* 
	 * For testing purposes the Deposit function should be separate
	 * SO 2 methods here
	 */
	public void depositScreen(Customer c, float deposit, Scanner in) throws SQLException;
	
	// Withdraw Screen
	/*
	 * Similar to Deposit 2 methods for testing purposes
	 */
	public void withdrawScreen(Customer c, float withdraw, Scanner in) throws SQLException;
	
	// Apply for a new Banking Account
	public void applyAccount(Customer c, float accountBalance) throws SQLException;
		
	// Delete one of the Banking Accounts 
	/*
	 * Has to have the condition that it is empty or the balance has to be zero!
	 */
	public void deleteAccount(Customer c, Scanner in) throws SQLException;
	
}
