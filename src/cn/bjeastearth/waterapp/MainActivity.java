package cn.bjeastearth.waterapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
private long exitTime = 0;
private Button btnAllNews=null;
private Button btnReport=null;
private Button btnHotProject=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		  this.btnAllNews=(Button)findViewById(R.id.btnAllNews);
		  this.btnAllNews.setOnClickListener(this);
		  this.btnReport=(Button)findViewById(R.id.btnReport);
		  this.btnReport.setOnClickListener(this);
		  this.btnHotProject=(Button)findViewById(R.id.btnHotProject);
		  this.btnHotProject.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		super.setTitleColor(Color.BLACK);

	}

	@Override
	public void onClick(View v) {
		Button btn=(Button)v;
		String btnName=btn.getText().toString();
		if (btnName.equals("治水新闻")) {
			Intent it=new Intent(this,AllNewsActivity.class);
			startActivity(it);
		}
		if (btnName.equals("举报")) {
			Intent it=new Intent(this,ReportActivity.class);
			startActivity(it);
		}
		if (btnName.equals("重大项目")) {
			Intent it=new Intent(this,HotProjectActivity.class);
			startActivity(it);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
