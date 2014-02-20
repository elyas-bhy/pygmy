package gameframework.game;

import gameframework.base.ObservableValue;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * Create a basic game application with menus and displays of lives and score
 */
public class GameDefaultImpl implements Game, Observer {
	
	protected String title = "Default Game";
	protected static int NB_ROWS = 31;
	protected static int NB_COLUMNS = 28;
	protected static final int SPRITE_SIZE = 16;
	public static int MAX_NUMBER_OF_PLAYER = 4;
	public static  int MIN_NUMBER_OF_PLAYER=1;
	public static final int NUMBER_OF_LIVES = 1;

	protected CanvasDefaultImpl defaultCanvas = null;

	// initialized before each level
	protected ObservableValue<Boolean> endOfGame = null;

	private Frame f;
	private AbstractGameLevel currentPlayedLevel = null;
	private List<Player> players;
	private Player currentPlayer;

	protected int levelNumber;
	protected ArrayList<GameLevel> gameLevels;

	
	protected Label information;
	protected Label informationValue;
	protected Label currentLevel;
	protected Label currentLevelValue;


	public GameDefaultImpl() {
		information = new Label("State:");
		informationValue = new Label("Playing");
		currentLevel = new Label("Level:");	
	}

	public void createGUI() {
		f = new Frame("Default Game");
		f.dispose();

		createMenuBar();
		Container c = createStatusBar();

		defaultCanvas = new CanvasDefaultImpl();
		defaultCanvas.setSize(SPRITE_SIZE * NB_COLUMNS, SPRITE_SIZE * NB_ROWS);
		f.add(defaultCanvas);
		f.add(c, BorderLayout.NORTH);
		f.pack();
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private void createMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("file");
		MenuItem start = new MenuItem("new game");
		MenuItem save = new MenuItem("save");
		MenuItem restore = new MenuItem("load");
		MenuItem quit = new MenuItem("quit");
		Menu game = new Menu("game");
		MenuItem pause = new MenuItem("pause");
		MenuItem resume = new MenuItem("resume");
		menuBar.add(file);
		menuBar.add(game);
		f.setMenuBar(menuBar);

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		file.add(start);
		file.add(save);
		file.add(restore);
		file.add(quit);
		game.add(pause);
		game.add(resume);
	}

	private Container createStatusBar() {
		JPanel c = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		c.setLayout(layout);
		currentLevelValue = new Label(Integer.toString(levelNumber));
		c.add(currentLevel);
		c.add(currentLevelValue);
		c.add(information);
		c.add(informationValue);
		return c;
	}

	public Canvas getCanvas() {
		return defaultCanvas;
	}

	public void start() {
		levelNumber = 0;
		for (GameLevel level : gameLevels) {
			endOfGame = new ObservableValue<Boolean>(false);
			endOfGame.addObserver(this);
			currentPlayedLevel = (AbstractGameLevel) level;
			levelNumber++;
			currentLevelValue.setText(Integer.toString(levelNumber));
		}

	}


	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}

	public void setLevels(ArrayList<GameLevel> levels) {
		gameLevels = levels;
	}

	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				informationValue.setText("You win");
				currentPlayedLevel.end();
			}
		}
	}

	@Override
	public void setPlayers(int minPlayers, int maxPlayers) {
		players = new ArrayList<Player>();
		this.MAX_NUMBER_OF_PLAYER=maxPlayers;
		this.MIN_NUMBER_OF_PLAYER=minPlayers;
		players.add(new Player());
		
		
		createGUI();
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public void setCurrentPlayer(Player player) {
		this.currentPlayer = player;
	}
	
	public void setTitle(String title){
		this.title=title;
	}

	public void setBoardsize(int columns, int rows) {
		this.NB_COLUMNS=columns;
		this.NB_ROWS=rows;
		
	}
}
