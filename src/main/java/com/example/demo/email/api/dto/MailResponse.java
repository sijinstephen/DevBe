package com.example.demo.email.api.dto;
public class MailResponse {
	private String message;
	private boolean status;
//	public MailResponse() {
//		super();
//		this.message = message;
//		this.status = status;
//	}
//	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
