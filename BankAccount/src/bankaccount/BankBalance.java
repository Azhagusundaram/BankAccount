package bankaccount;

import java.util.HashSet;
import java.util.Scanner;

public class BankBalance extends AccountDetails{
	public static void getAccount(int decision,int id) {
		if(decision==1) {
			Scanner scan=new Scanner(System.in);
			System.out.println("Enter the Account Number");
			int account=scan.nextInt();
			if(account_details.size()==0) {
				System.out.println("There is No Account for this Customer id");
			}
			else if(account_details.containsKey(account)) {
				if(connect_details.get(account)==id) {
					System.out.println("Account Number\tCustomer id\tBalance");
					AccountInfo temp=account_details.get(account);
					System.out.println(temp.getAccountNumber()+"\t"+temp.getCustomerId()+"\t"+temp.getbalance());
				}
				else {
					System.out.println("Account Number Mismatched");
				}
				
			}
			else {
				System.out.println("Account Number Mismatched");
			}
		}
		else if(decision==2) {
			if(account_details.size()==0) {
				System.out.println("There is No Account for this Customer id");
			}
			else {
				System.out.println("Account Number\tCustomer id\tBalance");
				if(connect_details.containsValue(id)) {
					HashSet<Integer>keys=new HashSet<Integer>();
					for(Integer key:connect_details.keySet()) {
						if(id==(connect_details.get(key))) {
							keys.add(key);
						}
					}
					for(Integer key:keys) {
						AccountInfo temp=account_details.get(key);
						System.out.println(temp.getAccountNumber()+"\t"+temp.getCustomerId()+"\t"+temp.getbalance());
					}
				}
				else {
					System.out.println("There is No Account for this Customer id");
				}
			}
		}
	}
}

