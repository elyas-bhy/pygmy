package com.lib.pygmy.util;

import java.io.Serializable;

public class Point implements Serializable {
	
	private static final long serialVersionUID = -2364779929670023241L;
	
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "[" + x + ":" + y + "]";
	}

}
