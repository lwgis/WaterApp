package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bjeastearth.waterapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class PsZz  implements PollutionSource,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6000031680546659990L;
	private PsZzLevel Cd;
	private double Cod;
	private String CTime;
	private double Gymj;
	private double Hdmj;
	private double Nfyl;
	private double Nh3N;
	private double Nsum;
	private double Nycc;
	private double Nyyl;
	private double Psum;
	private int Status;
	private double Stmj;
	private double Symj;
	private Region Xzq;
	private int ID;
	private double X;
	private double Y;
	private int EditEnable;

	private ArrayList<FieldItem> mFieldItems;
	
	public PsZzLevel getCd() {
		return Cd;
	}
	public void setCd(PsZzLevel cd) {
		Cd = cd;
	}
	public double getCod() {
		return Cod;
	}
	public void setCod(double cod) {
		Cod = cod;
	}
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public double getGymj() {
		return Gymj;
	}
	public void setGymj(double gymj) {
		Gymj = gymj;
	}
	public double getHdmj() {
		return Hdmj;
	}
	public void setHdmj(double hdmj) {
		Hdmj = hdmj;
	}
	public double getNfyl() {
		return Nfyl;
	}
	public void setNfyl(double nfyl) {
		Nfyl = nfyl;
	}
	public double getNh3N() {
		return Nh3N;
	}
	public void setNh3N(double nh3n) {
		Nh3N = nh3n;
	}
	public double getNsum() {
		return Nsum;
	}
	public void setNsum(double nsum) {
		Nsum = nsum;
	}
	public double getNycc() {
		return Nycc;
	}
	public void setNycc(double nycc) {
		Nycc = nycc;
	}
	public double getNyyl() {
		return Nyyl;
	}
	public void setNyyl(double nyyl) {
		Nyyl = nyyl;
	}
	public double getPsum() {
		return Psum;
	}
	public void setPsum(double psum) {
		Psum = psum;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public double getStmj() {
		return Stmj;
	}
	public void setStmj(double stmj) {
		Stmj = stmj;
	}
	public double getSymj() {
		return Symj;
	}
	public void setSymj(double symj) {
		Symj = symj;
	}
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	@Override
	public String getShowTitle() {
		// TODO Auto-generated method stub
		return getXzq().getName();
	}
	@Override
	public String getShowDescribing() {
		// TODO Auto-generated method stub
		return "污染程度："+getCd().getName();
	}
	@Override
	public ArrayList<FieldItem> getFieldItems() {
		if (mFieldItems==null) {
			mFieldItems=new ArrayList<FieldItem>();
			mFieldItems.add(new FieldItem("行政区域", getXzq().getName()));
			mFieldItems.add(new FieldItem("污染程度", getCd().getName()));
			mFieldItems.add(new FieldItem("水田面积", String.valueOf(getStmj())));
			mFieldItems.add(new FieldItem("旱地面积", String.valueOf(getHdmj())));
			mFieldItems.add(new FieldItem("桑园面积", String.valueOf(getSymj())));
			mFieldItems.add(new FieldItem("果园面积", String.valueOf(getGymj())));
			mFieldItems.add(new FieldItem("农药用量", String.valueOf(getNyyl())));
			mFieldItems.add(new FieldItem("农肥用量", String.valueOf(getNfyl())));
			mFieldItems.add(new FieldItem("农业产出", String.valueOf(getNycc())));
			mFieldItems.add(new FieldItem("COD", String.valueOf(getCod())));
			mFieldItems.add(new FieldItem("氨氮", String.valueOf(getNh3N())));
			mFieldItems.add(new FieldItem("TP", String.valueOf(getPsum())));
			mFieldItems.add(new FieldItem("TN", String.valueOf(getNsum())));
		}
		return mFieldItems;
	}
	@Override
	public String getImageString() {
		return null;
	}
	@Override
	public Drawable getMapDrawable(Context context) {
		Drawable image = context.getResources().getDrawable(R.drawable.map_item_ny);
		return image;
	}
	@Override
	public String getPID() {
		// TODO Auto-generated method stub
		return "NYZZ"+getID();
	}
	@Override
	public PsType getPsType() {
		// TODO Auto-generated method stub
		return PsType.NY;
	}
	public Region getSsxz() {
		// TODO Auto-generated method stub
		return Xzq;
	}
	public int getEditEnable() {
		return EditEnable;
	}
	public void setEditEnable(int editEnable) {
		EditEnable = editEnable;
	}
}
