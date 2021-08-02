package bankaccount;

import java.util.HashSet;
import java.util.Scanner;

public class BankBalance extends DataBaseDriver {
	public static void getAccount(int decision,int id) {
		Scanner scan=new Scanner(System.in);
		if(decision==1) {
			System.out.println("Enter the Account Number");
			int account=scan.nextInt();
			if(account_details.size()==0) {
				System.out.println("There is No Account for this Customer id");
			}
			else if(account_details.containsKey(account)) {
				if((account_details.get(account).getCustomerId())==id){
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
					HashSet<Integer>keys=new HashSet<Integer>();
					for(Integer key:account_details.keySet()) {
						if(id==(account_details.get(key).getCustomerId())) {
							keys.add(key);
						}
					}
					for(Integer key:keys) {
						AccountInfo temp=account_details.get(key);
						System.out.println(temp.getAccountNumber()+"\t"+temp.getCustomerId()+"\t"+temp.getbalance());
					}
				if(keys.size()==0) {
					System.out.println("There is No Account for this Customer id");
				}
			}
		}
	}
}

