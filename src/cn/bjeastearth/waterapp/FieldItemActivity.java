package cn.bjeastearth.waterapp;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bjeastearth.waterapp.model.FieldItem;
import cn.bjeastearth.waterapp.model.PollutionSource;
import cn.bjeastearth.waterapp.model.PsIndustry;
import cn.bjeastearth.waterapp.model.PsLive;
import cn.bjeastearth.waterapp.model.PsScyz;
import cn.bjeastearth.waterapp.model.PsXqyz;
import cn.bjeastearth.waterapp.model.PsZz;
import cn.bjeastearth.waterapp.myview.WebListView;
import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class FieldItemActivity extends Activity implements ViewFactory {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_fielditem);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FieldItemActivity.this.finish();
			}
		});
		WebListView myListView = (WebListView) findViewById(R.id.hotPrjDetailListView);
		myListView.showLoading();
		TextView titleTextView = (TextView) findViewById(R.id.titleTv);
		Intent oneIntent = getIntent();
		titleTextView.setText(oneIntent.getStringExtra("Title"));
		Serializable serializable = oneIntent
				.getSerializableExtra("FieldItems");
		ArrayList<FieldItem> fieldItems = (ArrayList<FieldItem>) serializable;
		FieldItemAdapter adapter = new FieldItemAdapter(this, fieldItems);
		myListView.setAdapter(adapter);
		final PollutionSource pollutionSource=(PollutionSource)oneIntent.getSerializableExtra("PollutionSource");
		Button btnEidt=(Button)findViewById(R.id.btnEdit);
		if (pollutionSource!=null) {
			btnEidt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (pollutionSource.getClass().equals(PsIndustry.class)) {
						Intent it=new Intent(FieldItemActivity.this, AddPsGyActivity.class);
						PsIndustry psIndustry=(PsIndustry)pollutionSource;
						it.putExtra("PsIndustry", psIndustry);
						startActivityForResult(it, 1000);
					}
					if (pollutionSource.getClass().equals(PsXqyz.class)) {
						Intent it=new Intent(FieldItemActivity.this, AddPsXqyzActivity.class);
						PsXqyz psXqyz=(PsXqyz)pollutionSource;
						it.putExtra("PsXqyz", psXqyz);
						startActivityForResult(it, 1000);
					}
					if (pollutionSource.getClass().equals(PsScyz.class)) {
						Intent it=new Intent(FieldItemActivity.this, AddPsScyzActivity.class);
						PsScyz psScyz=(PsScyz)pollutionSource;
						it.putExtra("PsScyz", psScyz);
						startActivityForResult(it, 1000);
					}
					if (pollutionSource.getClass().equals(PsZz.class)) {
						Intent it=new Intent(FieldItemActivity.this, AddPsZzActivity.class);
						PsZz psZz=(PsZz)pollutionSource;
						it.putExtra("PsZz", psZz);
						startActivityForResult(it, 1000);
					}
					if (pollutionSource.getClass().equals(PsLive.class)) {
						Intent it=new Intent(FieldItemActivity.this, AddPsShActivity.class);
						PsLive psLive=(PsLive)pollutionSource;
						it.putExtra("PsLive", psLive);
						startActivityForResult(it, 1000);
					}
				}
			});
		}
		else {
			btnEidt.setVisibility(View.GONE);
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1000&&resultCode==1000) {
			setResult(1000);
			finish();
		}
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
