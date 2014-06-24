package cn.bjeastearth.waterapp;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.model.WaterNew;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class AllNewsActivity extends Activity {
	private Button btnReport;
	private ListView mListView=null;
	@SuppressLint("HandlerLeak") private Handler myHandle=new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		 Gson gson=new Gson();
		 List<WaterNew> news=gson.fromJson(msg.obj.toString(),new TypeToken<List<WaterNew>>(){}.getType());
		 AllNewsActivity.this.mListView.setAdapter(new AllNewsAdapter(AllNewsActivity.this, news));
//		AllNewsActivity.this.myTextView.setText(wnes.get(1).toString());
	}
	
};



	@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_all_news); 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      
        R.layout.title_all_news); 
        this.btnReport=(Button)findViewById(R.id.btnReport);
        this.btnReport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it=new Intent(AllNewsActivity.this,ReportActivity.class);
				startActivity(it);
				
			}
		});
        this.mListView=(ListView)findViewById(R.id.allNewsListView);
       new Thread(new httpThread(),"httpthread").start();
       this.mListView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			WaterNew aNew=(WaterNew)view.getTag();
			Intent it=new Intent(AllNewsActivity.this,NewsDetailActivity.class);
			it.putExtra("title", aNew.getTitle());
			it.putExtra("content", aNew.getContent());
			AllNewsActivity.this.startActivity(it);
		}
	});
    }
    
class httpThread  implements  Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		 String jsonString= HttpUtil.getAllNewsString();
		 if (jsonString!="") {
			 Message msg=new Message();
			 msg.obj=jsonString;
			 myHandle.sendMessage(msg);
		}
	}
	
}

    


    /**
     * A placeholder fragment containing a simple view.
     */
  
}
