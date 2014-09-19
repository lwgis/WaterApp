package cn.bjeastearth.waterapp;

import java.util.List;

import com.esri.core.internal.tasks.ags.m;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.R.layout;
import cn.bjeastearth.waterapp.model.WaterNew;
import cn.bjeastearth.waterapp.myview.WebListView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AllNewsActivity extends Activity {
	private WebListView mListView = null;
	private Button btnBack;

	private Handler myHandle = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Gson gson = new Gson();
			if (msg.obj != null) {
				List<WaterNew> news = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<WaterNew>>() {
						}.getType());
				AllNewsActivity.this.mListView.setAdapter(new AllNewsAdapter(
						AllNewsActivity.this, news));
				AllNewsActivity.this.mListView
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								WaterNew aNew = (WaterNew) view.getTag();
								Intent it = new Intent(AllNewsActivity.this,
										NewsDetailActivity.class);
								it.putExtra("title", aNew.getTitle());
								it.putExtra("content", aNew.getContent());
								AllNewsActivity.this.startActivity(it);
							}
						});
			} else {
				Toast.makeText(AllNewsActivity.this, "连接服务器失败,请稍候再试!",
						Toast.LENGTH_SHORT).show();
				AllNewsActivity.this.finish();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_news);
		btnBack = (Button) findViewById(R.id.btnNewsBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AllNewsActivity.this.finish();
			}
		});
		this.mListView = (WebListView) findViewById(R.id.allNewsListView);
		this.mListView.showLoading();
		new Thread(new httpThread(), "httpthread").start();
	}

	class httpThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String jsonString = HttpUtil.getAllNewsString();
			if (jsonString != "") {
				Message msg = new Message();
				msg.obj = jsonString;
				myHandle.sendMessage(msg);
			}
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */

}
