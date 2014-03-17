package com.lib.pygmy.base;

import java.io.Serializable;

public class Overlap implements Serializable {
	
	private static final long serialVersionUID = 3501144486095658944L;
	
	private Overlappable overlappable1, overlappable2;

	public Overlap(Overlappable overlappable1, Overlappable overlappable2) {
		this.overlappable1 = overlappable1;
		this.overlappable2 = overlappable2;
	}

	public Overlappable getOverlappable1() {
		return overlappable1;
	}

	public Overlappable getOverlappable2() {
		return overlappable2;
	}

}
