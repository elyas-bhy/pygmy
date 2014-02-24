package gameframework.base;

public class MoveStrategyDefaultImpl implements MoveStrategy {
	
	public Direction getSpeedVector() {
		return DirectionDefaultImpl.createNullVector();
	}
}
