package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;
import java.util.List;

public interface SewageFactory {
	String getTitle();
	String getXzqName();
	List<ProjectImage> getImages();
	String getFID();
    ArrayList<FieldItem> getFieldItems();
    double getX();
    double getY();
    double getFgbj();
}
