package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account_title_v3")
public class Account_title_v3 {
	
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ac_id;
	private String ac_title;
	private String created_date;
	private String 	time;
	private String 	ac_type;
	
	
	public Account_title_v3()
	{
	
		
	}


	public Account_title_v3(int ac_id, String ac_title, String created_date, String time, String ac_type) {
		super();
		this.ac_id = ac_id;
		this.ac_title = ac_title;
		this.created_date = created_date;
		this.time = time;
		this.ac_type = ac_type;
	}


	public int getAc_id() {
		return ac_id;
	}


	public void setAc_id(int ac_id) {
		this.ac_id = ac_id;
	}


	public String getAc_title() {
		return ac_title;
	}


	public void setAc_title(String ac_title) {
		this.ac_title = ac_title;
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


	public String getAc_type() {
		return ac_type;
	}


	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	
	
	
	
	
	
	

}
