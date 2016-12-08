package com.soaring.widget.segmentindicator;

/**
 * @author renyuxiang
 * 
 */
public class SegmentView {
	private String name;
	private float startX;
	private float startY;
	private float endX;
	private float endY;
	private int backgroundColor;
	private int labelColor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public float getEndX() {
		return endX;
	}

	public void setEndX(float endX) {
		this.endX = endX;
	}

	public float getEndY() {
		return endY;
	}

	public void setEndY(float endY) {
		this.endY = endY;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(int labelColor) {
		this.labelColor = labelColor;
	}

}
