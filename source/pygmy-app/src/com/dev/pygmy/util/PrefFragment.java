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
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		
	}

}
