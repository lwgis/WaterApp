package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;



public class CountRiver {
	private String Name;
	private List<CountRiverZhen> ZhenAll;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<CountRiverZhen> getZhenAll() {
		return ZhenAll;
	}
	public void setZhenAll(List<CountRiverZhen> zhenAll) {
		ZhenAll = zhenAll;
	}
	public ArrayList<CountRiverYear> getCountRiverYearsByYear(String year) {
		ArrayList<CountRiverYear> countRiverYears = new ArrayList<CountRiverYear>();
		for (CountRiverZhen countRiverZhen : ZhenAll) {
			for (CountRiverYear countRiverYear : countRiverZhen.getYears()) {
				if (countRiverYear.getYearName().equals(year)) {
					countRiverYear.setName(countRiverZhen.getName());
					countRiverYears.add(countRiverYear);
				}
			}
		}
		return countRiverYears;
	}

	public ArrayList<CountRiverYear> getCountRiverYearsByZhen(String zhenName) {
		ArrayList<CountRiverYear> countRiverYears = new ArrayList<CountRiverYear>();
		for (CountRiverZhen countRiverZhen : ZhenAll) {
			if (countRiverZhen.getName().equals(zhenName)) {
				for (CountRiverYear countRiverYear : countRiverZhen.getYears()) {
					countRiverYear.setName(countRiverYear.getYearName());
					countRiverYears.add(countRiverYear);
				}
			}
		}
		return countRiverYears;
	}
}
