package cn.bjeastearth.waterapp;

import java.lang.ref.WeakReference;

import com.google.gson.Gson;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.model.Account;
import cn.bjeastearth.waterapp.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button btnLogin = null;
	private Button btnRegister = null;
	private EditText userNameEt = null;
	private EditText passwordEt = null;
	private User user;
	private MyHandler mHandle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_login);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginActivity.this.finish();
			}
		});
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		userNameEt = (EditText) findViewById(R.id.userNameEt);
		passwordEt = (EditText) findViewById(R.id.passwordEt);
		btnLogin.setEnabled(false);
		TextWatcherimpl textWatcherimpl = new TextWatcherimpl();
		userNameEt.addTextChangedListener(textWatcherimpl);
		passwordEt.addTextChangedListener(textWatcherimpl);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (user == null) {
					user = new User();
				}
				LoginActivity.this.user
						.setName(userNameEt.getText().toString());
				LoginActivity.this.user.setPassword(passwordEt.getText()
						.toString());
				new Thread(new HttpThread()).start();
			}
		});
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(LoginActivity.this,
						RegisterActivity.class);
				LoginActivity.this.startActivity(it);
				LoginActivity.this.finish();
			}
		});
		mHandle = new MyHandler(this);
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
			if (LoginActivity.this.userNameEt.getText().length() != 0
					&& LoginActivity.this.passwordEt.getText().length() != 0) {
				LoginActivity.this.btnLogin.setEnabled(true);
			} else {
				LoginActivity.this.btnLogin.setEnabled(false);
			}
		}

	}

	class HttpThread implements Runnable {
		@Override
		public void run() {
			Message msg = mHandle.obtainMessage();
			try {
				String jsonString = HttpUtil.login(LoginActivity.this.user);
				msg.what = 1;
				msg.obj = jsonString;
				msg.sendToTarget();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg.what = 0;
				msg.sendToTarget();
			}
		}
	}

	static class MyHandler extends Handler {
		WeakReference<LoginActivity> activityReference;

		public MyHandler(LoginActivity activity) {
			activityReference = new WeakReference<LoginActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				if (!msg.obj.toString().contains("密码错误")) {
					Gson gson=new Gson();
					Account account=gson.fromJson(msg.obj.toString(), Account.class);
					WaterDectionary.saveLoginInfo(account.getAccount().getXm(), String.valueOf(account.getAccount().getID()), account.getAccount().getUserDept().getName(), account.getAccount().getXzq().getName());
					
					Toast toast = Toast.makeText(activityReference.get(),
							"登录成功", Toast.LENGTH_SHORT);
					toast.show();
					activityReference.get().finish();
					
				} else {
					Toast toast = Toast.makeText(activityReference.get(),
							"登录失败，请检查用户名密码是否正确", Toast.LENGTH_SHORT);
					toast.show();
				}
			} else {
				Toast toast = Toast.makeText(activityReference.get(),
						"请求服务器失败，请稍候再试", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
}
