package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.waterapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class PsScyz implements PollutionSource,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6576139426014740698L;
	private double ABei;
	private double AQita;
	private double AXia;
	private double AYu;
	private double Bei;
	private double Cod;
	private String Contact;
	private String CTime;
	private String Fzr;
	private int ID;
	private List<ProjectImage>Images;
	private double Ncz;
	private double Nh3N;
	private double NSum;
	private double PSum;
	private double Qita;
	private Region Ssxz;
	private int Status;
	private double Xia;
	private double Yu;
	private double X;
	private double Y;
	private ArrayList<FieldItem> mFieldItems;
	
	public double getABei() {
		return ABei;
	}
	public void setABei(double aBei) {
		ABei = aBei;
	}
	public double getAQita() {
		return AQita;
	}
	public void setAQita(double aQita) {
		AQita = aQita;
	}
	public double getAXia() {
		return AXia;
	}
	public void setAXia(double aXia) {
		AXia = aXia;
	}
	public double getAYu() {
		return AYu;
	}
	public void setAYu(double aYu) {
		AYu = aYu;
	}
	public double getBei() {
		return Bei;
	}
	public void setBei(double bei) {
		Bei = bei;
	}
	public double getCod() {
		return Cod;
	}
	public void setCod(double cod) {
		Cod = cod;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public String getFzr() {
		return Fzr;
	}
	public void setFzr(String fzr) {
		Fzr = fzr;
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
	public double getNSum() {
		return NSum;
	}
	public void setNSum(double nSum) {
		NSum = nSum;
	}
	public double getPSum() {
		return PSum;
	}
	public void setPSum(double pSum) {
		PSum = pSum;
	}
	public double getQita() {
		return Qita;
	}
	public void setQita(double qita) {
		Qita = qita;
	}
	public Region getSsxz() {
		return Ssxz;
	}
	public void setSsxz(Region ssxz) {
		Ssxz = ssxz;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public double getXia() {
		return Xia;
	}
	public void setXia(double xia) {
		Xia = xia;
	}
	public double getYu() {
		return Yu;
	}
	public void setYu(double yu) {
		Yu = yu;
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
	@Override
	public String getShowTitle() {
		// TODO Auto-generated method stub
		return getFzr();
	}
	@Override
	public String getShowDescribing() {
		// TODO Auto-generated method stub
		return "所属乡镇："+ getSsxz().getName();
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("负责人",getFzr()));
			mFieldItems.add(new FieldItem("联系方式", getContact()));
			mFieldItems.add(new FieldItem("所属乡镇", getSsxz().getName()));
			mFieldItems.add(new FieldItem("年产值", String.valueOf(getNcz())));
			mFieldItems.add(new FieldItem("鱼类产量",  String.valueOf(getYu())));
			mFieldItems.add(new FieldItem("虾类产量",  String.valueOf(getXia())));
			mFieldItems.add(new FieldItem("贝类产量",  String.valueOf(getBei())));
			mFieldItems.add(new FieldItem("其他产量",  String.valueOf(getQita())));
			mFieldItems.add(new FieldItem("鱼类面积",  String.valueOf(getAYu())));
			mFieldItems.add(new FieldItem("虾类面积",  String.valueOf(getAXia())));
			mFieldItems.add(new FieldItem("贝类面积",  String.valueOf(getABei())));
			mFieldItems.add(new FieldItem("其他面积",  String.valueOf(getAQita())));
			mFieldItems.add(new FieldItem("COD",  String.valueOf(getCod())));
			mFieldItems.add(new FieldItem("氨氮",  String.valueOf(getNh3N())));
			mFieldItems.add(new FieldItem("TP",  String.valueOf(getPSum())));
			mFieldItems.add(new FieldItem("TN",  String.valueOf(getNSum())));
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
	@Override
	public Region getXzq() {
		// TODO Auto-generated method stub
		return Ssxz;
	}
}
