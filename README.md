# Project 0 Part 2: JDBC Banking App

## Description:
Welcome to the Banking App! Install the app into your IDLE and run the app through the console
If you are approved to the Banking Administrator (congratulations by the way) the login is

```bash
Login: admin
Password: password
```

## Functionality

**The User will be able to** 
1. Create a Banking Login if they do not have one
2. Make financial transactions like:
	- Deposit
	- Withdraw
3. Add a Secondary Banking Account 
4. Delete a Banking Account *as long as it's empty*
5. The ability to do all this for as long as they like **AFTER** logging in.
	- And logging out when they are done

**As Administrator, you will have**
1. Access User's information *with special care*
2. Update User's Account and Login Information
	- Change the User's
		- First and/or Last Name
		- Username
		- Password
	- Help User complete their financial transactions like if they logged in themselves
		- Deposit
		- Withdraw
		- Apply for a new Banking Account
		- Delete a Banking Account *as long as it's empty*
3. Create a new User login *if they need help creating one*
4. Delete User **and their information** Completely from the Banking App
5. Delete ALL Users **and their information** from the Banking App

# Structure of the Project
1. Driver.java
	- Will prompt the Banking App to Launch, all you need to do is run this file
2. MainMenu.java
	- Contains the methods that contain the User Interface the User and Administrator will see when using the Banking App
	- Includes Methods
		- StartMenu()
		- CreateCustomer() *specific to Customer creating their own account*
		- customerLogin()
		- customerScreen()
		- adminLogin()
		- adminScreen()
		- createCustomerScreen() *specific to Admin creating the Customer(User) login for themselves*
3. CustomerDaoImpl.java
	- Implements interface of CustomerDao.java
		- getCustomerUsingUserName()
		- createCustomer() 
		- depositScreen()
			- depositFunc() *to compute the mathematical component of the transactions*
		- withdrawScreen()
			- withdrawFunc() *to compute the mathematical component of the transactions*
		- applyAccount() *adds Seconding Banking Account*
		- deleteAccount() *deletes Banking Account NOT login account*
4. AdminDaoImpl.java
	- Implements interface of AdminDao.java
		- getAdminfromUserName()
		- createCustomer()
		- viewCustomerUsingUserName()
		- viewAllCustomers()
		- updateCustomer()
		- updateCustomerTransactions()
		- deleteCustomer()
5. Beans
	- Customer.java
		- Contains the essential information that a User needs to have in their account with the Banking
			- Name
			- Login
			- Password
			- Financial Account Information
	- Admin.java
		- Contains the essential information that the Administrator needs in order to log in as Admin
			- Name
			- Login
			- Password
6. ConnFactory.java
	- The .java file that allows the app to connect to the database that stores the information of the Users and the Adminstrator


Now go download this Repository and HAVE AT IT!

