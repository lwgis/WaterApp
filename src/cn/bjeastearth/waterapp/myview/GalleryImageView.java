package cn.bjeastearth.waterapp.myview;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bjeastearth.waterapp.R;
import cn.bjeastearth.waterapp.model.FieldItem;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GalleryImageView extends RelativeLayout {
	public GalleryImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		imageSwitcher=(ImageSwitcher)findViewById(R.id.imageSwitcher);
		gallery=(Gallery)findViewById(R.id.gallery);
		textView=(TextView)findViewById(R.id.textView);
		mContext=context;
		}
	private ArrayList<FieldItem>fieldItems;
	private ImageSwitcher imageSwitcher;
	private TextView textView;
	private Gallery gallery;
	private Context mContext;
	private ImageGalleryAdapter mImageGalleryAdapter;
	
	public ArrayList<FieldItem> getFieldItems() {
		return fieldItems;
	}
	public void setFieldItems(ArrayList<FieldItem> fieldItems) {
		this.fieldItems = fieldItems;		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (mImageGalleryAdapter!=null) {
			mImageGalleryAdapter=new ImageGalleryAdapter();
		}
		gallery.setAdapter(mImageGalleryAdapter);
	}
	private class ImageGalleryAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return fieldItems.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = (ImageView)LayoutInflater.from(mContext).inflate(R.layout.item_gallery_image, parent, false);
			}
			ImageLoader.getInstance().displayImage(mContext.getString(R.string.NewTileImgAddr)+ fieldItems.get(position).getContent(), imageView);
			return imageView;
		}
	}

}
