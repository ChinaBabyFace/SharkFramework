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
 * @version v0.1
 */

package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.DialChart;
import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.XEnum;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotAttrInfo;
import com.soaring.widget.chart.xclchart.renderer.plot.Pointer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DialChart例子
 * @Description  仪表盘例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class DialChart03View extends GraphicalView {

	private String TAG = "DialChart03View";	
	
	private DialChart chart = new DialChart();
	private float mPercentage = 0.9f;
	
	public DialChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public DialChart03View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public DialChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 
	 private void initView()
	 {
		chartRender();
	 }
	 
	 @Override  
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	        chart.setChartRange(w ,h ); 
	    }  		
						
		public void chartRender()
		{
			try {								
							
				//设置标题背景			
				chart.setApplyBackgroundColor(true);
				chart.setBackgroundColor(Color.rgb(28, 129, 243));
				//绘制边框
				chart.showRoundBorder();
						
				//设置当前百分比
				chart.getPointer().setPercentage(mPercentage);
				
				//设置指针长度
				chart.getPointer().setLength(0.75f);
				
				//增加轴承
				addAxis();						
				/////////////////////////////////////////////////////////////
				//增加指针
				addPointer();
				//设置附加信息
				addAttrInfo();
				/////////////////////////////////////////////////////////////
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.toString());
			}
			
		}
		
		public void addAxis()
		{
			
			List<Float> ringPercentage = new ArrayList<Float>();			
			float rper = MathHelper.getInstance().div(1, 4); //相当于40%	//270, 4
			ringPercentage.add(rper);
			ringPercentage.add(rper);
			ringPercentage.add(rper);
			ringPercentage.add(rper);
			
			List<Integer> rcolor  = new ArrayList<Integer>();			
			rcolor.add(Color.rgb(242, 110, 131));
			rcolor.add(Color.rgb(238, 204, 71));
			rcolor.add(Color.rgb(42, 231, 250));
			rcolor.add(Color.rgb(140, 196, 27));
			chart.addStrokeRingAxis(0.95f,0.8f, ringPercentage, rcolor);
			
			
			List<String> rlabels  = new ArrayList<String>();
			rlabels.add("00M");
			rlabels.add("10M");
			rlabels.add("20M");
			rlabels.add("30M");
			rlabels.add("40M");	
			rlabels.add("50M");
			rlabels.add("60M");
			rlabels.add("70M");
			rlabels.add("80M");
			chart.addInnerTicksAxis(0.8f, rlabels);
			
						
			chart.getPlotAxis().get(0).getFillAxisPaint().setColor(Color.rgb(28, 129, 243));
			chart.getPlotAxis().get(1).getFillAxisPaint().setColor(Color.rgb(28, 129, 243));
			chart.getPlotAxis().get(1).getTickLabelPaint().setColor(Color.WHITE);
			chart.getPlotAxis().get(1).getTickMarksPaint().setColor(Color.WHITE);
			chart.getPlotAxis().get(1).setAxisLineVisible(false);
			chart.getPlotAxis().get(1).setDetailModeSteps(3);
			
			chart.getPointer().setPointerStyle(XEnum.PointerStyle.TRIANGLE);
			chart.getPointer().getPointerPaint().setColor(Color.rgb(217, 34, 34));
			chart.getPointer().getPointerPaint().setStrokeWidth(3);			
			chart.getPointer().getPointerPaint().setStyle(Style.STROKE);			
			chart.getPointer().hideBaseCircle();
			
		}
		
		//增加指针
		public void addPointer()
		{					
			chart.addPointer();			
			List<Pointer> mp = chart.getPlotPointer();
			mp.get(0).setPercentage( mPercentage);
			//设置指针长度
			mp.get(0).setLength(0.75f);	
			mp.get(0).getPointerPaint().setColor(Color.WHITE);
			mp.get(0).setPointerStyle(XEnum.PointerStyle.TRIANGLE);
			mp.get(0).hideBaseCircle();
			
		}
		
		
		private void addAttrInfo()
		{
			/////////////////////////////////////////////////////////////
			PlotAttrInfo plotAttrInfo = chart.getPlotAttrInfo();
			//设置附加信息
			Paint paintTB = new Paint();
			paintTB.setColor(Color.WHITE);
			paintTB.setTextAlign(Align.CENTER);
			paintTB.setTextSize(30);	
			paintTB.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.TOP, "当前网速", 0.3f, paintTB);
			
			Paint paintBT = new Paint();
			paintBT.setColor(Color.WHITE);
			paintBT.setTextAlign(Align.CENTER);
			paintBT.setTextSize(35);
			paintBT.setFakeBoldText(true);
			paintBT.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM,
					Float.toString(MathHelper.getInstance().round(mPercentage * 100,2)), 0.3f, paintBT);
			
			Paint paintBT2 = new Paint();
			paintBT2.setColor(Color.WHITE);
			paintBT2.setTextAlign(Align.CENTER);
			paintBT2.setTextSize(30);
			paintBT2.setFakeBoldText(true);
			paintBT2.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, "MB/S", 0.4f, paintBT2);
		}
		
		public void setCurrentStatus(float percentage)
		{								
			mPercentage =  percentage;
			chart.clearAll();
			
			//设置当前百分比
			chart.getPointer().setPercentage(mPercentage);
			addAxis();
			//增加指针
			addPointer();
			addAttrInfo();
		}


		@Override
		public void render(Canvas canvas) {
			// TODO Auto-generated method stub
			 try{
		            chart.render(canvas);
		        } catch (Exception e){
		        	Log.e(TAG, e.toString());
		        }
		}

}
