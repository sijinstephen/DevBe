package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="invoice_template")
public class Invoice_template {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int template_id;
	private String 	template_logo;
	private String 	template_payTo;
	private String 	template_Name;
	private String 	template_sig;
	private String 	template_companyName;
	private String 	template_companyAddress;
	private String 	template_companyContact;
	public Invoice_template()
	{
	}
	public Invoice_template(int template_id, String template_logo, String template_payTo, String template_Name,
			String template_sig, String template_companyName, String template_companyAddress,
			String template_companyContact) {
		super();
		this.template_id = template_id;
		this.template_logo = template_logo;
		this.template_payTo = template_payTo;
		this.template_Name = template_Name;
		this.template_sig = template_sig;
		this.template_companyName = template_companyName;
		this.template_companyAddress = template_companyAddress;
		this.template_companyContact = template_companyContact;
	}
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}
	public String getTemplate_logo() {
		return template_logo;
	}
	public void setTemplate_logo(String template_logo) {
		this.template_logo = template_logo;
	}
	public String getTemplate_payTo() {
		return template_payTo;
	}
	public void setTemplate_payTo(String template_payTo) {
		this.template_payTo = template_payTo;
	}
	public String getTemplate_Name() {
		return template_Name;
	}
	public void setTemplate_Name(String template_Name) {
		this.template_Name = template_Name;
	}
	public String getTemplate_sig() {
		return template_sig;
	}
	public void setTemplate_sig(String template_sig) {
		this.template_sig = template_sig;
	}
	public String getTemplate_companyName() {
		return template_companyName;
	}
	public void setTemplate_companyName(String template_companyName) {
		this.template_companyName = template_companyName;
	}
	public String getTemplate_companyAddress() {
		return template_companyAddress;
	}
	public void setTemplate_companyAddress(String template_companyAddress) {
		this.template_companyAddress = template_companyAddress;
	}
	public String getTemplate_companyContact() {
		return template_companyContact;
	}
	public void setTemplate_companyContact(String template_companyContact) {
		this.template_companyContact = template_companyContact;
	}
}
