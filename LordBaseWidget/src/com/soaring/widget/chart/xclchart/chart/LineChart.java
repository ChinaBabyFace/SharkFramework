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
import android.graphics.RectF;
import android.util.Log;

import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.LnChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;
import com.soaring.widget.chart.xclchart.renderer.line.PlotCustomLine;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDot;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDotRender;
import com.soaring.widget.chart.xclchart.renderer.line.PlotLine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName LineChart
 * @Description  线图基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class LineChart extends LnChart {
	
	private  static final  String TAG ="LineChart";
	
	//数据源
	protected List<LineData> mDataSet;
	
	//数据轴显示在左边还是右边
	private XEnum.LineDataAxisLocation mDataAxisPosition =
								XEnum.LineDataAxisLocation.LEFT;

	//用于绘制定制线(分界线)
	private PlotCustomLine mCustomLine = null;
	
	private boolean mLineAxisIntersectVisible = false;
	
	
	public LineChart()
	{
		super();
		initChart();
	}

	private void initChart()
	{		
		mCustomLine = new PlotCustomLine();
		defaultAxisSetting();		
				
		getDataAxis().getAxisPaint().setStrokeWidth(2);
		getDataAxis().getTickMarksPaint().setStrokeWidth(2);
		
		getCategoryAxis().getAxisPaint().setStrokeWidth(2);
		getCategoryAxis().getTickMarksPaint().setStrokeWidth(2);
	}
	
	/**
	 * 设置数据轴显示在哪边,默认是左边
	 * @param position 显示位置
	 */
	public void setDataAxisLocation( XEnum.LineDataAxisLocation position)
	{
		mDataAxisPosition = position;				
		defaultAxisSetting();
	}
	
	/**
	 * 依数据库显示位置，设置相关的默认值
	 */
	private void defaultAxisSetting()
	{
		if(XEnum.LineDataAxisLocation.LEFT == mDataAxisPosition)
		{
			categoryAxis.setHorizontalTickAlign(Align.CENTER);
			dataAxis.setHorizontalTickAlign(Align.LEFT);	
		}else{		
			dataAxis.setHorizontalTickAlign(Align.RIGHT);
			dataAxis.getTickLabelPaint().setTextAlign(Align.LEFT);			
		}	
	}
	 
		/**
		 * 分类轴的数据源
		 * @param categories 标签集
		 */
		public void setCategories( List<String> categories)
		{
			if(null == categories || categories.size() == 0)
			{
				Log.e(TAG,"分类轴不能为空.");
			}else							
				categoryAxis.setDataBuilding(categories);
		}
		
		/**
		 *  设置数据轴的数据源
		 * @param dataSet 数据源
		 */
		public void setDataSource( LinkedList<LineData> dataSet)
		{
			if(null == dataSet || dataSet.size() == 0)
			{
				Log.e(TAG,"数据轴不能为空.");				
			}else					
				if(null != mDataSet) mDataSet.clear();
				this.mDataSet = dataSet;		
		}			
						
		/**
		 * 设置定制线值
		 * @param customLineDataset 定制线数据集合
		 */
		public void setDesireLines( List<CustomLineData> customLineDataset)
		{
			mCustomLine.setCustomLines(customLineDataset);
		}
		
		
		/**
		 *  设置当值与底轴的最小值相等时，线是否与轴连结显示. 默认为False
		 * @param visible 是否连接
		 */
		public void setLineAxisIntersectVisible( boolean visible)
		{
			mLineAxisIntersectVisible = visible;
		}
		
		/**
		 * 返回当值与底轴的最小值相等时，线是否与轴连结的当前状态
		 * @return 状态
		 */
		public boolean getLineAxisIntersectVisible()
		{
			return mLineAxisIntersectVisible;
		}
		
		/**
		 * 绘制线
		 * @param canvas	画布
		 * @param bd		数据类
		 * @param type		处理类型
		 */
		private boolean renderLine(Canvas canvas, LineData bd,String type,int dataID)
		{
			float initX =  plotArea.getLeft();
            float initY =  plotArea.getBottom();
             
			float lineStartX = initX;
            float lineStartY = initY;
            float lineEndX = 0.0f;
            float lineEndY = 0.0f;
            													
			//得到分类轴数据集
			List<String> dataSet =  categoryAxis.getDataSet();
			if(null == dataSet){
				Log.e(TAG,"分类轴数据为空.");
				return false;
			}		
			//数据序列
			List<Double> chartValues = bd.getLinePoint();	
			if(null == chartValues)
			{
				Log.e(TAG,"线的数据序列为空.");
				return false;			
			}			
				
			//步长
			float axisScreenHeight = getAxisScreenHeight();
			float axisDataHeight = (float) dataAxis.getAxisRange();	
			float XSteps = 0.0f;	
			int j = 0,childID = 0;	
			if (dataSet.size() == 1) //label仅一个时右移
			{
				XSteps = div( getAxisScreenWidth(),(dataSet.size() ));
				j = 1;
			}else{
				XSteps = div( getAxisScreenWidth(),(dataSet.size() - 1));
			}
					
		    //画线
			for(Double bv : chartValues)
            {																	
				//参数值与最大值的比例  照搬到 y轴高度与矩形高度的比例上来 	                                             	
            	float vaxlen = (float) MathHelper.getInstance().sub(bv, dataAxis.getAxisMin());
				float fvper = div( vaxlen,axisDataHeight );
				float valuePostion = mul(axisScreenHeight, fvper);			    
            		                	
            	if(j == 0 )
				{
					lineStartX = initX;
					lineStartY = sub(initY , valuePostion);
					
					lineEndX = lineStartX;
					lineEndY = lineStartY;
				}else{
					lineEndX = initX + (j) * XSteps;
					lineEndY = sub(initY , valuePostion);
				}            	            	            	           	
                        	
            	if( getLineAxisIntersectVisible() == false &&
            			Double.compare(bv, dataAxis.getAxisMin()) == 0 )
            	{
            		//如果值与最小值相等，即到了轴上，则忽略掉  
            		lineStartX = lineEndX;
    				lineStartY = lineEndY;

    				j++;
            	}else{
	            	PlotLine pLine = bd.getPlotLine();
	            	if(type.equalsIgnoreCase("LINE"))
	            	{
	            		
	            		if(getLineAxisIntersectVisible() == true ||
	            					Float.compare(lineStartY, initY) != 0 )	
	            		{
	            			canvas.drawLine( lineStartX ,lineStartY ,lineEndX ,lineEndY,
	            												pLine.getLinePaint()); 
	            		}
	            	}else if(type.equalsIgnoreCase("DOT2LABEL")){
	            		
	            		if(!pLine.getDotStyle().equals(XEnum.DotStyle.HIDE))
	                	{                		       	
	                		PlotDot pDot = pLine.getPlotDot();
	                		float rendEndX  = lineEndX  + pDot.getDotRadius();               		
	            				                		
	                		RectF rect = PlotDotRender.getInstance().renderDot(canvas,pDot,
	                				lineStartX ,lineStartY ,
	                				lineEndX ,lineEndY,
	                				pLine.getDotPaint()); //标识图形            		
	                		
	                		savePointRecord(dataID,childID,lineEndX, lineEndY,rect);    
	                		childID++;
	                		
	            			lineEndX = rendEndX;	            			
	                	}
	            		
	            		if(bd.getLabelVisible()) //标签
	                	{	                		       		
	                		canvas.drawText(this.getFormatterItemLabel(bv), 
	    							lineEndX, lineEndY,  pLine.getDotLabelPaint());
	                	}
	            			            		
	            	}else{
	            		Log.e(TAG,"未知的参数标识。"); //我不认识你，我不认识你。
	            		return false;
	            	}      				
            	
					lineStartX = lineEndX;
					lineStartY = lineEndY;
	
					j++;
            	} //if(bv != dataAxis.getAxisMin())
            } 				
			return true;
		}

		
		
		/**
		 * 绘制图表
		 */
		private boolean renderVerticalPlot(Canvas canvas)
		{											
			if(XEnum.LineDataAxisLocation.LEFT == mDataAxisPosition)
			{
				renderVerticalDataAxis(canvas);
			}else{
				renderVerticalDataAxisRight(canvas);
			}						
			renderVerticalCategoryAxis(canvas);
			if(null == mDataSet) 
			{
				Log.e(TAG,"数据轴数据为空.");
				return false;
			}
			
			List<LnData> lstKey = new ArrayList<LnData>();
			String key = "";
			//开始处 X 轴 即分类轴                  
			for(int i=0;i<mDataSet.size();i++)
			{								
				if(renderLine(canvas,mDataSet.get(i),"LINE",i) == false) 
					return false;
				if(renderLine(canvas,mDataSet.get(i),"DOT2LABEL",i) == false) 
					return false;
				key = mDataSet.get(i).getLineKey();				
				if("" != key && key.length() > 0)
					lstKey.add(mDataSet.get(i));
			}			
			//图例
			plotLegend.renderLineKey(canvas, lstKey);
			return true;
		}	
		 
		
		//绘制图表	
		@Override
		protected boolean postRender(Canvas canvas) throws Exception
		{			
			boolean ret = true;
			try{
				super.postRender(canvas);				
				
				//画线形图
				if((ret = renderVerticalPlot(canvas)) == true)
				{				
					//画横向定制线
					mCustomLine.setVerticalPlot(dataAxis, plotArea, getAxisScreenHeight());
					ret = mCustomLine.renderVerticalCustomlinesDataAxis(canvas);	
				}
			} catch (Exception e) {
				throw e;
			}
			return ret;
		}
		
		
}
