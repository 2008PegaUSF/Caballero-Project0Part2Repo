package DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Dao.AdminDao;
import beans.Admin;
import beans.Customer;
import util.ConnFactory;

public class AdminDaoImpl implements AdminDao {

	public static ConnFactory cf= ConnFactory.getInstance();
	
	public Admin getAdminfromUserName(String username) throws SQLException {
		Connection conn= cf.getConnection();
		String sql = "select * from \"BankAdmin\" where \"Username\"= ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		Admin c = null;
		while(rs.next()) {
			c = new Admin(rs.getString(5),rs.getString(4),rs.getString(2),rs.getString(3));
		}
		return c;
	}
	
	public void createCustomer(String LastName,String FirstName,String userName,String password, float Account1) throws SQLException {
		Connection conn = cf.getConnection();
		String sql = "insert into \"BankInformation\" (\"UserName\", \"UserPassword\", \"LastName\", \"FirstName\", \"Account1\")   values(?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userName);
		ps.setString(2,password);
		ps.setString(3,LastName);
		ps.setString(4, FirstName);
		ps.setFloat(5, Account1);
		ps.executeUpdate();
		}
	
	public Customer viewCustomerUsingUserName(String username) throws SQLException {
		Connection conn= cf.getConnection();
		String sql = "select * from \"BankInformation\" where \"UserName\"= ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		Customer c = null;
		while(rs.next()) {
			c = new Customer(rs.getString(5),rs.getString(4),rs.getString(2),rs.getString(3),rs.getFloat(6), rs.getFloat(7));
		}
		return c;
	}
	
	public List<Customer> viewAllCustomers() throws SQLException {
		List<Customer> custList=new ArrayList<Customer>();
		Connection conn= cf.getConnection();
		Statement stmt= conn.createStatement();
		ResultSet rs=stmt.executeQuery("select * from \"BankInformation\"");
		Customer a = null;
		while(rs.next()) {
			a = new Customer(rs.getString(5),rs.getString(4),rs.getString(2),rs.getString(3),rs.getFloat(6),rs.getFloat(7));
			custList.add(a);
		}
		return custList;
	}
	
