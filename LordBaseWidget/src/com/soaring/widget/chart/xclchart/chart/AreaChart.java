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
 * v1.3 2014-8-30 xcl 增加平滑区域面积图
 */
package com.soaring.widget.chart.xclchart.chart;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.soaring.widget.chart.xclchart.common.CurveHelper;
import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.LnChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDot;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDotRender;
import com.soaring.widget.chart.xclchart.renderer.line.PlotLine;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName AreaChart
 * @Description  面积图基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class AreaChart extends LnChart {
	
	private static final String TAG="AreaChart";
	
	//画点分类的画笔
  	protected Paint mPaintAreaFill =  null; 
  	
    //数据源
  	protected List<AreaData> mDataset;
  	
  	//透明度
  	private int mAreaAlpha = 100;  	
  	
  	//path area
  	private List<PointF> mLstPathPoints =new ArrayList<PointF>(); 
  	private Path mPathArea = null;
  	private PointF[] mBezierControls = new PointF[2];
	
	//key
  	private List<LnData> mLstKey = new ArrayList<LnData>();
	
	//line
  	private List<PointF> mLstPoints = new ArrayList<PointF>();	
	
	//dots
  	private List<RectF> mLstDots =new ArrayList<RectF>();
  	
  	//平滑曲线
  	private XEnum.CrurveLineStyle mCrurveLineStyle = XEnum.CrurveLineStyle.BEZIERCURVE;
  	
			  	
	public AreaChart()
	{
		super();
	
		categoryAxis.setHorizontalTickAlign(Align.CENTER);
		dataAxis.setHorizontalTickAlign(Align.LEFT);		
	}
	
	private void initPaint()
	{
		if(null == mPaintAreaFill)
		{
			mPaintAreaFill = new Paint();
			mPaintAreaFill.setStyle(Style.FILL);
			mPaintAreaFill.setAntiAlias(true);
			mPaintAreaFill.setColor(Color.rgb(73, 172, 72));
		}
	}
		
				
	 /**
	 * 分类轴的数据源
	 * @param categories 分类集
	 */
	public void setCategories(List<String> categories)
	{				
		categoryAxis.setDataBuilding(categories);
	}
	
	/**
	 *  设置数据轴的数据源
	 * @param dataset 数据源
	 */
	public void setDataSource(List<AreaData> dataset)
	{		
		if(null != mDataset) mDataset.clear();		
		this.mDataset = dataset;		
	}
	
	/**
	 * 设置透明度,默认为100
	 * @param alpha 透明度
	 */
	public void setAreaAlpha(int alpha)
	{
		mAreaAlpha = alpha;
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
	
	

	private boolean calcAllPoints(AreaData bd,
			List<RectF> lstDots,
			List<PointF> lstPoints,
			List<PointF> lstPathPoints)
	{
		//数据源
		List<Double> chartValues = bd.getLinePoint();
		if(null == chartValues)
		{
			Log.e(TAG,"线数据集合为空.");
			return false;
		}				
				
		float initX =  plotArea.getLeft();
        float initY =  plotArea.getBottom();
         
		float lineStartX = initX;
        float lineStartY = initY;
        float lineStopX = 0.0f;   
        float lineStopY = 0.0f;   
        						
		float axisScreenHeight = getAxisScreenHeight();
		float axisDataHeight =  (float) dataAxis.getAxisRange();	
		float currLablesSteps = div(getAxisScreenWidth(), 
										(categoryAxis.getDataSet().size() -1));
				
		//path area
		lstPathPoints.add( new PointF(initX,initY));
		            
        double dper = 0d;
		int j = 0;	 
		for(Double bv : chartValues)
        {								
			//参数值与最大值的比例  照搬到 y轴高度与矩形高度的比例上来 	                                
        	//float valuePosition = (float) Math.round(
			//		axisScreenHeight * ( (bv - dataAxis.getAxisMin() ) / axisDataHeight)) ;        	            
        	dper = MathHelper.getInstance().sub(bv, dataAxis.getAxisMin());
        	float valuePosition = mul(axisScreenHeight, div(dtof(dper),axisDataHeight) );
        	
        	if(j == 0 )
			{
				lineStartX = initX;
				lineStartY = sub(initY , valuePosition);
				
				lineStopX = lineStartX ;
				lineStopY = lineStartY;	
			}else{
				lineStopX = add(initX , (j) * currLablesSteps);
				lineStopY = sub(initY , valuePosition);
			}        
        	
        	if(0 == j )
    		{
        		//line
        		lstPoints.add( new PointF(lineStartX,lineStartY));
        		lstPoints.add( new PointF(lineStopX,lineStopY));
    			
        		//path area
        		lstPathPoints.add( new PointF(lineStartX,lineStartY));
        		lstPathPoints.add( new PointF(lineStopX,lineStopY));
    		}else{     
    			//line
    			lstPoints.add( new PointF(lineStopX,lineStopY));
    			//path area
    			lstPathPoints.add( new PointF(lineStopX,lineStopY));
    		}            		
    
        	//dot
        	lstDots.add(new RectF(lineStartX,lineStartY,lineStopX,lineStopY));
   	
        	lineStartX = lineStopX;
			lineStartY = lineStopY;
			j++;
        }	
		
		//path area
		lstPathPoints.add( new PointF(lineStartX ,lineStartY));
		lstPathPoints.add( new PointF(lineStartX ,initY));
		return true;        
	}
	
	private boolean renderArea(Canvas canvas,Paint paintAreaFill,Path pathArea,
								AreaData areaData,
								List<PointF> lstPathPoints)
	{		        		
		for(int i = 0;i<lstPathPoints.size();i++)
		{
			PointF point = lstPathPoints.get(i);
        	if(0 == i)
        	{
        		pathArea.moveTo(point.x ,point.y);  
        	}else{
        		pathArea.lineTo(point.x ,point.y);   
        	}				
		}							
		pathArea.close();
	
        //设置当前填充色
		paintAreaFill.setColor(areaData.getAreaFillColor());	
		paintAreaFill.setAlpha(this.mAreaAlpha); 
		//绘制area
	    canvas.drawPath(pathArea, paintAreaFill);	      
	    pathArea.reset();		
		return true;
	}
	

	private boolean renderBezierArea(Canvas canvas, Paint paintAreaFill,Path bezierPath,
										AreaData areaData,
										List<PointF> lstPathPoints)
	{		        				
		if(null == bezierPath)bezierPath = new Path();

		//start point
		bezierPath.moveTo(plotArea.getLeft(), plotArea.getBottom());		
		
		for(int i = 0;i<lstPathPoints.size();i++)
		{
			if(i<3) continue;
			
			CurveHelper.curve3(lstPathPoints.get(i - 2),
					lstPathPoints.get(i - 1),
					lstPathPoints.get(i - 3),
					lstPathPoints.get(i),
					mBezierControls);
			
			bezierPath.cubicTo( mBezierControls[0].x, mBezierControls[0].y, 
					mBezierControls[1].x, mBezierControls[1].y, 
					lstPathPoints.get(i -1 ).x, lstPathPoints.get(i -1 ).y);		
		}			
	
	
		if(lstPathPoints.size()> 3)
		{			
			PointF stop  = lstPathPoints.get(lstPathPoints.size()-1);
			//PointF start = lstPathPoints.get(lstPathPoints.size()-2);						
			CurveHelper.curve3(lstPathPoints.get(lstPathPoints.size() - 2),
					stop,
					lstPathPoints.get(lstPathPoints.size() - 3),
					stop,
					mBezierControls);
			bezierPath.cubicTo( mBezierControls[0].x, mBezierControls[0].y, 
					mBezierControls[1].x, mBezierControls[1].y, 
					lstPathPoints.get(lstPathPoints.size() -1 ).x, 
					lstPathPoints.get(lstPathPoints.size() -1 ).y);							
		}							
		bezierPath.close();
		paintAreaFill.setColor(areaData.getAreaFillColor());	
		
		paintAreaFill.setAlpha(this.mAreaAlpha); 		
	    canvas.drawPath(bezierPath, paintAreaFill);		
	    bezierPath.reset();
		return true;
	}
	
	
	private boolean renderLine(Canvas canvas, AreaData areaData,
								List<PointF> lstPoints)
	{		        
		for(int i=0;i<lstPoints.size();i++)
        {	        	
        	if(0 == i)continue;
        	PointF pointStart = lstPoints.get(i - 1);
        	PointF pointStop = lstPoints.get(i);
        	            
  	        canvas.drawLine( pointStart.x ,pointStart.y ,pointStop.x ,pointStop.y,
  	    		  			areaData.getLinePaint()); 	
        }
		return true;
	}
	
	
	private boolean renderBezierCurveLine(Canvas canvas,Path bezierPath,
											AreaData areaData,List<PointF> lstPoints)
	{		        		
		renderBezierCurveLine(canvas,areaData.getLinePaint(),bezierPath,lstPoints); 		 
		return true;
	}

//	/**
//	 * 绘制区域
//	 * @param bd	数据序列
//	 * @param type	绘制类型
//	 * @param alpha 透明度
//	 */
	private boolean renderDotAndLabel(Canvas canvas, AreaData bd,int dataID,
										List<RectF> lstDots)
	{
		
		 PlotLine pLine = bd.getPlotLine();
		 if(pLine.getDotStyle().equals(XEnum.DotStyle.HIDE) == true
				 					&&bd.getLabelVisible() == false )
		 {
    	   return true;
		 }
		 int childID = 0;

		//数据源
		List<Double> chartValues = bd.getLinePoint();
		if(null == chartValues)
		{
			Log.e(TAG,"线数据集合为空.");
			return false;
		}	
		
		for(int i=0;i<lstDots.size();i++)
		{
			Double dv = chartValues.get(i);			
			RectF  dot = lstDots.get(i);
			
			if(!pLine.getDotStyle().equals(XEnum.DotStyle.HIDE))
        	{            	
        		PlotDot pDot = pLine.getPlotDot();
        		float rendEndX  = add(dot.right  , pDot.getDotRadius());    
        		            	      
        		RectF rect = PlotDotRender.getInstance().renderDot(canvas,pDot,
        				dot.left ,dot.top ,
        				dot.right ,dot.bottom,
        				pLine.getDotPaint()); //标识图形            			                	
        		dot.right = rendEndX;
    			this.savePointRecord(dataID,childID,dot.right ,dot.bottom,rect);  
    			childID++;
        	}
    		
    		if(bd.getLabelVisible())
        	{        			            		
        		canvas.drawText(this.getFormatterItemLabel(dv) ,
        				dot.right ,dot.bottom,  pLine.getDotLabelPaint());
        	} 
		}
		return true;
	}	
						
	private boolean renderVerticalPlot(Canvas canvas)
	{								
		if(null == mDataset)
		{
			Log.e(TAG,"数据源为空.");
			return false;
		}
		
		renderVerticalDataAxis(canvas);
		renderVerticalCategoryAxis(canvas);
		
		initPaint();
		if(null == mPathArea) mPathArea = new Path();
								
		//透明度。其取值范围是0---255,数值越小，越透明，颜色上表现越淡             
		//mPaintAreaFill.setAlpha( mAreaAlpha );  
						
		//开始处 X 轴 即分类轴                  
		for(int i=0;i<mDataset.size();i++)
		{					
			AreaData areaData = mDataset.get(i);
			
			calcAllPoints( areaData,mLstDots,mLstPoints,mLstPathPoints);					
			
			switch(getCrurveLineStyle())
			{
				case BEZIERCURVE:
					renderBezierArea(canvas,mPaintAreaFill,mPathArea,areaData,mLstPathPoints);
					renderBezierCurveLine(canvas,mPathArea,areaData,mLstPoints);
					break;
				case BEELINE:
					renderArea(canvas,mPaintAreaFill,mPathArea,areaData,mLstPathPoints);	
					renderLine(canvas,areaData,mLstPoints);	
					break;
				default:
					Log.e(TAG,"未知的枚举类型.");
					continue;				
			}								
			renderDotAndLabel(canvas,areaData,i,mLstDots);						
			mLstKey.add(areaData);
			
			mLstDots.clear();
			mLstPoints.clear();
			mLstPathPoints.clear();
		}							
		plotLegend.renderLineKey(canvas, mLstKey);
		mLstKey.clear();
		return true;
	}
			
	@Override
	protected boolean postRender(Canvas canvas) throws Exception 
	{
		try {
			super.postRender(canvas);
			
			//绘制图表
			return renderVerticalPlot(canvas);
		} catch (Exception e) {
			throw e;
		}
	}
	 
}
