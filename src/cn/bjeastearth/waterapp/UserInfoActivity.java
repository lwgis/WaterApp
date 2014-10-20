package cn.bjeastearth.waterapp;

import cn.bjeastearth.http.WaterDectionary;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoActivity extends Activity {
	private TextView nameTv;
	private TextView departmentTv;
	private TextView xzqTv;
	private Button btnExit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_userinfo);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserInfoActivity.this.finish();
			}
		});
		nameTv=(TextView)findViewById(R.id.nameTv);
		departmentTv=(TextView)findViewById(R.id.departmentTv);
		xzqTv=(TextView)findViewById(R.id.xzqTv);
		nameTv.setText(WaterDectionary.getUserinfo("username"));
		departmentTv.setText(WaterDectionary.getUserinfo("department"));
		xzqTv.setText(WaterDectionary.getUserinfo("xzq"));
		btnExit=(Button)findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WaterDectionary.saveLoginInfo("", "", "", "");
				UserInfoActivity.this.finish();
			}
		});
	}
}
