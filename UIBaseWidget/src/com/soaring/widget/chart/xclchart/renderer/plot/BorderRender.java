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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.soaring.widget.chart.xclchart.common.DrawHelper;

/**
 * @ClassName BorderRender
 * @Description  图边框绘制类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * 
 */
public class BorderRender extends Border {
	
	private RectF mRect = new RectF();	

	public BorderRender()
	{
		super();
	}
	
	/**
	 * 边框默认内边距
	 * @return 内边距
	 */
	public int getBorderSpadding()
	{
		return mBorderSpadding;
	}
	
	/**
	 * 图的背景画笔
	 * @return 画笔
	 */
	public Paint getChartBackgroundPaint()
	{
		// 背景画笔
		if(null == mPaintChartBackground)mPaintChartBackground = new Paint();
		mPaintChartBackground.setStyle(Style.FILL);
		mPaintChartBackground.setColor(Color.WHITE);
		return mPaintChartBackground;
	}	

	/**
	 * 绘制边
	 * @param canvas
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void renderBorder(String type ,Canvas canvas,
							 float left,float top,float right,float bottom)
	{
		
		mRect.left = left + mBorderSpadding;
		mRect.top = top + mBorderSpadding;
		mRect.right = right - mBorderSpadding;
		mRect.bottom = bottom - mBorderSpadding;		
		
	
		switch(getBorderLineStyle())
		{
		case SOLID:					
			break;
		case DOT:			
			getLinePaint().setPathEffect(DrawHelper.getInstance().getDotLineStyle());
			break;
		case DASH:		
			//虚实线 	
			getLinePaint().setPathEffect(DrawHelper.getInstance().getDashLineStyle());
			break;
		}	
		
		switch(getBorderRectType())
		{
		case RECT:				
			if(type.equals("CHART"))
			{
				if(null != mPaintChartBackground) 
					canvas.drawRect(mRect, mPaintChartBackground);		
			}else{ //BORDER
				canvas.drawRect(mRect, getLinePaint());
			}
			break;
		case ROUNDRECT:		
			if(type.equals("CHART"))
			{
				if(null != mPaintChartBackground)
					canvas.drawRoundRect(mRect, getRoundRadius(), 
							getRoundRadius(), mPaintChartBackground);	
			}else{ //BORDER
				canvas.drawRoundRect(mRect, getRoundRadius(), getRoundRadius(), getLinePaint());		
			}
			break;
		}
	}
	
	
}
