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
			System.out.println("1.Create customer id 2.Create Account 3.Check Balance 4.Exit");
			int decision=scan.nextInt();
			if(decision==1) {
				ArrayList customers=new ArrayList();
				System.out.println("Enter the number of customers");
				int number =scan.nextInt();
				for(int i=0;i<number;i++) {
					System.out.println("Enter the Name");
					customers.add(scan.nextLine());
					System.out.println("Enter the Address");
					customers.add(scan.nextLine());
					System.out.println("Enter the Customer Id");
					customers.add(scan.nextInt());
					System.out.println("Enter the Phone number");
					customers.add(scan.nextLong());
					scan.nextLine();

				}
				dataBase.getCustomerInfo(customers,number);

			}
			else if(decision==2) {
				ArrayList accounts=new ArrayList();
				System.out.println("Enter the number of Accounts");
				int number=scan.nextInt();
				for(int i=0;i<number;i++) {
					System.out.println("Enter the Account Number");
					accounts.add(scan.nextLong());
					System.out.println("Enter the Customer Id");
					accounts.add(scan.nextInt());
					System.out.println("Enter the Balance");
					accounts.add(scan.nextFloat());
					scan.nextLine();
				}
				dataBase.getAccountInfo(accounts,number);
			}
			else if(decision==3) {
				System.out.println("Enter the Customer id");
				int id=scan.nextInt();
				System.out.println("Enter the Account Number of Individual Account otherwise enter 0");
				long accountNumber=scan.nextInt();
				if(operations!=0) {
					dataBase.setAccountInfo();
					dataBase.setCustomerInfo();
				}
				if(AccountManagement.getObject().getCustomerAccount().containsKey(id)) {
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
				dataBase.closeconnection();
				System.out.println("Thank you");
				break;
			}
		}
		scan.close();
	}
}
