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
package com.soaring.widget.chart.xclchart.chart;

import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.soaring.widget.chart.xclchart.common.IFormatterTextCallBack;
import com.soaring.widget.chart.xclchart.renderer.LnChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;
import com.soaring.widget.chart.xclchart.renderer.line.PlotCustomLine;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDot;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDotRender;
import com.soaring.widget.chart.xclchart.renderer.line.PlotLine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * @ClassName SplineChart
 * @Description  曲线图基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class SplineChart extends LnChart {
	
	private static  String TAG="SplineChart";
	
	//数据源
	private List<SplineData> mDataset;
	
	//分类轴的最大，最小值
	private double mMaxValue = 0d;
	private double mMinValue = 0d;
		
	//用于格式化标签的回调接口
	private IFormatterTextCallBack mLabelFormatter;
	
	//用于绘制定制线(分界线)
	private PlotCustomLine mCustomLine = null;
	
	//平滑曲线
	private List<PointF> mLstPoints = new ArrayList<PointF>(); 
	private Path mBezierPath = new Path();
	
	//dots
	private List<RectF> mLstDots =new ArrayList<RectF>();
		
	//key
	private List<LnData> mLstKey = new ArrayList<LnData>();
	
	//平滑曲线
  	private XEnum.CrurveLineStyle mCrurveLineStyle = XEnum.CrurveLineStyle.BEZIERCURVE;

		
	public SplineChart()
	{
		super();
		initChart();
	}
	
	private void initChart()
	{
		mCustomLine = new PlotCustomLine();
		
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
	public void setDataSource( List<SplineData> dataSeries)
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
	 * 设置定制线值
	 * @param customLineDataset 定制线数据集合
	 */
	public void setCustomLines( List<CustomLineData> customLineDataset)
	{
		mCustomLine.setCustomLines(customLineDataset);
	}
	
	/**
	 * 设置曲线显示风格:直线(NORMAL)或平滑曲线(BEZIERCURVE)
	 * @param style
	 */
	public void setCrurveLineStyle(XEnum.CrurveLineStyle style)
	{
		mCrurveLineStyle = style;
	}
	
	/**
	 * 返回曲线显示风格
	 * @return 显示风格
	 */
	public XEnum.CrurveLineStyle getCrurveLineStyle()
	{
		return mCrurveLineStyle;
	}
	
				
	private void calcAllPoints( SplineData bd,List<RectF> lstDots,List<PointF> lstPoints)
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
		LinkedHashMap<Double,Double> chartValues = bd.getLineDataSet();	
		if(null == chartValues) return ;
															
	    //画出数据集对应的线条				
		int j = 0;
		Iterator iter = chartValues.entrySet().iterator();
		while(iter.hasNext()){
			    Entry  entry=(Entry)iter.next();
			
			    Double xValue =(Double) entry.getKey();
			    Double yValue =(Double) entry.getValue();	
			    			    
			    //对应的Y坐标
			    /*
			     *精度较高
			    float ylen = (float) MathHelper.getInstance().sub(yValue, dataAxis.getAxisMin());			    
			    float YvaluePostion =  mul(axisScreenHeight, div(ylen,axisDataHeight));
			    YvaluePostion = MathHelper.getInstance().round(YvaluePostion, 2);							    			    			    
			  */
			    float YvaluePostion = (float) (axisScreenHeight * ( (yValue - dataAxis.getAxisMin() ) / axisDataHeight)) ;  
			    
            	
            	//对应的X坐标	      
			   /*
			    *精度较高
			    float tpostion = (float) MathHelper.getInstance().div(  
			    										  MathHelper.getInstance().sub(xValue, mMinValue) 
			    		    							, MathHelper.getInstance().sub(mMaxValue, mMinValue) ,2);
			    float XvaluePostion = mul(axisScreenWidth , tpostion);  
			    XvaluePostion = MathHelper.getInstance().round(XvaluePostion, 2);			    		
            	*/	  
			  float XvaluePostion = (float) (axisScreenWidth * ( (xValue - mMinValue ) / (mMaxValue - mMinValue))) ;  
            
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
            	            	
            	if(0 == j )
        		{
            		//line
            		lstPoints.add( new PointF(lineStartX,lineStartY));
            		lstPoints.add( new PointF(lineStopX,lineStopY));        			
        		}else{     
        			//line
        			lstPoints.add( new PointF(lineStopX,lineStopY));
        		}            		
        
            	//dot
            	lstDots.add(new RectF(lineStartX,lineStartY,lineStopX,lineStopY));
                         					
				lineStartX = lineStopX;
				lineStartY = lineStopY;

				j++;	              								
		}								
	}
	
	
	private boolean renderLine(Canvas canvas, SplineData spData,
												List<PointF> lstPoints)
	{		        
		for(int i=0;i<lstPoints.size();i++)
		{	        	
			if(0 == i)continue;
			PointF pointStart = lstPoints.get(i - 1);
			PointF pointStop = lstPoints.get(i);
			    
			canvas.drawLine( pointStart.x ,pointStart.y ,pointStop.x ,pointStop.y,
					spData.getLinePaint()); 	
		}
		return true;
	}
		
	private boolean renderBezierCurveLine(Canvas canvas,Path bezierPath,
			SplineData spData,List<PointF> lstPoints)
	{		        		
		renderBezierCurveLine(canvas,spData.getLinePaint(),bezierPath,lstPoints); 		 
		return true;
	}
	
	private boolean renderDotAndLabel(Canvas canvas, SplineData spData,int dataID,
										List<RectF> lstDots)
	{	
		PlotLine pLine = spData.getPlotLine();
		if(pLine.getDotStyle().equals(XEnum.DotStyle.HIDE) == true
					&& spData.getLabelVisible() == false )
		{
			return true;
		}
		int childID = 0;
		
		//得到标签对应的值数据集		
		LinkedHashMap<Double,Double> chartValues = spData.getLineDataSet();	
		if(null == chartValues) return false;
		
		int i = 0;
		Iterator iter = chartValues.entrySet().iterator();
		while(iter.hasNext()){
			    Entry  entry=(Entry)iter.next();
			
			    Double xValue =(Double) entry.getKey();
			    Double yValue =(Double) entry.getValue();	
			    			    
			    RectF  dot = lstDots.get(i);
			    			    
			    if(!pLine.getDotStyle().equals(XEnum.DotStyle.HIDE))
            	{
            		float rendEndX = dot.right;                		
            		PlotDot pDot = pLine.getPlotDot();
            		rendEndX  = add(dot.right , pDot.getDotRadius());               		
        			
            		RectF rect = PlotDotRender.getInstance().renderDot(canvas,pDot,
            				dot.left ,dot.top ,
    	    				dot.right ,dot.bottom,
            				pLine.getDotPaint()); //标识图形            			                	
            		dot.right = rendEndX;
        			            			
        			savePointRecord(dataID,childID,dot.right ,dot.bottom,rect); 
        			childID++;
            	}
        		
        		if(spData.getLabelVisible())
            	{            			
            		//请自行在回调函数中处理显示格式
                    canvas.drawText(
                    		getFormatterDotLabel(
                    				Double.toString(xValue)+","+ Double.toString(yValue)),
                    				dot.right ,dot.bottom,  pLine.getDotLabelPaint());
            	}        	
        		i++;
		}	
		return true;
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
			SplineData spData = mDataset.get(i);
			calcAllPoints( spData,mLstDots,mLstPoints);					
			
			switch(getCrurveLineStyle())
			{
				case BEZIERCURVE:								
					renderBezierCurveLine(canvas,mBezierPath,spData,mLstPoints);
					break;
				case BEELINE:				
					renderLine(canvas,spData,mLstPoints);	
					break;
				default:
					Log.e(TAG,"未知的枚举类型.");
					continue;				
			}								
			renderDotAndLabel(canvas,spData,i,mLstDots);								
			mLstKey.add(mDataset.get(i));
			
			mLstPoints.clear();
			mLstDots.clear();	
			mBezierPath.reset();
		}	
		//key
		plotLegend.renderLineKey(canvas,mLstKey);
		mLstKey.clear();
		return true;
	}
	
	@Override
	public boolean postRender(Canvas canvas) throws Exception {
		// TODO Auto-generated method stub
		boolean ret = true;
		try {
			super.postRender(canvas);
						
			//绘制图表
			if( (ret = renderPlot(canvas)) == true)
			{			
				//画曲线图，横向的定制线
				mCustomLine.setVerticalPlot(dataAxis, plotArea, getAxisScreenHeight());
				ret = mCustomLine.renderVerticalCustomlinesDataAxis(canvas);
			}
			
		}catch( Exception e){
			 throw e;
		}
		return ret;
	}
					
}
