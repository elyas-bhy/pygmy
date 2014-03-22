package com.lib.pygmy.util;

import java.io.Serializable;

/**
 * 
 * @author Pygmy
 *
 */
public class Color implements Serializable {
	
	private static final long serialVersionUID = 2077516928421365866L;
	
	private int a;
	private int r;
	private int g;
	private int b;
	
	public Color(int a, int r, int g, int b) {
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
}