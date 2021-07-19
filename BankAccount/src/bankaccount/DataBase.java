package bankaccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class DataBase extends AccountDetails{
	static Scanner scan=new Scanner(System.in);
	public static void setCustomerInfo() throws SQLException {
		Statement state=setConnection();;
		int i=1;
		while(i==1) {
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
		System.out.println("1.Continue entry 2.exit");
		i=scan.nextInt();
		scan.nextLine();
		state.addBatch(sql);
		}
		state.executeBatch();
	}
	public static void setAccountInfo() throws SQLException {
		Statement state=setConnection();
		int i=1;
		while(i==1) {
			String sql="insert into account (Account_Number, Customer_id, Balance) values(";
			System.out.println("Enter the Account Number");
			sql+=scan.nextInt();
			System.out.println("Enter the Customer Id");
			sql+=","+scan.nextInt();
			System.out.println("Enter the Balance");
			sql+=","+scan.nextInt()+")";
			System.out.println("1.Continue entry 2.exit");
			i=scan.nextInt();
			scan.nextLine();
			state.addBatch(sql);
		}
		state.executeBatch();
	}
	public static void getCustomerInfo() throws SQLException {
		Statement state=setConnection();
		ResultSet result=state.executeQuery("Select * from user_details");
		while(result.next()) {
			user_details.put(result.getInt("Customer_id"),new CustomerInfo(result.getString("Name"),result.getInt("Customer_id"),result.getString("Address"),result.getInt("Phone_Number")));
		}
	}
	public static void getAccountInfo() throws SQLException {
		Statement state=setConnection();
		ResultSet result=state.executeQuery("Select * from account");
		while(result.next()) {
			account_details.put(result.getInt("Account_Number"), new AccountInfo(result.getInt("Account_Number"),result.getInt("Customer_id"),result.getInt("Balance")));
			connect_details.put(result.getInt("Account_Number"), result.getInt("Customer_id"));
		}
	}
	public static Statement setConnection() throws SQLException {
		Connection connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdatabase","root","Azhagu3*7");
		Statement state=connect.createStatement();
		return state;
	}
}

