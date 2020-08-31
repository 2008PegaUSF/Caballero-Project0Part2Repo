package DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Dao.CustomerDao;
import beans.Customer;
import util.ConnFactory;

public class CustomerDaoImpl implements CustomerDao {

	public static ConnFactory cf= ConnFactory.getInstance();
	
	public Customer getCustomerUsingUserName(String username) throws SQLException {
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

	public void depositScreen(Customer c, float deposit, Scanner in) throws SQLException {
		Connection conn = cf.getConnection();
		String user = c.getUserName();
		float checkAccount2 = c.getAccount2();
		if(checkAccount2 > 0.0) {
			System.out.println("From which account do you want to deposit to?"
					+ "\n(1) Account 1\n(2) Account 2");
			int decision = in.nextInt();
			switch(decision) {
			case 1: 
				float ogBalance = c.getAccount1();
				float newBalance = depositFunc(ogBalance, deposit);
				c.setAccount1(newBalance);
				String sql = "update \"BankInformation\" set \"Account1\"=? where \"UserName\" = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setFloat(1, newBalance);
				ps.setString(2, user);
				ps.executeUpdate();
				break;
			case 2:
				float ogBalance2 = c.getAccount2();
				float newBalance2 = depositFunc(ogBalance2, deposit);
				c.setAccount2(newBalance2);
				String sql2 = "update \"BankInformation\" set \"Account2\"=? where \"UserName\" = ?";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setFloat(1, newBalance2);
				ps2.setString(2, user);
				ps2.executeUpdate();
				break;
			default:
				System.out.println("Please choose which account to deposit into.");
				depositScreen(c,deposit,in);
				break;
			}
		} else {
			float ogBalance = c.getAccount1();
			float newBalance = depositFunc(ogBalance, deposit);
			c.setAccount1(newBalance);
			String sql = "update \"BankInformation\" set \"Account1\"=? where \"UserName\" = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, newBalance);
			ps.setString(2, user);
			ps.executeUpdate();
		}
	}
	
	public float depositFunc(float balance, float deposit) {
		if( deposit < 0) { // if deposit is less than zero, will just return original balance
			return balance;
		}
		float newBalance = deposit + balance;
		return newBalance;
	}

	public void withdrawScreen(Customer c, float withdraw, Scanner in) throws SQLException {
		Connection conn = cf.getConnection();
		String user = c.getUserName();
		float checkAccount2 = c.getAccount2();
		if(checkAccount2 > 0.0) {
			System.out.println("From which account do you want to withdraw from?"
					+ "\n(1) Account 1\n(2) Account 2");
			int decision = in.nextInt();
			switch(decision) {
			case 1: 
				float ogBalance = c.getAccount1();
				float newBalance = withdrawFunc(ogBalance, withdraw);
				c.setAccount1(newBalance);
				String sql = "update \"BankInformation\" set \"Account1\"=? where \"UserName\" = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setFloat(1, newBalance);
				ps.setString(2, user);
				ps.executeUpdate();
				break;
			case 2:
				float ogBalance2 = c.getAccount2();
				float newBalance2 = withdrawFunc(ogBalance2, withdraw);
				c.setAccount2(newBalance2);
				String sql2 = "update \"BankInformation\" set \"Account2\"=? where \"UserName\" = ?";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setFloat(1, newBalance2);
				ps2.setString(2, user);
				ps2.executeUpdate();
				break;
			default:
				System.out.println("Please choose which account to withdraw from.");
				withdrawScreen(c,withdraw,in);
				break;
			}
		} else {
			float ogBalance = c.getAccount1();
			float newBalance = withdrawFunc(ogBalance, withdraw);
			c.setAccount1(newBalance);
			String sql = "update \"BankInformation\" set \"Account1\"=? where \"UserName\" = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, newBalance);
			ps.setString(2, user);
			ps.executeUpdate();
		}
	}
	
	public float withdrawFunc(float balance, float withdraw) {
		if(withdraw > balance) {
			System.out.println("Overwithdraw, action will not be completed");
			return balance;
		}
		float newBalance = balance - withdraw;
		return newBalance;
	}

	public void applyAccount(Customer c, float accountBalance) throws SQLException {
		Connection conn = cf.getConnection();
		String user = c.getUserName();
		float balance = c.getAccount2();
		float balanceAccount1Check = c.getAccount1();
		if( balance == 0.0) { // when account 1 was deleted and wants to reopen again
			String sql = "update \"BankInformation\" set \"Account2\"=? where \"UserName\" = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, balance);
			ps.setString(2, user);
			ps.executeUpdate();		
			c.setAccount2(accountBalance);
		} else if (balance > 0.0 && balanceAccount1Check > 0.0) {
			System.out.println("Cannot open more than 2 Accounts");
		}
	}

	public void deleteAccount(Customer c, Scanner in) throws SQLException {
		Connection conn = cf.getConnection();
		String user = c.getUserName();
		float balance = c.getAccount1();
		float balanceCheck = c.getAccount2();
		
		if(balance > 0.0 && balanceCheck > 0.0) { // When both accounts are opened, moves money to other account to delete itself
			System.out.println("Which account would you want to delete?\n*Note: Only Accounts that are "
				+ "EMPTY can be deleted"
				+ "\nThis action can be done and will be automatically moved to the other account."
				+ "\n(1) Account 1: $" + c.getAccount1()+ "\n(2) Account 2: $" + c.getAccount2() 
				+ "\n(3)Actually I don't want to delete anything");
			int decide = in.nextInt();
			switch(decide) {
			case 1: 
				// adds funds into other account, then deletes account by making it null, moves Account2 up to space Account1
				String sql = "update \"BankInformation\" set \"Account1\"=? where \"UserName\" = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				float newbalance = balance + balanceCheck;
				ps.setFloat(1, newbalance);
				ps.setString(2, user);
				ps.executeUpdate();	
				
				String sql2 = "update \"BankInformation\" set \"Account2\"=null where \"UserName\" = ?";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, user);
				ps2.executeUpdate();	
				c.setAccount1(newbalance);
				c.setAccount2(0);
				System.out.println("Requested action completed.\nAccount 2 moved to Account 1 as primary account");
				break;
			case 2: // Deletes account2 and moves funds to account1
				String sql3 = "update \"BankInformation\" set \"Account1\"=? where \"UserName\" = ?";
				PreparedStatement ps3 = conn.prepareStatement(sql3);
				float newbalance3 = balance + balanceCheck;
				ps3.setFloat(1, newbalance3);
				ps3.setString(2, user);
				ps3.executeUpdate();	
				
				String sql4 = "update \"BankInformation\" set \"Account2\"=null where \"UserName\" = ?";
				PreparedStatement ps4 = conn.prepareStatement(sql4);
				ps4.setString(1, user);
				ps4.executeUpdate();
				c.setAccount1(newbalance3);
				c.setAccount2(0);
				System.out.println("Requested Action completed.\nAccount 2 deleted");
				break;
			case 3:
				System.out.println("Exiting Deleting Screen");
				break;
			default: 
				System.out.println("Select one of the accounts to delete.");
				break;
			}
		} else if(balance > 0.0 && balanceCheck == 0.0) { // when it's just account1 open
				System.out.println("Cannot delete account.\nYou must withdraw the balance to your account first.");
		}
		
	}

}
