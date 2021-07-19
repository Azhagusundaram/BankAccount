package bankaccount;

public class AccountInfo {
	int accountnumber,customerid,balance;
	public AccountInfo(int accountnumber,int customerid,int balance) {
		this.accountnumber=accountnumber;
		this.customerid=customerid;
		this.balance=balance;
	}
	public int getAccountNumber() {
		return accountnumber;
	}
	public int getCustomerId() {
		return customerid;
	}
	public int getbalance() {
		return balance;
	}
}
