package cn.bjeastearth.waterapp.myview;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import cn.bjeastearth.waterapp.R;
import cn.bjeastearth.waterapp.model.River;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SelectZljhDialog extends DialogFragment {
	private ListView mListView;
	private River mRiver;

	public SelectZljhDialog(River river) {
		mRiver = river;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setStyle(STYLE_NO_TITLE, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.dialog_select_zljh, container);
		mListView = (ListView) view.findViewById(R.id.listView);
		mListView.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView==null) {
					convertView=SelectZljhDialog.this.getActivity().getLayoutInflater().inflate(R.layout.fielditem_button, null);
				}
				Button btnShowDetail=(Button)convertView.findViewById(R.id.btnShowDetail);
				DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				btnShowDetail.setText(dateFormat.format(mRiver.getHdzljls().get(position).getETime())+"|"+mRiver.getHdzljls().get(position).getXm().getName());
				btnShowDetail.setTag(mRiver.getHdzljls().get(position).getID());
				btnShowDetail.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						FragmentTransaction ft=getFragmentManager().beginTransaction();
						AddZljhDialog addZljhDialog = new AddZljhDialog(mRiver,Integer.parseInt(v.getTag().toString()));
						ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
						addZljhDialog.show(ft, "addzljh");
						dismiss();
					}
				});
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				if (mRiver.getHdzljls() != null) {
					return mRiver.getHdzljls().size();
				}
				return 0;
			}
		});
		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		return dialog;
	}
}
