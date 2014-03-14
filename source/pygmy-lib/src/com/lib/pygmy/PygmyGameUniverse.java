package com.lib.pygmy;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Point;

import com.lib.pygmy.base.Overlappable;
import com.lib.pygmy.view.Tile;

import dalvik.system.DexClassLoader;

public class PygmyGameUniverse implements GameUniverse {
	
	private GameLevel level;
	private Map<Tile,GameEntity> entities;
	private OverlapProcessor overlapProcessor;
	
	public PygmyGameUniverse() {
		entities = new ConcurrentHashMap<Tile,GameEntity>();
	}

	public PygmyGameUniverse(GameLevel level, OverlapProcessor processor) {
		this();
		this.level = level;
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
	public void updateData(TurnData ddata) {
		// FIXME clear entries from overlapProcessor
		entities.clear();
		String[] entities = ddata.data.split(";");
		for (String entity : entities) {
			addEntityFromString(entity.split(":"));
		}
	}
	
	private void addEntityFromString(String[] attrs) {
		GameEntity entity = null;
		try {
			DexClassLoader classLoader = PygmyLoader.getClassLoader();
			Class<?> clazz = classLoader.loadClass(attrs[0]);
			Constructor<?> constructor = clazz.getConstructor(
					GameLevel.class, Player.class, EntityType.class, Point.class);
			
			entity = (GameEntity) constructor.newInstance(
							this.level, 
							new Player(attrs[1]), 
							EntityType.valueOf(attrs[2]), 
							new Point(Integer.parseInt(attrs[3]), Integer.parseInt(attrs[4])));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (entity != null) {
			addGameEntity(entity);
		}
	}
	
	@Override
	public String getState() {
		StringBuilder sb = new StringBuilder();
		for (GameEntity entity : entities.values()) {
			sb.append(entity.getClass().getName());
			sb.append(":");
			sb.append(entity.getPlayer().getId());
			sb.append(":");
			sb.append(entity.getType());
			sb.append(":");
			sb.append(entity.getCurrentTile().getPosition().x);
			sb.append(":");
			sb.append(entity.getCurrentTile().getPosition().y);
			sb.append(";");
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

}