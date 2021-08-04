package com.bankaccountmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
class DataBase {
	private Connection connect;
	public void setCustomerInfo(CustomerInfo newcustomer) throws SQLException {
		try(Statement state=connect.createStatement()){
			String sql="insert into user_details (Name, Customer_id, Address, Phone_Number) values('"
					+ ""+newcustomer.getName()+"',"+newcustomer.getCustomerId()+",'"
					+ ""+newcustomer.getAddress()+"',"+newcustomer.getPhoneNumber()+")";
			state.executeQuery(sql);
		}
		DataBaseDriver.operations++;
	}
	public void setAccountInfo(AccountInfo newAccount) throws SQLException {
		try(Statement state=connect.createStatement()){
			String sql="insert into account (Account_Number, Customer_id, Balance) values("
					+ ""+newAccount.getAccountNumber()+","+newAccount.getCustomerId()+","
					+ ""+newAccount.getbalance()+")";
			state.executeQuery(sql);
		}
		DataBaseDriver.operations++;
	}
	public void setCustomerInfo() throws SQLException {
		try(Statement state=connect.createStatement();
			ResultSet result=state.executeQuery("Select * from user_details")){
			while(result.next()) {
				CustomerInfo customer=new CustomerInfo();
				customer.setName(result.getString("Name"));
				customer.setCustomerId(result.getInt("Customer_id"));
				customer.setAddress(result.getString("Address"));
				customer.setPhoneNumber(result.getLong("Phone_Number"));
				AccountManagement.getObject().setUserDetails(customer);
			}
		}
		DataBaseDriver.operations=0;
	}
	public void setAccountInfo() throws SQLException {
		try(Statement state=connect.createStatement();
			ResultSet result=state.executeQuery("Select * from account")){
			while(result.next()) {
				AccountInfo account=new AccountInfo();
				account.setAccountNumber(result.getLong("Account_Number"));
				account.setCustomerId(result.getInt("Customer_id"));
				account.setBalance(result.getFloat("Balance"));
				HashMap<Integer, HashMap<Long, AccountInfo>>accountHashMap=AccountManagement.getObject().getAccountDetails();
				if(accountHashMap!=null) {
					HashMap<Long,AccountInfo> accountDetails=accountHashMap.get(account.getCustomerId());
					accountDetails.put(account.getAccountNumber(),account);
				}
				else {
					HashMap<Long,AccountInfo>accountDetails=new HashMap<Long,AccountInfo>();
					accountDetails.put(account.getAccountNumber(),account);
					accountHashMap.put(account.getCustomerId(),accountDetails);
				}
			}
		}
	}
	public void getCustomerInfo(ArrayList customers,int number) throws SQLException {
		setConnection();
		for(int i=0;i<number;i++) {
			CustomerInfo newCustomer=new CustomerInfo();
			newCustomer.setName((String)customers.get(i*4));
			newCustomer.setAddress((String)customers.get(i*4+1));
			newCustomer.setCustomerId((int)customers.get(i*4+2));
			newCustomer.setPhoneNumber((long)customers.get(i*4+3));
			setCustomerInfo(newCustomer);
		}
	}
	public void getAccountInfo(ArrayList accounts,int number) throws SQLException {
		setConnection();
		for(int i=0;i<number;i++) {
			AccountInfo newAccount=new AccountInfo();
			newAccount.setAccountNumber((long)accounts.get(i*4));
			newAccount.setCustomerId((int)accounts.get(i*4+1));
			newAccount.setBalance((float)accounts.get(i*4+2));
			setAccountInfo(newAccount);
		}
	}
	public void setConnection() throws SQLException {
		if(connect==null) {
			connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdatabase","root","Azhagu3*7");
		}
	}
	public void closeconnection() throws SQLException {
		connect.close();
	}

}

