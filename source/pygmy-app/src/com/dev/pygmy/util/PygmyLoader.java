package com.dev.pygmy.util;

import java.lang.reflect.Constructor;

import com.dev.pygmy.PygmyApp;
import com.lib.pygmy.PygmyGame;

import dalvik.system.DexClassLoader;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

public class PygmyLoader {

	public static PygmyGame loadGame(Activity context, String gamePath) {
		DexClassLoader classLoader = new DexClassLoader(gamePath, 
				context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), 
				null, 
				context.getClassLoader());
		
		PygmyGame game = null;
		try {
			Class<?> clazz = classLoader.loadClass("com.client.pygmy.PygmyGameImpl");
			Constructor<?> constructor = clazz.getConstructor(Resources.class);
			game = (PygmyGame) constructor.newInstance(context.getResources());
		} catch (Exception e) {
			PygmyApp.logE(e.getMessage());
		}
		
		return game;
	}

}
