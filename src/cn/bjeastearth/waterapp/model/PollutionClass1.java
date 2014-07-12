package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PollutionClass1 implements Serializable,FieldItemable {
/**
	 * 
	 */
	private static final long serialVersionUID = -1481365968494800411L;
	private int ID;
	private double Jlvalue;
	private int Status;
	private Pollution1 Wrw;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public double getJlvalue() {
		return Jlvalue;
	}
	public void setJlvalue(double jlvalue) {
		Jlvalue = jlvalue;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public Pollution1 getWrw() {
		return Wrw;
	}
	public void setWrw(Pollution1 wrw) {
		Wrw = wrw;
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		// TODO Auto-generated method stub
		ArrayList<FieldItem> fieldItems=new ArrayList<FieldItem>();
		fieldItems.add(new FieldItem("", getWrw().getWrw()));
		fieldItems.add(new FieldItem("最高浓度", String.valueOf(getWrw().getBznd())));
		fieldItems.add(new FieldItem("实际浓度", String.valueOf(getJlvalue())));
		return fieldItems;
	}
	public void fillFieldItem(ArrayList<FieldItem> fieldItems) {
		for (FieldItem fieldItem : getFieldItems()) {
			fieldItems.add(fieldItem);
		}
	}

}
