package com.example.demo.email.api.dto;
public class MailRequest {
	private String name;
	private String to;
	private String from;
	private String subject;
	private int inv_id;
	private String 	inv_date;
	private String 	inv_no;
	private String 	cust_name;
	private String 	service;
	private String 	place_of_supply;
	private String 	bill_address;
	private String 	gst_no;
	private String 	tds_rate;
	private String 	created_date;
	private String 	created_time;
	private String 	status;
	private String 	total_amount;
	private String 	total_tax;
	private String 	payment_mode;
	private String 	bank_name;
	private String 	payment_date;
	private String 	amount_received;
	private String 	invoice_tran_id;
	private String 	totalTaxAmnt;
	private String 	totalAmnt;
	private String 	sgstAmnt;
	private String 	cgstAmnt;
	private String 	igstAmnt;
	public MailRequest(String name, String to, String from, String subject, int inv_id, String inv_date, String inv_no,
			String cust_name, String service, String place_of_supply, String bill_address, String gst_no,
			String tds_rate, String created_date, String created_time, String status, String total_amount,
			String total_tax, String payment_mode, String bank_name, String payment_date, String amount_received,
			String invoice_tran_id, String totalTaxAmnt, String totalAmnt, String sgstAmnt, String cgstAmnt,
			String igstAmnt) {
		super();
		this.name = name;
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.inv_id = inv_id;
		this.inv_date = inv_date;
		this.inv_no = inv_no;
		this.cust_name = cust_name;
		this.service = service;
		this.place_of_supply = place_of_supply;
		this.bill_address = bill_address;
		this.gst_no = gst_no;
		this.tds_rate = tds_rate;
		this.created_date = created_date;
		this.created_time = created_time;
		this.status = status;
		this.total_amount = total_amount;
		this.total_tax = total_tax;
		this.payment_mode = payment_mode;
		this.bank_name = bank_name;
		this.payment_date = payment_date;
		this.amount_received = amount_received;
		this.invoice_tran_id = invoice_tran_id;
		this.totalTaxAmnt = totalTaxAmnt;
		this.totalAmnt = totalAmnt;
		this.sgstAmnt = sgstAmnt;
		this.cgstAmnt = cgstAmnt;
		this.igstAmnt = igstAmnt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getInv_id() {
		return inv_id;
	}
	public void setInv_id(int inv_id) {
		this.inv_id = inv_id;
	}
	public String getInv_date() {
		return inv_date;
	}
	public void setInv_date(String inv_date) {
		this.inv_date = inv_date;
	}
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String inv_no) {
		this.inv_no = inv_no;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getPlace_of_supply() {
		return place_of_supply;
	}
	public void setPlace_of_supply(String place_of_supply) {
		this.place_of_supply = place_of_supply;
	}
	public String getBill_address() {
		return bill_address;
	}
	public void setBill_address(String bill_address) {
		this.bill_address = bill_address;
	}
	public String getGst_no() {
		return gst_no;
	}
	public void setGst_no(String gst_no) {
		this.gst_no = gst_no;
	}
	public String getTds_rate() {
		return tds_rate;
	}
	public void setTds_rate(String tds_rate) {
		this.tds_rate = tds_rate;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getTotal_tax() {
		return total_tax;
	}
	public void setTotal_tax(String total_tax) {
		this.total_tax = total_tax;
	}
	public String getPayment_mode() {
		return payment_mode;
	}
	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}
	public String getAmount_received() {
		return amount_received;
	}
	public void setAmount_received(String amount_received) {
		this.amount_received = amount_received;
	}
	public String getInvoice_tran_id() {
		return invoice_tran_id;
	}
	public void setInvoice_tran_id(String invoice_tran_id) {
		this.invoice_tran_id = invoice_tran_id;
	}
	public String getTotalTaxAmnt() {
		return totalTaxAmnt;
	}
	public void setTotalTaxAmnt(String totalTaxAmnt) {
		this.totalTaxAmnt = totalTaxAmnt;
	}
	public String getTotalAmnt() {
		return totalAmnt;
	}
	public void setTotalAmnt(String totalAmnt) {
		this.totalAmnt = totalAmnt;
	}
	public String getSgstAmnt() {
		return sgstAmnt;
	}
	public void setSgstAmnt(String sgstAmnt) {
		this.sgstAmnt = sgstAmnt;
	}
	public String getCgstAmnt() {
		return cgstAmnt;
	}
	public void setCgstAmnt(String cgstAmnt) {
		this.cgstAmnt = cgstAmnt;
	}
	public String getIgstAmnt() {
		return igstAmnt;
	}
	public void setIgstAmnt(String igstAmnt) {
		this.igstAmnt = igstAmnt;
	}
}
