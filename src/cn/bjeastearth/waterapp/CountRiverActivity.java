package cn.bjeastearth.waterapp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.CountPsActivity.GyPsAdepter;
import cn.bjeastearth.waterapp.CountPsActivity.HttpThread;
import cn.bjeastearth.waterapp.CountPsActivity.MyHandle;
import cn.bjeastearth.waterapp.CountPsActivity.NyShPsAdepter;
import cn.bjeastearth.waterapp.model.CountPsGy;
import cn.bjeastearth.waterapp.model.CountPsNySh;
import cn.bjeastearth.waterapp.model.CountRiver;
import cn.bjeastearth.waterapp.model.CountRiverYear;
import cn.bjeastearth.waterapp.model.CountRiverZhen;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.model.CountPsGy.CountPsGyZhen.CountPsGyYear;
import cn.bjeastearth.waterapp.model.CountPsNySh.CountNyShZhen.CountNyShYear;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CountRiverActivity extends Activity {
	private ListView mListView;
	private Spinner mRegionSpinner;
	private Spinner mYearSpinner;
	private List<Region> listRegions;
	private MyHandle mHandle;
	private int currentRID;
	private String currentYeas;
	private LinearLayout mHeadView;
	private ArrayList<String> psTypes;
	private ArrayList<String> years;
	private Button btnCount;
	private List<CountRiver> countRivers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_count_river);
		Button btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CountRiverActivity.this.finish();
			}
		});
		btnCount = (Button) findViewById(R.id.btnCount);
		btnCount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mHeadView.removeAllViews();
				CountRiverActivity.this.count();

			}
		});
		btnCount.setEnabled(false);
		mHandle = new MyHandle(this);
		mListView = (ListView) findViewById(R.id.listView);
		mRegionSpinner = (Spinner) findViewById(R.id.regionSpin);
		mYearSpinner = (Spinner) findViewById(R.id.yearSpin);
		mHeadView = (LinearLayout) findViewById(R.id.headView);
		setRegionSpinner();
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
		new Thread(new HttpThread()).start();
	}

	protected void count() {
		mListView.setAdapter(null);
		View view = getLayoutInflater()
				.inflate(R.layout.count_river_item, null);
		TextView nameTextView = (TextView) view.findViewById(R.id.nameTv);
		mHeadView.removeAllViews();
		mHeadView.addView(view);
		ArrayList<CountRiverYear> countRiverYears = new ArrayList<CountRiverYear>();
		if (mRegionSpinner.getSelectedItemPosition() == 0) {
			nameTextView.setText("行政区");
			for (CountRiver countRiver : this.countRivers) {
				for (CountRiverYear countRiverYear : countRiver
						.getCountRiverYearsByYear(mYearSpinner
								.getSelectedItem().toString())) {
					countRiverYears.add(countRiverYear);
				}
			}
		}
		else {
			nameTextView.setText("年份");
			for (CountRiver countRiver : this.countRivers) {
				for (CountRiverYear countRiverYear : countRiver
						.getCountRiverYearsByZhen(mRegionSpinner
								.getSelectedItem().toString().trim())) {
					countRiverYears.add(countRiverYear);
				}
			}
		}
		CountRiverAdepter countRiverAdepter = new CountRiverAdepter(this,
				countRiverYears);
		mListView.setAdapter(countRiverAdepter);
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

	private void setRegionSpinner() {
		listRegions = WaterDectionary.getRegions();
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("全部区域");
		for (Region region : listRegions) {
			if (region.getStatus()==1) {
				arrayList.add("  "+region.getName());
			}
			else {
				if (region.getStatus()==0) {
					arrayList.add("      "+region.getName());
				}
				else {
					arrayList.add(region.getName());

				}
			}
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

	class HttpThread implements Runnable {
		@Override
		public void run() {
			String jsonString = HttpUtil.getCountRiver();
			Message msg = mHandle.obtainMessage();
			msg.obj = jsonString;
			msg.sendToTarget();
		}
	}

	static class MyHandle extends Handler {
		WeakReference<CountRiverActivity> reference;

		public MyHandle(CountRiverActivity countRiverActivity) {
			reference = new WeakReference<CountRiverActivity>(
					countRiverActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			CountRiverActivity countRiverActivity = reference.get();
			super.handleMessage(msg);
			Gson gson = new Gson();
			countRiverActivity.countRivers = gson.fromJson(msg.obj.toString(),
					new TypeToken<List<CountRiver>>() {
					}.getType());
			countRiverActivity.btnCount.setEnabled(true);
		}
	}

	public static class CountRiverAdepter extends BaseAdapter {
		private ArrayList<CountRiverYear> countRiverYears;
		private CountRiverActivity mActivity;

		public CountRiverAdepter(CountRiverActivity countActivity,
				ArrayList<CountRiverYear> arrayList) {
			countRiverYears = arrayList;
			mActivity = countActivity;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return countRiverYears.size();
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
						R.layout.count_river_item, null);
			}
			CountRiverYear year = countRiverYears.get(position);
			TextView heiheTextView = (TextView) convertView
					.findViewById(R.id.heiheTv);
			heiheTextView.setText(String.valueOf(year.getHhCount()));
			TextView chouheTextView = (TextView) convertView
					.findViewById(R.id.chouheTv);
			chouheTextView.setText(String.valueOf(year.getChCount()));
			TextView lajiheTextView = (TextView) convertView
					.findViewById(R.id.lajiheTv);
			lajiheTextView.setText(String.valueOf(year.getLjhCount()));
			TextView nameTextView = (TextView) convertView
					.findViewById(R.id.nameTv);
			nameTextView.setText(year.getName());
			TextView sanTextView=(TextView)convertView.findViewById(R.id.sanTv);
			sanTextView.setText(String.valueOf(year.getSanCount()));
			TextView siTextView=(TextView)convertView.findViewById(R.id.siTv);
			siTextView.setText(String.valueOf(year.getSiCount()));
			TextView wuTextView=(TextView)convertView.findViewById(R.id.wuTv);
			wuTextView.setText(String.valueOf(year.getWuCount()));
			TextView liuTextView=(TextView)convertView.findViewById(R.id.liuTv);
			liuTextView.setText(String.valueOf(year.getLiuCount()));
			return convertView;
		}

	}
}
