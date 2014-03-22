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

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.dev.pygmy.game.DownloadTask;
import com.dev.pygmy.game.GameViewManager;
import com.dev.pygmy.util.Utils;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.util.PygmyLoader;
import com.lib.pygmy.util.TurnData;

/**
 * Handles match state management by implementing callbacks and using
 * the Google Play Services game client API
 * @author Pygmy
 *
 */
public class GameHelper {

	/**
	 * Should I be showing the turn API?
	 */
	public boolean isDoingTurn = false;

	/**
	 * This is the current match we're in; null if not loaded
	 */
	private TurnBasedMatch mMatch;
	
	/**
	 * This is the current match data after being unpersisted.
	 * Do not retain references to match data once you have
	 * taken an action on the match, such as takeTurn()
	 */
	private TurnData mTurnData;

	/**
	 * Reference to the current game
	 */
	private PygmyGame mGame;
	
	/**
	 * Manages all of the game's views
	 */
	private GameViewManager mGameViewManager;
	
	/**
	 * Current player's participant ID
	 */
	private String mCurrentPlayerId;

	private MainActivity mContext;
	private AlertDialog mAlertDialog;

	public GameHelper(MainActivity context) {
		mContext = context;
	}

	/**
	 * Switches to gameplay view
	 */
	public void setGameplayUI() {
		File versionFolder = new File(Utils.getGamePath(mContext,
				mTurnData.game, mTurnData.version));

		if (!versionFolder.exists()) {
			showDownloadPrompt();
		} else {
			initGameViewManager();
			isDoingTurn = true;
			mContext.setViewVisibility();
			mGameViewManager.updateData(mTurnData);
			Toast.makeText(mContext, "Player's " + mCurrentPlayerId + " turn", 
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Initializes the game and its view manager, either by dynamically loading it,
	 * or by deserializing it if the match was already persisted
	 */
	private void initGameViewManager() {
		String path = Utils.getGamePath(mContext, mTurnData.game, mTurnData.version, "game.jar");
		PygmyLoader.setGamePath(mContext, path);
		mGame = Utils.loadGameHistory(getHistoryPath());
		
		if (mGame == null) {
			mGame = PygmyLoader.loadGame();
			mGame.setPlayerIds(mMatch.getParticipantIds());
		}
		
		mCurrentPlayerId = mMatch.getParticipantId(mContext.getGamesClient().getCurrentPlayerId());
		mGame.getContext().setCurrentPlayerId(mCurrentPlayerId);
		mGameViewManager = new GameViewManager(mContext, mGame);
	}

	/**
	 * Get the next participant. In this function, we assume that we are
	 * round-robin, with all known players going before all automatch players.
	 * This is not a requirement; players can go in any order. However, you can
	 * take turns in any order.
	 * 
	 * @return participantId of next player, or null if automatching
	 */
	private String getNextParticipantId() {

		String myParticipantId = mMatch.getParticipantId(mContext
				.getGamesClient().getCurrentPlayerId());

		ArrayList<String> participantIds = mMatch.getParticipantIds();

		int desiredIndex = -1;

		for (int i = 0; i < participantIds.size(); i++) {
			if (participantIds.get(i).equals(myParticipantId)) {
				desiredIndex = i + 1;
			}
		}

		if (desiredIndex < participantIds.size()) {
			return participantIds.get(desiredIndex);
		}

		if (mMatch.getAvailableAutoMatchSlots() <= 0) {
			// You've run out of automatch slots, so we start over.
			return participantIds.get(0);
		} else {
			// You have not yet fully automatched, so null will find a new
			// person to play against.
			return null;
		}
	}

	public void onLeaveClicked() {
		String nextParticipantId = getNextParticipantId();
		mContext.getGamesClient().leaveTurnBasedMatchDuringTurn(mContext,
				mMatch.getMatchId(), nextParticipantId);
	}

	/**
	 * Starts a new match by loading the initial game state
	 * @param match
	 * @param gameID
	 * @param gameVersion
	 */
	public void startMatch(TurnBasedMatch match, String gameID, String gameVersion) {
		mMatch = match;

		mTurnData = new TurnData();
		mTurnData.game = gameID;
		mTurnData.version = gameVersion;
		mTurnData.turnCounter = 1;

		initGameViewManager();
		mTurnData.state = mGame.getCurrentLevel().getUniverse().getState();

		String myParticipantId = mMatch.getParticipantId(mContext
				.getGamesClient().getCurrentPlayerId());

		// Taking this turn will cause turnBasedMatchUpdated
		mContext.getGamesClient().takeTurn(mContext, match.getMatchId(),
				mTurnData.persist(), myParticipantId);
	}

	public void rematch() {
		mContext.getGamesClient().rematchTurnBasedMatch(mContext, mMatch.getMatchId());
		mMatch = null;
		isDoingTurn = false;
	}

	public void onCancelClicked() {
		mContext.getGamesClient().cancelTurnBasedMatch(mContext, mMatch.getMatchId());
		isDoingTurn = false;
	}

	public void onFinishClicked() {
		mContext.getGamesClient().finishTurnBasedMatch(mContext, mMatch.getMatchId());
		isDoingTurn = false;
	}

	public void onTurnBasedMatchCanceled(int statusCode, String matchId) {
		if (!checkStatusCode(null, statusCode)) {
			return;
		}

		isDoingTurn = false;
		showWarning("Match",
				"This match is canceled.  All other players will have their game ended.");
	}

	public void updateMatch(TurnBasedMatch match) {
		mMatch = match;

		int status = match.getStatus();
		int turnStatus = match.getTurnStatus();

		switch (status) {
		case TurnBasedMatch.MATCH_STATUS_CANCELED:
			showWarning("Canceled!", "This game was canceled!");
			return;
		case TurnBasedMatch.MATCH_STATUS_EXPIRED:
			showWarning("Expired!", "This game is expired.  So sad!");
			return;
		case TurnBasedMatch.MATCH_STATUS_AUTO_MATCHING:
			showWarning("Waiting for auto-match...",
					"We're still waiting for an automatch partner.");
			return;
		case TurnBasedMatch.MATCH_STATUS_COMPLETE:
			if (turnStatus == TurnBasedMatch.MATCH_TURN_STATUS_COMPLETE) {
				showWarning(
						"Complete!",
						"This game is over; someone finished it, and so did you!  There is nothing to be done.");
				break;
			}

			// Note that in this state, you must still call "Finish" yourself,
			// so we allow this to continue.
			showWarning("Complete!",
					"This game is over; someone finished it!  You can only finish it now.");
		}

		// OK, it's active. Check on turn status.
		switch (turnStatus) {
		case TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN:
			mTurnData = TurnData.unpersist(mMatch.getData());
			setGameplayUI();
			return;
		case TurnBasedMatch.MATCH_TURN_STATUS_THEIR_TURN:
			// Should return results.
			showWarning("Alas...", "It's not your turn.");
			break;
		case TurnBasedMatch.MATCH_TURN_STATUS_INVITED:
			showWarning("Good inititative!",
					"Still waiting for invitations.\n\nBe patient!");
		}

		mTurnData = null;
		mContext.setViewVisibility();
	}

	// Generic warning/info dialog
	public void showWarning(String title, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

		// set title
		alertDialogBuilder.setTitle(title).setMessage(message);

		// set dialog message
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// current activity
					}
				});

		// Create alert dialog & show it
		mAlertDialog = alertDialogBuilder.create();
		mAlertDialog.show();
	}

