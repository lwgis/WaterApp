package cn.bjeastearth.waterapp.model;

import java.io.Serializable;


public class ProjectRegion implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -2591053273389739535L;
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
}
