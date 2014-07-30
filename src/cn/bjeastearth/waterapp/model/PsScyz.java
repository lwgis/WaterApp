package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.waterapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class PsScyz implements PollutionSource{
	private double Bod;
	private String CTime;
	private double Clcz;
	private double Cod;
	private int ID;
	private List<ProjectImage> Images;
	private double Nh3N;
	private double PSum;
	private int Status;
	private double Symj;
	private Region Xzq;
	private PsScyzType Yztype;
	private double X;
	private double Y;
	private ArrayList<FieldItem> mFieldItems;
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
	public double getClcz() {
		return Clcz;
	}
	public void setClcz(double clcz) {
		Clcz = clcz;
	}
	public double getCod() {
		return Cod;
	}
	public void setCod(double cod) {
		Cod = cod;
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
	public double getSymj() {
		return Symj;
	}
	public void setSymj(double symj) {
		Symj = symj;
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
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
	}
	public PsScyzType getYztype() {
		return Yztype;
	}
	public void setYztype(PsScyzType yztype) {
		Yztype = yztype;
	}
	@Override
	public String getShowTitle() {
		// TODO Auto-generated method stub
		return getXzq().getName();
	}
	@Override
	public String getShowDescribing() {
		// TODO Auto-generated method stub
		return "水产类型："+ getYztype().getName();
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("行政区",getXzq().getName()));
			mFieldItems.add(new FieldItem("水产类型", getYztype().getName()));
			mFieldItems.add(new FieldItem("水域面积", String.valueOf(getSymj())));
			mFieldItems.add(new FieldItem("产量产值", String.valueOf(getClcz())));
			mFieldItems.add(new FieldItem("BOD",  String.valueOf(getBod())));
			mFieldItems.add(new FieldItem("COD",  String.valueOf(getBod())));
			mFieldItems.add(new FieldItem("氨氮",  String.valueOf(getNh3N())));
			mFieldItems.add(new FieldItem("总磷",  String.valueOf(getPSum())));
			mFieldItems.add(new FieldItem("时间", getCTime()));
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
		Drawable image = context.getResources().getDrawable(R.drawable.map_item_ny);
		return image;
	}
	@Override
	public String getPID() {
		// TODO Auto-generated method stub
		return "NYSCYZ"+getID();
	}
	@Override
	public PsType getPsType() {
		// TODO Auto-generated method stub
		return PsType.NY;
	}
}
