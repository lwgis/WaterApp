package cn.bjeastearth.waterapp.model;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface PollutionSource {
		int getID();
		String getShowTitle();
		String getShowDescribing();
		ArrayList<FieldItem> getFieldItems();
		String getImageString();
		double getX();
		double getY();
		Drawable getMapDrawable(Context context);
}
