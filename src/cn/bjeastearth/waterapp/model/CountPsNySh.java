package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.waterapp.model.CountPsNySh.CountNyShZhen.CountNyShYear;

public class CountPsNySh {
	private String Name;
	private List<CountNyShZhen> ZhenAll; 
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<CountNyShZhen> getZhenAll() {
		return ZhenAll;
	}
	public void setZhenAll(List<CountNyShZhen> zhenAll) {
		ZhenAll = zhenAll;
	}
	public ArrayList<CountNyShYear> getCountNyShYearsByYear(String year) {
		ArrayList<CountNyShYear> countNyShYears = new ArrayList<CountNyShYear>();
		for (CountNyShZhen countNyShZhen : ZhenAll) {
			for (CountNyShYear countNyShYear : countNyShZhen.Years) {
				if (countNyShYear.getYearName().equals(year)) {
					countNyShYear.Name=countNyShZhen.Name;
					countNyShYears.add(countNyShYear);
				}
			}
		}
		return countNyShYears;
	}

	public ArrayList<CountNyShYear> getCountNyShYearsByZhen(String zhenName) {
		ArrayList<CountNyShYear> countNyShYears = new ArrayList<CountNyShYear>();
		for (CountNyShZhen countNyShZhen : ZhenAll) {
			if (countNyShZhen.getName().equals(zhenName)) {
				for (CountNyShYear countNyShYear : countNyShZhen.Years) {
					countNyShYear.Name=countNyShYear.YearName;
					countNyShYears.add(countNyShYear);
				}
			}
		}
		return countNyShYears;
	}
	public class CountNyShZhen{
		private String Name;
		private List<CountNyShYear> Years;
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public List<CountNyShYear> getYears() {
			return Years;
		}
		public void setYears(List<CountNyShYear> years) {
			Years = years;
		}
		public class CountNyShYear{
			private String Name;
			private double Cod_sum;
			private double Fspfl;
			private double NH3N_sum;
			private double PSum_sum;
			private double TN_sum;
			private String YearName;
			public String getName() {
				return Name;
			}
			public void setName(String name) {
				Name = name;
			}
			public double getCod_sum() {
				return Cod_sum;
			}
			public void setCod_sum(double cod_sum) {
				Cod_sum = cod_sum;
			}
			public double getFspfl() {
				return Fspfl;
			}
			public void setFspfl(double fspfl) {
				Fspfl = fspfl;
			}
			public double getNH3N_sum() {
				return NH3N_sum;
			}
			public void setNH3N_sum(double nH3N_sum) {
				NH3N_sum = nH3N_sum;
			}
			public double getPSum_sum() {
				return PSum_sum;
			}
			public void setPSum_sum(double pSum_sum) {
				PSum_sum = pSum_sum;
			}
			public double getTN_sum() {
				return TN_sum;
			}
			public void setTN_sum(double tN_sum) {
				TN_sum = tN_sum;
			}
			public String getYearName() {
				return YearName;
			}
			public void setYearName(String yearName) {
				YearName = yearName;
			}
		}
	}
}
