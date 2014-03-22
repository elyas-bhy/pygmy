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

package com.lib.pygmy.base;

import java.io.Serializable;
import java.util.Observable;

public class ObservableValue<T> extends Observable implements Serializable {
	
	private static final long serialVersionUID = -2453057466577519147L;
	
	private T value;

	public ObservableValue(T initial) {
		setValue(initial);
	}

	public void setValue(T newValue) {
		if (value != newValue) {
			this.value = newValue;
			setChanged();
			notifyObservers();
		}
	}

	public T getValue() {
		return value;
	}
	
}