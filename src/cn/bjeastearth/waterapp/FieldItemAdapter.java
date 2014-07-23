package cn.bjeastearth.waterapp;

import java.util.List;

import com.esri.core.internal.tasks.ags.r;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.waterapp.model.FieldItem;
import cn.bjeastearth.waterapp.myview.LvHeightUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FieldItemAdapter extends BaseAdapter {
	private Context mContext;
	private List<FieldItem> mFieldItems;
	public FieldItemAdapter(Context con,List<FieldItem> fileFieldItems){
		this.mContext=con;
		this.mFieldItems=fileFieldItems;
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
		if (item.getType()==0) {
			convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fielditem_text, null);
			TextView tvName=(TextView)convertView.findViewById(R.id.fieldNameTv);
			tvName.setText(item.getName());
			TextView tvValue=(TextView)convertView.findViewById(R.id.fieldValueTv);
			tvValue.setText(item.getContent());
		}
		if (item.getType()==1) {
			convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fielditem_image, null);
			ImageView imageView=(ImageView)convertView.findViewById(R.id.fieldContentImage);
			ImageLoader.getInstance().displayImage(mContext.getString(R.string.NewTileImgAddr)+ item.getContent(), imageView,ImageOptions.options);
		}
		if (item.getType()==-1) {
			convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fielditem_listview, null);
			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.fieldlayout);
			for (FieldItem cItem : item.getChildFieldItems()) {
				View cView=null;
				if (cItem.getName().equals("")) {
					cView=LayoutInflater.from(mContext).inflate(R.layout.fielditem_content, null);
				}
				else {
					cView=LayoutInflater.from(mContext).inflate(R.layout.fielditem_text, null);
					TextView tvName=(TextView)cView.findViewById(R.id.fieldNameTv);
					tvName.setText(cItem.getName());
				}
				TextView tvValue=(TextView)cView.findViewById(R.id.fieldValueTv);
				tvValue.setText(cItem.getContent());
				layout.addView(cView);
				}
			TextView tvName=(TextView)convertView.findViewById(R.id.fieldNameTv);
			tvName.setText(item.getName());
		}
		return convertView;
	}

}
