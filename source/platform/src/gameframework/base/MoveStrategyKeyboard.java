package gameframework.base;

import gameframework.game.GameLevel;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import my.first.game.MovableEntity;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link Direction speed vectors} based on what the user typed.
 */
public class MoveStrategyKeyboard extends KeyAdapter implements MoveStrategy {
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

	@Override
	public void keyPressed(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_RIGHT:
			speedVector.setDirection(new Point(16, 0));
			break;
		case KeyEvent.VK_LEFT:
			speedVector.setDirection(new Point(-16, 0));
			break;
		case KeyEvent.VK_UP:
			speedVector.setDirection(new Point(0, -16));
			break;
		case KeyEvent.VK_DOWN:
			speedVector.setDirection(new Point(0, 16));
			break;
		}

		ml.tryMove(me, speedVector);
	}
}
