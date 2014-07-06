package cn.bjeastearth.waterapp;

import cn.bjeastearth.waterapp.model.HotProject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class HotProjectDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_hotprojectdetail);
		Button backButton=(Button)findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HotProjectDetailActivity.this.finish();
			}
		});
		ListView myListView=(ListView)findViewById(R.id.hotPrjDetailListView);
		
		Intent oneIntent=getIntent();
		HotProject oneHotProject=(HotProject)oneIntent.getSerializableExtra("HotProject");
		HotProjectDetailAdapter adapter=new HotProjectDetailAdapter(this, oneHotProject.getFieldItems());
		myListView.setAdapter(adapter);
	}
	

}
