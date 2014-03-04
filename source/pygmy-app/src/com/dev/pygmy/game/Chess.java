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

import android.content.Context;
import android.graphics.Paint;

import com.dev.pygmy.Entity;
import com.dev.pygmy.EntityImpl;
import com.dev.pygmy.R;

/**
 * This class initialise chess game pieces and give
 * game parameters.
 */
public class Chess {

	final static String TAG = "Chess";

	private Paint[] colors;
	private Entity[] chessEntities = null;
	private HashMap<String, Object> parameters;
	
	private int numberOfSquaresByColumn = 0;
	private int numberOfSquaresByRow = 0;
	private int numberOfPieces = 0;

	/**
	 * Gives the initial parameters of the chess board.
	 * @param context
	 */
	public Chess(Context context) {
		parameters = new HashMap<String, Object>();
		
		colors = new Paint[2];
		colors[0] = new Paint();
		colors[1] = new Paint();
		colors[0].setColor(context.getResources().getColor(R.color.green_dark));
		colors[1].setColor(context.getResources().getColor(R.color.green_light));

		numberOfSquaresByRow = 8;
		numberOfSquaresByColumn = 8;
		numberOfPieces = 32;
		
		//chessEntities = new Entity[numberOfSquaresByColumn*numberOfSquaresByRow];
		chessEntities = new Entity[numberOfPieces];
		setChessPiecesImages(context);

		parameters.put("numberRow", numberOfSquaresByRow);
		parameters.put("numberColumn", numberOfSquaresByColumn);
		parameters.put("numberPieces", numberOfPieces);
		parameters.put("entities", chessEntities);
		parameters.put("colors", colors);
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
		
		chessEntities[0] = new EntityImpl(context, 0, 0, R.drawable.black_rook);
		chessEntities[1] = new EntityImpl(context, 0, 1, R.drawable.black_knight);
		chessEntities[2] = new EntityImpl(context, 0, 2, R.drawable.black_bishop);
		chessEntities[3] = new EntityImpl(context, 0, 3, R.drawable.black_queen);
		chessEntities[4] = new EntityImpl(context, 0, 4, R.drawable.black_king);
		chessEntities[5] = new EntityImpl(context, 0, 5, R.drawable.black_bishop);
		chessEntities[6] = new EntityImpl(context, 0, 6, R.drawable.black_knight);
		chessEntities[7] = new EntityImpl(context, 0, 7, R.drawable.black_rook);

		for (int index=0; index<8; index++) {
			chessEntities[8+index] = new EntityImpl(context, 1, index, R.drawable.black_pawn);
		}
		
		// declare each white piece (entity) with the Entity class
		chessEntities[24] = new EntityImpl(context, 7, 0, R.drawable.white_rook);
		chessEntities[25] = new EntityImpl(context, 7, 1, R.drawable.white_knight);
		chessEntities[26] = new EntityImpl(context, 7, 2, R.drawable.white_bishop);
		chessEntities[27] = new EntityImpl(context, 7, 3, R.drawable.white_queen);
		chessEntities[28] = new EntityImpl(context, 7, 4, R.drawable.white_king);
		chessEntities[29] = new EntityImpl(context, 7, 5, R.drawable.white_bishop);
		chessEntities[30] = new EntityImpl(context, 7, 6, R.drawable.white_knight);
		chessEntities[31] = new EntityImpl(context, 7, 7, R.drawable.white_rook);

		for (int index=0; index<8; index++) {
			chessEntities[16+index] = new EntityImpl(context, 6, index, R.drawable.white_pawn);
		}
	}
}
