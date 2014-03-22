/*
 * Copyright (C) 2014 Pygmy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lib.pygmy.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.lib.pygmy.PygmyGame;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

public class PygmyLoader {
	
	private static final String TAG = "PygmyLoader";
	
	private static Activity mContext;
	private static String mGamePath;
	private static DexClassLoader mClassLoader;

	public static void setGamePath(Activity context, String path) {
		mContext = context;
		mGamePath = path;
		
		// Drop reference to previous class loader
		mClassLoader = null;
		
		// Delete optimized dex file of previous class loader
		File dex = new File(context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath() + "/game.dex");
		if (dex.exists()) {
			dex.delete();
		}
	}

	public static PygmyGame loadGame() {
		PygmyGame game = null;
		
		try {
			mClassLoader = getClassLoader();
			loadDex();
			Class<?> clazz = mClassLoader.loadClass("com.client.pygmy.PygmyGameImpl");
			Constructor<?> constructor = clazz.getConstructor();
			game = (PygmyGame) constructor.newInstance();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return game;
	}
	
	private static void loadDex() {
		try {
			DexFile dx = DexFile.loadDex(mGamePath, File.createTempFile("opt", "dex",
					mContext.getCacheDir()).getPath(), 0);
			for (Enumeration<String> classNames = dx.entries(); classNames.hasMoreElements();) {
				mClassLoader.loadClass(classNames.nextElement());
			}
		} catch (IOException e) {
			Log.w(TAG, "Error opening " + mGamePath, e);
		} catch (ClassNotFoundException e) {
			Log.w(TAG, "Error while loading: " + mGamePath, e);
		}
	}
	
	public static DexClassLoader getClassLoader() {
		if (mClassLoader != null) {
			return mClassLoader;
		}
		
		mClassLoader = new DexClassLoader(mGamePath, 
				mContext.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), 
				null, 
				mContext.getClassLoader());
		return mClassLoader;
	}

}