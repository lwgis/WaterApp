package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;

import cn.bjeastearth.waterapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class PsLive implements PollutionSource {
	 private String CTime;
	 private double Gsl;
	 private int ID;
	private double	Rk;
	private int	Status;
	private PsLiveType Type;
	private double	Wscll;
	private	double Wsgdjgl;
	private double	Wszl;
	private double	X;
	private Region	Xzq;
	private double	Y;
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public double getGsl() {
		return Gsl;
	}
	public void setGsl(double gsl) {
		Gsl = gsl;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public double getRk() {
		return Rk;
	}
	public void setRk(double rk) {
		Rk = rk;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public PsLiveType getType() {
		return Type;
	}
	public void setType(PsLiveType type) {
		Type = type;
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
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	@Override
	public String getShowTitle() {
		// TODO Auto-generated method stub
		return "污水类型："+getType().getName();
	}
	@Override
	public String getShowDescribing() {
		// TODO Auto-generated method stub
		return "污水总量："+String.valueOf(getWszl());
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		ArrayList<FieldItem> fieldItems=new ArrayList<FieldItem>();
		fieldItems.add(new FieldItem("行政区域", getXzq().getName()));
		fieldItems.add(new FieldItem("污水类型",getType().getName()));
		fieldItems.add(new FieldItem("供水量", String.valueOf(getGsl())));
		fieldItems.add(new FieldItem("污水管道接管率", String.valueOf(getWsgdjgl())));
		fieldItems.add(new FieldItem("污水总量",String.valueOf(getWszl())));
		fieldItems.add(new FieldItem("污水处理率", String.valueOf(getWscll())));
		fieldItems.add(new FieldItem("人口", String.valueOf(getRk())));
		return fieldItems;
	}
	@Override
	public String getImageString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Drawable getMapDrawable(Context context) {
		Drawable image = context.getResources().getDrawable(R.drawable.map_item_live);
		return image;
	}
	@Override
	public String getPID() {
		// TODO Auto-generated method stub
		return "SH"+getID();
	}
	@Override
	public PsType getPsType() {
		// TODO Auto-generated method stub
		return PsType.SH;
	}
}
