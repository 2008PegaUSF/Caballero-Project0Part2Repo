package beans;

public class Customer {
	
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private float account1;
	private float account2;
	
	public Customer() {
		super();
	}
	public Customer(String FirstName, String LastName, String Username, String Password,float account1, float account2) {
		this.firstName = FirstName;
		this.lastName = LastName;
		this.userName = Username;
		this.password = Password;
		this.account1 = account1;
		this.account2 = account2;
	}
	public Customer(String FirstName, String LastName, String Username, String Password,float account1) {
		this.firstName = FirstName;
		this.lastName = LastName;
		this.userName = Username;
		this.password = Password;
		this.account1 = account1;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public float getAccount1() {
		return account1;
	}
	public void setAccount1(float account1) {
		this.account1 = account1;
	}
	public float getAccount2() {
		return account2;
	}
	public void setAccount2(float account2) {
		this.account2 = account2;
	}
	@Override
	public String toString() {
		if(getAccount2() == 0) {
		return "Customer Information\n" + "Name: " + firstName + " " + lastName + "\n"
				+ "Username: " + userName + "\nAccount 1 Balance: $" + account1;
		}
		return "Customer Information\n" + "Name: " + firstName + " " + lastName + "\n"
		+ "Username: " + userName + "\nAccount 1 Balance: $" + account1 + "\nAccount 2 Balance: $" + account2;
	}
	
}
