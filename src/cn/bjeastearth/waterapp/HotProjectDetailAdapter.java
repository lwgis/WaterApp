package cn.bjeastearth.waterapp;

import java.util.List;

import cn.bjeastearth.imageload.ImageLoader;
import cn.bjeastearth.waterapp.model.FieldItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HotProjectDetailAdapter extends BaseAdapter {
	private Context mContext;
	private ImageLoader mImageLoader;
	private List<FieldItem> mFieldItems;
	public HotProjectDetailAdapter(Context con,List<FieldItem> fileFieldItems){
		this.mContext=con;
		this.mFieldItems=fileFieldItems;
		mImageLoader=new ImageLoader(con);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFieldItems.size();
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
		FieldItem item=mFieldItems.get(position);
		if (convertView==null) {
			if (item.getType()==0) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fielditem_text, null);
			}
			else {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fielditem_image, null);
			}
		}
	
		if (item.getType()==0) {
			TextView tvName=(TextView)convertView.findViewById(R.id.fieldNameTv);
			tvName.setText(item.getName());
			TextView tvValue=(TextView)convertView.findViewById(R.id.fieldValueTv);
			tvValue.setText(item.getContent());
		}
		else {
			ImageView imageView=(ImageView)convertView.findViewById(R.id.fieldContentImage);
			mImageLoader.DisplayImage(mContext.getString(R.string.NewTileImgAddr)+ item.getContent(), imageView, false);
		}
		return convertView;
	}

}
