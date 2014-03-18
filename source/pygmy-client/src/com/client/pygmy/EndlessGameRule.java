package com.client.pygmy;

import com.lib.pygmy.PygmyGameRule;

public class EndlessGameRule extends PygmyGameRule {

	@Override
	public boolean check() {
		return true;
	}
	
	@Override
	public String getMessage() {
		return "Playing";
	}

}