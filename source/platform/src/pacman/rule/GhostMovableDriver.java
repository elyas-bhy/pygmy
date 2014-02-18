package pacman.rule;

import gameframework.base.Movable;
import gameframework.base.Direction;
import gameframework.game.GameMovableDriverDefaultImpl;

public class GhostMovableDriver extends GameMovableDriverDefaultImpl {

	// A modified random strategy that makes ghosts mostly follow the alleys in
	// one direction.
	// Random speed vectors are (1,0) (0,1) (-1,0) (0,-1), but sometimes speed
	// vectors are reinitialized to (0,0) by GameMovableDriver.
	@Override
	public Direction getSpeedVector(Movable m) {
		Direction currentSpeedVector, possibleSpeedVector;

		currentSpeedVector = m.getSpeedVector();
		possibleSpeedVector = super.getSpeedVector(m);

		int nbTries = 10;
		while (true) {
			nbTries--;
			if ((possibleSpeedVector.getDirection().getX() == currentSpeedVector
					.getDirection().getX())
					&& (possibleSpeedVector.getDirection().getY() != -currentSpeedVector
							.getDirection().getY()))
				break;

			if ((possibleSpeedVector.getDirection().getX() != -currentSpeedVector
					.getDirection().getX())
					&& (possibleSpeedVector.getDirection().getY() == currentSpeedVector
							.getDirection().getY()))
				break;

			if ((possibleSpeedVector.getDirection().getX() != currentSpeedVector
					.getDirection().getX())
					&& (possibleSpeedVector.getDirection().getY() != currentSpeedVector
							.getDirection().getY()))
				break;

			possibleSpeedVector = super.getSpeedVector(m);
			if (nbTries < 1)
				break;
		}
		return (possibleSpeedVector);
	}

}