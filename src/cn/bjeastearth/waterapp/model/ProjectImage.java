package cn.bjeastearth.waterapp.model;

import java.io.Serializable;

public class ProjectImage implements Serializable {
	public final static int LOCAL=0;
	public final static int INTERNET=1;
	private int type;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8737859742996107657L;
	private int ID;
	private String Name;
	private int Status;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
