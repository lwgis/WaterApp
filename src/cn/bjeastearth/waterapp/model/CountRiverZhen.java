package cn.bjeastearth.waterapp.model;

import java.util.List;

public class CountRiverZhen {
	private String Name;
	private List<CountRiverYear> Years;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<CountRiverYear> getYears() {
		return Years;
	}
	public void setYears(List<CountRiverYear> years) {
		Years = years;
	}
}
