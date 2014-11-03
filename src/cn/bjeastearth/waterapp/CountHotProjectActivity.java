package cn.bjeastearth.waterapp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.model.CountHProject;
import cn.bjeastearth.waterapp.model.CountHProjectYear;
import cn.bjeastearth.waterapp.model.Region;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class CountHotProjectActivity extends Activity {
	private ListView mListView;
	private Spinner mRegionSpinner;
	private Spinner mYearSpinner;
	private List<Region> listRegions;
	private MyHandle mHandle;
	private LinearLayout mHeadView;
	private ArrayList<String> years;
	private Button btnCount;
	private List<CountHProject> countHProjects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_count_hotproject);
		Button btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CountHotProjectActivity.this.finish();
			}
		});
		btnCount = (Button) findViewById(R.id.btnCount);
		btnCount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mHeadView.removeAllViews();
				CountHotProjectActivity.this.count();

			}
		});
		btnCount.setEnabled(false);
		mHandle = new MyHandle(this);
		mListView = (ListView) findViewById(R.id.listView);
		mRegionSpinner = (Spinner) findViewById(R.id.regionSpin);
		mYearSpinner = (Spinner) findViewById(R.id.yearSpin);
		mHeadView = (LinearLayout) findViewById(R.id.headView);
		setRegionSpinner();

		new Thread(new HttpThread()).start();
	}

	protected void count() {
		mListView.setAdapter(null);
		View view = getLayoutInflater().inflate(R.layout.count_hotproject_item,
				null);
		TextView nameTextView = (TextView) view.findViewById(R.id.nameTv);
		mHeadView.removeAllViews();
		mHeadView.addView(view);
		ArrayList<CountHProjectYear> countHProjectYears = new ArrayList<CountHProjectYear>();
		if (mRegionSpinner.getSelectedItemPosition() == 0) {
			nameTextView.setText("行政区");
			for (CountHProject countHProject : this.countHProjects) {
				for (CountHProjectYear countHProjectYear : countHProject
						.getCountHProjectYearsByYear(mYearSpinner
								.getSelectedItem().toString())) {
					countHProjectYears.add(countHProjectYear);
				}
			}
		} else {
			nameTextView.setText("年份");
			for (CountHProject countHProject : this.countHProjects) {
				for (CountHProjectYear countHProjectYear : countHProject
						.getCountHProjectYearsByZhen(mRegionSpinner
								.getSelectedItem().toString().trim())) {
					countHProjectYears.add(countHProjectYear);
				}
			}
		}
		CountHotProjectAdapter countHProjectAdepter = new CountHotProjectAdapter(
				this, countHProjectYears);
		mListView.setAdapter(countHProjectAdepter);
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
			if (region.getStatus() == 1) {
				arrayList.add("  " + region.getName());
			} else {
				if (region.getStatus() == 0) {
					arrayList.add("      " + region.getName());
				} else {
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
			String jsonString = HttpUtil.getCountHProject();
			Message msg = mHandle.obtainMessage();
			msg.obj = jsonString;
			msg.sendToTarget();
		}
	}

	static class MyHandle extends Handler {
		WeakReference<CountHotProjectActivity> reference;

		public MyHandle(CountHotProjectActivity countHProjectActivity) {
			reference = new WeakReference<CountHotProjectActivity>(
					countHProjectActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			CountHotProjectActivity countHProjectActivity = reference.get();
			super.handleMessage(msg);
			Gson gson = new Gson();
			countHProjectActivity.countHProjects = gson.fromJson(
					msg.obj.toString(), new TypeToken<List<CountHProject>>() {
					}.getType());
			countHProjectActivity.btnCount.setEnabled(true);
		}
	}

	public static class CountHotProjectAdapter extends BaseAdapter {
		private ArrayList<CountHProjectYear> countHProjectYears;
		private CountHotProjectActivity mActivity;

		public CountHotProjectAdapter(CountHotProjectActivity countActivity,
				ArrayList<CountHProjectYear> arrayList) {
			countHProjectYears = arrayList;
			mActivity = countActivity;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return countHProjectYears.size();
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
						R.layout.count_hotproject_item, null);
			}
			CountHProjectYear year = countHProjectYears.get(position);
			TextView kgxmTextView = (TextView) convertView
					.findViewById(R.id.kgxmTv);
			kgxmTextView.setText(String.valueOf(year.getStartProCount()));
			TextView jgxmTextView = (TextView) convertView
					.findViewById(R.id.jgxmTv);
			jgxmTextView.setText(String.valueOf(year.getEndProCount()));
			TextView tzjeTextView = (TextView) convertView
					.findViewById(R.id.tzjeTv);
			tzjeTextView.setText(String.valueOf(year.getTzjeSum()));
			TextView nameTextView = (TextView) convertView
					.findViewById(R.id.nameTv);
			nameTextView.setText(year.getName());

			return convertView;
		}

	}
}
