package cn.bjeastearth.waterapp;

import java.util.List;









import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.waterapp.model.WaterNew;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AllNewsAdapter extends BaseAdapter {
	private List<WaterNew> allWaterNews;
	private Context mContext;
	public  AllNewsAdapter(Context con,List<WaterNew> news) {
		allWaterNews=news;
		mContext=con;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allWaterNews.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return allWaterNews.get(position);
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
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.allnews_item, null);
		}
		TextView tvtitle=(TextView)convertView.findViewById(R.id.newsItemTvTitle);
		tvtitle.setText(allWaterNews.get(position).getTitle());
		TextView tvDaTe=(TextView)convertView.findViewById(R.id.newsItemTvDate);
		tvDaTe.setText(allWaterNews.get(position).getNewsTime());
		ImageView iView=(ImageView)convertView.findViewById(R.id.newsItemImageView);
		String url=mContext.getString(R.string.NewTileImgAddr)+allWaterNews.get(position).getThumbnail();
		ImageLoader.getInstance().displayImage(url, iView,ImageOptions.options);
		convertView.setTag(allWaterNews.get(position));
		return convertView;
	}

}
