package cn.bjeastearth.waterapp;

import java.util.List;

import cn.bjeastearth.waterapp.model.RootProject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RootProjectAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<RootProject> mAllRootProjects;
	public RootProjectAdapter(Context con,List<RootProject> allpRootProjects){
		mContext=con;
		mAllRootProjects=allpRootProjects;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAllRootProjects.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAllRootProjects.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.rootproject_item, null);
		}
		TextView tvName=(TextView)convertView.findViewById(R.id.rootProjectNameTv);
		tvName.setText(mAllRootProjects.get(position).getTitle());
		ProgressBar progressBar=(ProgressBar)convertView.findViewById(R.id.rootProjectPressBar);
		progressBar.setMax(100);
		progressBar.setProgress(mAllRootProjects.get(position).getWcjd());
		return convertView;
	}

}
