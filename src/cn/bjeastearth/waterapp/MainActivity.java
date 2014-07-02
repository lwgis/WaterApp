package cn.bjeastearth.waterapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(R.layout.activity_main);
		  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      
			        R.layout.title_main); 
	}

}
