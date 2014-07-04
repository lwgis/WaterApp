package cn.bjeastearth.waterapp;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class HotProjectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_hotproject);
		View listView = (View) LayoutInflater.from(this).inflate(R.layout.tab_item, null);  
        TextView text0 = (TextView) listView.findViewById(R.id.tab_label);  
        text0.setText("列表");  
          
        View mapView = (View) LayoutInflater.from(this).inflate(R.layout.tab_item, null);  
        TextView text1 = (TextView) mapView.findViewById(R.id.tab_label);  
        text1.setText("地图");  
          
          
        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);  
        tabHost.setup();   //Call setup() before adding tabs if loading TabHost using findViewById().   
          
        tabHost.addTab(tabHost.newTabSpec("listView").setIndicator(listView).setContent(R.id.hotprojectListView));  
        tabHost.addTab(tabHost.newTabSpec("mapView").setIndicator(mapView).setContent(R.id.view2));  
	}

}
