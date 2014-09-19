package cn.bjeastearth.waterapp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.model.CountPsNySh;
import cn.bjeastearth.waterapp.model.CountPsNySh.CountNyShZhen.CountNyShYear;
import cn.bjeastearth.waterapp.model.CountPsGy;
import cn.bjeastearth.waterapp.model.CountPsGy.CountPsGyZhen.CountPsGyYear;

import cn.bjeastearth.waterapp.model.Region;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class CountPsActivity extends Activity {
	private final static String GYPS = "GywrySta";
	private final static String XQPS = "XqyzwrySta";
	private final static String SCPS = "ScwrySta";
	private final static String ZZPS = "ZzwrySta";
	private final static String SHPS = "ShwsSta";
	private ListView mListView;
	private Spinner mRegionSpinner;
	private Spinner mYearSpinner;
	private Spinner mPsSpinner;
	private List<Region> listRegions;
	private MyHandle mHandle;
	private String currentType = GYPS;
	private int currentRID;
	private String currentYeas;
	private LinearLayout mHeadView;
	private ArrayList<String> psTypes;
	private ArrayList<String> years;
	private Button btnCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_count_ps);
		Button btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CountPsActivity.this.finish();
			}
		});
		btnCount = (Button) findViewById(R.id.btnCount);
		btnCount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mHeadView.removeAllViews();
				CountPsActivity.this.mListView.setAdapter(null);
				new Thread(new HttpThread()).start();
			}
		});
		mHandle = new MyHandle(this);
		mListView = (ListView) findViewById(R.id.listView);
		mRegionSpinner = (Spinner) findViewById(R.id.regionSpin);
		mYearSpinner = (Spinner) findViewById(R.id.yearSpin);
		mPsSpinner = (Spinner) findViewById(R.id.psSpin);
		mHeadView = (LinearLayout) findViewById(R.id.headView);
		setRegionSpinner();
		setPsSpinner();
		mYearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				currentYeas = mYearSpinner.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setRegionSpinner() {
		listRegions = WaterDectionary.getRegions();
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("全部区域");
		for (Region region : listRegions) {
			arrayList.add(region.getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, arrayList);
		this.mRegionSpinner.setAdapter(adapter);
		mRegionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					setYearSpinner(false);
				} else {
					setYearSpinner(true);
				}
				currentYeas = mYearSpinner.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setYearSpinner(boolean isAllYears) {
		years = new ArrayList<String>();
		if (isAllYears) {
			years.add("全部年份");
		} else {
			Calendar calendar = Calendar.getInstance();
			for (int i = calendar.get(Calendar.YEAR); i > 1999; i--) {
				years.add(String.valueOf(i));
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, years);
		mYearSpinner.setAdapter(adapter);
	}

	private void setPsSpinner() {
		psTypes = new ArrayList<String>();
		psTypes.add("工业污染源");
		psTypes.add("畜禽养殖污染源");
		psTypes.add("水产养殖污染源");
		psTypes.add("种植污染源");
		psTypes.add("生活污染源");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, psTypes);
		mPsSpinner.setAdapter(adapter);
		mPsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					currentType = GYPS;
				}
				if (position == 1) {
					currentType = XQPS;
				}
				if (position == 2) {
					currentType = SCPS;
				}
				if (position == 3) {
					currentType = ZZPS;
				}
				if (position == 4) {
					currentType = SHPS;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	static class MyHandle extends Handler {
		WeakReference<CountPsActivity> reference;

		public MyHandle(CountPsActivity countPsActivity) {
			reference = new WeakReference<CountPsActivity>(countPsActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			CountPsActivity countPsActivity = reference.get();
			super.handleMessage(msg);
			if (countPsActivity.currentType.equals(GYPS)) {
				View view = countPsActivity.getLayoutInflater().inflate(
						R.layout.count_gyps_item, null);
				TextView nameTextView = (TextView) view
						.findViewById(R.id.nameTv);
				countPsActivity.mHeadView.removeAllViews();
				countPsActivity.mHeadView.addView(view);
				Gson gson = new Gson();
				List<CountPsGy> countPsGies = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<CountPsGy>>() {
						}.getType());
				ArrayList<CountPsGyYear> countPsGyYears = new ArrayList<CountPsGyYear>();
				for (CountPsGy countPsGy : countPsGies) {
					if (countPsActivity.mRegionSpinner
							.getSelectedItemPosition() == 0) {
						nameTextView.setText("行政区");
						for (CountPsGyYear countPsGyYear : countPsGy
								.getCountPsGyYearsByYear(countPsActivity.currentYeas)) {
							countPsGyYears.add(countPsGyYear);
						}
					} else {
						nameTextView.setText("年份");
						for (CountPsGyYear countPsGyYear : countPsGy
								.getCountPsGyYearsByZhen(countPsActivity.mRegionSpinner
										.getSelectedItem().toString())) {
							countPsGyYears.add(countPsGyYear);
						}
					}
				}
				GyPsAdepter gyPsAdepter = new GyPsAdepter(countPsActivity,
						countPsGyYears);
				countPsActivity.mListView.setAdapter(gyPsAdepter);

			} else {
				View view = countPsActivity.getLayoutInflater().inflate(
						R.layout.count_nyshps_item, null);
				TextView nameTextView = (TextView) view
						.findViewById(R.id.nameTv);
				countPsActivity.mHeadView.removeAllViews();
				countPsActivity.mHeadView.addView(view);
				Gson gson = new Gson();
				List<CountPsNySh> countPsNyShs = gson.fromJson(
						msg.obj.toString(), new TypeToken<List<CountPsNySh>>() {
						}.getType());
				ArrayList<CountNyShYear> countNyShYears = new ArrayList<CountNyShYear>();
				for (CountPsNySh countPsNySh : countPsNyShs) {
					if (countPsActivity.mRegionSpinner
							.getSelectedItemPosition() == 0) {
						nameTextView.setText("行政区");
						for (CountNyShYear countNyShYear : countPsNySh
								.getCountNyShYearsByYear(countPsActivity.currentYeas)) {
							countNyShYears.add(countNyShYear);
						}
					} else {
						nameTextView.setText("年份");
						for (CountNyShYear countNyShYear : countPsNySh
								.getCountNyShYearsByZhen(countPsActivity.mRegionSpinner
										.getSelectedItem().toString())) {
							countNyShYears.add(countNyShYear);
						}
					}
				}
				NyShPsAdepter nyShPsAdepter = new NyShPsAdepter(
						countPsActivity, countNyShYears);
				countPsActivity.mListView.setAdapter(nyShPsAdepter);
			}
		}
	}

	class HttpThread implements Runnable {
		@Override
		public void run() {
			String jsonString = HttpUtil.getCountPsString(currentType);
			Message msg = mHandle.obtainMessage();
			msg.obj = jsonString;
			msg.sendToTarget();
		}

	}

	public static class GyPsAdepter extends BaseAdapter {
		private ArrayList<CountPsGy.CountPsGyZhen.CountPsGyYear> countPsGyYears;
		private CountPsActivity mActivity;

		public GyPsAdepter(CountPsActivity countPsActivity,
				ArrayList<CountPsGy.CountPsGyZhen.CountPsGyYear> arrayList) {
			countPsGyYears = arrayList;
			mActivity = countPsActivity;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return countPsGyYears.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mActivity.getLayoutInflater().inflate(
						R.layout.count_gyps_item, null);
			}
			CountPsGyYear year = countPsGyYears.get(position);
			TextView codsumTextView = (TextView) convertView
					.findViewById(R.id.codsumTv);
			codsumTextView.setText(String.valueOf(year.getCod_sum()));
			TextView adclTextView = (TextView) convertView
					.findViewById(R.id.adclTv);
			adclTextView.setText(String.valueOf(year.getNH3N_c()));
			TextView adzpTextView = (TextView) convertView
					.findViewById(R.id.adzpTv);
			adzpTextView.setText(String.valueOf(year.getNH3N_z()));
			TextView adsumTextView = (TextView) convertView
					.findViewById(R.id.adsumTv);
			adsumTextView.setText(String.valueOf(year.getNH3N_sum()));
			TextView nameTextView = (TextView) convertView
					.findViewById(R.id.nameTv);
			nameTextView.setText(year.getName());
			TextView fspflTextView = (TextView) convertView
					.findViewById(R.id.fspflTv);
			fspflTextView.setText(String.valueOf(year.getFspfl()));
			TextView codcTextView = (TextView) convertView
					.findViewById(R.id.codclTv);
			codcTextView.setText(String.valueOf(year.getCod_c()));
			TextView codzTextView = (TextView) convertView
					.findViewById(R.id.codzpTv);
			codzTextView.setText(String.valueOf(year.getCod_z()));

			return convertView;
		}

	}

	public static class NyShPsAdepter extends BaseAdapter {
		private ArrayList<CountNyShYear> countNyShYears;
		private CountPsActivity mActivity;

		public NyShPsAdepter(CountPsActivity countPsActivity,
				ArrayList<CountNyShYear> arrayList) {
			mActivity = countPsActivity;
			countNyShYears = arrayList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return countNyShYears.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mActivity.getLayoutInflater().inflate(
						R.layout.count_nyshps_item, null);
			}
			CountNyShYear year = countNyShYears.get(position);
			TextView codsumTextView = (TextView) convertView
					.findViewById(R.id.codsumTv);
			codsumTextView.setText(String.valueOf(year.getCod_sum()));
			TextView tnTextView = (TextView) convertView
					.findViewById(R.id.tnsumTv);
			tnTextView.setText(String.valueOf(year.getTN_sum()));
			TextView tpTextView = (TextView) convertView
					.findViewById(R.id.tpsumTv);
			tpTextView.setText(String.valueOf(year.getPSum_sum()));
			TextView adsumTextView = (TextView) convertView
					.findViewById(R.id.adsumTv);
			adsumTextView.setText(String.valueOf(year.getNH3N_sum()));
			TextView nameTextView = (TextView) convertView
					.findViewById(R.id.nameTv);
			nameTextView.setText(year.getName());
			TextView fspflTextView = (TextView) convertView
					.findViewById(R.id.fspflTv);
			fspflTextView.setText(String.valueOf(year.getFspfl()));
			return convertView;
		}

	}
}
