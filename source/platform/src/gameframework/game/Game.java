package gameframework.game;

import gameframework.base.ObservableValue;

import java.awt.Canvas;
import java.util.List;

public interface Game {
	
	public void createGUI();
	
	public void setPlayers(int numPlayers);
	
	public List<Player> getPlayers();

	public Canvas getCanvas();

	public void start();

	public ObservableValue<Integer>[] score();

	public ObservableValue<Integer>[] life();

	public ObservableValue<Boolean> endOfGame();

	public Player getCurrentPlayer();

	public void setCurrentPlayer(Player player);
}
