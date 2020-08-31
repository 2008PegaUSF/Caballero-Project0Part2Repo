package Dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import beans.Admin;
import beans.Customer;
import util.ConnFactory;

public interface AdminDao {
	
	public static ConnFactory cf= ConnFactory.getInstance();
	
	public Admin getAdminfromUserName(String username) throws SQLException;
	
	public void createCustomer(String LastName,String FirstName,String userName,String password, float Account1) throws SQLException;
	
	// View User's Account
	/*
	 * UserName
	 * Account Names and Balances
	 */
	public Customer viewCustomerUsingUserName(String username) throws SQLException;
	
	public List<Customer> viewAllCustomers() throws SQLException;
	
	// Updating a User's Account
	/*
	 * Deposit
	 * Withdraw
	 * Apply for new Account
	 * Delete an Account for User (NOT Delete all Users)
	 */
	public void updateCustomer(Customer c, Scanner scan) throws SQLException;
	
	// Delete ALL Accounts in the Bank
	public void deleteCustomer(Scanner in) throws SQLException;
}
