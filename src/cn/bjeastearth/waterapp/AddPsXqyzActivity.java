package cn.bjeastearth.waterapp;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.http.UploadImageUtil;
import cn.bjeastearth.http.WaterDectionary;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.PsIndustry;
import cn.bjeastearth.waterapp.model.PsXqyz;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.myview.DpTransform;
import cn.bjeastearth.waterapp.myview.MyTextButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddPsXqyzActivity extends Activity {
	private final int sendPs = 100;
	private Button btnBack;
	private EditText qymcEditText;
	private EditText fzrEditText;
	private EditText lxfsEditText;
	private Spinner mRegionSpinner;
	private EditText nczEditText;
	private EditText zhuEditText;
	private EditText niuEditText;
	private EditText yangEditText;
	private EditText tuEditText;
	private EditText yslEditText;
	private EditText fspflEditText;
	private EditText codEditText;
	private EditText adEditText;
	private EditText tpEditText;
	private EditText tnEditText;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private Button btnLocation;
	private GridView imageGridView;
	private AddImageAdapter imageAdapter;
	private ArrayList<ProjectImage> projectImages;
	private TextView xTv;
	private TextView yTv;
	private double x = 0.0;
	private double y = 0.0;
	private PopupWindow mPopupWindow;
	private View popView;
	private File currentfile;
	private PsXqyz mPsXqyz;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == sendPs) {
				Toast.makeText(AddPsXqyzActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				AddPsXqyzActivity.this.btnSendPs.setEnabled(true);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_xqyz);
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddPsXqyzActivity.this.finish();
			}
		});
		this.btnSendPs = (MyTextButton) findViewById(R.id.btnSendPs);
		// this.btnSendPs.setFocusable(true);
		// this.btnSendPs.setFocusableInTouchMode(true);
		// this.btnSendPs.requestFocus();
		// this.btnSendPs.requestFocusFromTouch();
		this.btnSendPs.setEnabled(false);
		this.btnSendPs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddPsXqyzActivity.this.btnSendPs.setEnabled(false);
				AddPsXqyzActivity.this.uploadPsIndustry();
			}
		});
		qymcEditText = (EditText) findViewById(R.id.qymcEt);
		fzrEditText = (EditText) findViewById(R.id.fzrEt);
		lxfsEditText = (EditText) findViewById(R.id.lxfsEt);
		mRegionSpinner = (Spinner) findViewById(R.id.regionSpin);
		nczEditText = (EditText) findViewById(R.id.nczEt);
		zhuEditText = (EditText) findViewById(R.id.zhuEt);
		niuEditText = (EditText) findViewById(R.id.niuEt);
		yangEditText = (EditText) findViewById(R.id.yangEt);
		tuEditText = (EditText) findViewById(R.id.tuEt);
		yslEditText = (EditText) findViewById(R.id.yslEt);
		fspflEditText = (EditText) findViewById(R.id.fspflEt);
		codEditText = (EditText) findViewById(R.id.codEt);
		adEditText = (EditText) findViewById(R.id.adEt);
		tpEditText = (EditText) findViewById(R.id.tpEt);
		tnEditText = (EditText) findViewById(R.id.tnEt);
		this.xTv = (TextView) findViewById(R.id.xTv);
		this.yTv = (TextView) findViewById(R.id.yTv);
		this.btnLocation = (Button) findViewById(R.id.btnAddLocation);
		this.btnLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(AddPsXqyzActivity.this,
						LocationActivity.class);
				AddPsXqyzActivity.this.startActivityForResult(it, 2);
			}
		});

		// 图片
		this.imageGridView = (GridView) findViewById(R.id.imageGridView);
		this.projectImages = new ArrayList<ProjectImage>();
		this.imageAdapter = new AddImageAdapter(this, projectImages);
		this.imageGridView.setAdapter(imageAdapter);
		popView = LayoutInflater.from(AddPsXqyzActivity.this).inflate(
				R.layout.popupwindow_camera, null);
		this.imageGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (AddPsXqyzActivity.this.imageAdapter.getItem(position) == null) {
					if (mPopupWindow == null) {
						mPopupWindow = new PopupWindow(popView, DpTransform
								.dip2px(AddPsXqyzActivity.this, 180),
								DpTransform.dip2px(AddPsXqyzActivity.this, 100));
					}
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					} else {
						mPopupWindow.showAsDropDown(view,
								-DpTransform.dip2px(AddPsXqyzActivity.this, 0),
								DpTransform.dip2px(AddPsXqyzActivity.this, 0));
					}
				}

			}
		});
		// 相机按钮
		Button btnCamera = (Button) popView.findViewById(R.id.btnCamera);
		btnCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File fileCache = ImageOptions.getCache(AddPsXqyzActivity.this);
				Intent intent = new Intent();
				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				// intent.addCategory(Intent.CATEGORY_DEFAULT);
				currentfile = new File(fileCache.getPath() + "/"
						+ UUID.randomUUID().toString() + ".jpg");
				if (currentfile.exists()) {
					currentfile.delete();
				}
				Uri uri = Uri.fromFile(currentfile);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, 3);
				mPopupWindow.dismiss();
			}
		});
		// 相册按钮
		Button btnPhoto = (Button) popView.findViewById(R.id.btnPhoto);
		btnPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(it, 2);
				mPopupWindow.dismiss();
			}
		});
		setTextWatcher();
		listRegions = WaterDectionary.getRegions();
		ArrayList<String> arrayList = new ArrayList<String>();
		for (Region region : listRegions) {
			if (region.getName().lastIndexOf("镇")==region.getName().length()-1||region.getName().contains("街道")) {
				arrayList.add("  "+region.getName());
			}
			else {
				if (region.getName().contains("村")) {
					arrayList.add("      "+region.getName());
				}
				else {
					arrayList.add(region.getName());

				}
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				AddPsXqyzActivity.this, R.layout.simple_spinner_item, arrayList);
		AddPsXqyzActivity.this.mRegionSpinner.setAdapter(adapter);
		setContent();

	}

	private void setContent() {
		Intent it = getIntent();
		mPsXqyz = (PsXqyz) it.getSerializableExtra("PsXqyz");
		if (mPsXqyz != null) {
			TextView titleTextView = (TextView) findViewById(R.id.titleTv);
			titleTextView.setText("修改畜禽养殖污染源");
			qymcEditText.setText(mPsXqyz.getQymc());
			fzrEditText.setText(mPsXqyz.getFzr());
			lxfsEditText.setText(mPsXqyz.getContact());
			mRegionSpinner.setSelection(WaterDectionary.findRegionIndex(mPsXqyz
					.getSsxz().getID()));
			nczEditText.setText(String.valueOf(mPsXqyz.getNcz()));
			zhuEditText.setText(String.valueOf(mPsXqyz.getZhuCount()));
			niuEditText.setText(String.valueOf(mPsXqyz.getNiuCount()));
			yangEditText.setText(String.valueOf(mPsXqyz.getYangCount()));
			tuEditText.setText(String.valueOf(mPsXqyz.getTuCount()));
			yslEditText.setText(String.valueOf(mPsXqyz.getYsl()));
			fspflEditText.setText(String.valueOf(mPsXqyz.getFspfl()));
			codEditText.setText(String.valueOf(mPsXqyz.getCod()));
			adEditText.setText(String.valueOf(mPsXqyz.getNh3N()));
			tpEditText.setText(String.valueOf(mPsXqyz.getPSum()));
			tnEditText.setText(String.valueOf(mPsXqyz.getNSum()));
			if (mPsXqyz.getImages() != null && mPsXqyz.getImages().size() > 0) {
				for (ProjectImage projectImage : mPsXqyz.getImages()) {
					projectImage.setType(ProjectImage.INTERNET);
					projectImages.add(projectImage);
				}
				this.imageAdapter.setImages(projectImages);
				this.imageAdapter.notifyDataSetChanged();
				LayoutParams lParams = this.imageGridView.getLayoutParams();
				int height = (this.projectImages.size() / 4 + 1);
				lParams.height = DpTransform.dip2px(this, 80 * height);
				this.imageGridView.setLayoutParams(lParams);
			}
			x = mPsXqyz.getX();
			y = mPsXqyz.getY();
			DecimalFormat df = new DecimalFormat("0.00000");
			xTv.setText("X: " + df.format(x));
			yTv.setText("Y: " + df.format(y));
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			} else {
				btnSendPs.setEnabled(false);
			}
		}
	}

	protected void uploadPsIndustry() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				PsXqyz psXqyz = createPollution();
				Message msg = new Message();
				msg.what = sendPs;
				try {
					HttpUtil.uploadPollutionSource(psXqyz, "Xqyzwry");
					msg.obj = "上传成功";
					mHandler.sendMessage(msg);
					setResult(1000);
					AddPsXqyzActivity.this.finish();
				} catch (Throwable e) {
					msg.obj = "上传失败";
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}

			}
		}).start();
	}

	protected PsXqyz createPollution() {
		if (mPsXqyz==null) {
			mPsXqyz=new PsXqyz();
		}
		mPsXqyz.setQymc(this.qymcEditText.getText().toString());
		mPsXqyz.setFzr(this.fzrEditText.getText().toString());
		mPsXqyz.setContact(this.lxfsEditText.getText().toString());
		mPsXqyz.setSsxz(creatRegion(this.mRegionSpinner.getSelectedItem()
				.toString().trim()));
		mPsXqyz.setNcz(Double.parseDouble(this.nczEditText.getText().toString()));
		mPsXqyz.setZhuCount(Integer.parseInt(this.zhuEditText.getText()
				.toString()));
		mPsXqyz.setNiuCount(Integer.parseInt(this.niuEditText.getText()
				.toString()));
		mPsXqyz.setYangCount(Integer.parseInt(this.yangEditText.getText()
				.toString()));
		mPsXqyz.setTuCount(Integer
				.parseInt(this.tuEditText.getText().toString()));
		mPsXqyz.setYsl(Double.parseDouble(this.yslEditText.getText().toString()));
		mPsXqyz.setFspfl(Double.parseDouble(this.fspflEditText.getText()
				.toString()));
		mPsXqyz.setCod(Double.parseDouble(this.codEditText.getText().toString()));
		mPsXqyz.setNh3N(Double.parseDouble(this.adEditText.getText().toString()));
		mPsXqyz.setPSum(Double.parseDouble(this.tpEditText.getText().toString()));
		mPsXqyz.setNSum(Double.parseDouble(this.tnEditText.getText().toString()));
		mPsXqyz.setX(x);
		mPsXqyz.setY(y);
		mPsXqyz.setImages(getImages());
		return mPsXqyz;
	}

	private List<ProjectImage> getImages() {
		for (ProjectImage projectImage : projectImages) {
			if (projectImage.getType() == ProjectImage.LOCAL) {
				projectImage
						.setName(UploadImageUtil.uploadImage(
								projectImage.getName(),
								"http://159.226.110.64:8001/WaterService/Files.svc/upload"));
			}
		}
		return projectImages;
	}

	private Region creatRegion(String rName) {
		// TODO Auto-generated method stub
		for (Region region : listRegions) {
			if (region.getName().equals(rName)) {
				return region;
			}
		}
		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 2) {
			x = data.getExtras().getDouble("X");
			y = data.getExtras().getDouble("Y");
			DecimalFormat df = new DecimalFormat("0.00000");
			xTv.setText("X: " + df.format(x));
			yTv.setText("Y: " + df.format(y));
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			}
		}
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 2) {
				Uri uri = data.getData();
				Cursor cursor = AddPsXqyzActivity.this.getContentResolver()
						.query(uri, null, null, null, null);
				cursor.moveToFirst();
				String imgPath = cursor.getString(1);
				ProjectImage projectImage = new ProjectImage();
				projectImage.setName(imgPath);
				projectImage.setType(ProjectImage.LOCAL);
				projectImages.add(projectImage);
				this.imageAdapter.setImages(projectImages);
				this.imageAdapter.notifyDataSetChanged();
				LayoutParams lParams = this.imageGridView.getLayoutParams();
				int height = (this.projectImages.size() / 4 + 1);
				lParams.height = DpTransform.dip2px(this, 80 * height);
				this.imageGridView.setLayoutParams(lParams);
				cursor.close();
			}
			if (requestCode == 3) {
				ProjectImage projectImage = new ProjectImage();
				projectImage.setName(currentfile.getPath());
				projectImage.setType(ProjectImage.LOCAL);
				projectImages.add(projectImage);
				this.imageAdapter.setImages(projectImages);
				this.imageAdapter.notifyDataSetChanged();
				LayoutParams lParams = this.imageGridView.getLayoutParams();
				int height = (this.projectImages.size() / 4 + 1);
				lParams.height = DpTransform.dip2px(this, 80 * height);
				this.imageGridView.setLayoutParams(lParams);
			}
		}
	}

	private boolean checkTextView() {
		return qymcEditText.getText().length() > 0
				&& fzrEditText.getText().length() > 0
				&& lxfsEditText.getText().length() > 0
				&& nczEditText.getText().length() > 0
				&& zhuEditText.getText().length() > 0
				&& niuEditText.getText().length() > 0
				&& yangEditText.getText().length() > 0
				&& tuEditText.getText().length() > 0
				&& yslEditText.getText().length() > 0
				&& fspflEditText.getText().length() > 0
				&& codEditText.getText().length() > 0
				&& adEditText.getText().length() > 0
				&& tpEditText.getText().length() > 0
				&& tnEditText.getText().length() > 0 && x != 0.0 && y != 0;
	}

	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl = new TextWatcherimpl();
		qymcEditText.addTextChangedListener(textWatcherimpl);
		fzrEditText.addTextChangedListener(textWatcherimpl);
		lxfsEditText.addTextChangedListener(textWatcherimpl);
		nczEditText.addTextChangedListener(textWatcherimpl);
		zhuEditText.addTextChangedListener(textWatcherimpl);
		niuEditText.addTextChangedListener(textWatcherimpl);
		yangEditText.addTextChangedListener(textWatcherimpl);
		tuEditText.addTextChangedListener(textWatcherimpl);
		yslEditText.addTextChangedListener(textWatcherimpl);
		fspflEditText.addTextChangedListener(textWatcherimpl);
		codEditText.addTextChangedListener(textWatcherimpl);
		adEditText.addTextChangedListener(textWatcherimpl);
		tpEditText.addTextChangedListener(textWatcherimpl);
		tnEditText.addTextChangedListener(textWatcherimpl);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
			mPopupWindow = null;
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}

	class TextWatcherimpl implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			} else {
				btnSendPs.setEnabled(false);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

		}

	}
}
