package cn.bjeastearth.http;

import java.lang.reflect.Type;
import java.util.List;

import cn.bjeastearth.waterapp.model.WaterNew;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	
	public static List<WaterNew> parseJsonToNews(String jsonString){
		
		Type objType=new TypeToken<List<WaterNew>>() {
		}.getType();
		
		Gson sGson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
				.create();
		
		List<WaterNew> items=null;
		
		try {
			
			items = sGson.fromJson(jsonString, objType);
			
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		}
		
		return items;
		
	}
	
	public static String convertObjectToJson(Object object){
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		
		String jsString = gson.toJson(object);
		
		return jsString;
		
	}

}
