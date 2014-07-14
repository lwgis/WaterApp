package cn.bjeastearth.waterapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.imageload.ImageLoader;
import cn.bjeastearth.waterapp.model.PollutionSource;
import cn.bjeastearth.waterapp.model.PsFarmingManager;
import cn.bjeastearth.waterapp.model.PsIndustry;
import cn.bjeastearth.waterapp.model.PsLive;
import cn.bjeastearth.waterapp.model.PsManager;
import cn.bjeastearth.waterapp.myview.WebListView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class PollutionActivity extends Activity implements OnClickListener{
	MapView mapView = null;
	WebListView mListView = null;
	ArcGISTiledMapServiceLayer tiledMapServiceLayer = null;
	GraphicsLayer mGraphicsLayer;
	RelativeLayout mapInfoLayout=null;
	ArrayList<PollutionSource> mAllPollutionSources;
	List<PsIndustry> mAllPsIndustries;
	PsFarmingManager mPsFarmingManager;
	List<PsLive> mAllPsLives;
	PsManager mPsManager;
	PollutionAdapter mAdapter;
	Button btnGywry;
	Button btnNywry;
	Button btnShwry;
	Button btnAllwry;
	PollutionSource currentPsIndustry;
	ImageLoader mImageLoader;
	TextView firstTv=null;
	TextView secondTv=null;
	ImageView itemImageView=null;
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		
//			mAllPollutionSources=new ArrayList<PollutionSource>(mAllPsIndustries);
			mAllPollutionSources=getAllPollutionSources(msg);

			mAdapter=new PollutionAdapter(PollutionActivity.this, mAllPollutionSources);
			mListView.setAdapter(mAdapter);
			if (mGraphicsLayer == null) {
				mGraphicsLayer = new GraphicsLayer();
				mapView.addLayer(mGraphicsLayer);
			}
			mGraphicsLayer.removeAll();
			for (PollutionSource pollutionSource : mAllPollutionSources) {
				Point onePoint = new Point(pollutionSource.getX(), pollutionSource.getY());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", String.valueOf(pollutionSource.getID()));
				PictureMarkerSymbol symbol = new PictureMarkerSymbol(pollutionSource.getMapDrawable(PollutionActivity.this));
				Graphic oneGraphic = new Graphic(onePoint, symbol, map);
				mGraphicsLayer.addGraphic(oneGraphic);
			}
			PollutionActivity.this.mListView.setOnItemClickListener(mOnItemClickListener);
		}
		
	};
	private OnItemClickListener mOnItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			PollutionSource onePollutionSource=mAllPollutionSources.get(position);
			Intent intent = new Intent(PollutionActivity.this, FieldItemActivity.class);  
			intent.putExtra("Title", "污染源信息");
			intent.putExtra("FieldItems", onePollutionSource.getFieldItems());  
			startActivity(intent); 
		}
	};
	private	OnSingleTapListener mSingleTapListener=new OnSingleTapListener() {
		
		@Override
		public void onSingleTap(float x, float y) {
			if (mGraphicsLayer != null && mGraphicsLayer.isInitialized() && mGraphicsLayer.isVisible()) {
				Graphic result = null;
				// 检索当前 光标点（手指按压位置）的附近的 graphic对象
				result = GetGraphicsFromLayer(x, y, mGraphicsLayer);
				if (result != null) {
					// 获得附加特别的属性
					int hotProjectID = Integer.parseInt(String.valueOf(result
							.getAttributeValue("id")));

					showMapInfo(hotProjectID);
				}
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_pollution);
		Button backButton=(Button)findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PollutionActivity.this.finish();
			}
		});
		// 列表
		this.mListView = (WebListView) findViewById(R.id.pollutionListView);
		this.mListView.showLoading();
//		this.mListView.setOnItemClickListener(mOnItemClickListener);
		// 地图
		View tabListView = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_item, null);
		TextView text0 = (TextView) tabListView.findViewById(R.id.tab_label);
		text0.setText("列表");

		View tabMapView = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_item, null);
		TextView text1 = (TextView) tabMapView.findViewById(R.id.tab_label);
		text1.setText("地图");

		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup(); // Call setup() before adding tabs if loading TabHost
							// using findViewById().

		tabHost.addTab(tabHost.newTabSpec("listView").setIndicator(tabListView)
				.setContent(R.id.pollutionListView));
		tabHost.addTab(tabHost.newTabSpec("mapView").setIndicator(tabMapView)
				.setContent(R.id.mapLayout));

		String mapURL = "http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineStreetColor/MapServer";
		mapView = (MapView) findViewById(R.id.mapView);
		tiledMapServiceLayer = new ArcGISTiledMapServiceLayer(mapURL);
		mapView.addLayer(tiledMapServiceLayer);
		Envelope initextext = new Envelope(12899459.4956466, 4815363.65520802,
				13004178.2243971, 4882704.67712717);
		mapView.setExtent(initextext);
