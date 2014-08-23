package cn.bjeastearth.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

public class UploadImageUtil {
	/***
	 * 上传图片方法
	 * @param path
	 * @return
	 */
	public static String uploadImage(final String path,String url){
		
		//服务端保存的图片名称
		final String imgname = UUID.randomUUID().toString() + ".png";
		HttpClient hc = new DefaultHttpClient();
	    HttpPost hp = new HttpPost(
	            url);
	    HttpResponse hr;
	    File f = new File(path);
	    if (f.exists()) {
	        // System.out.println("successful");
	        try {
	            //将图片转成字符串
	            String jason = UploadImageUtil.get64String(path);
	            JSONObject jo1 = new JSONObject();
	            jo1.put("name", imgname);
	            jo1.put("content", jason);
//	            jo1.put("content", "1111");

	            jo1.put("type", ".png");
	            StringEntity se = new StringEntity(jo1.toString(),
	                    HTTP.UTF_8);
	            se.setContentType("application/json");
	            hp.setEntity(se);

	            hr = hc.execute(hp);
	            String strResp = null;
	            if (hr.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
	                strResp = EntityUtils.toString(hr.getEntity());
	            } else {
	                strResp = "$no_found_date$";
	            }

	            
	        }
	           catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } 
	          catch (Throwable e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } finally {
	            hp.abort();
	        }
		
	    }
		
		return imgname;
	}
	/**
	 * 图片转成字符串方法
	 * @param filepath
	 * @return
	 */
	public static String get64String(String filepath){
		
		String result = null;
		
		FileInputStream fin = null;
		
		try{ 
			
	         fin = new FileInputStream(filepath);
	         
	         int length = fin.available(); 
	         
	         byte[] buffer = new byte[length]; 
	         
	         fin.read(buffer);
	         
	         result = Base64.encodeToString(buffer, Base64.DEFAULT);
	         
	         fin.close();   
	         
	    } 
	    catch(Exception e){ 
	    	
	     e.printStackTrace(); 
	     
	    }finally{
	    	
	    	
	    }
		
		return result;
		
	}
}
