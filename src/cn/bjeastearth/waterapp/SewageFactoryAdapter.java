package cn.bjeastearth.waterapp;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bjeastearth.http.ImageOptions;
import cn.bjeastearth.waterapp.model.ProjectImage;
import cn.bjeastearth.waterapp.model.SewageFactory;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SewageFactoryAdapter extends BaseAdapter {

	private List<SewageFactory> mAllSewageFactorys=null;
	private Context mContext;
	public  SewageFactoryAdapter(Context con,List<SewageFactory> factorys) {
		mAllSewageFactorys=factorys;
		mContext=con;
	}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mAllSewageFactorys.size();
		}

		@Override
		public Object getItem(int postion) {
			// TODO Auto-generated method stub
			return mAllSewageFactorys.get(postion);
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
						R.layout.sewagefactory_item, null);
			}
			TextView tvtitle=(TextView)convertView.findViewById(R.id.firstTv);
			tvtitle.setText(mAllSewageFactorys.get(position).getTitle());
			TextView tvDaTe=(TextView)convertView.findViewById(R.id.secondTv);
			tvDaTe.setText(mAllSewageFactorys.get(position).getXzqName());
			ImageView iView=(ImageView)convertView.findViewById(R.id.imageView);
			List<ProjectImage> images=mAllSewageFactorys.get(position).getImages();
			if (images!=null&&images.size()>0) {
				ProjectImage projectImage=mAllSewageFactorys.get(position).getImages().get(0);
				String url=mContext.getString(R.string.NewTileImgAddr)+projectImage.getName();
				ImageLoader.getInstance().displayImage(url, iView,ImageOptions.options);
			}
			convertView.setTag(mAllSewageFactorys.get(position));
			return convertView;
		}

}
