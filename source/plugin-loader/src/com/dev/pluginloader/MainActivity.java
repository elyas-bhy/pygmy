package com.dev.pluginloader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.lib.pygmy.PygmyGame;

import dalvik.system.DexClassLoader;

public class MainActivity extends Activity {

	private final String TAG = "PL";
	private String jar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		jar = getExternalFilesDir(null).getAbsolutePath() + "/plugin-dexed.jar";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		DexClassLoader classLoader = new DexClassLoader(jar, 
				getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), null, getClassLoader());
		try {
			Class<?> clazz = classLoader.loadClass("com.client.pygmy.PygmyGameImpl");
			PygmyGame obj = (PygmyGame) clazz.newInstance();
			Log.d(TAG, obj.getTitle());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

}
