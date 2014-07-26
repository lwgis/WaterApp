package cn.bjeastearth.waterapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.UploadImageUtil;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.PsScyz;
import cn.bjeastearth.waterapp.model.PsScyzType;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddPsScyzActivity extends Activity {
	private final int sendPs=100;
	private Button btnBack;
	private EditText symjEditText;
	private EditText clczEditText;
	private EditText bodEditText;
	private EditText codEditText;
	private EditText adEditText;
	private EditText zlEditText;
	private Spinner mRegionSpinner;
	private Spinner mSclxSpinner;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private List<PsScyzType> psScyzTypes;
	private Button btnLocation;
	private GridView imageGridView;
	private AddImageAdapter imageAdapter;
	private ArrayList<String> allImageStrings;
	private TextView xTv;
	private TextView yTv;
	private double x=0.0;
	private double y=0.0;
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
						AddPsScyzActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsScyzActivity.this.mRegionSpinner.setAdapter(adapter);
			}
			if (msg.what==6) {
				psScyzTypes = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<PsScyzType>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (PsScyzType department : psScyzTypes) {
					arrayList.add(department.getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsScyzActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsScyzActivity.this.mSclxSpinner.setAdapter(adapter);
			}
			if (msg.what==sendPs) {
				Toast.makeText(AddPsScyzActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			    AddPsScyzActivity.this.btnSendPs.setEnabled(true);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_scyz);
		btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsScyzActivity.this.finish();
			}
		});
		this.btnSendPs=(MyTextButton)findViewById(R.id.btnSendPs);
		 this.btnSendPs.setFocusable(true);
		 this.btnSendPs.setFocusableInTouchMode(true);
		 this.btnSendPs.requestFocus();
		 this.btnSendPs.requestFocusFromTouch();
		 this.btnSendPs.setEnabled(false);
		 this.btnSendPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsScyzActivity.this.btnSendPs.setEnabled(false);
			    AddPsScyzActivity.this.uploadPsIndustry();
			}
		});
		symjEditText=(EditText)findViewById(R.id.symjEt);
		clczEditText=(EditText)findViewById(R.id.clczEt);
		bodEditText=(EditText)findViewById(R.id.bodEt);
		codEditText=(EditText)findViewById(R.id.codEt);
		adEditText=(EditText)findViewById(R.id.adEt);
		zlEditText=(EditText)findViewById(R.id.zlEt);
		mRegionSpinner=(Spinner)findViewById(R.id.regionSpin);
		mSclxSpinner=(Spinner)findViewById(R.id.sclxSpin);
	
		 this.xTv=(TextView)findViewById(R.id.xTv);
		 this.yTv=(TextView)findViewById(R.id.yTv);
		 this.btnLocation=(Button)findViewById(R.id.btnAddLocation);
		 this.btnLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent it=new Intent(AddPsScyzActivity.this,LocationActivity.class);
					AddPsScyzActivity.this.startActivityForResult(it, 2);
			}
		});
		 
		 //图片
		 this.imageGridView=(GridView)findViewById(R.id.imageGridView);
		 this.allImageStrings=new ArrayList<String>();
		 this.imageAdapter=new AddImageAdapter(this,allImageStrings);
		 this.imageGridView.setAdapter(imageAdapter);
		 this.imageGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (AddPsScyzActivity.this.imageAdapter.getItem(position)==null) {
					Intent it=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(it,3);
				}
				
			}
		});
		 setTextWatcher();
		new Thread(new HttpThread("Xzq")).start();
		new Thread(new HttpThread("ScType")).start();
	}



	protected void uploadPsIndustry() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				PsScyz psScyz= createPollution();
				Message msg=new Message();
				msg.what=sendPs;
				try {
					HttpUtil.uploadPollutionSource(psScyz,"Scyzwry");
					msg.obj="上传成功";
					mHandler.sendMessage(msg);
					AddPsScyzActivity.this.finish();
				} catch (Throwable e) {
					msg.obj="上传失败";
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	protected PsScyz createPollution() {
		// TODO Auto-generated method stub
		PsScyz psScyz=new PsScyz();
		psScyz.setXzq(creatRegion(this.mRegionSpinner.getSelectedItem().toString()));
		psScyz.setYztype(creatYztype(this.mSclxSpinner.getSelectedItem().toString()));
		psScyz.setSymj(Double.parseDouble(this.symjEditText.getText().toString()));
		psScyz.setClcz(Double.parseDouble(this.clczEditText.getText().toString()));
		psScyz.setBod(Double.parseDouble(this.bodEditText.getText().toString()));
		psScyz.setCod(Double.parseDouble(this.codEditText.getText().toString()));
		psScyz.setNh3N(Double.parseDouble(this.adEditText.getText().toString()));
		psScyz.setPSum(Double.parseDouble(this.zlEditText.getText().toString()));
		psScyz.setX(x);
		psScyz.setY(y);
		psScyz.setImages(getImages());
		return psScyz;
	}

	private PsScyzType creatYztype(String typeName) {
		for (PsScyzType psScyzType : psScyzTypes) {
			if (psScyzType.getName().equals(typeName)) {
				return psScyzType;
			}
		}
		return null;
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
			if (symjEditText.getText().length() > 0
					&& clczEditText.getText().length() > 0
					&& bodEditText.getText().length() > 0
					&& codEditText.getText().length() > 0
					&& adEditText.getText().length() > 0
					&& zlEditText.getText().length()>0
					&& x!=0.0&& y!=0) {
				btnSendPs.setEnabled(true);
			}
		}
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode==3) {
				Uri uri = data.getData(); 
				Cursor cursor = AddPsScyzActivity.this.getContentResolver().query(uri, null, 
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
		}
	}
	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl=new TextWatcherimpl();
		symjEditText.addTextChangedListener(textWatcherimpl);
		clczEditText.addTextChangedListener(textWatcherimpl);
		bodEditText.addTextChangedListener(textWatcherimpl);
		codEditText.addTextChangedListener(textWatcherimpl);
		adEditText.addTextChangedListener(textWatcherimpl);
		zlEditText.addTextChangedListener(textWatcherimpl);
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
				if (typeString.equals("ScType")) {
					msg.what=6;
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
			if (symjEditText.getText().length() > 0
					&& clczEditText.getText().length() > 0
					&& bodEditText.getText().length() > 0
					&& codEditText.getText().length() > 0
					&& adEditText.getText().length() > 0
					&& zlEditText.getText().length()>0
					&& x!=0.0&& y!=0) {
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

