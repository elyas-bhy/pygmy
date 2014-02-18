package com.dev.pygmy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

public class SettingsPrefs {
	
	public static final String PREF_SOUND_CHECK = "pref_check_sound";
	public static final String PREF_NOTIF_CHECK = "pref_check_notif";
	public static final String PREF_VIBRATE_CHECK = "pref_check_vibrate";
	//public static final String PREF_SKIN_SELECT = "pref_select_skin";	
	
	// Connectivity issue
	private ConnectivityManager con_manager;
	
	private SharedPreferences settings;
	private SharedPreferences.Editor settings_editor;
	
	public SettingsPrefs(Context context){
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings_editor = settings.edit();
		
		// Connectivity issue
		con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
	}
	
	public boolean isCheckedSound(){
		return settings.getBoolean(PREF_SOUND_CHECK, true);
	}
	
	public void setCheckedSound(boolean checked){
		settings_editor.putBoolean(PREF_SOUND_CHECK, checked);
		settings_editor.commit();
	}
	
	public boolean isCheckedNotif(){
		return settings.getBoolean(PREF_NOTIF_CHECK, true);
	}
	
	public void setCheckedNotif(boolean checked){
		settings_editor.putBoolean(PREF_NOTIF_CHECK, checked);
		settings_editor.commit();
	}
		
	public boolean isCheckedVibrate(){
		return settings.getBoolean(PREF_VIBRATE_CHECK, true);
	}
	
	public void setCheckedVibrate(boolean checked){
		settings_editor.putBoolean(PREF_VIBRATE_CHECK, checked);
		settings_editor.commit();
	}

}
