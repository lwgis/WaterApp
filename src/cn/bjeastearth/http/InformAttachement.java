package cn.bjeastearth.http;

import java.io.Serializable;

/**
 * 群众举报的附件实例
 * @author liuyan
 *
 */
public class InformAttachement implements Serializable {

	public InformAttachement(){}
	
	private long ID;
	
	private Inform InForm;
	
	private AttachmentType Type;
	
	private String Name;
	
	private int Status;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public Inform getInForm() {
		return InForm;
	}

	public void setInForm(Inform inForm) {
		InForm = inForm;
	}

	public AttachmentType getType() {
		return Type;
	}

	public void setType(AttachmentType type) {
		Type = type;
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
