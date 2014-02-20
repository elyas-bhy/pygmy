package pacman.rule;

import gameframework.base.ObservableValue;
import gameframework.base.MoveStrategyRandom;
import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.Overlap;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.AbstractOverlapRulesApplier;

import java.awt.Point;
import java.util.Vector;

import pacman.entity.Ghost;
import pacman.entity.Jail;
import pacman.entity.Pacgum;
import pacman.entity.Pacman;
import pacman.entity.SuperPacgum;
import pacman.entity.TeleportPairOfPoints;

public class PacmanOverlapRules extends AbstractOverlapRulesApplier {
	
	protected GameUniverse universe;
	protected Vector<Ghost> vGhosts = new Vector<Ghost>();

	// Time duration during which pacman is invulnerable and during which ghosts
	// can be eaten (in number of cycles)
	static final int INVULNERABLE_DURATION = 60;
	protected Point pacManStartPos;
	protected Point ghostStartPos;
	protected boolean managePacmanDeath;
	private final ObservableValue<Boolean> endOfGame;
	private int totalNbGums = 0;
	private int nbEatenGums = 0;

	public PacmanOverlapRules(Point pacPos, Point gPos,
			ObservableValue<Boolean> endOfGame) {
		pacManStartPos = (Point) pacPos.clone();
		ghostStartPos = (Point) gPos.clone();
		this.endOfGame = endOfGame;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	public void setTotalNbGums(int totalNbGums) {
		this.totalNbGums = totalNbGums;
	}

	public void addGhost(Ghost g) {
		vGhosts.addElement(g);
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		managePacmanDeath = true;
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(Pacman p, Ghost g) {
		if (!p.isVulnerable()) {
			if (g.isActive()) {
				g.setAlive(false);
				MoveStrategyStraightLine strat = new MoveStrategyStraightLine(
						g.getPosition(), ghostStartPos);
				GameMovableDriverDefaultImpl ghostDriv = (GameMovableDriverDefaultImpl) g
						.getDriver();
				ghostDriv.setStrategy(strat);

			}
		} else {
			if (g.isActive()) {
				if (managePacmanDeath) {
					p.setPosition(pacManStartPos);
					for (Ghost ghost : vGhosts) {
						ghost.setPosition(ghostStartPos);
					}
					managePacmanDeath = false;
				}
			}
		}
	}

	public void overlapRule(Ghost g, SuperPacgum spg) {
	}

	public void overlapRule(Ghost g, Pacgum spg) {
	}

	public void overlapRule(Ghost g, TeleportPairOfPoints teleport) {
		g.setPosition(teleport.getDestination());
	}

	public void overlapRule(Pacman p, TeleportPairOfPoints teleport) {
		p.setPosition(teleport.getDestination());
	}

	public void overlapRule(Ghost g, Jail jail) {
		if (!g.isActive()) {
			g.setAlive(true);
			MoveStrategyRandom strat = new MoveStrategyRandom();
			GameMovableDriverDefaultImpl ghostDriv = (GameMovableDriverDefaultImpl) g
					.getDriver();
			ghostDriv.setStrategy(strat);
			g.setPosition(ghostStartPos);
		}
	}

	public void overlapRule(Pacman p, SuperPacgum spg) {
		universe.removeGameEntity(spg);
		pacgumEatenHandler();
		p.setInvulnerable(INVULNERABLE_DURATION);
		for (Ghost ghost : vGhosts) {
			ghost.setAfraid(INVULNERABLE_DURATION);
		}
	}

	public void overlapRule(Pacman p, Pacgum pg) {
		universe.removeGameEntity(pg);
		pacgumEatenHandler();
	}

	private void pacgumEatenHandler() {
		nbEatenGums++;
		if (nbEatenGums >= totalNbGums) {
			endOfGame.setValue(true);
		}
	}
}
