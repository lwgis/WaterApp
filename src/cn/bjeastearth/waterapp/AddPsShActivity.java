package cn.bjeastearth.waterapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.model.PsLive;
import cn.bjeastearth.waterapp.model.PsLiveType;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.myview.MyTextButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddPsShActivity extends Activity {
	private final int sendPs=100;
	private Button btnBack;
	private EditText gslEditText;
	private EditText wsgdjglEditText;
	private EditText wszlEditText;
	private EditText wscllEditText;
	private EditText rkEditText;
	private Spinner mWslxSpinner;
	private Spinner mRegionSpinner;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private List<PsLiveType> psLiveTypes;
	private Button btnLocation;
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
						AddPsShActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsShActivity.this.mRegionSpinner.setAdapter(adapter);
			}
			if (msg.what==2) {
				psLiveTypes = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<PsLiveType>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (PsLiveType psLiveType : psLiveTypes) {
					arrayList.add(psLiveType.getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsShActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsShActivity.this.mWslxSpinner.setAdapter(adapter);
			}
			if (msg.what==sendPs) {
				Toast.makeText(AddPsShActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			    AddPsShActivity.this.btnSendPs.setEnabled(true);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_sh);
		btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsShActivity.this.finish();
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
				AddPsShActivity.this.btnSendPs.setEnabled(false);
			    AddPsShActivity.this.uploadPsIndustry();
			}
		});
		gslEditText=(EditText)findViewById(R.id.gslEt);
		wsgdjglEditText=(EditText)findViewById(R.id.wsgdjglEt);
		wszlEditText=(EditText)findViewById(R.id.wszlEt);
		wscllEditText=(EditText)findViewById(R.id.wscllEt);
		rkEditText=(EditText)findViewById(R.id.rkEt);
		mWslxSpinner=(Spinner)findViewById(R.id.wslxSpin);
		mRegionSpinner=(Spinner)findViewById(R.id.regionSpin);	
		 this.xTv=(TextView)findViewById(R.id.xTv);
		 this.yTv=(TextView)findViewById(R.id.yTv);
		 this.btnLocation=(Button)findViewById(R.id.btnAddLocation);
		 this.btnLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent it=new Intent(AddPsShActivity.this,LocationActivity.class);
					AddPsShActivity.this.startActivityForResult(it, 2);
			}
		});
		 setTextWatcher();
		new Thread(new HttpThread("Xzq")).start();
		new Thread(new HttpThread("ShwsType")).start();
	}



	protected void uploadPsIndustry() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				PsLive psLive= createPollution();
				Message msg=new Message();
				msg.what=sendPs;
				try {
					HttpUtil.uploadPollutionSource(psLive,"Shws");
					msg.obj="上传成功";
					mHandler.sendMessage(msg);
					AddPsShActivity.this.finish();
				} catch (Throwable e) {
					msg.obj="上传失败";
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	protected PsLive createPollution() {
		// TODO Auto-generated method stub
		PsLive psLive=new PsLive();
		psLive.setXzq(creatRegion(this.mRegionSpinner.getSelectedItem().toString()));
		psLive.setType(creatType(this.mWslxSpinner.getSelectedItem().toString()));
		psLive.setGsl(Double.parseDouble(this.gslEditText.getText().toString()));
		psLive.setWsgdjgl(Double.parseDouble(this.wsgdjglEditText.getText().toString()));
		psLive.setWszl(Double.parseDouble(this.wszlEditText.getText().toString()));
		psLive.setWscll(Double.parseDouble(this.wscllEditText.getText().toString()));
		psLive.setRk(Double.parseDouble(this.rkEditText.getText().toString()));
		psLive.setX(x);
		psLive.setY(y);
		return psLive;
	}

	private PsLiveType creatType(String typeName) {
		for (PsLiveType psLiveType : psLiveTypes) {
			if (psLiveType.getName().equals(typeName)) {
				return psLiveType;
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
			if (gslEditText.getText().length() > 0
					&& wsgdjglEditText.getText().length() > 0
					&& wszlEditText.getText().length() > 0
					&& wscllEditText.getText().length() > 0
					&& rkEditText.getText().length() > 0
					&& x!=0.0&& y!=0) {
				btnSendPs.setEnabled(true);
			}
		}

	}
	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl=new TextWatcherimpl();
		gslEditText.addTextChangedListener(textWatcherimpl);
		wsgdjglEditText.addTextChangedListener(textWatcherimpl);
		wszlEditText.addTextChangedListener(textWatcherimpl);
		wscllEditText.addTextChangedListener(textWatcherimpl);
		rkEditText.addTextChangedListener(textWatcherimpl);
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
				if (typeString.equals("ShwsType")) {
					msg.what=2;
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
			if (gslEditText.getText().length() > 0
					&& wsgdjglEditText.getText().length() > 0
					&& wszlEditText.getText().length() > 0
					&& wscllEditText.getText().length() > 0
					&& rkEditText.getText().length() > 0
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


