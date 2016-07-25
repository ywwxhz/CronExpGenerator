package com.ywwxhz.beans;

import java.util.TreeSet;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

public abstract class BaseCronConfig {
	protected ToggleGroup toggleGroup;
	protected ToggleButton type1;
	protected ToggleButton type2;
	protected ToggleButton type3;
	protected ToggleButton type4;
	protected FlowPane flowPane;

	public BaseCronConfig(ToggleGroup toggleGroup, ToggleButton type1, ToggleButton type2, ToggleButton type3,
			ToggleButton type4, FlowPane flowPane) {
		super();
		this.toggleGroup = toggleGroup;
		this.type1 = type1;
		this.type2 = type2;
		this.type3 = type3;
		this.type4 = type4;
		this.flowPane = flowPane;
	}

	/**
	 * 初始化
	 */
	protected abstract void init();

	/**
	 * 重置设置
	 */
	public void reset() {
		toggleGroup.selectToggle(type1);
	}

	/**
	 * 设置参数前的操作
	 * 
	 * @param val
	 * @return
	 */
	protected String beforeSetValue(String val) {
		return val;
	}

	/**
	 * 设置参数类型为1
	 * 
	 * @param val
	 *            参数值
	 */
	protected void setType1(String val) {
		toggleGroup.selectToggle(type1);
	}

	/**
	 * 设置参数类型为2
	 * 
	 * @param val
	 *            参数值
	 */
	protected abstract void setType2(String val);

	/**
	 * 设置参数类型为3
	 * 
	 * @param val
	 *            参数值
	 */
	protected abstract void setType3(String val);

	/**
	 * 设置参数类型为4
	 * 
	 * @param val
	 *            参数值
	 */
	protected abstract void setType4(String val, TreeSet<Integer> treeSet);

	/**
	 * 设置参数
	 * 
	 * @param val
	 * @param treeSet
	 */
	public final void setValue(String val, TreeSet<Integer> treeSet) {
		val = beforeSetValue(val);
		if (val == null) {
			return;
		}
		if ("?".equals(val) || "*".equals(val)) {
			setType1(val);
		} else if (val.contains("-") && !val.contains(",")) {
			setType2(val);
		} else if (val.contains("/") && !val.contains(",")) {
			setType3(val);
		} else {
			setType4(val, treeSet);
		}
	};

	public static String convertChoiseBoxValToInt(ChoiceBox<WeekItem> choiceBox) throws VerifyError {
		return convertStringToInt(choiceBox.getValue().getValue(), true);
	}

	/**
	 * 检测输入是否合法
	 * 
	 * @param val
	 *            字符串
	 * @param allowStar
	 *            允许星号
	 * @return
	 * @throws VerifyError
	 */
	public static String convertStringToInt(String val, boolean allowStar) throws VerifyError {
		try {
			if (!(allowStar && "*".equalsIgnoreCase(val))) {
				Integer.parseInt(val);
			}
			return val;
		} catch (Exception e) {
			throw new VerifyError("数字校验失败");
		}
	}

	/**
	 * 转换字符为数字类型
	 * 
	 * @param val
	 *            字符串
	 * @return 数字
	 */
	public static int convertStringToInt(String val) {
		return Integer.parseInt(val);
	}

	/**
	 * 获得文本框内容并校验是否合法
	 * 
	 * @param textField
	 *            文本框
	 * @param allowStar
	 *            是否允许星号
	 * @return 文本值
	 * @throws VerifyError
	 */
	public static String convertTextFieldValToInt(TextField textField, boolean allowStar) throws VerifyError {
		return convertStringToInt(getValueOrPrompt(textField), allowStar);
	}

	/**
	 * 获取文本框内容
	 * 
	 * @param textField
	 *            文本框
	 * @return
	 */
	public static String getValueOrPrompt(TextField textField) {
		String input = textField.getText();
		return input != null && input.trim().length() > 0 ? input : textField.getPromptText();
	}

	/**
	 * 获取值之前处理步骤
	 * 
	 * @param sb
	 * @param toggle
	 * @return 是否继续操作
	 * @throws VerifyError
	 */
	protected boolean beforeGetValue(StringBuilder sb, Toggle toggle) throws VerifyError {
		return true;
	}

	/**
	 * 获取模式为1的值
	 * 
	 * @param sb
	 * @throws VerifyError
	 */
	protected abstract void getType1Value(StringBuilder sb) throws VerifyError;

	/**
	 * 获取模式为2的值
	 * 
	 * @param sb
	 * @throws VerifyError
	 */
	protected abstract void getType2Value(StringBuilder sb) throws VerifyError;

	/**
	 * 获取模式为3的值
	 * 
	 * @param sb
	 * @throws VerifyError
	 */
	protected abstract void getType3Value(StringBuilder sb) throws VerifyError;

	/**
	 * 获取模式为4的值
	 * 
	 * @param sb
	 * @throws VerifyError
	 */
	protected abstract void getType4Value(StringBuilder sb) throws VerifyError;

	/**
	 * 获取值
	 * 
	 * @return
	 * @throws VerifyError
	 */
	public final String getValue() throws VerifyError {
		StringBuilder sb = new StringBuilder();
		Toggle toggle = toggleGroup.getSelectedToggle();
		if (beforeGetValue(sb, toggle)) {
			if (toggle == type1) {
				getType1Value(sb);
			} else if (toggle == type2) {
				getType2Value(sb);
			} else if (toggle == type3) {
				getType3Value(sb);
			} else if (toggle == type4) {
				getType4Value(sb);
			}
		}
		afterGetValue(sb,toggle);
		return sb.toString();
	};

	protected String getConfigName() {
		return getClass().getSimpleName();
	}
	
	/**
	 * 获取值之后处理步骤
	 * 
	 * @param sb
	 * @param toggle
	 * @throws VerifyError
	 */
	protected void afterGetValue(StringBuilder sb, Toggle toggle) throws VerifyError {
	}

}
