package cn.bjeastearth.waterapp.model;

public class WaterNew {
	private int ID;
	private String Title;
	private String Content;
	private String Description;
	private String Thumbnail;
	private Department Dept;
	private String NewsTime;

public int getID() {
	return ID;
}

public void setID(int iD) {
	ID = iD;
}

public String getTitle() {
	return Title;
}

public void setTitle(String title) {
	this.Title = title;
}


public String getDescription() {
	return Description;
}

public void setDescription(String description) {
	Description = description;
}

public String getThumbnail() {
	return Thumbnail;
}

public void setThumbnail(String thumbnail) {
	Thumbnail = thumbnail;
}

@Override
public String toString() {
	// TODO Auto-generated method stub
	return this.Dept.getName();
}

public Department getDept() {
	return Dept;
}

public void setDept(Department dept) {
	this.Dept = dept;
}

public String getContent() {
	return Content;
}

public void setContent(String content) {
	Content = content;
}

public String getNewsTime() {
	return NewsTime;
}

public void setNewsTime(String newsTime) {
	NewsTime = newsTime;
}
}

