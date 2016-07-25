package com.ywwxhz.beans.impl;

import com.ywwxhz.beans.BaseSMHMCronConfig;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

public class MinuteCronConfig extends BaseSMHMCronConfig {

	public MinuteCronConfig(ToggleGroup toggleGroup, ToggleButton type1, ToggleButton type2, ToggleButton type3,
			ToggleButton type4, FlowPane flowPane, TextField from, TextField to, TextField sFrom, TextField sPre) {
		super(toggleGroup, type1, type2, type3, type4, flowPane, from, to, sFrom, sPre, false);
	}

	@Override
	protected int getCount() {
		return 60;
	}

	@Override
	protected String getSuffixString() {
		return null;
	}

}
