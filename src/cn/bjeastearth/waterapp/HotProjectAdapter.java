package cn.bjeastearth.waterapp;

import java.util.List;

import cn.bjeastearth.imageload.ImageLoader;
import cn.bjeastearth.waterapp.model.HotProject;
import cn.bjeastearth.waterapp.model.ProjectImage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HotProjectAdapter extends BaseAdapter {
private List<HotProject> allHotProjects=null;
private Context mContext;
private ImageLoader mImageLoader;
public  HotProjectAdapter(Context con,List<HotProject> hotProjects) {
	allHotProjects=hotProjects;
	mContext=con;
	mImageLoader = new ImageLoader(con);
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allHotProjects.size();
	}

	@Override
	public Object getItem(int postion) {
		// TODO Auto-generated method stub
		return allHotProjects.get(postion);
	}

	@Override
	public long getItemId(int postion) {
		// TODO Auto-generated method stub
		return postion;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.hotproject_item, null);
		}
		TextView tvtitle=(TextView)convertView.findViewById(R.id.hpNameTextView);
		tvtitle.setText(allHotProjects.get(position).getName());
		TextView tvDaTe=(TextView)convertView.findViewById(R.id.hpGcjdTextView);
		tvDaTe.setText("工程进度:"+allHotProjects.get(position).getGcjd()+"%");
		ImageView iView=(ImageView)convertView.findViewById(R.id.hpImageView);
		List<ProjectImage> images=allHotProjects.get(position).getImages();
		if (images != null && images.size() > 0) {
			ProjectImage projectImage = allHotProjects.get(position)
					.getImages().get(0);
			String url = mContext.getString(R.string.NewTileImgAddr)
					+ projectImage.getName();
			mImageLoader.DisplayImage(url, iView, false);
		}
		convertView.setTag(allHotProjects.get(position));
		return convertView;
	}

}
