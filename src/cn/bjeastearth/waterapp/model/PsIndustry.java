package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PsIndustry implements Serializable,FieldItemable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7210753076400650517L;
	private int ID;
	private double Bod;
	private List<PollutionClass1> Class1jls;
	private List<PollutionClass2> Class2jls;
	private double Cod;
	private String Cylx;
	private String Fzr;
	private Department GsDept;
	private Department HbDept;
	private List<ProjectImage> Images;
	private double NH3N;
	private double Ncz;
	private double PSum;
	private String Qymc;
	private int Status;
	private PollutionType Wrwlx;
	private double X;
	private double Y;
	private double Zczj;
	private double Zjs;
	private Region Xzq;
	public double getBod() {
		return Bod;
	}
	public void setBod(int bod) {
		Bod = bod;
	}
	public List<PollutionClass1> getClass1jls() {
		return Class1jls;
	}
	public void setClass1jls(List<PollutionClass1> class1jls) {
		Class1jls = class1jls;
	}
	public List<PollutionClass2> getClass2jls() {
		return Class2jls;
	}
	public void setClass2jls(List<PollutionClass2> class2jls) {
		Class2jls = class2jls;
	}
	public double getCod() {
		return Cod;
	}
	public void setCod(int cod) {
		Cod = cod;
	}
	public String getCylx() {
		return Cylx;
	}
	public void setCylx(String cylx) {
		Cylx = cylx;
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
	public List<ProjectImage> getImages() {
		return Images;
	}
	public void setImages(List<ProjectImage> images) {
		Images = images;
	}
	public double getNH3N() {
		return NH3N;
	}
	public void setNH3N(int nH3N) {
		NH3N = nH3N;
	}
	public double getNcz() {
		return Ncz;
	}
	public void setNcz(int ncz) {
		Ncz = ncz;
	}
	public double getPSum() {
		return PSum;
	}
	public void setPSum(int pSum) {
		PSum = pSum;
	}
	public String getQymc() {
		return Qymc;
	}
	public void setQymc(String qymc) {
		Qymc = qymc;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public PollutionType getWrwlx() {
		return Wrwlx;
	}
	public void setWrwlx(PollutionType wrwlx) {
		Wrwlx = wrwlx;
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
	public double getZczj() {
		return Zczj;
	}
	public void setZczj(int zczj) {
		Zczj = zczj;
	}
	public double getZjs() {
		return Zjs;
	}
	public void setZjs(int zjs) {
		Zjs = zjs;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		// TODO Auto-generated method stub
		ArrayList<FieldItem> fieldItems=new ArrayList<FieldItem>();
		fieldItems.add(new FieldItem("企业名称", getQymc()));
		fieldItems.add(new FieldItem("产业类型",getCylx()));
		fieldItems.add(new FieldItem("污染物类型", getWrwlx().getName()));
		fieldItems.add(new FieldItem("BOD", String.valueOf(getBod())));
		fieldItems.add(new FieldItem("COD", String.valueOf(getCod())));
		fieldItems.add(new FieldItem("氨氮", String.valueOf(getNH3N())));
		fieldItems.add(new FieldItem("总磷", String.valueOf(getPSum())));
		fieldItems.add(new FieldItem("重金属", String.valueOf(getZjs())));
		ArrayList<FieldItem> childFieldItems1=new ArrayList<FieldItem>();
		ArrayList<FieldItem> childFieldItems2=new ArrayList<FieldItem>();
		for (PollutionClass1 pollutionClass1 : getClass1jls()) {
			pollutionClass1.fillFieldItem(childFieldItems1);
		}
		for (PollutionClass2 pollutionClass2 : getClass2jls()) {
			pollutionClass2.fillFieldItem(childFieldItems2);
		}
		fieldItems.add(new FieldItem("一类污染",childFieldItems1));
		fieldItems.add(new FieldItem("二类污染",childFieldItems2));
		fieldItems.add(new FieldItem("年产值",String.valueOf(getNcz())));
		fieldItems.add(new FieldItem("负责人",getFzr()));
		fieldItems.add(new FieldItem("注册资金",String.valueOf(getZczj())));
		fieldItems.add(new FieldItem("行政区域",getXzq().getName()));
		fieldItems.add(new FieldItem("主管环保部门",getHbDept().getName()));
		fieldItems.add(new FieldItem("主管工商部门",getGsDept().getName()));
		return fieldItems;
	}
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
	}
}
