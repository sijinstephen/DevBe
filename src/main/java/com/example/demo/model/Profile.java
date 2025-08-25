package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="profile")
public class Profile {
	@Id
	private int organization_id;
	private String 	organization_name;
	private String 	industry;
	private String 	location;
	private String 	street1;
	private String 	street2;
	private String 	city;
	private String 	state;
	private String 	zip;
	private String 	phone;
	private String 	fax;
	private String 	website;
	private String 	ifsc;
	private String 	swift_code;
	private String 	pan_no;
	private String 	acc_no;
	private String 	bank;
	private String 	branch;
	private String 	fiscal_year;
	private String 	time_zone;
	private String 	date_format;
	private String 	company_id;
	private String 	gst_id;
	private String 	signatory_name;
	private String 	signatory_designation;
	private String 	gmail;
	public Profile()
	{
	}
	public Profile(int organization_id, String organization_name, String industry, String location, String street1,
			String street2, String city, String state, String zip, String phone, String fax, String website,
			String ifsc, String swift_code, String pan_no, String acc_no, String bank, String branch,
			String fiscal_year, String time_zone, String date_format, String company_id, String gst_id,
			String signatory_name, String signatory_designation, String gmail) {
		super();
		this.organization_id = organization_id;
		this.organization_name = organization_name;
		this.industry = industry;
		this.location = location;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.fax = fax;
		this.website = website;
		this.ifsc = ifsc;
		this.swift_code = swift_code;
		this.pan_no = pan_no;
		this.acc_no = acc_no;
		this.bank = bank;
		this.branch = branch;
		this.fiscal_year = fiscal_year;
		this.time_zone = time_zone;
		this.date_format = date_format;
		this.company_id = company_id;
		this.gst_id = gst_id;
		this.signatory_name = signatory_name;
		this.signatory_designation = signatory_designation;
		this.gmail = gmail;
	}
	public int getOrganization_id() {
		return organization_id;
	}
	public void setOrganization_id(int organization_id) {
		this.organization_id = organization_id;
	}
	public String getOrganization_name() {
		return organization_name;
	}
	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getSwift_code() {
		return swift_code;
	}
	public void setSwift_code(String swift_code) {
		this.swift_code = swift_code;
	}
	public String getPan_no() {
		return pan_no;
	}
	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
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
	public String getFiscal_year() {
		return fiscal_year;
	}
	public void setFiscal_year(String fiscal_year) {
		this.fiscal_year = fiscal_year;
	}
	public String getTime_zone() {
		return time_zone;
	}
	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}
	public String getDate_format() {
		return date_format;
	}
	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getGst_id() {
		return gst_id;
	}
	public void setGst_id(String gst_id) {
		this.gst_id = gst_id;
	}
	public String getSignatory_name() {
		return signatory_name;
	}
	public void setSignatory_name(String signatory_name) {
		this.signatory_name = signatory_name;
	}
	public String getSignatory_designation() {
		return signatory_designation;
	}
	public void setSignatory_designation(String signatory_designation) {
		this.signatory_designation = signatory_designation;
	}
	public String getGmail() {
		return gmail;
	}
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
}
