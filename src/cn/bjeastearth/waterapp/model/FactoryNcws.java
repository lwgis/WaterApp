package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;


public class FactoryNcws implements FieldItemable,SewageFactory {
	private int ID;
	private int Status;
	private String CTime;
	private double Fgrksl;
	private double Gsl;
	private int ObjID;
	private double Rk;
	private String Wsclgy;
	private double Wscll;
	private double Wsgdjgl;
	private double Wszl;
	private Region Xzq;
	private double X;
	private double Y;
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
	public double getFgrksl() {
		return Fgrksl;
	}
	public void setFgrksl(double fgrksl) {
		Fgrksl = fgrksl;
	}
	public double getGsl() {
		return Gsl;
	}
	public void setGsl(double gsl) {
		Gsl = gsl;
	}
	public int getObjID() {
		return ObjID;
	}
	public void setObjID(int objID) {
		ObjID = objID;
	}
	public double getRk() {
		return Rk;
	}
	public void setRk(double rk) {
		Rk = rk;
	}
	public String getWsclgy() {
		return Wsclgy;
	}
	public void setWsclgy(String wsclgy) {
		Wsclgy = wsclgy;
	}
	public double getWscll() {
		return Wscll;
	}
	public void setWscll(double wscll) {
		Wscll = wscll;
	}
	public double getWsgdjgl() {
		return Wsgdjgl;
	}
	public void setWsgdjgl(double wsgdjgl) {
		Wsgdjgl = wsgdjgl;
	}
	public double getWszl() {
		return Wszl;
	}
	public void setWszl(double wszl) {
		Wszl = wszl;
	}
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
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
		mFieldItems.add(new FieldItem("行政区", Xzq.getName()));
		mFieldItems.add(new FieldItem("污水处理工艺", Wsclgy));
		mFieldItems.add(new FieldItem("总人口", String.valueOf(Rk)));
		mFieldItems.add(new FieldItem("覆盖人口数量", String.valueOf(Fgrksl)));
		mFieldItems.add(new FieldItem("供水量", String.valueOf(Gsl)));
		mFieldItems.add(new FieldItem("污水总量", String.valueOf(Wszl)));
		mFieldItems.add(new FieldItem("污水处理率", String.valueOf(Wscll)));
		mFieldItems.add(new FieldItem("污水管道接管率", String.valueOf(Wsgdjgl)));
		mFieldItems.add(new FieldItem("建设时间", CTime));
		return mFieldItems;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "污水总量:"+String.valueOf(Wszl);
	}
	@Override
	public String getXzqName() {
		// TODO Auto-generated method stub
		return Xzq!=null?Xzq.getName():"";
	}
	@Override
	public List<ProjectImage> getImages() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getFID() {
		// TODO Auto-generated method stub
		return "Ncws"+ID;
	}


}
