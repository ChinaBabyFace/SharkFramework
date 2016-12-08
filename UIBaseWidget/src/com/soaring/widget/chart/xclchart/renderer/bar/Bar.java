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

package com.soaring.widget.chart.xclchart.renderer.bar;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.Log;

import com.soaring.widget.chart.xclchart.common.DrawHelper;
import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

/**
 * @ClassName Bar
 * @Description  柱形基类，定义了柱形的一些属性
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class Bar {
	
	private static final String TAG ="Bar";
	
	//确定是横向柱形还是竖向柱形图
	private XEnum.Direction mBarDirection = XEnum.Direction.VERTICAL;
	
	//柱形画笔
	protected Paint mPaintBar = null;
	
	//文字画笔
	private Paint mPaintItemLabel = null;		
	
	//柱形顶上文字偏移量
	private int mItemLabelAnchorOffset = 10;
	//柱形顶上文字旋转角度
	private float mItemLabelRotateAngle = 0.0f;	
	//是否显示柱形顶上文字标签
	private boolean mShowItemLabel = false;		
	
	//柱形间距所占比例
	private double mBarInnerMargin = 0.2f;
	
	//FlatBar类的有效，3D柱形无效
	private XEnum.BarStyle mBarStyle = XEnum.BarStyle.GRADIENT;
	
	public Bar()
	{				
		initPaint();
	}
	
	private void initPaint()
	{
		mPaintBar  = new Paint();
		mPaintBar.setColor(Color.rgb(252, 210, 9));
		mPaintBar.setStyle(Style.FILL);
		
	}
		
	/**
	 * 返回柱形的显示方向
	 * @return 显示方向
	 */
	public XEnum.Direction getBarDirection() {
		return mBarDirection;
	}

	/**
	 * 设置柱形的显示方向
	 * @param direction 显示方向
	 */
	public void setBarDirection(XEnum.Direction direction) {
		this.mBarDirection = direction;
	}

	/**
	 * 开放柱形画笔
	 * @return 画笔
	 */
	public Paint getBarPaint() {
		
		if(null == mPaintBar)
		{
			mPaintBar  = new Paint();
			mPaintBar.setColor(Color.rgb(252, 210, 9));
			mPaintBar.setStyle(Style.FILL);
		}				
		return mPaintBar;
	}
	
	/**
	 * 开放柱形顶部标签画笔
	 * @return 画笔
	 */
	public Paint getItemLabelPaint() {
		
		//柱形顶上的文字标签	
		if(null == mPaintItemLabel)
		{
			mPaintItemLabel = new Paint();
			mPaintItemLabel.setTextSize(12);
			mPaintItemLabel.setColor(Color.BLACK);
			mPaintItemLabel.setTextAlign(Align.CENTER);
		}		
		return mPaintItemLabel;
	}

	/**
	 * 返回柱形顶部标签在显示时的偏移距离
	 * @return 偏移距离
	 */
	public int getItemLabelAnchorOffset() {
		return mItemLabelAnchorOffset;
	}

	/**
	 * 设置柱形顶部标签在显示时的偏移距离
	 * @param offset 偏移距离
	 */
	public void setItemLabelAnchorOffset(int offset) {
		this.mItemLabelAnchorOffset = offset;
	}

	/**
	 * 返回柱形顶部标签在显示时的旋转角度
	 * @return 旋转角度
	 */
	public float getItemLabelRotateAngle() {
		return mItemLabelRotateAngle;
	}

	/**
	 * 设置柱形顶部标签在显示时的旋转角度
	 * @param rotateAngle 旋转角度
	 */
	public void setItemLabelRotateAngle(float rotateAngle) {
		this.mItemLabelRotateAngle = rotateAngle;
	}

	/**
	 * 设置是否显示柱形顶部标签
	 * @param visible 是否显示
	 */
	public void setItemLabelVisible(boolean visible) {
		this.mShowItemLabel = visible;
	}
	
	/**
	 * 设置柱形间空白所占的百分比
	 * @param percentage 百分比
	 */
	public boolean setBarInnerMargin(double percentage)
	{		
		if(Double.compare(percentage, 0d) == -1)
		{
			Log.e(TAG, "此比例不能为负数噢!");
			return false;
		}if( Double.compare(percentage, 0.9d) ==  1 
				|| Double.compare(percentage, 0.9d) ==  0 ){  
			Log.e(TAG, "此比例不能大于等于0.9,要给柱形留下点显示空间!");
			return false;
		}else{
			this.mBarInnerMargin = percentage;
		}
		return true;
	}
	
	/**
	 * 得到柱形间空白所占的百分比
	 * @return 百分比
	 */
	public double getInnerMargin()
	{
		return mBarInnerMargin;
	}
	
	
	/**
	 * 返回是否显示柱形顶部标签
	 * @return 是否显示
	 */
	public boolean getItemLabelsVisible()
	{
		return mShowItemLabel;
	}
	
	/**
	 * 计算同标签多柱形时的Y分隔
	 * @param YSteps    Y轴步长
	 * @param barNumber  柱形个数
	 * @return 返回单个柱形的高度及间距
	 */	
	protected  float[] calcBarHeightAndMargin(float YSteps,int barNumber)
	{			
			if(0 == barNumber)
			{
				Log.e(TAG,"柱形个数为零.");
				return null;		
			}					
			float labelBarTotalHeight = MathHelper.getInstance().mul(YSteps , 0.9f);
			float barTotalInnerMargin = MathHelper.getInstance().mul(
											labelBarTotalHeight ,
											MathHelper.getInstance().dtof(mBarInnerMargin) );
			float barInnerMargin = MathHelper.getInstance().div(barTotalInnerMargin , barNumber);
			float barHeight = MathHelper.getInstance().div(
									MathHelper.getInstance().sub(labelBarTotalHeight ,
																 barTotalInnerMargin) , 
									barNumber);			
			float[] ret = new float[2];
			ret[0] = barHeight;
			ret[1] = barInnerMargin;
			
			return ret;
	}
	

	/**
	 * 计算同标签多柱形时的X分隔
	 * @param XSteps	X轴步长
	 * @param barNumber 柱形个数
	 * @return 返回单个柱形的宽度及间距
	 */
	protected float[] calcBarWidthAndMargin(float XSteps,int barNumber)
	{			
			if(0 == barNumber)
			{
				Log.e(TAG,"柱形个数为零.");
				return null;		
			}
			float labelBarTotalWidth = MathHelper.getInstance().mul(XSteps , 0.9f);
			float barTotalInnerMargin = MathHelper.getInstance().mul(labelBarTotalWidth ,
					 							MathHelper.getInstance().dtof(mBarInnerMargin));
			
			float barTotalWidth = MathHelper.getInstance().sub(labelBarTotalWidth , barTotalInnerMargin);
			float barInnerMargin = MathHelper.getInstance().div(barTotalInnerMargin , barNumber);
			float barWidth = MathHelper.getInstance().div(barTotalWidth , barNumber);
			
			float[] ret = new float[2];
			ret[0] = barWidth;
			ret[1] = barInnerMargin;			
			return ret;
	}
	
	/**
	 * 绘制柱形顶部标签
	 * @param text	内容	
	 * @param x		x坐标
	 * @param y		y坐标
	 * @param canvas 画布
	 */
	protected void drawBarItemLabel(String text,float x,float y,Canvas canvas)
	{
		//在柱形的顶端显示上柱形的当前值			
		if(getItemLabelsVisible())
		{				
			DrawHelper.getInstance().drawRotateText(text,
								x ,
								y,
	            			  getItemLabelRotateAngle(),
	            			  canvas, 
	            			  getItemLabelPaint());	
		}
	}
	
	/**
	 * 设置柱形的显示风格，对3D柱形无效
	 * @param style 显示风格
	 */
	public void setBarStyle(XEnum.BarStyle style)
	{
		this.mBarStyle = style;
	}
	
	/**
	 * 返回当前柱形的显示风格,对3D柱形无效
	 * @return 显示风格
	 */
	public XEnum.BarStyle getBarStyle()
	{
		return this.mBarStyle;
	}

}
