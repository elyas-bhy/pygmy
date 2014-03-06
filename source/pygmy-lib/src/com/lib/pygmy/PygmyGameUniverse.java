package com.lib.pygmy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Point;

import com.lib.pygmy.base.Overlappable;

public class PygmyGameUniverse implements GameUniverse {
	
	private Map<Point,GameEntity> entities;
	private OverlapProcessor overlapProcessor;
	
	public PygmyGameUniverse() {
		entities = new ConcurrentHashMap<Point,GameEntity>();
	}

	public PygmyGameUniverse(OverlapProcessor processor) {
		this();
		overlapProcessor = processor;
	}
	
	public Map<Point,GameEntity> getGameEntities() {
		return entities;
	}

	public synchronized void addGameEntity(GameEntity gameEntity) {
		entities.put(gameEntity.getPosition(), gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.addOverlappable((Overlappable) gameEntity);
		}
	}

	public synchronized void removeGameEntity(GameEntity gameEntity) {
		entities.remove(gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.removeOverlappable((Overlappable) gameEntity);
		}
	}

	public void processMove(GameMove move) {
		overlapProcessor.processOverlap(move);
	}

}