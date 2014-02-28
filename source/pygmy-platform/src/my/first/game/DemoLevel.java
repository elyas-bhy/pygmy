package my.first.game;

import gameframework.game.OverlapRulesApplier;
import gameframework.game.Player;
import gameframework.game.PygmyGame;
import gameframework.game.PygmyGameLevel;

import java.util.List;

import my.first.game.entity.MyMovableEntity;
import my.first.game.entity.Wall;

public class DemoLevel extends PygmyGameLevel {
	
	public DemoLevel(PygmyGame game, OverlapRulesApplier overlapRules) {
		super(game, overlapRules);
	}

	@Override
	public void init() {
		List<Player> players = getContext().getGame().getPlayers();
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		setDimensions(16, 16);
		
		addGameRule(new EternalGameRule());
		addMovableEntity(new MyMovableEntity(this, player1, 10, 10));
		addEntity(new Wall(this, 16, 16));
		addEntity(new Wall(this, 32, 32));
		addEntity(new Wall(this, 48, 48));
		addEntity(new Wall(this, 64, 64));
	}
	
}
