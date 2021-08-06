package com.bankaccountmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {
	private Connection connect;
	public String uploadCustomerInfo(ArrayList<ArrayList> customer, int number) throws SQLException {
		setConnection();
		String outputString ="";
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
				outputString +="\nCustomer Id is created. Customer Id for "+ tempCustomer.getName()+" is : "+id;
				tempAccount.setCustomerId(id);
				outputString += uploadAccountInfo(tempAccount);
			}
			result.close();
			DataBaseDriver.operations=true;
		}
		return outputString;
	}
	public String uploadAccountInfo(AccountInfo account) throws SQLException {
		setConnection();
		String outputString ="";
		String sql="insert into Account_Info (CustomerId, Balance) values(?,?)";
		try (PreparedStatement prepState=connect.prepareStatement (sql,Statement.RETURN_GENERATED_KEYS)) {
				prepState.setInt(1,account.getCustomerId() );
				prepState.setDouble(2,account.getBalance());
				prepState.executeUpdate();
				ResultSet result=prepState.getGeneratedKeys();
				result.next();
				outputString += "\nAccount Created.Account Number for "+account.getCustomerId() +
						" is : "+result.getInt(1);
		}
		DataBaseDriver.operations=true;
		return outputString;
	}
	public void setCustomerInfo() throws SQLException {
		setConnection();
		PreparedStatement prepState=connect.prepareStatement("Select * from Customer_Info where ?>=?");
		prepState.setInt(1,Helper.tableLastCustomerId);
		prepState.setInt(2,Helper.HashMapLastCustomerId);
		try(Statement state=connect.createStatement();
			ResultSet result=state.executeQuery("Select * from Customer_Info")){
			while(result.next()) {
				String name=result.getString("Name");
				int id=result.getInt("CustomerId");
				String address=result.getString("Address");
				long phone=result.getLong("PhoneNumber");
				CustomerInfo customer = Helper.getCustomerInfo(name, id, address, phone);
				Helper.HashMapLastCustomerId = customer.getCustomerId();
				AccountManagement.OBJECT.setUserDetails(customer);
			}
		}
		DataBaseDriver.operations=false;
	}



	public void setAccountInfo() throws SQLException {
		setConnection();
		try(Statement state=connect.createStatement();
			ResultSet result=state.executeQuery("Select * from Account_Info")){
			while(result.next()) {
				long number=result.getLong("AccountNumber");
				int id=result.getInt("CustomerId");
				double balance=result.getDouble("Balance");
				AccountInfo account = Helper.getAccountInfo(number, id, balance);
				Helper.HashMapLastaccount =account.getAccountNumber();
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

