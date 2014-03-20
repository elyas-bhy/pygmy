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

public class Persistence {
	
	private final String PREF_SOUND_CHECK = "pref_check_sound";
	private final String PREF_NOTIF_CHECK = "pref_check_notif";
	private final String PREF_VIBRATE_CHECK = "pref_check_vibrate";
	//private final String PREF_SKIN_SELECT = "pref_select_skin";
	
	private GamePreferences previousGame;
	private GamePreferences lastGame;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	
	public Persistence(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		editor = prefs.edit();
		previousGame = new GamePreferences(context, "PREVIOUS_GAME");
		lastGame = new GamePreferences(context, "LAST_GAME");
	}
	
	public boolean isCheckedSound() {
		return prefs.getBoolean(PREF_SOUND_CHECK, true);
	}
	
	public void setCheckedSound(boolean checked) {
		editor.putBoolean(PREF_SOUND_CHECK, checked);
		editor.commit();
	}
	
	public boolean isCheckedNotif() {
		return prefs.getBoolean(PREF_NOTIF_CHECK, true);
	}
	
	public void setCheckedNotif(boolean checked) {
		editor.putBoolean(PREF_NOTIF_CHECK, checked);
		editor.commit();
	}
		
	public boolean isCheckedVibrate() {
		return prefs.getBoolean(PREF_VIBRATE_CHECK, true);
	}
	
	public void setCheckedVibrate(boolean checked) {
		editor.putBoolean(PREF_VIBRATE_CHECK, checked);
		editor.commit();
	}
	
	public GamePreferences getPreviousGame() {
		return previousGame;
	}
	
	public GamePreferences getLastGame() {
		return lastGame;
	}

	public void copyToLastGame(GamePreferences game) {
		lastGame.setId(game.getId());
		lastGame.setName(game.getName());
		lastGame.setImage(game.getImage());
		lastGame.setVersion(game.getVersion());
		lastGame.setFilename(game.getFilename());
		lastGame.setMinPlayers(game.getMinPlayers());
		lastGame.setMaxPlayers(game.getMaxPlayers());
	}

}