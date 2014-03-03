package com.client.pygmy;

import com.lib.pygmy.OverlapRulesApplier;
import com.lib.pygmy.Player;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.PygmyGameLevel;

import java.util.List;

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
		addGameRule(new EndlessGameRule());
		addMovableEntity(new MyMovableEntity(this, player1, 10, 10));
		addEntity(new Wall(this, 16, 16));
		addEntity(new Wall(this, 32, 32));
		addEntity(new Wall(this, 48, 48));
		addEntity(new Wall(this, 64, 64));
	}
	
}
