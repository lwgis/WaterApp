package cn.bjeastearth.waterapp;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.http.UploadImageUtil;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.PsXqyz;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.myview.DpTransform;
import cn.bjeastearth.waterapp.myview.MyTextButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddPsXqyzActivity extends Activity {
	private final int sendPs=100;
	private Button btnBack;
	private EditText qymcEditText;
	private EditText fzrEditText;
	private EditText lxfsEditText;
	private Spinner mRegionSpinner;
	private EditText nczEditText;
	private EditText zhuEditText;
	private EditText niuEditText;
	private EditText yangEditText;
	private EditText tuEditText;
	private EditText yslEditText;
	private EditText fspflEditText;
	private EditText codEditText;
	private EditText adEditText;
	private EditText tpEditText;
	private EditText tnEditText;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private Button btnLocation;
	private GridView imageGridView;
	private AddImageAdapter imageAdapter;
	private ArrayList<String> allImageStrings;
	private TextView xTv;
	private TextView yTv;
	private double x=0.0;
	private double y=0.0;
	private PopupWindow mPopupWindow;
	private View popView;
	private File currentfile;
	private Handler  mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Gson gson = new Gson();
			if (msg.what==1) {
				listRegions = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<Region>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Region region : listRegions) {
					arrayList.add(region.getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsXqyzActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsXqyzActivity.this.mRegionSpinner.setAdapter(adapter);
			}
			if (msg.what==sendPs) {
				Toast.makeText(AddPsXqyzActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			    AddPsXqyzActivity.this.btnSendPs.setEnabled(true);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_xqyz);
		btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsXqyzActivity.this.finish();
			}
		});
		this.btnSendPs=(MyTextButton)findViewById(R.id.btnSendPs);
