package com.predictor;

public class PhoneInfo {
	private String name;
	private String country;
	private String code;
	public PhoneInfo(String name, String country, String code) {
		this.name = name;
		this.country = country;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public String getCountry() {
		return country;
	}
	public String getCode() {
		return code;
	}
	
}
