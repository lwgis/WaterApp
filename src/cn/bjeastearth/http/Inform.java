package cn.bjeastearth.http;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.bjeastearth.waterapp.model.Department;

/**
 * 群众举报实例
 * @author liuyan
 *
 */
public class Inform implements Serializable {

	public Inform(){}
	
	private long ID;
	
	private String Name;
	
	private String Address;
	
	private Date InformTime;
	
	private String Content;
	
	private String Email;
	
	private Department Dept;
	
	private long Status;
	
	private List<InformAttachement> Images;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public Date getInformTime() {
		return InformTime;
	}

	public void setInformTime(Date informTime) {
		InformTime = informTime;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Department getDept() {
		return Dept;
	}

	public void setDept(Department dept) {
		Dept = dept;
	}

	public long getStatus() {
		return Status;
	}

	public void setStatus(long status) {
		Status = status;
	}

	public List<InformAttachement> getImages() {
		return Images;
	}

	public void setImages(List<InformAttachement> images) {
		Images = images;
	}
	
	
	
}
