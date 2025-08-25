package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="account_user_v3")
public class Account_user_v3 {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String user_id;
	private String user_name;
	private String password;
	private String user_type;
	private String company_name;
	public Account_user_v3()
	{
	}
	public Account_user_v3(int id, String user_id, String user_name, String password, String user_type,
			String company_name) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
		this.user_type = user_type;
		this.company_name = company_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
}
