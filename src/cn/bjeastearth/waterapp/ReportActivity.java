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
import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.http.Inform;
import cn.bjeastearth.http.InformAttachement;
import cn.bjeastearth.http.UploadImageUtil;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.myview.DpTransform;
import cn.bjeastearth.waterapp.myview.MyEditText;
import cn.bjeastearth.waterapp.myview.MyTextButton;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class ReportActivity extends Activity {
	private Button reportBack;
	private MyTextButton reportSend;
	private EditText reportTitleEditText;
	private EditText reportAddrEditText;
	private EditText reportTelEditText;
	private EditText reportMailEditText;
	private MyEditText reportEditText;
	private GridView imageGridView;
	private AddImageAdapter reportAdapter;
	private ArrayList<ProjectImage> projectImages;
	private PopupWindow mPopupWindow;
	private View popView;
	private File currentfile;
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
		 this.imageGridView=(GridView)findViewById(R.id.imageGridView);
		 this.projectImages=new ArrayList<ProjectImage>();
		 this.reportAdapter=new AddImageAdapter(this,projectImages);
		 this.imageGridView.setAdapter(reportAdapter);
		 popView = LayoutInflater.from(ReportActivity.this)
					.inflate(R.layout.popupwindow_camera, null);
		 this.imageGridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (ReportActivity.this.reportAdapter.getItem(position)==null) {
					if (mPopupWindow==null) {
						mPopupWindow=new PopupWindow(popView,DpTransform.dip2px(ReportActivity.this, 180),DpTransform.dip2px(ReportActivity.this, 100));
					}
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					else {
						mPopupWindow.showAsDropDown(view,-DpTransform.dip2px(ReportActivity.this, 0),DpTransform.dip2px(ReportActivity.this, 0));
					}
				}
				
			}
		});
		 //相机按钮
		 Button btnCamera=(Button)popView.findViewById(R.id.btnCamera);
		 btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 File fileCache = ImageOptions.getCache(ReportActivity.this);
			 Intent intent = new Intent();
				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//				intent.addCategory(Intent.CATEGORY_DEFAULT);
				currentfile = new File(fileCache.getPath()+"/"+ UUID.randomUUID().toString() + ".jpg");
				if (currentfile.exists()) {
					currentfile.delete();
				}
				Uri uri = Uri.fromFile(currentfile);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, 3);
				mPopupWindow.dismiss();
			}
		});
		 //相册按钮
		 Button btnPhoto=(Button)popView.findViewById(R.id.btnPhoto);
		 btnPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(it,2);		
				mPopupWindow.dismiss();
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
			if (requestCode == 2) {
				Uri uri = data.getData();
				Cursor cursor = ReportActivity.this.getContentResolver()
						.query(uri, null, null, null, null);
				cursor.moveToFirst();
				String imgPath = cursor.getString(1);
				ProjectImage projectImage = new ProjectImage();
				projectImage.setName(imgPath);
				projectImage.setType(ProjectImage.LOCAL);
				projectImages.add(projectImage);
				this.reportAdapter.setImages(projectImages);
				this.reportAdapter.notifyDataSetChanged();
				LayoutParams lParams = this.imageGridView.getLayoutParams();
				int height = (this.projectImages.size() / 4 + 1);
				lParams.height = DpTransform.dip2px(this, 80 * height);
				this.imageGridView.setLayoutParams(lParams);
				cursor.close();
			}
			if (requestCode == 3) {
				ProjectImage projectImage = new ProjectImage();
				projectImage.setName(currentfile.getPath());
				projectImage.setType(ProjectImage.LOCAL);
				projectImages.add(projectImage);
				this.reportAdapter.setImages(projectImages);
				this.reportAdapter.notifyDataSetChanged();
				LayoutParams lParams = this.imageGridView.getLayoutParams();
				int height = (this.projectImages.size() / 4 + 1);
				lParams.height = DpTransform.dip2px(this, 80 * height);
				this.imageGridView.setLayoutParams(lParams);
			}
		}
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
			mPopupWindow = null;
			return true;
		}
		return super.dispatchTouchEvent(ev);
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
					for (ProjectImage projectImage : projectImages) {
						
						String imgname = UploadImageUtil.uploadImage(projectImage.getName(),"http://159.226.110.64:8001/WaterService/Inform.svc/file/upload");
						
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

}
