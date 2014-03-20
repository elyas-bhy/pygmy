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
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.dev.pygmy.PygmyApp;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.Tile;
import com.lib.pygmy.util.Point;

/**
 * 	This class represents the grid of the board.
 */
public class GameBoardView extends View {

	private static int numberOfRows;
	private static int numberOfColumns;
	private static int tileSize;

	private int boardType;
	private List<Integer> colors;
	private Paint colorBlack = null;

	private static Map<Point,Tile> tilesMap;

	/**
	 * Default constructor.
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
		colorBlack.setColor(Color.BLACK);
		colorBlack.setTextSize(20);

		numberOfRows = game.getCurrentLevel().getNumberRows();
		numberOfColumns = game.getCurrentLevel().getNumberColumns();
		boardType = game.getCurrentLevel().getBoardType();
		//colors = game.getCurrentLevel().getColors();
	}

	/**
	 * 
	 * @param row		Relative position on Y
	 * @param column	Relative position on X
	 * @return the Tile according to coordinates (column, row)
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
		case 0:
			drawCheckerboard(canvas);
			break;
		case 1:
			drawGrid(canvas);
			break;
		case 2:
			drawHexGrid(canvas);
			break;
		default:
			PygmyApp.logE("Error : Board's type do not exist.");
			break;
		}
	}
	
	private void drawCheckerboard(Canvas canvas) {

		//		if (colors.size() < 2) {
		//			throw new IllegalStateException("It is mandatory to have two colors to build a Checker Board.");
		//		}
		//		Paint color1 = new Paint(colors.get(1));
		//		Paint color2 = new Paint(colors.get(2));

		Paint color1 = new Paint();
		Paint color2 = new Paint();
		color1.setColor(Color.GRAY);
		color2.setColor(Color.WHITE);

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
						t.setColor(((x + y)%2 != 0) ? color1:color2);
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

	private void drawHexbox(Canvas canvas, int tileSize, int coordX, int coordY) {

		// Distance difference between square and hexa
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

		for(int y = 0; y < tileWidth +1 ; ++y) {
			if (y != 0){
				for(int x = 0 ; x < tileHeight +1; ++x) {
					if (x != 0) {
						coordX = x*tileSize+offset;
						coordY = (y+1)*(tileSize-colision);

						Tile tile = null;new Tile(coordX,coordY, tileSize);
						if(y%2 == 0) {
							drawHexbox(canvas, tileSize, coordY, coordX + half);
							tile = new Tile(coordX + half, coordY, tileSize-half);
						} else { 
							drawHexbox(canvas, tileSize, coordY, coordX);
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
				if (y != tileWidth+1) {
					canvas.drawText(Character.toString((char)('A'-1+y)), 
							(y + 1)*(tileSize-colision) + (tileSize/4) - colorBlack.getTextSize()/2 + offset,
							tileSize/2 + colorBlack.getTextSize()/2 + offset, colorBlack);
				}
			}
		}
	}

	private void drawGrid(Canvas canvas) {

		int tileWidth = Math.min(numberOfRows, numberOfColumns);
		int tileHeight = Math.max(numberOfRows, numberOfColumns);

		int width = getWidth();
		int height = getHeight();

		int offset = 0, coordY = 0, coordX = 0;

		// One tile distance
		tileSize = Math.min(width / (tileWidth+2), height / (tileHeight + 2));
		// Margin based to tileSize
		offset = tileSize / 3;

		int smallDistance = tileSize + offset;
		int longDistanceHeight = tileSize*(tileHeight + 1) + offset;
		int longDistanceWidth = tileSize*(tileWidth + 1) + offset;

		for(int y = 0; y <= tileWidth +1 ; ++y) {
			if (y != 0) {
				for(int x = 0 ; x <= tileHeight +1; ++x) {
					if (x != 0) {
						coordY = y*tileSize+offset;
						coordX = x*tileSize+offset;

						if(y < tileWidth+1 && x < tileHeight+1) {
							tilesMap.put(new Point(x-1,y-1), new Tile(coordX, coordY, tileSize));
						}

						// Draw case
						canvas.drawLine(coordY, smallDistance, coordY, longDistanceHeight, colorBlack);
						canvas.drawLine(smallDistance, coordX, longDistanceWidth, coordX, colorBlack);
						if (y == 1 && x != tileHeight+1) {
							canvas.drawText(Integer.toString(x), 
									tileSize/2 - colorBlack.getTextSize()/2 + offset, 
									x*tileSize + (tileSize/2) + colorBlack.getTextSize()/2 + offset, 
									colorBlack);
						}
					}
				}
				if (y != tileWidth+1) {
					canvas.drawText(Character.toString((char)('A' - 1 + y)), 
							y*tileSize + (tileSize/2) - colorBlack.getTextSize()/2 + offset, 
							tileSize/2 + colorBlack.getTextSize()/2 + offset, colorBlack);
				}
			}
		}
	}

	private void drawBoard(Canvas canvas) {
		int tileWidth = Math.min(numberOfRows, numberOfColumns);
		int tileHeight = Math.max(numberOfRows, numberOfColumns);

		int width = getWidth();
		int height = getHeight();

		int offset = 0, coordY = 0, coordX = 0;

		// One tile distance
		tileSize = Math.min(width / (tileWidth+2), height / (tileHeight + 2));
		// Margin based to tileSize
		offset = tileSize / 3;


		//--// CheckerBoard
		if (boardType == 0) {

		}

		//--//Grid
		if (boardType == 1) {
			int smallDistance = tileSize + offset;
			int longDistanceHeight = tileSize*(tileHeight + 1) + offset;
			int longDistanceWidth = tileSize*(tileWidth + 1) + offset;
		}

		//--// HexGrid
		if (boardType == 2) {
			// One tile distance
			if(tileSize * 1.2 < numberOfRows - tileSize / 3 * 1.2 && 
					tileSize * 1.2 < numberOfColumns-tileSize / 3 * 1.2) {
				tileSize *= 1.2;
			}

			int colision = tileSize / 4;
			int half = tileSize / 2;
		}

		for(int y = 0; y < tileWidth +1 ; ++y) {
			if (y != 0){
				for(int x = 0 ; x < tileHeight +1; ++x) {
					if (x != 0) {}}}}
	}
}