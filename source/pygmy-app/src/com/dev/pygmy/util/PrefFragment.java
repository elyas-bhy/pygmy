package com.dev.pygmy.util;

import com.dev.pygmy.R;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceFragment;

public class PrefFragment extends PreferenceFragment implements
		SharedPreferences.OnSharedPreferenceChangeListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(PrefFragment.this);
	}	
	

	/*public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {

		return super.onPreferenceTreeClick(preferenceScreen, preference);

	}*/

	@Override
	public void onDestroy() {
		super.onDestroy();
		getPreferenceManager().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(PrefFragment.this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		

	}

}
