package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;

public class CountHProject {
     private String Name;
     private List<CountHProjectZhen> ZhenAll;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<CountHProjectZhen> getZhenAll() {
		return ZhenAll;
	}
	public void setZhenAll(List<CountHProjectZhen> zhenAll) {
		ZhenAll = zhenAll;
	}
	public ArrayList<CountHProjectYear> getCountHProjectYearsByYear(String year) {
		ArrayList<CountHProjectYear> countHProjectYears = new ArrayList<CountHProjectYear>();
		for (CountHProjectZhen countHProjectZhen : ZhenAll) {
			for (CountHProjectYear countHProjectYear : countHProjectZhen.getYears()) {
				if (countHProjectYear.getYearName().equals(year)) {
					countHProjectYear.setName(countHProjectZhen.getName());
					countHProjectYears.add(countHProjectYear);
				}
			}
		}
		return countHProjectYears;
	}

	public ArrayList<CountHProjectYear> getCountHProjectYearsByZhen(String zhenName) {
		ArrayList<CountHProjectYear> countHProjectYears = new ArrayList<CountHProjectYear>();
		for (CountHProjectZhen countHProjectZhen : ZhenAll) {
			if (countHProjectZhen.getName().equals(zhenName)) {
				for (CountHProjectYear countHProjectYear : countHProjectZhen.getYears()) {
					countHProjectYear.setName(countHProjectYear.getYearName());
					countHProjectYears.add(countHProjectYear);
				}
			}
		}
		return countHProjectYears;
	}
}
