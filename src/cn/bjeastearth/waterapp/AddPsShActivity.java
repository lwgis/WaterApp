package cn.bjeastearth.waterapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.PsLive;
import cn.bjeastearth.waterapp.model.PsLiveType;
import cn.bjeastearth.waterapp.model.PsXqyz;
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
	private final int sendPs = 100;
	private Button btnBack;
	private EditText gslEditText;
	private EditText czhjfnyrkEditText;
	private EditText nchjnyrkEditText;
	private EditText czzzrkEditText;
	private EditText nczzrkEditText;
	private EditText rkEditText;
	private EditText wsgdjglEditText;
	private EditText wszlEditText;
	private EditText wscllEditText;
	private EditText codEditText;
	private EditText adEditText;
	private EditText tpEditText;
	private EditText tnEditText;
	private Spinner mRegionSpinner;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private Button btnLocation;
	private TextView xTv;
	private TextView yTv;
	private double x = 0.0;
	private double y = 0.0;
	private PsLive mPsLive;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == sendPs) {
				Toast.makeText(AddPsShActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				AddPsShActivity.this.btnSendPs.setEnabled(true);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_sh);
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddPsShActivity.this.finish();
			}
		});
		this.btnSendPs = (MyTextButton) findViewById(R.id.btnSendPs);
		this.btnSendPs.setEnabled(false);
		this.btnSendPs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddPsShActivity.this.btnSendPs.setEnabled(false);
				AddPsShActivity.this.uploadPsIndustry();
			}
		});
		gslEditText = (EditText) findViewById(R.id.gslEt);
		czhjfnyrkEditText = (EditText) findViewById(R.id.czhjfnyrkEt);
		nchjnyrkEditText = (EditText) findViewById(R.id.nchjnyrkEt);
		czzzrkEditText = (EditText) findViewById(R.id.czzzrkEt);
		nczzrkEditText = (EditText) findViewById(R.id.nczzrkEt);
		rkEditText = (EditText) findViewById(R.id.rkEt);
		wszlEditText = (EditText) findViewById(R.id.wszlEt);
		wscllEditText = (EditText) findViewById(R.id.wscllEt);
		wsgdjglEditText = (EditText) findViewById(R.id.wsgdjglEt);
		codEditText = (EditText) findViewById(R.id.codEt);
		adEditText = (EditText) findViewById(R.id.adEt);
		tpEditText = (EditText) findViewById(R.id.tpEt);
		tnEditText = (EditText) findViewById(R.id.tnEt);
		mRegionSpinner = (Spinner) findViewById(R.id.regionSpin);
		this.xTv = (TextView) findViewById(R.id.xTv);
		this.yTv = (TextView) findViewById(R.id.yTv);
		this.btnLocation = (Button) findViewById(R.id.btnAddLocation);
		this.btnLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(AddPsShActivity.this,
						LocationActivity.class);
				AddPsShActivity.this.startActivityForResult(it, 2);
			}
		});
		setTextWatcher();
		listRegions = WaterDectionary.getRegions();
		ArrayList<String> arrayList = new ArrayList<String>();
		for (Region region : listRegions) {
			if (region.getName().lastIndexOf("镇")==region.getName().length()-1||region.getName().contains("街道")) {
				arrayList.add("  "+region.getName());
			}
			else {
				if (region.getName().contains("村")) {
					arrayList.add("      "+region.getName());
				}
				else {
					arrayList.add(region.getName());

				}
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				AddPsShActivity.this, R.layout.simple_spinner_item, arrayList);
		AddPsShActivity.this.mRegionSpinner.setAdapter(adapter);
		setContent();
	}

	private void setContent() {
		Intent it = getIntent();
		mPsLive = (PsLive) it.getSerializableExtra("PsLive");
		if (mPsLive != null) {
			TextView titleTextView = (TextView) findViewById(R.id.titleTv);
			titleTextView.setText("修改生活污染源");
			mRegionSpinner.setSelection(WaterDectionary.findRegionIndex(mPsLive
					.getSsxz().getID()));
			gslEditText.setText(String.valueOf(mPsLive.getGsl()));
			czhjfnyrkEditText.setText(String.valueOf(mPsLive.getCzhjfnrk()));
			nchjnyrkEditText.setText(String.valueOf(mPsLive.getNchjnyrk()));
			czzzrkEditText.setText(String.valueOf(mPsLive.getCzzzrk()));
			nczzrkEditText.setText(String.valueOf(mPsLive.getNchjzzrk()));
			rkEditText.setText(String.valueOf(mPsLive.getRk()));
			wszlEditText.setText(String.valueOf(mPsLive.getWszl()));
			wscllEditText.setText(String.valueOf(mPsLive.getWscll()));
			wsgdjglEditText.setText(String.valueOf(mPsLive.getWsgdjgl()));
			codEditText.setText(String.valueOf(mPsLive.getCod()));
			adEditText.setText(String.valueOf(mPsLive.getNh3N()));
			tpEditText.setText(String.valueOf(mPsLive.getPsum()));
			tnEditText.setText(String.valueOf(mPsLive.getNsum()));
			x = mPsLive.getX();
			y = mPsLive.getY();
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
				PsLive psLive = createPollution();
				Message msg = new Message();
				msg.what = sendPs;
				try {
					HttpUtil.uploadPollutionSource(psLive, "Shws");
					msg.obj = "上传成功";
					mHandler.sendMessage(msg);
					setResult(1000);
					AddPsShActivity.this.finish();
				} catch (Throwable e) {
					msg.obj = "上传失败";
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}

			}
		}).start();
	}

	protected PsLive createPollution() {
		if (mPsLive == null) {
			mPsLive = new PsLive();
		}
		mPsLive.setXzq(creatRegion(this.mRegionSpinner.getSelectedItem()
				.toString().trim()));
		mPsLive.setGsl(Double
				.parseDouble(this.gslEditText.getText().toString()));
		mPsLive.setCzhjfnrk(Double.parseDouble(this.czhjfnyrkEditText.getText()
				.toString()));
		mPsLive.setNchjnyrk(Double.parseDouble(this.nchjnyrkEditText.getText()
				.toString()));
		mPsLive.setCzzzrk(Double.parseDouble(this.czzzrkEditText.getText()
				.toString()));
		mPsLive.setNchjzzrk(Double.parseDouble(this.nczzrkEditText.getText()
				.toString()));
		mPsLive.setRk(Double.parseDouble(this.rkEditText.getText().toString()));
		mPsLive.setWsgdjgl(Double.parseDouble(this.wsgdjglEditText.getText()
				.toString()));
		mPsLive.setWszl(Double.parseDouble(this.wszlEditText.getText()
				.toString()));
		mPsLive.setWscll(Double.parseDouble(this.wscllEditText.getText()
				.toString()));
		mPsLive.setCod(Double
				.parseDouble(this.codEditText.getText().toString()));
		mPsLive.setNh3N(Double
				.parseDouble(this.adEditText.getText().toString()));
		mPsLive.setPsum(Double
				.parseDouble(this.tpEditText.getText().toString()));
		mPsLive.setNsum(Double
				.parseDouble(this.tnEditText.getText().toString()));
		mPsLive.setX(x);
		mPsLive.setY(y);
		return mPsLive;
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
		if (resultCode == 2) {
			x = data.getExtras().getDouble("X");
			y = data.getExtras().getDouble("Y");
			DecimalFormat df = new DecimalFormat("0.00000");
			xTv.setText("X: " + df.format(x));
			yTv.setText("Y: " + df.format(y));
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			}
		}

	}

	private boolean checkTextView() {
		return gslEditText.getText().length() > 0
				&& czhjfnyrkEditText.getText().length() > 0
				&& nchjnyrkEditText.getText().length() > 0
				&& czzzrkEditText.getText().length() > 0
				&& nczzrkEditText.getText().length() > 0
				&& wsgdjglEditText.getText().length() > 0
				&& wszlEditText.getText().length() > 0
				&& wscllEditText.getText().length() > 0
				&& rkEditText.getText().length() > 0
				&& codEditText.getText().length() > 0
				&& adEditText.getText().length() > 0
				&& tpEditText.getText().length() > 0
				&& tnEditText.getText().length() > 0 && x != 0.0 && y != 0;
	}

	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl = new TextWatcherimpl();
		gslEditText.addTextChangedListener(textWatcherimpl);
		czhjfnyrkEditText.addTextChangedListener(textWatcherimpl);
		nchjnyrkEditText.addTextChangedListener(textWatcherimpl);
		czzzrkEditText.addTextChangedListener(textWatcherimpl);
		nczzrkEditText.addTextChangedListener(textWatcherimpl);
		rkEditText.addTextChangedListener(textWatcherimpl);
		wsgdjglEditText.addTextChangedListener(textWatcherimpl);
		wszlEditText.addTextChangedListener(textWatcherimpl);
		wscllEditText.addTextChangedListener(textWatcherimpl);
		codEditText.addTextChangedListener(textWatcherimpl);
		adEditText.addTextChangedListener(textWatcherimpl);
		tpEditText.addTextChangedListener(textWatcherimpl);
		tnEditText.addTextChangedListener(textWatcherimpl);
	}

	class TextWatcherimpl implements TextWatcher {

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
			} else {
				btnSendPs.setEnabled(false);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

		}

	}
}
