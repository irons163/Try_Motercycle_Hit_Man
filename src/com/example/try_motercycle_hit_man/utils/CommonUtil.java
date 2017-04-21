package com.example.try_motercycle_hit_man.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.Window;

public class CommonUtil {
	public static int screenWidth;
	public static int screenHeight;
	public static int statusBarHeight;
	public static int titleBarHeight;
	public static int gameBoardHeight;
	public static float cat_bg_height;
	
	public static int getStatusBarHeight(Activity activity) { 
	      int result = 0;
	      int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
	      if (resourceId > 0) {
	          result = activity.getResources().getDimensionPixelSize(resourceId);
	      } 
	      return result;
	} 
	
	public static int getTitleBarHeight(Activity activity) {

		 Rect rect = new Rect();
		 activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
	    int viewTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
	    return (viewTop - getStatusBarHeight(activity));
	}
}
