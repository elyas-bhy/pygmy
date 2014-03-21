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

package com.dev.pygmy.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.dev.pygmy.PygmyApp;
import com.lib.pygmy.BoardType;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.Tile;
import com.lib.pygmy.util.Color;
import com.lib.pygmy.util.Point;

/**
 * 	This class represents the grid of the board.
 * @author Pygmy
 */
public class GameBoardView extends View {

	private static int numberOfRows;
	private static int numberOfColumns;
	private static int tileSize;
	private static Map<Point,Tile> tilesMap;

	private int boardType;
	private List<Color> colors;
	private Paint colorBlack = null;
	
	/**
	 * Default constructor, needed by Android.
	 */
	public GameBoardView(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param context	Context parent.
	 * @param game 		The current game.
	 */
	public GameBoardView(Context context, PygmyGame game) {
		super(context);

		tilesMap = new HashMap<Point,Tile>();
		colorBlack = new Paint();
		colorBlack.setColor(android.graphics.Color.BLACK);
		colorBlack.setTextSize(20);

		numberOfRows = game.getCurrentLevel().getNumberRows();
		numberOfColumns = game.getCurrentLevel().getNumberColumns();
		boardType = game.getCurrentLevel().getBoardType();
		colors = game.getCurrentLevel().getColors();
	}

	/**
	 * @param row		Relative position on Y
	 * @param column	Relative position on X
	 * @return the Tile according to position (row, column)
	 */
	public static Tile getTileAt(int row, int column) {
		return tilesMap.get(new Point(row, column));
	}

	/**
	 * @return the number of rows of the board.
	 */
	public static int getNumberOfRows() {
		return numberOfRows;
	}

	/**
	 * @return the number of columns of the board.
	 */
	public static int getNumberOfColumns() {
		return numberOfColumns;
	}

	/**
	 * @return the size of a tile in the board.
	 */
	public static int getTileSize() {
		return tileSize;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// If the number of available board changes, that must be notify 
		// to setBoardType at PygmyGameLevel class.
		switch (boardType) {
		case BoardType.CHECKER_BOARD:
			drawBoard(canvas);
			break;
		case BoardType.GRID_BOARD:
			drawBoard(canvas);
			break;
		case BoardType.HEX_GRID_BOARD:
			drawHexGrid(canvas);
			break;
		default:
			PygmyApp.logE("Error : Board's type do not exist.");
			break;
		}
	}
	
	/**
	 * Draws the type board Checker or grid according with value in @param boardType.
	 */
	private void drawBoard(Canvas canvas) {

		Paint color1 = new Paint();
		Paint color2 = new Paint();

		if (boardType == BoardType.CHECKER_BOARD) {
			if (colors.size() < 2) {
				throw new IllegalStateException("It is mandatory to have two colors to build a Checker Board.");
			}

			Color c;
			c = colors.get(0);
			color1.setARGB(c.getA(), c.getR(), c.getG(), c.getB());
			c = colors.get(1);
			color2.setARGB(c.getA(), c.getR(), c.getG(), c.getB());
		}

		int tileWidth = Math.min(numberOfRows, numberOfColumns);
		int tileHeight = Math.max(numberOfRows, numberOfColumns);

		int width = getWidth();
		int height = getHeight();

		int offset = 0, coordX = 0, coordY = 0;

		tileSize = Math.min(width / (tileWidth + 2), height / (tileHeight + 2));
		offset = tileSize / 3;

		for (int x = 0; x < tileHeight +1 ; ++x) {
			if (x != 0) {
				for(int y = 0; y < tileWidth +1; ++y) {
					if (y != 0) {

						coordX = x * tileSize + offset;
						coordY = y * tileSize + offset;
						if (x == 1) {
							canvas.drawText(Integer.toString(y), 
									tileSize/2 - colorBlack.getTextSize()/2 + offset, 
									y*tileSize + (tileSize/2) + colorBlack.getTextSize()/2 + offset, 
									colorBlack);
						}

						Tile t = new Tile(coordY, coordX, tileSize);
						t.setPosition(new Point(x-1, y-1));
						
						if (boardType == BoardType.CHECKER_BOARD) {
							t.setColor(((x + y)%2 != 0) ? color1:color2);
						} else if (boardType == BoardType.GRID_BOARD) {
							Paint c = new Paint();
							c.setColor(android.graphics.Color.TRANSPARENT);
							t.setColor(c);
							
							int smallDist = tileSize + offset;
							int longDistHeight = tileSize*(tileHeight + 1) + offset;
							int longDistWidth = tileSize*(tileWidth + 1) + offset;

							// Draw lines representing each tiles.
							canvas.drawLine(coordY, smallDist, coordY, longDistHeight, colorBlack);
							canvas.drawLine(smallDist, coordX, longDistWidth, coordX, colorBlack);
						}

						tilesMap.put(new Point(x-1, y-1), t);
						t.draw(canvas);

						canvas.drawText(Character.toString((char)('A'-1+y)), 
								y*tileSize + (tileSize/2) - colorBlack.getTextSize()/2 + offset, 
								tileSize/2 + colorBlack.getTextSize()/2 + offset, colorBlack);
					}
				}
				canvas.drawText(Integer.toString(x), 
						tileSize/2 - colorBlack.getTextSize()/2 + offset, 
						x*tileSize + (tileSize/2) + colorBlack.getTextSize()/2 + offset, colorBlack);
			}
		}

		// Draw checkerboard's outline
		int smallDistance = tileSize + offset;
		int longDistanceHeight = tileSize * (tileHeight + 1) + offset;
		int longDistanceWidth = tileSize * (tileWidth + 1) + offset;
		canvas.drawLine(smallDistance, smallDistance, smallDistance, longDistanceHeight, colorBlack);
		canvas.drawLine(smallDistance, smallDistance, longDistanceWidth, smallDistance, colorBlack);
		canvas.drawLine(longDistanceWidth, longDistanceHeight, longDistanceWidth, smallDistance, colorBlack);
		canvas.drawLine(longDistanceWidth, longDistanceHeight, smallDistance, longDistanceHeight, colorBlack);
	}

	/**
	 * Draws a board of type Hexagonal Grid.
	 */
	private void drawHexGrid(Canvas canvas) {

		int tileWidth = Math.min(numberOfRows, numberOfColumns);
		int tileHeight = Math.max(numberOfRows, numberOfColumns);

		int width = getWidth();
		int height = getHeight();

		int offset = 0, coordX = 0, coordY = 0;

		// One tile distance
		tileSize = Math.min(width / (tileWidth + 2), height / (tileHeight + 2));
		if(tileSize*1.2 < numberOfRows - tileSize / 3*1.2 && tileSize*1.2 < numberOfColumns-tileSize/3*1.2) {
			tileSize *= 1.2;
		}

		// Margin based to tileSize
		offset = tileSize / 3;

		int colision = tileSize / 4;
		int half = tileSize / 2;

		for (int x = 0; x < tileHeight +1 ; ++x) {
			if (x != 0) {
				for(int y = 0; y < tileWidth +1; ++y) {
					if (y != 0) {
						coordX = x*tileSize+offset;
						coordY = (y+1)*(tileSize-colision);

						Tile tile = null;new Tile(coordX,coordY, tileSize);
						if(y%2 == 0) {
							drawHexTile(canvas, coordY, coordX + half);
							tile = new Tile(coordX + half, coordY, tileSize-half);
						} else { 
							drawHexTile(canvas, coordY, coordX);
							tile = new Tile(coordX,coordY, tileSize-half);
						}
						tilesMap.put(new Point(x-1, y-1), tile);

						if (y == 1 && x != tileHeight+1) {
							canvas.drawText(Integer.toString(x), 
									tileSize/2 - colorBlack.getTextSize()/2 + offset, 
									x*tileSize + (tileSize/2) + colorBlack.getTextSize()/2 + offset, 
									colorBlack);
						}
					}
				}
				if (x != tileWidth+1) {
					canvas.drawText(Character.toString((char)('A'-1+x)), 
							(x + 1)*(tileSize-colision) + (tileSize/4) - colorBlack.getTextSize()/2 + offset,
							tileSize/2 + colorBlack.getTextSize()/2 + offset, colorBlack);
				}
			}
		}
	}

	/**
	 * Draws a hexagonal tile for a hexagonal grid.
	 */
	private void drawHexTile(Canvas canvas, int coordX, int coordY) {
		
		// Distance difference between square and hexagonal.
		int dist = tileSize / 4;
		// Half tile's size
		int half = tileSize / 2;
		
		// Left Up
		canvas.drawLine(coordX + dist, coordY, coordX, coordY + half, colorBlack);
		// Up
		canvas.drawLine(coordX + dist, coordY, coordX + tileSize - dist, coordY, colorBlack);
		// Down
		canvas.drawLine(coordX + tileSize - dist, coordY + tileSize, 
				coordX + dist, coordY + tileSize, colorBlack);
		// Right Down
		canvas.drawLine(coordX + tileSize, coordY + half, 
				coordX + tileSize - dist, coordY + tileSize, colorBlack);
		// Left Down
		canvas.drawLine(coordX, coordY + half, coordX + dist, coordY + tileSize, colorBlack);
		// Right Up
		canvas.drawLine(coordX + tileSize - dist, coordY, coordX + tileSize, coordY + half, colorBlack);
	}

}