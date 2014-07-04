package cn.bjeastearth.waterapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

import cn.bjeastearth.http.AttachmentType;
import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.Inform;
import cn.bjeastearth.http.InformAttachement;

import cn.bjeastearth.waterapp.model.Department;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class ReportActivity extends Activity {
	private Button reportBack;
	private MyTextButton reportSend;
	private EditText reportTitleEditText;
	private EditText reportAddrEditText;
	private EditText reportTelEditText;
	private EditText reportMailEditText;
	private MyEditText reportEditText;
	private GridView reportGridView;
	private ReportAdapter reportAdapter;
	private ArrayList<String> allImageStrings;
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what==1) {
				Toast toast = Toast.makeText(ReportActivity.this, "上传成功", Toast.LENGTH_SHORT); 
		        toast.show();
		        ReportActivity.this.finish();
			}
			else {
				Toast toast = Toast.makeText(ReportActivity.this, "上传失败", Toast.LENGTH_SHORT); 
		        toast.show();
				ReportActivity.this.reportSend.setEnabled(true);

			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_report);
		 this.reportBack=(Button)findViewById(R.id.btnReportBack);
		 this.reportBack.setFocusable(true);
		 this.reportBack.setFocusableInTouchMode(true);
		 this.reportBack.requestFocus();
		 this.reportBack.requestFocusFromTouch();
		 this.reportBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ReportActivity.this.finish();
			}
		});
		 this.reportSend=(MyTextButton)findViewById(R.id.btnSendReport);
		 this.reportSend.setEnabled(false);
		 this.reportTitleEditText=(EditText)findViewById(R.id.reportTitleEditText);
		 this.reportAddrEditText=(EditText)findViewById(R.id.reportAddrEditText);
		 this.reportTelEditText=(EditText)findViewById(R.id.reportTelEditText);
		 this.reportMailEditText=(EditText)findViewById(R.id.reportMailEditText);
		 this.reportTitleEditText=(EditText)findViewById(R.id.reportTitleEditText);
		 this.reportEditText=(MyEditText)findViewById(R.id.reportEditText);
		 TextWatcherimpl textWatcherimpl=new TextWatcherimpl();
		 this.reportTitleEditText.addTextChangedListener(textWatcherimpl);
		 this.reportAddrEditText.addTextChangedListener(textWatcherimpl);
		 this.reportTelEditText.addTextChangedListener(textWatcherimpl);
		 this.reportMailEditText.addTextChangedListener(textWatcherimpl);
		 this.reportEditText.addTextChangedListener(textWatcherimpl);
		 this.reportGridView=(GridView)findViewById(R.id.reportGridView);
		 this.allImageStrings=new ArrayList<String>();
		 this.reportAdapter=new ReportAdapter(this,allImageStrings);
		 this.reportGridView.setAdapter(reportAdapter);
		 this.reportGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (ReportActivity.this.reportAdapter.getItem(position)==null) {
					Intent it=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(it,2);
				}
				
			}
		});
		 this.reportSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ReportActivity.this.reportSend.setEnabled(false);
				ReportActivity.this.uploadInform();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode==2) {
				Uri uri = data.getData(); 
				Cursor cursor = ReportActivity.this.getContentResolver().query(uri, null, 
				null, null, null); 
				cursor.moveToFirst(); 
//				String imgNo = cursor.getString(0); // 锟斤拷鍓э拷锟界紓锟斤拷锟斤拷 
				String imgPath = cursor.getString(1); // 锟斤拷鍓э拷锟斤拷锟斤拷娴犳儼鐭惧锟?
//				String imgSize = cursor.getString(2); // 锟斤拷鍓э拷锟芥径褍锟斤拷 
//				String imgname = cursor.getString(3); // 锟斤拷鍓э拷锟斤拷锟斤拷娴犺泛锟斤拷 
				allImageStrings.add(imgPath);
				this.reportAdapter=new ReportAdapter(this, allImageStrings); 
				this.reportGridView.setAdapter(this.reportAdapter);
				LayoutParams lParams=this.reportGridView.getLayoutParams();
				int height=(this.allImageStrings.size()/4+1);
				lParams.height=DpTransform.dip2px(this, 80*height);
				this.reportGridView.setLayoutParams(lParams);
				cursor.close(); 
			}
		}
	}

	class TextWatcherimpl implements TextWatcher{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (ReportActivity.this.reportEditText.getText().length()!=0
					&&ReportActivity.this.reportAddrEditText.getText().length()!=0
					&&ReportActivity.this.reportTitleEditText.getText().length()!=0
					&&ReportActivity.this.reportTelEditText.getText().length()!=0
					&&ReportActivity.this.reportMailEditText.getText().length()!=0) {
				ReportActivity.this.reportSend.setEnabled(true);
			}
			else {
				ReportActivity.this.reportSend.setEnabled(false);

			}
		}
		
	}
public void uploadInform(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				try {
					//附件类型id为1指图片，这里只需要给id赋值就可以了
					AttachmentType attachmentType = new AttachmentType();
					
					attachmentType.setId(1);
					List<InformAttachement> imgs = new ArrayList<InformAttachement>();
					for (String filepath : allImageStrings) {
						
						String imgname = uploadImage(filepath);
						
						//举报信息附件对象，（注意新增的对象不要给id赋值）					
						InformAttachement img1 = new InformAttachement();
						
						img1.setName(imgname);
						
						img1.setType(attachmentType);
						
						imgs.add(img1);
					}
					
					//举报的对象（新增的对象不要给id赋值）
					Inform inform = new Inform();
					
					inform.setName(ReportActivity.this.reportTitleEditText.getText().toString());
					
					inform.setContent(ReportActivity.this.reportEditText.getText().toString());
					
					inform.setAddress(ReportActivity.this.reportAddrEditText.getText().toString());
					
					Department dept = new Department();
					
					dept.setID(1);
					
					inform.setDept(dept);
					
					inform.setEmail(ReportActivity.this.reportMailEditText.getText().toString());
					
					//获取当前时间
					Calendar calendar = Calendar.getInstance();
					
					calendar.setTime(new Date());
						
					Date date = calendar.getTime();
					
					inform.setInformTime(date);
					inform.setImages(imgs);
					HttpUtil.uploadReport(inform);	
					Message msg=new Message();
					msg.what=1;
					mHandler.sendMessage(msg);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					Message msg=new Message();
					msg.what=0;
					mHandler.sendMessage(msg);
					
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg=new Message();
					msg.what=0;
					mHandler.sendMessage(msg);
				}
				
			}
		}).start(); 
		
	}
/***
 * 上传图片方法
 * @param path
 * @return
 */
public String uploadImage(final String path){
	
	//服务端保存的图片名称
	final String imgname = UUID.randomUUID().toString() + ".png";
	
	HttpClient hc = new DefaultHttpClient();
    HttpPost hp = new HttpPost(
            "http://159.226.110.64:8001/WaterService/Inform.svc/file/upload");
    HttpResponse hr;
    File f = new File(path);
    if (f.exists()) {
        // System.out.println("successful");
        try {
            //将图片转成字符串
            String jason = get64String(path);
            JSONObject jo1 = new JSONObject();
            jo1.put("name", imgname);
            jo1.put("content", jason);
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
            
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
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
public String get64String(String filepath){
	
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
