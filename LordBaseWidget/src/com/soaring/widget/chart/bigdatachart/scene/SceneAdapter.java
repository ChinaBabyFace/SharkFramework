package com.soaring.widget.chart.bigdatachart.scene;

import android.content.Context;

public abstract class SceneAdapter implements ISurfaceView {
    private String tag;
    private Context context;
    private int screenWidth;
    private int screenHeight;
    private boolean isRecycled = false;

    public SceneAdapter(Context context) {
        this.setContext(context);

    }

    public void setScreen(int width, int height) {
        this.setScreenWidth(width);
        this.setScreenHeight(height);
        initScene();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public boolean isRecycled() {
        return isRecycled;
    }

    public void setRecycled(boolean isRecycled) {
        this.isRecycled = isRecycled;
    }
}
