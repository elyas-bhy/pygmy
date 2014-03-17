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
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.dev.pygmy.PygmyApp;
import com.lib.pygmy.Tile;
import com.lib.pygmy.util.Point;

/**
 * 	This class represents the grid of the board.
 */
public class GameBoardView extends View {

	private static int numberOfRows;
	private static int numberOfColumns;
	private int boardType;
	private Paint color1 = null;
	private Paint color2 = null;
	private Paint colorBlack = null;

	private static Map<Point,Tile> tilesMap;
	
	/**
	 * 
	 * @param context	Context parent.
	 * @param params	A map with board parameters. 
	 */
	public GameBoardView(Context context) {
		super(context);

		numberOfRows = 8;  //(Integer) params.get("numberRows");
		numberOfColumns = 8;  //(Integer) params.get("numberColumns");
		boardType = 0;  //(Integer) params.get("boardType");

		color1 = new Paint();
		color2 = new Paint();
		color1.setColor(Color.CYAN);
		color2.setColor(Color.WHITE);
		tilesMap = new HashMap<Point,Tile>();
		colorBlack = new Paint();
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

	@Override
	protected void onDraw(Canvas canvas) {

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
		canvas.drawLine(coordX + tileSize - dist, coordY + tileSize, coordX + dist, coordY + tileSize, colorBlack);
		// Right Down
		canvas.drawLine(coordX + tileSize, coordY + half, coordX + tileSize - dist, coordY + tileSize, colorBlack);
		// Left Down
		canvas.drawLine(coordX, coordY + half, coordX + dist, coordY + tileSize, colorBlack);
		// Right Up
		canvas.drawLine(coordX + tileSize - dist, coordY, coordX + tileSize, coordY + half, colorBlack);

	}

	private void drawHexGrid(Canvas canvas) {

		int widthBox = Math.min(numberOfRows, numberOfColumns);
		int heightBox = Math.max(numberOfRows, numberOfColumns);

		// Max Size Width
		int widthSize = numberOfRows;
		// Max Size Height
		int heightSize = numberOfColumns;

		int width = getWidth();
		int height = getHeight();

		int tileSize = 0, offset = 0, coordX = 0, coordY = 0;

		// One tile distance
		tileSize = Math.min(width / (widthBox + 2), height / (heightBox+2));
		if(tileSize*1.2 < widthSize - tileSize / 3*1.2 && tileSize*1.2 < heightSize-tileSize/3*1.2) {
			tileSize *= 1.2;
		}

		// Margin based to tileSize
		offset = tileSize / 3;

		int colision = tileSize / 4;
		int half = tileSize / 2;

		for(int y = 0; y < widthBox +1 ; ++y) {
			if (y != 0){
				for(int x = 0 ; x < heightBox +1; ++x) {
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

						if (y == 1 && x != heightBox+1) {
							canvas.drawText(Integer.toString(x), 
									tileSize/2-colorBlack.getTextSize()/2+offset, 
									x*tileSize+(tileSize/2)+colorBlack.getTextSize()/2+offset, 
									colorBlack);
						}
					}
				}
				if (y != widthBox+1) {
					canvas.drawText(Character.toString((char)('A'-1+y)), 
							(y+1)*(tileSize-colision)+(tileSize/4)-colorBlack.getTextSize()/2+offset,
							tileSize/2+colorBlack.getTextSize()/2+offset, colorBlack);
				}
			}
		}
	}

