package cn.bjeastearth.imageload;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "WaterApp/files/";
		} else {
			return CommonUtil.getRootFilePath() + "WaterApp/files/";
		}
	}
}
