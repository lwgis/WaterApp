package cn.bjeastearth.waterapp;



import cn.bjeastearth.http.MapUtil;
import cn.bjeastearth.waterapp.location.Common;
import cn.bjeastearth.waterapp.location.LocationSvc;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LocationActivity extends Activity {
	private Button btnFinish;
	private Button btnLocation;
	private double x;
	private double y;
	private MapView mapView = null;
	private 	ArcGISTiledMapServiceLayer tiledMapServiceLayer = null;
	private GraphicsLayer mGraphicsLayer;
	private OnSingleTapListener mSingleTapListener=new OnSingleTapListener() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2827060711321208484L;

		@Override
		public void onSingleTap(float x, float y) {
				mGraphicsLayer.removeAll();
				Point onePoint = new Point(x,y);
				Point mapPoint=mapView.toMapPoint(onePoint);
				 LocationActivity.this.x=mapPoint.getX();
				LocationActivity.this.y=mapPoint.getY();
				Drawable image = LocationActivity.this.getBaseContext()
						.getResources().getDrawable(R.drawable.map_item_loc);
				PictureMarkerSymbol symbol = new PictureMarkerSymbol(image);
				Graphic oneGraphic = new Graphic(mapPoint, symbol);
				mGraphicsLayer.addGraphic(oneGraphic);
			}
	};

        
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_location);
		Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LocationActivity.this.finish();
			}
		});
		String mapURL = getString(R.string.mapBg);
		mapView = (MapView) findViewById(R.id.mapView);
		tiledMapServiceLayer = new ArcGISTiledMapServiceLayer(mapURL);
		mapView.addLayer(tiledMapServiceLayer);
		mGraphicsLayer=new GraphicsLayer();
		mapView.addLayer(mGraphicsLayer);
		MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapJiaXing));
		Envelope initextext = new Envelope(
				Double.parseDouble(getString(R.string.mapMinX)),
				Double.parseDouble(getString(R.string.mapMinY)),
				Double.parseDouble(getString(R.string.mapMaxX)),
				Double.parseDouble(getString(R.string.mapMaxY)));
		mapView.setExtent(initextext);
		mapView.setOnSingleTapListener(mSingleTapListener);
		this.btnFinish=(Button)findViewById(R.id.btnFinish);
		this.btnFinish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(LocationActivity.this,AddPsGyActivity.class);
				if (x!=y) {
					it.putExtra("X", x);
					it.putExtra("Y", y);
					LocationActivity.this.setResult(2, it);
				}
				LocationActivity.this.finish();
			}
		});
		this.btnLocation=(Button)findViewById(R.id.btnLocation);
		this.btnLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 注册广播
				IntentFilter filter = new IntentFilter();
				filter.addAction(Common.LOCATION_ACTION);
				LocationActivity.this.registerReceiver(new LocationBroadcastReceiver(), filter);

				// 启动服务
				Intent intent = new Intent();
				intent.setClass(LocationActivity.this, LocationSvc.class);
				LocationActivity.this.startService(intent);
			}
		});
	}

	private class LocationBroadcastReceiver extends BroadcastReceiver {
 
		@Override
		public void onReceive(Context context, Intent intent) {
			if (!intent.getAction().equals(Common.LOCATION_ACTION)) return;
			double x=intent.getExtras().getDouble(Common.X);
			double y=intent.getExtras().getDouble(Common.Y);
			mGraphicsLayer.removeAll();
			Point ptLatLon = new Point(x, y);
			SpatialReference sr4326 = SpatialReference.create(4326);
			Point ptMap = (Point) GeometryEngine.project(ptLatLon,
					sr4326, mapView.getSpatialReference());
			mapView.centerAt(ptMap, true);
			Drawable image = LocationActivity.this.getBaseContext()
					.getResources().getDrawable(R.drawable.map_item_loc);
			PictureMarkerSymbol symbol = new PictureMarkerSymbol(image);
			Graphic oneGraphic = new Graphic(ptMap, symbol);
			 LocationActivity.this.x=ptMap.getX();
			LocationActivity.this.y=ptMap.getY();
			mGraphicsLayer.addGraphic(oneGraphic);
			LocationActivity.this.unregisterReceiver(this);// 不需要时注销
		}
	}


}
