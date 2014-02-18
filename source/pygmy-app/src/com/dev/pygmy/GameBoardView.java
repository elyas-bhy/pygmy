package com.dev.pygmy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
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
		int nb_case = 8;
		
		Paint color_black = new Paint();
		color_black.setColor(Color.BLACK);
		color_black.setTextSize(20);
		
		// Color 1
		Paint color1 = new Paint();
		color1.setColor(Color.BLACK);
		Paint color2 = new Paint();
		color2.setColor(Color.WHITE);

		// Largeur de la vue
		int width = getWidth();
		// Hauteur de la vue
		int height = getHeight();

		int step = 0, min = 0;
		// Minimum taille in width and length
		min = Math.min(width, height);
		int marge = min/nb_case;
		min = min - marge;

		// Variable number column and line + space for numbering
		step = min / nb_case;

		float text_position = step/2+color_black.getTextSize()/2;
		
		boolean switchColor = true;
		for(int i = 0 ; i < min ; i += step) {
			if (i != 0){
				for(int j = 0 ; j < min ; j += step) {
					if (j != 0){
						if(switchColor)
							canvas.drawRect(i, j, i + step, j + step, color1);
						else
							canvas.drawRect(i, j, i + step, j + step, color2);
						 // Change color (line)
						switchColor = !switchColor;
						

					}
				}
				// Change color (column)
				switchColor = !switchColor;
				System.out.println("Text size = " + color_black.getTextSize() + " Slide size " + min);
				canvas.drawText(Integer.toString(i/step), i+(step/2)-color_black.getTextSize()/2, text_position, color_black);		
				canvas.drawText(Integer.toString(i/step), step/2-color_black.getTextSize()/2, i+(step/2)+color_black.getTextSize()/2, color_black);		
			}		
			
		}
		
		
		canvas.drawLine(step, step, step, step*(nb_case+1), color_black);
		canvas.drawLine(step, step, step*(nb_case+1), step, color_black);
		canvas.drawLine(step*(nb_case+1), step*(nb_case+1), step*(nb_case+1), step, color_black);
		canvas.drawLine(step*(nb_case+1), step*(nb_case+1), step, step*(nb_case+1), color_black);

		
	}
}