package cn.bjeastearth.waterapp.model;

public class User {
	private int ID;
    private String Name;
    private String Password;
    private String Phone;
    private Department UserDept;
    private String Xm;
    private Region Xzq;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public Department getUserDept() {
		return UserDept;
	}
	public void setUserDept(Department userDept) {
		UserDept = userDept;
	}
	public String getXm() {
		return Xm;
	}
	public void setXm(String xm) {
		Xm = xm;
	}
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
}
