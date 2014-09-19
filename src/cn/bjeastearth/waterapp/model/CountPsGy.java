package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.waterapp.model.CountPsGy.CountPsGyZhen.CountPsGyYear;

public class CountPsGy {
	private String Name;
	private List<CountPsGyZhen> ZhenAll;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List<CountPsGyZhen> getZhenAll() {
		return ZhenAll;
	}

	public void setZhenAll(List<CountPsGyZhen> zhenAll) {
		ZhenAll = zhenAll;
	}

	public ArrayList<CountPsGyYear> getCountPsGyYearsByYear(String year) {
		ArrayList<CountPsGyYear> countPsGyYears = new ArrayList<CountPsGy.CountPsGyZhen.CountPsGyYear>();
		for (CountPsGyZhen countPsGyZhen : ZhenAll) {
			for (CountPsGyYear countPsGyYear : countPsGyZhen.Years) {
				if (countPsGyYear.getYearName().equals(year)) {
					countPsGyYear.Name=countPsGyZhen.Name;
					countPsGyYears.add(countPsGyYear);
				}
			}
		}
		return countPsGyYears;
	}

	public ArrayList<CountPsGyYear> getCountPsGyYearsByZhen(String zhenName) {
		ArrayList<CountPsGyYear> countPsGyYears = new ArrayList<CountPsGy.CountPsGyZhen.CountPsGyYear>();
		for (CountPsGyZhen countPsGyZhen : ZhenAll) {
			if (countPsGyZhen.getName().equals(zhenName)) {
				for (CountPsGyYear countPsGyYear : countPsGyZhen.Years) {
					countPsGyYear.Name=countPsGyYear.YearName;
					countPsGyYears.add(countPsGyYear);
				}
			}
		}
		return countPsGyYears;
	}
	public ArrayList<String> getAllYears(){
		ArrayList<String> arrayList=new ArrayList<String>();
		for (CountPsGyZhen countPsGyZhen : ZhenAll) {
			for (CountPsGyYear countPsGyYear : countPsGyZhen.Years) {
				if (arrayList.contains(countPsGyYear.getYearName())) {
					arrayList.add(countPsGyYear.getYearName());
				}
			}
		}
		return arrayList;
	}
	public class CountPsGyZhen {
		private String Name;
		private List<CountPsGyYear> Years;

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public List<CountPsGyYear> getYears() {
			return Years;
		}

		public void setYears(List<CountPsGyYear> years) {
			Years = years;
		}

		public class CountPsGyYear {
			private double Cod_c;
			private double Cod_sum;
			private double Cod_z;
			private double Fspfl;
			private double NH3N_c;
			private double NH3N_sum;
			private double NH3N_z;
			private double PSum_c;
			private double PSum_sum;
			private double PSum_z;
			private double TN_c;
			private double TN_sum;
			private double TN_z;
			private String YearName;
			private String Name;
			public double getCod_c() {
				return Cod_c;
			}

			public void setCod_c(double cod_c) {
				Cod_c = cod_c;
			}

			public double getCod_sum() {
				return Cod_sum;
			}

			public void setCod_sum(double cod_sum) {
				Cod_sum = cod_sum;
			}

			public double getCod_z() {
				return Cod_z;
			}

			public void setCod_z(double cod_z) {
				Cod_z = cod_z;
			}

			public double getFspfl() {
				return Fspfl;
			}

			public void setFspfl(double fspfl) {
				Fspfl = fspfl;
			}

			public double getNH3N_c() {
				return NH3N_c;
			}

			public void setNH3N_c(double nH3N_c) {
				NH3N_c = nH3N_c;
			}

			public double getNH3N_sum() {
				return NH3N_sum;
			}

			public void setNH3N_sum(double nH3N_sum) {
				NH3N_sum = nH3N_sum;
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

			public double getPSum_sum() {
				return PSum_sum;
			}

			public void setPSum_sum(double pSum_sum) {
				PSum_sum = pSum_sum;
			}

			public double getPSum_z() {
				return PSum_z;
			}

			public void setPSum_z(double pSum_z) {
				PSum_z = pSum_z;
			}

			public double getTN_c() {
				return TN_c;
			}

			public void setTN_c(double tN_c) {
				TN_c = tN_c;
			}

			public double getTN_sum() {
				return TN_sum;
			}

			public void setTN_sum(double tN_sum) {
				TN_sum = tN_sum;
			}

			public double getTN_z() {
				return TN_z;
			}

			public void setTN_z(double tN_z) {
				TN_z = tN_z;
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
	}
}
