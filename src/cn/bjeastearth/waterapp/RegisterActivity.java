package cn.bjeastearth.waterapp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.model.User;
import android.app.Activity;
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
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private EditText userNameEt = null;
	private EditText passwordEt = null;
	private EditText password2Et = null;
	private EditText phoneEt = null;
	private EditText nameEt=null;
	private Spinner mRegionSpinner = null;
	private Spinner departmentSpinner = null;
	private Button btnRegister = null;
	private User user;
	private MyHandler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_register);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RegisterActivity.this.finish();
			}
		});
		mHandler=new MyHandler(this);
		userNameEt = (EditText) findViewById(R.id.userNameEt);
		passwordEt = (EditText) findViewById(R.id.passwordEt);
		password2Et = (EditText) findViewById(R.id.password2Et);
		nameEt=(EditText)findViewById(R.id.nameEt);
		phoneEt = (EditText) findViewById(R.id.phoneEt);
		mRegionSpinner = (Spinner) findViewById(R.id.regionSpin);
		departmentSpinner = (Spinner) findViewById(R.id.departmentSpin);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setEnabled(false);
		TextWatcherimpl textWatcherimpl = new TextWatcherimpl();
		userNameEt.addTextChangedListener(textWatcherimpl);
		passwordEt.addTextChangedListener(textWatcherimpl);
		password2Et.addTextChangedListener(textWatcherimpl);
		phoneEt.addTextChangedListener(textWatcherimpl);
		nameEt.addTextChangedListener(textWatcherimpl);
		setRegionView();
		setDepartmentView();
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (user==null) {
					user=new User();
				}
				user.setName(userNameEt.getText().toString());
				user.setPassword(passwordEt.getText().toString());
				user.setPhone(phoneEt.getText().toString());
				user.setXzq(WaterDectionary.getRegions().get(WaterDectionary.findRegionIndex(mRegionSpinner.getSelectedItem().toString().trim())));
				user.setUserDept(WaterDectionary.getDepartments().get(departmentSpinner.getSelectedItemPosition()));
				user.setXm(nameEt.getText().toString());
				new Thread(new HttpThread()).start();
			}
		});
	}

	private void setRegionView() {
		List<Region> listRegions = WaterDectionary.getRegions();
		ArrayList<String> arrayList = new ArrayList<String>();
		for (Region region : listRegions) {
			if (region.getStatus() == 1) {
				arrayList.add("  " + region.getName());
			} else {
				if (region.getStatus() == 0) {
					arrayList.add("      " + region.getName());
				} else {
					arrayList.add(region.getName());

				}
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, arrayList);
		this.mRegionSpinner.setAdapter(adapter);
	}
	private void setDepartmentView() {
		List<Department> listDepartments = WaterDectionary.getDepartments();
		ArrayList<String> arrayList = new ArrayList<String>();
		for (Department department : listDepartments) {
			arrayList.add(department.getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, arrayList);
		this.departmentSpinner.setAdapter(adapter);
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
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (RegisterActivity.this.userNameEt.getText().length() != 0
					&& RegisterActivity.this.phoneEt.getText().length() != 0
					&& RegisterActivity.this.passwordEt.getText().length() != 0
					&&RegisterActivity.this.nameEt.getText().length()!=0
					&& RegisterActivity.this.passwordEt
							.getText()
							.toString()
							.equals(RegisterActivity.this.password2Et.getText()
									.toString())) {
				RegisterActivity.this.btnRegister.setEnabled(true);
			} else {
				RegisterActivity.this.btnRegister.setEnabled(false);
			}
		}
	}

	class HttpThread implements Runnable {
		@Override
		public void run() {
			try {
				HttpUtil.register(user);
				Message msg=mHandler.obtainMessage();
				msg.what=1;
				msg.sendToTarget();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Message msg=mHandler.obtainMessage();
				msg.what=0;
				msg.sendToTarget();
			}
		}
	}

	static class MyHandler extends Handler {
		WeakReference<RegisterActivity> activityReference;

		public MyHandler(RegisterActivity activity) {
			activityReference = new WeakReference<RegisterActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what==1) {
				Toast toast = Toast.makeText(activityReference.get(),
						"申请成功,等待管理员审核", Toast.LENGTH_SHORT);
				toast.show();
				activityReference.get().finish();
			}
			else {
				Toast toast = Toast.makeText(activityReference.get(),
						"申请失败", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
}
