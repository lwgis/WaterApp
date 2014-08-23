package cn.bjeastearth.waterapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.http.HttpUtil;
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
						AddPsZzActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsZzActivity.this.mRegionSpinner.setAdapter(adapter);
			}
			if (msg.what==2) {
				psZzLevels = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<PsZzLevel>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (PsZzLevel psZzLevel : psZzLevels) {
					arrayList.add(psZzLevel.getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsZzActivity.this,R.layout.simple_spinner_item,
						arrayList);
				AddPsZzActivity.this.mWrcdSpinner.setAdapter(adapter);
			}
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
		new Thread(new HttpThread("Xzq")).start();
		new Thread(new HttpThread("Wrcd")).start();
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
		// TODO Auto-generated method stub
		PsZz psZz=new PsZz();
		psZz.setXzq(creatRegion(this.mRegionSpinner.getSelectedItem().toString()));
		psZz.setStmj(Double.parseDouble(this.stmjEditText.getText().toString()));
		psZz.setHdmj(Double.parseDouble(this.hdmjEditText.getText().toString()));
		psZz.setSymj(Double.parseDouble(this.symjEditText.getText().toString()));
		psZz.setGymj(Double.parseDouble(this.gymjEditText.getText().toString()));
		psZz.setNyyl(Double.parseDouble(this.nyylEditText.getText().toString()));
		psZz.setNfyl(Double.parseDouble(this.nfylEditText.getText().toString()));
		psZz.setNycc(Double.parseDouble(this.nyccEditText.getText().toString()));
		psZz.setCd(creatPsZzlevel(this.mWrcdSpinner.getSelectedItem().toString()));
		psZz.setCod(Double.parseDouble(this.codEditText.getText().toString()));
		psZz.setNh3N(Double.parseDouble(this.adEditText.getText().toString()));
		psZz.setPsum(Double.parseDouble(this.tpEditText.getText().toString()));
		psZz.setNsum(Double.parseDouble(this.tnEditText.getText().toString()));
		psZz.setX(x);
		psZz.setY(y);
		return psZz;
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
				if (typeString.equals("Wrcd")) {
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

