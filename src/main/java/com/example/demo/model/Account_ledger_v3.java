package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account_ledger_v3")
public class Account_ledger_v3 {

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String 	ledger_name;
	private String 	ac_type;
	private String 	ac_title;
	private String 	ac_group;
	private String 	name;
	private String 	address;
	private String 	state;
	private String 	pin;
	private String 	contact;
	private String 	mobile;
	private String 	fax;
	private String 	email;
	private String  acc_number;
	private String 	bank;
	private String 	branch;
	private String 	ifsc_code;
	private String 	open_balance;
	private String 	amount;
	private String 	account_id;
	private String 	created_date;
	private String 	time;
	
//	@Column(name="userID")
	private String 	userID;
	
	private String 	balance_type;
	private String ledger_date;
	
	
	public Account_ledger_v3()
	{
	
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLedger_name() {
		return ledger_name;
	}


	public void setLedger_name(String ledger_name) {
		this.ledger_name = ledger_name;
	}


	public String getAc_type() {
		return ac_type;
	}


	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}


	public String getAc_title() {
		return ac_title;
	}


	public void setAc_title(String ac_title) {
		this.ac_title = ac_title;
	}


	public String getAc_group() {
		return ac_group;
	}


	public void setAc_group(String ac_group) {
		this.ac_group = ac_group;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getPin() {
		return pin;
	}


	public void setPin(String pin) {
		this.pin = pin;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAcc_number() {
		return acc_number;
	}


	public void setAcc_number(String acc_number) {
		this.acc_number = acc_number;
	}


	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getIfsc_code() {
		return ifsc_code;
	}


	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}


	public String getOpen_balance() {
		return open_balance;
	}


	public void setOpen_balance(String open_balance) {
		this.open_balance = open_balance;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getAccount_id() {
		return account_id;
	}


	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}


	public String getCreated_date() {
		return created_date;
	}


	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getBalance_type() {
		return balance_type;
	}


	public void setBalance_type(String balance_type) {
		this.balance_type = balance_type;
	}

	
	
	
	

	public String getLedger_date() {
		return ledger_date;
	}


	public void setLedger_date(String ledger_date) {
		this.ledger_date = ledger_date;
	}


	public Account_ledger_v3(int id, String ledger_name, String ac_type, String ac_title, String ac_group, String name,
			String address, String state, String pin, String contact, String mobile, String fax, String email,
			String acc_number, String bank, String branch, String ifsc_code, String open_balance, String amount,
			String account_id, String created_date, String time, String userID, String balance_type,
			String ledger_date) {
		super();
		this.id = id;
		this.ledger_name = ledger_name;
		this.ac_type = ac_type;
		this.ac_title = ac_title;
		this.ac_group = ac_group;
		this.name = name;
		this.address = address;
		this.state = state;
		this.pin = pin;
		this.contact = contact;
		this.mobile = mobile;
		this.fax = fax;
		this.email = email;
		this.acc_number = acc_number;
		this.bank = bank;
		this.branch = branch;
		this.ifsc_code = ifsc_code;
		this.open_balance = open_balance;
		this.amount = amount;
		this.account_id = account_id;
		this.created_date = created_date;
		this.time = time;
		this.userID = userID;
		this.balance_type = balance_type;
		this.ledger_date = ledger_date;
	}



	
	
	
	
	
	
	
	
}
