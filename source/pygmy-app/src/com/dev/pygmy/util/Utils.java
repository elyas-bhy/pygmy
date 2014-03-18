package com.dev.pygmy.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dev.pygmy.R;
import com.lib.pygmy.EntityType;

public class Utils {

	public static Bitmap getBitmapByType(Resources res, EntityType type, int scale) {
		int resId;
		switch (type) {
		case BLACK_ROOK:
			resId = R.drawable.black_rook;
			break;
		case BLACK_KNIGHT:
			resId = R.drawable.black_knight;
			break;
		case BLACK_BISHOP:
			resId = R.drawable.black_bishop;
			break;
		case BLACK_QUEEN:
			resId = R.drawable.black_queen;
			break;
		case BLACK_KING:
			resId = R.drawable.black_king;
		case BLACK_PAWN:
			resId = R.drawable.black_pawn;
			break;
			
		case WHITE_ROOK:
			resId = R.drawable.white_rook;
			break;
		case WHITE_KNIGHT:
			resId = R.drawable.white_knight;
			break;
		case WHITE_BISHOP:
			resId = R.drawable.white_bishop;
			break;
		case WHITE_QUEEN:
			resId = R.drawable.white_queen;
			break;
		case WHITE_KING:
			resId = R.drawable.white_king;
		case WHITE_PAWN:
			resId = R.drawable.white_pawn;
			break;
		default:
			resId = -1;
			break;
		}

		Bitmap bitmap = null;
		if (resId != -1) {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeResource(res, resId);
			bitmap = Bitmap.createScaledBitmap(bitmap, scale, scale, false);
		}
		
		return bitmap;
	}
	
	public static String getGamePath(Context mC, String gameID, String gameVersion) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(mC.getFilesDir().getPath());
		sb.append("/");
		sb.append(gameID);
		sb.append("/");
		sb.append(gameVersion);
		sb.append("/");
		sb.append("game.jar");
		
		return sb.toString();
	}

}
