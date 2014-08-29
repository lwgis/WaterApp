package cn.bjeastearth.waterapp.myview;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bjeastearth.http.HttpUtil;
import cn.bjeastearth.waterapp.R;
import cn.bjeastearth.waterapp.RiverDetailActivity;
import cn.bjeastearth.waterapp.model.Department;
import cn.bjeastearth.waterapp.model.River;
import cn.bjeastearth.waterapp.model.RiverProject;
import cn.bjeastearth.waterapp.model.RiverRepairPlan;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddZljhDialog extends DialogFragment {
	private static final int MSG_PROJECT = 1;
	private static final int MSG_UPLOAD = 2;
	private static final int MSG_DEPARTMENT = 3;
	private River mRiver;
	private Dialog dialog;
	private Spinner xmmcSpinner;
	private EditText jhrwEditText;
	private TextView dwTextView;
	private DatePicker wcsjDatePicker;
	private EditText wcjdEditText;
	private Spinner zrdwsSpinner;
	private EditText fzrEditText;
	private MyHandler mHandler;
	private Button btnSave;
	private List<RiverProject> riverProjects;
	private List<Department> departments;
	private int hdzljhID = -1;
	private boolean isGetProject = false;
	private boolean isGetDepartment = false;

	public AddZljhDialog(River river) {
		this.mRiver = river;
	}

	public AddZljhDialog(River river, int hid) {
		this.mRiver = river;
		this.hdzljhID = hid;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setStyle(STYLE_NO_TITLE, 0);
		mHandler = new MyHandler(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.dialog_addzljh, container, true);
		setEditView(view);
		new Thread(new Runnable() {

			@Override
			public void run() {
				String jsonString = HttpUtil.getAllRiverProjectTypeString();
				Message msg = mHandler.obtainMessage();
				msg.what = MSG_PROJECT;
				msg.obj = jsonString;
				msg.sendToTarget();
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				String jsonString = HttpUtil.getDectionaryString("Dept");
				Message msg = mHandler.obtainMessage();
				msg.what = MSG_DEPARTMENT;
				msg.obj = jsonString;
				msg.sendToTarget();
			}
		}).start();
		btnSave = (Button) view.findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (hdzljhID==-1) {
					RiverRepairPlan riverRepairPlan = new RiverRepairPlan();
					riverRepairPlan.setXm(findRiverProjectByName(xmmcSpinner
							.getSelectedItem().toString()));
					riverRepairPlan.setContent(jhrwEditText.getText().toString());
					GregorianCalendar date = new GregorianCalendar(wcsjDatePicker
							.getYear(), wcsjDatePicker.getMonth(), wcsjDatePicker
							.getDayOfMonth());
					riverRepairPlan.setETime(date.getTime());
					riverRepairPlan.setWcjd(Double.parseDouble(wcjdEditText
							.getText().toString()));
					riverRepairPlan.setZrdept(findDepartMent(zrdwsSpinner
							.getSelectedItem().toString()));
					riverRepairPlan.setFzr(fzrEditText.getText().toString());
					mRiver.addZljh(riverRepairPlan);
				}
				else {
					RiverRepairPlan riverRepairPlan = findRiverRepairPlanByID(hdzljhID);
					riverRepairPlan.setXm(findRiverProjectByName(xmmcSpinner
							.getSelectedItem().toString()));
					riverRepairPlan.setContent(jhrwEditText.getText().toString());
					GregorianCalendar date = new GregorianCalendar(wcsjDatePicker
							.getYear(), wcsjDatePicker.getMonth(), wcsjDatePicker
							.getDayOfMonth());
					riverRepairPlan.setETime(date.getTime());
					riverRepairPlan.setWcjd(Double.parseDouble(wcjdEditText
							.getText().toString()));
					riverRepairPlan.setZrdept(findDepartMent(zrdwsSpinner
							.getSelectedItem().toString()));
					riverRepairPlan.setFzr(fzrEditText.getText().toString());
				}
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
		return view;
	}

	protected Department findDepartMent(String departmentName) {
		for (Department department : departments) {
			if (department.getName().equals(departmentName)) {
				return department;
			}
		}
		return null;
	}

	protected void uploadRiver() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = mHandler.obtainMessage();
				msg.what = MSG_UPLOAD;
				String opString="添加";
				if (hdzljhID!=-1) {
					opString="修改";
				}
				try {
					HttpUtil.uploadRiver(mRiver);
					msg.obj = opString+"整治计划成功";
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					msg.obj =opString+ "整治计划失败";
					e.printStackTrace();
				} finally {
					msg.sendToTarget();
				}
			}
		}).start();
	}

	public void dismiss(boolean isclose) {
		try {
			Field field = dialog.getClass().getDeclaredField("mShowing");
			field.setAccessible(true);
			// 将mShowing变量设为false，表示对话框已关闭
			field.set(dialog, isclose);
		} catch (Exception e) {
		}
		AddZljhDialog.this.dismiss();
	}

	private void setEditView(View view) {
		xmmcSpinner = (Spinner) view.findViewById(R.id.xmmcSpin);
		jhrwEditText = (EditText) view.findViewById(R.id.jhrwEt);
		dwTextView = (TextView) view.findViewById(R.id.dwTv);
		wcsjDatePicker = (DatePicker) view.findViewById(R.id.wcsjDp);
		wcjdEditText = (EditText) view.findViewById(R.id.wcjdEt);
		zrdwsSpinner = (Spinner) view.findViewById(R.id.zrdwSpin);
		fzrEditText = (EditText) view.findViewById(R.id.fzrEt);
		TextWatcherImpl textWatcherImpl = new TextWatcherImpl();
		jhrwEditText.addTextChangedListener(textWatcherImpl);
		wcjdEditText.addTextChangedListener(textWatcherImpl);
		fzrEditText.addTextChangedListener(textWatcherImpl);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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

	/**
	 * 通过项目类型设置单位
	 */
	public void setDwByProjectName() {
		RiverProject riverProject = findRiverProjectByName(xmmcSpinner
				.getSelectedItem().toString());
		if (riverProject != null) {
			dwTextView.setText(riverProject.getUnit());
		}
	}

	/**
	 * 查找RiverProject
	 * 
	 * @param name
	 *            名称
	 * @return 治理项目
	 */
	private RiverProject findRiverProjectByName(String name) {
		for (RiverProject riverProject : riverProjects) {
			if (riverProject.getName().equals(name)) {
				return riverProject;
			}
		}
		return null;
	}

	static class MyHandler extends Handler {
		WeakReference<AddZljhDialog> reference;

		public MyHandler(AddZljhDialog addZljhDialog) {
			reference = new WeakReference<AddZljhDialog>(addZljhDialog);
		}

		@Override
		public void handleMessage(Message msg) {
			final AddZljhDialog addZljhDialog = reference.get();
			super.handleMessage(msg);
			if (msg.what == MSG_PROJECT) {
				Gson gson = new Gson();
				addZljhDialog.riverProjects = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<RiverProject>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (RiverProject riverProject : addZljhDialog.riverProjects) {
					arrayList.add(riverProject.getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						addZljhDialog.getActivity(),
						R.layout.simple_spinner_item, arrayList);
				addZljhDialog.xmmcSpinner.setAdapter(adapter);
				addZljhDialog.xmmcSpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								addZljhDialog.setDwByProjectName();
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
								// TODO Auto-generated method stub

							}
						});
				addZljhDialog.isGetProject = true;
			}
			if (msg.what == MSG_DEPARTMENT) {
				Gson gson = new Gson();
				addZljhDialog.departments = gson.fromJson(msg.obj.toString(),
						new TypeToken<List<Department>>() {
						}.getType());
				ArrayList<String> arrayList = new ArrayList<String>();
				for (Department department : addZljhDialog.departments) {
					arrayList.add(department.getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						addZljhDialog.getActivity(),
						R.layout.simple_spinner_item, arrayList);
				addZljhDialog.zrdwsSpinner.setAdapter(adapter);
				addZljhDialog.isGetDepartment = true;
			}
			if (msg.what == MSG_UPLOAD) {
				Toast.makeText(addZljhDialog.getActivity(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				RiverDetailActivity riverDetailActivity = (RiverDetailActivity) addZljhDialog
						.getActivity();
				riverDetailActivity.refreshUi(addZljhDialog.mRiver);
				addZljhDialog.dismiss(true);
			}
			if (addZljhDialog.isGetDepartment && addZljhDialog.isGetProject&&addZljhDialog.hdzljhID!=-1) {
				addZljhDialog.setContent();
			}
		}
	}

	class TextWatcherImpl implements TextWatcher {

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
			if (jhrwEditText.getText().length() > 0
					&& wcjdEditText.getText().length() > 0
					&& fzrEditText.getText().length() > 0) {
				btnSave.setEnabled(true);
			} else {
				btnSave.setEnabled(false);
			}
		}

	}

	/**
	 * 设置界面内容
	 */
	public void setContent() {
		RiverRepairPlan riverRepairPlan = findRiverRepairPlanByID(hdzljhID);
		xmmcSpinner
				.setSelection(findXmmcIndex(riverRepairPlan.getXm().getID()));
		jhrwEditText.setText(riverRepairPlan.getContent());
		wcjdEditText.setText(String.valueOf(riverRepairPlan.getWcjd()));
		zrdwsSpinner.setSelection(findZrdwIndex(riverRepairPlan.getZrdept()
				.getID()));
		fzrEditText.setText(riverRepairPlan.getFzr());
		GregorianCalendar gregorianCalendar = new GregorianCalendar(
				Locale.CHINA);
		gregorianCalendar.setTime(riverRepairPlan.getETime());
		wcsjDatePicker.init(gregorianCalendar.get(GregorianCalendar.YEAR),
				gregorianCalendar.get(GregorianCalendar.MONTH),
				gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH),
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub

					}
				});
	}

	private RiverRepairPlan findRiverRepairPlanByID(int id) {
		for (RiverRepairPlan riverRepairPlan : mRiver.getHdzljls()) {
			if (riverRepairPlan.getID() == id) {
				return riverRepairPlan;
			}
		}
		return null;
	}

	private int findZrdwIndex(int id) {
		int index = 0;
		for (Department department : departments) {
			if (department.getID() == id) {
				return index;
			}
			index++;
		}
		return 0;
	}

	private int findXmmcIndex(int id) {
		int index = 0;
		for (RiverProject riverProject : riverProjects) {
			if (riverProject.getID() == id) {
				return index;
			}
			index++;
		}
		return 0;
	}
}
