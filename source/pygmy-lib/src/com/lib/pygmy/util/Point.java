/*
 * Copyright (C) 2014 Pygmy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lib.pygmy.util;

import java.io.Serializable;

/**
 * 
 * @author Pygmy
 *
 */
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