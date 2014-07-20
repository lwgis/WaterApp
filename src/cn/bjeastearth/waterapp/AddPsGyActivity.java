package cn.bjeastearth.waterapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.R.id;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.Pollution1;
import cn.bjeastearth.waterapp.model.Pollution2;
import cn.bjeastearth.waterapp.model.PollutionType;
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
import android.widget.AdapterView.OnItemClickListener;


public class AddPsGyActivity extends Activity {
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
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_gy);
		mRegionSpinner=(Spinner)findViewById(R.id.regionSpin);
		mDeptGsSpinner=(Spinner)findViewById(R.id.deptGsSpin);
		mDeptHbSpinner=(Spinner)findViewById(R.id.deptHbSpin);
		mPsTypeSpinner=(Spinner)findViewById(R.id.psTypeSpin);
		this.btnSendPs=(MyTextButton)findViewById(R.id.btnSendPs);
		 this.btnSendPs.setFocusable(true);
		 this.btnSendPs.setFocusableInTouchMode(true);
		 this.btnSendPs.requestFocus();
		 this.btnSendPs.requestFocusFromTouch();
		 this.btnAddPc1=(Button)findViewById(R.id.btnAddPc1);
		 this.layoutPc1=(LinearLayout)findViewById(R.id.layoutPc1);
		 this.btnAddPc1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LinearLayout layout=(LinearLayout)AddPsGyActivity.this.getLayoutInflater().inflate(R.layout.pc1_item, null);
				layoutPc1.addView(layout);
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Pollution1 pollution1 : pollution1s) {
					arrayList.add(pollution1.getWrw());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsGyActivity.this,R.layout.simple_spinner_item,
						arrayList);
				Spinner spinner=(Spinner)layout.findViewById(R.id.pc1Spin);
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
				LinearLayout layout=(LinearLayout)AddPsGyActivity.this.getLayoutInflater().inflate(R.layout.pc1_item, null);
				layoutPc2.addView(layout);
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Pollution2 pollution2 : pollution2s) {
					arrayList.add(pollution2.getType().getName()+"("+pollution2.getSyfw()+")");
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsGyActivity.this,R.layout.simple_spinner_item,
						arrayList);
				Spinner spinner=(Spinner)layout.findViewById(R.id.pc1Spin);
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
		 this.btnLocation=(Button)findViewById(R.id.btnAddLocation);
		 this.btnLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent it=new Intent(AddPsGyActivity.this,LocationActivity.class);
					AddPsGyActivity.this.startActivityForResult(it, 2);
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
				if (AddPsGyActivity.this.imageAdapter.getItem(position)==null) {
					Intent it=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(it,3);
				}
				
			}
		});
		new Thread(new HttpThread("Xzq")).start();
		new Thread(new HttpThread("Dept")).start();
		new Thread(new HttpThread("GywrType")).start();
		new Thread(new HttpThread("WrwClass1")).start();
		new Thread(new HttpThread("WrwClass2")).start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==2) {
			TextView xTv=(TextView)findViewById(R.id.xTv);
			TextView yTv=(TextView)findViewById(R.id.yTv);
			double x=data.getExtras().getDouble("X");
			double y=data.getExtras().getDouble("Y");
			DecimalFormat df = new DecimalFormat("0.00000");   
			xTv.setText("X: "+df.format(x));
			yTv.setText("Y: "+df.format(y));
		}
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode==3) {
				Uri uri = data.getData(); 
				Cursor cursor = AddPsGyActivity.this.getContentResolver().query(uri, null, 
				null, null, null); 
				cursor.moveToFirst(); 
//				String imgNo = cursor.getString(0); // 锟斤拷鍓э拷锟界紓锟斤拷锟斤拷 
				String imgPath = cursor.getString(1); // 锟斤拷鍓э拷锟斤拷锟斤拷娴犳儼鐭惧锟?
//				String imgSize = cursor.getString(2); // 锟斤拷鍓э拷锟芥径褍锟斤拷 
//				String imgname = cursor.getString(3); // 锟斤拷鍓э拷锟斤拷锟斤拷娴犺泛锟斤拷 
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
}
