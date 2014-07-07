package cn.bjeastearth.waterapp;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.model.HotProject;
import cn.bjeastearth.waterapp.model.RootProject;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class RootProjectActivity extends Activity {

	private List<RootProject> allRootProjects;
	private ListView mListView;
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Gson gson=new Gson();
			allRootProjects=gson.fromJson(msg.obj.toString(), 
					new TypeToken<List<RootProject>>() {
					}.getType());
			RootProjectAdapter adapter=new RootProjectAdapter(RootProjectActivity.this, RootProjectActivity.this.allRootProjects);
			mListView.setAdapter(adapter);
		}
		
	};
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
		this.mListView=(ListView)findViewById(R.id.rootProjectListView);
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
		}).start();;
	}

}
