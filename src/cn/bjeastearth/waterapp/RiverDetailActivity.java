package cn.bjeastearth.waterapp;

import java.util.ArrayList;

import cn.bjeastearth.waterapp.model.FieldItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class RiverDetailActivity extends Activity implements ViewFactory {
	private TabHost tabHost;
	private ListView jbxxListView;
	private ListView wryListView;
	private ListView szjlListView;
	private ListView zljhListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_riverdetail);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RiverDetailActivity.this.finish();
			}
		});
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup(); // Call setup() before adding tabs if loading TabHost
							// using findViewById().
		addTab("基本信息","jbxx",R.id.jbxxLv);
		addTab("污染源","wryxx",R.id.wryLv);
		addTab("水质记录","sz",R.id.szjlLv);
		addTab("整治计划","zzjh",R.id.zljhLv);
		jbxxListView=(ListView)findViewById(R.id.jbxxLv);
		wryListView=(ListView)findViewById(R.id.wryLv);
		szjlListView=(ListView)findViewById(R.id.szjlLv);
		zljhListView=(ListView)findViewById(R.id.zljhLv);
		Intent it=getIntent();
		ArrayList<FieldItem> jsbx=(ArrayList<FieldItem>)it.getSerializableExtra("jbxx");
		FieldItemAdapter jbxxAdapter=new FieldItemAdapter(this,jsbx);
		jbxxListView.setAdapter(jbxxAdapter);
		ArrayList<FieldItem> wry=(ArrayList<FieldItem>)it.getSerializableExtra("wry");
		FieldItemAdapter wryAdapter=new FieldItemAdapter(this,wry);
		wryListView.setAdapter(wryAdapter);
		ArrayList<FieldItem> szjl=(ArrayList<FieldItem>)it.getSerializableExtra("szjl");
		FieldItemAdapter szjlAdapter=new FieldItemAdapter(this,szjl);
		szjlListView.setAdapter(szjlAdapter);
		ArrayList<FieldItem> zljh=(ArrayList<FieldItem>)it.getSerializableExtra("zljh");
		FieldItemAdapter zljhAdapter=new FieldItemAdapter(this,zljh);
		zljhListView.setAdapter(zljhAdapter);
	}
	/**
	 * 添加Tab页
	 */
	private void addTab(String title, String tabName,int id) {
		View tabView = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_item, null);
		TextView text = (TextView) tabView.findViewById(R.id.tab_label);
		text.setText(title);
		tabHost.addTab(tabHost.newTabSpec(tabName).setIndicator(tabView)
				.setContent(id));
	}
	@Override
	public View makeView() {
		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setBackgroundColor(0x00000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return imageView;

	}
}
