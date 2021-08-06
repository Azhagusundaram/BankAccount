package com.bankaccountmanagement;

import java.util.HashMap;

public enum AccountManagement {
    OBJECT;
    private  HashMap <Integer,CustomerInfo> user_details=new HashMap<>();
    private  HashMap<Integer,HashMap<Long,AccountInfo>>all_details=new HashMap<>();
    public void setUserDetails(CustomerInfo customer) {
        user_details.put(customer.getCustomerId(),customer);
    }
    public HashMap<Integer,HashMap<Long,AccountInfo>> getAccountDetails() {
        return all_details;
    }
    public HashMap<Long,AccountInfo> getAccount(int id) {
        return all_details.get(id);
    }
    public HashMap<Integer,CustomerInfo> getCustomerAccount() {
        return user_details;
    }
}
