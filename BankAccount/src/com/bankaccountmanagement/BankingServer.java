package com.bankaccountmanagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BankingServer {

	public static void main(String[] args) throws SQLException {
		Scanner scan=new Scanner(System.in);
		ProgramDriver driver=new ProgramDriver();
		driver.setDataBase();
		while(true) {
			System.out.println("1.New customer\n2.New Account \n3.Check Balance \n4.Exit");
			int decision=scan.nextInt();
			if(decision==1) {
				System.out.print("Enter the number of customers : ");
				int number =scan.nextInt();
				scan.nextLine();
				ArrayList<ArrayList> customers = new ArrayList<>(number);
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
				HashMap<String,ArrayList<ArrayList>> newCustomer =driver.addNewCustomer(customers);
				System.out.println(newCustomer);
			}
			else if(decision==2) {
					System.out.print("Enter the Customer Id : ");
					int id=scan.nextInt();
					if(driver.checkCustomerId(id)){
						System.out.print("Enter the Deposit Amount : ");
						double amount=scan.nextDouble();
						scan.nextLine();
						AccountInfo account = Helper.getAccountInfo( id, amount);
						HashMap<String,AccountInfo> newAccount=driver.addNewAccount(account);
						System.out.println(newAccount);
					}
					else{
						System.out.println("Invalid CustomerId");
					}
			}
			else if(decision==3) {
				System.out.print("Enter the Customer id : ");
				int customerId =scan.nextInt();
				System.out.print("Enter the Account Number of Individual Account otherwise enter 0 : ");
				long accountNumber=scan.nextInt();
				if(driver.checkCustomerId(customerId)) {
					if(accountNumber!=0) {
						String str=driver.getAccount(accountNumber, customerId);
						System.out.println(str);
					}
					else {
						ArrayList<String> array=driver.getAccount(customerId);
						System.out.println(array);
					}
				}
				else {
					System.out.println("Invalid Customer id ");
				}
			}
			else if(decision==4) {
				driver.closeConnection();
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
