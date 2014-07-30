package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;


public class RootProject implements FieldItemable {

	/**
	 * 
	 */
private int ID;
private int Status;
private int Wcjd;
private String Title;
private String Description;
private String Gjdgzap;
private String Jqgzbs;
private String Remark;
private String WorkSum;
private ArrayList<FieldItem>mFieldItems;
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
public int getWcjd() {
	return Wcjd;
}
public void setWcjd(int wcjd) {
	Wcjd = wcjd;
}
public String getTitle() {
	return Title;
}
public void setTitle(String title) {
	Title = title;
}
public String getDescription() {
	return Description;
}
public void setDescription(String description) {
	Description = description;
}
public String getGjdgzap() {
	return Gjdgzap;
}
public void setGjdgzap(String gjdgzap) {
	Gjdgzap = gjdgzap;
}
public String getJqgzbs() {
	return Jqgzbs;
}
public void setJqgzbs(String jqgzbs) {
	Jqgzbs = jqgzbs;
}
public String getRemark() {
	return Remark;
}
public void setRemark(String remark) {
	Remark = remark;
}
public String getWorkSum() {
	return WorkSum;
}
public void setWorkSum(String workSum) {
	WorkSum = workSum;
}
@Override
public ArrayList<FieldItem> getFieldItems() {
	if (mFieldItems==null) {
		mFieldItems=new ArrayList<FieldItem>();
		mFieldItems.add(new FieldItem("标题", getTitle()));
		mFieldItems.add(new FieldItem("工作概述",getDescription()));
		mFieldItems.add(new FieldItem("工作总量", getWorkSum()));
		mFieldItems.add(new FieldItem("完成进度", String.valueOf(getWcjd())));
		mFieldItems.add(new FieldItem("近期工作部署", getJqgzbs()));
		mFieldItems.add(new FieldItem("阶段工作安排", getGjdgzap()));
		mFieldItems.add(new FieldItem("备注", getRemark()));
	}
	return mFieldItems;
}

}
