package com.bankaccountmanagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBaseDriver {
	static int operations=0;
	public static void main(String[] args) throws SQLException {
		Scanner scan=new Scanner(System.in);
		DataBase dataBase=new DataBase();
		BankBalance balance=new BankBalance();
		while(true) {
			System.out.println("1.Create customer id \n2.Create Account \n3.Check Balance \n4.Exit");
			int decision=scan.nextInt();
			if(decision==1) {
				ArrayList<CustomerInfo> customers =new ArrayList<CustomerInfo>();
				ArrayList<AccountInfo> accounts =new ArrayList<AccountInfo>();
				System.out.print("Enter the number of customers : ");
				int number =scan.nextInt();
				scan.nextLine();
				for(int i=0;i<number;i++) {
					CustomerInfo customer =new CustomerInfo();
					AccountInfo account =new AccountInfo();
					System.out.print("Enter the Name : ");
					customer.setName(scan.nextLine());
					System.out.print("Enter the Address : ");
					customer.setAddress(scan.nextLine());
					System.out.print("Enter the Phone number : ");
					customer.setPhoneNumber(scan.nextLong());
					System.out.print("Enter the Account Number : ");
					account.setAccountNumber(scan.nextLong());
					System.out.print("Enter the Balance : ");
					account.setBalance(scan.nextDouble());
					scan.nextLine();
					customers.add(customer);
					accounts.add(account);
				}
				dataBase.setCustomerInfo(customers,number);
				dataBase.setAccountInfo(accounts,number);
			}
			else if(decision==2) {
				ArrayList<AccountInfo> accounts =new ArrayList<AccountInfo>();
				System.out.print("Enter the number of Accounts : ");
				int number=scan.nextInt();
				for(int i=0;i<number;i++) {
					AccountInfo account =new AccountInfo();
					System.out.print("Enter the Account Number : ");
					account.setAccountNumber(scan.nextLong());
					System.out.print("Enter the Customer Id : ");
					account.setCustomerId(scan.nextInt());
					System.out.print("Enter the Balance : ");
					account.setBalance(scan.nextDouble());
					scan.nextLine();
					accounts.add(account);
				}
				dataBase.setAccountInfo(accounts,number);
			}
			else if(decision==3) {
				System.out.print("Enter the Customer id : ");
				int id=scan.nextInt();
				System.out.print("Enter the Account Number of Individual Account otherwise enter 0 : ");
				long accountNumber=scan.nextInt();
					dataBase.setAccountInfo();
					dataBase.setCustomerInfo();
				if(AccountManagement.OBJECT.getCustomerAccount().containsKey(id)) {
					if(accountNumber!=0) {
						balance.getAccount(accountNumber,id);
					}
					else if(accountNumber==0) {
						balance.getAccount(id);
					}
				}
				else {
					System.out.println("Customer id Mismatched");
				}
			}
			else if(decision==4) {
				dataBase.closeConnection();
				System.out.println("Thank you");
				break;
			}
		}
		scan.close();
	}
}
