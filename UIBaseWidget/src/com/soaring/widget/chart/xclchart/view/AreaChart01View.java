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

import com.soaring.widget.chart.xclchart.chart.AreaChart;
import com.soaring.widget.chart.xclchart.chart.AreaData;
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
 * @ClassName AreaChart01View
 * @Description  面积图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class AreaChart01View extends TouchView {
	
	private String TAG = "AreaChart01View";
	
	private AreaChart chart = new AreaChart();
	//标签集合
	private LinkedList<String> mLabels = new LinkedList<String>();
	//数据集合
	private LinkedList<AreaData> mDataset = new LinkedList<AreaData>();
	

	public AreaChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}	 
	
	public AreaChart01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public AreaChart01View(Context context, AttributeSet attrs, int defStyle) {
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
				//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
				int [] ltrb = getBarLnDefaultSpadding();
				chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
											
				//轴数据源						
				//标签轴
				chart.setCategories(mLabels);
				//数据轴
				chart.setDataSource(mDataset);
				
				chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEELINE);
							
				//数据轴最大值
				chart.getDataAxis().setAxisMax(100);
				//数据轴刻度间隔
				chart.getDataAxis().setAxisSteps(10);
				
				//网格
				chart.getPlotGrid().showHorizontalLines();
				chart.getPlotGrid().showVerticalLines();	
				//把顶轴和右轴隐藏
				chart.setTopAxisVisible(false);
				chart.setRightAxisVisible(false);
				//把轴线和刻度线给隐藏起来
				chart.getDataAxis().setAxisLineVisible(false);
				chart.getDataAxis().setTickMarksVisible(false);			
				chart.getCategoryAxis().setAxisLineVisible(false);
				chart.getCategoryAxis().setTickMarksVisible(false);				
				
				//标题
				chart.setTitle("区域图(Area Chart)");
				chart.addSubtitle("(XCL-Charts Demo)");	
				//轴标题
				chart.getAxisTitle().setLowerAxisTitle("(年份)");
				
				//透明度
				chart.setAreaAlpha(100);
				//显示图例
				chart.getPlotLegend().showLegend();
				
				//激活点击监听
				chart.ActiveListenItemClick();
				//为了让触发更灵敏，可以扩大5px的点击监听范围
				chart.extPointClickRange(5);
				
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
				
				//设定交叉点标签显示格式
				chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
					@Override
					public String doubleFormatter(Double value) {
						// TODO Auto-generated method stub
						DecimalFormat df=new DecimalFormat("#0");					 
						String label = df.format(value).toString();
						return label;
					}});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}
	
	private void chartDataSet()
	{
		//将标签与对应的数据集分别绑定
		//标签对应的数据集
		List<Double> dataSeries1= new LinkedList<Double>();	
		dataSeries1.add((double)55); 
		dataSeries1.add((double)60); 
		dataSeries1.add((double)71); 
		dataSeries1.add((double)40);
		dataSeries1.add((double)35);
		
		List<Double> dataSeries2 = new LinkedList<Double>();			
		dataSeries2.add((double)10); 
		dataSeries2.add((double)22); 
		dataSeries2.add((double)30); 	
		dataSeries2.add((double)30); 
		dataSeries2.add((double)15); 
		
		//设置每条线各自的显示属性
		//key,数据集,线颜色,区域颜色
		AreaData line1 = new AreaData("小熊",dataSeries1,Color.BLUE,Color.YELLOW);
		//不显示点
		line1.setDotStyle(XEnum.DotStyle.HIDE);
		
		AreaData line2 = new AreaData("小小熊",dataSeries2,
				Color.rgb(79, 200, 100),Color.GREEN);
		//设置线上每点对应标签的颜色
		line2.getDotLabelPaint().setColor(Color.RED);
		//设置点标签
		line2.setLabelVisible(true);
		line2.getDotLabelPaint().setTextAlign(Align.LEFT);	
		
		mDataset.add(line1);
		mDataset.add(line2);	
	}
	
	private void chartLabels()
	{		
		mLabels.add("2010");
		mLabels.add("2011");
		mLabels.add("2012");
		mLabels.add("2013");
		mLabels.add("2014");
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
		PointPosition record = chart.getPositionRecord(x,y);
		if( null == record) return;

		AreaData lData = mDataset.get(record.getDataID());
		Double lValue = lData.getLinePoint().get(record.getDataChildID());	
		
		Toast.makeText(this.getContext(), 
				record.getPointInfo() +
				" Key:"+lData.getLineKey() +
				" Label:"+lData.getLabel() +								
				" Current Value:"+Double.toString(lValue), 
				Toast.LENGTH_SHORT).show();			
	}
	

	
}
