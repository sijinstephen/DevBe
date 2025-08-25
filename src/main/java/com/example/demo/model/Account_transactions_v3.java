package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="account_transactions_v3")
public class Account_transactions_v3 {
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private int 	tranID;
	private String 	transactionID;
	private String 	dbt_ac;
	private String 	crdt_ac;
	private String 	mode;
	private String 	amount;
	private String 	type;
	private String 	tran_gen;
	private String 	tran_Date;
	private String 	description;
	private String 	ac_no;
	private String 	chq_no;
	private String 	chq_date;
	private String 	branch;
	private String 	user_bank;
	private String 	bank;
	private String 	status;
	private String 	filename;
	private String 	filepath;
	private String 	createdBy;
	private String 	createdDate;
	private String 	createdTime;
	private String 	credit_blnc_bfore_txn;
	private String 	debit_blnc_bfore_txn;
	public Account_transactions_v3()
	{
	}
	public Account_transactions_v3(int tranID, String transactionID, String dbt_ac, String crdt_ac, String mode,
			String amount, String type, String tran_gen, String tran_Date, String description, String ac_no,
			String chq_no, String chq_date, String branch, String user_bank, String bank, String status,
			String filename, String filepath, String createdBy, String createdDate, String createdTime,
			String credit_blnc_bfore_txn, String debit_blnc_bfore_txn) {
		super();
		this.tranID = tranID;
		this.transactionID = transactionID;
		this.dbt_ac = dbt_ac;
		this.crdt_ac = crdt_ac;
		this.mode = mode;
		this.amount = amount;
		this.type = type;
		this.tran_gen = tran_gen;
		this.tran_Date = tran_Date;
		this.description = description;
		this.ac_no = ac_no;
		this.chq_no = chq_no;
		this.chq_date = chq_date;
		this.branch = branch;
		this.user_bank = user_bank;
		this.bank = bank;
		this.status = status;
		this.filename = filename;
		this.filepath = filepath;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.createdTime = createdTime;
		this.credit_blnc_bfore_txn = credit_blnc_bfore_txn;
		this.debit_blnc_bfore_txn = debit_blnc_bfore_txn;
	}
	public int getTranID() {
		return tranID;
	}
	public void setTranID(int tranID) {
		this.tranID = tranID;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getDbt_ac() {
		return dbt_ac;
	}
	public void setDbt_ac(String dbt_ac) {
		this.dbt_ac = dbt_ac;
	}
	public String getCrdt_ac() {
		return crdt_ac;
	}
	public void setCrdt_ac(String crdt_ac) {
		this.crdt_ac = crdt_ac;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTran_gen() {
		return tran_gen;
	}
	public void setTran_gen(String tran_gen) {
		this.tran_gen = tran_gen;
	}
	public String getTran_Date() {
		return tran_Date;
	}
	public void setTran_Date(String tran_Date) {
		this.tran_Date = tran_Date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAc_no() {
		return ac_no;
	}
	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}
	public String getChq_no() {
		return chq_no;
	}
	public void setChq_no(String chq_no) {
		this.chq_no = chq_no;
	}
	public String getChq_date() {
		return chq_date;
	}
	public void setChq_date(String chq_date) {
		this.chq_date = chq_date;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getUser_bank() {
		return user_bank;
	}
	public void setUser_bank(String user_bank) {
		this.user_bank = user_bank;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getCredit_blnc_bfore_txn() {
		return credit_blnc_bfore_txn;
	}
	public void setCredit_blnc_bfore_txn(String credit_blnc_bfore_txn) {
		this.credit_blnc_bfore_txn = credit_blnc_bfore_txn;
	}
	public String getDebit_blnc_bfore_txn() {
		return debit_blnc_bfore_txn;
	}
	public void setDebit_blnc_bfore_txn(String debit_blnc_bfore_txn) {
		this.debit_blnc_bfore_txn = debit_blnc_bfore_txn;
	}
}
