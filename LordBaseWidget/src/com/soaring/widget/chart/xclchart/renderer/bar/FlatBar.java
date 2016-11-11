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
import android.graphics.LinearGradient;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.util.Log;

import com.soaring.widget.chart.xclchart.common.DrawHelper;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

/**
 * @ClassName FlatBar
 * @Description  平面柱形类，对柱形加了一点渲染效果
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class FlatBar extends Bar {
	
	private static final String TAG = "FlatBar";
	
	//柱形填充色透明度
	private int mFillAlpha = 255;
	
	private LinearGradient linearGradient = null;
	
	public FlatBar()
	{
		super();
	}

	/**
	 * 返回填充透明度
	 * @return 透明度
	 */
	public int getFillAlpha() {
		return mFillAlpha;
	}

	/**
	 * 设置填充透明度
	 * @param alpha 透明度
	 */
	public void setFillAlpha(int alpha) {
		this.mFillAlpha = alpha;
	}
	
	/**
	 * 计算同标签多柱形时的Y分隔
	 * @param YSteps    Y轴步长
	 * @param barNumber  柱形个数
	 * @return 返回单个柱形的高度及间距
	 */
	public float[] getBarHeightAndMargin(float YSteps,int barNumber)
	{
		return calcBarHeightAndMargin( YSteps, barNumber);
	}
	/**
	 * 计算同标签多柱形时的X分隔
	 * @param XSteps	X轴步长
	 * @param barNumber 柱形个数
	 * @return 返回单个柱形的宽度及间距
	 */
	public float[] getBarWidthAndMargin(float XSteps,int barNumber)
	{
		return calcBarWidthAndMargin(XSteps,barNumber);
	}
	
	/**
	 * 绘制柱形渲染效果
	 * @param left	左边X坐标
	 * @param top	顶部Y坐标
	 * @param right	右边X坐标
	 * @param bottom	底部Y坐标
	 */
	private void setBarGradient(float left,float top,float right ,float bottom)
	{
		int barColor = getBarPaint().getColor();						
		int lightColor = DrawHelper.getInstance().getLightColor(barColor,150);
		float width = Math.abs(right - left);
		float height = Math.abs(bottom - top);
			
		Shader.TileMode tm = Shader.TileMode.MIRROR;
		if(width > height) //横向柱形
		{
			 linearGradient = new LinearGradient(right,bottom, right ,top,  
			           new int[]{lightColor,barColor},  
			           null,tm);  		   				 
				 
		}else{
			  linearGradient = new LinearGradient(left,bottom, right ,bottom,  
			           new int[]{lightColor,barColor},  
			           null,tm);  					  			  
		}
		getBarPaint().setShader(linearGradient);		
	}
	
	/**
	 * 绘制柱形
	 * @param left	左边X坐标
	 * @param top	顶部Y坐标
	 * @param right	右边X坐标
	 * @param bottom	底部Y坐标
	 * @param canvas	画布
	 */
	public boolean renderBar(float left,float top,float right ,float bottom,Canvas canvas)
	{				
		
		if( XEnum.BarStyle.OUTLINE == getBarStyle())
		{
			int barColor = getBarPaint().getColor();						
			int lightColor = DrawHelper.getInstance().getLightColor(barColor,150);
			float pWidth = getBarPaint().getStrokeWidth();
			
			getBarPaint().setStyle(Style.FILL);
			getBarPaint().setColor(lightColor);
			canvas.drawRect( left ,bottom,right,top  ,getBarPaint());
			
			getBarPaint().setStyle(Style.STROKE);			
			//getBarPaint().setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			getBarPaint().setColor(barColor);
			getBarPaint().setStrokeWidth(5);
			canvas.drawRect( left ,bottom,right,top  ,getBarPaint());	
			getBarPaint().setStrokeWidth(pWidth);
			return true;
		}
		
		//GRADIENT,FILL,STROKE
		switch(getBarStyle())
		{
		case GRADIENT:
			setBarGradient(left,top,right,bottom);
			break;
		case FILL:
			getBarPaint().setStyle(Style.FILL);
			break;
		case STROKE:						
			if(Float.compare(1f,  getBarPaint().getStrokeWidth() ) == 0)
										getBarPaint().setStrokeWidth(3);
			getBarPaint().setStyle(Style.STROKE);
			break;
		default:
			Log.e(TAG,"不认识的柱形风格参数.");
			return false;
		} 
		
		setBarGradient(left,top,right,bottom);
		canvas.drawRect( left ,bottom,right,top  ,getBarPaint());	
		return true;
	}
	
	/**
	 * 绘制柱形标签
	 * @param text	文本内容
	 * @param x	x坐标
	 * @param y	y坐标
	 * @param canvas	画布
	 */
	public void renderBarItemLabel(String text,float x,float y,Canvas canvas)
	{		    	 
		drawBarItemLabel(text,x,y,canvas);
	}
	
	
}
