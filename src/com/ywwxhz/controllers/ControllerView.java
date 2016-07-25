package com.ywwxhz.controllers;

import javafx.scene.Parent;

/**
 * @author ywwxhz
 *         包含控制器和视图
 */
public class ControllerView<T> {
    public T controller;
    public Parent view;

    @SuppressWarnings("unchecked")
    public <E> E getController() {
        return (E) controller;
    }
}