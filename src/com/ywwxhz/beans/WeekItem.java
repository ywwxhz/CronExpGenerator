package com.ywwxhz.beans;

public class WeekItem {
	private String name;
	private String value;

	public WeekItem(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return name;
	}

}
