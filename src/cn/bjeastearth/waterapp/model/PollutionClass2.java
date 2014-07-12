package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PollutionClass2 implements Serializable,FieldItemable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4682600322971705196L;
	private int ID;
	private String Jlvalue;
	private int Status;
	private Pollution2 Wrw;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getJlvalue() {
		return Jlvalue;
	}
	public void setJlvalue(String jlvalue) {
		Jlvalue = jlvalue;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public Pollution2 getWrw() {
		return Wrw;
	}
	public void setWrw(Pollution2 wrw) {
		Wrw = wrw;
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		// TODO Auto-generated method stub
		ArrayList<FieldItem> fieldItems=new ArrayList<FieldItem>();
		fieldItems.add(new FieldItem("", getWrw().getType().getName()));
		fieldItems.add(new FieldItem("适用范围", String.valueOf(getWrw().getSyfw())));
		fieldItems.add(new FieldItem("一级标准", getWrw().getBz1()));
		fieldItems.add(new FieldItem("二级标准", getWrw().getBz2()));
		fieldItems.add(new FieldItem("三级标准", getWrw().getBz3()));
		fieldItems.add(new FieldItem("实际浓度", String.valueOf(getJlvalue())));
		return fieldItems;
	}
	public void fillFieldItem(ArrayList<FieldItem> fieldItems) {
		for (FieldItem fieldItem : getFieldItems()) {
			fieldItems.add(fieldItem);
		}
	}
}
