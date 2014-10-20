package cn.bjeastearth.waterapp.model;

public class Account {
	private User Account;
	private int Code;
	private String Meassage;
	public User getAccount() {
		return Account;
	}
	public void setAccount(User account) {
		Account = account;
	}
	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		Code = code;
	}
	public String getMeassage() {
		return Meassage;
	}
	public void setMeassage(String meassage) {
		Meassage = meassage;
	}
}
