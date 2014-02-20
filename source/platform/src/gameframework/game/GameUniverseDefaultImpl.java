package gameframework.game;

import gameframework.base.Overlappable;

import java.awt.Point;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class GameUniverseDefaultImpl implements GameUniverse {
	private ConcurrentHashMap<Point,GameEntity> gameEntities = new ConcurrentHashMap<Point,GameEntity>();
	private OverlapProcessor overlapProcessor;
	private MoveBlockerChecker moveBlockerChecker;

	public Iterator<GameEntity> gameEntities() {
		return gameEntities.values().iterator();
	}
	
	public ConcurrentHashMap<Point,GameEntity> getGameEntities() {
		return gameEntities;
	}

	public GameUniverseDefaultImpl(MoveBlockerChecker obs, OverlapProcessor col) {
		overlapProcessor = col;
		moveBlockerChecker = obs;
	}

	public synchronized void addGameEntity(GameEntity gameEntity) {
		gameEntities.put(gameEntity.getPosition(), gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.addOverlappable((Overlappable) gameEntity);
		}
		if (gameEntity instanceof MoveBlocker) {
			moveBlockerChecker.addMoveBlocker((MoveBlocker) gameEntity);
		}
	}

	public synchronized void removeGameEntity(GameEntity gameEntity) {
		gameEntities.remove(gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.removeOverlappable((Overlappable) gameEntity);
		}
		if (gameEntity instanceof MoveBlocker) {
			moveBlockerChecker.removeMoveBlocker((MoveBlocker) gameEntity);
		}
	}

	public void processOverlap(GameMovable entity) {
		overlapProcessor.processOverlap(entity);
	}

}
