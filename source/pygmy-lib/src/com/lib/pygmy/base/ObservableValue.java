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
