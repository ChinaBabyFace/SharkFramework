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


import android.graphics.Canvas;
import android.graphics.Paint.Align;

import com.soaring.widget.chart.xclchart.renderer.XChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

import java.util.List;

/**
 * @ClassName CategoryAxisRender
 * @Description 分类轴(Category Axis)绘制类，绑定数据源并负责具体的绘制
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class CategoryAxisRender extends CategoryAxis {
	

	public CategoryAxisRender()
	{
		super();
		getTickLabelPaint().setTextAlign(Align.CENTER);		
		setVerticalTickPosition(XEnum.VerticalAlign.BOTTOM);
	}
	
	/**
	 * 返回数据源
	 * @return 数据源
	 */
	public List<String> getDataSet()
	{
		return this.mDataSet;
	}
	


	/**
	 * 绘制横向刻度标识
	 * @param xchart	图表基类
	 * @param canvas	画布
	 * @param centerX	点X坐标
	 * @param centerY	点Y坐标
	 * @param text	内容
	 */	
	public 	void renderAxisHorizontalTick(XChart xchart,Canvas canvas,
							float centerX,float centerY,String text)
	{			
		if(getVisible())
			renderHorizontalTick(xchart,canvas,centerX,centerY,text);
	}
	
	
	/**
	 * 绘制竖向刻度标识
	 * @param centerX	点X坐标
	 * @param centerY	点Y坐标
	 * @param text	内容
	 */
	public void renderAxisVerticalTick(Canvas canvas,
							float centerX,float centerY,String text)
	{
		
		if(getVisible())
			renderVerticalTick(canvas,centerX,centerY,text);
	}
	
	/**
	 * 绘制轴给
	 * @param startX 起始点X坐标
	 * @param startY 起始点Y坐标
	 * @param stopX	 终止点X坐标	
	 * @param stopY	 终止点Y坐标
	 */
	public void renderAxis(Canvas canvas,float startX,float startY,float stopX,float stopY)
	{
		if(getVisible() && getAxisLineVisible())
			canvas.drawLine(startX, startY, stopX, stopY, this.getAxisPaint());
	}
	
	/**
	 * 设置分类轴数据源
	 * @param dataSet 数据源
	 */
	public void setDataBuilding(List<String> dataSet)
	{
		if(null != mDataSet) mDataSet.clear();
		 mDataSet = dataSet;
	}
	

}
