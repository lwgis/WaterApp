package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bjeastearth.waterapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class PsLive implements PollutionSource,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7665042964780137144L;
	private double Cod;
	private String CTime;
	private double Czhjfnrk;
	private double Czzzrk;
	private double Gsl;
	private  int ID;
	private double Nchjnyrk;
	private double Nchjzzrk;
	private double Nh3N;
	private double Nsum;
	private double Psum;
	private double Rk;
	private int Status;
	private double Wscll;
	private double Wsgdjgl;
	private double Wszl;
	private Region Xzq;
	private double X;
	private double Y;
	private ArrayList<FieldItem>mFieldItems;

	public double getCod() {
		return Cod;
	}
	public void setCod(double cod) {
		Cod = cod;
	}
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public double getCzhjfnrk() {
		return Czhjfnrk;
	}
	public void setCzhjfnrk(double czhjfnrk) {
		Czhjfnrk = czhjfnrk;
	}
	public double getCzzzrk() {
		return Czzzrk;
	}
	public void setCzzzrk(double czzzrk) {
		Czzzrk = czzzrk;
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
	public double getNchjnyrk() {
		return Nchjnyrk;
	}
	public void setNchjnyrk(double nchjnyrk) {
		Nchjnyrk = nchjnyrk;
	}
	public double getNchjzzrk() {
		return Nchjzzrk;
	}
	public void setNchjzzrk(double nchjzzrk) {
		Nchjzzrk = nchjzzrk;
	}
	public double getNh3N() {
		return Nh3N;
	}
	public void setNh3N(double nh3n) {
		Nh3N = nh3n;
	}
	public double getNsum() {
		return Nsum;
	}
	public void setNsum(double nsum) {
		Nsum = nsum;
	}
	public double getPsum() {
		return Psum;
	}
	public void setPsum(double psum) {
		Psum = psum;
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
	public String getShowTitle() {
		// TODO Auto-generated method stub
		return "污水总量："+String.valueOf(getWszl());
	}
	@Override
	public String getShowDescribing() {
		// TODO Auto-generated method stub
		return getXzq().getName();
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("行政区域", getXzq().getName()));
			mFieldItems.add(new FieldItem("供水量",String.valueOf(getGsl())));
			mFieldItems.add(new FieldItem("城镇户籍非农业人口", String.valueOf(getCzhjfnrk())));
			mFieldItems.add(new FieldItem("农村户籍农业人口", String.valueOf(getNchjnyrk())));
			mFieldItems.add(new FieldItem("城镇暂住人口", String.valueOf(getCzzzrk())));
			mFieldItems.add(new FieldItem("农村暂住人口", String.valueOf(getNchjzzrk())));
			mFieldItems.add(new FieldItem("人口", String.valueOf(getRk())));
			mFieldItems.add(new FieldItem("污水总量",String.valueOf(getWszl())));
			mFieldItems.add(new FieldItem("污水处理率", String.valueOf(getWscll())));
			mFieldItems.add(new FieldItem("污水管道接管率", String.valueOf(getWsgdjgl())));
			mFieldItems.add(new FieldItem("COD", String.valueOf(getCod())));
			mFieldItems.add(new FieldItem("氨氮", String.valueOf(getNh3N())));
			mFieldItems.add(new FieldItem("TP", String.valueOf(getPsum())));
			mFieldItems.add(new FieldItem("TN", String.valueOf(getNsum())));
		}
		return mFieldItems;
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
	public Region getSsxz() {
		// TODO Auto-generated method stub
		return Xzq;
	}
}
