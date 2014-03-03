package com.lib.pygmy;

import com.lib.pygmy.base.Overlappable;

import java.awt.Point;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class GameUniverseDefaultImpl implements GameUniverse {
	
	private ConcurrentHashMap<Point,GameEntity> gameEntities;
	private OverlapProcessor overlapProcessor;
	//private MoveBlockerChecker moveBlockerChecker;
	
	public GameUniverseDefaultImpl() {
		gameEntities = new ConcurrentHashMap<Point,GameEntity>();
	}

	public GameUniverseDefaultImpl(OverlapProcessor olp) {
		this();
		overlapProcessor = olp;
	}
	
	/*public GameUniverseDefaultImpl(MoveBlockerChecker mbc, OverlapProcessor olp) {
		this(olp);
		moveBlockerChecker = mbc;
	}*/
	
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
		/*if (gameEntity instanceof MoveBlocker) {
			moveBlockerChecker.addMoveBlocker((MoveBlocker) gameEntity);
		}*/
	}

	public synchronized void removeGameEntity(GameEntity gameEntity) {
		gameEntities.remove(gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.removeOverlappable((Overlappable) gameEntity);
		}
		/*if (gameEntity instanceof MoveBlocker) {
			moveBlockerChecker.removeMoveBlocker((MoveBlocker) gameEntity);
		}*/
	}

	public void processMove(GameMove move) {
		overlapProcessor.processOverlap(move);
		//moveBlockerChecker.moveValidation(move);
	}

}
