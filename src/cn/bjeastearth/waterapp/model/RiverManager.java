package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;


public class RiverManager implements FieldItemable {
	private String Dw;
	private RiverManagerGrade Grade;
	private int ID;
	private String Name;
	private int Status;
	private String Zw;
	private ArrayList<FieldItem> mFieldItems;
	public String getDw() {
		return Dw;
	}
	public void setDw(String dw) {
		Dw = dw;
	}
	public RiverManagerGrade getGrade() {
		return Grade;
	}
	public void setGrade(RiverManagerGrade grade) {
		Grade = grade;
	}
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
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getZw() {
		return Zw;
	}
	public void setZw(String zw) {
		Zw = zw;
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("", getGrade().getName()));
			mFieldItems.add(new FieldItem("姓名", getName()));
			mFieldItems.add(new FieldItem("职务",getZw()));
			mFieldItems.add(new FieldItem("单位", getDw()));
		}
		// TODO Auto-generated method stub
		return mFieldItems;
	}
	public void fillFieldItem(ArrayList<FieldItem> fieldItems) {
		for (FieldItem fieldItem : getFieldItems()) {
			fieldItems.add(fieldItem);
		}
	}
}
