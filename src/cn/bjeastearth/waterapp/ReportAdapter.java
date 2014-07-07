package cn.bjeastearth.waterapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bjeastearth.waterapp.myview.GridImageView;
import android.R.raw;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ReportAdapter extends BaseAdapter {
	private ArrayList<String> mlistimImages;
	private Context mContext;
	public  ReportAdapter(Context context,ArrayList<String> list) {
		this.mContext=context;
		mlistimImages=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlistimImages.size()+1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position<mlistimImages.size()) {
			return mlistimImages.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.gridview_item, null);
		}
		GridImageView img =(GridImageView)convertView.findViewById(R.id.gridViewItem);
		if (position<mlistimImages.size()) {
				// 定义图片视图
			img.setImageFilePath(mlistimImages.get(position)); 	// 给ImageView设置资源
			img.setScaleType(ImageView.ScaleType.FIT_XY);
		}
		else {
//			LayoutParams lParams=new LayoutParams(DpTransform.px2dip(mContext, 80), DpTransform.px2dip(mContext, 80));
//			img.setLayoutParams(lParams);
			img.setImageResource(R.drawable.gridview_item); 	// 给ImageView设置资源
			img.setScaleType(ImageView.ScaleType.CENTER); // 居中显示

		}
		return convertView;
	}

}
