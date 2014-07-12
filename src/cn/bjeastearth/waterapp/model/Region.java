package cn.bjeastearth.waterapp.model;

import java.io.Serializable;

public class Region implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1097244985799536395L;
	private int ID;
	private String Name;
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
	private int Parentid;
	private int Status;
}
