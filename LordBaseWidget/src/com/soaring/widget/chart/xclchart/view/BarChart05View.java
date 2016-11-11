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
 * @version v0.1
 */
package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.BarChart;
import com.soaring.widget.chart.xclchart.chart.BarData;
import com.soaring.widget.chart.xclchart.chart.CustomLineData;
import com.soaring.widget.chart.xclchart.common.DensityUtil;
import com.soaring.widget.chart.xclchart.common.IFormatterDoubleCallBack;
import com.soaring.widget.chart.xclchart.common.IFormatterTextCallBack;
import com.soaring.widget.chart.xclchart.renderer.XChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName BarChart05View
 * @Description  定制线横向柱形图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class BarChart05View extends TouchView implements Runnable{
	
	private String TAG = "BarChart05View";
	private BarChart chart = new BarChart();
	//轴数据源
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();
	
	public BarChart05View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		initView();
	}
	
	public BarChart05View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public BarChart05View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
			chartDataSet();
			chartCustomLines();
			chartRender();
			
			new Thread(this).start();
	 }
	 
	 
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  		
	
	private void chartRender()
	{
		try {
			
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(DensityUtil.dip2px(getContext(), 35),ltrb[1], ltrb[2],  ltrb[3]);
		
					
			//标题
			chart.setTitle("小小熊 - 期末考试成绩");
			chart.addSubtitle("(XCL-Charts Demo)");	
			//数据源
			//chart.setDataSource(chartData);
			chart.setCategories(chartLabels);	
			chart.setCustomLines(mCustomLineDataset);
			
			//图例
			chart.getAxisTitle().setLeftAxisTitle("分数");
			chart.getAxisTitle().setLowerAxisTitle("科目");
			
			//数据轴
			chart.getDataAxis().setAxisMax(100);
			chart.getDataAxis().setAxisMin(0);
			chart.getDataAxis().setAxisSteps(5);		
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(5);
			
			//背景网格
			chart.getPlotGrid().showHorizontalLines();
									
			//横向显示柱形,横向显示效果
			chart.setChartDirection(XEnum.Direction.HORIZONTAL);
			//chart.getPlotGrid().setVerticalLinesVisible(true);
			
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
			
			
			//在柱形顶部显示值
			chart.getBar().setItemLabelVisible(true);
			//设定格式
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("#0");					 
					String label = df.format(value).toString();
					return label;
				}});
			
			//隐藏Key
			chart.getPlotLegend().hideLegend();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void chartDataSet()
	{
		//标签对应的柱形数据集
		List<Double> dataSeriesA= new LinkedList<Double>();	
		dataSeriesA.add(98d); 
		dataSeriesA.add(100d); 
		dataSeriesA.add(95d); 
		dataSeriesA.add(100d); 
		BarData BarDataA = new BarData("",dataSeriesA, Color.rgb(53, 169, 239));
		
		
		chartData.add(BarDataA);
	}
	
	private void chartLabels()
	{
		chartLabels.add("语文"); 
		chartLabels.add("数学"); 
		chartLabels.add("英语"); 
		chartLabels.add("计算机"); 
	}	
	
	/**
	 * 定制线/分界线
	 */
	private void chartCustomLines()
	{				
		CustomLineData line1 = new CustomLineData("及格线",60d, Color.RED,3);
		line1.setCustomLineCap(XEnum.DotStyle.TRIANGLE);
		line1.setLabelVerticalAlign(XEnum.VerticalAlign.BOTTOM);
		line1.setLabelOffset(25);	
		line1.getLineLabelPaint().setColor(Color.RED);
		line1.setLineStyle(XEnum.LineStyle.DASH);
		mCustomLineDataset.add(line1);
		
		CustomLineData line2 = new CustomLineData("良好",80d, Color.rgb(35, 172, 57),5);
		line2.setCustomLineCap(XEnum.DotStyle.RECT);
		line2.setLabelVerticalAlign(XEnum.VerticalAlign.MIDDLE);
		
		line2.setLineStyle(XEnum.LineStyle.DOT);
		mCustomLineDataset.add(line2);
		
		CustomLineData line3 = new CustomLineData("优秀",90d, Color.rgb(53, 169, 239),5);
		line3.setCustomLineCap(XEnum.DotStyle.PRISMATIC);
		line3.setLabelOffset(15);
		line3.getLineLabelPaint().setColor(Color.rgb(216, 44, 41));
		
		line3.setLineStyle(XEnum.LineStyle.DOT);
		mCustomLineDataset.add(line3);
			
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
	public void run() {
		// TODO Auto-generated method stub
		try {          
         	chartAnimation();         	
         }
         catch(Exception e) {
             Thread.currentThread().interrupt();
         } 
	}
	
	private void chartAnimation()
	{
		  try {                            	            	 
          	          	
          	for(int i=0;i< chartData.size() ;i++)
          	{           		       		          		          		          	
          		BarData barData = chartData.get(i);
          		for(int j=0;j<barData.getDataSet().size();j++)
                {     
          			Thread.sleep(100);
          			List<BarData> animationData = new LinkedList<BarData>();
          			List<Double> dataSeries= new LinkedList<Double>();	
          			for(int k=0;k<=j;k++)
          			{          				
          				dataSeries.add(barData.getDataSet().get(k));    
          			}
          			
          			BarData animationBarData = new BarData("",dataSeries, Color.rgb(53, 169, 239));
          			animationData.add(animationBarData);
          			chart.setDataSource(animationData);
          			postInvalidate(); 
                }          		          		   
          	}
          	 
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }            
	}
}
