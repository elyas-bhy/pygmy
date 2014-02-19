package com.dev.pygmy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class GameBoardView extends View {

	public GameBoardView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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

		// If layout don't specify dimension, view take screen's half
		if(mode == MeasureSpec.UNSPECIFIED)
			return screenDim/2;
		else
			// Else, she take the layout's size
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
		Log.d("", "draw");
		int nb_case = 19;

		// Colors
		Paint color1 = new Paint();
		color1.setColor(Color.BLACK);
		Paint color2 = new Paint(Color.WHITE);
		color2.setColor(Color.WHITE);

		draw_checkerboard(canvas, nb_case, color1, color2);
	}

	private void draw_checkerboard(Canvas canvas, int nb_case, Paint color1, Paint color2){
		Paint color_black = new Paint();
		color_black.setColor(Color.BLACK);
		color_black.setTextSize(20);

		int width = getWidth();
		int height = getHeight();

		int step = 0, min = 0, offset = 0, coord_i = 0, coord_j = 0;
		// Minimum taille in width and length
		min = Math.min(width, height);

		// Variable number column and line + space for numbering and marge
		step = min / (nb_case+2);
		offset = step / 3;

		for(int i = 0; i < nb_case +1 ; ++i) {
			if (i != 0){
				for(int j = 0 ; j < nb_case +1; ++j) {
					if (j != 0){
						// Alternance
						coord_i = i*step+offset;
						coord_j = j*step+offset;
						canvas.drawRect(coord_i, coord_j, coord_i + step, coord_j + step, ((i + j)%2 != 0)?color1:color2);
					}
				}
				canvas.drawText(Character.toString((char)('A'-1+i)), i*step+(step/2)-color_black.getTextSize()/2+offset, step/2+color_black.getTextSize()/2+offset, color_black);		
				canvas.drawText(Integer.toString(i), step/2-color_black.getTextSize()/2+offset, i*step+(step/2)+color_black.getTextSize()/2+offset, color_black);		
			}
		}

		int small_distance = step+offset;
		int long_distance = step*(nb_case+1)+offset;
		canvas.drawLine(small_distance, small_distance, small_distance, long_distance, color_black);
		canvas.drawLine(small_distance, small_distance, long_distance, small_distance, color_black);
		canvas.drawLine(long_distance, long_distance, long_distance, small_distance, color_black);
		canvas.drawLine(long_distance, long_distance, small_distance, long_distance, color_black);
	}

}