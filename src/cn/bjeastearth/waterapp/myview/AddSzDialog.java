package cn.bjeastearth.waterapp.myview;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.R;
import cn.bjeastearth.waterapp.RiverDetailActivity;
import cn.bjeastearth.waterapp.model.River;
import cn.bjeastearth.waterapp.model.RiverWaterQuality;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSzDialog extends DialogFragment {
	private Dialog dialog;
	private River mRiver;
	private EditText bodEditText;
	private EditText codEditText;
	private EditText doEditText;
	private EditText adEditText;
	private EditText xdEditText;
	private EditText tpEditText;
	private EditText tnEditText;
	private EditText sdEditText;
	private EditText wdEditText;
	private EditText zdEditText;
	private Button btnSave;
	private MyHandler mHandler;
	public AddSzDialog(River river) {
		this.mRiver = river;
	}

	public Dialog getDialog() {
		return dialog;
	}

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		mHandler=new MyHandler(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.dialog_addsz, container, true);
		btnSave = (Button) view.findViewById(R.id.btnSave);
		btnSave.setEnabled(false);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnSave.setEnabled(false);
				RiverWaterQuality riverWaterQuality=new RiverWaterQuality();
				riverWaterQuality.setBod(Double.parseDouble(bodEditText.getText().toString()));
				riverWaterQuality.setCod(Double.parseDouble(codEditText.getText().toString()));
				riverWaterQuality.setDo(Double.parseDouble(doEditText.getText().toString()));
				riverWaterQuality.setNh3N(Double.parseDouble(adEditText.getText().toString()));
				riverWaterQuality.setNitreN(Double.parseDouble(xdEditText.getText().toString()));
				riverWaterQuality.setPsum(Double.parseDouble(tpEditText.getText().toString()));
				riverWaterQuality.setNsum(Double.parseDouble(tnEditText.getText().toString()));
				riverWaterQuality.setSd(Double.parseDouble(sdEditText.getText().toString()));
				riverWaterQuality.setWd(Double.parseDouble(wdEditText.getText().toString()));
				riverWaterQuality.setZd(Double.parseDouble(zdEditText.getText().toString()));
				Date date=new Date();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
				String dateString=df.format(date);
				riverWaterQuality.setCTime(dateString);
				mRiver.addSzjl(riverWaterQuality);
				uploadRiver();
			}
		});
		Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss(true);
			}
		});
		setEditViews(view);
		return view;
	}

	protected void uploadRiver() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Message msg=mHandler.obtainMessage();
				try {
					HttpUtil.uploadRiver(mRiver);
					msg.obj="添加水质记录成功";
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					msg.obj="添加水质记录失败";
					e.printStackTrace();
				}
				finally{
					msg.sendToTarget();
				}
			}
		}).start();
	}

	private void setEditViews(View view) {
		bodEditText = (EditText) view.findViewById(R.id.bodEt);
		codEditText = (EditText) view.findViewById(R.id.codEt);
		doEditText = (EditText) view.findViewById(R.id.doEt);
		adEditText = (EditText) view.findViewById(R.id.adEt);
		xdEditText = (EditText) view.findViewById(R.id.xdEt);
		tpEditText = (EditText) view.findViewById(R.id.tpEt);
		tnEditText = (EditText) view.findViewById(R.id.tnEt);
		sdEditText = (EditText) view.findViewById(R.id.sdEt);
		wdEditText = (EditText) view.findViewById(R.id.wdEt);
		zdEditText = (EditText) view.findViewById(R.id.zdEt);
		TextWatcherimpl textWatcherimpl = new TextWatcherimpl();
		bodEditText.addTextChangedListener(textWatcherimpl);
		codEditText.addTextChangedListener(textWatcherimpl);
		doEditText.addTextChangedListener(textWatcherimpl);
		adEditText.addTextChangedListener(textWatcherimpl);
		xdEditText.addTextChangedListener(textWatcherimpl);
		tpEditText.addTextChangedListener(textWatcherimpl);
		tnEditText.addTextChangedListener(textWatcherimpl);
		sdEditText.addTextChangedListener(textWatcherimpl);
		wdEditText.addTextChangedListener(textWatcherimpl);
		zdEditText.addTextChangedListener(textWatcherimpl);		
	}

	public void dismiss(boolean isclose) {
		try {
			Field field = dialog.getClass().getDeclaredField("mShowing");
			field.setAccessible(true);
			// 将mShowing变量设为false，表示对话框已关闭
			field.set(dialog, isclose);
		} catch (Exception e) {
		}
		AddSzDialog.this.dismiss();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		dialog = super.onCreateDialog(savedInstanceState);
		return dialog;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			Field field = dialog.getClass().getDeclaredField("mShowing");
			field.setAccessible(true);
			// 将mShowing变量设为false，表示对话框已关闭
			field.set(dialog, false);
		} catch (Exception e) {
			Log.e("dialog", e.toString());
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		try {
			Field field = dialog.getClass().getDeclaredField("mShowing");
			field.setAccessible(true);
			// 将mShowing变量设为false，表示对话框已关闭
			field.set(dialog, true);
		} catch (Exception e) {
			Log.e("dialog", e.toString());
		}
		super.onStop();
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
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (bodEditText.getText().length() > 0
					&& codEditText.getText().length() > 0
					&& doEditText.getText().length() > 0
					&& adEditText.getText().length() > 0
					&& xdEditText.getText().length() > 0
					&& tpEditText.getText().length() > 0
					&& tnEditText.getText().length() > 0
					&& sdEditText.getText().length() > 0
					&& wdEditText.getText().length() > 0
					&& zdEditText.getText().length() > 0) {
				btnSave.setEnabled(true);
			}
			else {
				btnSave.setEnabled(false);
			}
		}

	}
	static class MyHandler extends Handler{
		WeakReference<AddSzDialog> activityReference;
		public MyHandler(AddSzDialog addSzDialog){
			activityReference=new WeakReference<AddSzDialog>(addSzDialog);
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Toast.makeText(activityReference.get().getActivity(), msg.obj.toString(),
					Toast.LENGTH_SHORT).show();
			RiverDetailActivity riverDetailActivity=(RiverDetailActivity)activityReference.get().getActivity();
			riverDetailActivity.refreshUi(activityReference.get().mRiver);
			activityReference.get().dismiss(true);
		}
	}
}