	// Update Username, password, First and Last Names, do traditional customer transactions(will be implemented in the main menu class)
	public void updateCustomer(Customer c, Scanner scan) throws SQLException {
		Connection conn= cf.getConnection();
		System.out.println("What changes apart of your account balances do you want to change?"
				+ "\n(1) Change First Name\n(2) Change Last Name"
				+ "\n(3) Change Username \n(4) Change Password\n(0) To Exit for Customer Transactions");
		int decide = scan.nextInt();
		switch(decide) {
		case 0:
			break;
		case 1:
			System.out.println("What would you like to change your first name to?");
			String fname = scan.next();
			String username = c.getUserName();
			String sql = "update \"BankInformation\" set \"FirstName\"=? where \"UserName\" = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, fname);
			ps.setString(2, username);
			ps.executeUpdate();	
			c.setFirstName(fname);
			updateCustomer(c,scan);
			break;
		case 2: 
			System.out.println("What would you like to change your last name to?");
			String lname = scan.next();
			String usernamel = c.getUserName();
			String sql2 = "update \"BankInformation\" set \"LastName\"=? where \"UserName\" = ?";
			PreparedStatement psl = conn.prepareStatement(sql2);
			psl.setString(1, lname);
			psl.setString(2, usernamel);
			psl.executeUpdate();	
			c.setLastName(lname);
			updateCustomer(c,scan);
			break;
		case 3:
			System.out.println("What would you like to change your username to?");
			String Uname = scan.next();
			String usernameU = c.getUserName();
			String sql3 = "update \"BankInformation\" set \"UserName\"=? where \"UserName\" = ?";
			PreparedStatement psU = conn.prepareStatement(sql3);
			psU.setString(1, Uname);
			psU.setString(2, usernameU);
			psU.executeUpdate();	
			c.setUserName(Uname);
			updateCustomer(c,scan);
			break;
		case 4:
			System.out.println("What would you like to change your password to?");
			String pname = scan.next();
			String usernamep = c.getUserName();
			String sql4 = "update \"BankInformation\" set \"UserPassword\"=? where \"UserName\" = ?";
			PreparedStatement psp = conn.prepareStatement(sql4);
			psp.setString(1, pname);
			psp.setString(2, usernamep);
			psp.executeUpdate();	
			c.setPassword(pname);
			updateCustomer(c,scan);
			break;
		default:
			System.out.println("Please select which you'd like to change to");
			updateCustomer(c,scan);
			break;
		}
		
	}
	
	
	public void updateCustomerTransactions(Customer c, Scanner scan) {
		CustomerDaoImpl custImp = new CustomerDaoImpl();
		System.out.println("Now onto the portion of the User's Transactions");
		System.out.println();
		System.out.println("Here's the User's Information: \n"+ c.toString());
		System.out.println();
		System.out.println("(1) Deposit\n(2) Withdraw\n(3) Apply for a New Account\n(4) Delete an account *Note account has to be empty first\n(0) To Log Out");
		int decision = scan.nextInt();
		switch(decision) {
		case 0: 
			System.out.println("Logging out\nExiting");
			break;
		case 1:
			System.out.println("DEPOSIT SCREEN");
			System.out.println("How much would you like to deposit?");
			float deposit = scan.nextFloat();
			try {
				custImp.depositScreen(c,deposit,scan);
				System.out.println("Deposit of $" + deposit + " completed");
			} catch (SQLException e) {
				System.out.println("Deposit could not be completed.");
				e.printStackTrace();
			}
			updateCustomerTransactions(c,scan);
			break;
		case 2:
			System.out.println("WITHDRAW SCREEN");
			System.out.println("How much would you like the withdraw?");
			float withdraw = scan.nextFloat();
			try {
				custImp.withdrawScreen(c, withdraw,scan);
				System.out.println("Withdraw of $" + withdraw + "completed.");
			} catch (SQLException e) {
				System.out.println("Withdraw could not be completed.");
				e.printStackTrace();
			}
			updateCustomerTransactions(c,scan);
			break;
		case 3:
			System.out.println("APPLY ACCOUNT SCREEN");
			try {
				System.out.println("How much would you want to have in your new Account?");
				float account2 = scan.nextFloat();					
				custImp.applyAccount(c,account2);
				System.out.println("Account created");
			} catch (SQLException e1) {
				System.out.println("Could not complete application for opening new account.");
				e1.printStackTrace();
			}
			updateCustomerTransactions(c,scan);
			break;
		case 4:
			System.out.println("DELETE ACCOUNT SCREEN");
			try {
				custImp.deleteAccount(c,scan);
			} catch (SQLException e) {
				System.out.println("Requested action could not be completed.");
				e.printStackTrace();
			}
			updateCustomerTransactions(c,scan);
			break;
		default:
			System.out.println("Please Select one of the Options Please");
			updateCustomerTransactions(c,scan);
		}
	}
	

	public void deleteCustomer(Scanner scan) throws SQLException  {
		Connection conn= cf.getConnection();
		System.out.println("(1) Do you want to Delete a particular user? Or\n(2) Do you want to delete all users?\n(3) DO NOT DELETE ANYONE");
		int decide = scan.nextInt();
		switch(decide) {
		case 1:
			System.out.println("Please give the username of the user you'd like to delete");
			String userDelete = scan.next();
			String sql = "delete from \"BankInformation\" where \"UserName\" = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userDelete);
			ps.executeUpdate();	
			break;
		case 2:
			System.out.println("Are you SURE you want to delete all users? (Yes--> 1 / No--> 0)");
			int decision = scan.nextInt();
			if(decision == 1) {
				String sql2 = "truncate \"BankInformation\" cascade";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.executeUpdate();	
			} else {
				System.out.println("Okay, no users will be deleted.");
			}
			break;
		case 3:
			System.out.println("Admin changed their mind, will not delete any user");
			break;
		}

			
	}

}
