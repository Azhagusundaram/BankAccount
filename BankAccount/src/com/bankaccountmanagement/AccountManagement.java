package com.bankaccountmanagement;

import java.util.HashMap;

public enum AccountManagement {
    OBJECT;
    private  HashMap <Integer,CustomerInfo> customerHashMap =new HashMap<>();
    private  HashMap<Integer,HashMap<Long,AccountInfo>> accountHashMap =new HashMap<>();
    public void setUserDetails(CustomerInfo customer) {
        customerHashMap.put(customer.getCustomerId(),customer);
    }
    public HashMap<Integer,HashMap<Long,AccountInfo>> getAccountDetails() {
        return accountHashMap;
    }
    public HashMap<Long,AccountInfo> getAccount(int id) {
        return accountHashMap.get(id);
    }
    public HashMap<Integer,CustomerInfo> getCustomerAccount() {
        return customerHashMap;
    }
    public void setAccountDetails(AccountInfo account){
        int tempCustomerId= account.getCustomerId();
        HashMap<Long,AccountInfo> accountDetails=accountHashMap.get(tempCustomerId);
        if(accountDetails==null) {
            accountDetails=new HashMap<>();
            accountHashMap.put(account.getCustomerId(),accountDetails);
        }
        accountDetails.put(account.getAccountNumber(),account);

    }
}
