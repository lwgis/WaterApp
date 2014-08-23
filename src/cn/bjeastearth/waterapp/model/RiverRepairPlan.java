package cn.bjeastearth.waterapp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RiverRepairPlan implements FieldItemable{
	private Date ETime;
	private int ID;
	private String Fzr;
	private String Content;
	private double Wcjd;
	private int Status;
	private Department Zrdept;
	private RiverProject Xm;
	public Department getZrdept() {
		return Zrdept;
	}
	public void setZrdept(Department zrdept) {
		Zrdept = zrdept;
	}
	public RiverProject getXm() {
		return Xm;
	}
	public void setXm(RiverProject xm) {
		Xm = xm;
	}
	private ArrayList<FieldItem> mFieldItems;

	public Date getETime() {
		return ETime;
	}
	public void setETime(Date eTime) {
		ETime = eTime;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getFzr() {
		return Fzr;
	}
	public void setFzr(String fzr) {
		Fzr = fzr;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public double getWcjd() {
		return Wcjd;
	}
	public void setWcjd(double wcjd) {
		Wcjd = wcjd;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("", getXm().getName()));
			mFieldItems.add(new FieldItem("计划任务", getContent()));
			mFieldItems.add(new FieldItem("单位", getXm().getUnit()));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
			mFieldItems.add(new FieldItem("完成时间", df.format(getETime())));
			mFieldItems.add(new FieldItem("完成进度", String.valueOf(getWcjd())));
			mFieldItems.add(new FieldItem("责任单位", getZrdept().getName()));
			mFieldItems.add(new FieldItem("责任人", getFzr()));

		}
		return mFieldItems;
	}
	public void fillFieldItem(ArrayList<FieldItem> fieldItems) {
		for (FieldItem fieldItem : getFieldItems()) {
			fieldItems.add(fieldItem);
		}
	}
}
