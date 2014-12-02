package cn.bjeastearth.waterapp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.http.MapUtil;
import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.model.PollutionSource;
import cn.bjeastearth.waterapp.model.PsFarmingManager;
import cn.bjeastearth.waterapp.model.PsIndustry;
import cn.bjeastearth.waterapp.model.PsLive;
import cn.bjeastearth.waterapp.model.PsManager;
import cn.bjeastearth.waterapp.model.PsType;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.model.River;
import cn.bjeastearth.waterapp.myview.WebListView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer.MODE;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.tasks.SpatialRelationship;
import com.esri.core.tasks.ags.query.Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;

public class RiverActivity extends Activity implements OnClickListener {
	private final int MSG_XZQ = -1;
	private final int MSG_ALLRIVER = 1;
	private final int MSG_AllPs = 2;
	private final String riverUrlhdlx = "http://159.226.110.64:8001/ags/rest/services/sanhe/MapServer";
	private final String riverUrlhddj = "http://159.226.110.64:8001/ags/rest/services/grade/MapServer";
	private final String riverUrlhdsz = "http://159.226.110.64:8001/ags/rest/services/hdsz/MapServer";
	private MapView mapView = null;
	private WebListView mListView = null;
	private ArcGISTiledMapServiceLayer tiledMapServiceLayer = null;
	private ArcGISDynamicMapServiceLayer riverLayer = null;
	private ArcGISFeatureLayer riverFeatureLayer = null;
	private GraphicsLayer mGraphicsLayer;
	private RelativeLayout mapInfoLayout = null;
	private TabHost tabHost;
	private List<River> mRivers;
	private ArrayList<PollutionSource> mAllPollutionSources;
	private ArrayList<PollutionSource> mPollutionSources;
	private List<PsIndustry> mAllPsIndustries;
	private PsFarmingManager mPsFarmingManager;
	private List<PsLive> mAllPsLives;
	private PsManager mPsManager;
	private RiverAdapter mAdapter;
	private Button btnGywry;
	private Button btnNywry;
	private Button btnShwry;
	private Button btnAllwry;
	private TextView firstTv = null;
	private TextView secondTv = null;
	private ImageView itemImageView = null;
	private AutoCompleteTextView mSearchEditView = null;
	private Spinner mapSpinner = null;
	private Button btnSearch;
	private PsType mPsType;
	private PollutionSource currentPs;
	private MyHandler mHandler;
	private Envelope initextext;
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			River onerRiver = (River) mAdapter.getItem(position);
			showRiverDetail(onerRiver);
		}
	};
	private OnClickListener mlocationClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			final River river = (River) v.getTag();
			if (riverFeatureLayer.isInitialized()) {
				locationRiver(river);
			} else {
				riverFeatureLayer
						.setOnStatusChangedListener(new OnStatusChangedListener() {
							private static final long serialVersionUID = 2484966524402589675L;

							@Override
							public void onStatusChanged(Object source,
									STATUS status) {
								// TODO Auto-generated method stub
								if (status == STATUS.INITIALIZED) {
									locationRiver(river);
								}
							}
						});
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_river);
		mHandler = new MyHandler(this);
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RiverActivity.this.finish();
			}
		});
		mapSpinner = (Spinner) findViewById(R.id.mapSpin);
		ArrayList<String> maptypes = new ArrayList<String>();
		maptypes.add("按河道类型");
		maptypes.add("按河道等级");
		maptypes.add("按河道水质");
		ArrayAdapter<String> mapAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, maptypes);
		mapSpinner.setAdapter(mapAdapter);
		mapSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (riverLayer != null) {
					RiverActivity.this.mapView.removeLayer(riverLayer);
				}
				switch (position) {
				case 0:
					riverLayer = new ArcGISDynamicMapServiceLayer(riverUrlhdlx);
					break;
				case 1:
					riverLayer = new ArcGISDynamicMapServiceLayer(riverUrlhddj);
					break;
				case 2:
					riverLayer = new ArcGISDynamicMapServiceLayer(riverUrlhdsz);
					break;
				default:
				 break;
				}
				RiverActivity.this.mapView.addLayer(riverLayer);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		// 统计
		Button btnCount = (Button) findViewById(R.id.btnRiverCount);
		btnCount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(RiverActivity.this, CountRiverActivity.class);
				RiverActivity.this.startActivity(it);
			}
		});
		// 列表
		this.mListView = (WebListView) findViewById(R.id.listView);
		this.mListView.showLoading();
		this.mSearchEditView = (AutoCompleteTextView) findViewById(R.id.SearchEditText);
		// new Thread(new HttpThread("xzq")).start();
		List<Region> listRegions = WaterDectionary.getRegions();
		if (listRegions == null) {
			Toast.makeText(this, "连接服务器失败,请稍候再试!", Toast.LENGTH_SHORT).show();
			WaterDectionary.config();
			this.finish();
			return;
		}
		ArrayList<String> arrayList = new ArrayList<String>();
		for (Region region : listRegions) {
			arrayList.add(region.getName());
		}
		SearchAdapter<String> adapter = new SearchAdapter<String>(this,
				R.layout.autotext_item, arrayList);
		this.mSearchEditView.setAdapter(adapter);
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
		String mapURL = getString(R.string.mapBg);
		mapView = (MapView) findViewById(R.id.mapView);
		tiledMapServiceLayer = new ArcGISTiledMapServiceLayer(mapURL);
		mapView.addLayer(tiledMapServiceLayer);
