package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SewageFactory implements Serializable,FieldItemable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1297451627595377902L;
	private int ID;
	private String Name;
	private String Address;
	private String CTime;
	private String Clff;
	private String Cljb;
	private String Clnl;
	private String Contact;
	private String Fzr;
	private int Status;
	private double Tzje;
	private List<ProjectImage> Images;
	private SewageFactoryTpye Type;
	private double X;
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
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public String getClff() {
		return Clff;
	}
	public void setClff(String clff) {
		Clff = clff;
	}
	public String getCljb() {
		return Cljb;
	}
	public void setCljb(String cljb) {
		Cljb = cljb;
	}
	public String getClnl() {
		return Clnl;
	}
	public void setClnl(String clnl) {
		Clnl = clnl;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public double getTzje() {
		return Tzje;
	}
	public void setTzje(double tzje) {
		Tzje = tzje;
	}
	public List<ProjectImage> getImages() {
		return Images;
	}
	public void setImages(List<ProjectImage> images) {
		Images = images;
	}
	public SewageFactoryTpye getType() {
		return Type;
	}
	public void setType(SewageFactoryTpye type) {
		Type = type;
	}
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	private double Y;
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		ArrayList<FieldItem>fieldItems=new ArrayList<FieldItem>();
		fieldItems.add(new FieldItem("名称", getName()));
		fieldItems.add(new FieldItem("类型", getType().getName()));
		fieldItems.add(new FieldItem("地址",getAddress()));
		fieldItems.add(new FieldItem("创建时间", getCTime()));
		fieldItems.add(new FieldItem("处理能力", getClnl()));
		fieldItems.add(new FieldItem("处理方法", getClff()));
		fieldItems.add(new FieldItem("处理级别", getCljb()));
		fieldItems.add(new FieldItem("投资金额", String.valueOf(getTzje())));
		fieldItems.add(new FieldItem("负责人", getFzr()));
		fieldItems.add(new FieldItem("联系方式", getContact()));
		for (ProjectImage projectImage : getImages()) {
			fieldItems.add(new FieldItem(projectImage.getName()));
		}

		return fieldItems;	
	}
	public String getFzr() {
		return Fzr;
	}
	public void setFzr(String fzr) {
		Fzr = fzr;
	}
}
