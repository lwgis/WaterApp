package cn.bjeastearth.waterapp.model;

import java.util.List;

public class CountHProjectZhen {
	private String Name;
	private List<CountHProjectYear> Years;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<CountHProjectYear> getYears() {
		return Years;
	}
	public void setYears(List<CountHProjectYear> yeas) {
		Years = yeas;
	}
}
