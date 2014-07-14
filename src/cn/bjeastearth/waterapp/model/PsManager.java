package cn.bjeastearth.waterapp.model;

import java.util.List;

public class PsManager {
 private List<PsIndustry> Gys;
 private PsFarmingManager NySource;
 private List<PsLive> Shs;
public List<PsIndustry> getGys() {
	return Gys;
}
public void setGys(List<PsIndustry> gys) {
	Gys = gys;
}
public PsFarmingManager getNySource() {
	return NySource;
}
public void setNySource(PsFarmingManager nySource) {
	NySource = nySource;
}
public List<PsLive> getShs() {
	return Shs;
}
public void setShs(List<PsLive> shs) {
	Shs = shs;
}
}
