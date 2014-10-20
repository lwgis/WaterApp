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
	private ArrayList<ProjectImage> projectImages;
	private TextView xTv;
	private TextView yTv;
	private double x = 0.0;
	private double y = 0.0;
	private PopupWindow mPopupWindow;
	private View popView;
	private File currentfile;
	private PsIndustry mPsIndustry ;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
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

		listRegions = WaterDectionary.getRegions();
		ArrayList<String> arrayList = new ArrayList<String>();
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
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				AddPsGyActivity.this, R.layout.simple_spinner_item, arrayList);
		 AddPsGyActivity.this.mRegionSpinner.setAdapter(adapter);

		// 图片
		this.imageGridView = (GridView) findViewById(R.id.imageGridView);
		this.projectImages = new ArrayList<ProjectImage>();
		this.imageAdapter = new AddImageAdapter(this, projectImages);
		this.imageGridView.setAdapter(imageAdapter);
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
		popView = LayoutInflater.from(AddPsGyActivity.this).inflate(
				R.layout.popupwindow_camera, null);
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
		setContent();
	}
	/**
	 * 填充修改内容
	 */
	private void setContent() {
		Intent it=getIntent();
		mPsIndustry=(PsIndustry)it.getSerializableExtra("PsIndustry");
		if (mPsIndustry!=null) {
			TextView titleTextView=(TextView)findViewById(R.id.titleTv);
			titleTextView.setText("修改工业污染源");
			qymcEditText.setText(mPsIndustry.getQymc());
			fzrEditText.setText(mPsIndustry.getFzr());
			lxfsEditText.setText(mPsIndustry.getContact());
			mRegionSpinner.setSelection(WaterDectionary.findRegionIndex(mPsIndustry.getSsxz().getID()));
			nczEditText.setText(String.valueOf(mPsIndustry.getNcz()));
			gyyslEditText.setText(String.valueOf(mPsIndustry.getGyysl()));
			gyfspflzpEditText.setText(String.valueOf(mPsIndustry.getGyfspfl_z()));
			gyfspflclEditText.setText(String.valueOf(mPsIndustry.getGyfspfl_c()));
			codpflzpEditText.setText(String.valueOf(mPsIndustry.getCod_z()));
			codpflclEditText.setText(String.valueOf(mPsIndustry.getCod_c()));
			adpflzpEditText.setText(String.valueOf(mPsIndustry.getNH3N_z()));
			adpflclEditText.setText(String.valueOf(mPsIndustry.getNH3N_c()));
			tpzpEditText.setText(String.valueOf(mPsIndustry.getPSum_z()));
			tpclEditText.setText(String.valueOf(mPsIndustry.getPSum_c()));
			tnzpEditText.setText(String.valueOf(mPsIndustry.getTN_z()));
			tnclEditText.setText(String.valueOf(mPsIndustry.getCod_c()));
			sfywsssSpinner.setSelection(mPsIndustry.getFsclss().equals("是")?0:1);
			sfdbpfSpinner.setSelection(mPsIndustry.getSfdb().equals("是")?0:1);
			if (mPsIndustry.getImages()!=null&&mPsIndustry.getImages().size()>0) {
				for (ProjectImage projectImage : mPsIndustry.getImages()) {
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
			x = mPsIndustry.getX();
			y = mPsIndustry.getY();
			DecimalFormat df = new DecimalFormat("0.00000");
			xTv.setText("X: " + df.format(x));
			yTv.setText("Y: " + df.format(y));
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			}
			else {
				btnSendPs.setEnabled(false);
			}
		}
	}


	protected void uploadPsIndustry() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				PsIndustry mPsIndustry = createPollution();
				Message msg = new Message();
				msg.what = sendPs;
				try {
					HttpUtil.uploadPollutionSource(mPsIndustry, "Gywry");
					msg.obj = "上传成功";
					mHandler.sendMessage(msg);
					setResult(1000);
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
		if (mPsIndustry==null) {
			mPsIndustry=new  PsIndustry();
		}
		mPsIndustry.setQymc(this.qymcEditText.getText().toString());
		mPsIndustry.setFzr(this.fzrEditText.getText().toString());
		mPsIndustry.setContact(this.lxfsEditText.getText().toString());
		mPsIndustry.setSsxz(creatRegion(this.mRegionSpinner.getSelectedItem()
				.toString().trim()));
		mPsIndustry.setNcz(Double.parseDouble(this.nczEditText.getText()
				.toString()));
		mPsIndustry.setGyysl(Double.parseDouble(this.gyyslEditText.getText()
				.toString()));
		mPsIndustry.setGyfspfl_z(Double.parseDouble(this.gyfspflzpEditText
				.getText().toString()));
		mPsIndustry.setGyfspfl_c(Double.parseDouble(this.gyfspflclEditText
				.getText().toString()));
		mPsIndustry.setCod_z(Double.parseDouble(this.codpflzpEditText.getText()
				.toString()));
		mPsIndustry.setCod_c(Double.parseDouble(this.codpflclEditText.getText()
				.toString()));
		mPsIndustry.setNH3N_z(Double.parseDouble(this.adpflzpEditText.getText()
				.toString()));
		mPsIndustry.setNH3N_c(Double.parseDouble(this.adpflclEditText.getText()
				.toString()));
		mPsIndustry.setPSum_z(Double.parseDouble(this.tpzpEditText.getText()
				.toString()));
		mPsIndustry.setPSum_c(Double.parseDouble(this.tpclEditText.getText()
				.toString()));
		mPsIndustry.setTN_z(Double.parseDouble(this.tnzpEditText.getText()
				.toString()));
		mPsIndustry.setTN_c(Double.parseDouble(this.tnclEditText.getText()
				.toString()));
		mPsIndustry.setFsclss(this.sfywsssSpinner.getSelectedItem().toString());
		mPsIndustry.setSfdb(this.sfdbpfSpinner.getSelectedItem().toString());
		mPsIndustry.setX(x);
		mPsIndustry.setY(y);
		mPsIndustry.setImages(getImages());
		return mPsIndustry;
	}

	private List<ProjectImage> getImages() {
		for (ProjectImage projectImage : projectImages) {
			if (projectImage.getType()==ProjectImage.LOCAL) {
				projectImage
				.setName(UploadImageUtil
						.uploadImage(projectImage.getName(),
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
			else {
				btnSendPs.setEnabled(false);
			}
		}
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 2) {
				Uri uri = data.getData();
				Cursor cursor = AddPsGyActivity.this.getContentResolver()
						.query(uri, null, null, null, null);
				cursor.moveToFirst();
				String imgPath = cursor.getString(1);
				ProjectImage projectImage=new ProjectImage();
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
				ProjectImage projectImage=new ProjectImage();
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
