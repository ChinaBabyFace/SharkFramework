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
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.BarChart;
import com.soaring.widget.chart.xclchart.chart.BarData;
import com.soaring.widget.chart.xclchart.chart.LineChart;
import com.soaring.widget.chart.xclchart.chart.LineData;
import com.soaring.widget.chart.xclchart.common.DensityUtil;
import com.soaring.widget.chart.xclchart.common.IFormatterDoubleCallBack;
import com.soaring.widget.chart.xclchart.common.IFormatterTextCallBack;
import com.soaring.widget.chart.xclchart.renderer.XChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;
import com.soaring.widget.chart.xclchart.renderer.axis.CategoryAxis;
import com.soaring.widget.chart.xclchart.renderer.axis.DataAxis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName MultiAxisChart02View
 * @Description  柱形图与折线图的结合例子,主要演示右轴
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class MultiAxisChart02View extends TouchView {

	private String TAG = "MultiAxisChart02View";
	private
	//标签轴
	List<String> chartLabels = new LinkedList<String>();
	List<BarData> chartData = new LinkedList<BarData>();
	
	//标签轴
	List<String> chartLabelsLn = new LinkedList<String>();
	LinkedList<LineData> chartDataLn = new LinkedList<LineData>();
	
	BarChart chart = new BarChart();
	LineChart lnChart = new LineChart();

	public MultiAxisChart02View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initChart();
	}
	
	/**
	 * 用于初始化
	 */
	private void initChart()
	{			
		chartLabels();
		chartDataLnSet();
		
		chartLnLabels();
		chartLnDataSet();	
		
		chartRender();
		chartLnRender();
	}

	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
        lnChart.setChartRange(w,h);
    }  	
	
	private void chartRender()
	{
		try {
						
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			
			chart.setChartDirection(XEnum.Direction.VERTICAL);
			//标题
			chart.setTitle("Virtual vs Native Oracle RAC Performance");
			chart.addSubtitle("(XCL-Charts Demo)");	
			//因为太长缩小标题字体
			//chart.getPlotTitle().getChartTitlePaint().setTextSize(20);
			//图例
			chart.getAxisTitle().setLeftAxisTitle("Orders Per Minute (OPM)");
			chart.getAxisTitle().setRightAxisTitle("Average Response Time (RT)");			
			
			//标签轴
			chart.setCategories(chartLabels);				
			//数据轴
			chart.setDataSource(chartData);
			chart.getDataAxis().setAxisMax(90000);
			chart.getDataAxis().setAxisSteps(10000);					
			
			//定制数据轴上的标签格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub				
				
				    double label=Double.parseDouble(value);
					DecimalFormat df=new DecimalFormat("#0");
					return df.format(label).toString();
				}
				
			});
			
			//定制标签轴标签的标签格式
			CategoryAxis categoryAxis = chart.getCategoryAxis();
			categoryAxis.setTickLabelRotateAngle(-15f);			
			categoryAxis.getTickLabelPaint().setTextSize(15);
			categoryAxis.getTickLabelPaint().setTextAlign(Align.CENTER);
			
			categoryAxis.setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub				
					//String tmp = "c-["+value+"]";
					return value;
				}
				
			});
			//定制柱形顶上的标签格式
			chart.getBar().setItemLabelVisible(true);
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					// DecimalFormat df=new DecimalFormat("#0.00");
					DecimalFormat df=new DecimalFormat("#0");
					return df.format(value).toString();
				}});	
			
			//网格背景
			chart.getPlotGrid().showHorizontalLines();
			chart.getPlotGrid().showEvenRowBgColor();
			chart.getPlotGrid().showOddRowBgColor();
			//隐藏Key值
			//chart.setPlotKeyVisible(false);	
			chart.getPlotLegend().hideLegend();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());			
		}
	}
	private void chartDataLnSet()
	{
		//标签1对应的柱形数据集
		List<Double> dataSeries1= new LinkedList<Double>();	
		dataSeries1.add(40000d); 
		dataSeries1.add(73000d); 
		//dataSeries1.add(400d); 
		
		List<Double> dataSeries2= new LinkedList<Double>();	
		dataSeries2.add(45000d); 
		dataSeries2.add(85000d); 
		//dataSeries2.add(450d); 

		BarData BarDataA = new BarData("Virtual OPM",dataSeries1, Color.rgb(0, 221, 177));
		BarData BarDataB = new BarData("Physical OPM",dataSeries2, Color.rgb(238, 28, 161));
					
		chartData.add(BarDataA);
		chartData.add(BarDataB);		
	}
	
	private void chartLabels()
	{
		
		chartLabels.add("4 Cores Per Node"); 
		chartLabels.add("8 Cores per Node"); 	
	}
	
	
	private void chartLnDataSet()
	{
		//标签1对应的数据集
		LinkedList<Double> virtual= new LinkedList<Double>();	
		virtual.add(0d); 			
		virtual.add(95d); 
		virtual.add(100d); 
		virtual.add(0d); 
		
		//标签2对应的数据集
		LinkedList<Double> physical= new LinkedList<Double>();				
		physical.add(0d); 
		physical.add(110d); 
		physical.add(125d); 
		physical.add(0d); 
		
		//将标签与对应的数据集分别绑定
		LineData lineData1 = new LineData("Virtual RT",virtual, Color.rgb(234, 83, 71));
		LineData lineData2 = new LineData("Physical RT",physical, Color.rgb(75, 166, 51));
		lineData1.setDotStyle(XEnum.DotStyle.TRIANGLE);
		lineData1.getDotPaint().setColor(Color.rgb(234, 83, 71));
		
		LinkedList<Double> BarKey1= new LinkedList<Double>();				
		BarKey1.add(0d); 
		LinkedList<Double> BarKey2= new LinkedList<Double>();				
		BarKey2.add(0d); 
		
		LineData lineDataBarKey1 = new LineData("Virtual OPM",BarKey1, Color.rgb(0, 221, 177));
		LineData lineDataBarKey2 = new LineData("Physical OPM",BarKey2, Color.rgb(238, 28, 161));
		lineDataBarKey1.setDotStyle(XEnum.DotStyle.RECT);
		lineDataBarKey2.setDotStyle(XEnum.DotStyle.RECT);
			
		chartDataLn.add(lineDataBarKey1);
		chartDataLn.add(lineDataBarKey2);	
		
		chartDataLn.add(lineData1);
		chartDataLn.add(lineData2);	
		
	}
	
	
	private void chartLnLabels()
	{
		chartLabelsLn.add(" "); 
		chartLabelsLn.add("4 Cores Per Node"); 
		chartLabelsLn.add("8 Cores per Node"); 	
		chartLabelsLn.add(" "); 
	}
	
	private void chartLnRender()
	{
		try {
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			lnChart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
					
			renderLnAxis();			
		
			lnChart.getPlotLegend().showLegend();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	/**
	 * 折线图轴相关
	 */
	private void renderLnAxis()
	{
		//标签轴
		lnChart.setCategories(chartLabelsLn);		
		lnChart.getCategoryAxis().setVisible(false);	
		
		//设定数据源						
		lnChart.setDataSource(chartDataLn);
		//数据轴
		lnChart.setDataAxisLocation(XEnum.LineDataAxisLocation.RIGHT);
		DataAxis dataAxis = lnChart.getDataAxis();
		dataAxis.setAxisMax(135);
		dataAxis.setAxisMin(0);
		dataAxis.setAxisSteps(15);			
		dataAxis.getAxisPaint().setColor(Color.rgb(51, 204, 204));
		dataAxis.getTickMarksPaint().setColor(Color.rgb(51, 204, 204));
	
		//定制数据轴上的标签格式
		lnChart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

			@Override
			public String textFormatter(String value) {
				// TODO Auto-generated method stub				
			
			    double label=Double.parseDouble(value);
				DecimalFormat df=new DecimalFormat("#0");
				return df.format(label).toString();
			}
			
		});		
	}
	
	@Override
	protected int[] getBarLnDefaultSpadding()
	{
		int [] ltrb = new int[4];
		ltrb[0] = DensityUtil.dip2px(getContext(), 40); //left
		ltrb[1] = DensityUtil.dip2px(getContext(), 56); //top
		ltrb[2] = DensityUtil.dip2px(getContext(), 40); //right
		ltrb[3] = DensityUtil.dip2px(getContext(), 36); //bottom
		return ltrb;
	}
	
	@Override
    public void render(Canvas canvas) {
        try{
        	chart.render(canvas);
        	lnChart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);		
		lst.add(lnChart);
		return lst;
	}
	
}
