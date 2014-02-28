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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;

import com.dev.pygmy.Entity;
import com.dev.pygmy.EntityImpl;
import com.dev.pygmy.R;

/**
 * This class initialise chess game pieces and give
 * game parameters.
 */
@SuppressLint("ResourceAsColor")
public class Chess {

	final static String TAG = "Chess";

	private Paint color1 = null;
	private Paint color2 = null;
	private Entity[] chessEntities = null;
	private HashMap<String, Object> parameters;
	
	private int numberOfSquaresByColumn = 0;
	private int numberOfSquaresByRow = 0;
	private int numberOfEntities = 0;

	/**
	 * Gives the initial parameters of the chess board.
	 * @param context
	 */
	public Chess(Context context) {
		parameters = new HashMap<String, Object>();
		color1 = new Paint();
		// color1.setColor(Color.CYAN);
		color1.setColor(R.color.green_dark);
		color2 = new Paint();
		// color2.setColor(Color.WHITE);
		color2.setColor(R.color.green_light);
		
		numberOfSquaresByRow = 8;
		numberOfSquaresByColumn = 8;
		numberOfEntities = 32;

		chessEntities = new Entity[numberOfEntities];
		setChessPiecesImages(context);

		parameters.put("numberRow", numberOfSquaresByRow);
		parameters.put("numberColumn", numberOfSquaresByColumn);
		parameters.put("numberEntities", numberOfEntities);
		parameters.put("entities", chessEntities);
		parameters.put("color1", color1);
		parameters.put("color2",  color2);
	}

	/**
	 * Returns a HashMap with whole parameters for this game.
	 */
	public HashMap<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * Sets images for each entity in the initial position.
	 * @param context Context of the parent view
	 */
	private void setChessPiecesImages(Context context) {

		// declare each black piece (entity) with the Entity class
		chessEntities[0] = new EntityImpl(context, 1, R.drawable.black_rook);
		chessEntities[1] = new EntityImpl(context, 10, R.drawable.black_knight);
		chessEntities[2] = new EntityImpl(context, 19, R.drawable.black_bishop);
		chessEntities[3] = new EntityImpl(context, 28, R.drawable.black_queen);
		chessEntities[4] = new EntityImpl(context, 37, R.drawable.black_king);
		chessEntities[5] = new EntityImpl(context, 46, R.drawable.black_bishop);
		chessEntities[6] = new EntityImpl(context, 55, R.drawable.black_knight);
		chessEntities[7] = new EntityImpl(context, 64, R.drawable.black_rook);

		for (int index=8, id=2; index<chessEntities.length/2 && id<=65; ++index, id+=9) {
			chessEntities[index] = new EntityImpl(context, id, R.drawable.black_pawn);
		}

		// declare each white piece (entity) with the Entity class
		chessEntities[16] = new EntityImpl(context, 8, R.drawable.white_rook);
		chessEntities[17] = new EntityImpl(context, 17, R.drawable.white_knight);
		chessEntities[18] = new EntityImpl(context, 26, R.drawable.white_bishop);
		chessEntities[19] = new EntityImpl(context, 35, R.drawable.white_queen);
		chessEntities[20] = new EntityImpl(context, 44, R.drawable.white_king);
		chessEntities[21] = new EntityImpl(context, 53, R.drawable.white_bishop);
		chessEntities[22] = new EntityImpl(context, 62, R.drawable.white_knight);
		chessEntities[23] = new EntityImpl(context, 71, R.drawable.white_rook);

		for (int index=24, id=7; index<chessEntities.length && id<=71; ++index, id+=9) {
			chessEntities[index] = new EntityImpl(context, id, R.drawable.white_pawn);
		}
	}
}
