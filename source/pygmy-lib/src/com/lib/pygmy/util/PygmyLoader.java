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