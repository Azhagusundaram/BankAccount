package com.bankaccountmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {
	private Connection connect;
	public void uploadCustomerInfo(ArrayList<ArrayList> customer, int number) throws SQLException {
		setConnection();
		ResultSet result=null;
		String sql="insert into Customer_Info (Name,Address, PhoneNumber) values(?,?,?)";
		try(PreparedStatement prepState =connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
		for(int i=0;i<number;i++){
				CustomerInfo tempCustomer=(CustomerInfo) customer.get(i).get(0);
				AccountInfo tempAccount=(AccountInfo) customer.get(i).get(1);
				prepState.setString(1,tempCustomer.getName());
				prepState.setString(2,tempCustomer.getAddress());
				prepState.setLong(3,tempCustomer.getPhoneNumber());
				prepState.executeUpdate();
				result= prepState.getGeneratedKeys();
				result.next();
				int id=result.getInt(1);
				Helper.setOutput("\nCustomer Id is created. Customer Id for "+ tempCustomer.getName()+" is : "+id);
				tempAccount.setCustomerId(id);
				uploadAccountInfo(tempAccount);
			}
			result.close();
			DataBaseDriver.operations=true;
		}
	}
	public void uploadAccountInfo(AccountInfo account) throws SQLException {
		setConnection();
		String sql="insert into Account_Info (CustomerId, Balance) values(?,?)";
		try (PreparedStatement prepState=connect.prepareStatement (sql,Statement.RETURN_GENERATED_KEYS)) {
				prepState.setInt(1,account.getCustomerId() );
				prepState.setDouble(2,account.getBalance());
				prepState.executeUpdate();
				ResultSet result=prepState.getGeneratedKeys();
				result.next();
				Helper.setOutput("\nAccount Created.Account Number for "+account.getCustomerId() +
						" is : "+result.getInt(1));
		}
		DataBaseDriver.operations=true;
	}
	public void setCustomerInfo() throws SQLException {
		setConnection();
		PreparedStatement prepState=connect.prepareStatement("Select * from Customer_Info where CustomerId>?");
		prepState.setInt(1,Helper.lastCustomerId);
		try(ResultSet result=prepState.executeQuery()){
			while(result.next()) {
				String name=result.getString("Name");
				int id=result.getInt("CustomerId");
				String address=result.getString("Address");
				long phone=result.getLong("PhoneNumber");
				CustomerInfo customer = Helper.getCustomerInfo(name, id, address, phone);
				Helper.lastCustomerId = customer.getCustomerId();
				AccountManagement.OBJECT.setUserDetails(customer);
			}
		}
		prepState.close();
		DataBaseDriver.operations=false;
	}
	public void setAccountInfo() throws SQLException {
		setConnection();
		PreparedStatement prepState=connect.prepareStatement("Select * from Account_Info where AccountNumber>?");
		prepState.setLong(1,Helper.lastaccount);
		try(ResultSet result=prepState.executeQuery()){
			while(result.next()) {
				long number=result.getLong("AccountNumber");
				int id=result.getInt("CustomerId");
				double balance=result.getDouble("Balance");
				AccountInfo account = Helper.getAccountInfo(number, id, balance);
				Helper.lastaccount =account.getAccountNumber();
				HashMap<Integer, HashMap<Long, AccountInfo>>accountHashMap;
				accountHashMap = AccountManagement.OBJECT.getAccountDetails();

				if(accountHashMap.get(account.getCustomerId())!=null) {
					HashMap<Long,AccountInfo> accountDetails=accountHashMap.get(account.getCustomerId());
					accountDetails.put(account.getAccountNumber(),account);
				}
				else {
					HashMap<Long,AccountInfo>accountDetails=new HashMap<>();
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
	public void setDataBase(boolean operations) throws SQLException {
		if(operations==true){
			setCustomerInfo();
			setAccountInfo();
		}
	}

}

