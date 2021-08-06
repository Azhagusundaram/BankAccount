package com.bankaccountmanagement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BankBalance {
	public String getAccount(Long accountNumber,int id) {
		HashMap <Long,AccountInfo> individualAccount = AccountManagement.OBJECT.getAccount(id);
		if(individualAccount.size()==0) {
			return "There is No Account for this Customer id";
		}
		else if(individualAccount.containsKey(accountNumber)) {
				AccountInfo temp= individualAccount.get(accountNumber);
				return temp.toString();
		}
		else {
			return "Account Number Mismatched";
		}
	}
	public String getAccount(int id) {
		HashMap <Long,AccountInfo> individualAccount =AccountManagement.OBJECT.getAccount(id);
		if(individualAccount.size()==0) {
			return "There is No Account for this Customer id";
		}
		else {
			Iterator iterate=individualAccount.entrySet().iterator();
			String str="";
			while(iterate.hasNext()){
				Map.Entry<Long,AccountInfo> map = (Map.Entry<Long, AccountInfo>) iterate.next();
				str+="\n"+map.toString();
			}
			return str;
		}
	}
}

