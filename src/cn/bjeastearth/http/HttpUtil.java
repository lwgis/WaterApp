package cn.bjeastearth.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class HttpUtil {
	public static String executeHttpGet(String urlStirng) {
		String result = null;
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		try {
			url = new URL(urlStirng);
			connection = (HttpURLConnection) url.openConnection();
			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result="";
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}
public static void postRequest(String serviceUrl,String informjson) throws Throwable {
		
		StringEntity entity = new StringEntity(informjson, "UTF-8");
		entity.setContentType("application/json;charset=UTF-8");
		entity.setContentEncoding("UTF-8");
		
		HttpPost post = new HttpPost(serviceUrl);
		post.setEntity(entity);
		post.setHeader("Content-Type", "application/json;charset=UTF-8");
		
		HttpClientParams.setRedirecting(post.getParams(), false);
		DefaultHttpClient client=new DefaultHttpClient();
		HttpResponse response = client.execute(post);		
		System.out.println(response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 201) {
			return;
		}
		else {
			throw new Exception("错误");
		}
	}
public static String getAllNewsString() {
	String jsonString=HttpUtil.executeHttpGet("http://159.226.110.64:8001/WaterService/News.svc/All");
	return jsonString;
}
	
public static String getHotProjectString() {
	String jsonString=HttpUtil.executeHttpGet("http://159.226.110.64:8001/WaterService/GProject.svc/All");
	return jsonString;
}
public static void uploadReport(Inform inform) throws Throwable {
	String jsonString = JsonUtil.convertObjectToJson(inform);
	HttpUtil.postRequest("http://159.226.110.64:8001/WaterService/Inform.svc/IInform/Add",jsonString);

}
}
