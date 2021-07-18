package bankaccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
public class accountdetails {

	public static void main(String[] args) {
		HashMap <Integer,Account> user_details=new HashMap<Integer,Account>();
		try (Scanner scan=new Scanner(System.in)){
			while(true) {
				Connection connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdatabase","root","Azhagu3*7");
				Statement state=connect.createStatement();
				System.out.println("1.Create customer id 2.Create Account 3.Check Balance 4.Exit");
				int decision=scan.nextInt();
				if(decision==1) {
					String sql="insert into user_details (Name, Customer_id, Address, Phone_Number) values('";
					System.out.println("Enter the Name");
					sql+=scan.nextLine();
					System.out.println("Enter the Customer Id");
					sql+="',"+scan.nextInt();
					scan.nextLine();
					System.out.println("Enter the Address");
					sql+=",'"+scan.nextLine();
					System.out.println("Enter the Phone number");
					sql+="',"+scan.nextInt()+")";
					scan.nextLine();
					state.executeUpdate(sql);
				}
				else if(decision==2) {
					String sql="insert into account (Account_Number, Customer_id, Balance) values(";
					System.out.println("Enter the Account Number");
					sql+=scan.nextInt();
					System.out.println("Enter the Customer Id");
					sql+=","+scan.nextInt();
					System.out.println("Enter the Balance");
					sql+=","+scan.nextInt()+")";
					scan.nextLine();
					state.executeUpdate(sql);
				}
				else if(decision==3) {
					ResultSet res=state.executeQuery("Select * from user_details");
					while(res.next()) {
						user_details.put(res.getInt(2),new Account(res.getString(1),res.getString(3),res.getInt(4)));
					}
					ResultSet res1=state.executeQuery("Select * from account");
					while(res1.next()) {
						if(user_details.containsKey(res1.getInt(2))) {
							user_details.get(res1.getInt(2)).addAccount(res1.getInt(1)+"\t"+res1.getInt(2)+"\t"+res1.getInt(3));
						}
					}
					System.out.println("Enter the Customer id");
					int id=scan.nextInt();
					if(user_details.containsKey(id)) {
						user_details.get(id).getAccount();
					}
					else {
						System.out.println("Customer id Mismatched");
					}
				}
				else if(decision==4) {
					System.out.println("Thank you");
					break;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
class Account{
	String name,address;
	int phonenumber;
	ArrayList<String>account_details=new ArrayList<String>();
	public Account(String name,String address,int phonenumber) {
		this.name=name;
		this.address=address;
		this.phonenumber=phonenumber;
	}
	public void addAccount(String details) {
		account_details.add(details);
	}
	public void getAccount() {
		Iterator i=account_details.iterator();
		if(account_details.size()==0) {
			System.out.println("There is No Account for this Customer id");
		}
		else {
			System.out.println("Account Number\tCustomer id\tBalance");
			while(i.hasNext()) {
				System.out.println(i.next());
			}
		}
	}
}

