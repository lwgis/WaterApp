package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;

public class SewageFactoryManager {
	private List<FactoryGywn> Gywnczds;
	private List<FactoryNcws> Ncwscls;
	private List<FactorySzws> Szwsclcs;
	private ArrayList<SewageFactory> sewageFactories;
	public List<FactoryGywn> getGywnczds() {
		return Gywnczds;
	}
	public void setGywnczds(List<FactoryGywn> gywnczds) {
		Gywnczds = gywnczds;
	}
	public List<FactoryNcws> getNcwscls() {
		return Ncwscls;
	}
	public void setNcwscls(List<FactoryNcws> ncwscls) {
		Ncwscls = ncwscls;
	}
	public List<FactorySzws> getSzwsclcs() {
		return Szwsclcs;
	}
	public void setSzwsclcs(List<FactorySzws> szwsclcs) {
		Szwsclcs = szwsclcs;
	}
	public ArrayList<SewageFactory> getSewageFactories() {
		if (sewageFactories==null) {
			sewageFactories=new ArrayList<SewageFactory>();			
		}
		sewageFactories.clear();
		if (Gywnczds!=null) {
			for (FactoryGywn factoryGywn : Gywnczds) {
				sewageFactories.add(factoryGywn);
			}
		}
		if (Ncwscls!=null) {
			for (FactoryNcws factoryNcws : Ncwscls) {
				sewageFactories.add(factoryNcws);
			}
		}
		if (Szwsclcs!=null) {
			for (FactorySzws factorySzws : Szwsclcs) {
				sewageFactories.add(factorySzws);
			}
		}
		return sewageFactories;
	}
	
}
