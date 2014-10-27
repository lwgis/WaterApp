package cn.bjeastearth.waterapp;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.SendSmsActivity.MyHandler.SmsAdapter;
import cn.bjeastearth.waterapp.model.River;
import cn.bjeastearth.waterapp.model.User;
import cn.bjeastearth.waterapp.model.Wsgw;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SendSmsActivity extends Activity {
	String SENT_SMS_ACTION = "SENT_SMS_ACTION";
	String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
	public static final Uri SMS_URI = Uri.parse("content://sms/");
	private EditText smsEdit = null;
	private Button btnSendSms = null;
	private TextView titleTv = null;
	private ListView listView = null;
	private List<User> users;
	private MyHandler mHandler;
	private SmsAdapter adapter = null;
	private BroadcastReceiver sendMessage = new BroadcastReceiver() {
		@Override
		public void onReceive(Context c, Intent intent) {
			// 判断短信是否成功
			switch (getResultCode()) {
			case Activity.RESULT_OK:
				Toast.makeText(SendSmsActivity.this, "发送成功！",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(SendSmsActivity.this, "发送失败！",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_sendsms);
		mHandler = new MyHandler(this);
		Button btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SendSmsActivity.this.finish();
			}
		});
		titleTv = (TextView) findViewById(R.id.tilteTv);
		smsEdit = (EditText) findViewById(R.id.smsEt);
		btnSendSms = (Button) findViewById(R.id.btnSendSms);
		listView = (ListView) findViewById(R.id.listView);
		btnSendSms.setEnabled(false);
		smsEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				setSendBtn();
			}
		});
		btnSendSms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SendSmsActivity.this.titleTv.setText("发送中(0/"
						+ SendSmsActivity.this.users.size());
				int i = 1;
				for (User user : SendSmsActivity.this.users) {
					SendSmsActivity.this.sendSMS(user.getPhone());
					SendSmsActivity.this.titleTv.setText("发送中(" + i + "/"
							+ SendSmsActivity.this.users.size());
					i++;
				}
				SendSmsActivity.this.titleTv.setText("发送完成");
				Toast.makeText(SendSmsActivity.this, "发送完成！",
						Toast.LENGTH_SHORT).show();
				SendSmsActivity.this.finish();
			}
		});
		new Thread(new Runnable() {

			@Override
			public void run() {
				String jsonString = HttpUtil.getMyUsers();
				Message msg = SendSmsActivity.this.mHandler.obtainMessage();
				msg.obj = jsonString;
				msg.sendToTarget();
			}
		}).start();
	}

	private void setSendBtn() {
		if (SendSmsActivity.this.smsEdit.getText().length() > 0
				&& SendSmsActivity.this.users != null
				&& SendSmsActivity.this.users.size() > 0) {
			SendSmsActivity.this.btnSendSms.setEnabled(true);
		} else {
			SendSmsActivity.this.btnSendSms.setEnabled(false);
		}
	}

	public void sendSMS(String phone) {
		Intent delive = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent sendPI = PendingIntent.getBroadcast(this, 0, delive, 0);
		SmsManager smsManager = SmsManager.getDefault();
		List<String> divideContents = smsManager.divideMessage(this.smsEdit
				.getText().toString().trim());
		for (String text : divideContents) {
			smsManager.sendTextMessage(phone, null, text, null, null);
		}
	}

	protected void onResume() {
		super.onResume();
		// 注册监听
		registerReceiver(sendMessage, new IntentFilter(SENT_SMS_ACTION));
	}

	static class MyHandler extends Handler {
		WeakReference<SendSmsActivity> activityReference;

		public MyHandler(SendSmsActivity activity) {
			activityReference = new WeakReference<SendSmsActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.obj == null) {
				Toast.makeText(activityReference.get(), "连接服务器失败,请稍候再试!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Gson gson = new Gson();
			activityReference.get().users = gson.fromJson(msg.obj.toString(),
					new TypeToken<List<User>>() {
					}.getType());
			if (activityReference.get().adapter == null) {
				activityReference.get().adapter = new SmsAdapter();
				activityReference.get().listView.setAdapter(activityReference
						.get().adapter);

			}
			activityReference.get().adapter.notifyDataSetChanged();
			activityReference.get().setSendBtn();
		}

		class SmsAdapter extends BaseAdapter {
			public SmsAdapter() {

			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return activityReference.get().users.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return activityReference.get().users.get(position);
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				if (convertView == null) {
					convertView = LayoutInflater.from(activityReference.get())
							.inflate(R.layout.sms_item, null);
				}
				TextView firstTextView = (TextView) convertView
						.findViewById(R.id.firstTv);
				TextView secondTextView = (TextView) convertView
						.findViewById(R.id.secondTv);
				firstTextView.setText(activityReference.get().users.get(
						position).getXm());
				secondTextView.setText(activityReference.get().users.get(
						position).getPhone());
				Button btnDelete = (Button) convertView
						.findViewById(R.id.btnDelete);
				btnDelete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						activityReference.get().users.remove(position);
						activityReference.get().adapter.notifyDataSetChanged();
						activityReference.get().setSendBtn();
					}
				});
				return convertView;
			}

		}
	}

}
