package cn.bjeastearth.waterapp.model;

import java.util.List;

import android.R.integer;
import android.R.string;

public class HotProject {
private int ID;
private string Name;
private string StartTime;
private string EndTime;
private string Fzr;
private int Gcjd;
private HotProjectType Gclb;
private List<HotProjectImage> Images;
private string Jsdw;
private string Dzdw;
private int Status;
private double Tzje;
private ProjectRegion Xzq;
private double X;
private double Y;
public int getID() {
	return ID;
}
public void setID(int iD) {
	ID = iD;
}
public string getName() {
	return Name;
}
public void setName(string name) {
	Name = name;
}
public string getStartTime() {
	return StartTime;
}
public void setStartTime(string startTime) {
	StartTime = startTime;
}
public string getEndTime() {
	return EndTime;
}
public void setEndTime(string endTime) {
	EndTime = endTime;
}
public string getFzr() {
	return Fzr;
}
public void setFzr(string fzr) {
	Fzr = fzr;
}
public int getGcjd() {
	return Gcjd;
}
public void setGcjd(int gcjd) {
	Gcjd = gcjd;
}
public HotProjectType getGclb() {
	return Gclb;
}
public void setGclb(HotProjectType gclb) {
	Gclb = gclb;
}
public List<HotProjectImage> getImages() {
	return Images;
}
public void setImages(List<HotProjectImage> images) {
	Images = images;
}
public string getJsdw() {
	return Jsdw;
}
public void setJsdw(string jsdw) {
	Jsdw = jsdw;
}
public string getDzdw() {
	return Dzdw;
}
public void setDzdw(string dzdw) {
	Dzdw = dzdw;
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
public ProjectRegion getXzq() {
	return Xzq;
}
public void setXzq(ProjectRegion xzq) {
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
}
