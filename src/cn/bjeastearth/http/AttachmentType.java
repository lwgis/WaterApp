package cn.bjeastearth.http;

import java.io.Serializable;

import android.R.integer;


/**
 * 附件类型的对象实例
 * @author liuyan
 *
 */
public class AttachmentType implements Serializable {

	public AttachmentType(){}
	
	private long Id;
	
	private String Name;
	
	private int Status;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}
	
	
	
}
