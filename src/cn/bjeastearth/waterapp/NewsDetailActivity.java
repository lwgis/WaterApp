package cn.bjeastearth.waterapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;

public class NewsDetailActivity extends Activity {
private WebView webView;
private Button btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_news_detail);
		btnBack=(Button)findViewById(R.id.btnNewsBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NewsDetailActivity.this.finish();
			}
		});
		webView=(WebView)findViewById(R.id.newsWebView);
		
        webView.getSettings().setDefaultTextEncodingName("UTF-8") ;
		Bundle bl=getIntent().getExtras();
		String content=bl.getString("content");
		webView.loadData(content, "text/html; charset=UTF-8", null);
		
		
	}
	

}
