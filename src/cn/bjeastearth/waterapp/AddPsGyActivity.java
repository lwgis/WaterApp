package cn.bjeastearth.waterapp;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.PollutionType;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.myview.MyTextButton;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class AddPsGyActivity extends Activity {
	private Spinner mRegionSpinner;
	private Spinner mDeptHbSpinner;
	private Spinner mDeptGsSpinner;
	private Spinner mPsTypeSpinner;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private List<Department> departments;
	private List<PollutionType> pollutionTypes;
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
					arrayList.add("");
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
		new Thread(new HttpThread("Xzq")).start();
		new Thread(new HttpThread("Dept")).start();
		new Thread(new HttpThread("GywrType")).start();
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
				mHandler.sendMessage(msg);
			}
		}
		public HttpThread(String typeString) {
			super();
			this.typeString = typeString;
		}
		
	}
}
