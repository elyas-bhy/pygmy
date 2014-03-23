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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.pygmy.game.GameHomePageActivity;
import com.dev.pygmy.navbar.NavbarAdapter;
import com.dev.pygmy.navbar.NavbarEntryItem;
import com.dev.pygmy.navbar.NavbarItem;
import com.dev.pygmy.util.GamePreferences;
import com.dev.pygmy.util.ImageDownloader;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayerListener;
import com.google.android.gms.plus.model.people.Person;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.actionbar.ActionBarSlideIcon;

/**
 * INSTRUCTIONS: To run this app, please set up a project in the Developer
 * Console. Then, place your app ID on res/values/ids.xml. Also, change the
 * package name to the package name you used to create the client ID in
 * Developer Console. Make sure you sign the APK with the certificate whose
 * fingerprint you entered in Developer Console when creating your Client Id.
 * 
 * @author Pygmy
 */
public class MainActivity extends BaseGameActivity implements
		TurnBasedMultiplayerListener, PygmyTurnListener {

	// For our intents
	public static final int RC_SELECT_GAME = 10000;
	public static final int RC_SELECT_PLAYERS = 10001;
	public static final int RC_LOOK_AT_MATCHES = 10002;
	public static final String EXTRA_GAME_ID = "com.dev.pygmy.EXTRA_GAME_ID";
	public static final String EXTRA_GAME_VERSION = "com.dev.pygmy.EXTRA_GAME_VERSION";
	
	// How long to show toasts
	private final static int TOAST_DELAY = 2000;

	// Selected game's infos
	private String gameId;
	private String gameVersion;
	private boolean shouldStartMatch;
	
	private SlidingMenu mSlidingMenu;
	private GameHelper mGameHelper;
	private GamePreferences mGamePrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGameHelper = new GameHelper(this);

		// Start animation
		TranslateAnimation animation = new TranslateAnimation(500, 0, 0, 0);
		animation.setDuration(1400);
		animation.setFillAfter(false);

		ImageView imageview = (ImageView) findViewById(R.id.logo_image);
		imageview.startAnimation(animation);

		// Load preferences
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		initSigninButtons();
		initSlidingMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.default_actionbar, menu);
		menu.findItem(R.id.set_report).setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.set_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case android.R.id.home:
			mSlidingMenu.toggle();
			break;
		default:
			break;
		}
		return false;
	}

	private void initSlidingMenu() {
		mSlidingMenu = new SlidingMenu(this);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.setSlidingEnabled(false);
		mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		mSlidingMenu.setActionBarSlideIcon(new ActionBarSlideIcon(this,
				R.drawable.ic_navigation_drawer, R.string.app_name,
				R.string.app_name));

		List<NavbarItem> entries = new ArrayList<NavbarItem>();
		entries.add(new NavbarEntryItem(R.drawable.ic_action_home, R.string.home));
		entries.add(new NavbarEntryItem(R.drawable.ic_action_icon_games, R.string.games));
		entries.add(new NavbarEntryItem(R.drawable.ic_action_sword, R.string.check_games));
		entries.add(new NavbarEntryItem(R.drawable.ic_action_signout, R.string.sign_out));
		NavbarAdapter adapter = new NavbarAdapter(this, entries);

		// Assign adapter to slidemenu list view
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View slideMenu = inflater.inflate(R.layout.slidemenu, null);
		ListView menuView = (ListView) slideMenu.findViewById(R.id.slidemenu);
		menuView.setAdapter(adapter);
		menuView.setItemChecked(0, true);
		menuView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSlidingMenu.showContent();
				if (position == 0) {
					setProfileView();
				}
				if (position == 1) {
					Intent intent = new Intent(MainActivity.this, GameListActivity.class);
					startActivityForResult(intent, RC_SELECT_GAME);
				}
				if (position == 2) {
					findViewById(R.id.screen_profile).setVisibility(View.GONE);
					onCheckGamesClicked();
				}
				if (position == 3) {
					signOut();
					setViewVisibility();
				}
			}
		});
		mSlidingMenu.setMenu(slideMenu);
	}

	private void initSigninButtons() {
		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Start the asynchronous sign in flow
						beginUserInitiatedSignIn();
						findViewById(R.id.sign_in_button).setVisibility(View.GONE);
						findViewById(R.id.offline_button).setVisibility(View.GONE);
						findViewById(R.id.welcome).setVisibility(View.GONE);
					}
				});
	}

	// Displays your inbox. You will get back onActivityResult where
	// you will need to figure out what you clicked on.
	private void onCheckGamesClicked() {
		Intent intent = getGamesClient().getMatchInboxIntent();
		startActivityForResult(intent, RC_LOOK_AT_MATCHES);
		findViewById(R.id.screen_profile).setVisibility(View.VISIBLE);
		findViewById(R.id.matchup_layout).setVisibility(View.GONE);
	}

	// Open the create-game UI. You will get back an onActivityResult
	// and figure out what to do.
	private void onStartMatchClicked() {
		Intent intent = getGamesClient().getSelectPlayersIntent(1, 2, true);
		startActivityForResult(intent, RC_SELECT_PLAYERS);
		shouldStartMatch = false;
	}

	// Create a one-on-one automatch game.
	public void onQuickMatchClicked(View view) {
		Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(1, 1, 0);

		TurnBasedMatchConfig tbmc = TurnBasedMatchConfig.builder()
				.setAutoMatchCriteria(autoMatchCriteria).build();

		// Kick the match off
		showSpinner();
		getGamesClient().createTurnBasedMatch(this, tbmc);
	}

	// In-game controls

	// Cancel the game. Should possibly wait until the game is canceled before
	// giving up on the view.
	public void onCancelClicked(View view) {
		showSpinner();
		mGameHelper.onCancelClicked();
		setViewVisibility();
	}

	// Leave the game during your turn. Note that there is a separate
	// GamesClient.leaveTurnBasedMatch() if you want to leave NOT on your turn.
	public void onLeaveClicked(View view) {
		showSpinner();
		mGameHelper.onLeaveClicked();
		setViewVisibility();
	}

	// Finish the game. Sometimes, this is your only choice.
	public void onFinishClicked(View view) {
		showSpinner();
		mGameHelper.onFinishClicked();
		setViewVisibility();
	}

	// Update the visibility based on what state we're in.
	public void setViewVisibility() {
		FrameLayout gameplayLayout = ((FrameLayout) findViewById(R.id.gameplay_layout));
		if (!isSignedIn()) {
			findViewById(R.id.screen_profile).setVisibility(View.GONE);
			findViewById(R.id.login_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
			findViewById(R.id.offline_button).setVisibility(View.VISIBLE);
			findViewById(R.id.welcome).setVisibility(View.VISIBLE);
			findViewById(R.id.matchup_layout).setVisibility(View.GONE);
			gameplayLayout.setVisibility(View.GONE);

			mGameHelper.dismissDialog();
			return;
		}
		
		findViewById(R.id.login_layout).setVisibility(View.GONE);

		if (mGameHelper.isDoingTurn()) {
			findViewById(R.id.matchup_layout).setVisibility(View.GONE);
			findViewById(R.id.screen_profile).setVisibility(View.GONE);
			gameplayLayout.setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.screen_profile).setVisibility(View.VISIBLE);
			findViewById(R.id.matchup_layout).setVisibility(View.GONE);
			gameplayLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void onSignInFailed() {
		setViewVisibility();
	}

	@Override
	public void onSignInSucceeded() {
		
		if (mHelper.getTurnBasedMatch() != null) {
			// GameHelper will cache any connection hint it gets. In this case,
			// it can cache a TurnBasedMatch that it got from choosing a
			// turn-based game notification.
			// If that's the case, you should go straight into the game.
			updateMatch(mHelper.getTurnBasedMatch());
			return;
		}

		setViewVisibility();

		// As a demonstration, we are registering this activity as a handler for
		// invitation and match events.

		// This is *NOT* required; if you do not register a handler for
		// invitation events, you will get standard notifications instead.
		// Standard notifications may be preferable behavior in many cases.
		getGamesClient().registerInvitationListener(this);

		// Likewise, we are registering the optional MatchUpdateListener, which
		// will replace notifications you would get otherwise. You do *NOT* have
		// to register a MatchUpdateListener.
		getGamesClient().registerMatchUpdateListener(this);

		setProfileView();

		if (shouldStartMatch) {
			dismissSpinner();
			onStartMatchClicked();
		}
	}

	// Switches to profile view
	private void setProfileView() {
		if (!isSignedIn()) {
			setViewVisibility();
		} else {
			URL imageUrl = null;
			URL iconUrl = null;
			URL iconUrl2 = null;
			Person p = getPlusClient().getCurrentPerson();

			// To display
			String name = p.getDisplayName();
			String nationality = p.getLanguage().toUpperCase();
			
			String previousGame = PygmyApp.persistence.getPreviousGame().getName();
			String previousImage = PygmyApp.persistence.getPreviousGame().getImage();
			String lastGame = PygmyApp.persistence.getLastGame().getName();
			String lastImage = PygmyApp.persistence.getLastGame().getImage();
			
			// Load image URLs
			try {
				imageUrl = new URL(p.getImage().getUrl());
				iconUrl = new URL(previousImage);
				iconUrl2 = new URL(lastImage);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			// Set texts and images in views
			((TextView) findViewById(R.id.last_played)).setText(previousGame);
			((TextView) findViewById(R.id.last_played2)).setText(lastGame);
			
			((TextView) findViewById(R.id.name_profile)).setText(name);
			((TextView) findViewById(R.id.nat_profile)).setText(nationality);
			ImageView picture = (ImageView) findViewById(R.id.image_profile);
			ImageView gameIcon = (ImageView) findViewById(R.id.game_icon_profile_last);
			ImageView gameIcon2 = (ImageView) findViewById(R.id.game_icon_profile_last2);
			
			// Retrieve images for the last played games
			final ImageDownloader downloader = new ImageDownloader();
			downloader.download(imageUrl.toString(), picture);
			downloader.download(iconUrl.toString(), gameIcon);
			downloader.download(iconUrl2.toString(), gameIcon2);
			findViewById(R.id.screen_profile).setVisibility(View.VISIBLE);
		}
	}
	
	public void onPreviouslyPlayedGameClick(View v) {
		mGamePrefs = PygmyApp.persistence.getPreviousGame();
		putGameInfos();
	}
	
	public void onLastPlayedGameClick(View v) {
		mGamePrefs = PygmyApp.persistence.getLastGame();
		putGameInfos();
	}

	// Helper dialogs
	private void showSpinner() {
		findViewById(R.id.progressLayout).setVisibility(View.VISIBLE);
	}

	private void dismissSpinner() {
		findViewById(R.id.progressLayout).setVisibility(View.GONE);
	}

	// This method is what gets called when you return from either the Play
	// Games built-in inbox, or else the create game built-in interface.
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		// It's VERY IMPORTANT for you to remember to call your superclass.
		// BaseGameActivity will not work otherwise.
		super.onActivityResult(request, response, data);
		switch (request) {

		case RC_SELECT_GAME:
			if (data != null) {
				if (data.hasExtra(EXTRA_GAME_ID))
					gameId = data.getStringExtra(EXTRA_GAME_ID);
				if (data.hasExtra(EXTRA_GAME_VERSION))
					gameVersion = data.getStringExtra(EXTRA_GAME_VERSION);
				showSpinner();
				shouldStartMatch = true;
			} else {
				shouldStartMatch = false;
			}
			break;

		case RC_LOOK_AT_MATCHES:  // Returning from the 'Select Match' dialog
			if (response != Activity.RESULT_OK) {
				// User canceled
				return;
			}

			TurnBasedMatch match = data
					.getParcelableExtra(GamesClient.EXTRA_TURN_BASED_MATCH);

			if (match != null) {
				updateMatch(match);
			}
			break;

		case RC_SELECT_PLAYERS:  // Returned from 'Select players to Invite' dialog
			if (response != Activity.RESULT_OK) {
				// User canceled
				shouldStartMatch = false;
				return;
			}

			// Get the invitee list
			final ArrayList<String> invitees = data
					.getStringArrayListExtra(GamesClient.EXTRA_PLAYERS);

			// Get automatch criteria
			Bundle autoMatchCriteria = null;

			int minAutoMatchPlayers = data.getIntExtra(
					GamesClient.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
			int maxAutoMatchPlayers = data.getIntExtra(
					GamesClient.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

			if (minAutoMatchPlayers > 0) {
				autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
						minAutoMatchPlayers, maxAutoMatchPlayers, 0);
			} else {
				autoMatchCriteria = null;
			}

			TurnBasedMatchConfig tbmc = TurnBasedMatchConfig.builder()
					.addInvitedPlayers(invitees)
					.setAutoMatchCriteria(autoMatchCriteria).build();

			// Kick the match off
			getGamesClient().createTurnBasedMatch(this, tbmc);
			showSpinner();
			break;

		default:
			break;
		}
	}

	// startMatch() happens in response to the createTurnBasedMatch()
	// above. This is only called on success, so we should have a
	// valid match object. We're taking this opportunity to setup the
	// game, saving our initial state. Calling takeTurn() will
	// callback to OnTurnBasedMatchUpdated(), which will show the game
	// UI.
	public void startMatch(TurnBasedMatch match) {
		showSpinner();
		mGameHelper.startMatch(match, gameId, gameVersion);
	}

	// If you choose to rematch, then call it and wait for a response.
	public void rematch() {
		showSpinner();
		mGameHelper.rematch();
	}

	// This is the main function that gets called when players choose a match
	// from the inbox, or else create a match and want to start it.
	public void updateMatch(TurnBasedMatch match) {
		mGameHelper.updateMatch(match);
	}

	@Override
	public void onTurnBasedMatchCanceled(int statusCode, String matchId) {
		dismissSpinner();
		mGameHelper.onTurnBasedMatchCanceled(statusCode, matchId);
	}

	@Override
	public void onTurnBasedMatchInitiated(int statusCode, TurnBasedMatch match) {
		dismissSpinner();
		mGameHelper.onTurnBasedMatchInitiated(statusCode, match);
	}

	@Override
	public void onTurnBasedMatchLeft(int statusCode, TurnBasedMatch match) {
		dismissSpinner();
		mGameHelper.onTurnBasedMatchLeft(statusCode, match);
	}

	@Override
	public void onTurnBasedMatchUpdated(int statusCode, TurnBasedMatch match) {
		dismissSpinner();
		mGameHelper.onTurnBasedMatchUpdated(statusCode, match);
		setViewVisibility();
	}

	// Handle notification events.
	@Override
	public void onInvitationReceived(Invitation invitation) {
		Toast.makeText(
				this,
				"An invitation has arrived from "
						+ invitation.getInviter().getDisplayName(), TOAST_DELAY)
				.show();
	}

	@Override
	public void onInvitationRemoved(String invitationId) {
		Toast.makeText(this, "An invitation was removed.", TOAST_DELAY).show();
	}

	@Override
	public void onTurnBasedMatchesLoaded(int statusCode, LoadMatchesResponse response) {
		// Not used.
	}

	@Override
	public void onTurnBasedMatchReceived(TurnBasedMatch match) {
		Toast.makeText(this, "A match was updated.", TOAST_DELAY).show();
	}

	@Override
	public void onTurnBasedMatchRemoved(String matchId) {
		Toast.makeText(this, "A match was removed.", TOAST_DELAY).show();
	}

	public GamesClient getGamesClient() {
		return super.getGamesClient();
	}

	@Override
	public void onTurnTaken() {
		showSpinner();
		mGameHelper.onTurnTaken();
	}

	// Pass game infos to the new Activity
	public void putGameInfos() {
		Intent intent = new Intent(MainActivity.this, GameHomePageActivity.class);

		intent.putExtra("id", mGamePrefs.getId());
		intent.putExtra("gameName", mGamePrefs.getName());
		intent.putExtra("summary", mGamePrefs.getSummary());
		intent.putExtra("filename", mGamePrefs.getFilename());
		intent.putExtra("version", mGamePrefs.getVersion());
		intent.putExtra("image", mGamePrefs.getImage());
		intent.putExtra("minPlayer", mGamePrefs.getMinPlayers());
		intent.putExtra("maxPlayer", mGamePrefs.getMaxPlayers());

		startActivityForResult(intent, MainActivity.RC_SELECT_GAME);
	}
	
}