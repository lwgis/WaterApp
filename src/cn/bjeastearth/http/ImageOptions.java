package cn.bjeastearth.http;

import cn.bjeastearth.waterapp.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageOptions {
	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.imageview)
	.showImageForEmptyUri(R.drawable.imageview)
	.showImageOnFail(R.drawable.imageview)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.considerExifParams(true)
//	.displayer(new RoundedBitmapDisplayer(20))
	.build();
}
