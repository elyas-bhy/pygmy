package com.lib.pygmy.util;

import java.lang.reflect.Constructor;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.lib.pygmy.PygmyGame;

import dalvik.system.DexClassLoader;

public class PygmyLoader {
	
	private static final String TAG = "PygmyLoader";
	private static Activity mContext;
	private static String mGamePath;

	public static PygmyGame loadGame(Activity context, String gamePath) {
		mContext = context;
		mGamePath = gamePath;
		PygmyGame game = null;
		
		try {
			DexClassLoader classLoader = getClassLoader();
			Class<?> clazz = classLoader.loadClass("com.client.pygmy.PygmyGameImpl");
			Constructor<?> constructor = clazz.getConstructor();
			game = (PygmyGame) constructor.newInstance();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return game;
	}
	
	public static DexClassLoader getClassLoader() {
		DexClassLoader classLoader = new DexClassLoader(mGamePath, 
				mContext.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), 
				null, 
				mContext.getClassLoader());
		return classLoader;
	}

}