package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.R;



public class HotProject implements Serializable,FieldItemable {

private static final long serialVersionUID = -1619801501541078578L;
private int ID;
private String Name;
private String StartTime;
private String EndTime;
private String Fzr;
private int Gcjd;
private HotProjectType Gclb;
private List<ProjectImage> Images;
private String Jsdw;
private String Dzdw;
private int Status;
private String Tzdw;
private double Tzje;
private ProjectRegion Xzq;
private double X;
private double Y;
private ArrayList<FieldItem>mFieldItems;
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
public String getStartTime() {
	return StartTime;
}
public void setStartTime(String startTime) {
	StartTime = startTime;
}
public String getEndTime() {
	return EndTime;
}
public void setEndTime(String endTime) {
	EndTime = endTime;
}
public String getFzr() {
	return Fzr;
}
public void setFzr(String fzr) {
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
public List<ProjectImage> getImages() {
	return Images;
}
public void setImages(List<ProjectImage> images) {
	Images = images;
}
public String getJsdw() {
	return Jsdw;
}
public void setJsdw(String jsdw) {
	Jsdw = jsdw;
}
public String getDzdw() {
	return Dzdw;
}
public void setDzdw(String dzdw) {
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
public String getTzdw() {
	return Tzdw;
}
public void setTzdw(String tzdw) {
	Tzdw = tzdw;
}
@Override
public ArrayList<FieldItem> getFieldItems() {
	if (mFieldItems==null) {
		mFieldItems=new ArrayList<FieldItem>();
		mFieldItems.add(new FieldItem("工程名称", getName()));
		mFieldItems.add(new FieldItem("工程进度", getGcjd()+"%"));
		mFieldItems.add(new FieldItem("工程类型", getGclb().getName()));
		mFieldItems.add(new FieldItem("投资单位", getTzdw()));
		mFieldItems.add(new FieldItem("投资金额", String.valueOf(getTzje())));
		mFieldItems.add(new FieldItem("建设单位", getJsdw()));
		mFieldItems.add(new FieldItem("负责人", getFzr()));
		mFieldItems.add(new FieldItem("开始时间", getStartTime()));
		mFieldItems.add(new FieldItem("结束时间", getEndTime()));
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




}
