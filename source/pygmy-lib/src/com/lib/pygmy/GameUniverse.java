package com.lib.pygmy;

import android.graphics.Point;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * stores all the gameframework.game entities of a gameframework.game level:
 * oneStepMoveAll() makes all the entities move ; overlapAll() manages all the
 * interactions between the entities.
 */
public interface GameUniverse {

	public void addGameEntity(GameEntity gameEntity);

	public void removeGameEntity(GameEntity gameEntity);

	public Iterator<GameEntity> gameEntities();

	public ConcurrentHashMap<Point,GameEntity> getGameEntities();

	public void processMove(GameMove move);

}
