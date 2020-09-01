package MainMenu;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import DaoImpl.AdminDaoImpl;
import DaoImpl.CustomerDaoImpl;
import beans.Admin;
import beans.Customer;


public class MainMenu {
	// Will contain most of the menu pages in this class... for BankAdmin and the Customers
	
	
	static Scanner scan = new Scanner(System.in);
	
	public static void startMenu() {
		
		System.out.println("Welcome to the Banking App");
		System.out.println("Before logging in, are you:");
		System.out.println("(1) Customer \n(2) Administrator \n(3) New Customer? Create an Account Here\n(0) To Exit Application");
		int decision = scan.nextInt();
		
		switch(decision) {
		case 0: // Exit Option
			System.out.println("Exiting Application\nSee you soon!");
			break;
		case 1: // Customer Option
			customerLogin();
			break;
		case 2: // Admin Option
			adminLogin();
			break;
		case 3: // Create an Account Here
			createCustomer();
			break;
		default:
			System.out.println("Please choose Customer or Administrator or exit app.");
			startMenu();
			break;
		}	
	}
	
	
	public static void createCustomer() {
		CustomerDaoImpl cusDaoImpl = new CustomerDaoImpl();
		System.out.println("Want to Create a new Account with the Bank?\nPlease Enter your First Name");
		String first = scan.next();
		System.out.println("Please enter your Last Name");
		String last = scan.next();
		System.out.println("Please enter a desired username");
		String username = scan.next();
		System.out.println("Now your password, and make it a good one!");
		String password = scan.next();
		System.out.println("For your first account, how much money do you want to deposit?");
		float account1 = scan.nextFloat();
		try {
			cusDaoImpl.createCustomer(last, first, username, password, account1);
			System.out.println("Account Created! \nWelcome! Please try logging in again with the username and password");
		} catch (SQLException e) {
			System.out.println("Could not create Account and add to database\nPlease try again later");
			System.out.println("Username already exists");
		} finally {
			startMenu();
		}
	}
	

	// Login Portions of the Menus for Customer and Bank Administrators
	
	public static void customerLogin() {
			System.out.println("Please Enter your Username");
			String user = scan.next();
			CustomerDaoImpl cusDaoImpl = new CustomerDaoImpl();	
			try {
				Customer c = cusDaoImpl.getCustomerUsingUserName(user);
				String actualpassword = c.getPassword();
				System.out.println("Enter your password\nIf you want to quit login write 'quit'");
				String password = scan.next();
				if(actualpassword.equals(password)) {
					customerScreen(c);
				} else if(password.equals("quit")) {
					startMenu();
				} else {
					System.out.println("Incorrect Password");
					customerLogin();
				}
			} catch (SQLException e) {
				System.out.println("User not found.");
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println("User does not exist.");
				e.printStackTrace();
			}
	}
	