//		riverLayer = new ArcGISDynamicMapServiceLayer(riverUrlhdsz);
//		mapView.addLayer(riverLayer);
		riverFeatureLayer = new ArcGISFeatureLayer(
				"http://159.226.110.64:8001/ags/rest/services/sanhe/MapServer/0",
				MODE.SELECTION);
		mapView.addLayer(riverFeatureLayer);
		// MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapWsgwngl));
		// MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapNcwscll));
		MapUtil.addMapLayerByUrl(mapView, getString(R.string.mapJiaXing));
		Envelope initextext = new Envelope(
				Double.parseDouble(getString(R.string.mapMinX)),
				Double.parseDouble(getString(R.string.mapMinY)),
				Double.parseDouble(getString(R.string.mapMaxX)),
				Double.parseDouble(getString(R.string.mapMaxY)));
		mapView.setExtent(initextext);
		// new Thread(new httpThread()).start();
		mapInfoLayout = (RelativeLayout) findViewById(R.id.mapInfoLayout);
		mapInfoLayout.setVisibility(View.GONE);
		// mapView.setOnSingleTapListener(mSingleTapListener);
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
		// 地图信息栏
		firstTv = (TextView) findViewById(R.id.firstTv);
		secondTv = (TextView) findViewById(R.id.secondTv);
		itemImageView = (ImageView) findViewById(R.id.itemImageView);
		Button btn = (Button) findViewById(R.id.showDetailBtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RiverActivity.this,
						FieldItemActivity.class);
				intent.putExtra("Title", "污染源信息");
				intent.putExtra("FieldItems", currentPs.getFieldItems());
				startActivity(intent);
			}
		});
		mapView.setOnSingleTapListener(new OnSingleTapListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3254659496598709337L;

			@Override
			public void onSingleTap(float x, float y) {
				final float tx = x;
				final float ty = y;
				Point startPoint = mapView.toMapPoint(x - 50, y - 50);
				Point endPoint = mapView.toMapPoint(x + 50, y + 50);
				final Envelope envelope = new Envelope(startPoint.getX(),
						startPoint.getY(), endPoint.getX(), endPoint.getY());
				// build a query to select the clicked feature
				Query query = new Query();
				// query.setOutFields(new String[] { "*" });
				query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
				query.setGeometry(envelope);
				query.setInSpatialReference(mapView.getSpatialReference());
				riverFeatureLayer.selectFeatures(query,
						ArcGISFeatureLayer.SELECTION_METHOD.NEW,
						new CallbackListener<FeatureSet>() {
							@Override
							public void onCallback(FeatureSet queryResults) {
								if (queryResults.getGraphics().length > 0) {
									String codeString = queryResults
											.getGraphics()[0]
											.getAttributeValue("CODE")
											.toString();
									River river = RiverActivity.this
											.findRiverByCode(codeString);
									if (river != null) {
										showRiverDetail(river);
										Query query = new Query();
										query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
										query.setGeometry(envelope);
										query.setInSpatialReference(mapView
												.getSpatialReference());
										query.setWhere("CODE='" + codeString
												+ "'");
										riverFeatureLayer
												.selectFeatures(
														query,
														ArcGISFeatureLayer.SELECTION_METHOD.NEW,
														null);
										Envelope en = new Envelope();
										mapView.getExtent().queryEnvelope(en);
										System.out.println("x1=" + en.getXMin()
												+ "  y1=" + en.getYMin()
												+ " x2=" + en.getXMax() + " y2"
												+ en.getYMax());
									}
								} else {
									RiverActivity.this
											.runOnUiThread(new Runnable() {

												@Override
												public void run() {
													Graphic result = null;
													// 检索当前 光标点（手指按压位置）的附近的
													// graphic对象
													result = GetGraphicsFromLayer(
															tx, ty,
															mGraphicsLayer);
													if (result != null) {
														// 获得附加特别的属性
														String pid = String.valueOf(result
																.getAttributeValue("PID"));
														showMapInfo(pid);
													} else {
														mapInfoLayout
																.setVisibility(View.GONE);
													}

												}
											});
								}
							}

							@Override
							public void onError(Throwable e) {
								// TODO Auto-generated method stub

							}
						});

			}
		});
		new Thread(new HttpThread(MSG_ALLRIVER)).start();
		new Thread(new HttpThread(MSG_AllPs)).start();

	}

	private PollutionSource findHotProjectByid(String psID) {
		for (PollutionSource pollutionSource : mPollutionSources) {
			if (psID.equals(pollutionSource.getPID())) {
				return pollutionSource;
			}
		}
		return null;
	}

	protected void showMapInfo(String pid) {
		PollutionSource pollutionSource = findHotProjectByid(pid);
		Point point = new Point(pollutionSource.getX(), pollutionSource.getY());
		mapView.centerAt(point, true);
		currentPs = pollutionSource;
		firstTv.setText(pollutionSource.getShowTitle());
		secondTv.setText(pollutionSource.getShowDescribing());
		if (pollutionSource.getImageString() != null) {
			String url = this.getString(R.string.NewTileImgAddr)
					+ pollutionSource.getImageString();
			ImageLoader.getInstance().displayImage(url, itemImageView,
					ImageOptions.options);
		} else {
			itemImageView.setImageResource(R.drawable.imageview);
		}
		mapInfoLayout.setVisibility(View.VISIBLE);

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

	protected void updatePollutionSource(PsType type) {
		if (mPollutionSources == null) {
			mPollutionSources = new ArrayList<PollutionSource>();
		} else {
			mPollutionSources.clear();
		}
		for (PollutionSource pollutionSource : mAllPollutionSources) {
			if (pollutionSource.getPsType().equals(this.mPsType)
					|| this.mPsType == PsType.ALL) {
				mPollutionSources.add(pollutionSource);
			}
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
					pollutionSource.getMapDrawable(RiverActivity.this));
			Graphic oneGraphic = new Graphic(onePoint, symbol, map);
			mGraphicsLayer.addGraphic(oneGraphic);
		}
		mapInfoLayout.setVisibility(View.GONE);
	}

	protected River findRiverByCode(String codeString) {
		for (River river : mRivers) {
			if (river.getCode().equals(codeString)) {
				return river;
			}
		}
		return null;
	}

	protected List<River> findRivers(String filter) {
		if (filter.equals("")) {
			return mRivers;
		}
		List<River> rivers = new ArrayList<River>();
		for (River river : mRivers) {
			if (river.getName().contains(filter)
					|| (river.getXzq() != null && river.getXzq().getName()
							.equals(filter))) {
				rivers.add(river);
			}
		}
		return rivers;
	}

	private void locationRiver(River river) {
		Query query = new Query();
		query.setReturnGeometry(true);
		query.setWhere("CODE='" + river.getCode() + "'");
		query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
		query.setGeometry(initextext);
		query.setInSpatialReference(mapView.getSpatialReference());
		riverFeatureLayer.selectFeatures(query,
				ArcGISFeatureLayer.SELECTION_METHOD.NEW,
				new CallbackListener<FeatureSet>() {
					@Override
					public void onCallback(FeatureSet queryResults) {
						if (queryResults.getGraphics().length > 0) {
							final Geometry geometry = queryResults
									.getGraphics()[0].getGeometry();
							RiverActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									mapView.setExtent(geometry);
									Envelope envelope = new Envelope();
									geometry.queryEnvelope(envelope);
									double dx = (envelope.getXMax() - envelope
											.getXMin()) / 10;
									double dy = (envelope.getYMax() - envelope
											.getYMin()) / 10;
									Envelope extent = new Envelope(envelope
											.getXMin() - dx, envelope.getYMin()
											- dy, envelope.getXMax() + dx,
											envelope.getYMax() + dy);
									mapView.setExtent(extent);
									tabHost.setCurrentTab(0);
									mapView.postInvalidate();
								}
							});
						}
					}

					@Override
					public void onError(Throwable e) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
		updatePollutionSource(mPsType);
	}

	private void showRiverDetail(River onerRiver) {
		Intent intent = new Intent(RiverActivity.this,
				RiverDetailActivity.class);
		// intent.putExtra("jbxx", onerRiver.getJbxxFieldItems());
		// intent.putExtra("wry", onerRiver.getWryFieldItems());
		// intent.putExtra("szjl", onerRiver.getSzjlFieldItems());
		// intent.putExtra("zljh", onerRiver.getZljhFieldItems());
		intent.putExtra("river", onerRiver);
		startActivity(intent);
	}

	class HttpThread implements Runnable {
		int tpyeString;

		public HttpThread(int type) {
			tpyeString = type;
		}

		@Override
		public void run() {
			if (tpyeString == MSG_XZQ) {
				String jsonString = HttpUtil.getDectionaryString("Xzq");
				Message msg = RiverActivity.this.mHandler.obtainMessage();
				msg.what = MSG_XZQ;
				msg.obj = jsonString;
				msg.sendToTarget();
			}
			if (tpyeString == MSG_ALLRIVER) {
				String jsonString = HttpUtil.getAllRiverString();
				Message msg = RiverActivity.this.mHandler.obtainMessage();
				msg.what = MSG_ALLRIVER;
				msg.obj = jsonString;
				msg.sendToTarget();
			}
			if (tpyeString == MSG_AllPs) {
				String jsonString = HttpUtil.getAllPollutionString("all");
				Message msg = mHandler.obtainMessage();
				msg.obj = jsonString;
				msg.what = MSG_AllPs;
				msg.sendToTarget();
			}
		}
	}

	static class MyHandler extends Handler {
		WeakReference<RiverActivity> activityReference;

		public MyHandler(RiverActivity activity) {
			activityReference = new WeakReference<RiverActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			final RiverActivity activity = activityReference.get();
			if (msg.obj == null) {
				Toast.makeText(activity, "连接服务器失败,请稍候再试!", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (msg.what == activity.MSG_ALLRIVER) {
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
						.create();
				activity.mRivers = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<River>>() {
						}.getType());
				activity.mAdapter = new RiverAdapter(activity,
						activity.mRivers, activity.mlocationClickListener);
				activity.mListView.setAdapter(activity.mAdapter);
				activity.mListView
						.setOnItemClickListener(activity.mOnItemClickListener);
				activity.btnSearch.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						InputMethodManager im = (InputMethodManager) activity
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						im.hideSoftInputFromWindow(
								activity.mSearchEditView.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
						activity.tabHost.setCurrentTab(1);
						activity.mAdapter.refresh(activity
								.findRivers(activity.mSearchEditView.getText()
										.toString()));
						activity.tabHost.setCurrentTab(1);
					}
				});
				activity.mSearchEditView
						.setOnEditorActionListener(new OnEditorActionListener() {

							@Override
							public boolean onEditorAction(TextView v,
									int actionId, KeyEvent event) {
								if (actionId == EditorInfo.IME_ACTION_SEARCH) {
									InputMethodManager im = (InputMethodManager) activity
											.getSystemService(Context.INPUT_METHOD_SERVICE);
									im.hideSoftInputFromWindow(
											activity.mSearchEditView
													.getWindowToken(),
											InputMethodManager.HIDE_NOT_ALWAYS);
									activity.tabHost.setCurrentTab(1);
									activity.mAdapter.refresh(activity
											.findRivers(activity.mSearchEditView
													.getText().toString()));
									activity.tabHost.setCurrentTab(1);
								}
								return false;
							}
						});
			}
			if (msg.what == activity.MSG_AllPs) {
				Gson gson = new Gson();
				activity.mPsManager = gson.fromJson(msg.obj.toString(),
						PsManager.class);
				activity.mAllPollutionSources = new ArrayList<PollutionSource>();
				for (PollutionSource pollutionSource : activity.mPsManager
						.getGys()) {
					activity.mAllPollutionSources.add(pollutionSource);
				}
				for (PollutionSource pollutionSource : activity.mPsManager
						.getNySource().getScyzwry()) {
					activity.mAllPollutionSources.add(pollutionSource);
				}
				for (PollutionSource pollutionSource : activity.mPsManager
						.getNySource().getXqyzwry()) {
					activity.mAllPollutionSources.add(pollutionSource);
				}
				for (PollutionSource pollutionSource : activity.mPsManager
						.getNySource().getZzwry()) {
					activity.mAllPollutionSources.add(pollutionSource);
				}
				for (PollutionSource pollutionSource : activity.mPsManager
						.getShs()) {
					activity.mAllPollutionSources.add(pollutionSource);
				}
				activity.updatePollutionSource(PsType.GY);
			}
		}
	}

}
