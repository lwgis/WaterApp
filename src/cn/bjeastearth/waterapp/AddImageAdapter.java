package cn.bjeastearth.waterapp;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.myview.GridImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AddImageAdapter extends BaseAdapter {
	private ArrayList<ProjectImage> projectImages;
	public ArrayList<ProjectImage> getImages() {
		return projectImages;
	}
	public void setImages(ArrayList<ProjectImage> projectImages) {
		this.projectImages = projectImages;
	}

	private Context mContext;
	public  AddImageAdapter(Context context,ArrayList<ProjectImage> list) {
		this.mContext=context;
		projectImages=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return projectImages.size()+1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position<projectImages.size()) {
			return projectImages.get(position);
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
//		if (convertView==null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.gridview_item, null);
//		}
		GridImageView img =(GridImageView)convertView.findViewById(R.id.gridViewItem);
		if (position<projectImages.size()) {
				// 定义图片视图
			if (projectImages.get(position).getType()==ProjectImage.LOCAL) {
				img.setImageFilePath(projectImages.get(position).getName()); 	
				img.setScaleType(ImageView.ScaleType.CENTER_CROP);
			}
			else {
				ImageLoader.getInstance().displayImage(mContext.getString(R.string.NewTileImgAddr)+projectImages.get(position).getName(), img, ImageOptions.options);
			}

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
