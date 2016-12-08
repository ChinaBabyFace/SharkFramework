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
package com.soaring.widget.chart.xclchart.event.zoom;

import android.util.Log;
import android.view.View;

import com.soaring.widget.chart.xclchart.renderer.XChart;

/**
 * @ClassName ChartZoom
 * @Description  放大缩小图表  <br/>
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

//附注: 对于有放大缩小图，数据随着放大缩小或左右移动要求的，可在view中
// 通过控制图的Axis和Datasource重置来实现。
// 图表库本身其实应当只负责绘图的。唉。不能对图表库要求太多。
// 像动画之类完全都应当是在view中去操作的，见我写的Demo。

public class ChartZoom implements IChartZoom {
	
	private static final String TAG = "ChartZoom";	
	
	private View mView;
	private XChart mChart;
			
	//图原始的宽高
	private float mInitWidth = 0.0f;
	private float mInitHeight = 0.0f;
	
	//处理类型
	private static final int ZOOM_IN = 0; //放大
	private static final int ZOOM_OUT = 1; //缩小	
	
	//当前缩放比
	private float mScaleRate = 0.1f; 
	
	//所允许的最小缩小比,固定为0.5f;
	private static final float MIN_SCALE_RATE = 0.5f;
	//最小缩小比对应的宽高
	private float mScaleMinWidth = 0.0f;	
	private float mScaleMinHeight = 0.0f;
		
	public ChartZoom(View view, XChart chart) {
		this.mChart = chart;
		this.mView = view;
		
		mInitWidth = chart.getWidth();
		mInitHeight = chart.getHeight();
		
		mScaleMinWidth = (int)(MIN_SCALE_RATE * mInitWidth);
		mScaleMinHeight =  (int)(MIN_SCALE_RATE * mInitHeight);		
	}

	@Override
	public void setZoomRate(float rate) {
		// TODO Auto-generated method stub		
		mScaleRate = rate;
	}

	@Override
	public void zoomIn() {
		// TODO Auto-generated method stub
		 //放大
		reSize(ZOOM_IN);
	}

	@Override
	public void zoomOut() {
		// TODO Auto-generated method stub
		reSize(ZOOM_OUT);
	}

	//重置大小
	private void reSize(int flag)
	{
		float newWidth = 0.0f,newHeight = 0.0f;
		int scaleWidth = (int)(mScaleRate * mInitWidth);
	   	int scaleHeight =  (int)(mScaleRate * mInitHeight);	   	
	   	 
		if(ZOOM_OUT == flag) //缩小
		{			
			 newWidth = mChart.getWidth() - scaleWidth; 
		   	 newHeight = mChart.getHeight() - scaleHeight; 
		   	 
		   	 if(mScaleMinWidth > newWidth) newWidth = mScaleMinWidth;
		   	 if(mScaleMinHeight > newHeight) newHeight = mScaleMinHeight;		
		}else if(ZOOM_IN == flag){  //放大		 
			 newWidth = mChart.getWidth() + scaleWidth; 
		   	 newHeight = mChart.getHeight() + scaleHeight; 
		}else{
			Log.e(TAG, "不认识这个参数.");
			return ;
		}
		
		if( Float.compare(newWidth, 0) > 0 &&  Float.compare(newHeight,0) > 0 )
		{
			mChart.setChartRange(mChart.getLeft(),mChart.getTop(), newWidth, newHeight);
	   	 	mView.invalidate();
		}
	
		
	}
	
}
