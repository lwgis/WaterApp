package cn.bjeastearth.waterapp.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface PollutionSource extends Serializable {
		String getPID();
		String getShowTitle();
		String getShowDescribing();
		ArrayList<FieldItem> getFieldItems();
		String getImageString();
		double getX();
		double getY();
		Drawable getMapDrawable(Context context);
		PsType getPsType();
		Region getXzq();
		int getEditEnable();
}
