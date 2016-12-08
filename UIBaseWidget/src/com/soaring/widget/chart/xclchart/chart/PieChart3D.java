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
import android.graphics.PointF;
import android.util.Log;

import com.soaring.widget.chart.xclchart.common.DrawHelper;
import com.soaring.widget.chart.xclchart.common.MathHelper;

import java.util.List;

/**
 * @ClassName Pie3DChart
 * @Description  3D饼图基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class PieChart3D extends PieChart {
	
	private static final String TAG="PieChart3D";
	
	//渲染层数
	private final int mRender3DLevel = 15; 
	
		
	public PieChart3D() {
		// TODO Auto-generated constructor stub
		super();	 
		
	}
	
	private boolean render3D(Canvas canvas,
							float initOffsetAngle,
							List<PieData> chartDataSource,
							float cirX,float cirY,float radius)
	{		
 		float offsetAngle = initOffsetAngle;		
        float currentAngle = 0.0f;	              
        float newRadius = 0.0f;	
        int size = 0;
     
		for(int i=0;i < mRender3DLevel;i++)
		{
              canvas.save(Canvas.MATRIX_SAVE_FLAG);
              canvas.translate(0,mRender3DLevel - i );
              size = chartDataSource.size();
			  for(int j=0;j< size;j++)
			  {			  
				    PieData cData =  chartDataSource.get(j);
					currentAngle = cData.getSliceAngle();						
					if(!validateAngle(currentAngle)) continue;
					geArcPaint().setColor(cData.getSliceColor());	
					
				    if(cData.getSelected()) //指定突出哪个块
		            {				    			    	
				    	//偏移圆心点位置(默认偏移半径的1/10)
				    	newRadius = div(radius , SELECTED_OFFSET);
				    	 //计算百分比标签
				    	PointF point = MathHelper.getInstance().calcArcEndPointXY(
				    								cirX,cirY,newRadius,
				    								add(offsetAngle, div(currentAngle,2f))); 	
				        			        
				        initRectF("mRectF",sub(point.x, radius) ,sub(point.y , radius),
				        				   add(point.x , radius),add(point.y , radius));
				        
	                    canvas.drawArc(mRectF, offsetAngle, currentAngle, true,geArcPaint());
		            }else{
	                    canvas.drawArc(mArcRF0, offsetAngle, currentAngle, true,geArcPaint());
		            }			    			    
		            //下次的起始角度  
				    offsetAngle = add(offsetAngle,currentAngle);  	            		           
				}
	            canvas.restore();
	            offsetAngle = initOffsetAngle;
		}
		return true;
	}
	
	private boolean renderFlatArcAndLegend(Canvas canvas,
										float initOffsetAngle,
										List<PieData> chartDataSource,
										float cirX,float cirY,float radius)
	{
 		float offsetAngle = initOffsetAngle;				
        float currentAngle = 0.0f;	              
        float newRadius = 0.0f;	
        PointF[] arrPoint = new PointF[chartDataSource.size()];
					
        int size = chartDataSource.size();
		for(int j=0;j< size;j++)
		{
		 	PieData cData = chartDataSource.get(j);
		 	currentAngle = cData.getSliceAngle();
		 	if(!validateAngle(currentAngle)) continue;		  
		 	geArcPaint().setColor( DrawHelper.getInstance().getDarkerColor(
					cData.getSliceColor()) );
		  	
		    if(cData.getSelected()) //指定突出哪个块
            {					    					    	
		    	//偏移圆心点位置(默认偏移半径的1/10)
		    	newRadius = div(radius , SELECTED_OFFSET);
		    	 //计算百分比标签
		    	PointF point = MathHelper.getInstance().calcArcEndPointXY(
		    					cirX,cirY,newRadius,add(offsetAngle , div(currentAngle,2f))); 	
		          		        
		        initRectF("mRectF",sub(point.x , radius) ,sub(point.y , radius ),
		        				   add(point.x , radius ),add(point.y , radius));   
		        
                canvas.drawArc(mRectF, offsetAngle, currentAngle, true,geArcPaint());
                
		        arrPoint[j] = new PointF(point.x,point.y);
            }else{
                canvas.drawArc(mArcRF0, offsetAngle, currentAngle, true, geArcPaint());
                
     	       arrPoint[j] = new PointF(cirX,cirY);
     	    }		
		    
		    //保存角度
		    saveArcRecord(j,cirX,cirY,radius,offsetAngle,currentAngle);
		    
           //下次的起始角度  
		    offsetAngle = add(offsetAngle,currentAngle);
		}		
		
		//绘制Label
		renderLabels(canvas,initOffsetAngle,radius,arrPoint);	
				
		//图例
		plotLegend.renderPieKey(canvas,this.getDataSource());	
		arrPoint = null;
		return true;
	}


	@Override 
	protected boolean renderPlot(Canvas canvas)
	{				
		//数据源
 		List<PieData> chartDataSource = this.getDataSource();
 		if(null == chartDataSource)
		{
 			Log.e(TAG,"数据源为空.");
 			return false;
		}
 	 		
		//计算中心点坐标		
		float cirX = plotArea.getCenterX();
	    float cirY = plotArea.getCenterY();	     
        float radius = getRadius();
        
        //确定饼图范围       
        initRectF("mArcRF0",sub(cirX , radius) ,sub(cirY , radius),
        					add(cirX , radius),add(cirY , radius)); 
    
				
		if(render3D(canvas,mOffsetAngle,chartDataSource,cirX, cirY, radius))
		{
			return renderFlatArcAndLegend(canvas,mOffsetAngle,chartDataSource,
											cirX, cirY, radius);
		}else{
			return false;
		}
	}

}
