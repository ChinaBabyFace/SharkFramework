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
 * @version 1.5
 */
package com.soaring.widget.chart.xclchart.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.Log;

import com.soaring.widget.chart.xclchart.common.IFormatterTextCallBack;
import com.soaring.widget.chart.xclchart.renderer.LnChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDot;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDotRender;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;


/**
 * @ClassName ScatterChart
 * @Description  散点图基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class ScatterChart extends LnChart {
	
	private static  String TAG="ScatterChart";
	
	//数据源
	private List<ScatterData> mDataset;
	
	//分类轴的最大，最小值
	private double mMaxValue = 0d;
	private double mMinValue = 0d;
		
	//用于格式化标签的回调接口
	private IFormatterTextCallBack mLabelFormatter;
	//用于绘制点的画笔
	private Paint mPaintPoint = null;

	public ScatterChart()
	{
		super();
		initChart();
	}
	
	private void initChart()
	{
		
		categoryAxis.setHorizontalTickAlign(Align.CENTER);
		dataAxis.setHorizontalTickAlign(Align.LEFT);
	}
	
	/**
	 * 分类轴的数据源
	 * @param categories 标签集
	 */
	public void setCategories( List<String> categories)
	{
		categoryAxis.setDataBuilding(categories);
	}
	
	/**
	 *  设置数据轴的数据源
	 * @param dataSeries 数据序列
	 */
	public void setDataSource( List<ScatterData> dataSeries)
	{
		if(null != mDataset) mDataset.clear();
		this.mDataset = dataSeries;		
	}	
	
	/**
	 *  显示数据的数据轴最大值
	 * @param value 数据轴最大值
	 */
	public void setCategoryAxisMax( double value)
	{
		mMaxValue = value;
	}	
	
	/**
	 * 设置分类轴最小值
	 * @param value 最小值
	 */
	public void setCategoryAxisMin( double value)
	{
		mMinValue = value;
	}	
	
	/**
	 * 设置标签的显示格式
	 * @param callBack 回调函数
	 */
	public void setDotLabelFormatter(IFormatterTextCallBack callBack) {
		this.mLabelFormatter = callBack;
	}
	
	/**
	 * 返回标签显示格式
	 * 
	 * @param text 传入当前值
	 * @return 显示格式
	 */
	protected String getFormatterDotLabel(String text) {
		String itemLabel = "";
		try {
			itemLabel = mLabelFormatter.textFormatter(text);
		} catch (Exception ex) {
			itemLabel = text;
		}
		return itemLabel;
	}
	
	/**
	 * 用于绘制点的画笔
	 * @return 画笔
	 */
	public Paint getPointPaint()
	{
		if(null == mPaintPoint)
		{
			mPaintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);			
		}
		return mPaintPoint;
	}
	
	
				
	private void renderPoints( Canvas canvas, ScatterData bd ,int dataID)
	{			
		float initX =  plotArea.getLeft();
        float initY =  plotArea.getBottom();
		float lineStartX = initX;
        float lineStartY = initY;
        float lineStopX = 0.0f;
        float lineStopY = 0.0f;        
    	
    	float axisScreenWidth = getAxisScreenWidth(); 
    	float axisScreenHeight = getAxisScreenHeight();
		float axisDataHeight = (float) dataAxis.getAxisRange(); 	
		
		//得到标签对应的值数据集		
		LinkedHashMap<Double,Double> chartValues = bd.getDataSet();	
		if(null == chartValues) return ;
															
	    //画出数据集对应的线条				
		int j = 0;
		int childID = 0;
		
		float YvaluePostion = 0.0f,XvaluePostion =0.0f;
		
		Iterator iter = chartValues.entrySet().iterator();
		while(iter.hasNext()){
			    Entry  entry=(Entry)iter.next();
			
			    Double xValue =(Double) entry.getKey();
			    Double yValue =(Double) entry.getValue();	
			    			    
			    //对应的Y坐标
			    YvaluePostion = (float) (axisScreenHeight * ( (yValue - dataAxis.getAxisMin() ) / axisDataHeight)) ;  
			                	
            	//对应的X坐标	  	  
			    XvaluePostion = (float) (axisScreenWidth * ( (xValue - mMinValue ) / (mMaxValue - mMinValue))) ;  
            
            	if(j == 0 )
				{	                		
            		lineStartX = add(initX , XvaluePostion);
					lineStartY = sub(initY , YvaluePostion);
					
					lineStopX = lineStartX ;
					lineStopY = lineStartY;														
				}else{
					lineStopX =  add(initX , XvaluePostion);  
					lineStopY =  sub(initY , YvaluePostion);
				}            
            	
            	PlotDot dot = bd.getPlotDot();
            	if(!dot.getDotStyle().equals(XEnum.DotStyle.HIDE))
             	{          		
            		
            		getPointPaint().setColor(dot.getColor());
            		getPointPaint().setAlpha(dot.getAlpha());
            		
	            	RectF rect = PlotDotRender.getInstance().renderDot(
	            			canvas, bd.getPlotDot(),
	            			lineStartX,lineStartY,lineStopX,lineStopY,
	            			getPointPaint());
	            	
	            	savePointRecord(dataID,childID,lineStopX,lineStopY,rect); 
	    			childID++;
             	}
    			            	
	    			
            	if(bd.getLabelVisible())
            	{            			
            		//请自行在回调函数中处理显示格式
                    canvas.drawText(
                    		getFormatterDotLabel(
                    				Double.toString(xValue)+","+ Double.toString(yValue)),
                    				lineStopX,lineStopY,  bd.getDotLabelPaint());
            	}  
            	
            	            	            	
				lineStartX = lineStopX;
				lineStartY = lineStopY;

				j++;	              								
		}								
	}

		

	/**
	 * 绘制图
	 */
	private boolean renderPlot(Canvas canvas)
	{
		//检查是否有设置分类轴的最大最小值		
		if(mMaxValue == mMinValue && 0 == mMaxValue)
		{
			Log.e(TAG,"请检查是否有设置分类轴的最大最小值。");
			return false;
		}
		if(null == mDataset)
		{
			Log.e(TAG,"数据源为空.");
			return false;
		}
					
		renderVerticalDataAxis(canvas);
		renderVerticalCategoryAxis(canvas);		
		
		//开始处 X 轴 即分类轴              	
		for(int i=0;i<mDataset.size();i++)
		{																	
			ScatterData bd =  mDataset.get(i);
			if(bd.getPlotDot().getDotStyle().equals(XEnum.DotStyle.HIDE) == true
					&& bd.getLabelVisible() == false )
			{
				continue;
			}			
			renderPoints( canvas, bd,i);	
		}	
		//key
		plotLegend.renderPointKey(canvas,mDataset);
		
		return true;
	}
	
	@Override
	public boolean postRender(Canvas canvas) throws Exception {
		// TODO Auto-generated method stub
		boolean ret = true;
		try {
			super.postRender(canvas);
						
			//绘制图表
			ret = renderPlot(canvas);
			
		}catch( Exception e){
			 throw e;
		}
		return ret;
	}
	
}
