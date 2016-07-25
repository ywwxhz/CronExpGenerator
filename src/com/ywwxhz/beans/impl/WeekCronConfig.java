package com.ywwxhz.beans.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.ywwxhz.beans.BaseCronConfig;
import com.ywwxhz.beans.WeekItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class WeekCronConfig extends BaseCronConfig {

	private ChoiceBox<WeekItem> from;
	private ChoiceBox<WeekItem> to;
	private ChoiceBox<WeekItem> sFrom;
	private TextField sPre;
	private CheckBox useWeek;
	private TextField indexOfWeek;
	private HBox weekIndexHolder;
	private VBox weekHolder;
	private Font font = new Font(13);
	private ToggleButton dayType2;
	private ToggleButton dayType3;
	private ToggleButton dayType4;

	public WeekCronConfig(ToggleGroup toggleGroup, ToggleButton type1, ToggleButton type2, ToggleButton type3,
			ToggleButton type4, FlowPane flowPane, ChoiceBox<WeekItem> from, ChoiceBox<WeekItem> to,
			ChoiceBox<WeekItem> sFrom, TextField sPre, CheckBox useWeek, TextField indexOfWeek, HBox weekIndexHolder,
			VBox weekHolder, ToggleButton dayType2, ToggleButton dayType3, ToggleButton dayType4) {
		super(toggleGroup, type1, type2, type3, type4, flowPane);
		this.from = from;
		this.to = to;
		this.sFrom = sFrom;
		this.sPre = sPre;
		this.useWeek = useWeek;
		this.indexOfWeek = indexOfWeek;
		this.weekIndexHolder = weekIndexHolder;
		this.weekHolder = weekHolder;
		this.dayType2 = dayType2;
		this.dayType3 = dayType3;
		this.dayType4 = dayType4;
		init();
	}

	@Override
	protected void init() {
		useWeek.selectedProperty().addListener((ob, o, n) -> {
			if (!n) {
				toggleGroup.selectToggle(type1);
			}
			dayType2.setDisable(n);
			dayType3.setDisable(n);
			dayType4.setDisable(n);
		});
		indexOfWeek.disableProperty().addListener((ob, o, n) -> {
			if (n) {
				indexOfWeek.clear();
			}
		});
		flowPane.setVgap(10);
		flowPane.setHgap(10);
		weekHolder.disableProperty().bind(useWeek.selectedProperty().not());
		weekIndexHolder.disableProperty()
				.bind(useWeek.selectedProperty().not().or(toggleGroup.selectedToggleProperty().isEqualTo(type1)));
		from.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type2));
		to.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type2));
		sFrom.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type3));
		sPre.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type3));
		flowPane.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type4));
		LinkedList<WeekItem> weekItems = new LinkedList<>();
		weekItems.add(new WeekItem("星期日", "1"));
		weekItems.add(new WeekItem("星期一", "2"));
		weekItems.add(new WeekItem("星期二", "3"));
		weekItems.add(new WeekItem("星期三", "4"));
		weekItems.add(new WeekItem("星期四", "5"));
		weekItems.add(new WeekItem("星期五", "6"));
		weekItems.add(new WeekItem("星期六", "7"));
		LinkedList<WeekItem> weekItemsCP = new LinkedList<>(weekItems);
		weekItemsCP.add(0, new WeekItem("任意", "*"));
		sFrom.setItems(FXCollections.observableArrayList(weekItemsCP));
		sFrom.getSelectionModel().select(0);
		from.setItems(FXCollections.observableArrayList(weekItemsCP));
		from.getSelectionModel().select(0);
		to.setItems(FXCollections.observableArrayList(weekItemsCP));
		to.getSelectionModel().select(0);
		ObservableList<Node> children = flowPane.getChildren();
		for (WeekItem weekItem : weekItems) {
			CheckBox checkBox = new CheckBox(weekItem.getName());
			checkBox.setFont(font);
			children.add(checkBox);
		}
		CheckBox checkBox = new CheckBox("L");
		checkBox.setFont(font);
		children.add(checkBox);
		toggleGroup.selectedToggleProperty().addListener((ob, o, n) -> {
			if (o == type2) {
				from.getSelectionModel().select(0);
				to.getSelectionModel().select(0);
			} else if (o == type3) {
				sFrom.getSelectionModel().select(0);
				sPre.clear();
			} else if (o == type4) {
				children.forEach(item -> {
					((CheckBox) item).setSelected(false);
				});
			}
			if (n == type1) {
				indexOfWeek.clear();
			}
		});
	}

	@Override
	protected String beforeSetValue(String val) {
		if ("?".equals(val)) {
			useWeek.setSelected(false);
			return null;
		}
		useWeek.setSelected(true);
		String[] weekindex = val.split("#");
		if (weekindex.length == 2) {
			this.indexOfWeek.setText(weekindex[1]);
			val = weekindex[0];
		} else {
			this.indexOfWeek.clear();
		}
		return val;
	}

	@Override
	protected void setType2(String val) {
		toggleGroup.selectToggle(type2);
		String[] vals = val.split("-");
		if (vals.length == 2) {
			from.getSelectionModel().select("*".equals(vals[0]) ? 0 : convertStringToInt(vals[0]));
			to.getSelectionModel().select("*".equals(vals[1]) ? 0 : convertStringToInt(vals[1]));
		} else {
			from.getSelectionModel().select(0);
			to.getSelectionModel().select("*".equals(vals[0]) ? 0 : convertStringToInt(vals[1]));
		}
	}

	@Override
	protected void setType3(String val) {
		toggleGroup.selectToggle(type3);
		String[] vals = val.split("/");
		if (vals.length == 2) {
			sFrom.getSelectionModel().select("*".equals(vals[0]) ? 0 : convertStringToInt(vals[0]));
			sPre.setText(vals[1]);
		} else {
			sFrom.getSelectionModel().select(0);
			sPre.setText(vals[0]);
		}
	}

	@Override
	protected void setType4(String val, TreeSet<Integer> treeSet) {
		toggleGroup.selectToggle(type4);
		ObservableList<Node> childrens = flowPane.getChildren();
		childrens.forEach(item -> {
			((CheckBox) item).setSelected(false);
		});
		treeSet.forEach(index -> ((CheckBox) childrens.get(index - 1)).setSelected(true));
		if (val.toUpperCase().contains("L")) {
			((CheckBox) childrens.get(7)).setSelected(true);
		}
	}

	@Override
	protected void getType1Value(StringBuilder sb) throws VerifyError {
		if (!useWeek.isSelected()) {
			sb.append("?");
		} else {
			sb.append("*");
		}
	}

	@Override
	protected void getType2Value(StringBuilder sb) throws VerifyError {
		sb.append(convertChoiseBoxValToInt(from)).append("-").append(convertChoiseBoxValToInt(to));
	}

	@Override
	protected void getType3Value(StringBuilder sb) throws VerifyError {
		sb.append(convertChoiseBoxValToInt(sFrom)).append("/").append(convertTextFieldValToInt(sPre, false));
	}

	@Override
	protected void getType4Value(StringBuilder sb) throws VerifyError {
		String ext = "";
		List<Integer> integers = new LinkedList<>();
		for (int i = 0; i < flowPane.getChildren().size(); i++) {
			CheckBox node = (CheckBox) flowPane.getChildren().get(i);
			if (node.isSelected()) {
				if (i < 7) {
					integers.add(i + 1);
				} else {
					ext = "L";
				}
			}
		}
		if (ext.length() == 0) {
			if (integers.size() == 0) {
				throw new VerifyError("星期未选择");
			}
		}
		for (int i = 0; i < integers.size(); i++) {
			sb.append(integers.get(i));
			if (i < integers.size() - 1) {
				sb.append(",");
			}
		}
		sb.append(ext);
	}

	@Override
	protected void afterGetValue(StringBuilder sb, Toggle toggle) throws VerifyError {
		if (indexOfWeek.getText() != null && indexOfWeek.getText().trim().length() > 0) {
			sb.append("#").append(indexOfWeek.getText().trim());
		}
	}

	@Override
	public void reset() {
		super.reset();
		useWeek.setSelected(false);
	}

}
