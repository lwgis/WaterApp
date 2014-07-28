package cn.bjeastearth.waterapp.model;

import java.util.Date;

public class RiverRepairPlan {
	private Date EndTime;
	private int ID;
	private String ProName;
	private String Remark;
	private int Status;
	private String Task;
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

}
