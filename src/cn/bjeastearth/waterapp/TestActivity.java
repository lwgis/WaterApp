package cn.bjeastearth.waterapp;

import cn.bjeastearth.http.ImageOptions;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class TestActivity extends  Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_test);
		ImageLoader imageLoader=ImageLoader.getInstance();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.imageview)
		.showImageForEmptyUri(R.drawable.imageview)
		.showImageOnFail(R.drawable.imageview)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
		ImageView imageView=(ImageView)findViewById(R.id.imageView);
		imageLoader.displayImage("http://159.226.110.64:8001/WaterAdmin/file/img/58073be1-1878-4629-983d-74b8b7dba9fb.png", imageView,ImageOptions.options);
	}
	

}
