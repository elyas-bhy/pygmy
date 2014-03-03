package com.client.pygmy;

import com.lib.pygmy.GameRule;

public class EndlessGameRule implements GameRule {

	@Override
	public boolean check() {
		return true;
	}
	
	@Override
	public String getMessage() {
		return "Playing";
	}

}
