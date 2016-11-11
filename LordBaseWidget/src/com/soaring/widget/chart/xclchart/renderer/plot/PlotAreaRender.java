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

import com.soaring.widget.chart.xclchart.renderer.IRender;

/**
 * @ClassName PlotAreaRender
 * @Description 主图表区绘制类，绘制背景等
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * 
 */

public class PlotAreaRender extends PlotArea implements IRender {

	public PlotAreaRender()
	{
		super();
		init();
	}
	
	private void init()
	{
		
	}
		
	/**
	 * 绘制背景
	 */
	protected void drawPlotBackground(Canvas canvas)
	{
		if(null == canvas) return;
		if(getBackgroundColorVisible())
			canvas.drawRect(mLeft,mTop,
					mRight,mBottom, getBackgroundPaint());
	}		
	
	/**
	 * 得到中心点X坐标
	 * @return X坐标
	 */
	public float getCenterX() {			
		return Math.abs(mLeft + (mRight - mLeft)/2);
	}

	/**
	 * 得到中心点Y坐标
	 * @return Y坐标
	 */
	public float getCenterY() {				
		return Math.abs(mBottom - (mBottom - mTop)/2);
	}
	
	public float getLeft() {
		return mLeft;
	}

	/**
	 * 设置绘图区的左边X坐标
	 * @param left	X坐标
	 */
	public void setLeft(float left) {
		this.mLeft = left;
	}
	
	/**
	 * 设置绘图区的上面Y坐标
	 * @param top	Y坐标
	 */
	public void setTop(float top) {
		this.mTop = top;
	}

	/**
	 * 设置绘图区的右边X坐标
	 * @param right	X坐标
	 */
	public void setRight(float right) {
		this.mRight = right;
	}
	
	/**
	 * 设置绘图区的底部Y坐标
	 * @param bottom	Y坐标
	 */
	public void setBottom(float bottom) {
		this.mBottom = bottom;
	}

	@Override
	public boolean render(Canvas canvas) throws Exception {
		// TODO Auto-generated method stub
		try{
			if(null == canvas) return false;
			drawPlotBackground(canvas);
		}catch( Exception e){
			 throw e;
		}
		return false;
	}

}
