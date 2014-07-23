package cn.bjeastearth.waterapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.UploadImageUtil;
import cn.bjeastearth.waterapp.AddPsXqyzActivity.HttpThread;
import cn.bjeastearth.waterapp.AddPsXqyzActivity.TextWatcherimpl;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.Pollution1;
import cn.bjeastearth.waterapp.model.Pollution2;
import cn.bjeastearth.waterapp.model.PollutionClass1;
import cn.bjeastearth.waterapp.model.PollutionClass2;
import cn.bjeastearth.waterapp.model.PollutionType;
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

public class AddPsXqyzActivity extends Activity {
	private final int sendPs=100;
	private Button btnBack;
	private EditText dwmcEditText;
	private EditText xqslEditText;
	private EditText nczEditText;
	private EditText fzrEditText;
	private EditText zczjEditText;
	private EditText bodEditText;
	private EditText codEditText;
	private EditText adEditText;
	private EditText zlEditText;
	private Spinner mRegionSpinner;
	private Spinner mDeptHbSpinner;
	private Spinner mDeptGsSpinner;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private List<Department> departments;
	private List<Pollution2> pollution2s;
	private Button btnAddPc2;
	private LinearLayout layoutPc2;
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
						AddPsXqyzActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsXqyzActivity.this.mRegionSpinner.setAdapter(adapter);
			}
			if (msg.what==2) {
				departments = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<Department>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Department department : departments) {
					arrayList.add(department.getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsXqyzActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsXqyzActivity.this.mDeptGsSpinner.setAdapter(adapter);
				AddPsXqyzActivity.this.mDeptHbSpinner.setAdapter(adapter);
			}
			if (msg.what==5) {
				pollution2s= gson.fromJson(msg.obj.toString(),
						new TypeToken<List<Pollution2>>() {
						}.getType());
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
		 this.btnSendPs.setFocusable(true);
		 this.btnSendPs.setFocusableInTouchMode(true);
		 this.btnSendPs.requestFocus();
		 this.btnSendPs.requestFocusFromTouch();
		 this.btnSendPs.setEnabled(false);
		 this.btnSendPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsXqyzActivity.this.btnSendPs.setEnabled(false);
			    AddPsXqyzActivity.this.uploadPsIndustry();
			}
		});
		dwmcEditText=(EditText)findViewById(R.id.dwmcEt);
		xqslEditText=(EditText)findViewById(R.id.xqslEt);
		nczEditText=(EditText)findViewById(R.id.nczEt);
		fzrEditText=(EditText)findViewById(R.id.fzrEt);
		zczjEditText=(EditText)findViewById(R.id.zczjEt);
		bodEditText=(EditText)findViewById(R.id.bodEt);
		codEditText=(EditText)findViewById(R.id.codEt);
		adEditText=(EditText)findViewById(R.id.adEt);
		zlEditText=(EditText)findViewById(R.id.zlEt);
		mRegionSpinner=(Spinner)findViewById(R.id.regionSpin);
		mDeptGsSpinner=(Spinner)findViewById(R.id.deptGsSpin);
		mDeptHbSpinner=(Spinner)findViewById(R.id.deptHbSpin);
		 this.layoutPc2=(LinearLayout)findViewById(R.id.layoutPc2);
		 this.btnAddPc2=(Button)findViewById(R.id.btnAddPc2);
		 this.btnAddPc2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout layout=(LinearLayout)AddPsXqyzActivity.this.getLayoutInflater().inflate(R.layout.pc_item, null);
				layoutPc2.addView(layout);
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Pollution2 pollution2 : pollution2s) {
					arrayList.add(pollution2.getType().getName()+"("+pollution2.getSyfw()+")");
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsXqyzActivity.this,R.layout.simple_spinner_item,
						arrayList);
				Spinner spinner=(Spinner)layout.findViewById(R.id.pcSpin);
				spinner.setAdapter(adapter);
				Button btnRemovePc=(Button)layout.findViewById(R.id.btnRemvoePc);
				btnRemovePc.setTag(layout);
				btnRemovePc.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						layoutPc2.removeView((View)v.getTag());
					}
				});				
			}
		});
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
		 this.imageGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (AddPsXqyzActivity.this.imageAdapter.getItem(position)==null) {
					Intent it=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(it,3);
				}
				
			}
		});
		 setTextWatcher();
		new Thread(new HttpThread("Xzq")).start();
		new Thread(new HttpThread("Dept")).start();
		new Thread(new HttpThread("GywrType")).start();
		new Thread(new HttpThread("WrwClass1")).start();
		new Thread(new HttpThread("WrwClass2")).start();
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
		psXqyz.setDwmc(this.dwmcEditText.getText().toString());
		psXqyz.setNcz(Double.parseDouble(this.nczEditText.getText().toString()));
		psXqyz.setFzr(this.fzrEditText.getText().toString());
		psXqyz.setZczj(Double.parseDouble(this.zczjEditText.getText().toString()));
		psXqyz.setXzq(creatRegion(this.mRegionSpinner.getSelectedItem().toString()));
		psXqyz.setHbDept(creatDept(this.mDeptHbSpinner.getSelectedItem().toString()));
		psXqyz.setGsDept(creatDept(this.mDeptGsSpinner.getSelectedItem().toString()));
		psXqyz.setXqCount(Double.parseDouble(this.xqslEditText.getText().toString()));
		psXqyz.setBod(Double.parseDouble(this.bodEditText.getText().toString()));
		psXqyz.setCod(Double.parseDouble(this.codEditText.getText().toString()));
		psXqyz.setNh3N(Double.parseDouble(this.adEditText.getText().toString()));
		psXqyz.setPSum(Double.parseDouble(this.zlEditText.getText().toString()));
		psXqyz.setXqwrwjls(getPsClass2s());
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

	private List<PollutionClass2> getPsClass2s() {
		ArrayList<PollutionClass2> pollutionClass2s=new ArrayList<PollutionClass2>();
		for (int i = 0; i < layoutPc2.getChildCount(); i++) {
			View view=layoutPc2.getChildAt(i);
			if (view.getClass().equals(LinearLayout.class)) {
				LinearLayout cLayout=(LinearLayout)view;
				Spinner cSpinner=(Spinner)cLayout.findViewById(R.id.pcSpin);
				EditText cEditText=(EditText)cLayout.findViewById(R.id.pcValue);
				PollutionClass2 pollutionClass2=new PollutionClass2();
				pollutionClass2.setWrw(createPollution2(cSpinner.getSelectedItem().toString()));
				pollutionClass2.setJlvalue(cEditText.getText().toString());
				pollutionClass2s.add(pollutionClass2);
			}
		}
		if (pollutionClass2s.size()>0) {
			return pollutionClass2s;
		}
		return null;
	}

	private Pollution2 createPollution2(String p2String) {
		for (Pollution2 pollution2 :pollution2s) {
			String tempString=pollution2.getType().getName()+"("+pollution2.getSyfw()+")";
			if (tempString.equals(p2String)){
				return pollution2;
			}
		}
		return null;
	}







	private Department creatDept(String dString) {
		for (Department department : departments) {
			if (department.getName().equals(dString)) {
				return department;
			}
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
			if (dwmcEditText.getText().length() > 0
					&& xqslEditText.getText().length() > 0
					&& zczjEditText.getText().length() > 0
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
		}
	}
	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl=new TextWatcherimpl();
		dwmcEditText.addTextChangedListener(textWatcherimpl);
		xqslEditText.addTextChangedListener(textWatcherimpl);
		zczjEditText.addTextChangedListener(textWatcherimpl);
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
			if (dwmcEditText.getText().length() > 0
					&& xqslEditText.getText().length() > 0
					&& zczjEditText.getText().length() > 0
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

