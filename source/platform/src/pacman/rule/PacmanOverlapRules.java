package pacman.rule;

import gameframework.base.Overlap;
import gameframework.game.AbstractOverlapRulesApplier;
import gameframework.game.GameUniverse;

import java.awt.Point;
import java.util.Vector;

public class PacmanOverlapRules extends AbstractOverlapRulesApplier {
	
	protected GameUniverse universe;

	// Time duration during which pacman is invulnerable and during which ghosts
	// can be eaten (in number of cycles)
	static final int INVULNERABLE_DURATION = 60;
	protected Point pacManStartPos;
	protected Point ghostStartPos;
	protected boolean managePacmanDeath;

	public PacmanOverlapRules(Point pacPos, Point gPos) {
		pacManStartPos = (Point) pacPos.clone();
		ghostStartPos = (Point) gPos.clone();
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		managePacmanDeath = true;
		super.applyOverlapRules(overlappables);
	}
	
//	public void overlapRule(Pacman p, Pacgum pg) {
//		universe.removeGameEntity(pg);
//	}
}
