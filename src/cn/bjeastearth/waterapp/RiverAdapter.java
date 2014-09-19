package cn.bjeastearth.waterapp;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.waterapp.model.River;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RiverAdapter extends BaseAdapter {
	private List<River> mRivers;
	private Context mContext;
	private OnClickListener mlocatiOnClickListener;
	public RiverAdapter(Context context,List<River> rivers,OnClickListener locatiOnClickListener){
		this.mContext=context;
		this.mRivers=rivers;
		this.mlocatiOnClickListener=locatiOnClickListener;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mRivers.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mRivers.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.pollution_item, null);
		}
		TextView tvtitle=(TextView)convertView.findViewById(R.id.firstTv);
		tvtitle.setText(mRivers.get(position).getName());
		TextView tvDaTe=(TextView)convertView.findViewById(R.id.secondTv);
		tvDaTe.setText(mRivers.get(position).getCategory().getName());
		ImageView iView=(ImageView)convertView.findViewById(R.id.imageView);
		Button btnLocation=(Button)convertView.findViewById(R.id.btnLocation);
		btnLocation.setTag(getItem(position));
		btnLocation.setOnClickListener(mlocatiOnClickListener);
		if (mRivers.get(position).getImages()!=null&&mRivers.get(position).getImages().size()>0) {
			String url=mContext.getString(R.string.NewTileImgAddr)+mRivers.get(position).getImages().get(0).getName();
			ImageLoader.getInstance().displayImage(url, iView,ImageOptions.options);
		}
		else {
			iView.setImageResource(R.drawable.imageview);
		}
		return convertView;
	}
  public void refresh(List<River>  rivers){
	  mRivers=rivers;
	  notifyDataSetChanged();
  }
}
