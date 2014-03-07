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
import android.graphics.Point;
import android.view.View;

import com.lib.pygmy.view.Tile;

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

	private static Map<Point, Tile> mapTileCoord;

	/**
	 * Default constructor.
	 */
	public GameBoardView(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param context	Context parent.
	 * @param params	A map with board parameters. 
	 */
	public GameBoardView(Context context, Map<String,Object> params) {
		super(context);

		numberOfRows = (Integer) params.get("numberRows");
		numberOfColumns = (Integer) params.get("numberColumns");
		boardType = (Integer) params.get("boardType");

		color1 = new Paint();
		color2 = new Paint();
		color1.setColor(Color.CYAN);
		color2.setColor(Color.WHITE);
		mapTileCoord = new HashMap<Point,Tile>();
		colorBlack = new Paint();
	}
	
	/**
	 * 
	 * @param row		Relative position on Y
	 * @param column	Relative position on X
	 * @return the Tile according to coordinates (column, row)
	 */
	public static Tile getTileAt(int row, int column) {
		return mapTileCoord.get(new Point(row, column));
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
		int dim1 = numberOfRows;
		int dim2 = numberOfColumns;
		
		switch (boardType) {
		case 0:
			drawCheckerboard(canvas, dim1, dim2, color1, color2);
			break;
		case 1:
			draw_grid(canvas, dim1, dim2);
			break;
		case 2:
			draw_hexgrid(canvas, dim1, dim2);
			break;
		default:
			System.err.println("Error : Board's type do not exist.");
			break;
		}
	}

	private void draw_hexcase(Canvas canvas, int case_size, int coord_x, int coord_y){

		// Distance difference between carre and hexa
		int dist = case_size / 4;
		// Half case's size
		int half = case_size /2;

		// Left Up
		canvas.drawLine(coord_x + dist, coord_y, coord_x, coord_y + half, colorBlack);
		// Up
		canvas.drawLine(coord_x + dist, coord_y, coord_x + case_size - dist, coord_y, colorBlack);
		// Down
		canvas.drawLine(coord_x + case_size - dist, coord_y + case_size, coord_x + dist, coord_y + case_size, colorBlack);
		// Right Down
		canvas.drawLine(coord_x + case_size, coord_y + half, coord_x + case_size - dist, coord_y + case_size, colorBlack);
		// Left Down
		canvas.drawLine(coord_x, coord_y + half, coord_x + dist, coord_y + case_size, colorBlack);
		// Right Up
		canvas.drawLine(coord_x + case_size - dist, coord_y, coord_x + case_size, coord_y + half, colorBlack);

	}

	private void draw_hexgrid(Canvas canvas, int dim1, int dim2){

		int width_case = Math.min(dim1, dim2);
		int height_case = Math.max(dim1, dim2);

		// Max Size Width
		int width_size = dim1;
		// Max Size Height
		int height_size = dim2;

		int width = getWidth();
		int height = getHeight();

		int case_size = 0, offset = 0, coord_x = 0, coord_y;

		// One case distance
		case_size = Math.min(width / (width_case+2), height / (height_case+2));
		if(case_size*1.2 < width_size-case_size/3*1.2 && case_size*1.2 < height_size-case_size/3*1.2)
			case_size *= 1.2;
		// Marge based to case_size
		offset = case_size / 3;

		int colision = case_size / 4;
		int half = case_size /2;

		for(int y = 0; y < width_case +1 ; ++y) {
			if (y != 0){
				for(int x = 0 ; x < height_case +1; ++x) 
					if (x != 0){
						coord_x = x*case_size+offset;
						coord_y = (y+1)*(case_size-colision);
						// FIXME use new Tile(int, int, int, int, int)
						Tile tile = null;new Tile(coord_x,coord_y, case_size);
						if(y%2 == 0){
							draw_hexcase(canvas, case_size, coord_y, coord_x + half);
							tile = new Tile(coord_x + half, coord_y, case_size-half);
							
						}
						else { 
							draw_hexcase(canvas, case_size, coord_y, coord_x);
							tile = new Tile(coord_x,coord_y, case_size-half);
						}
						mapTileCoord.put(new Point(x-1, y-1), tile);
						
						if (y == 1 && x != height_case+1)
							canvas.drawText(Integer.toString(x), case_size/2-colorBlack.getTextSize()/2+offset, x*case_size+(case_size/2)+colorBlack.getTextSize()/2+offset, colorBlack);		

					}
				if (y != width_case+1)
					canvas.drawText(Character.toString((char)('A'-1+y)), (y+1)*(case_size-colision)+(case_size/4)-colorBlack.getTextSize()/2+offset, case_size/2+colorBlack.getTextSize()/2+offset, colorBlack);

			}
		}
	}

	private void draw_grid(Canvas canvas, int dim1, int dim2){

		int width_case = Math.min(dim1, dim2);
		int height_case = Math.max(dim1, dim2);

		int width = getWidth();
		int height = getHeight();

		int case_size = 0, offset = 0, coord_y = 0, coord_x = 0;

		// One case distance
		case_size = Math.min(width / (width_case+2), height / (height_case+2));
		// Marge based to case_size
		offset = case_size / 3;

		int small_distance = case_size+offset;
		int long_distance_height = case_size*(height_case+1)+offset;
		int long_distance_width = case_size*(width_case+1)+offset;

		for(int y = 0; y <= width_case +1 ; ++y) {
			if (y != 0){
				for(int x = 0 ; x <= height_case +1; ++x) 
					if (x != 0){
						coord_y = y*case_size+offset;
						coord_x = x*case_size+offset;

						// FIXME use new Tile(int, int, int, int, int)
						if(y < width_case +1 && x < height_case +1)
							mapTileCoord.put(new Point(x-1,y-1), new Tile(coord_x, coord_y, case_size));

						// Draw case
						canvas.drawLine(coord_y, small_distance, coord_y, long_distance_height, colorBlack);
						canvas.drawLine(small_distance, coord_x, long_distance_width, coord_x, colorBlack);
						if (y == 1 && x != height_case+1)
							canvas.drawText(Integer.toString(x), case_size/2-colorBlack.getTextSize()/2+offset, x*case_size+(case_size/2)+colorBlack.getTextSize()/2+offset, colorBlack);		

					}
				if (y != width_case+1)
					canvas.drawText(Character.toString((char)('A'-1+y)), y*case_size+(case_size/2)-colorBlack.getTextSize()/2+offset, case_size/2+colorBlack.getTextSize()/2+offset, colorBlack);
			}
		}
	}

	private void drawCheckerboard(Canvas canvas, int dim1, int dim2, Paint color1, Paint color2) {

		colorBlack.setColor(Color.BLACK);
		colorBlack.setTextSize(20);
		int tileWidth = Math.min(dim1, dim2);
		int tileHeight = Math.max(dim1, dim2);

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
						mapTileCoord.put(new Point(x-1, y-1), t);
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
		int small_distance = tileSize+offset;
		int long_distance_height = tileSize*(tileHeight+1)+offset;
		int long_distance_width = tileSize*(tileWidth+1)+offset;
		canvas.drawLine(small_distance, small_distance, small_distance, long_distance_height, colorBlack);
		canvas.drawLine(small_distance, small_distance, long_distance_width, small_distance, colorBlack);
		canvas.drawLine(long_distance_width, long_distance_height, long_distance_width, small_distance, colorBlack);
		canvas.drawLine(long_distance_width, long_distance_height, small_distance, long_distance_height, colorBlack);
	}
}