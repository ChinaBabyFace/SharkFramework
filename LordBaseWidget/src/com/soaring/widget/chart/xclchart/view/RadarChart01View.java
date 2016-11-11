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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.soaring.widget.chart.xclchart.chart.RadarChart;
import com.soaring.widget.chart.xclchart.chart.RadarData;
import com.soaring.widget.chart.xclchart.common.IFormatterDoubleCallBack;
import com.soaring.widget.chart.xclchart.common.IFormatterTextCallBack;
import com.soaring.widget.chart.xclchart.event.click.PointPosition;
import com.soaring.widget.chart.xclchart.renderer.XChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName RadarChart01View
 * @Description  蜘蛛雷达图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class RadarChart01View extends TouchView {

	private String TAG = "RadarChart01View";
	private RadarChart chart = new RadarChart();
	
	
	//标签集合
	private List<String> labels = new LinkedList<String>();
	private List<RadarData> chartData = new LinkedList<RadarData>();
	
	
	public RadarChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		 initView();
	}
	
	public RadarChart01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public RadarChart01View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
			chartDataSet();	
			chartRender();
	 }
	 
	 
	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  	
	
	private void chartRender()
	{
		try{				
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
			
			chart.setTitle("蜘蛛雷达图");
			chart.addSubtitle("(XCL-Charts Demo)");
			

			//设定数据源
			chart.setCategories(labels);								
			chart.setDataSource(chartData);
			
			//点击事件处理
			chart.ActiveListenItemClick();
			chart.extPointClickRange(50);
			
			
			//数据轴最大值
			chart.getDataAxis().setAxisMax(50);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(10);
			//主轴标签偏移50，以便留出空间用于显示点和标签
			chart.getDataAxis().setTickLabelMargin(50);
			
			//定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub		
					Double tmp = Double.parseDouble(value);
					DecimalFormat df=new DecimalFormat("#0");
					String label = df.format(tmp).toString();				
					return (label);
				}
				
			});
			
			//定义数据点标签显示格式
			chart.setDotLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df= new DecimalFormat("#0");					 
					String label = "["+df.format(value).toString()+"]";
					return label;
				}});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
		
	}
	
	private void chartDataSet()
	{
		LinkedList<Double> dataSeriesA= new LinkedList<Double>();	
		dataSeriesA.add(20d); 
		dataSeriesA.add(10d); 
		dataSeriesA.add(30d); 
		dataSeriesA.add(25d);
		dataSeriesA.add(20d);
		
		RadarData lineData1 = new RadarData("现状",dataSeriesA,
				Color.rgb(234, 83, 71), XEnum.RadarDataAreaStyle.FILL);
		lineData1.setLabelVisible(true);	
		lineData1.getPlotLine().getDotLabelPaint().setTextAlign(Align.LEFT);
		
		LinkedList<Double> dataSeriesB= new LinkedList<Double>();	
		dataSeriesB.add((double)30); 
		dataSeriesB.add((double)20); 
		dataSeriesB.add((double)35); 	
		dataSeriesB.add((double)30); 
		dataSeriesB.add((double)40); 
		RadarData lineData2 = new RadarData("短期目标",dataSeriesB,
				Color.rgb(75, 166, 51), XEnum.RadarDataAreaStyle.STROKE);
		//lineData2.setDotStyle(XEnum.DotStyle.RING);				
		lineData2.getPlotLine().getDotPaint().setColor(Color.BLACK);
		
		
		LinkedList<Double> dataSeriesC= new LinkedList<Double>();	
		dataSeriesC.add(40d); 
		dataSeriesC.add(30d); 
		dataSeriesC.add(40d); 
		dataSeriesC.add(35d);
		dataSeriesC.add(45d);
		
		RadarData lineData3 = new RadarData("长期目标",dataSeriesC,
				Color.rgb(224, 53, 49), XEnum.RadarDataAreaStyle.STROKE);
		lineData3.setLineStyle(XEnum.LineStyle.DASH);
		lineData3.getPlotLine().setDotStyle(XEnum.DotStyle.RING);
		
		
		chartData.add(lineData1);
		chartData.add(lineData2);
		chartData.add(lineData3);
		
	}
	
	private void chartLabels()
	{
		labels.add("战略匹配");
		labels.add("组织有效");
		labels.add("流程可行");
		labels.add("有效落地");
		labels.add("高效决策");
	}
	
	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub		
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);		
		return lst;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_UP) 
		{			
			triggerClick(event.getX(),event.getY());			
		}
		return true;
	}
	
	//触发监听
	private void triggerClick(float x,float y)
	{
		PointPosition record = chart.getPositionRecord(x,y);
		if( null == record) return;
			
		if(record.getDataID() < chartData.size())
		{
			RadarData lData = chartData.get(record.getDataID());
			Double lValue = lData.getLinePoint().get(record.getDataChildID());
			
			Toast.makeText(this.getContext(), 					
					" Current Value:"+Double.toString(lValue) +
					" Point info:"+record.getPointInfo() , 
					Toast.LENGTH_SHORT).show();			
		}
	}

}
