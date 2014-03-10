package com.lib.pygmy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	
	@Override
	public Map<Tile,GameEntity> getGameEntities() {
		return entities;
	}

	@Override
	public synchronized void addGameEntity(GameEntity gameEntity) {
		entities.put(gameEntity.getCurrentTile(), gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.addOverlappable((Overlappable) gameEntity);
		}
	}

	@Override
	public synchronized void removeGameEntity(GameEntity gameEntity) {
		// TODO remove entry by key
		entities.remove(gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.removeOverlappable((Overlappable) gameEntity);
		}
	}

	@Override
	public void processMove(GameMove move) {
		overlapProcessor.processOverlap(move);
	}
	
	@Override
	public String getState() {
		StringBuilder sb = new StringBuilder();
		for (GameEntity entity : entities.values()) {
			sb.append(entity.getClass().getName());
			sb.append(":");
			sb.append(entity.getPlayer().getId());
			sb.append(":");
			sb.append(entity.getResourceId());
			sb.append(":");
			sb.append(entity.getCurrentTile().getPosition().x);
			sb.append(":");
			sb.append(entity.getCurrentTile().getPosition().y);
			sb.append("|");
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

}