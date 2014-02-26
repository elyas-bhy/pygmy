package com.dev.pygmy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class GameBoardView extends View {

	private String TAG = "GameBoardView";
	
	private int nbCase;
	private Paint mColor1 = null;
	private Paint mColor2 = null;
	private Paint color_black = null;
	
	private static int[][] rectCoord;
	
	public GameBoardView(Context context) {
		super(context);
		nbCase = 8;
		rectCoord = new int[nbCase*(nbCase+1)][4];

		// Colors
		mColor1 = new Paint();
		mColor1.setColor(Color.CYAN);
		mColor2 = new Paint();
		mColor2.setColor(Color.WHITE);
		color_black = new Paint();
	}

	/**
	 * @return the boardRectCoord
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
		
		color_black.setColor(Color.BLACK);
		color_black.setTextSize(20);
		
		int width = getWidth();
		int height = getHeight();
		
		int step = 0, min = 0, offset = 0, coord_X = 0, coord_Y = 0;
		// Minimum size in width and length
		min = Math.min(width, height);

		// Variable number column and line + space for numbering and margin
		step = min / (nbCase+2);
		offset = step / 3;

		int ent=0;
		for(int x = 0; x < nbCase +1; ++x) {
			if (x != 0){
				for(int y = 0 ; y < nbCase+1; ++y, ent++) {
					if (y != 0){
						coord_X = x*step+offset;
						coord_Y = y*step+offset;

						// (0,1) top left corner, (2,3) bottom right corner
						rectCoord[ent][0] = coord_X;
						rectCoord[ent][1] = coord_Y;
						rectCoord[ent][2] = coord_X + step;
						rectCoord[ent][3] = coord_Y + step;
						
						canvas.drawRect(rectCoord[ent][0], 
										rectCoord[ent][1],
										rectCoord[ent][2],
										rectCoord[ent][3], 
										((x + y)%2 != 0)?mColor1:mColor2);
					}
				}
				canvas.drawText(Character.toString((char)('A'-1+x)), 
								x*step+(step/2)-color_black.getTextSize()/2+offset, 
								step/2+color_black.getTextSize()/2+offset, 
								color_black);
				canvas.drawText(Integer.toString(x), 
						        step/2-color_black.getTextSize()/2+offset, 
						        x*step+(step/2)+color_black.getTextSize()/2+offset, 
						        color_black);
			}
		}
				
		int small_distance = step+offset;
		int long_distance = step*(nbCase+1)+offset;
		canvas.drawLine(small_distance, small_distance, small_distance, long_distance, color_black);
		canvas.drawLine(small_distance, small_distance, long_distance, small_distance, color_black);
		canvas.drawLine(long_distance, long_distance, long_distance, small_distance, color_black);
		canvas.drawLine(long_distance, long_distance, small_distance, long_distance, color_black);
	}
}