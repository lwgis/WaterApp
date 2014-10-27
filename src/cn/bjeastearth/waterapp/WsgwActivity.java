package cn.bjeastearth.waterapp;

import java.lang.ref.WeakReference;

import cn.bjeastearth.http.MapUtil;
import cn.bjeastearth.waterapp.model.Wsgw;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer.MODE;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureSet;
import com.esri.core.tasks.SpatialRelationship;
import com.esri.core.tasks.ags.query.Query;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WsgwActivity extends Activity {
	private LinearLayout infolayout;
	private TextView nameTv;
	private TextView wscllTv;
	private TextView wsgwnglTv;
	private MapView mapView;
	private ArcGISFeatureLayer wsgwFeatureLayer = null;
	private MyHandler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_wsgw);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WsgwActivity.this.finish();
			}
		});
		mHandler = new MyHandler(this);
		mapView = (MapView) findViewById(R.id.mapView);
		infolayout = (LinearLayout) findViewById(R.id.infoLayout);
		nameTv = (TextView) findViewById(R.id.nameTv);
		wscllTv = (TextView) findViewById(R.id.wscllTv);
		wsgwnglTv = (TextView) findViewById(R.id.wsgwnwlTv);
		String mapURL = getString(R.string.mapBg);
		ArcGISTiledMapServiceLayer tiledMapServiceLayer = new ArcGISTiledMapServiceLayer(
				mapURL);
		mapView.addLayer(tiledMapServiceLayer);
		Envelope initextext = new Envelope(
				Double.parseDouble(getString(R.string.mapMinX)),
				Double.parseDouble(getString(R.string.mapMinY)),
				Double.parseDouble(getString(R.string.mapMaxX)),
				Double.parseDouble(getString(R.string.mapMaxY)));
		mapView.setExtent(initextext);
		MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapNcwscll));
		MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapJiaXing));
		// MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapWsgwngl));
		wsgwFeatureLayer = new ArcGISFeatureLayer(
				"http://159.226.110.64:8001/ags/rest/services/wsngl/FeatureServer/0",
				MODE.SELECTION);
		mapView.addLayer(wsgwFeatureLayer);
		infolayout.setVisibility(View.GONE);
		mapView.setOnSingleTapListener(new OnSingleTapListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 9094707757568466575L;

			@Override
			public void onSingleTap(float x, float y) {
				Point pt = mapView.toMapPoint(x, y);
				Query query = new Query();
				// query.setOutFields(new String[] { "*" });
				query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
				query.setGeometry(pt);
				query.setInSpatialReference(mapView.getSpatialReference());
				 wsgwFeatureLayer.selectFeatures(query,
				 ArcGISFeatureLayer.SELECTION_METHOD.NEW,
				 new CallbackListener<FeatureSet>() {
				
				 @Override
				 public void onError(Throwable e) {
				 // TODO Auto-generated method stub
				
				 }
				
				 @Override
				 public void onCallback(FeatureSet queryResults) {
				 if (queryResults.getGraphics().length > 0) {
				 String nameString = queryResults
				 .getGraphics()[0]
				 .getAttributeValue("NAME")
				 .toString();
				 String wscllString = queryResults
				 .getGraphics()[0]
				 .getAttributeValue("WSCLL")
				 .toString();
				 String wsgwnglString = queryResults
				 .getGraphics()[0]
				 .getAttributeValue("WSGWNGL")
				 .toString();
				 Wsgw wsgw = new Wsgw();
				 wsgw.setName(nameString);
				 wsgw.setWscll(wscllString);
				 wsgw.setWsgwngl(wsgwnglString);
				 Message msg = WsgwActivity.this.mHandler
				 .obtainMessage();
				 msg.what = 1;
				 msg.obj = wsgw;
				 msg.sendToTarget();
				 } else {
				 Message msg = WsgwActivity.this.mHandler
				 .obtainMessage();
				 msg.what = 0;
				 msg.sendToTarget();
				 }
				 }
				 });
			}
		});
	}

	static class MyHandler extends Handler {
		WeakReference<WsgwActivity> activityReference;

		public MyHandler(WsgwActivity activity) {
			activityReference = new WeakReference<WsgwActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				Wsgw wsgw = (Wsgw) msg.obj;
				activityReference.get().showInfo(wsgw);
			} else {
				activityReference.get().hideInfo();
			}

		}
	}

	public void hideInfo() {
		infolayout.setVisibility(View.GONE);
	}

	public void showInfo(Wsgw wsgw) {
		infolayout.setVisibility(View.VISIBLE);
		nameTv.setText("名称:  " + wsgw.getName());
		wscllTv.setText("污水处理率: " + wsgw.getWscll());
		wsgwnglTv.setText("污水管网纳管率: " + wsgw.getWsgwngl());
	}
}
