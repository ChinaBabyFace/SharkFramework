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

import android.graphics.Paint;
import android.graphics.Paint.Align;

/**
 * @ClassName AxisTitle
 * @Description  图例(AxisTitle)基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * 
 */
public class AxisTitle {
	
	//图例文字画笔
	 private Paint mPaintLeftAxisTitle = null;
	 private Paint mPaintLowerAxisTitle = null;
	 private Paint mPaintRightAxisTitle = null;
	//图例文字说明
	 private String mLeftAxisTitle = "";
	 private String mLowerAxisTitle = "";
	 private String mRightAxisTitle = "";	 
	
	public AxisTitle()
	{							
		
	}
		
	private void initLeftAxisTitlePaint()
	{
		if(null == mPaintLeftAxisTitle)
		{
		 mPaintLeftAxisTitle = new Paint();
		 mPaintLeftAxisTitle.setTextAlign(Align.CENTER);
		 mPaintLeftAxisTitle.setAntiAlias(true);
		}
	}
	
	private void initLowerAxisTitlePaint()
	{
		if(null == mPaintLowerAxisTitle)
		{
		 mPaintLowerAxisTitle = new Paint();
		 mPaintLowerAxisTitle.setTextAlign(Align.CENTER);
		 mPaintLowerAxisTitle.setAntiAlias(true);
		}
	}
	
	private void initRightAxisTitlePaint()
	{
		if(null == mPaintRightAxisTitle)
		{
		 mPaintRightAxisTitle = new Paint();
		 mPaintRightAxisTitle.setTextAlign(Align.CENTER);
		 mPaintRightAxisTitle.setAntiAlias(true);
		}
	}
	
	/**
	 * 开放左边图例画笔
	 * @return 画笔
	 */
	public Paint getLeftAxisTitlePaint() {
		initLeftAxisTitlePaint();
		return mPaintLeftAxisTitle;
	}

	/**
	 * 开放底部图例画笔
	 * @return 画笔
	 */
	public Paint getLowerAxisTitlePaint() {
		initLowerAxisTitlePaint();
		return mPaintLowerAxisTitle;
	}

	/**
	 * 开放右边图例画笔
	 * @return 画笔
	 */
	public Paint getRightAxisTitlePaint() {
		initRightAxisTitlePaint();
		return mPaintRightAxisTitle;
	}

	/**
	 * 设置左边图例内容
	 * @param text 图例内容
	 */
	public void setLeftAxisTitle(String text) {
		this.mLeftAxisTitle = text;
	}

	/**
	 * 设置底部图例内容
	 * @param text 图例内容
	 */
	public void setLowerAxisTitle(String text) {
		this.mLowerAxisTitle = text;
	}


	/**
	 * 设置右边图例内容
	 * @param text 图例内容
	 */
	public void setRightAxisTitle(String text) {
		this.mRightAxisTitle = text;
	}
	
	/**
	 * 返回左边图例内容
	 * @return 图例内容
	 */
	public String getLeftAxisTitle() {
		return mLeftAxisTitle;
	}
	
	/**
	 * 返回底部图例内容
	 * @return 图例内容
	 */
	public String getLowerAxisTitle() {
		return mLowerAxisTitle;
	}
	
	/**
	 * 返回右边图例内容
	 * @return 图例内容
	 */
	public String getRightAxisTitle() {
		return mRightAxisTitle;
	}
	

}
