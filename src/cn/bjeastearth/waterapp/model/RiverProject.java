package cn.bjeastearth.waterapp.model;

import java.io.Serializable;

public class RiverProject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4563594080615024007L;
	private int ID;
	private int Status;
	private String Name;
	private String Unit;
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
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
}
