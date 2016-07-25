package com.ywwxhz.widget;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.function.Consumer;
import com.ywwxhz.utils.UITools;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogBuilder {

	public static Alert createAlertAndWait(AlertType type, Stage stage, String title, String message,
			Consumer<? super ButtonType> ok) {
		return createAlertAndWait(type, stage, title, message, ok, null);
	}

	public static Alert createAlertAndWait(AlertType type, Stage stage, String title, String message,
			Consumer<? super ButtonType> ok, Consumer<? super ButtonType> cancel) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.getDialogPane().setContentText(message);
		alert.initModality(Modality.APPLICATION_MODAL);
		if (stage != null) {
			alert.initOwner(stage);
		}
		Optional<ButtonType> optional = alert.showAndWait();
		if (ok != null) {
			optional.filter(response -> response == ButtonType.OK).ifPresent(ok);
		}
		if (cancel != null) {
			optional.filter(response -> response == ButtonType.CANCEL).ifPresent(cancel);
		}
		return alert;
	}

	public static Alert createAlert(AlertType type, String title, String message) {
		return createAlert(type, UITools.GLOBLE_SATGE, title, message);
	}

	public static Alert createAlert(AlertType type, Stage stage, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.getDialogPane().setContentText(message);
		alert.initModality(Modality.APPLICATION_MODAL);
		if (stage != null) {
			alert.initOwner(stage);
		}
		alert.show();
		return alert;
	}

	public static Alert createAlertAndWait(AlertType type, String title, String message,
			Consumer<? super ButtonType> ok) {
		return createAlertAndWait(type, UITools.GLOBLE_SATGE, title, message, ok, null);
	}

	public static Alert createAlertAndWait(AlertType type, String title, String message,
			Consumer<? super ButtonType> ok, Consumer<? super ButtonType> cancel) {
		return createAlertAndWait(type, UITools.GLOBLE_SATGE, title, message, ok, cancel);
	}

	public static Alert createAlertAndWait(AlertType type, String title, String message) {
		return createAlertAndWait(type, title, message, null, null);
	}

	public static Alert createAlertAndWait(AlertType type, Stage stage, String title, String message) {
		return createAlertAndWait(type, stage, title, message, null, null);
	}

	public static Dialog<ButtonType> createExceptionDialog(Throwable th) {
		return createExceptionDialog("错误详情: " + th.getLocalizedMessage() != null ? th.getLocalizedMessage() : "", th);
	}

	public static Dialog<ButtonType> createExceptionAlertAndWait(Throwable th) {
		return createAlertAndWait(AlertType.ERROR, "错误",
				"错误详情: " + th.getLocalizedMessage() != null ? th.getLocalizedMessage() : "");
	}

	public static Dialog<ButtonType> createExceptionDialog(String message, Throwable th) {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setTitle("发生错误了。。");
		final DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.setContentText("错误信息：\n"+message);
		dialogPane.getButtonTypes().addAll(ButtonType.OK);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.setWidth(600.00);
		dialogPane.setHeaderText("发生错误了。。");
		dialogPane.setPrefWidth(550);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		th.printStackTrace(pw);
		pw.close();
		TextArea textArea = new TextArea(sw.toString());
		textArea.setEditable(false);
		textArea.setWrapText(true);
		Node borders = Borders.wrap(textArea).lineBorder().title("错误堆栈").outerPadding(5).innerPadding(10).build()
				.build();
		dialogPane.setExpandableContent(borders);
		dialog.showAndWait();
		return dialog;
	}

	public static Alert createDialog(AlertType type, String title, String message,
			Consumer<? super ButtonType> consumer) {
		return createAlertAndWait(type, title, message, consumer);
	}

	public static Dialog<ButtonType> showExceptionDialog(Throwable th) {
		return showExceptionDialog(th, null);
	}

	public static Dialog<ButtonType> showExceptionDialog(Throwable th, Stage stage) {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setTitle("错误详情");
		if (stage != null) {
			dialog.initOwner(stage);
		}
		final DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.setContentText(th.getLocalizedMessage());
		dialogPane.getButtonTypes().addAll(ButtonType.OK);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.setWidth(600.00);
		dialogPane.setPrefWidth(550);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		th.printStackTrace(pw);
		pw.close();
		TextArea textArea = new TextArea(sw.toString());
		textArea.setEditable(false);
		textArea.setWrapText(true);
		Node borders = Borders.wrap(textArea).lineBorder().title("错误堆栈").outerPadding(5).innerPadding(10).build()
				.build();
		dialogPane.setExpandableContent(borders);
		dialog.showAndWait();
		return dialog;
	}
}