	private void drawGrid(Canvas canvas) {

		int widthBox = Math.min(numberOfRows, numberOfColumns);
		int heightBox = Math.max(numberOfRows, numberOfColumns);

		int width = getWidth();
		int height = getHeight();

		int tileSize = 0, offset = 0, coordY = 0, coordX = 0;

		// One tile distance
		tileSize = Math.min(width / (widthBox+2), height / (heightBox+2));
		// Margin based to tileSize
		offset = tileSize / 3;

		int smallDistance = tileSize+offset;
		int longDistanceHeight = tileSize*(heightBox+1)+offset;
		int longDistanceWidth = tileSize*(widthBox+1)+offset;

		for(int y = 0; y <= widthBox +1 ; ++y) {
			if (y != 0) {
				for(int x = 0 ; x <= heightBox +1; ++x) {
					if (x != 0) {
						coordY = y*tileSize+offset;
						coordX = x*tileSize+offset;

						if(y < widthBox +1 && x < heightBox +1)
							tilesMap.put(new Point(x-1,y-1), new Tile(coordX, coordY, tileSize));

						// Draw case
						canvas.drawLine(coordY, smallDistance, coordY, longDistanceHeight, colorBlack);
						canvas.drawLine(smallDistance, coordX, longDistanceWidth, coordX, colorBlack);
						if (y == 1 && x != heightBox+1)
							canvas.drawText(Integer.toString(x), 
									tileSize/2-colorBlack.getTextSize()/2+offset, 
									x*tileSize+(tileSize/2)+colorBlack.getTextSize()/2+offset, 
									colorBlack);		
					}
				}
				if (y != widthBox+1) {
					canvas.drawText(Character.toString((char)('A'-1+y)), 
							y*tileSize+(tileSize/2)-colorBlack.getTextSize()/2+offset, 
							tileSize/2+colorBlack.getTextSize()/2+offset, colorBlack);
				}
			}
		}
	}

	private void drawCheckerboard(Canvas canvas) {

		colorBlack.setColor(Color.BLACK);
		colorBlack.setTextSize(20);
		int tileWidth = Math.min(numberOfRows, numberOfColumns);
		int tileHeight = Math.max(numberOfRows, numberOfColumns);

		int width = getWidth();
		int height = getHeight();

		int tileSize = 0, offset = 0, coordX = 0, coordY = 0;
		// Minimum size in width and length

		tileSize = Math.min(width / (tileWidth+2), height / (tileHeight+2));
		offset = tileSize / 3;

		for (int x = 0; x < tileWidth +1 ; ++x) {
			if (x != 0) {
				for(int y = 0; y < tileHeight +1; ++y) {
					if (y != 0) {

						coordX = x * tileSize + offset;
						coordY = y * tileSize + offset;
						if (x == 1) {
							canvas.drawText(Integer.toString(y), 
									tileSize/2-colorBlack.getTextSize()/2+offset, 
									y*tileSize+(tileSize/2)+colorBlack.getTextSize()/2+offset, 
									colorBlack);	
						}

						Tile t = new Tile(coordY, coordX, tileSize);
						t.setPosition(new Point(x-1, y-1));
						t.setColor(((x + y)%2 != 0) ? color1:color2);
						tilesMap.put(new Point(x-1, y-1), t);
						t.draw(canvas);
					}
				}
				canvas.drawText(Character.toString((char)('A'-1+x)), 
						x*tileSize+(tileSize/2)-colorBlack.getTextSize()/2+offset, 
						tileSize/2+colorBlack.getTextSize()/2+offset, colorBlack);
				canvas.drawText(Integer.toString(x), 
						tileSize/2-colorBlack.getTextSize()/2+offset, 
						x*tileSize+(tileSize/2)+colorBlack.getTextSize()/2+offset, colorBlack);
			}
		}

		// Draw checkerboard's outline
		int smallDistance = tileSize+offset;
		int longDistanceHeight = tileSize*(tileHeight+1)+offset;
		int longDistanceWidth = tileSize*(tileWidth+1)+offset;
		canvas.drawLine(smallDistance, smallDistance, smallDistance, longDistanceHeight, colorBlack);
		canvas.drawLine(smallDistance, smallDistance, longDistanceWidth, smallDistance, colorBlack);
		canvas.drawLine(longDistanceWidth, longDistanceHeight, longDistanceWidth, smallDistance, colorBlack);
		canvas.drawLine(longDistanceWidth, longDistanceHeight, smallDistance, longDistanceHeight, colorBlack);
	}
}