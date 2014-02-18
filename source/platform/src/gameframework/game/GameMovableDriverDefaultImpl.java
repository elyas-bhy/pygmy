package gameframework.game;

import gameframework.base.Movable;
import gameframework.base.MoveStrategy;
import gameframework.base.MoveStrategyDefaultImpl;
import gameframework.base.Direction;
import gameframework.base.DirectionDefaultImpl;

public class GameMovableDriverDefaultImpl implements GameMovableDriver {
	protected MoveBlockerChecker moveBlockerChecker;
	protected MoveStrategy moveStrategy;

	public GameMovableDriverDefaultImpl() {
		moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveStrategy = new MoveStrategyDefaultImpl();
	}

	public void setStrategy(MoveStrategy strat) {
		moveStrategy = strat;
	}

	public void setmoveBlockerChecker(MoveBlockerChecker obst) {
		moveBlockerChecker = obst;
	}

	public Direction getSpeedVector(Movable m) {
		Direction possibleSpeedVector;

		possibleSpeedVector = moveStrategy.getSpeedVector();
		if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
			return possibleSpeedVector;
		}

		// If the strategy did not provide a valid vector, try to keep the
		// current vector.
		possibleSpeedVector = m.getSpeedVector();
		if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
			return possibleSpeedVector;
		}

		return DirectionDefaultImpl.createNullVector();
	}
}
