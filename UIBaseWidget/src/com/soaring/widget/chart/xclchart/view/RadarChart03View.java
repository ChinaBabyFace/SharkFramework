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
 * @Description Android图表基类库演示
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.3
 */
package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.PieData;
import com.soaring.widget.chart.xclchart.chart.RadarChart;
import com.soaring.widget.chart.xclchart.chart.RadarData;
import com.soaring.widget.chart.xclchart.chart.RoseChart;
import com.soaring.widget.chart.xclchart.common.IFormatterDoubleCallBack;
import com.soaring.widget.chart.xclchart.common.IFormatterTextCallBack;
import com.soaring.widget.chart.xclchart.renderer.XChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @ClassName RadarChart03View
 * @Description  圆形雷达图的例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class RadarChart03View extends TouchView {

	private String TAG = "RadarChart03View";
	private RadarChart chart = new RadarChart();
	
	private RoseChart chartRose = new RoseChart();
	LinkedList<PieData> roseData = new LinkedList<PieData>();
	
	private RoseChart chartRose3 = new RoseChart();
	LinkedList<PieData> roseData3 = new LinkedList<PieData>();
	
	
	//标签集合
	private List<String> labels = new LinkedList<String>();
	private List<RadarData> chartData = new LinkedList<RadarData>();
	
	
	public RadarChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		 initView();
	}
	
	public RadarChart03View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public RadarChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
			chartDataSet();	
			chartRender();
			
			chartRender2();
			chartDataSet2();
			
			chartRender3();
			chartDataSet3();
	 }
	 
	 
	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
        chartRose.setChartRange(w,h);
        chartRose3.setChartRange(w,h);
    }  	
	
	private void chartRender()
	{
		try{				
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
			
			chart.setTitle("雷达玫瑰混合图");
			chart.addSubtitle("(XCL-Charts Demo)");
			

			//设定数据源
			chart.setCategories(labels);								
			chart.setDataSource(chartData);
			//圆形雷达图
			chart.setChartType(XEnum.RadarChartType.ROUND);
		
			
			//数据轴最大值
			chart.getDataAxis().setAxisMax(100);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(20);
			
			chart.getLinePaint().setColor(Color.parseColor("#7579C3"));
			chart.getLabelPaint().setColor(Color.parseColor("#3EABEF"));
			chart.getLabelPaint().setFakeBoldText(true);
			chart.getDataAxis().setTickLabelVisible(false);
			
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
		dataSeriesA.add(0d); 
		dataSeriesA.add(0d);
		dataSeriesA.add(0d);
		dataSeriesA.add(0d);
		dataSeriesA.add(0d);
		
		RadarData lineData1 = new RadarData("",dataSeriesA,
				Color.rgb(234, 83, 71), XEnum.RadarDataAreaStyle.FILL);
		lineData1.setLabelVisible(false);	
		lineData1.getPlotLine().getDotLabelPaint().setTextAlign(Align.LEFT);
		
		chartData.add(lineData1);
		
	}
    
	private void chartLabels()
	{
		labels.add("说");
		labels.add("的");
		labels.add("就");
		labels.add("是");
		labels.add("你");
		
	}
	
	
	private void chartRender2()
	{
		try {						
			
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chartRose.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			//数据源
			chartRose.setDataSource(roseData);							
			chartRose.getInnerPaint().setStyle(Style.STROKE);			
			chartRose.setInitialAngle(270 + 72/2);
			
			//设置标签显示位置,当前设置标签显示在扇区中间
			chartRose.setLabelPosition(XEnum.SliceLabelPosition.OUTSIDE);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	
	private void chartDataSet3()
	{
		//设置图表数据源
							
		//PieData(标签，百分比，在饼图中对应的颜色)
		roseData.add(new PieData("",70, Color.rgb(77, 83, 97)));
		roseData.add(new PieData(""	,80, Color.rgb(148, 159, 181)));
		roseData.add(new PieData(""	,90, Color.rgb(253, 180, 90)));
		roseData.add(new PieData(""	,65, Color.rgb(52, 194, 188)));
		roseData.add(new PieData("",90, Color.rgb(39, 51, 72)));

	}
	
	
	private void chartRender3()
	{
		try {						
			
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chartRose3.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			//数据源
			chartRose3.setDataSource(roseData3);							
			chartRose3.getInnerPaint().setStyle(Style.STROKE);			
			chartRose3.setInitialAngle(270 + 72/2);
			
			//设置标签显示位置,当前设置标签显示在扇区中间
			chartRose3.setLabelPosition(XEnum.SliceLabelPosition.INSIDE);
			
			chartRose3.getLabelPaint().setColor(Color.parseColor("#D92222"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	
	private void chartDataSet2()
	{
		//设置图表数据源
							
		//PieData(标签，百分比，在饼图中对应的颜色)
		roseData3.add(new PieData("看",40, Color.rgb(31, 59, 123)));
		roseData3.add(new PieData("图"	 ,50, Color.rgb(173, 214, 224)));
		roseData3.add(new PieData("的"		 ,60, Color.rgb(233, 77, 67)));
		roseData3.add(new PieData("那" ,45, Color.rgb(191, 225, 84)));
		roseData3.add(new PieData("个",70, Color.rgb(0, 156, 214)));
	
	}
	
	
	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
            chartRose.render(canvas);
            chartRose3.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub		
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);		
		lst.add(chartRose);
		lst.add(chartRose3);
		return lst;
	}
	
	


}
