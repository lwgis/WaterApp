package cn.bjeastearth.waterapp;

import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.model.PollutionSource;
import cn.bjeastearth.waterapp.model.PsFarmingManager;
import cn.bjeastearth.waterapp.model.PsIndustry;
import cn.bjeastearth.waterapp.model.PsLive;
import cn.bjeastearth.waterapp.model.PsManager;
import cn.bjeastearth.waterapp.model.PsType;
import cn.bjeastearth.waterapp.model.River;
import cn.bjeastearth.waterapp.myview.WebListView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.Envelope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RiverActivity extends Activity implements OnClickListener {
	private final int MSG_XZQ=-1;
	private final int MSG_ALLRIVER=1;
	private MapView mapView = null;
	private WebListView mListView = null;
	private ArcGISTiledMapServiceLayer tiledMapServiceLayer = null;
	private ArcGISDynamicMapServiceLayer riverLayer=null;
	private GraphicsLayer mGraphicsLayer;
	private RelativeLayout mapInfoLayout = null;
	private TabHost tabHost;
	private List<River> mRivers;
	private ArrayList<PollutionSource> mAllPollutionSources;
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
	private Button btnSearch;
	private PsType mPsType;
	private PopupWindow mAddPopupWindow;
	private boolean popWindowIsShow;
	private Button btnAddPs;
	private PollutionSource currentPs;
	private Handler mHandler;
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			River onerRiver = (River)mAdapter
					.getItem(position);
			Intent intent = new Intent(RiverActivity.this,
					FieldItemActivity.class);
			intent.putExtra("Title", "河道信息");
			intent.putExtra("FieldItems", onerRiver.getFieldItems());
			startActivity(intent);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_river);
		mHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what==MSG_ALLRIVER) {
					Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
					mRivers=gson.fromJson(msg.obj.toString(),
							new TypeToken<List<River>>() {
							}.getType());
					mAdapter=new RiverAdapter(RiverActivity.this, mRivers);
					mListView.setAdapter(mAdapter);
					mListView.setOnItemClickListener(mOnItemClickListener);
				}
			}
			
		};
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RiverActivity.this.finish();
			}
		});
		// 列表
		this.mListView = (WebListView) findViewById(R.id.listView);
		this.mListView.showLoading();
		this.mSearchEditView = (AutoCompleteTextView) findViewById(R.id.SearchEditText);
//		new Thread(new HttpThread("xzq")).start();
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
		String riverUrl="http://159.226.110.64:8001/ags/rest/services/waterlines/MapServer";
		riverLayer=new ArcGISDynamicMapServiceLayer(riverUrl);
		mapView.addLayer(riverLayer);
		Envelope initextext = new Envelope(12899459.4956466, 4815363.65520802,
				13004178.2243971, 4882704.67712717);
		mapView.setExtent(initextext);
		// new Thread(new httpThread()).start();
		mapInfoLayout = (RelativeLayout) findViewById(R.id.mapInfoLayout);
		mapInfoLayout.setVisibility(View.GONE);
//		mapView.setOnSingleTapListener(mSingleTapListener);
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
//		new Thread(new HttpThread("all")).start();
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
		new Thread(new HttpThread(MSG_ALLRIVER)).start();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	class HttpThread implements Runnable {
		int tpyeString ;

		public HttpThread(int type) {
			tpyeString = type;
		}

		@Override
		public void run() {
			if (tpyeString==MSG_XZQ) {
				String jsonString = HttpUtil.getDectionaryString("Xzq");
				Message msg = RiverActivity.this.mHandler.obtainMessage();
				msg.what = MSG_XZQ;
				if (!jsonString.equals("")) {
					msg.obj = jsonString;
					msg.sendToTarget();
				}
			} else {
				String jsonString = HttpUtil
						.getAllRiverString();
				if (!jsonString.equals("")) {
					Message msg =RiverActivity.this.mHandler.obtainMessage();
					msg.what=MSG_ALLRIVER;
					msg.obj = jsonString;
					msg.sendToTarget();
				}
			}
		}
	}

}
