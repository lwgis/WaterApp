package cn.bjeastearth.waterapp.myview;

import cn.bjeastearth.waterapp.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class WebListView extends ListView {
	public WebListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
		// TODO Auto-generated constructor stub
	
	public void showLoading() {
		WebAdapter adapter=new WebAdapter();
		this.setAdapter(adapter);
	}
class WebAdapter extends BaseAdapter{

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
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
		// TODO Auto-generated method stub
		if (convertView==null) {	
			convertView=LayoutInflater.from(WebListView.this.getContext()).inflate(R.layout.web_listview, null);
			convertView.setLayoutParams(new LayoutParams(WebListView.this.getWidth(), WebListView.this.getHeight()));
		}
		return convertView;
	}
	
}
}
