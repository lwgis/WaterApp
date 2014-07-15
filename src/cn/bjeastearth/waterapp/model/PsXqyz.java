package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.waterapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class PsXqyz  implements PollutionSource {
	private double Bod;
	private String CTime;
	private double Cod;
	private String Dwmc;
	private String Fzr;
	private Department GsDept;
	private Department HbDept;
	private int ID;
	private List<ProjectImage> Images;
	private double Ncz;
	private double Nh3N;
	private double PSum;
	private int Status;
	private double X;
	private double XqCount;
	private List<PollutionClass2> Xqwrwjls;
	private Region Xzq;
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
	public double getCod() {
		return Cod;
	}
	public void setCod(double cod) {
		Cod = cod;
	}
	public String getDwmc() {
		return Dwmc;
	}
	public void setDwmc(String dwmc) {
		Dwmc = dwmc;
	}
	public String getFzr() {
		return Fzr;
	}
	public void setFzr(String fzr) {
		Fzr = fzr;
	}
	public Department getGsDept() {
		return GsDept;
	}
	public void setGsDept(Department gsDept) {
		GsDept = gsDept;
	}
	public Department getHbDept() {
		return HbDept;
	}
	public void setHbDept(Department hbDept) {
		HbDept = hbDept;
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
	public double getNcz() {
		return Ncz;
	}
	public void setNcz(double ncz) {
		Ncz = ncz;
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
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public double getXqCount() {
		return XqCount;
	}
	public void setXqCount(double xqCount) {
		XqCount = xqCount;
	}
	public List<PollutionClass2> getXqwrwjls() {
		return Xqwrwjls;
	}
	public void setXqwrwjls(List<PollutionClass2> xqwrwjls) {
		Xqwrwjls = xqwrwjls;
	}
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	public double getZczj() {
		return Zczj;
	}
	public void setZczj(double zczj) {
		Zczj = zczj;
	}
	private double Zczj;
	@Override
	public String getShowTitle() {
		return getDwmc();
	}
	@Override
	public String getShowDescribing() {
		// TODO Auto-generated method stub
		return "畜禽数量："+String.valueOf(getXqCount());
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		ArrayList<FieldItem> fieldItems=new ArrayList<FieldItem>();
		fieldItems.add(new FieldItem("单位名称", getDwmc()));
		fieldItems.add(new FieldItem("畜禽数量", String.valueOf(getXqCount())));
		fieldItems.add(new FieldItem("行政区域", getXzq().getName()));
		fieldItems.add(new FieldItem("BOD", String.valueOf(getBod())));
		fieldItems.add(new FieldItem("COD", String.valueOf(getCod())));
		fieldItems.add(new FieldItem("氨氮", String.valueOf(getNh3N())));
		fieldItems.add(new FieldItem("总磷", String.valueOf(getPSum())));
		ArrayList<FieldItem> childFieldItems2=new ArrayList<FieldItem>();
		for (PollutionClass2 pollutionClass2 : getXqwrwjls()) {
			pollutionClass2.fillFieldItem(childFieldItems2);
		}
		fieldItems.add(new FieldItem("二类污染", childFieldItems2));
		fieldItems.add(new FieldItem("负责人", getFzr()));
		fieldItems.add(new FieldItem("年产值", String.valueOf(getNcz())));
		fieldItems.add(new FieldItem("注册资金",String.valueOf(getZczj())));
		fieldItems.add(new FieldItem("主管环保部门", getHbDept().getName()));
		fieldItems.add(new FieldItem("主管工商部门", getGsDept().getName()));
		return fieldItems;
	}
	@Override
	public String getImageString() {
		// TODO Auto-generated method stub
			if (getImages() != null && getImages().size() > 0) {
				ProjectImage projectImage = getImages().get(0);
				return projectImage.getName();
			}
			return null;
		}
	@Override
	public Drawable getMapDrawable(Context context) {
		Drawable image = context.getResources().getDrawable(R.drawable.map_item_ny);
		return image;
	}
	@Override
	public String getPID() {
		// TODO Auto-generated method stub
		return "NY"+getID();
	}
	@Override
	public PsType getPsType() {
		// TODO Auto-generated method stub
		return PsType.NY;
	}

}
