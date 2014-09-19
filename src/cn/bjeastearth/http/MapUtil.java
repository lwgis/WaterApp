package cn.bjeastearth.http;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;

public class MapUtil {
	public static void addMapLayerByUrl(MapView view, String url) {
		ArcGISDynamicMapServiceLayer dynamicMapServiceLayer = new ArcGISDynamicMapServiceLayer(
				url);
		view.addLayer(dynamicMapServiceLayer);
	}
}
