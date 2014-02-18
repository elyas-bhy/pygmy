package gameframework.game;

public class EternalGameRule implements GameRule {

	@Override
	public boolean check() {
		return true;
	}
	
	@Override
	public String getMessage() {
		return "Playing";
	}

}
