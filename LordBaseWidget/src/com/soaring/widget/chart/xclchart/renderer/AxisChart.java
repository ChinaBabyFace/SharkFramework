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
package com.soaring.widget.chart.xclchart.renderer;


import android.graphics.Canvas;

import com.soaring.widget.chart.xclchart.common.IFormatterDoubleCallBack;
import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.axis.CategoryAxis;
import com.soaring.widget.chart.xclchart.renderer.axis.CategoryAxisRender;
import com.soaring.widget.chart.xclchart.renderer.axis.DataAxis;
import com.soaring.widget.chart.xclchart.renderer.axis.DataAxisRender;
import com.soaring.widget.chart.xclchart.renderer.plot.AxisTitle;
import com.soaring.widget.chart.xclchart.renderer.plot.AxisTitleRender;

/**
 * @ClassName AxisChart
 * @Description 所有用到坐标类的图表的基类,主要用于定义和绘制坐标轴
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class AxisChart extends EventChart {
		
	//数据轴
	protected DataAxisRender dataAxis  = null;
	//标签轴
	protected CategoryAxisRender categoryAxis  = null;	
	//轴标题类
	protected AxisTitleRender axisTitle = null;
	
	//格式化柱形顶上或线交叉点的标签
	private IFormatterDoubleCallBack mItemLabelFormatter;
	
	public AxisChart() {
		// TODO Auto-generated constructor stub		
		super();
		initChart();		
	}
	
	
	/**
	 * 初始化设置
	 */
	private void initChart()
	{				
		//数据轴
		dataAxis  = new DataAxisRender();
		//标签轴
		categoryAxis  = new CategoryAxisRender();				
		//轴标题
		axisTitle = new AxisTitleRender();			
	}
		

	 /**
	  * 开放数据轴绘制类
	  * @return 数据轴绘制类
	  */
	public DataAxis getDataAxis() {
		return dataAxis;
	}

	/**
	 * 开放标签轴绘制类
	 * @return 标签轴绘制类
	 */
	public CategoryAxis getCategoryAxis() {
		return categoryAxis;
	}

	/**
	 * 开放轴标题绘制类
	 * @return 图例绘制类
	 */
	public AxisTitle getAxisTitle() {
		return axisTitle;
	}

	/**
	 * 轴所占的屏幕宽度
	 * @return  屏幕宽度
	 */
	protected float getAxisScreenWidth()
	{
		return(Math.abs(plotArea.getRight() - plotArea.getLeft()));
	}
	
	/**
	 * 轴所占的屏幕高度
	 * @return 屏幕高度
	 */
	protected float getAxisScreenHeight()
	{
		return( Math.abs(plotArea.getBottom() - plotArea.getTop()));
	}
	
	/**
	 * 设置标签显示格式
	 * 
	 * @param callBack
	 *            回调函数
	 */
	public void setItemLabelFormatter(IFormatterDoubleCallBack callBack) {
		this.mItemLabelFormatter = callBack;
	}

	/**
	 * 返回标签显示格式
	 * 
	 * @param value 传入当前值
	 * @return 显示格式
	 */
	protected String getFormatterItemLabel(double value) {
		String itemLabel = "";
		try {
			itemLabel = mItemLabelFormatter.doubleFormatter(value);
		} catch (Exception ex) {
			itemLabel = Double.toString(value);
			// DecimalFormat df=new DecimalFormat("#0");
			// itemLabel = df.format(value).toString();
		}
		return itemLabel;
	}
	
	
	/**
	 * 竖向柱形图 Y轴的屏幕高度/数据轴的刻度标记总数 = 步长
	 * 
	 * @return Y轴步长
	 */
	private float getVerticalYSteps(int tickCount) {
		return MathHelper.getInstance().div(getAxisScreenHeight() , tickCount);
	}

	/**
	 * 竖向图 得到X轴的步长 X轴的屏幕宽度 / 刻度标记总数 = 步长
	 * 
	 * @param num
	 *            刻度标记总数
	 * @return X轴步长
	 */
	protected float getVerticalXSteps(int num) {
		//float XSteps = (float) Math.ceil(getAxisScreenWidth() / num);		
		return MathHelper.getInstance().div(getAxisScreenWidth(),num);		
	}
	
	
	
		
	@Override
	protected boolean postRender(Canvas canvas) throws Exception
	{
		try {
			super.postRender(canvas);
			
			//计算主图表区范围
			 calcPlotRange();
			//画Plot Area背景			
			 plotArea.render(canvas);	
			//绘制标题
			renderTitle(canvas);
			//绘制轴标题
			axisTitle.setRange(this);
			axisTitle.render(canvas);
		
		}catch( Exception e){
			 throw e;
		}
		return true;
	}

	public boolean render(Canvas canvas) throws Exception {
		// TODO Auto-generated method stub
	
		try {		
			super.render(canvas);
									
		}catch( Exception e){
			 throw e;
		}
		return true;
	}		
	 
	

}
