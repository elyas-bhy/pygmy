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

package com.dev.pygmy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsPrefs {
	
	public static final String PREF_SOUND_CHECK = "pref_check_sound";
	public static final String PREF_NOTIF_CHECK = "pref_check_notif";
	public static final String PREF_VIBRATE_CHECK = "pref_check_vibrate";
	//public static final String PREF_SKIN_SELECT = "pref_select_skin";	
	
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;
	
	public SettingsPrefs(Context context) {
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		editor = settings.edit();
	}
	
	public boolean isCheckedSound() {
		return settings.getBoolean(PREF_SOUND_CHECK, true);
	}
	
	public void setCheckedSound(boolean checked) {
		editor.putBoolean(PREF_SOUND_CHECK, checked);
		editor.commit();
	}
	
	public boolean isCheckedNotif() {
		return settings.getBoolean(PREF_NOTIF_CHECK, true);
	}
	
	public void setCheckedNotif(boolean checked) {
		editor.putBoolean(PREF_NOTIF_CHECK, checked);
		editor.commit();
	}
		
	public boolean isCheckedVibrate() {
		return settings.getBoolean(PREF_VIBRATE_CHECK, true);
	}
	
	public void setCheckedVibrate(boolean checked) {
		editor.putBoolean(PREF_VIBRATE_CHECK, checked);
		editor.commit();
	}

}