	public void showErrorMessage(TurnBasedMatch match, int statusCode, int stringId) {
		showWarning("Warning", mContext.getResources().getString(stringId));
	}

	// Returns false if something went wrong, probably. This should handle
	// more cases, and probably report more accurate results.
	private boolean checkStatusCode(TurnBasedMatch match, int statusCode) {
		switch (statusCode) {
		case GamesClient.STATUS_OK:
			return true;
		case GamesClient.STATUS_NETWORK_ERROR_OPERATION_DEFERRED:
			// This is OK; the action is stored by Google Play Services and will
			// be dealt with later.
			return true;
		case GamesClient.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER:
			showErrorMessage(match, statusCode,
					R.string.status_multiplayer_error_not_trusted_tester);
			break;
		case GamesClient.STATUS_MATCH_ERROR_ALREADY_REMATCHED:
			showErrorMessage(match, statusCode,
					R.string.match_error_already_rematched);
			break;
		case GamesClient.STATUS_NETWORK_ERROR_OPERATION_FAILED:
			showErrorMessage(match, statusCode,
					R.string.network_error_operation_failed);
			break;
		case GamesClient.STATUS_CLIENT_RECONNECT_REQUIRED:
			showErrorMessage(match, statusCode,
					R.string.client_reconnect_required);
			break;
		case GamesClient.STATUS_INTERNAL_ERROR:
			showErrorMessage(match, statusCode, R.string.internal_error);
			break;
		case GamesClient.STATUS_MATCH_ERROR_INACTIVE_MATCH:
			showErrorMessage(match, statusCode,
					R.string.match_error_inactive_match);
			break;
		case GamesClient.STATUS_MATCH_ERROR_LOCALLY_MODIFIED:
			showErrorMessage(match, statusCode,
					R.string.match_error_locally_modified);
			break;
		default:
			showErrorMessage(match, statusCode, R.string.unexpected_status);
			PygmyApp.logD("Did not have warning or string to deal with: "
					+ statusCode);
		}
		return false;
	}

