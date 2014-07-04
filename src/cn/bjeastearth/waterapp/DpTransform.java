package cn.bjeastearth.waterapp;

import android.content.Context;

public class DpTransform {
public static int px2dip(Context con,float pxValue) {
	final float scale = con.getResources().getDisplayMetrics().density; 
    return (int)(pxValue / scale + 0.5f); 
}
public static int dip2px(Context context, float dipValue){ 
    final float scale = context.getResources().getDisplayMetrics().density; 
    return (int)(dipValue * scale + 0.5f); 
} 
}
