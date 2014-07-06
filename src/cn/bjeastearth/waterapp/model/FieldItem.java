package cn.bjeastearth.waterapp.model;

import java.io.Serializable;


public class FieldItem implements Serializable {

private static final long serialVersionUID = 7630902320893226411L;
private String name;
public FieldItem(String name,String content) {
	this.name = name;
	this.type = 0;
	this.content = content;
}
public FieldItem(String content) {
	this.type = 1;
	this.content = content;
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
}