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
import android.util.Log;
import android.view.View;

/**
 * 	This class represents the grid of the board.
 */
public class GameBoardView extends View {

	private String TAG = "GameBoardView";

	private int numberOfTiles;
	private int numberOfRows;
	private int numberOfColumns;
	private Paint color1 = null;
	private Paint color2 = null;
	private Paint colorBlack = null;

	private static int[][] rectCoord;
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

		numberOfTiles = (Integer) params.get("numberRows");
		numberOfRows = numberOfTiles;
		numberOfColumns = (Integer) params.get("numberColumns");
		
		rectCoord = new int[numberOfTiles*(numberOfTiles+1)][4];

		color1 = new Paint();
		color2 = new Paint();
		color1.setColor(Color.CYAN);
		color2.setColor(Color.WHITE);
		mapTileCoord = new HashMap<Point, Tile>();
		colorBlack = new Paint();
	}

	/**
	 * @return A multidimensional array with the coordinates of each square 
	 * of the board.
	 */
	public static int[][] getRectCoord() {
		return rectCoord;
	}
	
	/**
	 * 
	 * @param row
	 * @param column
	 * @return a Tile 
	 */
	public static Tile getTileCoord(int row, int column) {
		return mapTileCoord.get(new Point(row, column));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
		int dim1 = numberOfTiles;
		int dim2 = numberOfTiles;
		drawCheckerboard(canvas, dim1, dim2);
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

		int case_size = 0, offset = 0, coord_j = 0;

		// One case distance
		case_size = Math.min(width / (width_case+2), height / (height_case+2));
		if(case_size*1.2 < width_size-case_size/3*1.2 && case_size*1.2 < height_size-case_size/3*1.2)
			case_size *= 1.2;
		// Marge based to case_size
		offset = case_size / 3;

		int colision = case_size / 4;
		int half = case_size /2;

		for(int i = 0; i < width_case +1 ; ++i) {
			if (i != 0){
				for(int j = 0 ; j < height_case +1; ++j) 
					if (j != 0){
						coord_j = j*case_size+offset;
						if(i%2 == 0)
							draw_hexcase(canvas, case_size, (i+1)*(case_size-colision), coord_j+half);
						else 
							draw_hexcase(canvas, case_size, (i+1)*(case_size-colision), coord_j);
						if (i == 1 && j != height_case+1)
							canvas.drawText(Integer.toString(j), case_size/2-colorBlack.getTextSize()/2+offset, j*case_size+(case_size/2)+colorBlack.getTextSize()/2+offset, colorBlack);		

					}
				if (i != width_case+1)
					canvas.drawText(Character.toString((char)('A'-1+i)), (i+1)*(case_size-colision)+(case_size/4)-colorBlack.getTextSize()/2+offset, case_size/2+colorBlack.getTextSize()/2+offset, colorBlack);

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

		int ent = 0;
		for(int y = 0; y <= width_case +1 ; ++y) {
			if (y != 0){
				for(int x = 0 ; x <= height_case +1; ++x, ++ent) 
					if (x != 0){
						coord_y = y*case_size+offset;
						coord_x = x*case_size+offset;
						
						rectCoord[ent][0] = coord_x + offset;
						rectCoord[ent][1] = coord_y + offset;
						rectCoord[ent][2] = coord_x + case_size + offset;
						rectCoord[ent][3] = coord_y + case_size + offset;

						/*tabCoord[ent][0] = coord_x;
						tabCoord[ent][1] = coord_y;
						tabCoord[ent][2] = coord_x + case_size;
						tabCoord[ent][3] = coord_y + case_size;*/
						
						// Draw case
						canvas.drawLine(coord_y, small_distance, coord_y, long_distance_height, colorBlack);
						canvas.drawLine(small_distance, coord_x, long_distance_width, coord_x, colorBlack);
						if (y == 1 && x != height_case+1)
							canvas.drawText(Integer.toString(y), case_size/2-colorBlack.getTextSize()/2+offset, x*case_size+(case_size/2)+colorBlack.getTextSize()/2+offset, colorBlack);		

					}
				if (y != width_case+1)
					canvas.drawText(Character.toString((char)('A'-1+y)), y*case_size+(case_size/2)-colorBlack.getTextSize()/2+offset, case_size/2+colorBlack.getTextSize()/2+offset, colorBlack);
			}
		}
	}

	private void drawCheckerboard(Canvas canvas, int dim1, int dim2) {

		colorBlack.setColor(Color.BLACK);
		colorBlack.setTextSize(20);
		int width_case = Math.min(dim1, dim2);
		int height_case = Math.max(dim1, dim2);


		int width = getWidth();
		int height = getHeight();

		int tileSize = 0, offset = 0, coordY = 0, coordX = 0;
		// Minimum size in width and length

		// One case distance
		tileSize = Math.min(width / (width_case+2), height / (height_case+2));
		// Marge based to case_size
		offset = tileSize / 3;

		int id_case = 0;
		for(int y = 0; y < width_case +1 ; ++y) {
			if(y != 0){
				for(int x = 0 ; x < height_case +1; ++x) {
					if(x != 0){

						coordY = y*tileSize+offset;
						coordX = x*tileSize+offset;
						if (y == 1)
							canvas.drawText(Integer.toString(x), 
									tileSize/2-colorBlack.getTextSize()/2+offset, 
									x*tileSize+(tileSize/2)+colorBlack.getTextSize()/2+offset, 
									colorBlack);		

						mapTileCoord.put(new Point(x-1, y-1),
										 new Tile(coordX, coordY,
												  tileSize));
						
						canvas.drawRect(coordX, coordY,
										coordX + tileSize,
										coordY + tileSize,
										((y + x)%2 != 0)?color1:color2);
						
						id_case++;
					}
				}
				canvas.drawText(Character.toString((char)('A'-1+y)), 
						y*tileSize+(tileSize/2)-colorBlack.getTextSize()/2+offset, 
						tileSize/2+colorBlack.getTextSize()/2+offset, colorBlack);		
				canvas.drawText(Integer.toString(y), 
						tileSize/2-colorBlack.getTextSize()/2+offset, 
						y*tileSize+(tileSize/2)+colorBlack.getTextSize()/2+offset, colorBlack);		

			}
		}

		// Draw checkerboard's outline
		int small_distance = tileSize+offset;
		int long_distance_height = tileSize*(height_case+1)+offset;
		int long_distance_width = tileSize*(width_case+1)+offset;
		canvas.drawLine(small_distance, small_distance, small_distance, long_distance_height, colorBlack);
		canvas.drawLine(small_distance, small_distance, long_distance_width, small_distance, colorBlack);
		canvas.drawLine(long_distance_width, long_distance_height, long_distance_width, small_distance, colorBlack);
		canvas.drawLine(long_distance_width, long_distance_height, small_distance, long_distance_height, colorBlack);
	}
}