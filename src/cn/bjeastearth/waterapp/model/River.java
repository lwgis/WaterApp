package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class River implements FieldItemable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8890344187438315799L;
	private RiverCategory Category;
	private String Code;
	private String Cjhz;
	private String Qjhz;
	private String Zjhz;
	private String EndPoint;
	private String EndTime;
	private RiverGrade Grade;
	private RiverGrade Hdsz;
	private List<PsIndustry> Gywrys;
	private int ID;
	private List<RiverImage> Images;
	private double Length;
	private String Name;
	private List<RiverRepairPlan> Hdzljls;
	private List<PsScyz> Scwrys;
	private List<PsLive> Shwss;
	private String StartPoint;
	private String StartTime;
	private double Width;
	private List<PsXqyz> Xqyzwrys;
	private Region Xzq;
	private List<PsZz> Zzwrys;
	private List<RiverWaterQuality> Szjls;
	private ArrayList<FieldItem> mFieldItems;
	private ArrayList<FieldItem> jbxxFieldItems;
	private ArrayList<FieldItem> wryFieldItems;
	private ArrayList<FieldItem> szjlFieldItems;
	private ArrayList<FieldItem> zljhFieldItems;
	private int EditEnable;

	public ArrayList<FieldItem> getJbxxFieldItems() {
		if (jbxxFieldItems == null) {
			jbxxFieldItems = new ArrayList<FieldItem>();
			jbxxFieldItems.add(new FieldItem("河道名", getName()));
			jbxxFieldItems.add(new FieldItem("河道类别", getCategory().getName()));
			if (getGrade() != null)
				jbxxFieldItems.add(new FieldItem("河道级别", getGrade().getName()));
			if (getHdsz()!=null) {
				jbxxFieldItems.add(new FieldItem("河道水质", getHdsz().getName()));
			}
			jbxxFieldItems.add(new FieldItem("河道编码", getCode()));
			jbxxFieldItems.add(new FieldItem("行政区域", getXzq().getName()));
			jbxxFieldItems.add(new FieldItem("开工时间", getStartTime()));
			jbxxFieldItems.add(new FieldItem("完工时间", getEndTime()));
			jbxxFieldItems.add(new FieldItem("起始点", getStartPoint()));
			jbxxFieldItems.add(new FieldItem("终止点", getEndPoint()));
			jbxxFieldItems
					.add(new FieldItem("长度", String.valueOf(getLength())));
			jbxxFieldItems.add(new FieldItem("宽度", String.valueOf(getWidth())));
			jbxxFieldItems
					.add(new FieldItem("区级河长", String.valueOf(getQjhz())));
			jbxxFieldItems
					.add(new FieldItem("镇级河长", String.valueOf(getZjhz())));
			jbxxFieldItems
					.add(new FieldItem("村级河长", String.valueOf(getCjhz())));
			if (getImages() != null && getImages().size() > 0) {
				ArrayList<FieldItem> imArrayList = new ArrayList<FieldItem>();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				for (RiverImage riverImage : getImages()) {
					imArrayList.add(new FieldItem(df.format(riverImage
							.getStartTime()), riverImage.getName()));
				}
				jbxxFieldItems.add(new FieldItem(imArrayList));
			}
		}
		return jbxxFieldItems;
	}

	public ArrayList<FieldItem> getWryFieldItems() {
		if (wryFieldItems == null) {
			wryFieldItems = new ArrayList<FieldItem>();
			// 工业
			if (getGywrys() != null && getGywrys().size() > 0) {
				ArrayList<FieldItem> gyArrayList = new ArrayList<FieldItem>();
				for (PsIndustry psIndustry : getGywrys()) {
					gyArrayList.add(new FieldItem(psIndustry.getShowTitle(),
							(Object) psIndustry.getFieldItems()));
				}
				wryFieldItems.add(new FieldItem("工业污染源", gyArrayList));
			}
			// 畜禽
			if (getXqyzwrys() != null && getXqyzwrys().size() > 0) {
				ArrayList<FieldItem> xqyzArrayList = new ArrayList<FieldItem>();
				for (PsXqyz psXqyz : getXqyzwrys()) {
					xqyzArrayList.add(new FieldItem(psXqyz.getShowTitle(),
							(Object) psXqyz.getFieldItems()));
				}
				wryFieldItems.add(new FieldItem("畜禽污染源", xqyzArrayList));
			}
			// 水产
			if (getScwrys() != null && getScwrys().size() > 0) {
				ArrayList<FieldItem> scArrayList = new ArrayList<FieldItem>();
				for (PsScyz psScyz : getScwrys()) {
					scArrayList.add(new FieldItem(psScyz.getShowTitle(),
							(Object) psScyz.getFieldItems()));
				}
				wryFieldItems.add(new FieldItem("水产污染源", scArrayList));
			}
			// 种植
			if (getZzwrys() != null && getZzwrys().size() > 0) {
				ArrayList<FieldItem> zzArrayList = new ArrayList<FieldItem>();
				for (PsZz psZz : getZzwrys()) {
					zzArrayList.add(new FieldItem(psZz.getShowTitle(),
							(Object) psZz.getFieldItems()));
				}
				wryFieldItems.add(new FieldItem("种植污染源", zzArrayList));
			}
			// 生活
			if (getShwss() != null && getScwrys().size() > 0) {
				ArrayList<FieldItem> shArrayList = new ArrayList<FieldItem>();
				for (PsLive psLive : getShwss()) {
					shArrayList.add(new FieldItem(psLive.getShowTitle(),
							(Object) psLive.getFieldItems()));
				}
				wryFieldItems.add(new FieldItem("生活污染源", shArrayList));
			}
		}
		return wryFieldItems;
	}

	public ArrayList<FieldItem> getSzjlFieldItems() {
		if (szjlFieldItems == null) {
			szjlFieldItems = new ArrayList<FieldItem>();
		}
		if (getSzjls() != null && getSzjls().size() > 0) {
			szjlFieldItems.clear();
			for (RiverWaterQuality riverWaterQuality : getSzjls()) {
				riverWaterQuality.fillFieldItem(szjlFieldItems);
			}
		}
		return szjlFieldItems;
	}

	public ArrayList<FieldItem> getZljhFieldItems() {
		if (zljhFieldItems == null) {
			zljhFieldItems = new ArrayList<FieldItem>();
		}
		if (getHdzljls() != null && getHdzljls().size() > 0) {
			zljhFieldItems.clear();
			for (RiverRepairPlan repairPlan : getHdzljls()) {
				repairPlan.fillFieldItem(zljhFieldItems);
			}
		}
		return zljhFieldItems;
	}

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

	public double getLength() {
		return Length;
	}

	public void setLength(double length) {
		Length = length;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
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
		if (mFieldItems == null) {
			mFieldItems = new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("河道名", getName()));
			mFieldItems.add(new FieldItem("河道类别", getCategory().getName()));
			mFieldItems.add(new FieldItem("河道级别", getGrade().getName()));
			mFieldItems.add(new FieldItem("河道编码", getCode()));
			mFieldItems.add(new FieldItem("行政区域", getXzq().getName()));
			mFieldItems.add(new FieldItem("开工时间", getStartTime()));
			mFieldItems.add(new FieldItem("完工时间", getEndTime()));
			mFieldItems.add(new FieldItem("起始点", getStartPoint()));
			mFieldItems.add(new FieldItem("终止点", getEndPoint()));
			mFieldItems.add(new FieldItem("长度", String.valueOf(getLength())));
			mFieldItems.add(new FieldItem("宽度", String.valueOf(getWidth())));
			mFieldItems.add(new FieldItem("区级河长", String.valueOf(getQjhz())));
			mFieldItems.add(new FieldItem("镇级河长", String.valueOf(getZjhz())));
			mFieldItems.add(new FieldItem("村级河长", String.valueOf(getCjhz())));
			// 工业
			if (getGywrys() != null && getGywrys().size() > 0) {
				ArrayList<FieldItem> gyArrayList = new ArrayList<FieldItem>();
				for (PsIndustry psIndustry : getGywrys()) {
					gyArrayList.add(new FieldItem(psIndustry.getShowTitle(),
							(Object) psIndustry.getFieldItems()));
				}
				mFieldItems.add(new FieldItem("工业污染源", gyArrayList));
			}
			// 畜禽
			if (getXqyzwrys() != null && getXqyzwrys().size() > 0) {
				ArrayList<FieldItem> xqyzArrayList = new ArrayList<FieldItem>();
				for (PsXqyz psXqyz : getXqyzwrys()) {
					xqyzArrayList.add(new FieldItem(psXqyz.getShowTitle(),
							(Object) psXqyz.getFieldItems()));
				}
				mFieldItems.add(new FieldItem("畜禽污染源", xqyzArrayList));
			}
			// 水产
			if (getScwrys() != null && getScwrys().size() > 0) {
				ArrayList<FieldItem> scArrayList = new ArrayList<FieldItem>();
				for (PsScyz psScyz : getScwrys()) {
					scArrayList.add(new FieldItem(psScyz.getShowTitle(),
							(Object) psScyz.getFieldItems()));
				}
				mFieldItems.add(new FieldItem("水产污染源", scArrayList));
			}
			// 种植
			if (getZzwrys() != null && getZzwrys().size() > 0) {
				ArrayList<FieldItem> zzArrayList = new ArrayList<FieldItem>();
				for (PsZz psZz : getZzwrys()) {
					zzArrayList.add(new FieldItem(psZz.getShowTitle(),
							(Object) psZz.getFieldItems()));
				}
				mFieldItems.add(new FieldItem("种植污染源", zzArrayList));
			}
			// 生活
			if (getShwss() != null && getScwrys().size() > 0) {
				ArrayList<FieldItem> shArrayList = new ArrayList<FieldItem>();
				for (PsLive psLive : getShwss()) {
					shArrayList.add(new FieldItem(psLive.getShowTitle(),
							(Object) psLive.getFieldItems()));
				}
				mFieldItems.add(new FieldItem("生活污染源", shArrayList));
			}
			// 整理计划
			if (getHdzljls() != null && getHdzljls().size() > 0) {
				ArrayList<FieldItem> rpArrayList = new ArrayList<FieldItem>();
				for (RiverRepairPlan repairPlan : getHdzljls()) {
					repairPlan.fillFieldItem(rpArrayList);
				}
				mFieldItems.add(new FieldItem("整治计划", rpArrayList));
			}
			if (getImages() != null && getImages().size() > 0) {
				ArrayList<FieldItem> imArrayList = new ArrayList<FieldItem>();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				for (RiverImage riverImage : getImages()) {
					imArrayList.add(new FieldItem(df.format(riverImage
							.getStartTime()), riverImage.getName()));
				}
				mFieldItems.add(new FieldItem(imArrayList));
			}
		}
		return mFieldItems;
	}

	public String getCjhz() {
		return Cjhz;
	}

	public void setCjhz(String cjhz) {
		Cjhz = cjhz;
	}

	public String getQjhz() {
		return Qjhz;
	}

	public void setQjhz(String qjhz) {
		Qjhz = qjhz;
	}

	public String getZjhz() {
		return Zjhz;
	}

	public void setZjhz(String zjhz) {
		Zjhz = zjhz;
	}

	public List<RiverWaterQuality> getSzjls() {
		return Szjls;
	}

	public void setSzjls(List<RiverWaterQuality> szjls) {
		Szjls = szjls;
	}

	public List<RiverRepairPlan> getHdzljls() {
		return Hdzljls;
	}

	public void setHdzljls(List<RiverRepairPlan> hdzljls) {
		Hdzljls = hdzljls;
	}

	/**
	 * 添加水质记录
	 */
	public void addSzjl(RiverWaterQuality riverWaterQuality) {
		ArrayList<RiverWaterQuality> temp = new ArrayList<RiverWaterQuality>(
				Szjls);
		if (Szjls == null) {
			Szjls = new ArrayList<RiverWaterQuality>();
		}
		Szjls.clear();
		Szjls.add(riverWaterQuality);
		for (RiverWaterQuality rq : temp) {
			Szjls.add(rq);
		}
	}

	public void addZljh(RiverRepairPlan riverRepairPlan) {
		ArrayList<RiverRepairPlan> temp = new ArrayList<RiverRepairPlan>(
				Hdzljls);
		if (Hdzljls == null) {
			Hdzljls = new ArrayList<RiverRepairPlan>();
		}
		Hdzljls.clear();
		Hdzljls.add(riverRepairPlan);
		for (RiverRepairPlan rp : temp) {
			Hdzljls.add(rp);
		}
	}

	public int getEditEnable() {
		return EditEnable;
	}

	public void setEditEnable(int editEnable) {
		EditEnable = editEnable;
	}

	public RiverGrade getHdsz() {
		return Hdsz;
	}

	public void setHdsz(RiverGrade hdsz) {
		Hdsz = hdsz;
	}
}
