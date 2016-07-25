package com.ywwxhz.beans.impl;

import com.ywwxhz.beans.BaseSMHMCronConfig;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

public class MonthCronConfig extends BaseSMHMCronConfig {

	public MonthCronConfig(ToggleGroup toggleGroup, ToggleButton type1, ToggleButton type2, ToggleButton type3,
			ToggleButton type4, FlowPane flowPane, TextField from, TextField to, TextField sFrom, TextField sPre) {
		super(toggleGroup, type1, type2, type3, type4, flowPane, from, to, sFrom, sPre, true);
	}

	@Override
	protected int getCount() {
		return 12;
	}

	@Override
	protected String getSuffixString() {
		return "月";
	}

}
