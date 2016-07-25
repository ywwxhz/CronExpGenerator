package com.ywwxhz.beans;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

public abstract class BaseSMHMCronConfig extends BaseCronConfig {

	protected TextField from;
	protected TextField to;
	protected TextField sFrom;
	protected TextField sPre;
	protected boolean addOne;
	protected Font font = new Font(13);

	public BaseSMHMCronConfig(ToggleGroup toggleGroup, ToggleButton type1, ToggleButton type2, ToggleButton type3,
			ToggleButton type4, FlowPane flowPane, TextField from, TextField to, TextField sFrom, TextField sPre,
			boolean addOne) {
		this(toggleGroup, type1, type2, type3, type4, flowPane, from, to, sFrom, sPre, addOne, true);
	}

	public BaseSMHMCronConfig(ToggleGroup toggleGroup, ToggleButton type1, ToggleButton type2, ToggleButton type3,
			ToggleButton type4, FlowPane flowPane, TextField from, TextField to, TextField sFrom, TextField sPre,
			boolean addOne, boolean init) {
		super(toggleGroup, type1, type2, type3, type4, flowPane);
		this.from = from;
		this.to = to;
		this.sFrom = sFrom;
		this.sPre = sPre;
		this.addOne = addOne;
		if (init) {
			init();
		}
	}

	@Override
	protected void setType2(String val) {
		toggleGroup.selectToggle(type2);
		String[] vals = val.split("-");
		if (vals.length == 2) {
			from.setText(vals[0]);
			to.setText(vals[1]);
		} else {
			from.setText("*");
			to.setText(vals[0]);
		}
	}

	@Override
	protected void setType3(String val) {
		toggleGroup.selectToggle(type3);
		String[] vals = val.split("/");
		if (vals.length == 2) {
			sFrom.setText(vals[0]);
			sPre.setText(vals[1]);
		} else {
			sFrom.setText("*");
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
		treeSet.forEach(index -> ((CheckBox) childrens.get(index + (addOne ? -1 : 0))).setSelected(true));
	}

	@Override
	protected void init() {
		flowPane.setVgap(10);
		flowPane.setHgap(10);
		from.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type2));
		to.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type2));
		sFrom.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type3));
		sPre.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type3));
		flowPane.disableProperty().bind(toggleGroup.selectedToggleProperty().isNotEqualTo(type4));
		ObservableList<Node> children = flowPane.getChildren();
		for (int i = 0; i < getCount(); i++) {
			int index = addOne ? i + 1 : i;
			CheckBox checkBox = new CheckBox(
					(index < 10 ? " " + index + " " : index) + (getSuffixString() == null ? "" : getSuffixString()));
			checkBox.setFont(font);
			children.add(checkBox);
		}
		toggleGroup.selectedToggleProperty().addListener((ob, o, n) -> {
			if (o == type2) {
				from.clear();
				to.clear();
			} else if (o == type3) {
				sFrom.clear();
				sPre.clear();
			} else if (o == type4) {
				flowPane.getChildren().forEach(item -> {
					((CheckBox) item).setSelected(false);
				});
			}
		});
	}

	/**
	 * 获取通用元素数量
	 * 
	 * @return
	 */
	protected abstract int getCount();

	/**
	 * 获取通用元素后缀
	 * 
	 * @return
	 */
	protected abstract String getSuffixString();

	@Override
	protected void getType1Value(StringBuilder sb) throws VerifyError {
		sb.append("*");
	}

	@Override
	protected void getType2Value(StringBuilder sb) throws VerifyError {
		sb.append(convertTextFieldValToInt(from, true)).append("-").append(convertTextFieldValToInt(to, false));
	}

	@Override
	protected void getType3Value(StringBuilder sb) throws VerifyError {
		sb.append(convertTextFieldValToInt(sFrom, true)).append("/").append(convertTextFieldValToInt(sPre, false));
	}

	@Override
	protected void getType4Value(StringBuilder sb) throws VerifyError {
		List<Integer> integers = new LinkedList<>();
		for (int i = 0; i < flowPane.getChildren().size(); i++) {
			CheckBox node = (CheckBox) flowPane.getChildren().get(i);
			if (node.isSelected()) {
				integers.add(i + (addOne ? 1 : 0));
			}
		}
		if (integers.size() == 0) {
			throw new VerifyError("Cron 表达式中" + getConfigName() + "参数在指定数值模式至少需要选择一个值");
		}
		for (int i = 0; i < integers.size(); i++) {
			sb.append(integers.get(i));
			if (i < integers.size() - 1) {
				sb.append(",");
			}
		}
	}

}
