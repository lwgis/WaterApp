package cn.bjeastearth.waterapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.imageload.ImageLoader;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.SewageFactory;
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

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SewageFactoryActivity extends Activity {
	MapView mapView = null;
	WebListView mListView = null;
	List<SewageFactory> allProjects=null;
	ArcGISTiledMapServiceLayer tiledMapServiceLayer = null;
	GraphicsLayer mGraphicsLayer;
	RelativeLayout mapInfoLayout=null;
	TextView firstTv=null;
	TextView secondTv=null;
	ImageView itemImageView=null;
	ImageLoader mImageLoader;
	SewageFactoryAdapter mAdapter;
	SewageFactory currentFactory;
	@SuppressLint("HandlerLeak")
	private Handler myHandle = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Gson gson = new Gson();
			allProjects = gson.fromJson(msg.obj.toString(),
					new TypeToken<List<SewageFactory>>() {
					}.getType());
			 mAdapter =new SewageFactoryAdapter(SewageFactoryActivity.this, SewageFactoryActivity.this.allProjects);
			SewageFactoryActivity.this.mListView.setAdapter(mAdapter);
			if (mGraphicsLayer == null) {
				mGraphicsLayer = new GraphicsLayer();
				mapView.addLayer(mGraphicsLayer);
			}
			for (SewageFactory factory : allProjects) {
				Point onePoint = new Point(factory.getX(), factory.getY());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", String.valueOf(factory.getID()));
				Drawable image = SewageFactoryActivity.this.getBaseContext()
						.getResources().getDrawable(R.drawable.map_item);
				PictureMarkerSymbol symbol = new PictureMarkerSymbol(image);
				
				Graphic oneGraphic = new Graphic(onePoint, symbol, map);
				mGraphicsLayer.addGraphic(oneGraphic);
			}

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
	private OnItemClickListener mOnItemClickListener= new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SewageFactory oneHotProject=(SewageFactory)mAdapter.getItem(position);
			showDetailActivity(oneHotProject);			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_sewagefactory);
		Button backButton=(Button)findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SewageFactoryActivity.this.finish();
			}
		});
		// 列表
		this.mListView = (WebListView) findViewById(R.id.sewageFactoryListView);
		this.mListView.showLoading();
		this.mListView.setOnItemClickListener(mOnItemClickListener);
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
						.setContent(R.id.sewageFactoryListView));
				tabHost.addTab(tabHost.newTabSpec("mapView").setIndicator(tabMapView)
						.setContent(R.id.mapLayout));

				String mapURL = "http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineStreetColor/MapServer";
				mapView = (MapView) findViewById(R.id.mapView);
				tiledMapServiceLayer = new ArcGISTiledMapServiceLayer(mapURL);
				mapView.addLayer(tiledMapServiceLayer);
				Envelope initextext = new Envelope(12899459.4956466, 4815363.65520802,
						13004178.2243971, 4882704.67712717);
				mapView.setExtent(initextext);
				new Thread(new httpThread()).start();
				mapInfoLayout=(RelativeLayout)findViewById(R.id.mapInfoLayout);
				mapInfoLayout.setVisibility(View.GONE);
				mapView.setOnSingleTapListener(mSingleTapListener);
				//地图信息栏
				firstTv=(TextView)findViewById(R.id.firstTv);
				secondTv=(TextView)findViewById(R.id.secondTv);
				itemImageView=(ImageView)findViewById(R.id.itemImageView);
				Button btn=(Button)findViewById(R.id.showDetailBtn);
				btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						SewageFactoryActivity.this.showDetailActivity(currentFactory);
					}
				});
	}
	class httpThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String jsonString = HttpUtil.getAllSewageFactoryString();
			if (jsonString != "") {
				Message msg = new Message();
				msg.obj = jsonString;
				myHandle.sendMessage(msg);
			}
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
		SewageFactory oneFactory=findHotProjectByid(swId);
		currentFactory=oneFactory;
		firstTv.setText(oneFactory.getName());
		secondTv.setText("类型:"+oneFactory.getType().getName());
		if (oneFactory.getImages()!=null&&oneFactory.getImages().size()>0) {
			if (mImageLoader==null) {
				mImageLoader=new ImageLoader(this);
			}
			ProjectImage projectImage=oneFactory.getImages().get(0);
			String url=this.getString(R.string.NewTileImgAddr)+projectImage.getName();
			mImageLoader.DisplayImage(url, itemImageView, false);
		}

	}
	protected void showDetailActivity(SewageFactory factory) {
		Intent intent = new Intent(this, FieldItemActivity.class);  
		intent.putExtra("Title", "污染处理厂信息");
		intent.putExtra("FieldItems", factory.getFieldItems());  
		startActivity(intent); 
	}
	
	private SewageFactory findHotProjectByid(int hotProjectID) {
		for (SewageFactory factory : allProjects) {
			if (hotProjectID==factory.getID()) {
				return factory;
			}
		}
		return null;
	}
}
