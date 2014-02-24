package gameframework.game;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;

import java.awt.Graphics;

public abstract class MovableEntity extends GameMovable
	implements Drawable, GameEntity, Overlappable {
	
	private PygmyGameLevel level;
	protected SpriteManager spriteManager = null;
	public static final int RENDERING_SIZE = 16;
	
	public MovableEntity(PygmyGameLevel level, String img) {
		this.level = level;
		spriteManager = new SpriteManagerDefaultImpl(img, level.getCanvas(), RENDERING_SIZE, 6);
		spriteManager.setTypes("static");
	}
	
	@Override
	public void draw(Graphics g) {
		String spriteType = "static";
		spriteManager.reset();
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}
	
	@Override
	public PygmyGameContext getContext() {
		return level.getContext();
	}
	
	@Override
	public abstract void oneStepMoveAddedBehavior();
	
}
