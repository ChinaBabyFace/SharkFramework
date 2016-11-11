/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */

package com.soaring.widget.chart.xclchart.renderer.plot;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
 * @ClassName PlotArea
 * @Description 主图表区类，用于定制其属性
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * 
 */

public class PlotArea {
	
	//主图表区范围
	protected float mLeft  = 0.0f;
	protected float mTop  = 0.0f;
	protected float mRight   = 0.0f;
	protected float mBottom  = 0.0f;
	
	private float mWidth = 0.0f;
	private float mHeight = 0.0f;
	
	//主图表区背景色,即画X轴与Y轴围成的区域
	private Paint mBackgroundPaint = null;	
	
	//是否画背景色
	private boolean mBackgroundColorVisible = false;
		
	
	public PlotArea()
	{

	}
	
	private void initBackgroundPaint()
	{
		if(null == mBackgroundPaint)
		{
			mBackgroundPaint = new Paint();
			mBackgroundPaint.setStyle(Style.FILL);	
			mBackgroundPaint.setColor(Color.WHITE);
		}
	}
	
	
	/**
	 * 开放主图表区背景画笔，即画X轴与Y轴围成的区域的背景画笔。
	 * @return 画笔
	 */
	 public Paint getBackgroundPaint()
	 {
		 initBackgroundPaint();
		 return mBackgroundPaint;
	 }
	 
	 /**
	  * 设置是否显示背景色
	  * @param visible 是否显示背景色
	  */
	public void setBackgroundColorVisible(boolean visible)
	{
		mBackgroundColorVisible = visible;
	}
	
	/**
	 * 返回是否显示背景色
	 * @return 是否显示背景色
	 */
	public boolean getBackgroundColorVisible()
	{
		return mBackgroundColorVisible;
	}
	
	
	/**
	 * 设置是否显示背景色及其背景色的值
	 * @param visible 是否显示背景色
	 * @param color	      背景色
	 */
	public void setBackgroundColor(boolean visible,int color)
	{
		mBackgroundColorVisible = visible;
		getBackgroundPaint().setColor(color);
	}	
	

	/**
	 * 绘图区左边位置X坐标
	 * @return X坐标
	 */
	public float getLeft() {
		return mLeft;
	}

	/**
	 * 绘图区上方Y坐标
	 * @return Y坐标
	 */
	public float getTop() {
		return mTop;
	}
	
	/**
	 * 绘图区下方Y坐标
	 * @return Y坐标
	 */
	public float getBottom() {
		return mBottom;
	}
	
	/**
	 * 绘图区右边位置X坐标
	 * @return X坐标
	 */
	public float getRight() {
		return mRight;
	}
	
	/**
	 * 绘图区宽度
	 * @return 宽度
	 */
	public float getWidth() {		
		mWidth = Math.abs(mRight - mLeft);
		return mWidth;
	}

	/**
	 * 绘图区高度
	 * @return 高度
	 */
	public float getHeight() {		
		mHeight = Math.abs(getBottom() - getTop());
		return mHeight;
	}
		
	/*
	public void showRoundRect(Canvas canvas)
	{
		BorderRender border = new BorderRender();
		border.renderBorder(canvas, mLeft  , mTop  , mRight , mBottom  ); 
	}
	*/
	
}
