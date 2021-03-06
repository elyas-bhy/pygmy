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

package com.dev.pygmy.view;

import java.util.Collection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.dev.pygmy.PygmyApp;
import com.dev.pygmy.PygmyTurnListener;
import com.dev.pygmy.util.Utils;
import com.lib.pygmy.GameEntity;
import com.lib.pygmy.GameMove;
import com.lib.pygmy.GameUniverse;
import com.lib.pygmy.IllegalMoveException;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.Tile;
import com.lib.pygmy.util.Point;
import com.lib.pygmy.util.TurnData;

/**
 * This class represents the view which shows the 
 * pieces of the board game on the screen.
 * @author Pygmy
 */
public class EntityView extends View {
	
	private Context context;
	private PygmyGame game;
	private Collection<GameEntity> entities;
	
	private boolean initial = true;
	private int tileSize = 0;
	private int offset = 0;
	
	private int targetColumn = 0;
	private int targetRow = 0;
	private GameEntity draggedEntity;
	private Tile entityCurrentPosition;

	/**
	 * Default constructor.
	 * @param context		Context parent
	 */
	public EntityView(Context context) {
		super(context);
	}

	/**
	 * Constructs the view with the entities (pieces) of the board game.
	 * @param context		Context parent.
	 * @param game			Reference to the game context 
	 */
	public EntityView(Context context, PygmyGame game) {
		super(context);
		setFocusable(true); // Necessary for getting the touch events

		this.context = context;
		this.game = game;
		GameUniverse universe = game.getCurrentLevel().getUniverse();
		entities = universe.getGameEntities();
	}
	
	/**
	 * Updates the entities and universe state according to the passed response.
	 */
	public void updateData(TurnData data) {
		GameUniverse universe = game.getCurrentLevel().getUniverse();
		universe.updateData(data);
		entities = universe.getGameEntities();
	}
	
	/**
	 * Sets position on the board for each entity.
	 */
	private void initTiles() {
		Tile tile;
		Point p;
		for (GameEntity entity : entities) {
			if (entity != null) {
				p = entity.getCurrentTile().getPosition();
				tile = GameBoardView.getTileAt(p.x, p.y);
				entity.setCurrentTile(tile);
			}
		}
	}

	@Override 
	protected void onDraw(Canvas canvas) {
		// Setting the start point for the entities
		if (initial) {
			initial = false;
			initTiles();
		}

		// Draw the entity on the canvas
		Point coords;
		for (GameEntity entity : entities) {
			if (entity != null) {
				if (entity.getBitmap() == null) {
					int hSize = entity.getCurrentTile().getHeight();
					int wSize = entity.getCurrentTile().getWidth();
					int scale = (int) (Math.min(hSize, wSize) * 0.95);
					Bitmap bitmap = Utils.getBitmapByType(context.getResources(), entity.getType(), 
							scale);
					entity.setBitmap(bitmap);
				}
				coords = entity.getCurrentTile().getCoordinates();
				canvas.drawBitmap(entity.getBitmap(), coords.x, coords.y, null);
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		int nbRows = GameBoardView.getNumberOfRows();
		int nbColumns = GameBoardView.getNumberOfColumns();
		
		int minX = tileSize + offset;
		int minY = tileSize + offset;
		int maxX = minX + (tileSize * nbRows);
		int maxY = minY + (tileSize * nbColumns);

		switch (event.getAction()) { 

		// The finger is on an entity
		case MotionEvent.ACTION_DOWN: 
			Point coords;
			Tile tile;
			
			// Get what entity is being dragged.
			for (GameEntity entity : entities) {
				// Check all the bounds of the entity
				if (entity != null) {
					tile = entity.getCurrentTile();
					coords = tile.getCoordinates();
					tileSize = tile.getWidth();
					
					// Event is inside a tile.
					if (x > coords.x && x < coords.x + tileSize 
					 && y > coords.y && y < coords.y + tileSize) {

						String entityPlayerId = entity.getPlayerId();
						String currentPlayerId = game.getCurrentPlayerId();

						if (!entityPlayerId.equals(currentPlayerId)) {
							PygmyApp.logD("It's not your turn!!");
							return true;
						}
						
						draggedEntity = entity;
						entityCurrentPosition = tile;
						offset = tileSize/3;
						break;
					}
				}
			}
			break;

		// Touch drag with the entity
		case MotionEvent.ACTION_MOVE:
			// Move the entities the same as the finger
			if (draggedEntity != null) {
				float eventX = event.getX() * event.getXPrecision();
				float eventY = event.getY() * event.getYPrecision();
				
				// The entity must not go out of the board.
				if (minX < eventX && eventX < maxX && minY < eventY && eventY < maxY) {
					//Identify the hovered tile
					
					targetColumn = (int)( (eventX - minX) / tileSize );
					targetRow = (int)( (eventY - minY) / tileSize );
					
					Tile nextTile = GameBoardView.getTileAt(targetRow, targetColumn);
					
					// Show the future position of the entity
					GameViewManager.redrawOverlay();
					GameViewManager.getOverlay()
							.setCoordinates(nextTile.getCoordinates().x, 
											nextTile.getCoordinates().y, tileSize, tileSize);

					// Move entity
					draggedEntity.setCurrentTile(new Tile((x - tileSize/2), (y - tileSize/2), 0));
				}
			}
			break;

		// The finger has left the screen.
		case MotionEvent.ACTION_UP:
			GameViewManager.resetOverlay();

			if (draggedEntity != null && entityCurrentPosition != null) {
				// Entity should not go outside of the board
				draggedEntity.setCurrentTile(entityCurrentPosition);
				if (!(entityCurrentPosition.getPosition().x == targetRow && 
						entityCurrentPosition.getPosition().y == targetColumn)) {
					if (minX < x && x < maxX && minY < y 
							&& y < maxY && targetRow >= 0 && targetColumn >= 0) {
						Tile dst = GameBoardView.getTileAt(targetRow, targetColumn);
						GameMove move = new GameMove(draggedEntity, dst);
						try {
							game.onPlayerMove(move);
							if (context instanceof PygmyTurnListener) {
								((PygmyTurnListener) context).onTurnTaken();
							}
						} catch (IllegalMoveException e) {
							Toast.makeText(context, "Illegal move!", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
			entityCurrentPosition = null;
			draggedEntity = null;
			break;
			
		default:
			break;
		}
		
		// Redraw the canvas
		invalidate(); 
		return true; 
	}
}