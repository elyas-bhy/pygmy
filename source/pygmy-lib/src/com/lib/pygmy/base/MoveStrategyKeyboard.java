package com.lib.pygmy.base;

import android.graphics.Point;
import android.view.KeyEvent;

import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameMove;
import com.lib.pygmy.MovableEntity;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link Direction speed vectors} based on what the user typed.
 */
public class MoveStrategyKeyboard implements MoveStrategy {
	protected Direction speedVector = new DirectionDefaultImpl(new Point(0, 0));

	private GameLevel ml;
	private MovableEntity me;

	public MoveStrategyKeyboard(MovableEntity me, GameLevel ml) {
		this.me = me;
		this.ml = ml;
	}

	public Direction getSpeedVector() {
		return speedVector;
	}

	public void keyPressed() {
		switch (1) {
		case KeyEvent.ACTION_DOWN:
			speedVector.setDirection(new Point(16, 0));
			break;
		case KeyEvent.ACTION_UP:
			speedVector.setDirection(new Point(-16, 0));
			break;
		}

		GameMove move = new GameMove();
		move.setEntity(me);
		move.setMove(speedVector.getDirection());
		ml.tryMove(move);
	}
}
