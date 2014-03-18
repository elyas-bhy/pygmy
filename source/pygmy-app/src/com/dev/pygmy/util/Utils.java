package com.dev.pygmy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.commons.io.input.ClassLoaderObjectInputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dev.pygmy.R;
import com.lib.pygmy.EntityType;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.util.PygmyLoader;

public class Utils {
	
	public static final String BASE_URL = 
			"http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper";

	public static Bitmap getBitmapByType(Resources res, EntityType type) {
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
			break;
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
			break;
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
			bitmap = Bitmap.createScaledBitmap(bitmap, 70, 70, false);
		}
		
		return bitmap;
	}
	
	public static String getGamePath(Context context, String... suffixes) {
		StringBuilder sb = new StringBuilder();
		sb.append(context.getFilesDir().getPath());
		for (String suffix : suffixes) {
			sb.append("/");
			sb.append(suffix);
		}
		return sb.toString();
	}

	public static void saveGame(PygmyGame game, String path) {
		ObjectOutputStream oos = null;
		try {
			File history = new File(path);
			history.getParentFile().createNewFile();
			FileOutputStream fout = new FileOutputStream(history);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(game);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();  
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads a saved game from the device's memory
	 * @return saved match if found, else returns null
	 */
	public static PygmyGame loadGameHistory(String path) {
		File file = new File(path);
		PygmyGame game = null;

		if (file.exists()) {
			try {
				ClassLoaderObjectInputStream ois = new ClassLoaderObjectInputStream(
						PygmyLoader.getClassLoader(), new FileInputStream(file));
				game = (PygmyGame) ois.readObject();
				ois.close();
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}
		return game;
	}

}