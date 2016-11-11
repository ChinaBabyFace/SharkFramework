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

package com.soaring.widget.chart.xclchart.renderer.axis;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;


/**
 * @ClassName Axis
 * @Description  轴(axis)基类，定义了刻度，标签，等的属性
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class Axis {
	
	//轴线画笔
	private Paint mPaintAxis = null;
	
	//是否显示轴线
	private boolean mAxisLineVisible = true;	
	
	//数据轴刻度线与边上的标注画笔
	private Paint mPaintTickMarks = null;
	private Paint mPaintTickLabel = null;	 
	 
	//数据轴刻度线与边上的标注是否显示
	private boolean mTickMarksVisible = true;
	private boolean mTickLabelVisible = true;
	 
	//刻度标记文字旋转角度
	private float mTickLabelRotateAngle = 0.0f; //-45f;
		
	//是否显示轴(包含轴线，刻度线和标签)
	private boolean mAxisVisible = true;	

	public Axis()
	{				
	}
	
	private void initAxisPaint()
	{
		if(null == mPaintAxis)
		{
			mPaintAxis = new Paint();
			mPaintAxis.setColor(Color.BLACK);		
			//mPaintAxis.setStrokeWidth(mAxisLineWidth);
			mPaintAxis.setAntiAlias(true);	
		}
	}
	
	private void initTickMarksPaint()
	{
		if(null == mPaintTickMarks)
		{
			mPaintTickMarks = new Paint();
			mPaintTickMarks.setColor(Color.BLACK);	
			mPaintTickMarks.setStrokeWidth(3);
			mPaintTickMarks.setAntiAlias(true);		
		}
	}
	
	private void initTickLabelPaint()
	{
		if(null == mPaintTickLabel)
		{
			mPaintTickLabel = new Paint();		
			mPaintTickLabel.setColor(Color.BLACK);	
			mPaintTickLabel.setTextAlign(Align.RIGHT);
			mPaintTickLabel.setTextSize(18);
			mPaintTickLabel.setAntiAlias(true);
		}
	}
	
	/**
	 * 是否显示轴(包含轴线，刻度线和标签)
	 * @param visible 显示轴
	 */
	public void setVisible(boolean visible)
	{
		mAxisVisible = visible; 
	}
	
	/**
	 * 返回是否显示轴(包含轴线，刻度线和标签)
	 * @return 是否显示
	 */
	public boolean getVisible()
	{
		return mAxisVisible;
	}

	
	/**
	 * 设置是否显示轴线
	 * @param visible 是否显示轴线
	 */
	public void setAxisLineVisible(boolean visible)
	{
		mAxisLineVisible = visible; 
	}
	
	/**
	 * 返回是否显示轴线
	 * @return 是否显示
	 */
	public boolean getAxisLineVisible()
	{
		return mAxisLineVisible;
	}
	
	/**
	 * 开放轴线画笔
	 * @return 画笔
	 */
	public Paint getAxisPaint() {
		initAxisPaint();
		return mPaintAxis;
	}
	
	/**
	 * 开放轴刻度线画笔
	 * @return 画笔
	 */
	public Paint getTickMarksPaint() {
		initTickMarksPaint();
		return mPaintTickMarks;
	}

	/**
	 * 开放轴标签画笔
	 * @return	画笔
	 */
	public Paint getTickLabelPaint() {
		initTickLabelPaint();
		return mPaintTickLabel;
	}

	/**
	 * 设置是否显示轴刻度线
	 * @param visible 是否显示
	 */
	public void setTickMarksVisible(boolean visible) {
		this.mTickMarksVisible = visible;
	}
	
	/**
	 * 返回设置是否显示轴刻度线
	 * @return 是否显示
	 */
	public boolean getTickMarksVisible()
	{
		return mTickMarksVisible;
	}

	/**
	 * 设置是否显示轴标签
	 * @param visible 是否显示
	 */
	public void setTickLabelVisible(boolean visible) {
		this.mTickLabelVisible = visible;
	}

	/**
	 * 返回是否显示轴标签
	 * @return 是否显示
	 */
	public boolean getTickLabelVisible() {
		return this.mTickLabelVisible ;
	}
	
	/**
	 * 返回轴标签文字旋转角度
	 * @return 旋转角度
	 */
	public float getTickLabelRotateAngle() {
		return mTickLabelRotateAngle;
	}

	/**
	 * 设置轴标签文字旋转角度
	 * @param rotateAngle 旋转角度
	 */
	public void setTickLabelRotateAngle(float rotateAngle) {
		this.mTickLabelRotateAngle = rotateAngle;
	}
		
	//轴结束方式,以Default还是箭头什么的,暂不实现
	//public void setEndArrowType()
	//{
		
	//}
	
	
	
}
