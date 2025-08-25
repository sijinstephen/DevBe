package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="invoice_sub")
public class Invoice_sub {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int inv_sub_id;
	private String 	inv_id;
	private String 	description;
	private String 	hsn;
	private String 	qty;
	private String 	amount;
	private String 	tax;
	private String 	remarks;
	private String 	created_date;
	private String 	created_time;
	public Invoice_sub()
	{
	}
	public Invoice_sub(int inv_sub_id, String inv_id, String description, String hsn, String qty, String amount,
			String tax, String remarks, String created_date, String created_time) {
		super();
		this.inv_sub_id = inv_sub_id;
		this.inv_id = inv_id;
		this.description = description;
		this.hsn = hsn;
		this.qty = qty;
		this.amount = amount;
		this.tax = tax;
		this.remarks = remarks;
		this.created_date = created_date;
		this.created_time = created_time;
	}
	public int getInv_sub_id() {
		return inv_sub_id;
	}
	public void setInv_sub_id(int inv_sub_id) {
		this.inv_sub_id = inv_sub_id;
	}
	public String getInv_id() {
		return inv_id;
	}
	public void setInv_id(String inv_id) {
		this.inv_id = inv_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHsn() {
		return hsn;
	}
	public void setHsn(String hsn) {
		this.hsn = hsn;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
}
