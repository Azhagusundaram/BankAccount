package com.bankaccountmanagement;
public class Helper{

    public static AccountInfo getAccountInfo(int id, double amount) {
        AccountInfo account =new AccountInfo();
        account.setCustomerId(id);
        account.setBalance(amount);
        return account;
    }
    public static CustomerInfo getCustomerInfo(String name, int id, String address, long phone) {
        CustomerInfo customer=new CustomerInfo();
        customer.setName(name);
        customer.setCustomerId(id);
        customer.setAddress(address);
        customer.setPhoneNumber(phone);
        return customer;
    }
    public static AccountInfo getAccountInfo(long number, int id, double balance) {
        AccountInfo account=new AccountInfo();
        account.setAccountNumber(number);
        account.setCustomerId(id);
        account.setBalance(balance);
        return account;
    }
    public static CustomerInfo getCustomerInfo(String name, String address, long phone) {
        CustomerInfo customer =new CustomerInfo();
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhoneNumber(phone);
        return customer;
    }

}
