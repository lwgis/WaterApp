package cn.bjeastearth.waterapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.http.MapUtil;
import cn.bjeastearth.waterapp.model.FactoryGywn;
import cn.bjeastearth.waterapp.model.FactoryNcws;
import cn.bjeastearth.waterapp.model.FactorySzws;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.SewageFactory;
import cn.bjeastearth.waterapp.model.SewageFactoryManager;
import cn.bjeastearth.waterapp.myview.WebListView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SewageFactoryActivity extends Activity {
	MapView mapView = null;
	WebListView mListView = null;
	List<SewageFactory> sewageFactorys = null;
	ArcGISTiledMapServiceLayer tiledMapServiceLayer = null;
	GraphicsLayer drawCircleLayer = null;
	GraphicsLayer mGraphicsLayer;
	RelativeLayout mapInfoLayout = null;
	TextView firstTv = null;
	TextView secondTv = null;
	ImageView itemImageView = null;
	SewageFactoryAdapter mAdapter;
	SewageFactory currentFactory;
	@SuppressLint("HandlerLeak")
	private Handler myHandle = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.obj == null) {
				Toast.makeText(SewageFactoryActivity.this, "连接服务器失败,请稍候再试!",
						Toast.LENGTH_SHORT).show();
				SewageFactoryActivity.this.finish();
				return;
			}
			Gson gson = new Gson();
			SewageFactoryManager sewageFactoryManager = gson.fromJson(
					msg.obj.toString(), SewageFactoryManager.class);
			sewageFactorys = sewageFactoryManager.getSewageFactories();
			mAdapter = new SewageFactoryAdapter(SewageFactoryActivity.this,
					SewageFactoryActivity.this.sewageFactorys);
			SewageFactoryActivity.this.mListView.setAdapter(mAdapter);
			if (drawCircleLayer == null) {
				drawCircleLayer = new GraphicsLayer();
				mapView.addLayer(drawCircleLayer);
			}
			if (mGraphicsLayer == null) {
				mGraphicsLayer = new GraphicsLayer();
				mapView.addLayer(mGraphicsLayer);
			}
			for (SewageFactory factory : sewageFactorys) {
				Point onePoint = new Point(factory.getX(), factory.getY());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", String.valueOf(factory.getFID()));
				Drawable image = SewageFactoryActivity.this.getBaseContext()
						.getResources().getDrawable(getMapDrawable(factory));
				PictureMarkerSymbol symbol = new PictureMarkerSymbol(image);

				Graphic oneGraphic = new Graphic(onePoint, symbol, map);
				mGraphicsLayer.addGraphic(oneGraphic);
				DrawCircle(onePoint, factory.getFgbj(), 150, 0X11FF0000);
			}
			SewageFactoryActivity.this.mListView
					.setOnItemClickListener(mOnItemClickListener);

		}

	};
	private OnSingleTapListener mSingleTapListener = new OnSingleTapListener() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2997108987638467818L;

		@Override
		public void onSingleTap(float x, float y) {
			if (mGraphicsLayer != null && mGraphicsLayer.isInitialized()
					&& mGraphicsLayer.isVisible()) {
				Graphic result = null;
				// 检索当前 光标点（手指按压位置）的附近的 graphic对象
				result = GetGraphicsFromLayer(x, y, mGraphicsLayer);
				if (result != null) {
					// 获得附加特别的属性
					String hotProjectID = String.valueOf(result
							.getAttributeValue("id"));

					showMapInfo(hotProjectID);
				} else {
					mapInfoLayout.setVisibility(View.GONE);
				}
			}

		}
	};
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SewageFactory oneHotProject = (SewageFactory) mAdapter
					.getItem(position);
			showDetailActivity(oneHotProject);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_sewagefactory);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SewageFactoryActivity.this.finish();
			}
		});
		// 列表
		this.mListView = (WebListView) findViewById(R.id.sewageFactoryListView);
		this.mListView.showLoading();
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

		tabHost.addTab(tabHost.newTabSpec("mapView").setIndicator(tabMapView)
				.setContent(R.id.mapLayout));
		tabHost.addTab(tabHost.newTabSpec("listView").setIndicator(tabListView)
				.setContent(R.id.sewageFactoryListView));

		String mapURL = getString(R.string.mapBg);
		mapView = (MapView) findViewById(R.id.mapView);
		tiledMapServiceLayer = new ArcGISTiledMapServiceLayer(mapURL);
		mapView.addLayer(tiledMapServiceLayer);
		Envelope initextext = new Envelope(
				Double.parseDouble(getString(R.string.mapMinX)),
				Double.parseDouble(getString(R.string.mapMinY)),
				Double.parseDouble(getString(R.string.mapMaxX)),
				Double.parseDouble(getString(R.string.mapMaxY)));
		mapView.setExtent(initextext);
		// MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapWsgwngl));
		// MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapNcwscll));
		MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapJiaXing));
		new Thread(new httpThread()).start();
		mapInfoLayout = (RelativeLayout) findViewById(R.id.mapInfoLayout);
		mapInfoLayout.setVisibility(View.GONE);
		mapView.setOnSingleTapListener(mSingleTapListener);
		// 地图信息栏
		firstTv = (TextView) findViewById(R.id.firstTv);
		secondTv = (TextView) findViewById(R.id.secondTv);
		itemImageView = (ImageView) findViewById(R.id.itemImageView);
		Button btn = (Button) findViewById(R.id.showDetailBtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SewageFactoryActivity.this.showDetailActivity(currentFactory);
			}
		});
	}

	protected int getMapDrawable(SewageFactory factory) {
		if (factory.getClass().equals(FactorySzws.class)) {
			return R.drawable.map_item_szws;
		}
		if (factory.getClass().equals(FactoryGywn.class)) {
			return R.drawable.map_item_gywn;
		}
		if (factory.getClass().equals(FactoryNcws.class)) {
			return R.drawable.map_item_ncws;
		}
		return 0;
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

	public void DrawCircle(Point center, double radius, int alpha, int fillColor) {
		Polygon polygon = new Polygon();
		getCircle(center, radius, polygon);
		FillSymbol symbol = new SimpleFillSymbol(fillColor);
		symbol.setAlpha(alpha);

		Graphic g = new Graphic(polygon, symbol);
		drawCircleLayer.addGraphic(g);
	}

	private void getCircle(Point center, double radius, Polygon circle) {
		circle.setEmpty();
		Point[] points = getPoints(center, radius);
		circle.startPath(points[0]);
		for (int i = 1; i < points.length; i++)
			circle.lineTo(points[i]);
	}

	private Point[] getPoints(Point center, double radius) {
		Point[] points = new Point[50];
		double sin;
		double cos;
		double x;
		double y;
		for (double i = 0; i < 50; i++) {
			sin = Math.sin(Math.PI * 2 * i / 50);
			cos = Math.cos(Math.PI * 2 * i / 50);
			x = center.getX() + radius * sin;
			y = center.getY() + radius * cos;
			points[(int) i] = new Point(x, y);
		}
		return points;
	}

	protected void showMapInfo(String swId) {
		mapInfoLayout.setVisibility(View.VISIBLE);
		SewageFactory oneFactory = findHotProjectByid(swId);
		currentFactory = oneFactory;
		firstTv.setText(oneFactory.getTitle());
		secondTv.setText(oneFactory.getXzqName());
		itemImageView.setImageResource(R.drawable.imageview);
		if (oneFactory.getImages() != null && oneFactory.getImages().size() > 0) {
			ProjectImage projectImage = oneFactory.getImages().get(0);
			String url = this.getString(R.string.NewTileImgAddr)
					+ projectImage.getName();
			ImageLoader.getInstance().displayImage(url, itemImageView,
					ImageOptions.options);
		}

	}

	protected void showDetailActivity(SewageFactory factory) {
		Intent intent = new Intent(this, FieldItemActivity.class);
		intent.putExtra("Title", "污染处理厂信息");
		intent.putExtra("FieldItems", factory.getFieldItems());
		startActivity(intent);
	}

	private SewageFactory findHotProjectByid(String hotProjectID) {
		for (SewageFactory factory : sewageFactorys) {
			if (hotProjectID.equals(factory.getFID())) {
				return factory;
			}
		}
		return null;
	}
}
