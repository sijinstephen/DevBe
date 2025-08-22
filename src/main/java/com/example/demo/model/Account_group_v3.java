package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account_group_v3")
public class Account_group_v3 {

	@Id
	private int group_id;
	private String ac_title;
	private String ac_type;
	private String group_name;
	private String created_date;
	private String time;
	
	public Account_group_v3()
	{
	
		
	}

	public Account_group_v3(int group_id, String ac_title, String ac_type, String group_name, String created_date,
			String time) {
		super();
		this.group_id = group_id;
		this.ac_title = ac_title;
		this.ac_type = ac_type;
		this.group_name = group_name;
		this.created_date = created_date;
		this.time = time;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public String getAc_title() {
		return ac_title;
	}

	public void setAc_title(String ac_title) {
		this.ac_title = ac_title;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
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

	
	
	
	
	
	
	
}
