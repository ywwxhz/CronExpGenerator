package com.ywwxhz.beans.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.ywwxhz.beans.BaseSMHMCronConfig;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

public class DayCronConfig extends BaseSMHMCronConfig {

	private ToggleButton dayType1;
	private CheckBox useWeek;

	public DayCronConfig(ToggleGroup toggleGroup, ToggleButton type1, ToggleButton type2, ToggleButton type3,
			ToggleButton type4, FlowPane flowPane, TextField from, TextField to, TextField sFrom, TextField sPre,
			CheckBox useWeek, ToggleButton dayType1) {
		super(toggleGroup, type1, type2, type3, type4, flowPane, from, to, sFrom, sPre, true, false);
		this.dayType1 = dayType1;
		this.useWeek = useWeek;
		init();
	}

	@Override
	protected void init() {
		super.init();
		ObservableList<Node> children = flowPane.getChildren();
		CheckBox l = new CheckBox("L");
		l.setFont(font);
		children.add(l);
		l.selectedProperty().addListener((ob, o, n) -> {
			for (int i = 0; i < 31; i++) {
				CheckBox node = (CheckBox) children.get(i);
				if (n) {
					node.setSelected(false);
				}
				node.setDisable(n);
			}
		});
		CheckBox w = new CheckBox("W");
		w.setFont(font);
		children.add(w);
		toggleGroup.selectedToggleProperty().addListener((ob, o, n) -> {
			useWeek.setDisable(n != dayType1);
			if (useWeek.isDisable()) {
				useWeek.setSelected(false);
			}
		});
	}

	@Override
	protected void getType1Value(StringBuilder sb) throws VerifyError {
		if (!useWeek.isSelected()) {
			sb.append("*");
		} else {
			sb.append("?");
		}
	}

	@Override
	protected void getType4Value(StringBuilder sb) throws VerifyError {
		List<Integer> integers = new LinkedList<>();
		String ext = "";
		for (int i = 0; i < flowPane.getChildren().size(); i++) {
			CheckBox node = (CheckBox) flowPane.getChildren().get(i);
			if (node.isSelected()) {
				if (i < 31) {
					integers.add(i + (addOne ? 1 : 0));
				} else {
					ext += node.getText();
				}
			}
		}
		if (ext.length() < 2 && !ext.contains("L")) {
			if (integers.size() == 0) {
				throw new VerifyError("Cron 表达式中日期参数在指定数值模式至少需要选择一个日期");
			}
			for (int i = 0; i < integers.size(); i++) {
				sb.append(integers.get(i));
				if (i < integers.size() - 1) {
					sb.append(",");
				}
			}
		}
		sb.append(ext);
	}

	@Override
	protected void setType4(String val, TreeSet<Integer> treeSet) {
		toggleGroup.selectToggle(type4);
		ObservableList<Node> childrens = flowPane.getChildren();
		childrens.forEach(item -> {
			((CheckBox) item).setSelected(false);
		});
		treeSet.forEach(index -> ((CheckBox) childrens.get(index + (addOne ? -1 : 0))).setSelected(true));
		if (val.toUpperCase().contains("L")) {
			((CheckBox) childrens.get(31)).setSelected(true);
		}
		if (val.toUpperCase().contains("W")) {
			((CheckBox) childrens.get(32)).setSelected(true);
		}
	}

	@Override
	protected int getCount() {
		return 31;
	}

	@Override
	protected String getSuffixString() {
		return null;
	}

}