//		 this.btnSendPs.setFocusable(true);
//		 this.btnSendPs.setFocusableInTouchMode(true);
//		 this.btnSendPs.requestFocus();
//		 this.btnSendPs.requestFocusFromTouch();
		 this.btnSendPs.setEnabled(false);
		 this.btnSendPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsXqyzActivity.this.btnSendPs.setEnabled(false);
			    AddPsXqyzActivity.this.uploadPsIndustry();
			}
		});
		qymcEditText=(EditText)findViewById(R.id.qymcEt);
		fzrEditText=(EditText)findViewById(R.id.fzrEt);
		lxfsEditText=(EditText)findViewById(R.id.lxfsEt);
		mRegionSpinner=(Spinner)findViewById(R.id.regionSpin);
		nczEditText=(EditText)findViewById(R.id.nczEt);
		zhuEditText=(EditText)findViewById(R.id.zhuEt);
		niuEditText=(EditText)findViewById(R.id.niuEt);
		yangEditText=(EditText)findViewById(R.id.yangEt);
		tuEditText=(EditText)findViewById(R.id.tuEt);
		yslEditText=(EditText)findViewById(R.id.yslEt);
		fspflEditText=(EditText)findViewById(R.id.fspflEt);
		codEditText=(EditText)findViewById(R.id.codEt);
		adEditText=(EditText)findViewById(R.id.adEt);
		tpEditText=(EditText)findViewById(R.id.tpEt);
		tnEditText=(EditText)findViewById(R.id.tnEt);
		 this.xTv=(TextView)findViewById(R.id.xTv);
		 this.yTv=(TextView)findViewById(R.id.yTv);
		 this.btnLocation=(Button)findViewById(R.id.btnAddLocation);
		 this.btnLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent it=new Intent(AddPsXqyzActivity.this,LocationActivity.class);
					AddPsXqyzActivity.this.startActivityForResult(it, 2);
			}
		});
		 
		 //图片
		 this.imageGridView=(GridView)findViewById(R.id.imageGridView);
		 this.allImageStrings=new ArrayList<String>();
		 this.imageAdapter=new AddImageAdapter(this,allImageStrings);
		 this.imageGridView.setAdapter(imageAdapter);
		 popView = LayoutInflater.from(AddPsXqyzActivity.this)
					.inflate(R.layout.popupwindow_camera, null);
		 this.imageGridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (AddPsXqyzActivity.this.imageAdapter.getItem(position)==null) {
					if (mPopupWindow==null) {
						mPopupWindow=new PopupWindow(popView,DpTransform.dip2px(AddPsXqyzActivity.this, 180),DpTransform.dip2px(AddPsXqyzActivity.this, 100));
					}
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					else {
						mPopupWindow.showAsDropDown(view,-DpTransform.dip2px(AddPsXqyzActivity.this, 0),DpTransform.dip2px(AddPsXqyzActivity.this, 0));
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
			 File fileCache = ImageOptions.getCache(AddPsXqyzActivity.this);
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
		 setTextWatcher();
		new Thread(new HttpThread("Xzq")).start();
	}



	protected void uploadPsIndustry() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				PsXqyz psXqyz= createPollution();
				Message msg=new Message();
				msg.what=sendPs;
				try {
					HttpUtil.uploadPollutionSource(psXqyz,"Xqyzwry");
					msg.obj="上传成功";
					mHandler.sendMessage(msg);
					AddPsXqyzActivity.this.finish();
				} catch (Throwable e) {
					msg.obj="上传失败";
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	protected PsXqyz createPollution() {
		// TODO Auto-generated method stub
		PsXqyz psXqyz=new PsXqyz();
		psXqyz.setQymc(this.qymcEditText.getText().toString());
		psXqyz.setFzr(this.fzrEditText.getText().toString());
		psXqyz.setContact(this.lxfsEditText.getText().toString());
		psXqyz.setSsxz(creatRegion(this.mRegionSpinner.getSelectedItem().toString()));
		psXqyz.setNcz(Double.parseDouble(this.nczEditText.getText().toString()));
		psXqyz.setZhuCount(Integer.parseInt(this.zhuEditText.getText().toString()));
		psXqyz.setNiuCount(Integer.parseInt(this.niuEditText.getText().toString()));
		psXqyz.setYangCount(Integer.parseInt(this.yangEditText.getText().toString()));
		psXqyz.setTuCount(Integer.parseInt(this.tuEditText.getText().toString()));
		psXqyz.setYsl(Double.parseDouble(this.yslEditText.getText().toString()));
		psXqyz.setFspfl(Double.parseDouble(this.fspflEditText.getText().toString()));
		psXqyz.setCod(Double.parseDouble(this.codEditText.getText().toString()));
		psXqyz.setNh3N(Double.parseDouble(this.adEditText.getText().toString()));
		psXqyz.setPSum(Double.parseDouble(this.tpEditText.getText().toString()));
		psXqyz.setNSum(Double.parseDouble(this.tnEditText.getText().toString()));
		psXqyz.setX(x);
		psXqyz.setY(y);
		psXqyz.setImages(getImages());
		return psXqyz;
	}

	private List<ProjectImage> getImages() {
		ArrayList<ProjectImage> images=new ArrayList<ProjectImage>();
		for (String imageString : allImageStrings) {
			ProjectImage projectImage=new ProjectImage();
			projectImage.setName(UploadImageUtil.uploadImage(imageString, "http://159.226.110.64:8001/WaterService/Files.svc/upload"));
			images.add(projectImage);
		}
		if (images.size()>0) {
			return images;
		}
		return null;
	}

	private Region creatRegion(String rName) {
		// TODO Auto-generated method stub
		for (Region region : listRegions) {
			if (region.getName().equals(rName)) {
				return region;
			}
		}
		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==2) {
			x=data.getExtras().getDouble("X");
			y=data.getExtras().getDouble("Y");
			DecimalFormat df = new DecimalFormat("0.00000");   
			xTv.setText("X: "+df.format(x));
			yTv.setText("Y: "+df.format(y));
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			}
		}
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode==2) {
				Uri uri = data.getData(); 
				Cursor cursor = AddPsXqyzActivity.this.getContentResolver().query(uri, null, 
				null, null, null); 
				cursor.moveToFirst(); 
				String imgPath = cursor.getString(1); 
				allImageStrings.add(imgPath);
				this.imageAdapter.setImages(allImageStrings); 
				this.imageAdapter.notifyDataSetChanged();
				LayoutParams lParams=this.imageGridView.getLayoutParams();
				int height=(this.allImageStrings.size()/4+1);
				lParams.height=DpTransform.dip2px(this, 80*height);
				this.imageGridView.setLayoutParams(lParams);
				cursor.close(); 
			}
			if (requestCode==3) {
				allImageStrings.add(currentfile.getPath());
				this.imageAdapter.setImages(allImageStrings);
				this.imageAdapter.notifyDataSetChanged();
				LayoutParams lParams=this.imageGridView.getLayoutParams();
				int height=(this.allImageStrings.size()/4+1);
				lParams.height=DpTransform.dip2px(this, 80*height);
				this.imageGridView.setLayoutParams(lParams);
			}
		}
	}



	private boolean checkTextView() {
		return qymcEditText.getText().length() > 0
				&& fzrEditText.getText().length() > 0
				&& lxfsEditText.getText().length() > 0
				&& nczEditText.getText().length() > 0
				&& zhuEditText.getText().length() > 0
				&& niuEditText.getText().length() > 0
				&& yangEditText.getText().length() > 0
				&& tuEditText.getText().length() > 0
				&& yslEditText.getText().length() > 0
				&& fspflEditText.getText().length() > 0
				&& codEditText.getText().length() > 0
				&& adEditText.getText().length() > 0
				&& tpEditText.getText().length() > 0
				&& tnEditText.getText().length() > 0
				&& x!=0.0&& y!=0;
	}
	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl=new TextWatcherimpl();
		qymcEditText.addTextChangedListener(textWatcherimpl);
		fzrEditText.addTextChangedListener(textWatcherimpl);
		lxfsEditText.addTextChangedListener(textWatcherimpl);
		nczEditText.addTextChangedListener(textWatcherimpl);
		zhuEditText.addTextChangedListener(textWatcherimpl);
		niuEditText.addTextChangedListener(textWatcherimpl);
		yangEditText.addTextChangedListener(textWatcherimpl);
		tuEditText.addTextChangedListener(textWatcherimpl);
		yslEditText.addTextChangedListener(textWatcherimpl);
		fspflEditText.addTextChangedListener(textWatcherimpl);
		codEditText.addTextChangedListener(textWatcherimpl);
		adEditText.addTextChangedListener(textWatcherimpl);
		tpEditText.addTextChangedListener(textWatcherimpl);
		tnEditText.addTextChangedListener(textWatcherimpl);
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
	class HttpThread implements Runnable{
		private String typeString;
		@Override
		public void run() {
			String jsonString = HttpUtil.getDectionaryString(typeString);
			Message msg = new Message();
			if (!jsonString.equals("")) {
				msg.obj=jsonString;
				if (typeString.equals("Xzq")) {
					msg.what=1;
				}
				if (typeString.equals("Dept")) {
					msg.what=2;
				}
				if (typeString.equals("GywrType")) {
					msg.what=3;
				}
				if (typeString.equals("WrwClass1")) {
					msg.what=4;
				}
				if (typeString.equals("WrwClass2")) {
					msg.what=5;
				}
				mHandler.sendMessage(msg);
			}
		}
		public HttpThread(String typeString) {
			super();
			this.typeString = typeString;
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
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			}
			else {
				btnSendPs.setEnabled(false);
			}
		}

		@Override 
		public void afterTextChanged(Editable s) {
			
		}
		
	}
}

