package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;


public class FactorySzws implements FieldItemable,SewageFactory {
	private String CTime;
	private String Clgy;
	private String Cljb;
	private String Contact;
	private double Fyclnl;
	private String Fzr;
	private double Gyfscll;
	private int ID;
	private List<ProjectImage> Images;
	private String Name;
	private double Shwsbz;
	private double Shwscll;
	private double Sjcll;
	private double Sjclnl;
	private String Snstmc;
	private int Status;
	private double X;
	private double Y;
	private Region Xzq;
	private double Fgbj;
	private ArrayList<FieldItem> mFieldItems;
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public String getClgy() {
		return Clgy;
	}
	public void setClgy(String clgy) {
		Clgy = clgy;
	}
	public String getCljb() {
		return Cljb;
	}
	public void setCljb(String cljb) {
		Cljb = cljb;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public double getFyclnl() {
		return Fyclnl;
	}
	public void setFyclnl(double fyclnl) {
		Fyclnl = fyclnl;
	}
	public String getFzr() {
		return Fzr;
	}
	public void setFzr(String fzr) {
		Fzr = fzr;
	}
	public double getGyfscll() {
		return Gyfscll;
	}
	public void setGyfscll(double gyfscll) {
		Gyfscll = gyfscll;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
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
	public double getShwsbz() {
		return Shwsbz;
	}
	public void setShwsbz(double shwsbz) {
		Shwsbz = shwsbz;
	}
	public double getShwscll() {
		return Shwscll;
	}
	public void setShwscll(double shwscll) {
		Shwscll = shwscll;
	}
	public double getSjcll() {
		return Sjcll;
	}
	public void setSjcll(double sjcll) {
		Sjcll = sjcll;
	}
	public double getSjclnl() {
		return Sjclnl;
	}
	public void setSjclnl(double sjclnl) {
		Sjclnl = sjclnl;
	}
	public String getSnstmc() {
		return Snstmc;
	}
	public void setSnstmc(String snstmc) {
		Snstmc = snstmc;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
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
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
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
		mFieldItems.add(new FieldItem("联系方式",Contact));
		mFieldItems.add(new FieldItem("处理工艺",Clgy));
		mFieldItems.add(new FieldItem("处理级别", Cljb));
		mFieldItems.add(new FieldItem("设计处理能力", String.valueOf(Sjclnl)));
		mFieldItems.add(new FieldItem("实际处理量", String.valueOf(Sjcll)));
		mFieldItems.add(new FieldItem("生活污水处理量", String.valueOf(Shwscll)));
		mFieldItems.add(new FieldItem("工业废水处理量", String.valueOf(Gyfscll)));
		mFieldItems.add(new FieldItem("生活污水比重", String.valueOf(Shwsbz)));
		mFieldItems.add(new FieldItem("富裕处理能力", String.valueOf(Fyclnl)));
		mFieldItems.add(new FieldItem("收纳水体名称", Snstmc));
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
		return "Szws"+ID;
	}
	public double getFgbj() {
		return Fgbj;
	}
	public void setFgbj(double fgbj) {
		Fgbj = fgbj;
	}

}
