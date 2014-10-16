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
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.PsScyz;
import cn.bjeastearth.waterapp.model.PsScyzType;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddPsScyzActivity extends Activity {
	private final int sendPs=100;
	private Button btnBack;
	private EditText fzrEditText;
	private EditText lxfsEditText;
	private Spinner mRegionSpinner;
	private EditText nczEditText;
	private EditText ylclEditText;
	private EditText xlclEditText;
	private EditText blclEditText;
	private EditText qtclEditText;
	private EditText ylmjEditText;
	private EditText xlmjEditText;
	private EditText blmjEditText;
	private EditText qtmjEditText;
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
	private double x=0.0;
	private double y=0.0;
	private PopupWindow mPopupWindow;
	private View popView;
	private File currentfile;
	private PsScyz mPsScyz;
	private Handler  mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==sendPs) {
				Toast.makeText(AddPsScyzActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			    AddPsScyzActivity.this.btnSendPs.setEnabled(true);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_addps_scyz);
		btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsScyzActivity.this.finish();
			}
		});
		this.btnSendPs=(MyTextButton)findViewById(R.id.btnSendPs);
		 this.btnSendPs.setEnabled(false);
		 this.btnSendPs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPsScyzActivity.this.btnSendPs.setEnabled(false);
			    AddPsScyzActivity.this.uploadPsIndustry();
			}
		});
		fzrEditText=(EditText)findViewById(R.id.fzrEt);
		lxfsEditText=(EditText)findViewById(R.id.lxfsEt);
		mRegionSpinner=(Spinner)findViewById(R.id.regionSpin);
		nczEditText=(EditText)findViewById(R.id.nczEt);
		ylclEditText=(EditText)findViewById(R.id.ylclEt);
		xlclEditText=(EditText)findViewById(R.id.xlclEt);
		blclEditText=(EditText)findViewById(R.id.blclEt);
		qtclEditText=(EditText)findViewById(R.id.qtclEt);
		ylmjEditText=(EditText)findViewById(R.id.ylmjEt);
		xlmjEditText=(EditText)findViewById(R.id.xlmjEt);
		blmjEditText=(EditText)findViewById(R.id.blmjEt);
		qtmjEditText=(EditText)findViewById(R.id.qtmjEt);
		codEditText=(EditText)findViewById(R.id.codEt);
		adEditText=(EditText)findViewById(R.id.adEt);
		tpEditText=(EditText)findViewById(R.id.tpEt);
		tnEditText=(EditText)findViewById(R.id.tnEt);	
		 this.xTv=(TextView)findViewById(R.id.xTv);
		 this.yTv=(TextView)findViewById(R.id.yTv);
		 this.btnLocation=(Button)findViewById(R.id.btnAddLocation);
		 this.btnLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent it=new Intent(AddPsScyzActivity.this,LocationActivity.class);
					AddPsScyzActivity.this.startActivityForResult(it, 2);
			}
		});
		 
		 //图片
		 this.imageGridView=(GridView)findViewById(R.id.imageGridView);
		 this.projectImages=new ArrayList<ProjectImage>();
		 this.imageAdapter=new AddImageAdapter(this,projectImages);
		 this.imageGridView.setAdapter(imageAdapter);
		 popView = LayoutInflater.from(AddPsScyzActivity.this)
					.inflate(R.layout.popupwindow_camera, null);
		 this.imageGridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (AddPsScyzActivity.this.imageAdapter.getItem(position)==null) {
					if (mPopupWindow==null) {
						mPopupWindow=new PopupWindow(popView,DpTransform.dip2px(AddPsScyzActivity.this, 180),DpTransform.dip2px(AddPsScyzActivity.this, 100));
					}
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					else {
						mPopupWindow.showAsDropDown(view,-DpTransform.dip2px(AddPsScyzActivity.this, 0),DpTransform.dip2px(AddPsScyzActivity.this, 0));
					}
				}
				
			}
		});
		 //相机按钮
		 Button btnCamera=(Button)popView.findViewById(R.id.btnCamera);
		 btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 File fileCache = ImageOptions.getCache(AddPsScyzActivity.this);
			 Intent intent = new Intent();
				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//				intent.addCategory(Intent.CATEGORY_DEFAULT);
				currentfile = new File(fileCache.getPath()+"/"+ UUID.randomUUID().toString() + ".jpg");
				if (currentfile.exists()) {
					currentfile.delete();
				}
				Uri uri = Uri.fromFile(currentfile);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, 3);
				mPopupWindow.dismiss();
			}
		});
		 //相册按钮
		 Button btnPhoto=(Button)popView.findViewById(R.id.btnPhoto);
		 btnPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(it,2);		
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
					AddPsScyzActivity.this, R.layout.simple_spinner_item, arrayList);
			AddPsScyzActivity.this.mRegionSpinner.setAdapter(adapter);
			setContent();
	}



	private void setContent() {
		Intent it = getIntent();
		mPsScyz = (PsScyz) it.getSerializableExtra("PsScyz");
		if (mPsScyz != null) {
			TextView titleTextView = (TextView) findViewById(R.id.titleTv);
			titleTextView.setText("修改水产养殖污染源");
			fzrEditText.setText(mPsScyz.getFzr());
			lxfsEditText.setText(mPsScyz.getContact());
			mRegionSpinner.setSelection(WaterDectionary.findRegionIndex(mPsScyz
					.getSsxz().getID()));
			nczEditText.setText(String.valueOf(mPsScyz.getNcz()));
			ylclEditText.setText(String.valueOf(mPsScyz.getYu()));
			xlclEditText.setText(String.valueOf(mPsScyz.getXia()));
			blclEditText.setText(String.valueOf(mPsScyz.getBei()));
			qtclEditText.setText(String.valueOf(mPsScyz.getQita()));
			ylmjEditText.setText(String.valueOf(mPsScyz.getAYu()));
			xlmjEditText.setText(String.valueOf(mPsScyz.getAXia()));
			blmjEditText.setText(String.valueOf(mPsScyz.getABei()));
			qtmjEditText.setText(String.valueOf(mPsScyz.getAQita()));
			codEditText.setText(String.valueOf(mPsScyz.getCod()));
			adEditText.setText(String.valueOf(mPsScyz.getNh3N()));
			tpEditText.setText(String.valueOf(mPsScyz.getPSum()));
			tnEditText.setText(String.valueOf(mPsScyz.getNSum()));
			if (mPsScyz.getImages() != null && mPsScyz.getImages().size() > 0) {
				for (ProjectImage projectImage : mPsScyz.getImages()) {
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
			x = mPsScyz.getX();
			y = mPsScyz.getY();
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
				PsScyz psScyz= createPollution();
				Message msg=new Message();
				msg.what=sendPs;
				try {
					HttpUtil.uploadPollutionSource(psScyz,"Scyzwry");
					msg.obj="上传成功";
					mHandler.sendMessage(msg);
					setResult(1000);
					AddPsScyzActivity.this.finish();
				} catch (Throwable e) {
					msg.obj="上传失败";
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	protected PsScyz createPollution() {
		// TODO Auto-generated method stub
		if (mPsScyz==null) {
			mPsScyz=new PsScyz();
		}
		mPsScyz.setFzr(this.fzrEditText.getText().toString());
		mPsScyz.setContact(this.lxfsEditText.getText().toString());
		mPsScyz.setSsxz(creatRegion(this.mRegionSpinner.getSelectedItem().toString().trim()));
		mPsScyz.setNcz(Double.parseDouble(this.nczEditText.getText().toString()));
		mPsScyz.setYu(Double.parseDouble(this.ylclEditText.getText().toString()));
		mPsScyz.setXia(Double.parseDouble(this.xlclEditText.getText().toString()));
		mPsScyz.setBei(Double.parseDouble(this.blclEditText.getText().toString()));
		mPsScyz.setQita(Double.parseDouble(this.qtclEditText.getText().toString()));
		mPsScyz.setAYu(Double.parseDouble(this.ylmjEditText.getText().toString()));
		mPsScyz.setAXia(Double.parseDouble(this.xlmjEditText.getText().toString()));
		mPsScyz.setABei(Double.parseDouble(this.blmjEditText.getText().toString()));
		mPsScyz.setAQita(Double.parseDouble(this.qtmjEditText.getText().toString()));
		mPsScyz.setCod(Double.parseDouble(this.codEditText.getText().toString()));
		mPsScyz.setNh3N(Double.parseDouble(this.adEditText.getText().toString()));
		mPsScyz.setPSum(Double.parseDouble(this.tpEditText.getText().toString()));
		mPsScyz.setNSum(Double.parseDouble(this.tnEditText.getText().toString()));
		mPsScyz.setX(x);
		mPsScyz.setY(y);
		mPsScyz.setImages(getImages());
		return mPsScyz;
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
		if (resultCode==2) {
			x=data.getExtras().getDouble("X");
			y=data.getExtras().getDouble("Y");
			DecimalFormat df = new DecimalFormat("0.00000");   
			xTv.setText("X: "+df.format(x));
			yTv.setText("Y: "+df.format(y));
			if (checkTextView()) {
				btnSendPs.setEnabled(true);
			}
		}
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 2) {
				Uri uri = data.getData();
				Cursor cursor = AddPsScyzActivity.this.getContentResolver()
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
		return fzrEditText.getText().length() > 0
				&& lxfsEditText.getText().length() > 0
				&& nczEditText.getText().length() > 0
				&& ylclEditText.getText().length() > 0
				&& xlclEditText.getText().length() > 0
				&& blclEditText.getText().length() > 0
				&& qtclEditText.getText().length() > 0
				&& ylmjEditText.getText().length() > 0
				&& xlmjEditText.getText().length() > 0
				&& blmjEditText.getText().length() > 0
				&& qtmjEditText.getText().length() > 0
				&& codEditText.getText().length() > 0
				&& adEditText.getText().length() > 0
				&& tpEditText.getText().length() > 0
				&& tnEditText.getText().length() > 0
				&& x!=0.0&& y!=0;
	}
	private void setTextWatcher() {
		TextWatcherimpl textWatcherimpl=new TextWatcherimpl();
		fzrEditText.addTextChangedListener(textWatcherimpl);
		lxfsEditText.addTextChangedListener(textWatcherimpl);
		nczEditText.addTextChangedListener(textWatcherimpl);
		ylclEditText.addTextChangedListener(textWatcherimpl);
		xlclEditText.addTextChangedListener(textWatcherimpl);
		blclEditText.addTextChangedListener(textWatcherimpl);
		qtclEditText.addTextChangedListener(textWatcherimpl);
		ylmjEditText.addTextChangedListener(textWatcherimpl);
		xlmjEditText.addTextChangedListener(textWatcherimpl);
		blmjEditText.addTextChangedListener(textWatcherimpl);
		qtmjEditText.addTextChangedListener(textWatcherimpl);
		codEditText.addTextChangedListener(textWatcherimpl);
		adEditText.addTextChangedListener(textWatcherimpl);
		tpEditText.addTextChangedListener(textWatcherimpl);
		tnEditText.addTextChangedListener(textWatcherimpl);
	}
	
	class TextWatcherimpl implements TextWatcher{

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
			}
			else {
				btnSendPs.setEnabled(false);
			}
		}

		@Override 
		public void afterTextChanged(Editable s) {
			
		}
		
	}
}

