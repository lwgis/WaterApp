package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;



public class FieldItem implements Serializable {

private static final long serialVersionUID = 7630902320893226411L;
private String name;
private FieldItemType type;
private String content;
private ArrayList<FieldItem> childFieldItems;
private Object targe;
public FieldItem(String name,String content) {
	this.name = name;
	this.type = FieldItemType.TOWTEXT;
	this.content = content;
}
public FieldItem(String content) {
	this.type = FieldItemType.IMAGE;
	this.content = content;
}
public FieldItem(String name,ArrayList<FieldItem> fieldItems){
	childFieldItems=fieldItems;
	this.type=FieldItemType.PARENT;
	this.name=name;
}
public FieldItem(String name,Object targeObject){
	this.name=name;
	this.targe=targeObject;
	this.type=FieldItemType.TEXTBUTTON;
}
public String getName() {

	return name;
}
public void setName(String name) {
	this.name = name;
}
public FieldItemType getType() {
	return type;
}
public void setType(FieldItemType type) {
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
public Object getTarge() {
	return targe;
}
public void setTarge(Object targe) {
	this.targe = targe;
}
}
