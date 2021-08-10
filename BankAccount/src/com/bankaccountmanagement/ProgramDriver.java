package com.bankaccountmanagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProgramDriver {

    DataBase dataBase=new DataBase();

    public HashMap<String,ArrayList<ArrayList>> addNewCustomer(ArrayList<ArrayList>customers) throws SQLException {
        ArrayList<Integer> customerIds = dataBase.uploadCustomerInfo(customers);
        HashMap<String,ArrayList<ArrayList>> newCustomer=new HashMap();
        ArrayList<ArrayList> successCustomerDetails=new ArrayList<>();
        ArrayList<ArrayList> failureCustomerDetails=new ArrayList<>();
        for(int i = 0; i< customerIds.size(); i++){
            ArrayList temp=customers.get(i);
            CustomerInfo tempCustomer = (CustomerInfo)temp.get(0);
            AccountInfo tempAccount = (AccountInfo) temp.get(1);
            ArrayList customerAccount =new ArrayList();
            if(customerIds.get(i)!=0){
                int customerId=customerIds.get(i);
                tempCustomer.setCustomerId(customerId);
                tempAccount.setCustomerId(customerId);
                Long accountNumber=dataBase.uploadAccountInfo(tempAccount);
                if(accountNumber!=0){
                    tempAccount.setAccountNumber(accountNumber);
                    setCustomerHashMap(tempCustomer);
                    setAccountHashMap(tempAccount);
                    customerAccount.add(tempCustomer);
                    customerAccount.add(tempAccount);
                    successCustomerDetails.add(customerAccount);
                    newCustomer.put("Success",successCustomerDetails);
                }
                else{
                    dataBase.deleteCustomerInfo(customerId);
                    tempCustomer.setCustomerId(0);
                    tempAccount.setCustomerId(0);
                    customerAccount.add(tempCustomer);
                    customerAccount.add(tempAccount);
                    failureCustomerDetails.add(customerAccount);
                    newCustomer.put("Failure",failureCustomerDetails);
                }
            }
            else{
                customerAccount.add(tempCustomer);
                customerAccount.add(tempAccount);
                failureCustomerDetails.add(customerAccount);
                newCustomer.put("Failure",failureCustomerDetails);
            }
        }
        return newCustomer;
    }
    public HashMap<String,AccountInfo> addNewAccount(AccountInfo account) throws SQLException{
        HashMap<String,AccountInfo> newAccount=new HashMap<>();
        Long accountNumber=dataBase.uploadAccountInfo(account);
        if (accountNumber!=0){
            account.setAccountNumber(accountNumber);
            setAccountHashMap(account);
            newAccount.put("Success",account);
        }
        else{
            newAccount.put("Failure",account);
        }
        return newAccount;
    }
    public void setDataBase() throws SQLException{
            ArrayList<CustomerInfo> customer=dataBase.setCustomerInfo();
            ArrayList<AccountInfo> account=dataBase.setAccountInfo();
            setCustomerHashMap(customer);
            setAccountHashMap(account);
    }
    public void setCustomerHashMap(CustomerInfo customer){
        AccountManagement.OBJECT.setUserDetails(customer);
    }
    public void setAccountHashMap(AccountInfo account){
        AccountManagement.OBJECT.setAccountDetails(account);
    }
    public void setCustomerHashMap( ArrayList<CustomerInfo>customer){
        for(int i=0;i< customer.size();i++){
            setCustomerHashMap(customer.get(i));
        }
    }
    public void setAccountHashMap(ArrayList<AccountInfo>account){
        for(int i=0;i<account.size();i++) {
            setAccountHashMap(account.get(i));
        }
    }
    public void closeConnection()throws SQLException{
        dataBase.closeConnection();
    }
    public boolean checkCustomerId(int id){
        return AccountManagement.OBJECT.getCustomerAccount().containsKey(id);
    }
    public String getAccount(long accountNumber,int customerId) {
        HashMap <Long,AccountInfo> individualAccount = AccountManagement.OBJECT.getAccount(customerId);
        AccountInfo temp= individualAccount.get(accountNumber);
        if(temp!=null) {
            return temp.toString();
        }
        else {
            return "Invalid Account Number";
        }
    }
    public ArrayList<String> getAccount(int customerId) {
        HashMap <Long,AccountInfo> individualAccount = AccountManagement.OBJECT.getAccount(customerId);
        Iterator iterate=individualAccount.entrySet().iterator();
        ArrayList<String> array=new ArrayList<>(individualAccount.size());
        while(iterate.hasNext()){
            Map.Entry<Long,AccountInfo> map = (Map.Entry<Long, AccountInfo>) iterate.next();
            array.add(map.getValue().toString());
        }
        return array;
    }
}
