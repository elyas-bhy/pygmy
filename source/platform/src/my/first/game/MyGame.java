package my.first.game;

import gameframework.game.GameDefaultImpl;
import gameframework.game.GameLevel;

import java.util.ArrayList;

public class MyGame {
	public static void main(String[] args) {
		
		GameDefaultImpl g = new GameDefaultImpl();
		g.setPlayers(1,10);
		g.setBoardsize(10,10);
		g.setTitle("Default Game");
		
	
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

		levels.add(new MyLevel(g)); // only one level is available at this time
		
		g.setLevels(levels);
	
	}

}
