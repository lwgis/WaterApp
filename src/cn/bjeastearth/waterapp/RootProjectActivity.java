package cn.bjeastearth.waterapp;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.model.RootProject;
import cn.bjeastearth.waterapp.myview.WebListView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RootProjectActivity extends Activity {

	private List<RootProject> allRootProjects;
	private WebListView mListView;
	RootProjectAdapter mAdapter;
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Gson gson=new Gson();
			allRootProjects=gson.fromJson(msg.obj.toString(), 
					new TypeToken<List<RootProject>>() {
					}.getType());
			mAdapter=new RootProjectAdapter(RootProjectActivity.this, RootProjectActivity.this.allRootProjects);
			mListView.setAdapter(mAdapter);
		}
		
	};
	private OnItemClickListener mOnItemClickListener= new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			RootProject oneRootProject=(RootProject)mAdapter.getItem(position);
			showDetailActivity(oneRootProject);			
		}
	};
	protected void showDetailActivity(RootProject oneRootProject) {
		Intent intent = new Intent(this, FieldItemActivity.class);  
		intent.putExtra("Title", "总体项目概况信息");
		intent.putExtra("FieldItems", oneRootProject.getFieldItems());  
		startActivity(intent); 
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_rootproject);
		Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RootProjectActivity.this.finish();
			}
		});
		this.mListView=(WebListView)findViewById(R.id.rootProjectListView);
		this.mListView.showLoading();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String jsonString=HttpUtil.getAllRootProjectString();
				if (jsonString!="") {
					Message msg=new Message();
					msg.obj=jsonString;
					mHandler.sendMessage(msg);
				}
			}
		}).start();
		this.mListView.setOnItemClickListener(mOnItemClickListener);
	}

}
