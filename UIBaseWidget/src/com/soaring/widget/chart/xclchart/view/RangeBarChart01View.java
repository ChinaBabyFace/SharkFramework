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
 * @version 1.5
 */
package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.soaring.widget.chart.xclchart.chart.RangeBarChart;
import com.soaring.widget.chart.xclchart.chart.RangeBarData;
import com.soaring.widget.chart.xclchart.common.DensityUtil;
import com.soaring.widget.chart.xclchart.common.IFormatterDoubleCallBack;
import com.soaring.widget.chart.xclchart.common.IFormatterTextCallBack;
import com.soaring.widget.chart.xclchart.event.click.BarPosition;
import com.soaring.widget.chart.xclchart.renderer.XChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName RangeBarChart01View
 * @Description  范围条形图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class RangeBarChart01View extends TouchView {
	
	private String TAG = "RangeBarChart01View";
	private RangeBarChart chart = new RangeBarChart();
	//标签轴
	List<String> chartLabels = new LinkedList<String>();
	List<RangeBarData> BarDataSet = new LinkedList<RangeBarData>();

	public RangeBarChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public RangeBarChart01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public RangeBarChart01View(Context context, AttributeSet attrs, int defStyle) {
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
		try {
			
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], DensityUtil.dip2px(getContext(), 55));
			
			//显示边框
			//chart.showRoundBorder();			
			
		
			//数据源		
			chart.setCategories(chartLabels);	
			chart.setDataSource(BarDataSet);
			
			//坐标系
			chart.getDataAxis().setAxisMax(1024);
			chart.getDataAxis().setAxisMin(0);
			chart.getDataAxis().setAxisSteps(64);
			
			chart.getDataAxis().setDetailModeSteps(4);
			
			//指定数据轴标签旋转-45度显示
			chart.getCategoryAxis().setTickLabelRotateAngle(-45f);
			
			chart.getDataAxis().getAxisPaint().setColor(Color.rgb(79, 200, 100));
			chart.getCategoryAxis().getAxisPaint().setColor(Color.rgb(79, 200, 100));
			
			chart.getDataAxis().getTickMarksPaint().setColor(Color.rgb(79, 200, 100));
			chart.getCategoryAxis().getTickMarksPaint().setColor(Color.rgb(79, 200, 100));
			
			chart.getPlotTitle().getTitlePaint().setColor(Color.rgb(79, 200, 100));
			chart.getPlotTitle().getSubtitlePaint().setColor(Color.rgb(79, 200, 100));
			
			chart.getDataAxis().getTickLabelPaint().setColor(Color.rgb(79, 200, 100));
			chart.getCategoryAxis().getTickLabelPaint().setColor(Color.rgb(79, 200, 100));
			
			
			//标题
			chart.setTitle("范围条形图");
			chart.addSubtitle("(XCL-Charts Demo)");
			chart.setTitleAlign(XEnum.ChartTitleAlign.MIDDLE);
			chart.setTitleVerticalAlign(XEnum.VerticalAlign.MIDDLE);
		
			
			//背景网格
			chart.getPlotGrid().showEvenRowBgColor();
			chart.getPlotGrid().showOddRowBgColor();
			
			//定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub				
				
					DecimalFormat df=new DecimalFormat("#0");
					Double tmp = Double.parseDouble(value);
					String label = df.format(tmp).toString();	
					return label;
				}
				
			});
			
			//定义标签轴标签显示格式
			chart.getCategoryAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub				
					String label = "***"+value+"***";
					return label;
				}
				
			});
						
					
			//定义柱形上标签显示格式
			chart.getBar().setItemLabelVisible(true);
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("#0");					 
					String label = df.format(value).toString();
					return label;
				}});	     
			
			//定义柱形上标签显示颜色
			chart.getBar().getItemLabelPaint().setColor(Color.rgb(77, 184, 73));
			chart.getBar().getItemLabelPaint().setTypeface(Typeface.DEFAULT_BOLD);
			
			//设置柱形颜色
			chart.getBar().getBarPaint().setColor(Color.rgb(79, 200, 100));
			//设置图例
			chart.setKey("图例");
			
			
			//激活点击监听
			chart.ActiveListenItemClick();
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	
	private void chartDataSet()
	{	
		RangeBarData data1 = new RangeBarData();
		data1.setMin(200d);
		data1.setMax(270d);
				
		RangeBarData data2 = new RangeBarData();
		data2.setMin(300d);
		data2.setMax(570d);		
		
		BarDataSet.add(data1);
		BarDataSet.add(data2);
		BarDataSet.add(new RangeBarData(400d,700d));
		
		BarDataSet.add(new RangeBarData(350d,650d));
		BarDataSet.add(new RangeBarData(500d,780d));
		BarDataSet.add(new RangeBarData(720d,980d));
		
	}
	private void chartLabels()
	{
		chartLabels.add("a"); 
		chartLabels.add("b"); 
		chartLabels.add("cc"); 
		chartLabels.add("e"); 
		chartLabels.add("f"); 
		chartLabels.add("g"); 
	}

	@Override
    public void render(Canvas canvas) {
        try{
        	
        	//chart.setChartRange(this.getMeasuredWidth(), this.getMeasuredHeight());
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
		
		BarPosition record = chart.getPositionRecord(x,y);
		if( null == record) return;
		
		RangeBarData bData = BarDataSet.get(record.getDataID());
		Toast.makeText(this.getContext(),
				"info:" + record.getRectInfo() +
				" Min:" + Double.toString( bData.getMin()) + 		
				" Max:" + Double.toString( bData.getMax()) , 
				Toast.LENGTH_SHORT).show();				
	}
}