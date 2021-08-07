package com.bankaccountmanagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBaseDriver {
	static boolean operations=true;
	public static void main(String[] args) throws SQLException {
		Scanner scan=new Scanner(System.in);
		DataBase dataBase=new DataBase();
		BankBalance balance=new BankBalance();
		dataBase.setDataBase(operations);
		while(true) {
			System.out.println("1.New customer\n2.New Account \n3.Check Balance \n4.Exit");
			int decision=scan.nextInt();
			if(decision==1) {
				System.out.print("Enter the number of customers : ");
				int number =scan.nextInt();
				scan.nextLine();
				ArrayList<ArrayList> customers = new ArrayList<ArrayList>(number*2);
				for(int i=0;i<number;i++) {
					ArrayList array=new ArrayList<>(2);
					System.out.print("Enter the Name : ");
					String name=scan.nextLine();
					System.out.print("Enter the Address : ");
					String address=scan.nextLine();
					System.out.print("Enter the Phone number : ");
					long phone=scan.nextLong();
					System.out.print("Enter the Deposit Amount : ");
					double amount=scan.nextDouble();
					scan.nextLine();
					CustomerInfo customer = Helper.getCustomerInfo(name, address, phone);
					AccountInfo account = Helper.getAccountInfo( 0, amount);
					array.add(customer);
					array.add(account);
					customers.add(array);
				}
				dataBase.uploadCustomerInfo(customers,number);
				System.out.println(Helper.getOutput());
			}
			else if(decision==2) {
				System.out.print("Enter the number of Accounts : ");
					System.out.print("Enter the Customer Id : ");
					int id=scan.nextInt();
					if(Helper.CheckCustomerId(id)){
						System.out.print("Enter the Deposit Amount : ");
						double amount=scan.nextDouble();
						scan.nextLine();
						AccountInfo account = Helper.getAccountInfo( id, amount);
						dataBase.uploadAccountInfo(account);
						System.out.println(Helper.getOutput());
					}
					else{
						System.out.println("Invalid CustomerId");
					}
			}
			else if(decision==3) {
				System.out.print("Enter the Customer id : ");
				int id=scan.nextInt();
				System.out.print("Enter the Account Number of Individual Account otherwise enter 0 : ");
				long accountNumber=scan.nextInt();
				dataBase.setDataBase(operations);
				if(AccountManagement.OBJECT.getCustomerAccount().containsKey(id)) {
					if(accountNumber!=0) {
						String outputString =balance.getAccount(accountNumber,id);
						System.out.println(outputString);
					}
					else {
						String outputString =balance.getAccount(id);
						System.out.println(outputString);
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
			else{
				System.out.println("Invalid Input");
			}
		}
		scan.close();
	}


}
