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

import com.soaring.widget.chart.xclchart.common.DrawHelper;
import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.IRender;
import com.soaring.widget.chart.xclchart.renderer.XChart;

/**
 * @ClassName axisTitle
 * @Description  轴标题(axisTitle)绘制类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * 
 */

public class AxisTitleRender extends AxisTitle implements IRender {
			
	private XChart mChart = null;
	 	 	
	public AxisTitleRender()
	{
		super();
		
		getLeftAxisTitlePaint().setTextSize(26);
		getLeftAxisTitlePaint().setColor(Color.rgb(255, 153, 204));
		
		getLowerAxisTitlePaint().setTextSize(26);
		getLowerAxisTitlePaint().setColor(Color.rgb(58, 65, 83));
		
		getRightAxisTitlePaint().setTextSize(26);
		getRightAxisTitlePaint().setColor(Color.rgb(51, 204, 204));
	}
	
	/**
	 * 传入chart给轴标题类
	 * @param chart 图基类
	 */
	public void setRange(XChart chart)
	{
		mChart = chart;
	}


	@Override
	public boolean render(Canvas canvas) {
		// TODO Auto-generated method stub
		
		if(null == mChart) return false;
		
		if(this.getLeftAxisTitle().length() > 0)
		{
			drawLeftAxisTitle(canvas,getLeftAxisTitle(),mChart.getLeft(),mChart.getTop(),
										mChart.getRight(),mChart.getBottom());
		}
		
		if(this.getLowerAxisTitle().length() > 0)
		{						
			drawAxisTitleLower(canvas,getLowerAxisTitle(),mChart.getLeft(),mChart.getTop(),
					mChart.getRight(),mChart.getBottom());
		}
		
		if(this.getRightAxisTitle().length() > 0)
		{
			drawRightAxisTitle(canvas,getRightAxisTitle(),mChart.getLeft(),mChart.getTop(),
					mChart.getRight(),mChart.getBottom());
		}
		
		return true;
	}
	
			
	 /**
	  * 绘制左边轴标题
	  * @param axisTitle	内容
	  * @param left		左边X坐标
	  * @param top		上方Y坐标
	  * @param right	右边X坐标
	  * @param bottom	下方Y坐标
	  */
	public void drawLeftAxisTitle(Canvas canvas, String axisTitle,double left,double top,
											 double right,double bottom) 
	{							
		if(null == canvas) return ;
		
		//是否需要绘制轴标题
		if( 0 == axisTitle.length() || "" == axisTitle)return;
		
		 //计算图列宽度		 
		 double axisTitleTextHeight = DrawHelper.getInstance().getTextWidth(
				 										getLeftAxisTitlePaint(),axisTitle);
         
		 //画布与图表1/3的地方显示
		 float axisTitleTextStartX = (float) Math.round(left + getLeftAxisTitlePaint().getTextSize()) ; 		 
		 
         //轴标题Y坐标
		 float axisTitleTextStartY = Math.round(top + (bottom - top ) /2 + axisTitleTextHeight/2);
         
         //得到单个轴标题文字高度     		
         double axisTitleCharHeight =0d;
         
         for(int i= 0; i< axisTitle.length();i++)
         {        	 
        	 axisTitleCharHeight = DrawHelper.getInstance().getTextWidth(
        			 						getLeftAxisTitlePaint(),axisTitle.substring(i, i+1));        	 
        	 //绘制文字，旋转-90得到横向效果
        	 DrawHelper.getInstance().drawRotateText(axisTitle.substring(i, i+1),
					        			 axisTitleTextStartX,axisTitleTextStartY,
					        			 -90,
					        			 canvas, getLeftAxisTitlePaint() );
        	 axisTitleTextStartY -= axisTitleCharHeight;
         }
	}
	
	/**
	 * 绘制底部轴标题
	 * @param axisTitle	内容
	 * @param left		左边X坐标
	 * @param top		上方Y坐标
	 * @param right		右边X坐标
	 * @param bottom	下方Y坐标
	 */
	public void drawAxisTitleLower(Canvas canvas, String axisTitle,
								double left,double top,double right,double bottom)
	{         
         if(null == canvas) return ;
 		 //是否需要绘制轴标题
 		 if(""==axisTitle || 0 == axisTitle.length() )return;
 	
 		 //计算轴标题文字宽度		 
 		 double axisTitleTextHeight = DrawHelper.getInstance().getPaintFontHeight(
 				 												getLowerAxisTitlePaint());     
 		 
 		 /*
 		 //画布与图表1/3的地方显示
 		 float axisTitleTextStartX =  (float) Math.round(left +  (right - left) / 2) ; 		  
 		 float plotBottom =  mChart.getPlotArea().getBottom();
 		 float axisTitleY =(float) Math.abs(
 				plotBottom +
 				 ((bottom - plotBottom) / 2 - (axisTitleTextHeight/2)) ); 		 		
 		DrawHelper.getInstance().drawRotateText(axisTitle,
       		 axisTitleTextStartX,axisTitleY,0,canvas, getLowerAxisTitlePaint());
 		*/
 		float axisTitleTextStartX =  (float) Math.round(left +  (right - left) / 2) ;  		
 		double axisTitleY = MathHelper.getInstance().sub(mChart.getBottom() ,axisTitleTextHeight/2);
 		DrawHelper.getInstance().drawRotateText(axisTitle,
 	       		 axisTitleTextStartX,(float) axisTitleY,0,canvas, getLowerAxisTitlePaint());
	}
	
	/**
	 * 绘制右边轴标题
	 * @param axisTitle	内容
	 * @param left		左边X坐标
	 * @param top		上方Y坐标
	 * @param right		右边X坐标
	 * @param bottom	下方Y坐标
	 */
	public void drawRightAxisTitle(Canvas canvas,String axisTitle,
								double left,double top,double right,double bottom)
	{			
		if(null == canvas) return ;
		
		//是否需要绘制轴标题
		if( 0 == axisTitle.length() || "" == axisTitle)return;
		
		//计算图列高度，超过最大高度要用...表示,这个后面再加		 
		 float axisTitleTextHeight = DrawHelper.getInstance().getTextWidth(
				 										getRightAxisTitlePaint(),axisTitle);         
		 //画布与图表1/3的地方显示
		 float axisTitleTextStartX =  (float) Math.round(right - getRightAxisTitlePaint().getTextSize()) ;         
         //轴标题Y坐标
		 float axisTitleTextStartY = (float) Math.round(top + (bottom - top -  axisTitleTextHeight) /2 );	          
         //得到单个轴标题文字高度     		
 		 float axisTitleCharHeight = 0.0f ;
         for(int i= 0; i< axisTitle.length();i++)
         {        	 
        	 axisTitleCharHeight = DrawHelper.getInstance().getTextWidth(
        			 						getRightAxisTitlePaint(),axisTitle.substring(i, i+1));        	 
        	 //绘制文字，旋转-90得到横向效果
        	 DrawHelper.getInstance().drawRotateText(axisTitle.substring(i, i+1),
        			 axisTitleTextStartX,axisTitleTextStartY,90,canvas, getRightAxisTitlePaint() );
        	 axisTitleTextStartY += axisTitleCharHeight;
         }
	
	}


}
