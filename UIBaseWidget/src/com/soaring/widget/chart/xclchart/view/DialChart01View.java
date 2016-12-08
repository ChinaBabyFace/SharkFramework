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

public class DialChart01View extends GraphicalView {

	private String TAG = "DialChart01View";	
	
	private DialChart chart = new DialChart();
	private float mPercentage = 0.9f;
	
	public DialChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public DialChart01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public DialChart01View(Context context, AttributeSet attrs, int defStyle) {
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
			chart.setBackgroundColor(Color.WHITE);
			//绘制边框
			chart.showRoundBorder();
					
			//设置当前百分比
			chart.getPointer().setPercentage(mPercentage);
			
			//设置指针长度
			chart.getPointer().setLength(0.8f);
			
			chart.setTotalAngle(180);
			chart.setStartAngle(180);
			
			//增加轴承
			addAxis();						
			/////////////////////////////////////////////////////////////
			//设置附加信息
			addAttrInfo();
			
			//设置指针
			addPointer();
			/////////////////////////////////////////////////////////////
			
			//chart.getPointer().getPointerPaint().setColor(Color.WHITE);
			chart.getPointer().setPointerStyle(XEnum.PointerStyle.TRIANGLE);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
		
	}
	
	public void addAxis()
	{
		/////////////////////////////////////////////////////////////		
		
		try{
			//开始设置轴			
			//轴1 --最外面的弧线轴
			chart.addArcLineAxis(1);
			
			//轴2 --外围的标签轴
			List<String> tickLabels = new ArrayList<String>();
			tickLabels.add("2006");
			tickLabels.add("");
			tickLabels.add("2008");
			tickLabels.add("");
			tickLabels.add("2010");
			tickLabels.add("");
			tickLabels.add("2012");			
			chart.addInnerTicksAxis(0.95f, tickLabels);
			
			//轴3 --环形颜色轴
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
			chart.addStrokeRingAxis(0.75f,0.6f, ringPercentage, rcolor);
			
			//轴4 -- 环下面的标签轴
			List<String> rlabels  = new ArrayList<String>();
			rlabels.add("a");
			rlabels.add("b");
			rlabels.add("c");
			rlabels.add("d");
			rlabels.add("e");	
			rlabels.add("f");
			chart.addOuterTicksAxis(0.6f, rlabels);
			
			//轴5 --  最里面的灰色底轴
			chart.addFillAxis(0.5f, Color.rgb(225, 230, 246));
			
			//轴6  -- 最里面的红色百分比例的轴
			List<Float> innerPercentage = new ArrayList<Float>();			
			innerPercentage.add(mPercentage);						
			List<Integer> innerColor  = new ArrayList<Integer>();			
			innerColor.add(Color.rgb(227, 64, 167));
			chart.addFillRingAxis(0.5f,innerPercentage, innerColor);
			
			/////////////////////////////////////////////////////////////
			//设置指定轴属性
			chart.getPlotAxis().get(0).getAxisPaint().setColor(Color.BLUE);								
			/////////////////////////////////////////////////////////////
			
			
			chart.addLineAxis(XEnum.Location.TOP,1.6f);
			chart.addLineAxis(XEnum.Location.BOTTOM,1.6f);
			chart.addLineAxis(XEnum.Location.LEFT,1.6f);
			chart.addLineAxis(XEnum.Location.RIGHT,1.6f);
			if(chart.getPlotAxis().size() >= 6)chart.getPlotAxis().get(6).getAxisPaint().setColor(Color.BLUE);		
			if(chart.getPlotAxis().size() >= 7)chart.getPlotAxis().get(7).getAxisPaint().setColor(Color.GREEN);		
			if(chart.getPlotAxis().size() >= 8)chart.getPlotAxis().get(8).getAxisPaint().setColor(Color.YELLOW);		
			if(chart.getPlotAxis().size() >= 9)chart.getPlotAxis().get(9).getAxisPaint().setColor(Color.RED);		
		
		}catch(Exception ex){
			Log.e(TAG,ex.toString());
		}				
	}
	
	
	private void addAttrInfo()
	{
		/////////////////////////////////////////////////////////////
		PlotAttrInfo plotAttrInfo = chart.getPlotAttrInfo();
		
		//设置附加信息
		Paint paintTB = new Paint();
		paintTB.setColor(Color.GRAY);
		paintTB.setTextAlign(Align.CENTER);
		paintTB.setTextSize(22);			
		plotAttrInfo.addAttributeInfo(XEnum.Location.TOP, "TOP info", 0.5f, paintTB);
		plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, "BOTTOM info", 0.5f, paintTB);
		
		Paint paintLR = new Paint();		
		paintLR.setTextAlign(Align.CENTER);
		paintLR.setTextSize(22);
		paintLR.setColor(Color.BLUE);			
		plotAttrInfo.addAttributeInfo(XEnum.Location.LEFT, "LEFT info", 0.5f, paintLR);
		plotAttrInfo.addAttributeInfo(XEnum.Location.RIGHT, "RIGHT info", 0.5f, paintLR);
		
		Paint paintBase = new Paint();		
		paintBase.setTextAlign(Align.CENTER);
		paintBase.setTextSize(22);
		paintBase.setColor(Color.rgb(56, 172, 240));
		plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM,
								"百分比:"+Float.toString(mPercentage * 100), 0.3f, paintBase);		
		/////////////////////////////////////////////////////////////
	}
	
	public void addPointer()
	{					
		chart.addPointer();
		chart.addPointer();
		
		List<Pointer> mp = chart.getPlotPointer();
		mp.get(0).setPercentage( mPercentage * 0.3f );
		mp.get(0).setLength(0.7f);	
		mp.get(0).getPointerPaint().setColor(Color.BLUE);
		
		mp.get(1).setLength(0.5f);
		mp.get(1).setPointerStyle(XEnum.PointerStyle.TRIANGLE);
		mp.get(1).setPercentage( mPercentage * 0.7f );	
		mp.get(1).getPointerPaint().setColor(Color.RED);
	}
	
	public void setCurrentStatus(float percentage)
	{			
		mPercentage = percentage;
		//清理
		chart.clearAll();
		
		//设置当前百分比
		chart.getPointer().setPercentage(mPercentage);
		
		//轴
		addAxis();
		//附加信息
		addAttrInfo();		
		//设置指针
		addPointer();
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
