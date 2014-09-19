package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import cn.bjeastearth.waterapp.R;

public class PsIndustry implements  PollutionSource,Serializable{
	private static final long serialVersionUID = -8927124004775827021L;
	/**
	 * 
	 */
	private int ID;
	private double Cod_c;
	private double Cod_z;
	private String Contact;
	private String CTime;
	private String Fsclss;
	private String Fzr;
	private double Gyfspfl_c;
	private double Gyfspfl_z;
	private double Gyysl;
	private List<ProjectImage> Images;
	private double Ncz;
	private double NH3N_c;
	private double NH3N_z;
	private double PSum_c;
	private double PSum_z;
	private String Qymc;
	private String Sfdb;
	private Region Ssxz;
	private int Status;
	private double TN_c;
	private double TN_z;
	private double X;
	private double Y;
	private int EditEnable;
	private ArrayList<FieldItem>mFieldItems;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public double getCod_c() {
		return Cod_c;
	}
	public void setCod_c(double cod_c) {
		Cod_c = cod_c;
	}
	public double getCod_z() {
		return Cod_z;
	}
	public void setCod_z(double cod_z) {
		Cod_z = cod_z;
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
	public String getFsclss() {
		return Fsclss;
	}
	public void setFsclss(String fsclss) {
		Fsclss = fsclss;
	}
	public String getFzr() {
		return Fzr;
	}
	public void setFzr(String fzr) {
		Fzr = fzr;
	}
	public double getGyfspfl_c() {
		return Gyfspfl_c;
	}
	public void setGyfspfl_c(double gyfspfl_c) {
		Gyfspfl_c = gyfspfl_c;
	}
	public double getGyfspfl_z() {
		return Gyfspfl_z;
	}
	public void setGyfspfl_z(double gyfspfl_z) {
		Gyfspfl_z = gyfspfl_z;
	}
	public double getGyysl() {
		return Gyysl;
	}
	public void setGyysl(double gyysl) {
		Gyysl = gyysl;
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
	public double getNH3N_c() {
		return NH3N_c;
	}
	public void setNH3N_c(double nH3N_c) {
		NH3N_c = nH3N_c;
	}
	public double getNH3N_z() {
		return NH3N_z;
	}
	public void setNH3N_z(double nH3N_z) {
		NH3N_z = nH3N_z;
	}
	public double getPSum_c() {
		return PSum_c;
	}
	public void setPSum_c(double pSum_c) {
		PSum_c = pSum_c;
	}
	public double getPSum_z() {
		return PSum_z;
	}
	public void setPSum_z(double pSum_z) {
		PSum_z = pSum_z;
	}
	public String getQymc() {
		return Qymc;
	}
	public void setQymc(String qymc) {
		Qymc = qymc;
	}
	public String getSfdb() {
		return Sfdb;
	}
	public void setSfdb(String sfdb) {
		Sfdb = sfdb;
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
	public double getTN_c() {
		return TN_c;
	}
	public void setTN_c(double tN_c) {
		TN_c = tN_c;
	}
	public double getTN_z() {
		return TN_z;
	}
	public void setTN_z(double tN_z) {
		TN_z = tN_z;
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
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("企业名称", getQymc()));
			mFieldItems.add(new FieldItem("负责人",getFzr()));
			mFieldItems.add(new FieldItem("联系方式",getContact()));
			mFieldItems.add(new FieldItem("行政区域",getSsxz().getName()));
			mFieldItems.add(new FieldItem("年产值",String.valueOf(getNcz())));
			mFieldItems.add(new FieldItem("工业用水量", String.valueOf(getGyysl())));
			mFieldItems.add(new FieldItem("工业废水排放量(直排)", String.valueOf(getGyfspfl_z())));
			mFieldItems.add(new FieldItem("工业废水排放量(处理)", String.valueOf(getGyfspfl_c())));
			mFieldItems.add(new FieldItem("COD排放量(直排)", String.valueOf(getCod_z())));
			mFieldItems.add(new FieldItem("COD排放量(处理)", String.valueOf(getCod_c())));
			mFieldItems.add(new FieldItem("氨氮排放量(直排)", String.valueOf(getNH3N_z())));
			mFieldItems.add(new FieldItem("氨氮排放量(处理)", String.valueOf(getNH3N_c())));
			mFieldItems.add(new FieldItem("TP(直排)", String.valueOf(getPSum_z())));
			mFieldItems.add(new FieldItem("TP(处理)", String.valueOf(getPSum_c())));
			mFieldItems.add(new FieldItem("TN(直排)", String.valueOf(getTN_z())));
			mFieldItems.add(new FieldItem("TN(处理)", String.valueOf(getTN_c())));
			mFieldItems.add(new FieldItem("是否有污水处理设施", getFsclss()));
			mFieldItems.add(new FieldItem("是否达标排放", getSfdb()));

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
	public String getShowTitle() {
		return Qymc;
	}
	@Override
	public String getShowDescribing() {
		return "所属乡镇："+ getSsxz().getName();
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
		Drawable image = context.getResources().getDrawable(R.drawable.map_item_gy);
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
	@Override
	public Region getXzq() {
		// TODO Auto-generated method stub
		return Ssxz;
	}
	public int getEditEnable() {
		return EditEnable;
	}
	public void setEditEnable(int editEnable) {
		EditEnable = editEnable;
	}
}
