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
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.soaring.widget.chart.xclchart.chart.BarChart;
import com.soaring.widget.chart.xclchart.chart.BarData;
import com.soaring.widget.chart.xclchart.common.IFormatterDoubleCallBack;
import com.soaring.widget.chart.xclchart.common.IFormatterTextCallBack;
import com.soaring.widget.chart.xclchart.event.click.BarPosition;
import com.soaring.widget.chart.xclchart.renderer.XChart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName BarChart01View
 * @Description  柱形图例子(竖向) <br/>
 * 	问动画效果的人太多了，其实图表库就应当只管绘图，动画效果就交给View或SurfaceView吧,
 * 	看看我弄的效果有多靓. ~_~
 *  
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class BarChart01View extends TouchView implements Runnable{
	
	private String TAG = "BarChart01View";
	private BarChart chart = new BarChart();
	
	//标签轴
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	
	public BarChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();				
	}
	
	public BarChart01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public BarChart01View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartLabels();
			chartDataSet();
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
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);			
			
			
			//标题
			chart.setTitle("主要数据库分布情况");
			chart.addSubtitle("(XCL-Charts Demo)");	
			chart.getPlotTitle().getTitlePaint().setColor(Color.BLUE);
			chart.getPlotTitle().getSubtitlePaint().setColor(Color.BLUE);
			//数据源
			//chart.setDataSource(chartData);
			chart.setCategories(chartLabels);	
			
			//轴标题
			chart.getAxisTitle().setLeftAxisTitle("数据库个数");
			chart.getAxisTitle().setLowerAxisTitle("分布位置");
			
			//数据轴
			chart.getDataAxis().setAxisMax(100);
			chart.getDataAxis().setAxisMin(0);
			chart.getDataAxis().setAxisSteps(5);
			
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
			
			 //让柱子间没空白
			 //chart.getBar().setBarInnerMargin(0d);
		
			
			//轴颜色
			int axisColor = Color.BLUE; // Color.rgb(222, 136, 166);			
			chart.getDataAxis().getAxisPaint().setColor(axisColor);
			chart.getCategoryAxis().getAxisPaint().setColor(axisColor);			
			chart.getDataAxis().getTickMarksPaint().setColor(axisColor);
			chart.getCategoryAxis().getTickMarksPaint().setColor(axisColor);
			
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(5);
			
			//激活点击监听
			chart.ActiveListenItemClick();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
		//标签对应的柱形数据集
		List<Double> dataSeriesA= new LinkedList<Double>();	
		dataSeriesA.add(66d); 
		dataSeriesA.add(33d); 
		dataSeriesA.add(50d);
		BarData BarDataA = new BarData("Oracle",dataSeriesA, Color.rgb(186, 20, 26));
		
		
		List<Double> dataSeriesB= new LinkedList<Double>();	
		dataSeriesB.add(32d);
		dataSeriesB.add(22d);
		dataSeriesB.add(18d);
		BarData BarDataB = new BarData("SQL Server",dataSeriesB, Color.rgb(1, 188, 242));
		
		List<Double> dataSeriesC= new LinkedList<Double>();	
		dataSeriesC.add(79d);
		dataSeriesC.add(91d);
		dataSeriesC.add(65d);
		BarData BarDataC = new BarData("MySQL",dataSeriesC, Color.rgb(0, 75, 106));
		
		chartData.add(BarDataA);
		chartData.add(BarDataB);
		chartData.add(BarDataC);
	}
	
	private void chartLabels()
	{
		chartLabels.add("福田数据中心"); 
		chartLabels.add("西丽数据中心"); 
		chartLabels.add("观澜数据中心"); 
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
          	List<Double> dataSeries= new LinkedList<Double>();	
          	dataSeries.add(0d);       
          	for(int i=0;i< chartData.size() ;i++)
          	{
          		Thread.sleep(100);
          		List<BarData> animationData = new LinkedList<BarData>();
          		for(int j=0;j<chartData.size();j++)
                {            			            			          			
          			if(j <= i)
          			{
          				animationData.add(chartData.get(j));
          			}else{
          				animationData.add(new BarData());
          			}
                }             		
          		if(chartData.size() - 1  == i)
          		{          			
          			chart.getPlotLegend().showLegend();
          		}          		
          		chart.setDataSource(animationData);          		
          		postInvalidate();            		
          	}      
          	
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }            
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub		
		super.onTouchEvent(event);		
		if(event.getAction() == MotionEvent.ACTION_UP) 
		{			
			triggerClick(event.getX(),event.getY());			
		}
		return true;
	}
	
	//触发监听
	private void triggerClick(float x,float y)
	{
		BarPosition record = chart.getPositionRecord(x,y);
		if( null == record) return;
		
		BarData bData = chartData.get(record.getDataID());
		Double bValue = bData.getDataSet().get(record.getDataChildID());			

		Toast.makeText(this.getContext(),				
				" Key:" + bData.getKey() + 							
				" Current Value:" + Double.toString(bValue) +
				" info:" + record.getRectInfo() , 
				Toast.LENGTH_SHORT).show();		
	}
	

	
}
