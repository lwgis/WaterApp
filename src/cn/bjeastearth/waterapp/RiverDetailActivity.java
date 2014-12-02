package cn.bjeastearth.waterapp;

import java.util.ArrayList;

import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.model.FieldItem;
import cn.bjeastearth.waterapp.model.River;
import cn.bjeastearth.waterapp.myview.AddSzDialog;
import cn.bjeastearth.waterapp.myview.AddZljhDialog;
import cn.bjeastearth.waterapp.myview.DpTransform;
import cn.bjeastearth.waterapp.myview.SelectZljhDialog;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class RiverDetailActivity extends Activity implements ViewFactory {
	private River currentRiver;
	private TabHost tabHost;
	private ListView jbxxListView;
	private ListView wryListView;
	private ListView szjlListView;
	private ListView zljhListView;
	private AddSzDialog addSzDialog;
	private AddZljhDialog addZljhDialog;
	private SelectZljhDialog selectZljhDialog;
	private PopupWindow editPopupWindow;
	private Button btnEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_riverdetail);
		Intent it = getIntent();
		currentRiver = (River) it.getSerializableExtra("river");
		initEditMenu();
		Button backButton = (Button) findViewById(R.id.btnBack);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RiverDetailActivity.this.finish();
			}
		});
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup(); // Call setup() before adding tabs if loading TabHost
							// using findViewById().
		addTab("基本信息", "jbxx", R.id.jbxxLv);
		addTab("污染源", "wryxx", R.id.wryLv);
		addTab("水质记录", "sz", R.id.szjlLv);
		addTab("整治计划", "zzjh", R.id.zljhLv);
		jbxxListView = (ListView) findViewById(R.id.jbxxLv);
		wryListView = (ListView) findViewById(R.id.wryLv);
		szjlListView = (ListView) findViewById(R.id.szjlLv);
		zljhListView = (ListView) findViewById(R.id.zljhLv);
		refreshUi(currentRiver);
	}
	/**
	 * 刷新界面（river）
	 */
	public void refreshUi(River river) {
		currentRiver=river;
		ArrayList<FieldItem> jsbx = river.getJbxxFieldItems();
		FieldItemAdapter jbxxAdapter = new FieldItemAdapter(this, jsbx);
		jbxxListView.setAdapter(jbxxAdapter);
		ArrayList<FieldItem> wry = river.getWryFieldItems();
		FieldItemAdapter wryAdapter = new FieldItemAdapter(this, wry);
		wryListView.setAdapter(wryAdapter);
		ArrayList<FieldItem> szjl = river.getSzjlFieldItems();
		FieldItemAdapter szjlAdapter = new FieldItemAdapter(this, szjl);
		szjlListView.setAdapter(szjlAdapter);
		ArrayList<FieldItem> zljh = river.getZljhFieldItems();
		FieldItemAdapter zljhAdapter = new FieldItemAdapter(this, zljh);
		zljhListView.setAdapter(zljhAdapter);
	}

	private void initEditMenu() {
		final View popView = LayoutInflater.from(this).inflate(
				R.layout.popupwindow_editriver, null);
		btnEdit = (Button) findViewById(R.id.btnEdit);
		if (currentRiver.getEditEnable()==0||WaterDectionary.getUserId()==-1) {
			btnEdit.setVisibility(View.INVISIBLE);
			return;
		}
		btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editPopupWindow == null) {
					editPopupWindow = new PopupWindow(popView, DpTransform
							.dip2px(RiverDetailActivity.this, 130), DpTransform
							.dip2px(RiverDetailActivity.this, 150));
				}
				if (editPopupWindow.isShowing()) {
					editPopupWindow.dismiss();
				} else {
					editPopupWindow.showAsDropDown(btnEdit,
							-DpTransform.dip2px(RiverDetailActivity.this, 45),
							DpTransform.dip2px(RiverDetailActivity.this, 4));
				}
			}
		});
		Button btnSzjl=(Button)popView.findViewById(R.id.btnSzjl);
		btnSzjl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editPopupWindow.dismiss();
				editSzjl();
			}
		});
		Button btnZljh=(Button)popView.findViewById(R.id.btnZljh);
		btnZljh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editPopupWindow.dismiss();
				editZljh();
			}
		});
		Button btnSelectZljh=(Button)popView.findViewById(R.id.btnEditZljh);
		btnSelectZljh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editPopupWindow.dismiss();
				if (currentRiver.getHdzljls()==null||currentRiver.getHdzljls().size()==0) {
					Toast.makeText(RiverDetailActivity.this, "没有整治计划可修改", Toast.LENGTH_LONG).show();
				}
				else {
					selectZljh();
				}
			}
		});
	}

	/**
	 * 添加Tab页
	 */
	private void addTab(String title, String tabName, int id) {
		View tabView = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_item, null);
		TextView text = (TextView) tabView.findViewById(R.id.tab_label);
		text.setText(title);
		tabHost.addTab(tabHost.newTabSpec(tabName).setIndicator(tabView)
				.setContent(id));
	}

	@Override
	public View makeView() {
		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setBackgroundColor(0x00000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return imageView;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (addSzDialog != null) {
				addSzDialog.dismiss(true);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 编辑水质记录
	 */
	private void editSzjl() {
		FragmentTransaction ft = getFragmentManager()
				.beginTransaction();
		if (addSzDialog == null) {
			addSzDialog = new AddSzDialog(currentRiver);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		}
		addSzDialog.show(ft, "addSz");
	}
	/**
	 * 编辑治理计划
	 */
	protected void editZljh() {
		FragmentTransaction ft=getFragmentManager().beginTransaction();
		if (addZljhDialog==null) {
			addZljhDialog=new AddZljhDialog(currentRiver);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		}
		addZljhDialog.show(ft, "addzljh");
	}
	/**
	 * 选择河道整理计划
	 */
	protected void selectZljh() {
		FragmentTransaction ft=getFragmentManager().beginTransaction();
		if (selectZljhDialog==null) {
			selectZljhDialog=new SelectZljhDialog(currentRiver);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		}
		selectZljhDialog.show(ft, "selectzljh");
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (editPopupWindow != null && editPopupWindow.isShowing()) {
			editPopupWindow.dismiss();
			editPopupWindow = null;
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}
}
