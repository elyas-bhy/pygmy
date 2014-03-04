package com.lib.pygmy;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Point;

import com.lib.pygmy.base.Overlappable;

public class GameUniverseDefaultImpl implements GameUniverse {
	
	private ConcurrentHashMap<Point,GameEntity> gameEntities;
	private OverlapProcessor overlapProcessor;
	
	public GameUniverseDefaultImpl() {
		gameEntities = new ConcurrentHashMap<Point,GameEntity>();
	}

	public GameUniverseDefaultImpl(OverlapProcessor olp) {
		this();
		overlapProcessor = olp;
	}
	
	public Iterator<GameEntity> gameEntities() {
		return gameEntities.values().iterator();
	}
	
	public ConcurrentHashMap<Point,GameEntity> getGameEntities() {
		return gameEntities;
	}

	public synchronized void addGameEntity(GameEntity gameEntity) {
		gameEntities.put(gameEntity.getPosition(), gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.addOverlappable((Overlappable) gameEntity);
		}
	}

	public synchronized void removeGameEntity(GameEntity gameEntity) {
		gameEntities.remove(gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.removeOverlappable((Overlappable) gameEntity);
		}
	}

	public void processMove(GameMove move) {
		overlapProcessor.processOverlap(move);
	}

}
