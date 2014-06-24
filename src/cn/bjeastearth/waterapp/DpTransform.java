package cn.bjeastearth.waterapp;

import android.content.Context;

public class DpTransform {
public static int px2dip(Context con,float pxValue) {
	final float scale = con.getResources().getDisplayMetrics().density; 
    return (int)(pxValue / scale + 0.5f); 
}
}
