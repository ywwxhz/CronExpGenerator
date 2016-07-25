package com.ywwxhz.utils;

import com.ywwxhz.controllers.ControllerView;
import javafx.fxml.FXMLLoader;

import java.io.FileInputStream;
import java.io.IOException;

public class FXMLLoaderHelper {
	/**
	 * 通过FXML创建ControllerView类，类中包含视图和控制器
	 *
	 * @param xmlpath
	 *            xml在包中的全限定路径
	 * @return ControllerView
	 * @throws IOException
	 */
	public static <T> ControllerView<T> createControllerViewFromResource(String xmlpath) throws IOException {
		return createControllerViewFromResource(xmlpath, null);
	}

	/**
	 * 通过FXML创建ControllerView类，类中包含视图和控制器
	 *
	 * @param xmlpath
	 *            xml在包中的全限定路径
	 * @return ControllerView
	 * @throws IOException
	 */
	public static <T> ControllerView<T> createControllerViewFromResource(String xmlpath, T controller)
			throws IOException {
		ControllerView<T> controllerView = new ControllerView<T>();
		FXMLLoader fxmlloader = new FXMLLoader(controllerView.getClass().getResource(xmlpath));
		if (controller != null) {
			controllerView.controller = controller;
			fxmlloader.setController(controller);
			controllerView.view = fxmlloader.load();
		} else {
			controllerView.view = fxmlloader.load();
			controllerView.controller = fxmlloader.getController();
		}
		return controllerView;
	}
	
	public static <T> ControllerView<T> createControllerViewFromResourceUsePointedClass(String xmlpath, Class<T> controller)
			throws Exception {
		ControllerView<T> controllerView = new ControllerView<T>();
		FXMLLoader fxmlloader = new FXMLLoader(controllerView.getClass().getResource(xmlpath));
		if (controller != null) {
			controllerView.controller = controller.newInstance();
			fxmlloader.setController(controllerView.controller);
			controllerView.view = fxmlloader.load();
		} else {
			controllerView.view = fxmlloader.load();
			controllerView.controller = fxmlloader.getController();
		}
		return controllerView;
	}


	/**
	 * 通过FXML创建ControllerView类，类中包含视图和控制器
	 *
	 * @param xmlpath
	 *            xml在文件系统中的路径
	 * @return
	 * @throws IOException
	 */
	public static <T> ControllerView<T> createControllerViewFromFileSystem(String xmlpath) throws IOException {
		ControllerView<T> controllerView = new ControllerView<T>();
		FXMLLoader fxmlloader = new FXMLLoader();
		controllerView.view = fxmlloader.load(new FileInputStream(xmlpath));
		controllerView.controller = fxmlloader.getController();
		return controllerView;
	}
}
