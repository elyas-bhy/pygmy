package com.lib.pygmy;

import java.util.Map;

import com.lib.pygmy.view.Tile;

/**
 * stores all the gameframework.game entities of a gameframework.game level:
 * oneStepMoveAll() makes all the entities move ; overlapAll() manages all the
 * interactions between the entities.
 */
public interface GameUniverse {

	public void addGameEntity(GameEntity gameEntity);

	public void removeGameEntity(GameEntity gameEntity);

	public Map<Tile,GameEntity> getGameEntities();

	public void processMove(GameMove move);

}
