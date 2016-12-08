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

import android.graphics.Color;
import android.graphics.Paint;

/**
 * @ClassName PlotLegend
 * @Description 用于设定图例的属性
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *
 */
public class PlotLegend {
	
		//数据集的说明描述与图这间的空白间距
		private float mDataKeyMargin  = 10f;	
		//数据集的说明描述画笔
		private Paint mDataKeyPaint = null;
			
		//是否显示图例
		private boolean mKeyLabelVisible = false;
		
		//图例起始偏移多少距离
		protected float mOffsetX = 0.0f;
		protected float mOffsetY = 0.0f;
		
		
		public PlotLegend() {
			initChart();		
		}
		/**
		 * 初始化设置
		 */
		private void initChart()
		{	
			mDataKeyPaint = new Paint();
			mDataKeyPaint.setColor(Color.BLACK);
			mDataKeyPaint.setAntiAlias(true);			
			mDataKeyPaint.setTextSize(15);
			//mDataKeyPaint.setStyle(Style.FILL);			
		}

		/**
		 * 在图的上方显示图例
		 * 
		 */
		public void showLegend()
		{
			mKeyLabelVisible = true;
		}
		
		/**
		 * 在图的上方不显示图例
		 */
		public void hideLegend()
		{
			mKeyLabelVisible = false;
		}
						
		/**
		 * 是否需绘制图的图例
		 * @return 是否显示
		 */
		public boolean isShowLegend()
		{
			return mKeyLabelVisible;
		}
		 
		 /**
		  * 开放图例绘制画笔
		  * @return 画笔
		  */
		 public Paint getLegendLabelPaint()
		 {		 
			 return mDataKeyPaint;
		 }
		 
		 /**
		  * 设置图例间距
		  * @param margin Key间距
		  */
		 public void setLegendLabelMargin(float margin)
		 {		 
			 mDataKeyMargin = margin;
		 }
		 
		 /**
		  * 返回图例间距
		  * @return Key间距
		  */
		 public float getLegendLabelMargin()
		 {
			 return mDataKeyMargin;
		 }
		 
		 /**
		  * 图例起始向X方向偏移多少距离
		  * @param offset 偏移值
		  */
		 public void setLegendOffsetX(float offset)
		 {		 
			 mOffsetX = offset;
		 }
		 
		 /**
		  * 图例起始向Y方向偏移多少距离
		  * @param offset 偏移值
		  */
		 public void setLegendOffsetY(float offset)
		 {		 
			 mOffsetY = offset;
		 }

}
