package cn.bjeastearth.waterapp.model;

import java.io.Serializable;

public class SewageFactoryTpye implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6159550988310713024L;
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
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	private int Status;
}
