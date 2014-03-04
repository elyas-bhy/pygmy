package com.lib.pygmy;

import android.graphics.Point;

import com.lib.pygmy.base.Movable;

public abstract class GameMovable implements Movable {

	private Player player;
	private Point source = new Point();
	private Point destination = new Point();
	
	public Point getPosition() {
		return source;
	}
	
	public void setPosition(Point p) {
		source.x = p.x;
		source.y = p.y;
	}
	
	@Override
	public Point getDestination() {
		return new Point(destination.x, destination.y);
	}

	@Override
	public void setDestination(Point p) {
		destination.x = p.x;
		destination.y = p.y;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void oneStepMove(Point p) {
		destination.x = p.x;
		destination.y = p.y;
		source.x = destination.x;
		source.y = destination.y;
		oneStepMoveAddedBehavior();
	}
	
	public abstract boolean isLegalMove(GameMove move);
	
	public abstract void oneStepMoveAddedBehavior();
	
}
