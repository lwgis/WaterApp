package cn.bjeastearth.waterapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
private long exitTime = 0;
private Button btnAllNews=null;
private Button btnReport=null;
private Button btnHotProject=null;
private Button btnSituation=null;
private Button btnSewageFactory=null;
private Button btnPollution=null;
private Button btnSanhe=null;
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
		  this.btnSituation=(Button)findViewById(R.id.btnSituation);
		  this.btnSituation.setOnClickListener(this);
		  this.btnSewageFactory=(Button)findViewById(R.id.btnWsclc);
		  this.btnSewageFactory.setOnClickListener(this);
		  this.btnPollution=(Button)findViewById(R.id.btnWry);
		  this.btnPollution.setOnClickListener(this);
		  this.btnSanhe=(Button)findViewById(R.id.btnSanhe);
		  this.btnSanhe.setOnClickListener(this);
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
		int id=btn.getId();
		Intent it=new Intent();
		switch (id) {
		case R.id.btnHotProject:
			it.setClass(this, HotProjectActivity.class);
			break;
		case R.id.btnReport:
			it.setClass(this, ReportActivity.class);
			break;
		case R.id.btnAllNews:
			it.setClass(this, AllNewsActivity.class);
			break;
		case R.id.btnSituation:
			it.setClass(this, RootProjectActivity.class);
			break;
		case R.id.btnWsclc:
			it.setClass(this, SewageFactoryActivity.class);
			break;
		case R.id.btnWry:
			it.setClass(this, PollutionActivity.class);
			break;
		case R.id.btnSanhe:
			it.setClass(this, RiverActivity.class);
		default:
			break;
		}
		startActivity(it);

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
