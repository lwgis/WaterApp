package cn.bjeastearth.waterapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.waterapp.model.PollutionSource;
import cn.bjeastearth.waterapp.model.PsFarmingManager;
import cn.bjeastearth.waterapp.model.PsIndustry;
import cn.bjeastearth.waterapp.model.PsLive;
import cn.bjeastearth.waterapp.model.PsManager;
import cn.bjeastearth.waterapp.model.PsType;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.myview.DpTransform;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class PollutionActivity extends Activity implements OnClickListener {
	private MapView mapView = null;
	private WebListView mListView = null;
	private ArcGISTiledMapServiceLayer tiledMapServiceLayer = null;
	private GraphicsLayer mGraphicsLayer;
	private RelativeLayout mapInfoLayout = null;
	private TabHost tabHost;
	private ArrayList<PollutionSource> mPollutionSources;
	private ArrayList<PollutionSource> mAllPollutionSources;
	private List<PsIndustry> mAllPsIndustries;
	private PsFarmingManager mPsFarmingManager;
	private List<PsLive> mAllPsLives;
	private PsManager mPsManager;
	private PollutionAdapter mAdapter;
	private Button btnGywry;
	private Button btnNywry;
	private Button btnShwry;
	private Button btnAllwry;
	private PollutionSource currentPs;
	private TextView firstTv = null;
	private TextView secondTv = null;
	private ImageView itemImageView = null;
	private AutoCompleteTextView mSearchEditView = null;
	private Button btnSearch;
	private PsType mPsType;
	private PopupWindow mAddPopupWindow;
	private Button btnAddPs;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what >= 0) {
				mAllPollutionSources = getAllPollutionSources(msg);
				update(PollutionActivity.this.mSearchEditView.getText()
						.toString());
				PollutionActivity.this.mListView
						.setOnItemClickListener(mOnItemClickListener);
				PollutionActivity.this.btnSearch
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								PollutionActivity.this
										.update(PollutionActivity.this.mSearchEditView
												.getText().toString());
								InputMethodManager im = (InputMethodManager) PollutionActivity.this
										.getSystemService(Context.INPUT_METHOD_SERVICE);
								im.hideSoftInputFromWindow(
										PollutionActivity.this.mSearchEditView
												.getWindowToken(),
										InputMethodManager.HIDE_NOT_ALWAYS);
//								PollutionActivity.this.mSearchEditView
//										.setCursorVisible(false);
//								PollutionActivity.this.btnSearch.requestFocus();
//								PollutionActivity.this.btnSearch
//										.requestFocusFromTouch();
//								PollutionActivity.this.mSearchEditView
//										.setCursorVisible(true);

							}
						});
				PollutionActivity.this.mSearchEditView
						.setOnEditorActionListener(new OnEditorActionListener() {

							@Override
							public boolean onEditorAction(TextView v,
									int actionId, KeyEvent event) {
								if (actionId == EditorInfo.IME_ACTION_SEARCH) {
									PollutionActivity.this
											.update(PollutionActivity.this.mSearchEditView
													.getText().toString());
									InputMethodManager im = (InputMethodManager) PollutionActivity.this
											.getSystemService(Context.INPUT_METHOD_SERVICE);
									im.hideSoftInputFromWindow(
											PollutionActivity.this.mSearchEditView
													.getWindowToken(),
											InputMethodManager.HIDE_NOT_ALWAYS);
								}
								return false;
							}
						});
			}
			if (msg.what == -1) {
				Gson gson = new Gson();
				List<Region> listRegions = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<Region>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Region region : listRegions) {
					arrayList.add(region.getName());
				}
				SearchAdapter<String> adapter = new SearchAdapter<String>(
						PollutionActivity.this, R.layout.autotext_item,
						arrayList);
				PollutionActivity.this.mSearchEditView.setAdapter(adapter);
			}
		}

	};
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			PollutionSource onePollutionSource = mPollutionSources
					.get(position);
			Intent intent = new Intent(PollutionActivity.this,
					FieldItemActivity.class);
			intent.putExtra("Title", "污染源信息");
			intent.putExtra("FieldItems", onePollutionSource.getFieldItems());
			startActivity(intent);
		}
	};
	private OnSingleTapListener mSingleTapListener = new OnSingleTapListener() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5233999380006567976L;

		@Override
		public void onSingleTap(float x, float y) {
			if (mGraphicsLayer != null && mGraphicsLayer.isInitialized()
					&& mGraphicsLayer.isVisible()) {
				Graphic result = null;
				// 检索当前 光标点（手指按压位置）的附近的 graphic对象
				result = GetGraphicsFromLayer(x, y, mGraphicsLayer);
				if (result != null) {
					// 获得附加特别的属性
					String pid = String
							.valueOf(result.getAttributeValue("PID"));
					showMapInfo(pid);
				} else {
					mapInfoLayout.setVisibility(View.GONE);
				}

			}

		}
	};
	private OnClickListener mLocationOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PollutionSource pollutionSource = (PollutionSource) v.getTag();
			PollutionActivity.this.showMapInfo(pollutionSource.getPID());
			tabHost.setCurrentTab(0);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_pollution);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PollutionActivity.this.finish();
			}
		});
		initAddPsBtn();
		// 列表
		this.mListView = (WebListView) findViewById(R.id.pollutionListView);
		this.mListView.showLoading();
		this.mSearchEditView = (AutoCompleteTextView) findViewById(R.id.SearchEditText);
		this.btnSearch = (Button) findViewById(R.id.btnSearch);
		// 地图
		View tabListView = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_item, null);
		TextView text0 = (TextView) tabListView.findViewById(R.id.tab_label);
		text0.setText("列表");

		View tabMapView = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_item, null);
		TextView text1 = (TextView) tabMapView.findViewById(R.id.tab_label);
		text1.setText("地图");

		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup(); // Call setup() before adding tabs if loading TabHost
							// using findViewById().
		tabHost.addTab(tabHost.newTabSpec("mapView").setIndicator(tabMapView)
				.setContent(R.id.mapLayout));
		tabHost.addTab(tabHost.newTabSpec("listView").setIndicator(tabListView)
				.setContent(R.id.listViewLayout));
		String mapURL = "http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineStreetColor/MapServer";
		mapView = (MapView) findViewById(R.id.mapView);
		tiledMapServiceLayer = new ArcGISTiledMapServiceLayer(mapURL);
		mapView.addLayer(tiledMapServiceLayer);
		Envelope initextext = new Envelope(12899459.4956466, 4815363.65520802,
				13004178.2243971, 4882704.67712717);
		mapView.setExtent(initextext);
		// new Thread(new httpThread()).start();
		mapInfoLayout = (RelativeLayout) findViewById(R.id.mapInfoLayout);
		mapInfoLayout.setVisibility(View.GONE);
		mapView.setOnSingleTapListener(mSingleTapListener);
		// 底部按钮
		this.btnGywry = (Button) findViewById(R.id.btnGywry);
		this.btnGywry.setOnClickListener(this);
		this.btnGywry.setSelected(true);
		this.btnNywry = (Button) findViewById(R.id.btnNywry);
		this.btnNywry.setOnClickListener(this);
		this.btnShwry = (Button) findViewById(R.id.btnShwry);
		this.btnShwry.setOnClickListener(this);
		this.btnAllwry = (Button) findViewById(R.id.btnAllwry);
		this.btnAllwry.setOnClickListener(this);
		this.mPsType = PsType.GY;
		new Thread(new HttpThread("all")).start();
		// 地图信息栏
		firstTv = (TextView) findViewById(R.id.firstTv);
		secondTv = (TextView) findViewById(R.id.secondTv);
		itemImageView = (ImageView) findViewById(R.id.itemImageView);
		Button btn = (Button) findViewById(R.id.showDetailBtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PollutionActivity.this,
						FieldItemActivity.class);
				intent.putExtra("Title", "污染源信息");
				intent.putExtra("FieldItems", currentPs.getFieldItems());
				startActivity(intent);
			}
		});
		new Thread(new HttpThread("xzq")).start();
	}

	//初始化新建按钮
	private void initAddPsBtn() {
		btnAddPs = (Button) findViewById(R.id.btnAddPs);
		final View popView = LayoutInflater.from(PollutionActivity.this)
				.inflate(R.layout.popupwindow_addps, null);
		btnAddPs.setOnClickListener(new OnClickListener() {
			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {
			
				if (mAddPopupWindow == null) {
					mAddPopupWindow = new PopupWindow(popView,DpTransform.dip2px(PollutionActivity.this, 100),DpTransform.dip2px(PollutionActivity.this, 250));
//					mAddPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//					mAddPopupWindow.setOutsideTouchable(true);
				}
				if (mAddPopupWindow.isShowing()) {
					mAddPopupWindow.dismiss();
				}
				else {
					mAddPopupWindow.showAsDropDown(btnAddPs,-DpTransform.dip2px(PollutionActivity.this, 45),DpTransform.dip2px(PollutionActivity.this, 4));
				}
			}
		});
		Button btnAddGyPs=(Button)popView.findViewById(R.id.btnAddGyPs);
		btnAddGyPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it=new Intent(PollutionActivity.this, AddPsGyActivity.class);
				PollutionActivity.this.startActivity(it);
				mAddPopupWindow.dismiss();
			}
		});
		Button btnAddXqyzPs=(Button)popView.findViewById(R.id.btnAddXqyzPs);
		btnAddXqyzPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it=new Intent(PollutionActivity.this, AddPsXqyzActivity.class);
				PollutionActivity.this.startActivity(it);
				mAddPopupWindow.dismiss();
				
			}
		});
		Button btnAddScyzPs=(Button)popView.findViewById(R.id.btnAddScyzPs);
		btnAddScyzPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(PollutionActivity.this, AddPsScyzActivity.class);
				PollutionActivity.this.startActivity(it);
				mAddPopupWindow.dismiss();
			}
		});
		Button btnAddZzPs=(Button)popView.findViewById(R.id.btnAddZzPs);
		btnAddZzPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(PollutionActivity.this, AddPsZzActivity.class);
				PollutionActivity.this.startActivity(it);
				mAddPopupWindow.dismiss();
			}
		});
		Button btnAddShPs=(Button)popView.findViewById(R.id.btnAddShPs);
		btnAddShPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(PollutionActivity.this, AddPsShActivity.class);
				PollutionActivity.this.startActivity(it);
				mAddPopupWindow.dismiss();
			}
		});
	}

	protected ArrayList<PollutionSource> getPollutionSources(String filter) {
		ArrayList<PollutionSource> sources = new ArrayList<PollutionSource>();
		if (mPsType.equals(PsType.ALL)) {
			for (PollutionSource pollutionSource : mAllPollutionSources) {
				if (filter.equals("")) {
					sources.add(pollutionSource);
				} else {
					if (pollutionSource.getXzq().getName().equals(filter)) {
						sources.add(pollutionSource);
					}
				}
			}
		} else {
			for (PollutionSource pollutionSource : mAllPollutionSources) {
				if (filter.equals("")) {
					if (pollutionSource.getPsType().equals(this.mPsType)) {
						sources.add(pollutionSource);
					}
				} else {
					if (pollutionSource.getPsType().equals(this.mPsType)
							&& pollutionSource.getXzq().getName()
									.equals(filter)) {
						sources.add(pollutionSource);
					}
				}
			}
		}
		return sources;
	}

	protected ArrayList<PollutionSource> getAllPollutionSources(Message msg) {
		ArrayList<PollutionSource> pollutionSources = null;
		Gson gson = new Gson();
		switch (msg.what) {
		case 0:// 工业
			mAllPsIndustries = gson.fromJson(msg.obj.toString(),
					new TypeToken<List<PsIndustry>>() {
					}.getType());
			pollutionSources = new ArrayList<PollutionSource>(mAllPsIndustries);
			break;
		case 1:// 农业
			mPsFarmingManager = gson.fromJson(msg.obj.toString(),
					PsFarmingManager.class);
			pollutionSources = new ArrayList<PollutionSource>();
			for (PollutionSource pollutionSource : mPsFarmingManager
					.getScyzwry()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsFarmingManager
					.getXqyzwry()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsFarmingManager.getZzwry()) {
				pollutionSources.add(pollutionSource);
			}
			break;
		case 2:// 生活
			mAllPsLives = gson.fromJson(msg.obj.toString(),
					new TypeToken<List<PsLive>>() {
					}.getType());
			pollutionSources = new ArrayList<PollutionSource>(mAllPsLives);
			break;
		case 3:// 全部
			mPsManager = gson.fromJson(msg.obj.toString(), PsManager.class);
			pollutionSources = new ArrayList<PollutionSource>();
			for (PollutionSource pollutionSource : mPsManager.getGys()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsManager.getNySource()
					.getScyzwry()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsManager.getNySource()
					.getXqyzwry()) {
				pollutionSources.add(pollutionSource);
			}
			for (PollutionSource pollutionSource : mPsManager.getNySource()
					.getZzwry()) {
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
		this.mGraphicsLayer.removeAll();
		switch (v.getId()) {
		case R.id.btnGywry:
			v.setSelected(true);
			mPsType = PsType.GY;
			break;
		case R.id.btnNywry:
			v.setSelected(true);
			mPsType = PsType.NY;
			break;
		case R.id.btnShwry:
			v.setSelected(true);
			mPsType = PsType.SH;
			break;
		case R.id.btnAllwry:
			v.setSelected(true);
			mPsType = PsType.ALL;
			break;
		default:
			break;
		}
		update(this.mSearchEditView.getText().toString());
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

	protected void showMapInfo(String pid) {
		mapInfoLayout.setVisibility(View.VISIBLE);
		PollutionSource pollutionSource = findHotProjectByid(pid);
		Point point = new Point(pollutionSource.getX(), pollutionSource.getY());
		mapView.centerAt(point, true);
		currentPs = pollutionSource;
		firstTv.setText(pollutionSource.getShowTitle());
		secondTv.setText(pollutionSource.getShowDescribing());
		if (pollutionSource.getImageString() != null) {
			String url = this.getString(R.string.NewTileImgAddr)
					+ pollutionSource.getImageString();
			ImageLoader.getInstance().displayImage(url, itemImageView,ImageOptions.options);
		}
		else {
			itemImageView.setImageResource(R.drawable.imageview);
		}

	}

	private PollutionSource findHotProjectByid(String psID) {
		for (PollutionSource pollutionSource : mPollutionSources) {
			if (psID.equals(pollutionSource.getPID())) {
				return pollutionSource;
			}
		}
		return null;
	}

	private void update(String filter) {
		mPollutionSources = getPollutionSources(filter);
		if (mAdapter == null) {
			mAdapter = new PollutionAdapter(PollutionActivity.this,
					mPollutionSources, mLocationOnClickListener);
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.refresh(mPollutionSources);
		}
		if (mGraphicsLayer == null) {
			mGraphicsLayer = new GraphicsLayer();
			mapView.addLayer(mGraphicsLayer);
		}
		mGraphicsLayer.removeAll();
		for (PollutionSource pollutionSource : mPollutionSources) {
			Point onePoint = new Point(pollutionSource.getX(),
					pollutionSource.getY());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PID", pollutionSource.getPID());
			PictureMarkerSymbol symbol = new PictureMarkerSymbol(
					pollutionSource.getMapDrawable(PollutionActivity.this));
			Graphic oneGraphic = new Graphic(onePoint, symbol, map);
			mGraphicsLayer.addGraphic(oneGraphic);
		}
		mapInfoLayout.setVisibility(View.GONE);
	}

	class HttpThread implements Runnable {
		String pollutionTpyeString = "gywry";

		public HttpThread(String type) {
			pollutionTpyeString = type;
		}

		@Override
		public void run() {
			if (pollutionTpyeString.equals("xzq")) {
				String jsonString = HttpUtil.getDectionaryString(pollutionTpyeString);
				Message msg = new Message();
				msg.what = -1;
				if (!jsonString.equals("")) {
					msg.obj = jsonString;
					mHandler.sendMessage(msg);
				}
			} else {
				String jsonString = HttpUtil
						.getAllPollutionString(pollutionTpyeString);
				if (!jsonString.equals("")) {
					Message msg = new Message();
					if (pollutionTpyeString.equals("gywry")) {
						msg.what = 0;
					}
					if (pollutionTpyeString.equals("nywry")) {
						msg.what = 1;
					}
					if (pollutionTpyeString.equals("shws")) {
						msg.what = 2;
					}
					if (pollutionTpyeString.equals("all")) {
						msg.what = 3;
					}
					msg.obj = jsonString;
					mHandler.sendMessage(msg);
				}
			}
		}
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mAddPopupWindow != null && mAddPopupWindow.isShowing()) {
			mAddPopupWindow.dismiss();
			mAddPopupWindow = null;
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}


}
