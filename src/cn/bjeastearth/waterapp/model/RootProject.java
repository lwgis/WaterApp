package cn.bjeastearth.waterapp.model;

import java.io.Serializable;


public class RootProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1898863801783542436L;
private int ID;
private int Status;
private int Wcjd;
private String Title;
private String Description;
private String Gjdgzap;
private String Jqgzbs;
private String Remark;
private String WorkSum;
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
public int getWcjd() {
	return Wcjd;
}
public void setWcjd(int wcjd) {
	Wcjd = wcjd;
}
public String getTitle() {
	return Title;
}
public void setTitle(String title) {
	Title = title;
}
public String getDescription() {
	return Description;
}
public void setDescription(String description) {
	Description = description;
}
public String getGjdgzap() {
	return Gjdgzap;
}
public void setGjdgzap(String gjdgzap) {
	Gjdgzap = gjdgzap;
}
public String getJqgzbs() {
	return Jqgzbs;
}
public void setJqgzbs(String jqgzbs) {
	Jqgzbs = jqgzbs;
}
public String getRemark() {
	return Remark;
}
public void setRemark(String remark) {
	Remark = remark;
}
public String getWorkSum() {
	return WorkSum;
}
public void setWorkSum(String workSum) {
	WorkSum = workSum;
}

}