	public static void adminLogin() {
		System.out.println("Please Enter your Username");
		String user = scan.next();
		AdminDaoImpl adminDaoImpl = new AdminDaoImpl();	
		try {
			Admin c = adminDaoImpl.getAdminfromUserName(user);
			String actualpassword = c.getPassword();
			System.out.println("Enter your password\nIf you want to quit login write 'quit'");
			String password = scan.next();
			if(actualpassword.equals(password)) {
				adminScreen(adminDaoImpl);
			} else if(password.equals("quit")) {
				startMenu();
			} else {
				System.out.println("Incorrect Password");
				adminLogin();
			}
		} catch (SQLException e) {
			System.out.println("User not found.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("User does not exist.");
			e.printStackTrace();
		}
	}
	
	// Screen (Dashboard) Portions of the Menus
	
	/*
	 * A user can view their own existing accounts and balances. [check] 
	 * A user can create an (banking) account. [check]
	 * A user can delete an account if it is empty.  [check]
	 * A user can add to or withdraw from an account. [check]
	 * A user can execute multiple deposits or withdrawals in a session. [check]
	 * A user can logout. [check]
	 * 
	 * 
	 */
	
	
	public static void customerScreen(Customer c) {
		CustomerDaoImpl custImp = new CustomerDaoImpl();
		System.out.println("Welcome to the Customer Screen");
		System.out.println("Here is your information");
		System.out.println(c.toString());
		System.out.println("What would you like to do today?");
		System.out.println("(1) Deposit\n(2) Withdraw\n(3) Apply for a New Account\n(4) Delete an account *Note account has to be empty first\n(0) To Log Out");
			int decision = scan.nextInt();
			switch(decision) {
			case 0: 
				System.out.println("Logging out\nExiting");
				startMenu();
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
				finally {
					customerScreen(c);
				}
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
				} finally {
					customerScreen(c);
				}
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
				} finally {
					customerScreen(c);
				}
				break;
			case 4:
				System.out.println("DELETE ACCOUNT SCREEN");
				try {
					custImp.deleteAccount(c,scan);
				} catch (SQLException e) {
					System.out.println("Requested action could not be completed.");
					e.printStackTrace();
				} finally {
					customerScreen(c);
				}
				break;
			default:
				System.out.println("Please Select one of the Options Please");
				customerScreen(c);
			}
	}
	
	// Admin Screen
	/*
	 * An Admin can view, create[check], update, and delete all users.
	 */
	public static void adminScreen(AdminDaoImpl c) {
		System.out.println("Welcome to the Bank Administration Screen");
		System.out.println("What would you like to do today?");
		System.out.println("(1) View a User's Information\n(2) View ALL Users\n(3) Create a new account for a User\n"
				+ "(4) Update a User's Account with the Company\n"
				+ "(5) Delete ALL Users *GASP*\n(0) To Log Out");
			int decision = scan.nextInt();
			switch(decision) {
			case 0: 
				System.out.println("Logging out\nExiting");
				startMenu();
				break;
			case 1:
				System.out.println("VIEW a User SCREEN");
				System.out.println("Enter username of Customer to view their information");
				String username = scan.next();
				try {
					Customer view = c.viewCustomerUsingUserName(username);
					System.out.println();
					System.out.println(view.toString());
					System.out.println();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					adminScreen(c);
				}
				break;
			case 2:
				System.out.println("VIEWING ALL Users");
				try {
					List<Customer> cList = c.viewAllCustomers();
					for(Customer cust : cList) {
						System.out.println(cust.toString());
						System.out.println();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					adminScreen(c);
				}
				break;
			
			case 3:
				System.out.println("CREATE a User ACCOUNT SCREEN");
				createCustomerScreen();
				adminScreen(c);
				break;
			case 4:
				System.out.println("UPDATE a User's BANKING INFORMATION SCREEN");
				System.out.println("Enter username of Customer to update their information");
				String user = scan.next();
				try {
					Customer update = c.viewCustomerUsingUserName(user);
					c.updateCustomer(update, scan);
					c.updateCustomerTransactions(update, scan);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				adminScreen(c);
				break;
			case 5:
				System.out.println("DELETE ALL USERS SCREEN");
				try {
					c.deleteCustomer(scan);
				} catch (SQLException e) {
					System.out.println("Something went wrong to delete user(s)");
					e.printStackTrace();
				} finally {
					adminScreen(c);
				}
				break;
			default:
				System.out.println("Please Select one of the Options Please");
				adminScreen(c);
			}
	}
	
	public static void createCustomerScreen() {
		AdminDaoImpl imp = new AdminDaoImpl();
		System.out.println("Please Enter User's First Name");
		String first = scan.next();
		System.out.println("Please enter User's Last Name");
		String last = scan.next();
		System.out.println("Please enter a desired username");
		String username = scan.next();
		System.out.println("Now for the User's password, and make it a good one!");
		String password = scan.next();
		System.out.println("For the User's first account, how much money do you want to deposit?");
		float account1 = scan.nextFloat();
		try {
			imp.createCustomer(last, first, username, password, account1);
			System.out.println("Account Created! \nWelcome! Please try logging in again with the username and password\nAfter the Bank Administrator logs out");
		} catch (SQLException e) {
			System.out.println("Could not create Account and add to database\nPlease try again later");
			e.printStackTrace();
		} finally {
			System.out.println("Going back to Admin Screen");
			System.out.println();
		}
	}
		
}
