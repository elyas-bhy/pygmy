package com.lib.pygmy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Point;

import com.lib.pygmy.base.Overlappable;
import com.lib.pygmy.view.Tile;

public class PygmyGameUniverse implements GameUniverse {
	
	private Map<Tile,GameEntity> entities;
	private OverlapProcessor overlapProcessor;
	
	public PygmyGameUniverse() {
		entities = new ConcurrentHashMap<Tile,GameEntity>();
	}

	public PygmyGameUniverse(OverlapProcessor processor) {
		this();
		overlapProcessor = processor;
	}
	
	public Map<Tile,GameEntity> getGameEntities() {
		return entities;
	}

	public synchronized void addGameEntity(GameEntity gameEntity) {
		entities.put(gameEntity.getCurrentTile(), gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.addOverlappable((Overlappable) gameEntity);
		}
	}

	public synchronized void removeGameEntity(GameEntity gameEntity) {
		// TODO remove entry by key
		entities.remove(gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.removeOverlappable((Overlappable) gameEntity);
		}
	}

	public void processMove(GameMove move) {
		overlapProcessor.processOverlap(move);
	}

}