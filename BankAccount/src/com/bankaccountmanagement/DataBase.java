package com.bankaccountmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {
	private Connection connect;
	public void setCustomerInfo(ArrayList<CustomerInfo> customer,int number) throws SQLException {
		setConnection();
		try(Statement state=connect.createStatement()){
		for(int i=0;i<number;i++){
				String sql="insert into Customer_Info (Name,Address, PhoneNumber) values('"
						+ ""+customer.get(i).getName()+"','"
						+ ""+customer.get(i).getAddress()+"',"+customer.get(i).getPhoneNumber()+")";
				state.addBatch(sql);
			}
		state.executeBatch();
		}

		DataBaseDriver.operations++;
	}
	public void setAccountInfo(ArrayList<AccountInfo> account,int number) throws SQLException {
		setConnection();
		try (Statement state = connect.createStatement()) {
		for(int i=0;i<number;i++) {
				String sql = "insert into Account_Info (AccountNumber, CustomerId, Balance) values("
						+ "" + account.get(i).getAccountNumber() + "," + account.get(i).getCustomerId() + ","
						+ "" + account.get(i).getBalance() + ")";
				state.addBatch(sql);
			}
		state.executeBatch();
		}
		DataBaseDriver.operations++;
	}
	public void setCustomerInfo() throws SQLException {
		setConnection();
		try(Statement state=connect.createStatement();
			ResultSet result=state.executeQuery("Select * from Customer_Info")){
			while(result.next()) {
				CustomerInfo customer=new CustomerInfo();
				customer.setName(result.getString("Name"));
				customer.setCustomerId(result.getInt("CustomerId"));
				customer.setAddress(result.getString("Address"));
				customer.setPhoneNumber(result.getLong("PhoneNumber"));
				AccountManagement.OBJECT.setUserDetails(customer);
			}
		}
		DataBaseDriver.operations=0;
	}
	public void setAccountInfo() throws SQLException {
		setConnection();
		try(Statement state=connect.createStatement();
			ResultSet result=state.executeQuery("Select * from Account_Info")){
			while(result.next()) {
				AccountInfo account=new AccountInfo();
				account.setAccountNumber(result.getLong("AccountNumber"));
				account.setCustomerId(result.getInt("CustomerId"));
				account.setBalance(result.getDouble("Balance"));
				HashMap<Integer, HashMap<Long, AccountInfo>>accountHashMap = AccountManagement.OBJECT.getAccountDetails();

				if(accountHashMap.get(account.getCustomerId())!=null) {
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
	public void setConnection() throws SQLException {
		if(connect==null) {
			connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdatabase","root","Root@123");
		}
	}
	public void closeConnection() throws SQLException {
		connect.close();
	}

}

