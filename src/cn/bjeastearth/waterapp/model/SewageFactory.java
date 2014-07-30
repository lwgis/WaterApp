package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SewageFactory implements FieldItemable {

	/**
	 * 
	 */
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
	private ArrayList<FieldItem>mFieldItems;
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
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("名称", getName()));
			mFieldItems.add(new FieldItem("类型", getType().getName()));
			mFieldItems.add(new FieldItem("地址",getAddress()));
			mFieldItems.add(new FieldItem("创建时间", getCTime()));
			mFieldItems.add(new FieldItem("处理能力", getClnl()));
			mFieldItems.add(new FieldItem("处理方法", getClff()));
			mFieldItems.add(new FieldItem("处理级别", getCljb()));
			mFieldItems.add(new FieldItem("投资金额", String.valueOf(getTzje())));
			mFieldItems.add(new FieldItem("负责人", getFzr()));
			mFieldItems.add(new FieldItem("联系方式", getContact()));
			if (getImages()!=null&&getImages().size()>0) {
				ArrayList<FieldItem> imArrayList=new ArrayList<FieldItem>();
				for (ProjectImage projectImage : getImages()) {
					imArrayList.add(new FieldItem(null, projectImage.getName()));
				}
				mFieldItems.add(new FieldItem(imArrayList));
			}
		}		
		return mFieldItems;	
	}
	public String getFzr() {
		return Fzr;
	}
	public void setFzr(String fzr) {
		Fzr = fzr;
	}
}
