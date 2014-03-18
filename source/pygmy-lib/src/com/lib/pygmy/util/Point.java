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
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!(o instanceof Point)) {
			return false;
		}
		Point p = (Point)o;
		return (this.x == p.x && this.y == p.y);
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + this.x;
	    result = prime * result + this.y;
	    return result;
	}
	
	public String toString() {
		return "[" + x + ":" + y + "]";
	}

}
