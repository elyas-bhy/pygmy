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

import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputFilter.LengthFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * 	This class represents the grid of the board.
 */
public class GameBoardView extends View {

	private String TAG = "GameBoardView";
	
	private int nbCase;
	private Paint color1 = null;
	private Paint color2 = null;
	private Paint colorBlack = null;
	
	private static int[][] rectCoord;
	
	/**
	 * Default constructor.
	 */
	public GameBoardView(Context context) {
		super(context);
	}
	
	/**
	 * 
	 * @param context			Context parent.
	 * @param gameParameters	A map with board parameters. 
	 */
	public GameBoardView(Context context, HashMap<String, Object> gameParameters) {
		super(context);
		
		nbCase = (Integer)gameParameters.get("numberRow");
		rectCoord = new int[nbCase*(nbCase+1)][4];

		// Colors
		Paint[] colors = (Paint[])gameParameters.get("colors");
		color1 = colors[0];
		color2 = colors[1];
		colorBlack = new Paint();
	}

	/**
	 * @return A multidimensional array with the coordinates of each square of the board.
	 */
	public static int[][] getRectCoord() {
		return rectCoord;
	}

	/**
	 * Return a good dimension in an axe
	 * @param spec - Mesure
	 * @param screenDim - Screen Dimension
	 * @return good size
	 */
	private int singleMeasure(int spec, int screenDim) {
		int mode = MeasureSpec.getMode(spec);
		int size = MeasureSpec.getSize(spec);

		// If layout don't specify dimension, view takes screen's half
		if(mode == MeasureSpec.UNSPECIFIED)
			return screenDim/2;
		else
			// Else, it takes the layout's size
			return size;
	}

	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		// Screen Dimension
		DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
		// Width
		int screenWidth = metrics.widthPixels;
		// length
		int screenHeight = metrics.heightPixels;

		int returnWidth = singleMeasure(widthMeasureSpec, screenWidth);
		int returnHeight = singleMeasure(heightMeasureSpec, screenHeight);

		setMeasuredDimension(returnWidth, returnHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
		int dim1 = nbCase;
		int dim2 = nbCase;
		draw_checkerboard(canvas, dim1, dim2, color1, color2);
	}
		
	private void draw_checkerboard(Canvas canvas, int dim1, int dim2, Paint color1, Paint color2){

		colorBlack.setColor(Color.BLACK);
		colorBlack.setTextSize(20);
		int width_case = Math.min(dim1, dim2);
		int height_case = Math.max(dim1, dim2);
		
		
		int width = getWidth();
		int height = getHeight();
		
		int case_size = 0, offset = 0, coord_y = 0, coord_x = 0;
		// Minimum size in width and length
		// Minimum size in width and length

		// One case distance
		case_size = Math.min(width / (width_case+2), height / (height_case+2));
		// Marge based to case_size
		offset = case_size / 3;

		int ent=0;
		for(int y = 0; y < width_case +1 ; ++y) {
			if(y != 0){
			for(int x = 0 ; x < height_case +1; ++x, ++ent) {
				if(x != 0){
						
						coord_y = y*case_size+offset;
						coord_x = x*case_size+offset;
						if (y == 1)
							canvas.drawText(Integer.toString(x), case_size/2-colorBlack.getTextSize()/2+offset, x*case_size+(case_size/2)+colorBlack.getTextSize()/2+offset, colorBlack);		

						// (0,1) top left corner, (2,3) bottom right corner
						rectCoord[ent][0] = coord_x;
						rectCoord[ent][1] = coord_y;
						rectCoord[ent][2] = coord_x + case_size;
						rectCoord[ent][3] = coord_y + case_size;
						
						canvas.drawRect(rectCoord[ent][0], 
										rectCoord[ent][1],
										rectCoord[ent][2],
										rectCoord[ent][3], 
										((y + x)%2 != 0)?color1:color2);
					}
				}
				canvas.drawText(Character.toString((char)('A'-1+y)), y*case_size+(case_size/2)-colorBlack.getTextSize()/2+offset, case_size/2+colorBlack.getTextSize()/2+offset, colorBlack);		
				canvas.drawText(Integer.toString(y), case_size/2-colorBlack.getTextSize()/2+offset, y*case_size+(case_size/2)+colorBlack.getTextSize()/2+offset, colorBlack);		
								
			}
		}
				
		// Draw checkerboard's outline
		int small_distance = case_size+offset;
		int long_distance_height = case_size*(height_case+1)+offset;
		int long_distance_width = case_size*(width_case+1)+offset;
		canvas.drawLine(small_distance, small_distance, small_distance, long_distance_height, colorBlack);
		canvas.drawLine(small_distance, small_distance, long_distance_width, small_distance, colorBlack);
		canvas.drawLine(long_distance_width, long_distance_height, long_distance_width, small_distance, colorBlack);
		canvas.drawLine(long_distance_width, long_distance_height, small_distance, long_distance_height, colorBlack);
	}
}