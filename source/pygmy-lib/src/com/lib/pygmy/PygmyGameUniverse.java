package com.lib.pygmy;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lib.pygmy.base.Overlappable;
import com.lib.pygmy.util.Point;
import com.lib.pygmy.util.PygmyLoader;
import com.lib.pygmy.util.TurnData;

import dalvik.system.DexClassLoader;

public class PygmyGameUniverse implements GameUniverse, Serializable {
	
	private static final long serialVersionUID = -6288249985123122976L;
	
	private GameLevel level;
	private Map<Tile,GameEntity> entities;
	private OverlapProcessor overlapProcessor;

	public PygmyGameUniverse(GameLevel level, OverlapProcessor processor) {
		this.entities = new ConcurrentHashMap<Tile,GameEntity>();
		this.level = level;
		this.overlapProcessor = processor;
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
		entities.remove(gameEntity.getCurrentTile());
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.removeOverlappable((Overlappable) gameEntity);
		}
	}

	@Override
	public void processMove(GameMove move) {
		overlapProcessor.processOverlap(move);
	}
	
	@Override
	public void updateData(TurnData data) {
		this.entities.clear();
		this.overlapProcessor.clear();
		String[] entities = data.state.split(";");
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
					GameLevel.class, String.class, EntityType.class, Point.class);
			
			entity = (GameEntity) constructor.newInstance(
							this.level, 
							attrs[1], 
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
			sb.append(entity.getPlayerId());
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