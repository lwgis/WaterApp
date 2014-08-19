package cn.bjeastearth.waterapp;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.http.UploadImageUtil;
import cn.bjeastearth.waterapp.AllNewsActivity.httpThread;
import cn.bjeastearth.waterapp.R.id;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.Pollution1;
import cn.bjeastearth.waterapp.model.Pollution2;
import cn.bjeastearth.waterapp.model.PollutionClass1;
import cn.bjeastearth.waterapp.model.PollutionClass2;
import cn.bjeastearth.waterapp.model.PollutionType;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.PsIndustry;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.myview.DpTransform;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class AddPsGyActivity extends Activity {
	private final int sendPs=100;
	private Button btnBack;
	private EditText qymcEditText;
	private EditText cylxEditText;
	private EditText nczEditText;
	private EditText fzrEditText;
	private EditText zczjEditText;
	private EditText bodEditText;
	private EditText codEditText;
	private EditText adEditText;
	private EditText zlEditText;
	private EditText zjsEditText;
	private Spinner mRegionSpinner;
	private Spinner mDeptHbSpinner;
	private Spinner mDeptGsSpinner;
	private Spinner mPsTypeSpinner;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private List<Department> departments;
	private List<PollutionType> pollutionTypes;
	private List<Pollution1> pollution1s;
	private List<Pollution2> pollution2s;
	private Button btnAddPc1;
	private Button btnAddPc2;
	private LinearLayout layoutPc1;
	private LinearLayout layoutPc2;
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
						AddPsGyActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsGyActivity.this.mRegionSpinner.setAdapter(adapter);
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
						AddPsGyActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsGyActivity.this.mDeptGsSpinner.setAdapter(adapter);
				AddPsGyActivity.this.mDeptHbSpinner.setAdapter(adapter);
			}
			if (msg.what==3) {
				pollutionTypes = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<PollutionType>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (PollutionType pollutionType : pollutionTypes) {
					arrayList.add(pollutionType.getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsGyActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsGyActivity.this.mPsTypeSpinner.setAdapter(adapter);
			}
			if (msg.what==4) {
				pollution1s= gson.fromJson(msg.obj.toString(),
						new TypeToken<List<Pollution1>>() {
						}.getType());
			}
			if (msg.what==5) {
				pollution2s= gson.fromJson(msg.obj.toString(),
						new TypeToken<List<Pollution2>>() {
						}.getType());
				}
			if (msg.what==sendPs) {
				Toast.makeText(AddPsGyActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			    AddPsGyActivity.this.btnSendPs.setEnabled(true);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_gy);
		btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsGyActivity.this.finish();
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
				AddPsGyActivity.this.btnSendPs.setEnabled(false);
			    AddPsGyActivity.this.uploadPsIndustry();
			}
		});
		qymcEditText=(EditText)findViewById(R.id.qymcEt);
		cylxEditText=(EditText)findViewById(R.id.cylxEt);
		nczEditText=(EditText)findViewById(R.id.nczEt);
		fzrEditText=(EditText)findViewById(R.id.fzrEt);
		zczjEditText=(EditText)findViewById(R.id.zczjEt);
		bodEditText=(EditText)findViewById(R.id.bodEt);
		codEditText=(EditText)findViewById(R.id.codEt);
		adEditText=(EditText)findViewById(R.id.adEt);
		zlEditText=(EditText)findViewById(R.id.zlEt);
		zjsEditText=(EditText)findViewById(R.id.zjsEt);
		mRegionSpinner=(Spinner)findViewById(R.id.regionSpin);
		mDeptGsSpinner=(Spinner)findViewById(R.id.deptGsSpin);
		mDeptHbSpinner=(Spinner)findViewById(R.id.deptHbSpin);
		mPsTypeSpinner=(Spinner)findViewById(R.id.psTypeSpin);
	
		 this.btnAddPc1=(Button)findViewById(R.id.btnAddPc1);
		 this.layoutPc1=(LinearLayout)findViewById(R.id.layoutPc1);
		 this.btnAddPc1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LinearLayout layout=(LinearLayout)AddPsGyActivity.this.getLayoutInflater().inflate(R.layout.pc_item, null);
				layoutPc1.addView(layout);
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Pollution1 pollution1 : pollution1s) {
					arrayList.add(pollution1.getWrw());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsGyActivity.this,R.layout.simple_spinner_item,
						arrayList);
				Spinner spinner=(Spinner)layout.findViewById(R.id.pcSpin);
				spinner.setAdapter(adapter);
				Button btnRemovePc=(Button)layout.findViewById(R.id.btnRemvoePc);
				btnRemovePc.setTag(layout);
				btnRemovePc.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						layoutPc1.removeView((View)v.getTag());
					}
				});
			}
		});
		 this.layoutPc2=(LinearLayout)findViewById(R.id.layoutPc2);
		 this.btnAddPc2=(Button)findViewById(R.id.btnAddPc2);
		 this.btnAddPc2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout layout=(LinearLayout)AddPsGyActivity.this.getLayoutInflater().inflate(R.layout.pc_item, null);
				layoutPc2.addView(layout);
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Pollution2 pollution2 : pollution2s) {
					arrayList.add(pollution2.getType().getName()+"("+pollution2.getSyfw()+")");
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsGyActivity.this,R.layout.simple_spinner_item,
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
					Intent it=new Intent(AddPsGyActivity.this,LocationActivity.class);
					AddPsGyActivity.this.startActivityForResult(it, 1);
			}
		});
		 
		 //图片
		 this.imageGridView=(GridView)findViewById(R.id.imageGridView);
		 this.allImageStrings=new ArrayList<String>();
		 this.imageAdapter=new AddImageAdapter(this,allImageStrings);
		 this.imageGridView.setAdapter(imageAdapter);
		 popView = LayoutInflater.from(AddPsGyActivity.this)
					.inflate(R.layout.popupwindow_camera, null);
		 this.imageGridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (AddPsGyActivity.this.imageAdapter.getItem(position)==null) {
					if (mPopupWindow==null) {
						mPopupWindow=new PopupWindow(popView,DpTransform.dip2px(AddPsGyActivity.this, 180),DpTransform.dip2px(AddPsGyActivity.this, 100));
					}
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					else {
						mPopupWindow.showAsDropDown(view,-DpTransform.dip2px(AddPsGyActivity.this, 0),DpTransform.dip2px(AddPsGyActivity.this, 0));
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
			 File fileCache = ImageOptions.getCache(AddPsGyActivity.this);
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
				PsIndustry psIndustry= createPollution();
				Message msg=new Message();
				msg.what=sendPs;
				try {
					HttpUtil.uploadPollutionSource(psIndustry,"Gywry");
					msg.obj="上传成功";
					mHandler.sendMessage(msg);
					AddPsGyActivity.this.finish();
				} catch (Throwable e) {
					msg.obj="上传失败";
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	protected PsIndustry createPollution() {
		// TODO Auto-generated method stub
		PsIndustry psIndustry=new PsIndustry();
		psIndustry.setQymc(this.qymcEditText.getText().toString());
		psIndustry.setCylx(this.cylxEditText.getText().toString());
		psIndustry.setNcz(Double.parseDouble(this.nczEditText.getText().toString()));
		psIndustry.setFzr(this.fzrEditText.getText().toString());
		psIndustry.setZczj(Double.parseDouble(this.zczjEditText.getText().toString()));
		psIndustry.setXzq(creatRegion(this.mRegionSpinner.getSelectedItem().toString()));
		psIndustry.setHbDept(creatDept(this.mDeptHbSpinner.getSelectedItem().toString()));
		psIndustry.setGsDept(creatDept(this.mDeptGsSpinner.getSelectedItem().toString()));
		psIndustry.setWrwlx(createPollutionType(this.mPsTypeSpinner.getSelectedItem().toString()));
		psIndustry.setBod(Double.parseDouble(this.bodEditText.getText().toString()));
		psIndustry.setCod(Double.parseDouble(this.codEditText.getText().toString()));
		psIndustry.setNH3N(Double.parseDouble(this.adEditText.getText().toString()));
		psIndustry.setPSum(Double.parseDouble(this.zlEditText.getText().toString()));
		psIndustry.setZjs(Double.parseDouble(this.zjsEditText.getText().toString()));
		psIndustry.setClass1jls(getPsClass1s());
		psIndustry.setClass2jls(getPsClass2s());
		psIndustry.setX(x);
		psIndustry.setY(y);
		psIndustry.setImages(getImages());
		return psIndustry;
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

	private List<PollutionClass1> getPsClass1s() {
		ArrayList<PollutionClass1> pollutionClass1s=new ArrayList<PollutionClass1>();
		for (int i = 0; i < layoutPc1.getChildCount(); i++) {
			View view=layoutPc1.getChildAt(i);
			if (view.getClass().equals(LinearLayout.class)) {
				LinearLayout cLayout=(LinearLayout)view;
				Spinner cSpinner=(Spinner)cLayout.findViewById(R.id.pcSpin);
				EditText cEditText=(EditText)cLayout.findViewById(R.id.pcValue);
				PollutionClass1 pollutionClass1=new PollutionClass1();
				pollutionClass1.setWrw(createPollution1(cSpinner.getSelectedItem().toString()));
				pollutionClass1.setJlvalue(Double.parseDouble(cEditText.getText().toString()));
				pollutionClass1s.add(pollutionClass1);
			}
		}
		if (pollutionClass1s.size()>0) {
			return pollutionClass1s;
		}
		return null;
	}

	private Pollution1 createPollution1(String p1String) {
		// TODO Auto-generated method stub
		for (Pollution1 pollution1 :pollution1s) {
			if (pollution1.getWrw().equals(p1String)){
				return pollution1;
			}
		}
		return null;
	}

	private PollutionType createPollutionType(String ptString) {
		for (PollutionType pollutionType : pollutionTypes) {
			if (pollutionType.getName().equals(ptString)) {
				return pollutionType;
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
			if (qymcEditText.getText().length() > 0
					&& cylxEditText.getText().length() > 0
					&& zczjEditText.getText().length() > 0
					&& bodEditText.getText().length() > 0
					&& codEditText.getText().length() > 0
					&& adEditText.getText().length() > 0
					&& zlEditText.getText().length()>0
					&& zjsEditText.getText().length()>0
					&& x!=0.0&& y!=0) {
				btnSendPs.setEnabled(true);
			}
		}
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode==2) {
				Uri uri = data.getData(); 
				Cursor cursor = AddPsGyActivity.this.getContentResolver().query(uri, null, 
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
	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl=new TextWatcherimpl();
		qymcEditText.addTextChangedListener(textWatcherimpl);
		cylxEditText.addTextChangedListener(textWatcherimpl);
		zczjEditText.addTextChangedListener(textWatcherimpl);
		bodEditText.addTextChangedListener(textWatcherimpl);
		codEditText.addTextChangedListener(textWatcherimpl);
		adEditText.addTextChangedListener(textWatcherimpl);
		zlEditText.addTextChangedListener(textWatcherimpl);
		zjsEditText.addTextChangedListener(textWatcherimpl);
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
			if (qymcEditText.getText().length() > 0
					&& cylxEditText.getText().length() > 0
					&& zczjEditText.getText().length() > 0
					&& bodEditText.getText().length() > 0
					&& codEditText.getText().length() > 0
					&& adEditText.getText().length() > 0
					&& zlEditText.getText().length()>0
					&& zjsEditText.getText().length()>0
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
