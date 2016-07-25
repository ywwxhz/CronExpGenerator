package com.ywwxhz.app;

import com.ywwxhz.controllers.ControllerView;
import com.ywwxhz.controllers.MainController;
import com.ywwxhz.utils.FXMLLoaderHelper;
import com.ywwxhz.utils.UITools;
import com.ywwxhz.widget.DialogBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CronExpGenerator extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		UITools.GLOBLE_SATGE = stage;
		try {
			ControllerView<MainController> controllerView = FXMLLoaderHelper
					.createControllerViewFromResource("/com/ywwxhz/res/MainControllerView.fxml");
			Scene scene = new Scene(controllerView.view);
			stage.setScene(scene);
			stage.setTitle("Quartz Cron 表达式生成器");
			stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/com/ywwxhz/res/icon.png")));
			stage.show();
			stage.setMinHeight(stage.getHeight());
			stage.setMinWidth(stage.getWidth());
			// stage.setResizable(false);			
		} catch (Exception e) {
			e.printStackTrace();
			DialogBuilder.createAlertAndWait(AlertType.ERROR, null, "加载失败",
					"界面加载失败\n" +e.getLocalizedMessage(), (c) -> {
						System.exit(0);
					});
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
