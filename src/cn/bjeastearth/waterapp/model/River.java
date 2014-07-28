package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;

public class River implements FieldItemable {
	private RiverCategory Category;
	private String Code;
	private double Depth;
	private String EndPoint;
	private String EndTime;
	private RiverGrade Grade;
	private List<PsIndustry> Gywrys;
	private List<RiverManager> Hdhzs;
	private int ID;
	private List<RiverImage> Images;
	private double KMnO4;
	private double Length;
	private double NH3N;
	private String Name;
	private double PSUM;
	private List<RiverRepairPlan> Repairplans;
	private List<PsScyz> Scwrys;
	private List<PsLive> Shwss;
	private String StartPoint;
	private String StartTime;
	private int Status;
	private RiverWaterQuality Szdj;
	private double Width;
	private List<PsXqyz> Xqyzwrys;
	private Region Xzq;
	private List<PsZz> Zzwrys;
	private ArrayList<FieldItem> mFieldItems;
	public RiverCategory getCategory() {
		return Category;
	}
	public void setCategory(RiverCategory category) {
		Category = category;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public double getDepth() {
		return Depth;
	}
	public void setDepth(double depth) {
		Depth = depth;
	}
	public String getEndPoint() {
		return EndPoint;
	}
	public void setEndPoint(String endPoint) {
		EndPoint = endPoint;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public RiverGrade getGrade() {
		return Grade;
	}
	public void setGrade(RiverGrade grade) {
		Grade = grade;
	}
	public List<PsIndustry> getGywrys() {
		return Gywrys;
	}
	public void setGywrys(List<PsIndustry> gywrys) {
		Gywrys = gywrys;
	}
	public List<RiverManager> getHdhzs() {
		return Hdhzs;
	}
	public void setHdhzs(List<RiverManager> hdhzs) {
		Hdhzs = hdhzs;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public List<RiverImage> getImages() {
		return Images;
	}
	public void setImages(List<RiverImage> images) {
		Images = images;
	}
	public double getKMnO4() {
		return KMnO4;
	}
	public void setKMnO4(double kMnO4) {
		KMnO4 = kMnO4;
	}
	public double getLength() {
		return Length;
	}
	public void setLength(double length) {
		Length = length;
	}
	public double getNH3N() {
		return NH3N;
	}
	public void setNH3N(double nH3N) {
		NH3N = nH3N;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getPSUM() {
		return PSUM;
	}
	public void setPSUM(double pSUM) {
		PSUM = pSUM;
	}
	public List<RiverRepairPlan> getRepairplans() {
		return Repairplans;
	}
	public void setRepairplans(List<RiverRepairPlan> repairplans) {
		Repairplans = repairplans;
	}
	public List<PsScyz> getScwrys() {
		return Scwrys;
	}
	public void setScwrys(List<PsScyz> scwrys) {
		Scwrys = scwrys;
	}
	public List<PsLive> getShwss() {
		return Shwss;
	}
	public void setShwss(List<PsLive> shwss) {
		Shwss = shwss;
	}
	public String getStartPoint() {
		return StartPoint;
	}
	public void setStartPoint(String startPoint) {
		StartPoint = startPoint;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public RiverWaterQuality getSzdj() {
		return Szdj;
	}
	public void setSzdj(RiverWaterQuality szdj) {
		Szdj = szdj;
	}
	public double getWidth() {
		return Width;
	}
	public void setWidth(double width) {
		Width = width;
	}
	public List<PsXqyz> getXqyzwrys() {
		return Xqyzwrys;
	}
	public void setXqyzwrys(List<PsXqyz> xqyzwrys) {
		Xqyzwrys = xqyzwrys;
	}
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
	}
	public List<PsZz> getZzwrys() {
		return Zzwrys;
	}
	public void setZzwrys(List<PsZz> zzwrys) {
		Zzwrys = zzwrys;
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("河道名", getName()));
			if (getGywrys()!=null&&getGywrys().size()>0) {
				ArrayList<FieldItem> gyArrayList=new ArrayList<FieldItem>();
				for (PsIndustry psIndustry : getGywrys()) {
					gyArrayList.add(new FieldItem(psIndustry.getQymc(), (Object)psIndustry.getFieldItems()));
				}
				mFieldItems.add(new FieldItem("工业污染源", gyArrayList));
			}
		}
		return mFieldItems;
	}
	
}
