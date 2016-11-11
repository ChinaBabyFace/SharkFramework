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

package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.soaring.widget.chart.xclchart.event.touch.ChartTouch;
import com.soaring.widget.chart.xclchart.event.zoom.ChartZoom;
import com.soaring.widget.chart.xclchart.event.zoom.IChartZoom;
import com.soaring.widget.chart.xclchart.renderer.XChart;

import java.util.List;

/**
 * @ClassName TouchView
 * @Description 可继承这个类用来放大缩小及手势移动图表
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com) QQ群: 374780627
 */

public abstract class TouchView  extends GraphicalView implements IChartZoom {
	
	private String TAG = "TouchView";
	
	private ChartZoom mChartZoom[];
	private ChartTouch mChartTouch[];
	
	//处理类型
	private static final int INIT_ZOOM = 0; 
	private static final int INIT_TOUCH = 1;
		
	
	public TouchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}	
	
	 public TouchView(Context context, AttributeSet attrs){   
	        super(context, attrs);   
	        
	 }
	 
	 public TouchView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		
	 }
	 
	
	public abstract List<XChart> bindChart();
	
	private boolean initArrayZoom()
	{		
		if(null != mChartZoom ) return true;
		return initArray(INIT_ZOOM);
	}
	 
	private boolean initArrayTouch()
	{
		if(null != mChartTouch ) return true;
		return initArray(INIT_TOUCH);
	}	
	
	private boolean initArray(int flag)
	{				
		 List<XChart> listCharts  = bindChart();
		 int count = listCharts.size();
		 
		 if(0 == count)
		 {
			 Log.e(TAG,"没有绑定相关的图表对象!!!");
			 return false;
		 }
		 
		 if( INIT_ZOOM == flag)
		 {
			 mChartZoom = new ChartZoom[count];
		 }else if( INIT_TOUCH == flag){
			 mChartTouch = new ChartTouch[count];
		 }else{
			Log.e(TAG,"这个参数不认识噢!!!");
			return false;
		 }
		 				
		 for(int i=0;i<count;i++)
		 {							 
			if( INIT_ZOOM == flag)
			{
				mChartZoom[i] = new ChartZoom(this,listCharts.get(i));
			}else if( INIT_TOUCH == flag){
				mChartTouch[i] = new ChartTouch(this,listCharts.get(i));
			}
		 }		 
		return true;
	}
	 
	@Override
	public void setZoomRate(float rate) {
		// TODO Auto-generated method stub
	
		if(!initArrayZoom())return;			
		if(null == mChartZoom) return;			
			
		for(int i=0;i<mChartZoom.length;i++)
		{
			mChartZoom[i].setZoomRate(rate);
		}		
	}

	@Override
	public void zoomIn() {
		// TODO Auto-generated method stub
		
		if(!initArrayZoom())return;
		if(null == mChartZoom) return;
		
		for(int i=0;i<mChartZoom.length;i++)
		{
			mChartZoom[i].zoomIn();
		}	
	}

	@Override
	public void zoomOut() {
		// TODO Auto-generated method stub
		
		if(!initArrayZoom())return;
		if(null == mChartZoom) return;
		
		for(int i=0;i<mChartZoom.length;i++)
		{
			mChartZoom[i].zoomOut();
		}		
	}

	@Override
	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub		
		
		if(null == mChartTouch)
				if(!initArrayTouch())return false;
		
		for(int i=0;i<mChartTouch.length;i++)
		{
			mChartTouch[i].handleTouch(event);		
		} 	
		
		
		return true;
	}
}
