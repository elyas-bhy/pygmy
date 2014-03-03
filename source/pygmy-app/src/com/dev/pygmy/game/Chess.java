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
import android.graphics.Color;
import android.graphics.Paint;

import com.dev.pygmy.Entity;
import com.dev.pygmy.EntityImpl;
import com.dev.pygmy.R;

/**
 * This class initialise chess game pieces and give
 * game parameters.
 */
//@SuppressLint("ResourceAsColor")
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
		colors[0].setColor(Color.CYAN);
		colors[1].setColor(Color.WHITE);
		
		//colors[0].setColor(R.color.green_dark);
		//colors[1].setColor(R.color.green_light);

		numberOfSquaresByRow = 8;
		numberOfSquaresByColumn = 8;

		numberOfPieces = 32;
		//chessEntities = new Entity[numberOfPieces];
		//chessEntities = new Entity[numberOfSquaresByColumn*numberOfSquaresByRow];
		chessEntities = new Entity[72];
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
		chessEntities[1] = new EntityImpl(context, 1, R.drawable.black_rook);
		chessEntities[2] = new EntityImpl(context, 2, R.drawable.black_knight);
		chessEntities[3] = new EntityImpl(context, 3, R.drawable.black_bishop);
		chessEntities[4] = new EntityImpl(context, 4, R.drawable.black_queen);
		chessEntities[5] = new EntityImpl(context, 5, R.drawable.black_king);
		chessEntities[6] = new EntityImpl(context, 6, R.drawable.black_bishop);
		chessEntities[7] = new EntityImpl(context, 7, R.drawable.black_knight);
		chessEntities[8] = new EntityImpl(context, 8, R.drawable.black_rook);

		for (int id=10; id<=17; ++id) {
			chessEntities[id] = new EntityImpl(context, id, R.drawable.black_pawn);
		}
		
		// declare each white piece (entity) with the Entity class
		chessEntities[64] = new EntityImpl(context, 64, R.drawable.white_rook);
		chessEntities[65] = new EntityImpl(context, 65, R.drawable.white_knight);
		chessEntities[66] = new EntityImpl(context, 66, R.drawable.white_bishop);
		chessEntities[67] = new EntityImpl(context, 67, R.drawable.white_queen);
		chessEntities[68] = new EntityImpl(context, 68, R.drawable.white_king);
		chessEntities[69] = new EntityImpl(context, 69, R.drawable.white_bishop);
		chessEntities[70] = new EntityImpl(context, 70, R.drawable.white_knight);
		chessEntities[71] = new EntityImpl(context, 71, R.drawable.white_rook);

		for (int id=55; id<=62; ++id) {
			chessEntities[id] = new EntityImpl(context, id, R.drawable.white_pawn);
		}

		/*
		// declare each black piece (entity) with the Entity class
		chessEntities[0] = new EntityImpl(context, 1, R.drawable.black_rook);
		chessEntities[1] = new EntityImpl(context, 10, R.drawable.black_knight);
		chessEntities[2] = new EntityImpl(context, 19, R.drawable.black_bishop);
		chessEntities[3] = new EntityImpl(context, 28, R.drawable.black_queen);
		chessEntities[4] = new EntityImpl(context, 37, R.drawable.black_king);
		chessEntities[5] = new EntityImpl(context, 46, R.drawable.black_bishop);
		chessEntities[6] = new EntityImpl(context, 55, R.drawable.black_knight);
		chessEntities[7] = new EntityImpl(context, 64, R.drawable.black_rook);

		for (int index=8, id=2; index<16 && id<=65; ++index, id+=9) {
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
		*/
	}
}
