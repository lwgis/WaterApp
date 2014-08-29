package cn.bjeastearth.waterapp.model;

import java.io.Serializable;

public class Department implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4733309836867019469L;
	private String Address;
	private int ID;
	private String Name;
	private int Parentid;
	private int Status;
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getParentid() {
		return Parentid;
	}
	public void setParentid(int parentid) {
		Parentid = parentid;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
}
