package cn.bjeastearth.waterapp;

import java.util.ArrayList;
import java.util.List;

import com.esri.core.internal.tasks.ags.r;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.waterapp.model.FieldItem;
import cn.bjeastearth.waterapp.model.FieldItemType;
import cn.bjeastearth.waterapp.myview.LvHeightUtil;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
		if (item.getType()==FieldItemType.TOWTEXT) {
			convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fielditem_text, null);
			TextView tvName=(TextView)convertView.findViewById(R.id.fieldNameTv);
			tvName.setText(item.getName());
			TextView tvValue=(TextView)convertView.findViewById(R.id.fieldValueTv);
			tvValue.setText(item.getContent());
		}
		if (item.getType()==FieldItemType.IMAGE) {
			convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fielditem_image, null);
			ImageView imageView=(ImageView)convertView.findViewById(R.id.fieldContentImage);
			ImageLoader.getInstance().displayImage(mContext.getString(R.string.NewTileImgAddr)+ item.getContent(), imageView,ImageOptions.options);
		}
		if (item.getType()==FieldItemType.PARENT) {
			convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fielditem_listview, null);
			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.fieldlayout);
			for (FieldItem cItem : item.getChildFieldItems()) {
				View cView=null;
				if (cItem.getType()!=FieldItemType.TEXTBUTTON) {
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
				}
				else {
					cView=LayoutInflater.from(mContext).inflate(R.layout.fielditem_button, null);
					Button btnShowDetail=(Button)cView.findViewById(R.id.btnShowDetail);
					btnShowDetail.setText(cItem.getName());
					btnShowDetail.setTag(cItem.getTarge());
					btnShowDetail.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(mContext,
									FieldItemActivity.class);
							intent.putExtra("Title", "污染源信息");
							intent.putExtra("FieldItems", (ArrayList<FieldItem>)v.getTag());
							mContext.startActivity(intent);
						}
					});
				}
				layout.addView(cView);

				}
			TextView tvName=(TextView)convertView.findViewById(R.id.fieldNameTv);
			tvName.setText(item.getName());
		}
		return convertView;
	}

}
