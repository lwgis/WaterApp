package cn.bjeastearth.waterapp;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.http.UploadImageUtil;
import cn.bjeastearth.waterapp.AllNewsActivity.httpThread;
import cn.bjeastearth.waterapp.R.id;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.Pollution1;
import cn.bjeastearth.waterapp.model.Pollution2;
import cn.bjeastearth.waterapp.model.PollutionClass1;
import cn.bjeastearth.waterapp.model.PollutionClass2;
import cn.bjeastearth.waterapp.model.PollutionType;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.PsIndustry;
import cn.bjeastearth.waterapp.model.Region;
import cn.bjeastearth.waterapp.myview.DpTransform;
import cn.bjeastearth.waterapp.myview.MyTextButton;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class AddPsGyActivity extends Activity {
	private final int sendPs = 100;
	private Button btnBack;
	private EditText qymcEditText;
	private EditText fzrEditText;
	private EditText lxfsEditText;
	private EditText nczEditText;
	private EditText gyyslEditText;
	private EditText gyfspflzpEditText;
	private EditText gyfspflclEditText;
	private EditText codpflzpEditText;
	private EditText codpflclEditText;
	private EditText adpflzpEditText;
	private EditText adpflclEditText;
	private EditText tpzpEditText;
	private EditText tpclEditText;
	private EditText tnzpEditText;
	private EditText tnclEditText;
	private Spinner mRegionSpinner;
	private Spinner sfywsssSpinner;
	private Spinner sfdbpfSpinner;
	private MyTextButton btnSendPs;
	private List<Region> listRegions;
	private Button btnLocation;
	private GridView imageGridView;
	private AddImageAdapter imageAdapter;
	private ArrayList<String> allImageStrings;
	private TextView xTv;
	private TextView yTv;
	private double x = 0.0;
	private double y = 0.0;
	private PopupWindow mPopupWindow;
	private View popView;
	private File currentfile;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Gson gson = new Gson();
			if (msg.what == 1) {
				listRegions = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<Region>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Region region : listRegions) {
					arrayList.add(region.getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddPsGyActivity.this, R.layout.simple_spinner_item,
						arrayList);
				AddPsGyActivity.this.mRegionSpinner.setAdapter(adapter);
			}
			if (msg.what == sendPs) {
				Toast.makeText(AddPsGyActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				AddPsGyActivity.this.btnSendPs.setEnabled(true);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_gy);
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddPsGyActivity.this.finish();
			}
		});
		this.btnSendPs = (MyTextButton) findViewById(R.id.btnSendPs);
		this.btnSendPs.setEnabled(false);
		this.btnSendPs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddPsGyActivity.this.btnSendPs.setEnabled(false);
				AddPsGyActivity.this.uploadPsIndustry();
			}
		});
		qymcEditText = (EditText) findViewById(R.id.qymcEt);
		fzrEditText = (EditText) findViewById(R.id.fzrEt);
		lxfsEditText = (EditText) findViewById(R.id.lxfsEt);
		mRegionSpinner = (Spinner) findViewById(R.id.regionSpin);
		nczEditText = (EditText) findViewById(R.id.nczEt);
		gyyslEditText = (EditText) findViewById(R.id.gyyslEt);
		gyfspflzpEditText = (EditText) findViewById(R.id.gyfspflzpEt);
		gyfspflclEditText = (EditText) findViewById(R.id.gyfspflclEt);
		codpflzpEditText = (EditText) findViewById(R.id.codpflzpEt);
		codpflclEditText = (EditText) findViewById(R.id.codpflclEt);
		adpflzpEditText = (EditText) findViewById(R.id.adpflzpEt);
		adpflclEditText = (EditText) findViewById(R.id.adpflclEt);
		tpzpEditText = (EditText) findViewById(R.id.tpzpEt);
		tpclEditText = (EditText) findViewById(R.id.tpclEt);
		tnzpEditText = (EditText) findViewById(R.id.tnzpEt);
		tnclEditText = (EditText) findViewById(R.id.tnclEt);
		sfywsssSpinner = (Spinner) findViewById(R.id.sfywsclssSpin);
		sfdbpfSpinner = (Spinner) findViewById(R.id.sfdbpfSpin);
		ArrayList<String> listTrueAndFlase = new ArrayList<String>();
		listTrueAndFlase.add("是");
		listTrueAndFlase.add("否");
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, listTrueAndFlase);
		sfywsssSpinner.setAdapter(arrayAdapter);
		sfdbpfSpinner.setAdapter(arrayAdapter);
		this.xTv = (TextView) findViewById(R.id.xTv);
		this.yTv = (TextView) findViewById(R.id.yTv);
		this.btnLocation = (Button) findViewById(R.id.btnAddLocation);
		this.btnLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(AddPsGyActivity.this,
						LocationActivity.class);
				AddPsGyActivity.this.startActivityForResult(it, 1);
			}
		});

		// 图片
		this.imageGridView = (GridView) findViewById(R.id.imageGridView);
		this.allImageStrings = new ArrayList<String>();
		this.imageAdapter = new AddImageAdapter(this, allImageStrings);
		this.imageGridView.setAdapter(imageAdapter);
		popView = LayoutInflater.from(AddPsGyActivity.this).inflate(
				R.layout.popupwindow_camera, null);
		this.imageGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (AddPsGyActivity.this.imageAdapter.getItem(position) == null) {
					if (mPopupWindow == null) {
						mPopupWindow = new PopupWindow(popView, DpTransform
								.dip2px(AddPsGyActivity.this, 180), DpTransform
								.dip2px(AddPsGyActivity.this, 100));
					}
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					} else {
						mPopupWindow.showAsDropDown(view,
								-DpTransform.dip2px(AddPsGyActivity.this, 0),
								DpTransform.dip2px(AddPsGyActivity.this, 0));
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
				File fileCache = ImageOptions.getCache(AddPsGyActivity.this);
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
		new Thread(new HttpThread("Xzq")).start();
	}

	protected void uploadPsIndustry() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				PsIndustry psIndustry = createPollution();
				Message msg = new Message();
				msg.what = sendPs;
				try {
					HttpUtil.uploadPollutionSource(psIndustry, "Gywry");
					msg.obj = "上传成功";
					mHandler.sendMessage(msg);
					AddPsGyActivity.this.finish();
				} catch (Throwable e) {
					msg.obj = "上传失败";
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}

			}
		}).start();
	}

	protected PsIndustry createPollution() {
		// TODO Auto-generated method stub
		PsIndustry psIndustry = new PsIndustry();
		psIndustry.setQymc(this.qymcEditText.getText().toString());
		psIndustry.setFzr(this.fzrEditText.getText().toString());
		psIndustry.setContact(this.lxfsEditText.getText().toString());
		psIndustry.setSsxz(creatRegion(this.mRegionSpinner.getSelectedItem()
				.toString()));
		psIndustry.setNcz(Double.parseDouble(this.nczEditText.getText()
				.toString()));
		psIndustry.setGyysl(Double.parseDouble(this.gyyslEditText.getText()
				.toString()));
		psIndustry.setGyfspfl_z(Double.parseDouble(this.gyfspflzpEditText
				.getText().toString()));
		psIndustry.setGyfspfl_c(Double.parseDouble(this.gyfspflclEditText
				.getText().toString()));
		psIndustry.setCod_z(Double.parseDouble(this.codpflzpEditText.getText()
				.toString()));
		psIndustry.setCod_c(Double.parseDouble(this.codpflclEditText.getText()
				.toString()));
		psIndustry.setNH3N_z(Double.parseDouble(this.adpflzpEditText.getText()
				.toString()));
		psIndustry.setNH3N_c(Double.parseDouble(this.adpflclEditText.getText()
				.toString()));
		psIndustry.setPSum_z(Double.parseDouble(this.tpzpEditText.getText()
				.toString()));
		psIndustry.setPSum_c(Double.parseDouble(this.tpclEditText.getText()
				.toString()));
		psIndustry.setTN_z(Double.parseDouble(this.tnzpEditText.getText()
				.toString()));
		psIndustry.setTN_c(Double.parseDouble(this.tnclEditText.getText()
				.toString()));
		psIndustry.setFsclss(this.sfywsssSpinner.getSelectedItem().toString());
		psIndustry.setSfdb(this.sfdbpfSpinner.getSelectedItem().toString());
		psIndustry.setX(x);
		psIndustry.setY(y);
		psIndustry.setImages(getImages());
		return psIndustry;
	}

	private List<ProjectImage> getImages() {
		ArrayList<ProjectImage> images = new ArrayList<ProjectImage>();
		for (String imageString : allImageStrings) {
			ProjectImage projectImage = new ProjectImage();
			projectImage
					.setName(UploadImageUtil
							.uploadImage(imageString,
									"http://159.226.110.64:8001/WaterService/Files.svc/upload"));
			images.add(projectImage);
		}
		if (images.size() > 0) {
			return images;
		}
		return null;
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
				Cursor cursor = AddPsGyActivity.this.getContentResolver()
						.query(uri, null, null, null, null);
				cursor.moveToFirst();
				String imgPath = cursor.getString(1);
				allImageStrings.add(imgPath);
				this.imageAdapter.setImages(allImageStrings);
				this.imageAdapter.notifyDataSetChanged();
				LayoutParams lParams = this.imageGridView.getLayoutParams();
				int height = (this.allImageStrings.size() / 4 + 1);
				lParams.height = DpTransform.dip2px(this, 80 * height);
				this.imageGridView.setLayoutParams(lParams);
				cursor.close();
			}
			if (requestCode == 3) {
				allImageStrings.add(currentfile.getPath());
				this.imageAdapter.setImages(allImageStrings);
				this.imageAdapter.notifyDataSetChanged();
				LayoutParams lParams = this.imageGridView.getLayoutParams();
				int height = (this.allImageStrings.size() / 4 + 1);
				lParams.height = DpTransform.dip2px(this, 80 * height);
				this.imageGridView.setLayoutParams(lParams);
			}
		}
	}

	private boolean checkTextView() {
		boolean result = (qymcEditText.getText().length() > 0
				&& fzrEditText.getText().length() > 0
				&& lxfsEditText.getText().length() > 0
				&& nczEditText.getText().length() > 0
				&& gyyslEditText.getText().length() > 0
				&& gyfspflzpEditText.getText().length() > 0
				&& gyfspflclEditText.getText().length() > 0
				&& codpflzpEditText.getText().length() > 0
				&& codpflclEditText.getText().length() > 0
				&& adpflzpEditText.getText().length() > 0
				&& adpflclEditText.getText().length() > 0
				&& tpzpEditText.getText().length() > 0
				&& tpclEditText.getText().length() > 0
				&& tnzpEditText.getText().length() > 0
				&& tnclEditText.getText().length() > 0 && x != 0.0 && y != 0);
		return result;
	}

	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl = new TextWatcherimpl();
		qymcEditText.addTextChangedListener(textWatcherimpl);
		fzrEditText.addTextChangedListener(textWatcherimpl);
		lxfsEditText.addTextChangedListener(textWatcherimpl);
		nczEditText.addTextChangedListener(textWatcherimpl);
		gyyslEditText.addTextChangedListener(textWatcherimpl);
		gyfspflzpEditText.addTextChangedListener(textWatcherimpl);
		gyfspflclEditText.addTextChangedListener(textWatcherimpl);
		codpflzpEditText.addTextChangedListener(textWatcherimpl);
		codpflclEditText.addTextChangedListener(textWatcherimpl);
		adpflzpEditText.addTextChangedListener(textWatcherimpl);
		adpflclEditText.addTextChangedListener(textWatcherimpl);
		tpzpEditText.addTextChangedListener(textWatcherimpl);
		tpclEditText.addTextChangedListener(textWatcherimpl);
		tnzpEditText.addTextChangedListener(textWatcherimpl);
		tnclEditText.addTextChangedListener(textWatcherimpl);
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

	class HttpThread implements Runnable {
		private String typeString;

		@Override
		public void run() {
			String jsonString = HttpUtil.getDectionaryString(typeString);
			Message msg = new Message();
			if (!jsonString.equals("")) {
				msg.obj = jsonString;
				if (typeString.equals("Xzq")) {
					msg.what = 1;
				}
				if (typeString.equals("Dept")) {
					msg.what = 2;
				}
				if (typeString.equals("GywrType")) {
					msg.what = 3;
				}
				if (typeString.equals("WrwClass1")) {
					msg.what = 4;
				}
				if (typeString.equals("WrwClass2")) {
					msg.what = 5;
				}
				mHandler.sendMessage(msg);
			}
		}

		public HttpThread(String typeString) {
			super();
			this.typeString = typeString;
		}
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
