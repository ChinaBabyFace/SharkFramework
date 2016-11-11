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
package com.soaring.widget.chart.xclchart.event.touch;

import android.view.MotionEvent;
import android.view.View;

import com.soaring.widget.chart.xclchart.renderer.XChart;


/**
 * @ClassName ChartTouch
 * @Description  上下左右移动图表   		
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class ChartTouch implements IChartTouch {
	
	private View mView;
	private XChart mChart;
  
	//单点移动前的坐标位置
	private float oldX = 0.0f,oldY = 0.0f; 	
	
	public ChartTouch(View view, XChart chart) {
		this.mChart = chart;
		this.mView = view;
	}
	
	//用来设置图表的位置   	
	private void setLocation(float oldX, float oldY,float newX, float newY ) {
		// TODO Auto-generated method stub
		
		float xx = 0.0f,yy = 0.0f;		          
        float[] txy = mChart.getTranslateXY();		          
        xx =  txy[0];
        yy =  txy[1];
        
        if(newX < oldX || newY < oldY)	 
        {
      	  xx = txy[0] + newX - oldX;
      	  yy = txy[1] + newY - oldY;
        }  
        mChart.setTranslateXY(xx, yy);
  
        mChart.setChartRange(mChart.getLeft() + newX-oldX, mChart.getTop() + newY-oldY, 
    			mChart.getWidth(), mChart.getHeight());
    	mView.invalidate();		
	}
		
	@Override
	 public void handleTouch(MotionEvent event) {  
		
		 int action = event.getAction();
		    if ( action == MotionEvent.ACTION_MOVE) {
			      if (oldX >= 0 || oldY >= 0) {
				        float newX = event.getX(0);
				        float newY = event.getY(0);
				        
				        if(newX-oldX == 0 || newY-oldY == 0) return;
				        
				        setLocation(oldX,oldY,newX,newY );
				        
				        oldX = newX;
				        oldY = newY;
			      }
		    } else if (action == MotionEvent.ACTION_DOWN) {
			      oldX = event.getX(0);
			      oldY = event.getY(0);
		      
		    } else if (action == MotionEvent.ACTION_UP 
		    		|| action == MotionEvent.ACTION_POINTER_UP) {
			      oldX = 0.0f;
			      oldY = 0.0f;
			      if (action == MotionEvent.ACTION_POINTER_UP) {
			        oldX = -1f;
			        oldY = -1f;
			      }
		    }	        
	    }  
	 

}
