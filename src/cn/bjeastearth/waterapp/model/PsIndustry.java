package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import android.content.Context;
import android.graphics.drawable.Drawable;
import cn.bjeastearth.waterapp.R;

public class PsIndustry implements  PollutionSource{

	/**
	 * 
	 */
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
	private ArrayList<FieldItem> mFieldItems;
	public double getBod() {
		return Bod;
	}
	public void setBod(double bod) {
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
	public void setCod(double cod) {
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
	public void setNH3N(double nH3N) {
		NH3N = nH3N;
	}
	public double getNcz() {
		return Ncz;
	}
	public void setNcz(double ncz) {
		Ncz = ncz;
	}
	public double getPSum() {
		return PSum;
	}
	public void setPSum(double pSum) {
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
	public void setZczj(double zczj) {
		Zczj = zczj;
	}
	public double getZjs() {
		return Zjs;
	}
	public void setZjs(double zjs) {
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
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("企业名称", getQymc()));
			mFieldItems.add(new FieldItem("产业类型",getCylx()));
			mFieldItems.add(new FieldItem("污染物类型", getWrwlx().getName()));
			mFieldItems.add(new FieldItem("BOD", String.valueOf(getBod())));
			mFieldItems.add(new FieldItem("COD", String.valueOf(getCod())));
			mFieldItems.add(new FieldItem("氨氮", String.valueOf(getNH3N())));
			mFieldItems.add(new FieldItem("总磷", String.valueOf(getPSum())));
			mFieldItems.add(new FieldItem("重金属", String.valueOf(getZjs())));
			ArrayList<FieldItem> childFieldItems1=new ArrayList<FieldItem>();
			ArrayList<FieldItem> childFieldItems2=new ArrayList<FieldItem>();
			for (PollutionClass1 pollutionClass1 : getClass1jls()) {
				pollutionClass1.fillFieldItem(childFieldItems1);
			}
			for (PollutionClass2 pollutionClass2 : getClass2jls()) {
				pollutionClass2.fillFieldItem(childFieldItems2);
			}
			mFieldItems.add(new FieldItem("一类污染",childFieldItems1));
			mFieldItems.add(new FieldItem("二类污染",childFieldItems2));
			mFieldItems.add(new FieldItem("年产值",String.valueOf(getNcz())));
			mFieldItems.add(new FieldItem("负责人",getFzr()));
			mFieldItems.add(new FieldItem("注册资金",String.valueOf(getZczj())));
			mFieldItems.add(new FieldItem("行政区域",getXzq().getName()));
			mFieldItems.add(new FieldItem("主管环保部门",getHbDept().getName()));
			mFieldItems.add(new FieldItem("主管工商部门",getGsDept().getName()));
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
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
	}
	@Override
	public String getShowTitle() {
		return Qymc;
	}
	@Override
	public String getShowDescribing() {
		return "产业类型："+ Cylx;
	}
	@Override
	public String getImageString() {
	
		if (getImages()!=null&&getImages().size()>0) {
			ProjectImage projectImage=getImages().get(0);
			return projectImage.getName();
		}
		return null;
	}
	@Override
	public Drawable getMapDrawable(Context context) {
		Drawable image = context.getResources().getDrawable(R.drawable.map_item);
		return image;
	}
	@Override
	public String getPID() {
		// TODO Auto-generated method stub
		return "GY"+getID();
	}
	@Override
	public PsType getPsType() {
		// TODO Auto-generated method stub
		return PsType.GY;
	}
}
