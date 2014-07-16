package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;

import cn.bjeastearth.waterapp.PollutionActivity;
import cn.bjeastearth.waterapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class PsZz  implements PollutionSource {
	private String CTime;
	private PsZzLevel	Cd;
	private double	Gdmj;
	private int	 ID;
	private double	Nfyl;
	private double	Nycc;
	private double	Nyyl;
	private int	 Status;
	private double	X;
	private Region	Xzq;
	private double	Y;
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public PsZzLevel getCd() {
		return Cd;
	}
	public void setCd(PsZzLevel cd) {
		Cd = cd;
	}
	public double getGdmj() {
		return Gdmj;
	}
	public void setGdmj(double gdmj) {
		Gdmj = gdmj;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public double getNfyl() {
		return Nfyl;
	}
	public void setNfyl(double nfyl) {
		Nfyl = nfyl;
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
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public Region getXzq() {
		return Xzq;
	}
	public void setXzq(Region xzq) {
		Xzq = xzq;
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
		ArrayList<FieldItem> fieldItems=new ArrayList<FieldItem>();
		fieldItems.add(new FieldItem("行政区域", getXzq().getName()));
		fieldItems.add(new FieldItem("耕地面积", String.valueOf(getGdmj())));
		fieldItems.add(new FieldItem("农药用量", String.valueOf(getNyyl())));
		fieldItems.add(new FieldItem("农肥用量", String.valueOf(getNfyl())));
		fieldItems.add(new FieldItem("农业产出", String.valueOf(getNycc())));
		fieldItems.add(new FieldItem("污染程度", getCd().getName()));
		return fieldItems;
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
}
