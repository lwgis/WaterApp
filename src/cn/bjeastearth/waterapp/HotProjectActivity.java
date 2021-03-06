package cn.bjeastearth.waterapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.http.MapUtil;
import cn.bjeastearth.waterapp.model.HotProject;
import cn.bjeastearth.waterapp.model.ProjectImage;
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
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class HotProjectActivity extends Activity {
	MapView mapView = null;
	WebListView mListView = null;
	List<HotProject> allProjects = null;
	ArcGISTiledMapServiceLayer tiledMapServiceLayer = null;
	GraphicsLayer mGraphicsLayer;
	RelativeLayout mapInfoLayout = null;
	TextView tvHpName = null;
	TextView tvHpJd = null;
	ImageView imHp = null;
	HotProjectAdapter mAdapter;
	HotProject currentHotProject;
	Button btnSearch;
	AutoCompleteTextView mSearchEditView;
	@SuppressLint("HandlerLeak")
	private Handler myHandle = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Gson gson = new Gson();
			if (msg.obj != null) {

				allProjects = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<HotProject>>() {
						}.getType());
				HotProjectActivity.this.mListView.setAdapter(mAdapter);
				if (mGraphicsLayer == null) {
					mGraphicsLayer = new GraphicsLayer();
					mapView.addLayer(mGraphicsLayer);
				}
				update("");
				HotProjectActivity.this.btnSearch.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						InputMethodManager im = (InputMethodManager) HotProjectActivity.this
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						im.hideSoftInputFromWindow(
								HotProjectActivity.this.mSearchEditView.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
						HotProjectActivity.this.update(HotProjectActivity.this.mSearchEditView.getText().toString());
					}
				});
			} else {
				Toast.makeText(HotProjectActivity.this, "连接服务器失败,请稍候再试!",
						Toast.LENGTH_SHORT).show();
				HotProjectActivity.this.finish();
			}
		}

	};
	private OnSingleTapListener mSingleTapListener = new OnSingleTapListener() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7148750396345079812L;

		@Override
		public void onSingleTap(float x, float y) {
			if (mGraphicsLayer != null && mGraphicsLayer.isInitialized()
					&& mGraphicsLayer.isVisible()) {
				Graphic result = null;
				// 检索当前 光标点（手指按压位置）的附近的 graphic对象
				result = GetGraphicsFromLayer(x, y, mGraphicsLayer);
				if (result != null) {
					// 获得附加特别的属性
					int hotProjectID = Integer.parseInt(String.valueOf(result
							.getAttributeValue("id")));

					showMapInfo(hotProjectID);
				} else {
					mapInfoLayout.setVisibility(View.GONE);
				}
			}// end if

		}
	};
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			HotProject oneHotProject = (HotProject) mAdapter.getItem(position);
			showDetailActivity(oneHotProject);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_hotproject);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HotProjectActivity.this.finish();
			}
		});
		Button btnCount=(Button)findViewById(R.id.btnCount);
		btnCount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it=new Intent(HotProjectActivity.this, CountHotProjectActivity.class);
				HotProjectActivity.this.startActivity(it);
			}
		});
		btnSearch = (Button) findViewById(R.id.btnSearch);
		mSearchEditView = (AutoCompleteTextView) findViewById(R.id.SearchEditText);
		// 列表
		this.mListView = (WebListView) findViewById(R.id.hotprojectListView);
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

		tabHost.addTab(tabHost.newTabSpec("mapView").setIndicator(tabMapView)
				.setContent(R.id.mapLayout));
		tabHost.addTab(tabHost.newTabSpec("listView").setIndicator(tabListView)
				.setContent(R.id.hotprojectListView));

		String mapURL = getString(R.string.mapBg);
		mapView = (MapView) findViewById(R.id.mapViewHotProject);
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
		tvHpName = (TextView) findViewById(R.id.hpNameTextView);
		tvHpJd = (TextView) findViewById(R.id.hpGcjdTextView);
		imHp = (ImageView) findViewById(R.id.hpImageView);
		Button btn = (Button) findViewById(R.id.hpShowDetailBtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HotProjectActivity.this.showDetailActivity(currentHotProject);
			}
		});
	}

	protected void showDetailActivity(HotProject oneHotProject) {
		Intent intent = new Intent(this, FieldItemActivity.class);
		intent.putExtra("Title", "重大项目信息");
		intent.putExtra("FieldItems", oneHotProject.getFieldItems());
		startActivity(intent);
	}

	protected void showMapInfo(int hotProjectID) {
		mapInfoLayout.setVisibility(View.VISIBLE);
		HotProject oneHotProject = findHotProjectByid(hotProjectID);
		currentHotProject = oneHotProject;
		tvHpName.setText(oneHotProject.getName());
		tvHpJd.setText("工程进度:" + oneHotProject.getGcjd() + "%");
		if (oneHotProject.getImages() != null
				&& oneHotProject.getImages().size() > 0) {
			ProjectImage projectImage = oneHotProject.getImages().get(0);
			String url = this.getString(R.string.NewTileImgAddr)
					+ projectImage.getName();
			ImageLoader.getInstance().displayImage(url, imHp,
					ImageOptions.options);
		}

	}

	private HotProject findHotProjectByid(int hotProjectID) {
		for (HotProject pHotProject : allProjects) {
			if (hotProjectID == pHotProject.getID()) {
				return pHotProject;
			}
		}
		return null;
	}

	class httpThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String jsonString = HttpUtil.getHotProjectString();
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

	private void update(String filter) {
		mGraphicsLayer.removeAll();
		ArrayList<HotProject> hotProjects = new ArrayList<HotProject>();
		if (mSearchEditView.getText().toString().equals("")) {
			hotProjects = (ArrayList<HotProject>) allProjects;
		} else {
			for (HotProject hotProject : allProjects) {
				if (hotProject.getName().contains(filter)
						|| hotProject.getXzq().getName().contains(filter)
						|| hotProject.getStartTime().contains(filter)
						|| hotProject.getEndTime().contains(filter)) {
					hotProjects.add(hotProject);
				}
			}
		}
		mAdapter = new HotProjectAdapter(HotProjectActivity.this, hotProjects);
		HotProjectActivity.this.mListView.setAdapter(mAdapter);
		for (HotProject hotProject : hotProjects) {
			Point onePoint = new Point(hotProject.getX(), hotProject.getY());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", String.valueOf(hotProject.getID()));
			Drawable image = HotProjectActivity.this.getBaseContext()
					.getResources().getDrawable(R.drawable.map_item_hotproject);
			PictureMarkerSymbol symbol = new PictureMarkerSymbol(image);

			Graphic oneGraphic = new Graphic(onePoint, symbol, map);
			mGraphicsLayer.addGraphic(oneGraphic);
		}
	}
}
