package com.bankaccountmanagement;

public class Helper {
    public static int lastCustomerId =0;
    public static long lastaccount =99999;
    public static String output="";
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
    public static boolean CheckCustomerId(int id){
        return AccountManagement.OBJECT.getCustomerAccount().containsKey(id);
    }
    public static void setOutput(String str){
       output+=str;
    }
    public static String getOutput(){
        String str=output;
        output=null;
        return str;
    }
}
