package cn.bjeastearth.waterapp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RiverRepairPlan implements FieldItemable{
	private Date EndTime;
	private int ID;
	private String ProName;
	private String Remark;
	private int Status;
	private String Task;
	private ArrayList<FieldItem> mFieldItems;
	public Date getEndTime() {
		return EndTime;
	}
	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getProName() {
		return ProName;
	}
	public void setProName(String proName) {
		ProName = proName;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getTask() {
		return Task;
	}
	public void setTask(String task) {
		Task = task;
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("", getProName()));
			mFieldItems.add(new FieldItem("计划任务", getTask()));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			mFieldItems.add(new FieldItem("完成时间", df.format(getEndTime())));
			mFieldItems.add(new FieldItem("备注", getRemark()));
		}
		return mFieldItems;
	}
	public void fillFieldItem(ArrayList<FieldItem> fieldItems) {
		for (FieldItem fieldItem : getFieldItems()) {
			fieldItems.add(fieldItem);
		}
	}
}