	public void onTurnBasedMatchUpdated(int statusCode, TurnBasedMatch match) {
		if (!checkStatusCode(match, statusCode)) {
			return;
		}
		if (match.canRematch()) {
			askForRematch();
		}

		isDoingTurn = (match.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN);
		if (isDoingTurn) {
			updateMatch(match);
			return;
		}
	}

	// Rematch dialog
	public void askForRematch() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				mContext);

		alertDialogBuilder.setMessage("Do you want a rematch?");

		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Sure, rematch!",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								mContext.rematch();
							}
						})
				.setNegativeButton("No.",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
							}
						});

		alertDialogBuilder.show();
	}

	public void onTurnBasedMatchLeft(int statusCode, TurnBasedMatch match) {
		if (!checkStatusCode(match, statusCode)) {
			return;
		}
		isDoingTurn = (match.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN);
		showWarning("Left", "You've left this match.");
	}

	public void onTurnBasedMatchInitiated(int statusCode, TurnBasedMatch match) {
		if (!checkStatusCode(match, statusCode)) {
			return;
		}

		if (match.getData() != null) {
			// This is a game that has already started, so just start it
			updateMatch(match);
			return;
		}

		mContext.startMatch(match);
	}

	public boolean isDoingTurn() {
		return isDoingTurn;
	}

	public AlertDialog getDialog() {
		return mAlertDialog;
	}
	
	private void showDownloadPrompt() {
		AlertDialog dialog = new AlertDialog.Builder(mContext)
		.setMessage(
				"The game you tried to play is not installed on your device. Would you like to install it?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						DownloadTask downloadtask = new DownloadTask(mContext);
						downloadtask.setOnPostExecutor(new DownloadTask.OnPostExecutor() {
							
							@Override
							public void onPostExecute() {
								initGameViewManager();
								isDoingTurn = true;
								mContext.setViewVisibility();
								mGameViewManager.updateData(mTurnData);
							}
						});
						downloadtask.execute(mTurnData.game, mTurnData.version);
					}
				}).setNegativeButton("No", null).show();
	}

	/**
	 * Upload your new game state, then take a turn, and pass it on to the next player
	 */
	public void onTurnTaken() {
		String nextParticipantId = getNextParticipantId();

		// Create the next turn
		mTurnData.turnCounter += 1;
		mTurnData.state = mGame.getCurrentLevel().getUniverse().getState();

		mContext.getGamesClient().takeTurn(mContext, mMatch.getMatchId(),
				mTurnData.persist(), nextParticipantId);

		Utils.saveGame(mGame, getHistoryPath());

		mTurnData = null;
	}

	private String getHistoryPath() {
		return Utils.getGamePath(mContext, mTurnData.game, mTurnData.version, mMatch.getMatchId());
	}

}