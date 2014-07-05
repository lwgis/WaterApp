package cn.bjeastearth.waterapp;


import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class HotProjectActivity extends Activity {
	MapView mapView = null;
	ArcGISTiledMapServiceLayer tiledMapServiceLayer = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_hotproject);
		View tabListView = (View) LayoutInflater.from(this).inflate(R.layout.tab_item, null);  
        TextView text0 = (TextView) tabListView.findViewById(R.id.tab_label);  
        text0.setText("列表");  
          
        View tabMapView = (View) LayoutInflater.from(this).inflate(R.layout.tab_item, null);  
        TextView text1 = (TextView) tabMapView.findViewById(R.id.tab_label);  
        text1.setText("地图");  
          
          
        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);  
        tabHost.setup();   //Call setup() before adding tabs if loading TabHost using findViewById().   
          
        tabHost.addTab(tabHost.newTabSpec("listView").setIndicator(tabListView).setContent(R.id.hotprojectListView));  
        tabHost.addTab(tabHost.newTabSpec("mapView").setIndicator(tabMapView).setContent(R.id.mapViewHotProject));  
        
        String mapURL = "http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineStreetColor/MapServer";
		mapView = (MapView)findViewById(R.id.mapViewHotProject);
		tiledMapServiceLayer = new ArcGISTiledMapServiceLayer(mapURL);
		mapView.addLayer(tiledMapServiceLayer);
	}

}
