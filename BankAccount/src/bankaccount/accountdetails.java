package bankaccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
						user_details.put(res.getInt(2),new Account(res.getString(1),res.getInt(2),res.getString(3),res.getInt(4)));
					}
					ResultSet res1=state.executeQuery("Select * from account");
					while(res1.next()) {
						if(user_details.containsKey(res1.getInt(2))) {
							user_details.get(res1.getInt(2)).addAccount(res1.getInt(1),res1.getInt(3));
						}
					}
					System.out.println("Enter the Customer id");
					int id=scan.nextInt();
					if(user_details.containsKey(id)) {
						System.out.println("1.Check Individual Account 2.All Accounts");
						int decision1=scan.nextInt();
						user_details.get(id).getAccount(decision1);
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
	int customerid;
	ArrayList<String>account_details=new ArrayList<String>();
	HashMap<Integer,Integer>accountdetails=new HashMap<Integer,Integer>();
	public Account(String name,int customerid,String address,int phonenumber) {
		this.name=name;
		this.address=address;
		this.phonenumber=phonenumber;
		this.customerid=customerid;
	}
	public void addAccount(int Accountnumber,int balance) {
		accountdetails.put(Accountnumber, balance);
	}
	public void getAccount(int decision1) {
		if(decision1==1) {
			Scanner scan=new Scanner(System.in);
			System.out.println("Enter the Account Number");
			int Accountnumber=scan.nextInt();
			if(accountdetails.size()==0) {
				System.out.println("There is No Account for this Customer id");
			}
			else if(accountdetails.containsKey(Accountnumber)) {
				System.out.println("Account Number\tCustomer id\tBalance");
				System.out.println(Accountnumber+"\t"+customerid+"\t"+accountdetails.get(Accountnumber));
			}
			else {
				System.out.println("Account Number Mismatched");
			}
		}
		else if(decision1==2) {
			if(accountdetails.size()==0) {
				System.out.println("There is No Account for this Customer id");
			}
			else {
				System.out.println("Account Number\tCustomer id\tBalance");
				for(Map.Entry<Integer,Integer>map:accountdetails.entrySet()) {
					System.out.println(map.getKey()+"\t"+customerid+"\t"+map.getValue());
				}
			}
		}
	}
}

