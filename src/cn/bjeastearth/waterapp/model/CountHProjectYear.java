package cn.bjeastearth.waterapp.model;

public class CountHProjectYear {
	private String  Name;
	private int EndProCount;
	private int StartProCount ;
	private double TzjeSum;
	private String YearName;
	public int getEndProCount() {
		return EndProCount;
	}
	public void setEndProCount(int endProCount) {
		EndProCount = endProCount;
	}
	public int getStartProCount() {
		return StartProCount;
	}
	public void setStartProCount(int startProCount) {
		StartProCount = startProCount;
	}
	public double getTzjeSum() {
		return TzjeSum;
	}
	public void setTzjeSum(double tzjeSum) {
		TzjeSum = tzjeSum;
	}
	public String getYearName() {
		return YearName;
	}
	public void setYearName(String yearName) {
		YearName = yearName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}

}
