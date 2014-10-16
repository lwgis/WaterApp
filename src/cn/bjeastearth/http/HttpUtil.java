package cn.bjeastearth.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.bjeastearth.waterapp.model.River;

public class HttpUtil {
	public static String executeHttpGet(String urlStirng) {
		StringBuffer sb = new StringBuffer();
		DefaultHttpClient client= new DefaultHttpClient();
		HttpGet get = new HttpGet(urlStirng);
		get.addHeader("accept", "application/json;charset=UTF-8");
		get.addHeader("Accept-Charset", "utf-8");
		get.addHeader("userid","1");

		try {
			HttpResponse response = client.execute(get);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				InputStream inputStream = response.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(inputStream,
								Charset.forName("utf-8")));
				String line = null;
				while ((line = buffer.readLine()) != null) {
					sb.append(line);
				}
				inputStream.close();
				return sb.toString();
			} else {
				// TODO 返回错误信息
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			// TODO 返回协议错误信息
		} catch (IOException e) {
			e.printStackTrace();
			// TODO 返回网络错误
		}
		return null;
	}

	public static void postRequest(String serviceUrl, String informjson)
			throws Throwable {

		StringEntity entity = new StringEntity(informjson, "UTF-8");
		entity.setContentType("application/json;charset=UTF-8");
		entity.setContentEncoding("UTF-8");

		HttpPost post = new HttpPost(serviceUrl);
		post.setEntity(entity);
		post.setHeader("Content-Type", "application/json;charset=UTF-8");
		post.addHeader("userid","1");
		HttpClientParams.setRedirecting(post.getParams(), false);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);
		System.out.println(response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 201) {

			return;
		} else {
			throw new Exception("错误");
		}
	}

	public static String getAllNewsString() {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/News.svc/All");
		return jsonString;
	}

	public static String getHotProjectString() {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/GProject.svc/All");
		return jsonString;
	}

	public static void uploadReport(Inform inform) throws Throwable {
		String jsonString = JsonUtil.convertObjectToJson(inform);
		HttpUtil.postRequest(
				"http://159.226.110.64:8001/WaterService/Inform.svc/IInform/Add",
				jsonString);

	}

	public static void uploadPollutionSource(Object object, String typeString)
			throws Throwable {
		String jsonString = JsonUtil.convertObjectToJson(object);
		HttpUtil.postRequest(
				"http://159.226.110.64:8001/WaterService/WrSource.svc/"
						+ typeString + "/Add", jsonString);
	}

	public static void uploadRiver(River river) throws Throwable {
		String jsonString=JsonUtil.convertObjectToJson(river,"yyyy-MM-dd");
		HttpUtil.postRequest("http://159.226.110.64:8001/WaterService/HdqlService.svc/hdql/edit", jsonString);
	}

	public static String getAllRootProjectString() {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/OverAll.svc/All");
		return jsonString;
	}

	public static String getAllSewageFactoryString() {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/WsWorks.svc/All");
		return jsonString;
	}

	public static String getAllPollutionString(String typeString) {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/WrSource.svc/"
						+ typeString);
		return jsonString;
	}

	public static String getDectionaryString(String typeString) {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/Dictionary.svc/"
						+ typeString);
		return jsonString;
	}

	public static String getAllRiverString() {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/HdqlService.svc/All");
		return jsonString;
	}

	public static String getAllRiverProjectTypeString() {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/Dictionary.svc/Hdzlxm");
		return jsonString;
	}
	public static String getCountPsString(String tpye) {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/WrSource.svc/"+tpye);
		return jsonString;
	}

	public static String getCountRiver() {
		String jsonString = HttpUtil
				.executeHttpGet("http://159.226.110.64:8001/WaterService/HdqlService.svc/StaHdql");
		return jsonString;
	}
}
