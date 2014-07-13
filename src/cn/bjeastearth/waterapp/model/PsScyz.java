package cn.bjeastearth.waterapp.model;

import java.util.List;

public class PsScyz {
	private double Bod;
	private String CTime;
	private double Clcz;
	private double Cod;
	private int ID;
	private List<ProjectImage> Images;
	private double Nh3N;
	private double PSum;
	private int Status;
	private double Symj;
	private double X;
	private double Y;
	public double getBod() {
		return Bod;
	}
	public void setBod(double bod) {
		Bod = bod;
	}
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public double getClcz() {
		return Clcz;
	}
	public void setClcz(double clcz) {
		Clcz = clcz;
	}
	public double getCod() {
		return Cod;
	}
	public void setCod(double cod) {
		Cod = cod;
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
	public double getNh3N() {
		return Nh3N;
	}
	public void setNh3N(double nh3n) {
		Nh3N = nh3n;
	}
	public double getPSum() {
		return PSum;
	}
	public void setPSum(double pSum) {
		PSum = pSum;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public double getSymj() {
		return Symj;
	}
	public void setSymj(double symj) {
		Symj = symj;
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
	public PsScyzType getYztype() {
		return Yztype;
	}
	public void setYztype(PsScyzType yztype) {
		Yztype = yztype;
	}
	private Region Xzq;
	private PsScyzType Yztype;
}
