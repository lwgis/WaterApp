package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;


public class RiverWaterQuality implements FieldItemable,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9153649822128758131L;
	private int ID;
	private int Status;
	private double Bod;
	private double Cod;
	private double Do;
	private double Nh3N;
	private double NitreN;
	private double Nsum;
	private double Psum;
	private double Sd;
	private double Wd;
	private double Zd;	
	private String CTime;
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
	public double getBod() {
		return Bod;
	}
	public void setBod(double bod) {
		Bod = bod;
	}
	public double getCod() {
		return Cod;
	}
	public void setCod(double cod) {
		Cod = cod;
	}
	public double getDo() {
		return Do;
	}
	public void setDo(double do1) {
		Do = do1;
	}
	public double getNh3N() {
		return Nh3N;
	}
	public void setNh3N(double nh3n) {
		Nh3N = nh3n;
	}
	public double getNitreN() {
		return NitreN;
	}
	public void setNitreN(double nitreN) {
		NitreN = nitreN;
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
	public double getSd() {
		return Sd;
	}
	public void setSd(double sd) {
		Sd = sd;
	}
	public double getWd() {
		return Wd;
	}
	public void setWd(double wd) {
		Wd = wd;
	}
	public double getZd() {
		return Zd;
	}
	public void setZd(double zd) {
		Zd = zd;
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("", getCTime()));
			mFieldItems.add(new FieldItem("Bod", String.valueOf(Bod)));
			mFieldItems.add(new FieldItem("Cod", String.valueOf(Cod)));
			mFieldItems.add(new FieldItem("Do", String.valueOf(Do)));
			mFieldItems.add(new FieldItem("氨氮", String.valueOf(Nh3N)));
			mFieldItems.add(new FieldItem("硝氮", String.valueOf(NitreN)));
			mFieldItems.add(new FieldItem("TP", String.valueOf(Psum)));
			mFieldItems.add(new FieldItem("TN", String.valueOf(Nsum)));
			mFieldItems.add(new FieldItem("色度", String.valueOf(Sd)));
			mFieldItems.add(new FieldItem("温度", String.valueOf(Wd)));
			mFieldItems.add(new FieldItem("浊度", String.valueOf(Zd)));
		}
		return mFieldItems;
	}
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public void fillFieldItem(ArrayList<FieldItem> fieldItems) {
		for (FieldItem fieldItem : getFieldItems()) {
			fieldItems.add(fieldItem);
		}
	}
}