//		new Thread(new httpThread()).start();
		mapInfoLayout=(RelativeLayout)findViewById(R.id.mapInfoLayout);
		mapInfoLayout.setVisibility(View.GONE);
		mapView.setOnSingleTapListener(mSingleTapListener);
		//底部按钮
		this.btnGywry=(Button)findViewById(R.id.btnGywry);
		this.btnGywry.setOnClickListener(this);
		this.btnGywry.setSelected(true);
		this.btnNywry=(Button)findViewById(R.id.btnNywry);
		this.btnNywry.setOnClickListener(this);
		this.btnShwry=(Button)findViewById(R.id.btnShwry);
		this.btnShwry.setOnClickListener(this);
		this.btnAllwry=(Button)findViewById(R.id.btnAllwry);
		this.btnAllwry.setOnClickListener(this);
		new Thread(new HttpThread("gywry")).start();
		//地图信息栏
		firstTv=(TextView)findViewById(R.id.firstTv);
		secondTv=(TextView)findViewById(R.id.secondTv);
		itemImageView=(ImageView)findViewById(R.id.itemImageView);
		Button btn=(Button)findViewById(R.id.showDetailBtn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PollutionActivity.this, FieldItemActivity.class);  
				intent.putExtra("Title", "污染源信息");
				intent.putExtra("FieldItems", currentPsIndustry.getFieldItems());  
				startActivity(intent); 
			}
		});
	}
	protected ArrayList<PollutionSource> getAllPollutionSources(Message msg) {
		ArrayList<PollutionSource> pollutionSources=null;
		Gson gson=new Gson();
		switch (msg.what) {
		case 0://工业
			mAllPsIndustries= gson.fromJson(msg.obj.toString(),
					new TypeToken<List<PsIndustry>>() {
					}.getType());
			pollutionSources=new ArrayList<PollutionSource>(mAllPsIndustries);
			break;
		case 1://农业
			mPsFarmingManager=gson.fromJson(msg.obj.toString(), PsFarmingManager.class);
			pollutionSources =new ArrayList<PollutionSource>();
			for (PollutionSource pollutionSource : mPsFarmingManager.getScyzwry()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsFarmingManager.getXqyzwry()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsFarmingManager.getZzwry()) {
				pollutionSources.add(pollutionSource);
			}
			break;
		case 2://生活
			mAllPsLives=gson.fromJson(msg.obj.toString(), new TypeToken<List<PsLive>>(){}.getType());
			pollutionSources=new ArrayList<PollutionSource>(mAllPsLives);
			break;
		case 3://全部
			mPsManager=gson.fromJson(msg.obj.toString(), PsManager.class);
			pollutionSources =new ArrayList<PollutionSource>();
			for (PollutionSource pollutionSource : mPsManager.getGys()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsManager.getNySource().getScyzwry()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsManager.getNySource().getXqyzwry()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsManager.getNySource().getZzwry()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsManager.getShs()) {
				pollutionSources.add(pollutionSource);
			}
			break;
		default:
			break;
		}
		return pollutionSources;
	}
	@Override
	public void onClick(View v) {
		if (v.isSelected()) {
			return;
		}
		this.btnGywry.setSelected(false);
		this.btnNywry.setSelected(false);
		this.btnShwry.setSelected(false);
		this.btnAllwry.setSelected(false);
		this.mListView.showLoading();
		this.mGraphicsLayer.removeAll();
		switch (v.getId()) {
		case R.id.btnGywry:v.setSelected(true);
		new Thread(new HttpThread("gywry")).start();
			break;
		case R.id.btnNywry:v.setSelected(true);
		new Thread(new HttpThread("nywry")).start();
		break;
		case R.id.btnShwry:v.setSelected(true);
		new Thread(new HttpThread("shws")).start();
		break;
		case R.id.btnAllwry:v.setSelected(true);
		new Thread(new HttpThread("all")).start();
		break;
		default:
			break;
		}
		
	}
	/*
	 * 从一个图层里里 查找获得 Graphics对象. x,y是屏幕坐标,layer
	 * 是GraphicsLayer目标图层（要查找的）。相差的距离是50像素。
	 */
	private Graphic GetGraphicsFromLayer(double xScreen, double yScreen,
			GraphicsLayer layer) {
		Graphic result = null;
		try {
			int[] idsArr = layer.getGraphicIDs();
			double x = xScreen;
			double y = yScreen;
			for (int i = 0; i < idsArr.length; i++) {
				Graphic gpVar = layer.getGraphic(idsArr[i]);
				if (gpVar != null) {
					Point pointVar = (Point) gpVar.getGeometry();
					pointVar = mapView.toScreenPoint(pointVar);
					double x1 = pointVar.getX();
					double y1 = pointVar.getY();
					if (Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1)) < 50) {
						result = gpVar;
						break;
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return result;
	}
	protected void showMapInfo(int swId) {
		mapInfoLayout.setVisibility(View.VISIBLE);
		PollutionSource pollutionSource=findHotProjectByid(swId);
		currentPsIndustry=pollutionSource;
		firstTv.setText(pollutionSource.getShowTitle());
		secondTv.setText(pollutionSource.getShowDescribing());
		if (pollutionSource.getImageString()!=null) {
			if (mImageLoader==null) {
				mImageLoader=new ImageLoader(this);
			}
			String url=this.getString(R.string.NewTileImgAddr)+pollutionSource.getImageString();
			mImageLoader.DisplayImage(url, itemImageView, false);
		}

	}
	private PollutionSource findHotProjectByid(int psID) {
		for (PollutionSource pollutionSource : mAllPollutionSources) {
			if (psID==pollutionSource.getID()) {
				return pollutionSource;
			}
		}
		return null;
	}
	class HttpThread implements Runnable {
		String pollutionTpyeString="gywry";
		private HttpThread(){
			
		}
		public HttpThread(String type){
			pollutionTpyeString=type;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String jsonString = HttpUtil.getAllPollutionString(pollutionTpyeString);
			if (jsonString != "") {
				Message msg = new Message();
				if (pollutionTpyeString.equals("gywry")) {
					msg.what=0;
				}
				if (pollutionTpyeString.equals("nywry")) {
					msg.what=1;
				}
				if (pollutionTpyeString.equals("shws")) {
					msg.what=2;
				}
				if (pollutionTpyeString.equals("all")) {
					msg.what=3;
				}
				msg.obj = jsonString;
				mHandler.sendMessage(msg);
			}
		}
	}

}