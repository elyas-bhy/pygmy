package com.dev.pygmy.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class allows to manage the different user parameters for our application.
 * @author Pygmy
 */
public class GamePreferences {
	private final String PREF_GAME_ID = "pref_game_id";
	private final String PREF_GAME_NAME = "pref_game_name";
	private final String PREF_GAME_SUMMARY = "pref_game_summary";
	private final String PREF_GAME_IMAGE = "pref_game_image";
	private final String PREF_GAME_VERSION = "pref_game_version";
	private final String PREF_GAME_FILENAME = "pref_game_filename";
	private final String PREF_MIN_PLAYERS = "pref_min_players";
	private final String PREF_MAX_PLAYERS = "pref_max_players";

	private final int DEFAULT_ID = 0;
	private final int DEFAULT_MIN_PLAYERS = 1;
	private final int DEFAULT_MAX_PLAYERS = 1;
	private final String DEFAULT_NAME = "None";
	private final String DEFAULT_IMAGE =  Utils.BASE_URL + "/gamesImages/Default/logo_home_page.png";
	private final String DEFAULT_VERSION = "0.1";
	private final String DEFAULT_SUMMARY = "None";
	private final String DEFAULT_FILENAME = "game.jar";
	
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	
	public GamePreferences(Context context, String name) {
		prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		editor = prefs.edit();
	}
	
	public String getName() {
		return prefs.getString(PREF_GAME_NAME, DEFAULT_NAME);
	}
	
	public void setName(String name) {
		editor.putString(PREF_GAME_NAME, name);
		editor.commit();
	}
	
	public String getSummary() {
		return prefs.getString(PREF_GAME_SUMMARY, DEFAULT_SUMMARY);
	}
	
	public void setSummary(String summary) {
		editor.putString(PREF_GAME_SUMMARY, summary);
		editor.commit();
	}
	
	public String getImage() {
		return prefs.getString(PREF_GAME_IMAGE, DEFAULT_IMAGE);
	}
	
	public void setImage(String image) {
		editor.putString(PREF_GAME_IMAGE, image);
		editor.commit();
	}

	public int getId() {
		return prefs.getInt(PREF_GAME_ID, DEFAULT_ID);
	}
	
	public void setId(int id) {
		editor.putInt(PREF_GAME_ID, id);
		editor.commit();
	}

	public String getVersion() {
		return prefs.getString(PREF_GAME_VERSION, DEFAULT_VERSION);
	}
	
	public void setVersion(String version) {
		editor.putString(PREF_GAME_VERSION, version);
		editor.commit();
	}
	
	public String getFilename() {
		return prefs.getString(PREF_GAME_FILENAME, DEFAULT_FILENAME);
	}
	
	public void setFilename(String filename) {
		editor.putString(PREF_GAME_FILENAME, filename);
		editor.commit();
	}

	public int getMinPlayers() {
		return prefs.getInt(PREF_MIN_PLAYERS, DEFAULT_MIN_PLAYERS);
	}
	
	public void setMinPlayers(int minPlayers) {
		editor.putInt(PREF_MIN_PLAYERS, minPlayers);
		editor.commit();
	}
	
	public int getMaxPlayers() {
		return prefs.getInt(PREF_MAX_PLAYERS, DEFAULT_MAX_PLAYERS);
	}
	
	public void setMaxPlayers(int maxPlayers) {
		editor.putInt(PREF_MAX_PLAYERS, maxPlayers);
		editor.commit();
	}
	
}