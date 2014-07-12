package cn.bjeastearth.waterapp.model;

import java.io.Serializable;

public class Pollution2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -767547029074879682L;
	private String Bz1;
	private String Bz2;
	private String Bz3;
	private int ID;
	private int Status;
	private String Syfw;
	private PollutionType Type;
	public String getBz1() {
		return Bz1;
	}
	public void setBz1(String bz1) {
		Bz1 = bz1;
	}
	public String getBz2() {
		return Bz2;
	}
	public void setBz2(String bz2) {
		Bz2 = bz2;
	}
	public String getBz3() {
		return Bz3;
	}
	public void setBz3(String bz3) {
		Bz3 = bz3;
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
	public String getSyfw() {
		return Syfw;
	}
	public void setSyfw(String syfw) {
		Syfw = syfw;
	}
	public PollutionType getType() {
		return Type;
	}
	public void setType(PollutionType type) {
		Type = type;
	}
}
