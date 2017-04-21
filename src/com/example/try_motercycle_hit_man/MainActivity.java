package com.example.try_motercycle_hit_man;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.Config.DestanceType;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.scene.Scene;
import com.example.try_gameengine.scene.SceneManager;
import com.example.try_gameengine.stage.Stage;
import com.example.try_motercycle_hit_man.utils.BitmapUtil;
import com.example.try_motercycle_hit_man.utils.CommonUtil;

public class MainActivity extends Stage {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		CommonUtil.screenHeight = dm.heightPixels;
		CommonUtil.screenWidth = dm.widthPixels;
		CommonUtil.statusBarHeight = CommonUtil.getStatusBarHeight(this);
		CommonUtil.screenHeight -= CommonUtil.statusBarHeight;
		
		Config.enableFPSInterval = true;
		Config.fps = 40;
		Config.showFPS = true;
		Config.destanceType = DestanceType.ScreenPersent;
		Config.currentScreenWidth = CommonUtil.screenWidth;
		Config.currentScreenHeight = CommonUtil.screenHeight;
		
//		BitmapUtil.initBitmap(this);
		
//		StageManager.init(this);
		
		initStage();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	SceneManager sceneManager;
	
	@Override
	public SceneManager initSceneManager() {
		// TODO Auto-generated method stub
		BitmapUtil.initBitmap(this);
		
		LayerManager.getInstance().setLayerBySenceIndex(0);
		Scene scene = new MyScene(this, "a", 1, Scene.RESUME);
//		LayerManager.setLayerBySenceIndex(1);
//		Scene scene2 = new MyScene2(this, "b", 2, Scene.RESTART);
		
		sceneManager = SceneManager.getInstance();
		sceneManager.addScene(scene);
//		sceneManager.addScene(scene2);
		
		sceneManager.startScene(0);
		
		return sceneManager;
	}
}
