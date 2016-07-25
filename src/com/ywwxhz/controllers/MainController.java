package com.ywwxhz.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.quartz.CronExpressionEx;

import com.ywwxhz.beans.BaseCronConfig;
import com.ywwxhz.beans.WeekItem;
import com.ywwxhz.beans.impl.DayCronConfig;
import com.ywwxhz.beans.impl.HourCronConfig;
import com.ywwxhz.beans.impl.MinuteCronConfig;
import com.ywwxhz.beans.impl.MonthCronConfig;
import com.ywwxhz.beans.impl.SecondCronConfig;
import com.ywwxhz.beans.impl.WeekCronConfig;
import com.ywwxhz.utils.UITools;
import com.ywwxhz.widget.DialogBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TabPane;

public class MainController implements Initializable {

	@FXML
	private TabPane configTabPane;
	@FXML
	ToggleGroup secondGroup;
	@FXML
	TextField fromSec;
	@FXML
	TextField toSec;
	@FXML
	TextField steptFromSec;
	@FXML
	TextField steptPreSec;
	@FXML
	FlowPane secGrp;
	@FXML
	ToggleGroup minuteGroup;
	@FXML
	TextField fromMin;
	@FXML
	TextField toMin;
	@FXML
	TextField steptFromMin;
	@FXML
	TextField steptPreMin;
	@FXML
	FlowPane minGrp;
	@FXML
	ToggleGroup hoursGroup;
	@FXML
	TextField fromHour;
	@FXML
	TextField toHour;
	@FXML
	TextField steptFromHour;
	@FXML
	TextField steptPreHour;
	@FXML
	FlowPane hoursGrp;
	@FXML
	ToggleGroup dayGroup;
	@FXML
	TextField fromDay;
	@FXML
	TextField toDay;
	@FXML
	TextField steptFromDay;
	@FXML
	TextField steptPreDay;
	@FXML
	FlowPane dayGrp;
	@FXML
	ToggleGroup monGroup;
	@FXML
	TextField fromMon;
	@FXML
	TextField toMon;
	@FXML
	TextField steptFromMon;
	@FXML
	TextField steptPreMon;
	@FXML
	FlowPane monGrp;
	@FXML
	CheckBox useWeek;
	@FXML
	VBox weekHolder;
	@FXML
	ToggleGroup weekGroup;
	@FXML
	ChoiceBox<WeekItem> weekFrom;
	@FXML
	ChoiceBox<WeekItem> weekTo;
	@FXML
	ChoiceBox<WeekItem> steptWeekFrom;
	@FXML
	TextField steptWeek;
	@FXML
	FlowPane weekGrp;
	@FXML
	TextField secTF;
	@FXML
	TextField minTF;
	@FXML
	TextField hourTF;
	@FXML
	TextField dayTF;
	@FXML
	TextField monthTF;
	@FXML
	TextField weekTF;
	@FXML
	TextField cronExpTF;
	@FXML
	TextField startTimeTF;
	@FXML
	ListView<String> execList;
	@FXML
	TextField yearTF;
	@FXML
	RadioButton secType1;
	@FXML
	RadioButton secType2;
	@FXML
	RadioButton secType3;
	@FXML
	RadioButton secType4;
	@FXML
	RadioButton minType1;
	@FXML
	RadioButton minType2;
	@FXML
	RadioButton minType3;
	@FXML
	RadioButton minType4;
	@FXML
	RadioButton hourType1;
	@FXML
	RadioButton hourType2;
	@FXML
	RadioButton hourType3;
	@FXML
	RadioButton hourType4;
	@FXML
	RadioButton dayType1;
	@FXML
	RadioButton dayType2;
	@FXML
	RadioButton dayType3;
	@FXML
	RadioButton dayType4;
	@FXML
	RadioButton monType1;
	@FXML
	RadioButton monType2;
	@FXML
	RadioButton monType3;
	@FXML
	RadioButton monType4;
	@FXML
	RadioButton weekType1;
	@FXML
	RadioButton weekType2;
	@FXML
	RadioButton weekType3;
	@FXML
	RadioButton weekType4;
	@FXML
	HBox weekIndexHolder;
	@FXML
	TextField indexOfWeek;
	@FXML
	private TextArea description;
	private SecondCronConfig secondCronConfig;
	private MinuteCronConfig minuteCronConfig;
	private HourCronConfig hourCronConfig;
	private DayCronConfig dayCronConfig;
	private MonthCronConfig monthCronConfig;
	private WeekCronConfig weekCronConfig;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initSecond();
		initMinute();
		initHour();
		initDay();
		initMonth();
		initWeek();
	}

	/**
	 * 初始化秒
	 */
	private void initSecond() {
		secondCronConfig = new SecondCronConfig(secondGroup, secType1, secType2, secType3, secType4, secGrp, fromSec,
				toSec, steptFromSec, steptPreSec);
		secTF.focusedProperty().addListener((ob, o, n) -> {
			if (n) {
				configTabPane.getSelectionModel().select(0);
			}
		});
	}

	/**
	 * 初始化分
	 */
	private void initMinute() {
		minuteCronConfig = new MinuteCronConfig(minuteGroup, minType1, minType2, minType3, minType4, minGrp, fromMin,
				toMin, steptFromMin, steptPreMin);
		minTF.focusedProperty().addListener((ob, o, n) -> {
			if (n) {
				configTabPane.getSelectionModel().select(1);
			}
		});
	}

	/**
	 * 初始化小时
	 */
	private void initHour() {
		hourCronConfig = new HourCronConfig(hoursGroup, hourType1, hourType2, hourType3, hourType4, hoursGrp, fromHour,
				toHour, steptFromHour, steptPreHour);
		hourTF.focusedProperty().addListener((ob, o, n) -> {
			if (n) {
				configTabPane.getSelectionModel().select(2);
			}
		});
	}

	/**
	 * 初始化天
	 */
	private void initDay() {
		dayCronConfig = new DayCronConfig(dayGroup, dayType1, dayType2, dayType3, dayType4, dayGrp, fromDay, toDay,
				steptFromDay, steptPreDay, useWeek, dayType1);
		dayTF.focusedProperty().addListener((ob, o, n) -> {
			if (n) {
				configTabPane.getSelectionModel().select(3);
			}
		});
	}

	/**
	 * 初始化月
	 */
	private void initMonth() {
		monthCronConfig = new MonthCronConfig(monGroup, monType1, monType2, monType3, monType4, monGrp, fromMon, toMon,
				steptFromMon, steptPreMon);
		monthTF.focusedProperty().addListener((ob, o, n) -> {
			if (n) {
				configTabPane.getSelectionModel().select(4);
			}
		});
	}

	/**
	 * 初始化周
	 */
	private void initWeek() {
		weekCronConfig = new WeekCronConfig(weekGroup, weekType1, weekType2, weekType3, weekType4, weekGrp, weekFrom,
				weekTo, steptWeekFrom, steptWeek, useWeek, indexOfWeek, weekIndexHolder, weekHolder, dayType2, dayType3,
				dayType4);
		weekTF.focusedProperty().addListener((ob, o, n) -> {
			if (n) {
				configTabPane.getSelectionModel().select(5);
			}
		});
	}

	/**
	 * 分析表达式
	 */
	@FXML
	public void analizeExp() {
		try {
			if (cronExpTF.getText() == null || cronExpTF.getText().trim().length() == 0) {
				DialogBuilder.createAlert(AlertType.WARNING, "警告", "Quartz Cron 表达式不能为空");
				return;
			}
			setUI(buildExpress(cronExpTF.getText()));
		} catch (Exception e) {
			clearAllValue(false);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.close();
			if (e instanceof NoSuchElementException) {
				DialogBuilder.createAlert(AlertType.ERROR, "错误", "Quartz Cron 表达式分析错误\n不存在该元素");
			} else {
				description.setText(sw.toString());
				DialogBuilder.createAlert(AlertType.ERROR, "错误", "Quartz Cron 表达式分析错误\n" + e.getMessage());
			}
		}
	}

	/**
	 * 更新UI控件
	 * 
	 * @param cronExpress
	 */
	private void setUI(CronExpressionEx cronExpress) {
		setCronConfigValue(secondCronConfig, cronExpress.getSecondsField(), cronExpress.getSecondsSet());
		setCronConfigValue(minuteCronConfig, cronExpress.getMinutesField(), cronExpress.getMinutesSet());
		setCronConfigValue(hourCronConfig, cronExpress.getHoursField(), cronExpress.getHoursSet());
		setCronConfigValue(dayCronConfig, cronExpress.getDaysOfMonthField(), cronExpress.getDaysOfMonthSet());
		setCronConfigValue(monthCronConfig, cronExpress.getMonthsField(), cronExpress.getMonthsSet());
		setCronConfigValue(weekCronConfig, cronExpress.getDaysOfWeekField(), cronExpress.getDaysOfWeekSet());
	}

	/**
	 * 更新配置
	 * 
	 * @param config
	 * @param val
	 *            表达式
	 * @param treeSet
	 */
	private void setCronConfigValue(BaseCronConfig config, String val, TreeSet<Integer> treeSet) {
		config.setValue(val, treeSet);
	}

	/**
	 * 生成表达式
	 */
	@FXML
	public void genExp() {
		try {
			String second = getSecondExp();
			String minute = getMinuteExp();
			String hour = getHourExp();
			String day = getDayExp();
			String month = getMonthExp();
			String week = getWeekExp();
			String year = "*";
			String cronExpress = String.format("%s %s %s %s %s %s %s", second, minute, hour, day, month, week, year);
			buildExpress(cronExpress);
		} catch (VerifyError | ParseException e) {
			DialogBuilder.createAlert(AlertType.ERROR, "错误", "Quartz Cron 表达式生成错误\n" + e.getLocalizedMessage());
		}
	}

	/**
	 * @param textField
	 * @param text
	 */
	private void setTextFieldValueIfNotNullOrEmpty(TextField textField, String text) {
		if (text != null && text.trim().length() > 0) {
			textField.setText(text);
		}
	}

	/**
	 * 构建表达式
	 * 
	 * @param cronExpress
	 *            表达式文本
	 * @return CronExpressionEx 对象
	 * @throws ParseException
	 */
	private CronExpressionEx buildExpress(String cronExpress) throws ParseException {
		CronExpressionEx ex = new CronExpressionEx(cronExpress);
		setTextFieldValueIfNotNullOrEmpty(secTF, ex.getSecondsField());
		setTextFieldValueIfNotNullOrEmpty(minTF, ex.getMinutesField());
		setTextFieldValueIfNotNullOrEmpty(hourTF, ex.getHoursField());
		setTextFieldValueIfNotNullOrEmpty(dayTF, ex.getDaysOfMonthField());
		setTextFieldValueIfNotNullOrEmpty(monthTF, ex.getMonthsField());
		setTextFieldValueIfNotNullOrEmpty(weekTF, ex.getDaysOfWeekField());
		setTextFieldValueIfNotNullOrEmpty(yearTF, ex.getYearField());
		Date dd = new Date();
		cronExpTF.setText(cronExpress);
		startTimeTF.setText(sdf.format(dd));
		ObservableList<String> list = FXCollections.observableArrayList();
		for (int i = 1; i <= 30; i++) {
			dd = ex.getNextValidTimeAfter(dd);
			if (dd != null) {
				list.add(sdf.format(dd));
				dd = new Date(dd.getTime() + 100);
			} else {
				list.add("日期越界");
				break;
			}
		}
		execList.setItems(list);
		execList.scrollTo(0);
		execList.getSelectionModel().clearSelection();
		cronExpTF.requestFocus();
		cronExpTF.selectRange(cronExpress.length(), cronExpress.length());
		description.setText(ex.getCronExpressionObj().getExpressionSummary());
		return ex;
	}

	private String getSecondExp() throws VerifyError {
		return secondCronConfig.getValue();
	}

	private String getMinuteExp() throws VerifyError {
		return minuteCronConfig.getValue();
	}

	private String getHourExp() throws VerifyError {
		return hourCronConfig.getValue();
	}

	private String getDayExp() throws VerifyError {
		return dayCronConfig.getValue();
	}

	private String getMonthExp() throws VerifyError {
		return monthCronConfig.getValue();
	}

	private String getWeekExp() throws VerifyError {
		return weekCronConfig.getValue();
	}

	private String getValueOrPrompt(TextField textField) {
		String input = textField.getText();
		return input != null && input.trim().length() > 0 ? input : textField.getPromptText();
	}

	/**
	 * 更新字段
	 */
	@FXML
	private void update() {
		update(true);
	}

	/**
	 * 更新字段
	 * 
	 * @param doAnalize
	 *            是否分析
	 */
	private void update(boolean doAnalize) {
		String second = getValueOrPrompt(secTF);
		String minute = getValueOrPrompt(minTF);
		String hour = getValueOrPrompt(hourTF);
		String day = getValueOrPrompt(dayTF);
		String month = getValueOrPrompt(monthTF);
		String week = getValueOrPrompt(weekTF);
		String year = getValueOrPrompt(yearTF);
		String cronExpress = String.format("%s %s %s %s %s %s %s", second, minute, hour, day, month, week, year);
		cronExpTF.setText(cronExpress);
		if (doAnalize) {
			analizeExp();
		}
	}

	/**
	 * 复制表达式
	 */
	@FXML
	public void copyExp() {
		if (cronExpTF.getText() != null && cronExpTF.getText().trim().length() > 0) {
			final ClipboardContent content = new ClipboardContent();
			content.putString(cronExpTF.getText());
			Clipboard.getSystemClipboard().setContent(content);
		}
	}

	/**
	 * 退出
	 */
	@FXML
	public void exit() {
		UITools.GLOBLE_SATGE.close();
		System.exit(0);
	}

	/**
	 * 重置
	 */
	@FXML
	public void reset() {
		DialogBuilder.createAlertAndWait(AlertType.CONFIRMATION, "清空", "是否清空全部输入？", (c) -> {
			clearAllValue(true);
		});
	}

	/**
	 * 清空所有
	 * 
	 * @param clearCronExp
	 */
	private void clearAllValue(boolean clearCronExp) {
		resetSecond();
		resetMinute();
		resetHour();
		resetDay();
		resetMonth();
		resetWeek();
		clearOtherValue();
		if (clearCronExp) {
			cronExpTF.clear();
		}
	}

	/**
	 * 重置秒
	 */
	private void resetSecond() {
		secondCronConfig.reset();
		secTF.clear();
	}

	/**
	 * 重置分
	 */
	private void resetMinute() {
		minuteCronConfig.reset();
		minTF.clear();
	}

	/**
	 * 重置小时
	 */
	private void resetHour() {
		hourCronConfig.reset();
		hourTF.clear();
	}

	/**
	 * 重置天
	 */
	private void resetDay() {
		dayCronConfig.reset();
		dayTF.clear();
	}

	/**
	 * 重置月
	 */
	private void resetMonth() {
		monthCronConfig.reset();
		monthTF.clear();
	}

	/**
	 * 重置周
	 */
	private void resetWeek() {
		weekCronConfig.reset();
		weekTF.clear();
	}

	/**
	 * 重置其他
	 */
	private void clearOtherValue() {
		startTimeTF.clear();
		execList.setItems(null);
		description.clear();
	}

	@FXML
	public void onKeyType(KeyEvent event) {
		if (!event.isAltDown() && !event.isControlDown() && !event.isShiftDown() && event.getCode() != KeyCode.ENTER
				&& event.getCode() != KeyCode.LEFT && event.getCode() != KeyCode.RIGHT && event.getCode() != KeyCode.UP
				&& event.getCode() != KeyCode.DOWN) {
			update(false);
			clearOtherValue();
		}
	}

}
