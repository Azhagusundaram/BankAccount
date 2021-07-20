package bankaccount;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
public class AccountDetails {
	static HashMap <Integer,CustomerInfo> user_details=new HashMap<Integer,CustomerInfo>();
	static HashMap<Integer,AccountInfo>account_details=new HashMap<Integer,AccountInfo>();
	public static void main(String[] args) throws SQLException {
		Scanner scan=new Scanner(System.in);	
		while(true) {
				System.out.println("1.Create customer id 2.Create Account 3.Check Balance 4.Exit");
				int decision=scan.nextInt();
				if(decision==1) {
					DataBase.setCustomerInfo();
				}
				else if(decision==2) {
					DataBase.setAccountInfo();
				}
				else if(decision==3) {
					DataBase.getAccountInfo();
					DataBase.getCustomerInfo();
					System.out.println("Enter the Customer id");
					int id=scan.nextInt();
					if(user_details.containsKey(id)) {
						System.out.println("1.Check Individual Account 2.All Accounts");
						int decision1=scan.nextInt();
						BankBalance.getAccount(decision1,id);
					}
					else {
						System.out.println("Customer id Mismatched");
					}
				}
				else if(decision==4) {
					System.out.println("Thank you");
					break;
				}
			}
			scan.close();
	}
}
