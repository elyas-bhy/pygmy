/*
 * Copyright (C) 2014 Pygmy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lib.pygmy;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lib.pygmy.base.Overlappable;
import com.lib.pygmy.util.Point;
import com.lib.pygmy.util.PygmyLoader;
import com.lib.pygmy.util.TurnData;

import dalvik.system.DexClassLoader;

/**
 * Basic implementation of {@link GameUniverse}
 * @author Pygmy
 *
 */
public class PygmyGameUniverse implements GameUniverse, Serializable {
	
	private static final long serialVersionUID = -6288249985123122976L;
	
	/**
	 * Reference to the parent level
	 */
	private GameLevel level;
	
	/**
	 * Maps the level's entities by their current tiles
	 */
	private Map<Tile,GameEntity> entities;
	
	/**
	 * Handles any overlap of two entities
	 */
	private OverlapProcessor overlapProcessor;

	public PygmyGameUniverse(GameLevel level, OverlapProcessor processor) {
		this.entities = new ConcurrentHashMap<Tile,GameEntity>();
		this.level = level;
		this.overlapProcessor = processor;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GameEntity getEntityAt(Tile tile) {
		return entities.get(tile);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<GameEntity> getGameEntities() {
		return Collections.unmodifiableCollection(entities.values());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void addGameEntity(GameEntity gameEntity) {
		entities.put(gameEntity.getCurrentTile(), gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.addOverlappable((Overlappable) gameEntity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void removeGameEntity(GameEntity gameEntity) {
		entities.remove(gameEntity.getCurrentTile());
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.removeOverlappable((Overlappable) gameEntity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processMove(GameMove move) {
		overlapProcessor.processOverlap(move);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateData(TurnData data) {
		this.entities.clear();
		this.overlapProcessor.clear();
		String[] entities = data.state.split(";");
		for (String entity : entities) {
			addEntityFromString(entity.split(":"));
		}
	}
	
	/**
	 * Instanciates a serialized entity using reflection
	 * @param attrs the attributes of the entity
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
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