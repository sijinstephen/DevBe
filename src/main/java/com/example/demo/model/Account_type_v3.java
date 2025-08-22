package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account_type_v3")
public class Account_type_v3 {

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ac_type_id;
	private String ac_type;
	
	
	public Account_type_v3()
	{
	
		
	}


	public Account_type_v3(int ac_type_id, String ac_type) {
		super();
		this.ac_type_id = ac_type_id;
		this.ac_type = ac_type;
	}


	public int getAc_type_id() {
		return ac_type_id;
	}


	public void setAc_type_id(int ac_type_id) {
		this.ac_type_id = ac_type_id;
	}


	public String getAc_type() {
		return ac_type;
	}


	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	
	
	
	
	
	
	
	
}
