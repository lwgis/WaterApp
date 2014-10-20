package cn.bjeastearth.waterapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.PsXqyz;
import cn.bjeastearth.waterapp.model.PsZz;
import cn.bjeastearth.waterapp.model.PsZzLevel;
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

public class AddPsZzActivity extends Activity {
	private final int sendPs=100;
	private Button btnBack;
	private EditText stmjEditText;
	private EditText hdmjEditText;
	private EditText symjEditText;
	private EditText gymjEditText;
	private EditText nyylEditText;
	private EditText nfylEditText;
	private EditText nyccEditText;
	private EditText codEditText;
	private EditText adEditText;
	private EditText tpEditText;
	private EditText tnEditText;
	private Spinner mWrcdSpinner;
	private Spinner mRegionSpinner;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private List<PsZzLevel> psZzLevels;
	private Button btnLocation;
	private TextView xTv;
	private TextView yTv;
	private double x=0.0;
	private double y=0.0;
	private PsZz mPsZz;
	private Handler  mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==sendPs) {
				Toast.makeText(AddPsZzActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			    AddPsZzActivity.this.btnSendPs.setEnabled(true);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_zz);
		btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsZzActivity.this.finish();
			}
		});
		this.btnSendPs=(MyTextButton)findViewById(R.id.btnSendPs);
		 this.btnSendPs.setEnabled(false);
		 this.btnSendPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsZzActivity.this.btnSendPs.setEnabled(false);
			    AddPsZzActivity.this.uploadPsIndustry();
			}
		});
		stmjEditText=(EditText)findViewById(R.id.stmjEt);
		hdmjEditText=(EditText)findViewById(R.id.hdmjEt);
		symjEditText=(EditText)findViewById(R.id.symjEt);
		gymjEditText=(EditText)findViewById(R.id.gymjEt);
		nyylEditText=(EditText)findViewById(R.id.nyylEt);
		nfylEditText=(EditText)findViewById(R.id.nfylEt);
		nyccEditText=(EditText)findViewById(R.id.nyccEt);
		codEditText=(EditText)findViewById(R.id.codEt);
		adEditText=(EditText)findViewById(R.id.adEt);
		tpEditText=(EditText)findViewById(R.id.tpEt);
		tnEditText=(EditText)findViewById(R.id.tnEt);
		mWrcdSpinner=(Spinner)findViewById(R.id.wrcdSpin);
		mRegionSpinner=(Spinner)findViewById(R.id.regionSpin);	
		 this.xTv=(TextView)findViewById(R.id.xTv);
		 this.yTv=(TextView)findViewById(R.id.yTv);
		 this.btnLocation=(Button)findViewById(R.id.btnAddLocation);
		 this.btnLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent it=new Intent(AddPsZzActivity.this,LocationActivity.class);
					AddPsZzActivity.this.startActivityForResult(it, 2);
			}
		});
		 setTextWatcher();
		 listRegions = WaterDectionary.getRegions();
			ArrayList<String> arrayList = new ArrayList<String>();
			for (Region region : listRegions) {
				if (region.getStatus()==1) {
					arrayList.add("  "+region.getName());
				}
				else {
					if (region.getStatus()==0) {
						arrayList.add("      "+region.getName());
					}
					else {
						arrayList.add(region.getName());

					}
				}
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					AddPsZzActivity.this, R.layout.simple_spinner_item, arrayList);
			AddPsZzActivity.this.mRegionSpinner.setAdapter(adapter);
			psZzLevels = WaterDectionary.getPsZzLevels();
			ArrayList<String> arrayListLevels = new ArrayList<String>();
			for (PsZzLevel psZzLevel : psZzLevels) {
				arrayListLevels.add(psZzLevel.getName());
			}
			ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(
					AddPsZzActivity.this,R.layout.simple_spinner_item,
					arrayListLevels);
			AddPsZzActivity.this.mWrcdSpinner.setAdapter(adapterLevel);
			setContent();
	}



	private void setContent() {
		Intent it = getIntent();
		mPsZz = (PsZz) it.getSerializableExtra("PsZz");
		if (mPsZz != null) {
			TextView titleTextView = (TextView) findViewById(R.id.titleTv);
			titleTextView.setText("修改种植污染源");
			mRegionSpinner.setSelection(WaterDectionary.findRegionIndex(mPsZz
					.getSsxz().getID()));
			mWrcdSpinner.setSelection(WaterDectionary.findWrcdIndex(mPsZz.getCd().getID()));
			stmjEditText.setText(String.valueOf(mPsZz.getStmj()));
			hdmjEditText.setText(String.valueOf(mPsZz.getHdmj()));
			symjEditText.setText(String.valueOf(mPsZz.getSymj()));
			gymjEditText.setText(String.valueOf(mPsZz.getGymj()));
			nyylEditText.setText(String.valueOf(mPsZz.getNyyl()));
			nfylEditText.setText(String.valueOf(mPsZz.getNfyl()));
			nyccEditText.setText(String.valueOf(mPsZz.getNycc()));
			codEditText.setText(String.valueOf(mPsZz.getCod()));
			adEditText.setText(String.valueOf(mPsZz.getNh3N()));
			tpEditText.setText(String.valueOf(mPsZz.getPsum()));
			tnEditText.setText(String.valueOf(mPsZz.getNsum()));
			x = mPsZz.getX();
			y = mPsZz.getY();
			DecimalFormat df = new DecimalFormat("0.00000");
			xTv.setText("X: " + df.format(x));
			yTv.setText("Y: " + df.format(y));
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			} else {
				btnSendPs.setEnabled(false);
			}
		}		
	}



	protected void uploadPsIndustry() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				PsZz psZz= createPollution();
				Message msg=new Message();
				msg.what=sendPs;
				try {
					HttpUtil.uploadPollutionSource(psZz,"Zzwry");
					msg.obj="上传成功";
					mHandler.sendMessage(msg);
					setResult(1000);
					AddPsZzActivity.this.finish();
				} catch (Throwable e) {
					msg.obj="上传失败";
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	protected PsZz createPollution() {
		if (mPsZz==null) {
			mPsZz=new PsZz();
		}
		mPsZz.setXzq(creatRegion(this.mRegionSpinner.getSelectedItem().toString().trim()));
		mPsZz.setStmj(Double.parseDouble(this.stmjEditText.getText().toString()));
		mPsZz.setHdmj(Double.parseDouble(this.hdmjEditText.getText().toString()));
		mPsZz.setSymj(Double.parseDouble(this.symjEditText.getText().toString()));
		mPsZz.setGymj(Double.parseDouble(this.gymjEditText.getText().toString()));
		mPsZz.setNyyl(Double.parseDouble(this.nyylEditText.getText().toString()));
		mPsZz.setNfyl(Double.parseDouble(this.nfylEditText.getText().toString()));
		mPsZz.setNycc(Double.parseDouble(this.nyccEditText.getText().toString()));
		mPsZz.setCd(creatPsZzlevel(this.mWrcdSpinner.getSelectedItem().toString()));
		mPsZz.setCod(Double.parseDouble(this.codEditText.getText().toString()));
		mPsZz.setNh3N(Double.parseDouble(this.adEditText.getText().toString()));
		mPsZz.setPsum(Double.parseDouble(this.tpEditText.getText().toString()));
		mPsZz.setNsum(Double.parseDouble(this.tnEditText.getText().toString()));
		mPsZz.setX(x);
		mPsZz.setY(y);
		return mPsZz;
	}

	private PsZzLevel creatPsZzlevel(String typeName) {
		for (PsZzLevel psZzlevel : psZzLevels) {
			if (psZzlevel.getName().equals(typeName)) {
				return psZzlevel;
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
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			}
		}

	}



	private boolean checkTextView() {
		return stmjEditText.getText().length() > 0
				&& hdmjEditText.getText().length() > 0
				&& symjEditText.getText().length() > 0
				&& gymjEditText.getText().length() > 0
				&& nyylEditText.getText().length() > 0
				&& nfylEditText.getText().length() > 0
				&& nyccEditText.getText().length() > 0
				&& codEditText.getText().length() > 0
				&& adEditText.getText().length() > 0
				&& tpEditText.getText().length() > 0
				&& tnEditText.getText().length() > 0
				&& x!=0.0&& y!=0;
	}
	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl=new TextWatcherimpl();
		stmjEditText.addTextChangedListener(textWatcherimpl);
		hdmjEditText.addTextChangedListener(textWatcherimpl);
		symjEditText.addTextChangedListener(textWatcherimpl);
		gymjEditText.addTextChangedListener(textWatcherimpl);
		nyylEditText.addTextChangedListener(textWatcherimpl);
		nfylEditText.addTextChangedListener(textWatcherimpl);
		nyccEditText.addTextChangedListener(textWatcherimpl);
		codEditText.addTextChangedListener(textWatcherimpl);
		adEditText.addTextChangedListener(textWatcherimpl);
		tpEditText.addTextChangedListener(textWatcherimpl);
		tnEditText.addTextChangedListener(textWatcherimpl);
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

