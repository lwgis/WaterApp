package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.esri.core.internal.tasks.ags.s;


public class FieldItem implements Serializable {

private static final long serialVersionUID = 7630902320893226411L;
private String name;
private ArrayList<FieldItem> childFieldItems;
public FieldItem(String name,String content) {
	this.name = name;
	this.type = 0;
	this.content = content;
}
public FieldItem(String content) {
	this.type = 1;
	this.content = content;
}
public FieldItem(String name,ArrayList<FieldItem> fieldItems){
	childFieldItems=fieldItems;
	this.type=-1;
	this.name=name;
}
private int type;
private String content;
public String getName() {

	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public String getContent() {
	
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public ArrayList<FieldItem> getChildFieldItems() {
	return childFieldItems;
}
public void setChildFieldItems(ArrayList<FieldItem> childFieldItems) {
	this.childFieldItems = childFieldItems;
}
}
