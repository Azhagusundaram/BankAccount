package com.bankaccountmanagement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class BankBalance {
	public void getAccount(Long accountNumber,int id) {
		HashMap <Long,AccountInfo> individualAccount =AccountManagement.getObject().getAccount(id);
		if(individualAccount.size()==0) {
			System.out.println("There is No Account for this Customer id");
		}
		else if(individualAccount.containsKey(accountNumber)) {
				System.out.println("Account Number\tCustomer id\tBalance");
				AccountInfo temp= individualAccount.get(accountNumber);
				System.out.println(temp.getAccountNumber()+"\t"+temp.getCustomerId()+"\t"+temp.getbalance());
		}
		else {
			System.out.println("Account Number Mismatched");
		}
	}
	public void getAccount(int id) {
		HashMap <Long,AccountInfo> individualAccount =AccountManagement.getObject().getAccount(id);
		if(individualAccount.size()==0) {
			System.out.println("There is No Account for this Customer id");
		}
		else {
			System.out.println("Account Number\tCustomer id\tBalance");
			Iterator iterate=individualAccount.entrySet().iterator();
			while(iterate.hasNext()){
				Map.Entry<Long,AccountInfo> map = (Map.Entry<Long, AccountInfo>) iterate.next();
				System.out.println(map.getValue().getAccountNumber()+"\t"+map.getValue().getCustomerId()+"\t"+map.getValue().getbalance());
			}
		}
	}
}

