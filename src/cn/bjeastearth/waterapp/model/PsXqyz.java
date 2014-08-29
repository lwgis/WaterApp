package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.waterapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class PsXqyz  implements PollutionSource,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1805797267739080131L;
	private double Cod;
	private String Contact;
	private String CTime;
	private double Fspfl;
	private String Fzr;
	private int ID;
	private List<ProjectImage>Images;
	private int JQCount;
	private double Ncz;
	private double Nh3N;
	private int NiuCount;
	private double NSum;
	private double PSum;
	private String Qymc;
	private String Sfdbpf;
	private Region Ssxz;
	private int Status;
	private int TuCount;
	private String Wsclss;
	private double X;
	private double Y;
	private int YangCount;
	private double Ysl;
	private int ZhuCount;
	private ArrayList<FieldItem> mFieldItems;

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
	public double getFspfl() {
		return Fspfl;
	}
	public void setFspfl(double fspfl) {
		Fspfl = fspfl;
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
	public int getJQCount() {
		return JQCount;
	}
	public void setJQCount(int jQCount) {
		JQCount = jQCount;
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
	public int getNiuCount() {
		return NiuCount;
	}
	public void setNiuCount(int niuCount) {
		NiuCount = niuCount;
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
	public String getQymc() {
		return Qymc;
	}
	public void setQymc(String qymc) {
		Qymc = qymc;
	}
	public String getSfdbpf() {
		return Sfdbpf;
	}
	public void setSfdbpf(String sfdbpf) {
		Sfdbpf = sfdbpf;
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
	public int getTuCount() {
		return TuCount;
	}
	public void setTuCount(int tuCount) {
		TuCount = tuCount;
	}
	public String getWsclss() {
		return Wsclss;
	}
	public void setWsclss(String wsclss) {
		Wsclss = wsclss;
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
	public int getYangCount() {
		return YangCount;
	}
	public void setYangCount(int yangCount) {
		YangCount = yangCount;
	}
	public double getYsl() {
		return Ysl;
	}
	public void setYsl(double ysl) {
		Ysl = ysl;
	}
	public int getZhuCount() {
		return ZhuCount;
	}
	public void setZhuCount(int zhuCount) {
		ZhuCount = zhuCount;
	}
	@Override
	public String getShowTitle() {
		// TODO Auto-generated method stub
		return getQymc();
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
			mFieldItems.add(new FieldItem("企业名称",getQymc()));
			mFieldItems.add(new FieldItem("负责人", getFzr()));
			mFieldItems.add(new FieldItem("联系方式", getContact()));
			mFieldItems.add(new FieldItem("所属乡镇", getSsxz().getName()));
			mFieldItems.add(new FieldItem("年产值",  String.valueOf(getNcz())));
			mFieldItems.add(new FieldItem("猪数量",  String.valueOf(getZhuCount())));
			mFieldItems.add(new FieldItem("牛数量",  String.valueOf(getNiuCount())));
			mFieldItems.add(new FieldItem("羊数量",  String.valueOf(getYangCount())));
			mFieldItems.add(new FieldItem("家禽数量",  String.valueOf(getJQCount())));
			mFieldItems.add(new FieldItem("兔数量",  String.valueOf(getTuCount())));
			mFieldItems.add(new FieldItem("用水量",  String.valueOf(getYsl())));
			mFieldItems.add(new FieldItem("废水排放量",  String.valueOf(getFspfl())));
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
		return "NYXQYZ"+getID();
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
