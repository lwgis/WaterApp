package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;


public class FactoryGywn implements FieldItemable,SewageFactory {
	private int ID;
	private int Status;
	private String CTime;
	private String Clff;
	private double Clnl;
	private String Cljb;
	private String Contact;
	private String Fzr;
	private List<ProjectImage> Images;
	private String Name;
	private double X;
	private double Y;
	private Region Xzq;
	private double  Fgbj;
	private ArrayList<FieldItem> mFieldItems;
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
	public double getClnl() {
		return Clnl;
	}
	public void setClnl(double clnl) {
		Clnl = clnl;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getFzr() {
		return Fzr;
	}
	public void setFzr(String fzr) {
		Fzr = fzr;
	}
	public List<ProjectImage> getImages() {
		return Images;
	}
	public void setImages(List<ProjectImage> images) {
		Images = images;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
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
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
		}
		mFieldItems.clear();
		mFieldItems.add(new FieldItem("名称", Name));
		mFieldItems.add(new FieldItem("行政区", Xzq.getName()));
		mFieldItems.add(new FieldItem("负责人", Fzr));
		mFieldItems.add(new FieldItem("联系方式", Contact));
		mFieldItems.add(new FieldItem("处理方法", Clff));
		mFieldItems.add(new FieldItem("处理级别", Cljb));
		mFieldItems.add(new FieldItem("处理能力", String.valueOf(Clnl)));
		mFieldItems.add(new FieldItem("建设时间", CTime));
		if (getImages()!=null&&getImages().size()>0) {
			ArrayList<FieldItem> imArrayList=new ArrayList<FieldItem>();
			for (ProjectImage projectImage : getImages()) {
				imArrayList.add(new FieldItem(null, projectImage.getName()));
			}
			mFieldItems.add(new FieldItem(imArrayList));
		}
		return mFieldItems;
	}
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
	}
	public String getCljb() {
		return Cljb;
	}
	public void setCljb(String cljb) {
		Cljb = cljb;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return Name;
	}
	@Override
	public String getXzqName() {
		// TODO Auto-generated method stub
		return Xzq!=null?Xzq.getName():"";
	}
	@Override
	public String getFID() {
		// TODO Auto-generated method stub
		return "Gywn"+ID;
	}
	public double getFgbj() {
		return Fgbj;
	}
	public void setFgbj(double fgbj) {
		Fgbj = fgbj;
	}

}
