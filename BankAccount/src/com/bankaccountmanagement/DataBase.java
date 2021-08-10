package com.bankaccountmanagement;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
	private Connection connect;
	public ArrayList<Integer> uploadCustomerInfo(ArrayList<ArrayList> customer)throws  SQLException{
			setConnection();
			int number = customer.size();
			ArrayList<Integer> customerIds = new ArrayList<>(number);
			String sql = "insert into Customer_Info (Name,Address, PhoneNumber) values(?,?,?)";
			PreparedStatement prepState = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet result=null;
			try {
				for (int i = 0; i < number; i++) {
					try {
						ArrayList totalCustomerInfo = customer.get(i);
						CustomerInfo tempCustomer = (CustomerInfo) totalCustomerInfo.get(0);
						prepState.setString(1, tempCustomer.getName());
						prepState.setString(2, tempCustomer.getAddress());
						prepState.setLong(3, tempCustomer.getPhoneNumber());
						prepState.executeUpdate();
						result = prepState.getGeneratedKeys();
						result.next();
						int id = result.getInt(1);
						customerIds.add(id);
					} catch (SQLException e) {
						customerIds.add(0);
					}

				}
			}
			finally {
				prepState.close();
				result.close();
			}
		return customerIds;
	}
	public void deleteCustomerInfo(int customerId)throws SQLException{
		setConnection();
		String sql="Delete from Customer_Info where CustomerId=?";

		try (PreparedStatement prepState =connect.prepareStatement(sql)){
			prepState.setInt(1,customerId);
			prepState.executeUpdate();
		}
	}
	public long uploadAccountInfo(AccountInfo account) throws SQLException {
		setConnection();
		String sql="insert into Account_Info (CustomerId, Balance) values(?,?)";
		long accountNumber;
		PreparedStatement prepState=connect.prepareStatement (sql,Statement.RETURN_GENERATED_KEYS);
		ResultSet result=null;
		try{
				prepState.setInt(1,account.getCustomerId() );
				prepState.setDouble(2,account.getBalance());
				prepState.executeUpdate();
				result=prepState.getGeneratedKeys();
				result.next();
				accountNumber=result.getInt(1);
		}
		catch (SQLException e){
			return 0;
		}
		finally {

			try{
				prepState.close();
				result.close();
			}
			catch (Exception e){

			}
		}
		return accountNumber;
	}
	public ArrayList<CustomerInfo> setCustomerInfo() throws SQLException {
		setConnection();
		ArrayList<CustomerInfo> customer=new ArrayList<>();
		try(PreparedStatement prepState=connect.prepareStatement("Select * from Customer_Info");
			ResultSet result=prepState.executeQuery()){
			while(result.next()) {
				String name=result.getString("Name");
				int id=result.getInt("CustomerId");
				String address=result.getString("Address");
				long phone=result.getLong("PhoneNumber");
				customer.add(Helper.getCustomerInfo(name, id, address, phone));
			}
		}
		return customer;
	}
	public ArrayList<AccountInfo> setAccountInfo() throws SQLException {
		setConnection();
		ArrayList<AccountInfo> account=new ArrayList<>();
		try(PreparedStatement prepState=connect.prepareStatement("Select * from Account_Info");
			ResultSet result=prepState.executeQuery()){
			while(result.next()) {
				long number=result.getLong("AccountNumber");
				int id=result.getInt("CustomerId");
				double balance=result.getDouble("Balance");
				account.add(Helper.getAccountInfo(number, id, balance));
			}
		}
		return account;
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

