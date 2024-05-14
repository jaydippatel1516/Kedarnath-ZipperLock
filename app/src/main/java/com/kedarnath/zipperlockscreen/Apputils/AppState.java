package com.kedarnath.zipperlockscreen.Apputils;

public class AppState {
    private static AppState instance;
    private boolean visible = true;

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void SetVisible(boolean z) {
        this.visible = z;
    }

    public boolean GetVisible() {
        return this.visible;
    }
}
