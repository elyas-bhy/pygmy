package com.lib.pygmy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Point;

import com.lib.pygmy.base.Overlappable;

public class PygmyGameUniverse implements GameUniverse {
	
	private Map<Point,GameEntity> gameEntities;
	private OverlapProcessor overlapProcessor;
	
	public PygmyGameUniverse() {
		gameEntities = new ConcurrentHashMap<Point,GameEntity>();
	}

	public PygmyGameUniverse(OverlapProcessor processor) {
		this();
		overlapProcessor = processor;
	}
	
	public Map<Point,GameEntity> getGameEntities() {
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
