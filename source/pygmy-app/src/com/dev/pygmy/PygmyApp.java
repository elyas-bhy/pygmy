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

package com.dev.pygmy;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.dev.pygmy.util.Persistence;

/**
 * Base class for maintaining global application state. 
 * @author Pygmy
 *
 */
public class PygmyApp extends Application {

	/**
	 * Tag used for logging in the whole app
	 */
	public static final String TAG = "Pygmy";
	/**
	 * Main debug switch, turns on/off debugging for the whole app
	 */
	public static final boolean DEBUG = true;

	public static Persistence persistence;
	private static PygmyApp instance;

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		logD("App starting on " + Build.MODEL + " by " + Build.MANUFACTURER);
		instance = this;
		persistence = new Persistence(this);
	}

	@Override
	public void onTerminate() {
		logD("Pygmy app terminated!");
		super.onTerminate();
	}

	public static PygmyApp getInstance() {
		return instance;
	}

	public static void logD(String message) {
		if (DEBUG) Log.d(TAG, message);
	}
	
	public static void logE(String message) {
		Log.e(TAG, message);
	}
}