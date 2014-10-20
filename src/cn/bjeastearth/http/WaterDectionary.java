package cn.bjeastearth.http;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.ArrayAdapter;
import cn.bjeastearth.waterapp.AddPsZzActivity;
import cn.bjeastearth.waterapp.R;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.PsZzLevel;
import cn.bjeastearth.waterapp.model.Region;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WaterDectionary {
	private static List<Region> regions;
	private static List<PsZzLevel> psZzLevels;
	private static List<Department> departments;
	public static Context context;
	public static List<Region> getRegions() {
		return regions;
	}
	/**
	 * 通过Region  ID找到索引位置
	 * @param id
	 * @return
	 */
	public static int findRegionIndex(int id) {
		for (int i = 0; i < getRegions().size(); i++) {
			Region region=getRegions().get(i);
			if (region.getID()==id) {
				return i;
			}
		}
		return 0;
	}
	/**
	 * 通过名称找到RegioID
	 * @param id
	 * @return
	 */
	public static int findRegionIndex(String name) {
		for (int i = 0; i < getRegions().size(); i++) {
			Region region=getRegions().get(i);
			if (region.getName().equals(name)) {
				return region.getID();
			}
		}
		return 0;
	}

	public static void config() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Gson gson=new Gson();
				regions= gson.fromJson(HttpUtil.getDectionaryString("Xzq"),
							new TypeToken<List<Region>>() {
							}.getType());
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Gson gson=new Gson();
				psZzLevels = gson.fromJson(HttpUtil.getDectionaryString("Wrcd"),
						new TypeToken<List<PsZzLevel>>() {
						}.getType());
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Gson gson=new Gson();
				departments=(gson.fromJson(HttpUtil.getDectionaryString("Dept"),
						new TypeToken<List<Department>>() {
						}.getType()));
			}
		}).start();
	}
	public static List<PsZzLevel> getPsZzLevels() {
		return psZzLevels;
	}
	public static void setPsZzLevels(List<PsZzLevel> psZzLevels) {
		WaterDectionary.psZzLevels = psZzLevels;
	}
	public static int findWrcdIndex(int id) {
		for (int i = 0; i < getPsZzLevels().size(); i++) {
			PsZzLevel psZzLevel=getPsZzLevels().get(i);
			if (psZzLevel.getID()==id) {
				return i;
			}
		}
		return 0;
	}
	public static List<Department> getDepartments() {
		return departments;
	}
	public static void setDepartments(List<Department> departments) {
		WaterDectionary.departments = departments;
	}	
	 public static void saveLoginInfo(String username,String userid,String department,String xzq){
	        //获取SharedPreferences对象
	        SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
	        //获取Editor对象
	        Editor editor=sharedPre.edit();
	        //设置参数
	        editor.putString("username", username);
	        editor.putString("userid", userid);
	        editor.putString("department", department);
	        editor.putString("xzq", xzq);
	        //提交
	        editor.commit();
	    }
	 public static int getUserId() {
		 SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
	        String userid=sharedPre.getString("userid", "");
	        if (!userid.equals("")) {
		        return Integer.valueOf(userid);
			}
	        return -1;
	}
	public static String getUserinfo(String field){
		 SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
	     String relust= sharedPre.getString(field, "");
	     return relust;
	}
}
