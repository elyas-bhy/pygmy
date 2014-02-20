package com.dev.pygmy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputFilter.LengthFilter;
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
		int dim1 = 5;
		int dim2 = 9;

		// Colors
		Paint color1 = new Paint();
		color1.setColor(Color.BLACK);
		Paint color2 = new Paint(Color.WHITE);
		color2.setColor(Color.WHITE);


		draw_checkerboard(canvas, dim1, dim2, color1, color2);
	}

	private void draw_checkerboard(Canvas canvas, int dim1, int dim2, Paint color1, Paint color2){

		int width_case = Math.min(dim1, dim2);
		int height_case = Math.max(dim1, dim2);

		Paint color_black = new Paint();
		color_black.setColor(Color.BLACK);
		color_black.setTextSize(20);

		int width = getWidth();
		int height = getHeight();

		int case_size = 0, offset = 0, coord_i = 0, coord_j = 0;

		// One case distance
		case_size = Math.min(width / (width_case+2), height / (height_case+2));
		// Marge based to case_size
		offset = case_size / 3;

		for(int i = 0; i < width_case +1 ; ++i) {
			if (i != 0){
				for(int j = 0 ; j < height_case +1; ++j) {
					if (j != 0){
						coord_i = i*case_size+offset;
						coord_j = j*case_size+offset;
						// Draw case
						canvas.drawRect(coord_i, coord_j, coord_i + case_size, coord_j + case_size, ((i + j)%2 != 0)?color1:color2);
						if (i == 1)
							canvas.drawText(Integer.toString(j), case_size/2-color_black.getTextSize()/2+offset, j*case_size+(case_size/2)+color_black.getTextSize()/2+offset, color_black);		

					}
				}
				canvas.drawText(Character.toString((char)('A'-1+i)), i*case_size+(case_size/2)-color_black.getTextSize()/2+offset, case_size/2+color_black.getTextSize()/2+offset, color_black);		
			}
		}

		// Draw checkerboard's outline
		int small_distance = case_size+offset;
		int long_distance_height = case_size*(height_case+1)+offset;
		int long_distance_width = case_size*(width_case+1)+offset;
		canvas.drawLine(small_distance, small_distance, small_distance, long_distance_height, color_black);
		canvas.drawLine(small_distance, small_distance, long_distance_width, small_distance, color_black);
		canvas.drawLine(long_distance_width, long_distance_height, long_distance_width, small_distance, color_black);
		canvas.drawLine(long_distance_width, long_distance_height, small_distance, long_distance_height, color_black);

	}

}