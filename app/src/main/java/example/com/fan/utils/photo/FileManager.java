package example.com.fan.utils.photo;


public class FileManager {

	public static onConfigSaveFilePath callBack;
	
	public static String FLODER_NAME_PATH = "fan/image/";
	
	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			String root = CommonUtil.getRootFilePath();
			if(callBack != null) {
				return callBack.hasSDCard(root);
			}
//			return root + "temp/image/";
			return root + FLODER_NAME_PATH;
		} else {
			String root = CommonUtil.getRootFilePath();
			if(callBack != null) {
				return callBack.notSDCard(root);
			}
//			return root + "temp/image/";
			return root + FLODER_NAME_PATH;
		}
	}
	
	public static interface onConfigSaveFilePath {
		public String hasSDCard(String root);
		public String notSDCard(String root);
	}
}
