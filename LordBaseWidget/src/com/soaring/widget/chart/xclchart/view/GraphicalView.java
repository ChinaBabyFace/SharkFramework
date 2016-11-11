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
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */
package com.soaring.widget.chart.xclchart.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.soaring.widget.chart.xclchart.common.DensityUtil;
import com.soaring.widget.chart.xclchart.common.SysinfoHelper;

/**
 * @ClassName GraphicalView
 * @Description  展示XCL-Charts图表的View基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com) QQ群: 374780627
 */

@SuppressLint("NewApi")
public abstract class GraphicalView extends View {

	private String TAG = "GraphicalView";	
	protected int mScrWidth = 0;
	protected int mScrHeight = 0;
	
	public GraphicalView(Context context) {
		super(context);	
		initView();		
	}
	
	 public GraphicalView(Context context, AttributeSet attrs){   
	        super(context, attrs);   
	        initView();
	 }
	 
	 public GraphicalView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
		
	  public abstract void render(Canvas canvas);
	
	  public void onDraw(Canvas canvas)
	  {		 
		  try {	
			 
			/*
			//绘制出view所占范围
	         RectF rect = new RectF();
	         rect.left = 1f;
	         rect.right = getMeasuredWidth() -1 ;
	         rect.top = 1f;  
	         rect.bottom = this.getMeasuredHeight() - 1;	  
	        
	         Paint paint = new Paint();
		     paint.setColor(Color.BLUE);
		     paint.setStyle(Style.STROKE);		       
	         canvas.drawRect(rect, paint);
		     */ 
			 
			  render(canvas);	    	 		
		  } catch (Exception e) {
				// TODO Auto-generated catch block
			  Log.e(TAG, e.toString());
		  }	
	   }
	  
	  private void initView()
	  {
		//禁用硬件加速
			disableHardwareAccelerated();	
			//得到屏幕信息
			getScreenInfo();
	  }
	  
	/**
	 * 禁用硬件加速.
	 * 原因:android自3.0引入了硬件加速，即使用GPU进行绘图,但它并不能完善的支持所有的绘图，
	 * 通常表现为内容(如Rect或Path)不可见，异常或渲染错误。所以类了保证图表的正常显示，强制禁用掉.
	 */
	private void disableHardwareAccelerated()
	{			
		if(SysinfoHelper.getInstance().supportHardwareAccelerated())
		{		
			//是否开启了硬件加速,如开启将其禁掉
			if(!isHardwareAccelerated())
			{
				setLayerType(View.LAYER_TYPE_SOFTWARE,null); 
			}
		}
	}
	
	/**
	 * 得到屏幕信息,当有父控件在时，取View的宽高，否则取屏幕的宽高
	 */	
	private void getScreenInfo()
	{				
		DisplayMetrics dm = getResources().getDisplayMetrics();
		mScrWidth = dm.widthPixels;
		mScrHeight = dm.heightPixels;
	}

	public int getScreenWidth() {
		return mScrWidth;
	}

	public int getScreenHeight() {
		return mScrHeight;
	}
	
	//Demo中bar chart所使用的默认偏移值。
	//偏移出来的空间用于显示tick,axistitle....
	protected int[] getBarLnDefaultSpadding()
	{
		int [] ltrb = new int[4];
		ltrb[0] = DensityUtil.dip2px(getContext(), 40); //left
		ltrb[1] = DensityUtil.dip2px(getContext(), 60); //top
		ltrb[2] = DensityUtil.dip2px(getContext(), 20); //right
		ltrb[3] = DensityUtil.dip2px(getContext(), 40); //bottom
		return ltrb;
	}
	
	protected int[] getPieDefaultSpadding()
	{
		int [] ltrb = new int[4];
		ltrb[0] = DensityUtil.dip2px(getContext(), 20); //left
		ltrb[1] = DensityUtil.dip2px(getContext(), 65); //top
		ltrb[2] = DensityUtil.dip2px(getContext(), 20); //right
		ltrb[3] = DensityUtil.dip2px(getContext(), 20); //bottom
		return ltrb;
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
	}
	
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) { //fill_parent
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) { //wrap_content
			result = Math.min(result, specSize);
		}
		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) { //fill_parent
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) { //wrap_content
			result = Math.min(result, specSize);
		}
		return result;
	}

	
}
