package cn.bjeastearth.waterapp.model;

import java.io.Serializable;

public class PsZzLevel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2277140005095580024L;
	private int ID;
	private int Status;
	private String Name;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
}
