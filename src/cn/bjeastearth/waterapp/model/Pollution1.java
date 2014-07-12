package cn.bjeastearth.waterapp.model;

import java.io.Serializable;

public class Pollution1 implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 81156211303897534L;
	private double Bznd;
	private int ID;
	private int Status;
	private String Wrw;
	public double getBznd() {
		return Bznd;
	}
	public void setBznd(double bznd) {
		Bznd = bznd;
	}
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
	public String getWrw() {
		return Wrw;
	}
	public void setWrw(String wrw) {
		Wrw = wrw;
	}
}
