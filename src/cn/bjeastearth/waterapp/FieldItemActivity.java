package cn.bjeastearth.waterapp;

import java.util.ArrayList;

import cn.bjeastearth.waterapp.model.FieldItem;
import cn.bjeastearth.waterapp.model.HotProject;
import cn.bjeastearth.waterapp.myview.WebListView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FieldItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_fielditem);
		Button backButton=(Button)findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FieldItemActivity.this.finish();
			}
		});
		WebListView myListView=(WebListView)findViewById(R.id.hotPrjDetailListView);
		myListView.showLoading();
		TextView titleTextView=(TextView)findViewById(R.id.titleTv);
		Intent oneIntent=getIntent();
		titleTextView.setText(oneIntent.getStringExtra("Title"));
		ArrayList<FieldItem> fieldItems=(ArrayList<FieldItem>)oneIntent.getSerializableExtra("FieldItems");
		FieldItemAdapter adapter=new FieldItemAdapter(this, fieldItems);
		myListView.setAdapter(adapter);
	}
	

}
