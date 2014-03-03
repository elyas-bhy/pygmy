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
		
		colorBlack.setColor(Color.BLACK);
		colorBlack.setTextSize(20);
		
		int width = getWidth();
		int height = getHeight();
		
		int step = 0, min = 0, offset = 0, coordX = 0, coordY = 0;
		
		// Minimum size in width and length
		min = Math.min(width, height);

		// Variable number column and line + space for numbering and margin
		step = min / (nbCase+2);
		offset = step / 3;

		int ent=0;
		for(int y = 0; y < nbCase +1; ++y) {
			if (y != 0){
				for(int x = 0 ; x < nbCase+1; ++x, ent++) {
					if (x != 0){
						coordX = x*step+offset;
						coordY = y*step+offset;

						// (0,1) top left corner, (2,3) bottom right corner
						rectCoord[ent][0] = coordX;
						rectCoord[ent][1] = coordY;
						rectCoord[ent][2] = coordX + step;
						rectCoord[ent][3] = coordY + step;
						
						canvas.drawRect(rectCoord[ent][0], 
										rectCoord[ent][1],
										rectCoord[ent][2],
										rectCoord[ent][3], 
										((x + y)%2 != 0)?color1:color2);
					}
				}
				canvas.drawText(Character.toString((char)('A'-1+y)), 
								y*step+(step/2)-colorBlack.getTextSize()/2+offset, 
								step/2+colorBlack.getTextSize()/2+offset, 
								colorBlack);
				canvas.drawText(Integer.toString(y), 
						        step/2-colorBlack.getTextSize()/2+offset, 
						        y*step+(step/2)+colorBlack.getTextSize()/2+offset, 
						        colorBlack);
			}
		}
				
		int small_distance = step+offset;
		int long_distance = step*(nbCase+1)+offset;
		canvas.drawLine(small_distance, small_distance, small_distance, long_distance, colorBlack);
		canvas.drawLine(small_distance, small_distance, long_distance, small_distance, colorBlack);
		canvas.drawLine(long_distance, long_distance, long_distance, small_distance, colorBlack);
		canvas.drawLine(long_distance, long_distance, small_distance, long_distance, colorBlack);
	}
}