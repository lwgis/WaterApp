package cn.bjeastearth.http;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import cn.bjeastearth.waterapp.AddPsZzActivity;
import cn.bjeastearth.waterapp.R;
import cn.bjeastearth.waterapp.model.PsZzLevel;
import cn.bjeastearth.waterapp.model.Region;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WaterDectionary {
	private static List<Region> regions;
	private static List<PsZzLevel> psZzLevels;
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
}
