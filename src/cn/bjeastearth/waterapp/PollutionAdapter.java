package cn.bjeastearth.waterapp;

import java.util.List;

import cn.bjeastearth.imageload.ImageLoader;
import cn.bjeastearth.waterapp.model.PsIndustry;
import cn.bjeastearth.waterapp.model.ProjectImage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PollutionAdapter extends BaseAdapter {
	List<PsIndustry> mAllPollutionSources;
	Context mContext;
	ImageLoader mImageLoader;
	public  PollutionAdapter(Context con,List<PsIndustry> sources) {
		mContext=con;
		mAllPollutionSources=sources;
		mImageLoader=new ImageLoader(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mAllPollutionSources!=null&&mAllPollutionSources.size()>0) {
			return  mAllPollutionSources.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAllPollutionSources.get(position);
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
			convertView=LayoutInflater.from(mContext).inflate(R.layout.pollution_item, null);
		}
		TextView tvtitle=(TextView)convertView.findViewById(R.id.firstTv);
		tvtitle.setText(mAllPollutionSources.get(position).getQymc());
		TextView tvDaTe=(TextView)convertView.findViewById(R.id.secondTv);
		tvDaTe.setText("产业类型:"+mAllPollutionSources.get(position).getCylx());
		ImageView iView=(ImageView)convertView.findViewById(R.id.imageView);
		List<ProjectImage> images=mAllPollutionSources.get(position).getImages();
		if (images!=null&&images.size()>0) {
			ProjectImage projectImage=mAllPollutionSources.get(position).getImages().get(0);
			String url=mContext.getString(R.string.NewTileImgAddr)+projectImage.getName();
			mImageLoader.DisplayImage(url, iView, false);
		}
		return convertView;
	}

}
