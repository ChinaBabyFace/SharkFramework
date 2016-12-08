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
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.soaring.widget.chart.xclchart.chart.BubbleChart;
import com.soaring.widget.chart.xclchart.chart.BubbleData;
import com.soaring.widget.chart.xclchart.common.IFormatterTextCallBack;
import com.soaring.widget.chart.xclchart.event.click.PointPosition;
import com.soaring.widget.chart.xclchart.renderer.XChart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;


/**
 * @ClassName BubbleChart01View
 * @Description  气泡图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class BubbleChart01View extends TouchView {

	private String TAG = "ScatterChart01View";
	private BubbleChart chart = new BubbleChart();
	//分类轴标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private List<BubbleData> chartData = new LinkedList<BubbleData>();
	
	public BubbleChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public BubbleChart01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public BubbleChart01View(Context context, AttributeSet attrs, int defStyle) {
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
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			
			//显示边框
			chart.showRoundBorder();
			
			//数据源	
			chart.setCategories(labels);
			chart.setDataSource(chartData);
						
			//坐标系
			//数据轴最大值
			chart.getDataAxis().setAxisMax(100);
			//chart.getDataAxis().setAxisMin(0);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(10);
			
			//标签轴最大值
			chart.setCategoryAxisMax(100);	
			//标签轴最小值
			chart.setCategoryAxisMin(0);	
			
			//指定气泡半径的最大大小
			chart.setBubbleMaxSize(200);
			//指定气泡半径的最小大小
			chart.setBubbleMinSize(10);
			
			//指定最大气泡大小的实际数据值
			chart.setBubbleScaleMax(100);
			//指定最小气泡大小的实际数据值
			chart.setBubbleScaleMin(0);
			
			//设置图的背景色
			//chart.setBackgroupColor(true,Color.BLACK);
			//设置绘图区的背景色
			//chart.getPlotArea().setBackgroupColor(true, Color.WHITE);
		
		
			chart.getDataAxis().getAxisPaint().setStrokeWidth(3);
			chart.getCategoryAxis().getAxisPaint().setStrokeWidth(3);
			
			chart.getDataAxis().getAxisPaint().setColor(Color.rgb(127, 204, 204));
			chart.getCategoryAxis().getAxisPaint().setColor(Color.rgb(127, 204, 204));
			
			chart.getDataAxis().getTickMarksPaint().setColor(Color.rgb(127, 204, 204));
			chart.getCategoryAxis().getTickMarksPaint().setColor(Color.rgb(127, 204, 204));
			
		
			
			
			//定义交叉点标签显示格式,特别备注,因曲线图的特殊性，所以返回格式为:  x值,y值
			//请自行分析定制
			chart.setDotLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub						
					String label = "("+value+")";				
					return (label);
				}
				
			});
			//标题
			chart.setTitle("气泡图");
			chart.addSubtitle("(XCL-Charts Demo)");
			
			//激活点击监听
			chart.ActiveListenItemClick();
			//为了让触发更灵敏，可以扩大5px的点击监听范围
			chart.extPointClickRange(5);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
		//线1的数据集
		LinkedHashMap<Double,Double> linePoint1 = new LinkedHashMap<Double,Double>();
		linePoint1.put(5d, 8d);
		
		linePoint1.put(12d, 12d);
		linePoint1.put(25d, 15d);
		linePoint1.put(30d, 30d);
		linePoint1.put(45d, 25d);
		
		linePoint1.put(55d, 33d);
		linePoint1.put(62d, 45d);
		
		ArrayList<Double> linePoint1_bubble = new ArrayList<Double>();		
		linePoint1_bubble.add(55d);
		linePoint1_bubble.add(60d);
		linePoint1_bubble.add(65d);
		linePoint1_bubble.add(95d);				
		linePoint1_bubble.add(75d);	
		linePoint1_bubble.add(78d);	
		
		
		BubbleData dataSeries1 = new BubbleData("青菜萝卜够吃",linePoint1,linePoint1_bubble,
				Color.rgb(54, 141, 238));
		dataSeries1.setLabelVisible(true);	
		dataSeries1.getDotLabelPaint().setTextAlign(Align.CENTER);
		
		
		//线2的数据集
		LinkedHashMap<Double,Double> linePoint2 = new LinkedHashMap<Double,Double>();
		linePoint2.put(40d, 50d);
		linePoint2.put(55d, 55d);
		linePoint2.put(60d, 65d);
		linePoint2.put(65d, 85d);				
		linePoint2.put(72d, 70d);	
		linePoint2.put(85d, 68d);	
		
		ArrayList<Double> linePoint2_bubble = new ArrayList<Double>();		
		linePoint2_bubble.add(55d);
		linePoint2_bubble.add(60d);
		linePoint2_bubble.add(65d);
		linePoint2_bubble.add(95d);				
		linePoint2_bubble.add(75d);	
		linePoint2_bubble.add(78d);	
				
		BubbleData dataSeries2 = new BubbleData("饭管够",
				linePoint2,linePoint2_bubble, Color.rgb(255, 165, 132));
		
		
		
		dataSeries2.setLabelVisible(true);		
		//dataSeries2.setDotStyle(XEnum.DotStyle.RECT);				
		//dataSeries2.getDotLabelPaint().setColor(Color.RED);
			
			
		//设定数据源		
		chartData.add(dataSeries1);				
		chartData.add(dataSeries2);	
	}
	
	private void chartLabels()
	{
		labels.add("b1");
		labels.add("b2");
		labels.add("b3");
		labels.add("b4");
		labels.add("b5");
		labels.add("b6");
		labels.add("b7");
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

		BubbleData lData = chartData.get(record.getDataID());
		LinkedHashMap<Double,Double> mapPoint =  lData.getDataSet();	
		int pos = record.getDataChildID();
		int i = 0;
		Iterator it = mapPoint.entrySet().iterator();
		while(it.hasNext())
		{
			Entry  entry=(Entry)it.next();	
			
			if(pos == i)
			{							 						
			     Double xValue =(Double) entry.getKey();
			     Double yValue =(Double) entry.getValue();	
			     
			     Toast.makeText(this.getContext(), 
							record.getPointInfo() +
							" Key:"+lData.getKey() +								
							" Current Value(key,value):"+
							Double.toString(xValue)+","+Double.toString(yValue), 
							Toast.LENGTH_SHORT).show();
			     break;
			}
	        i++;
		}//end while
				
	}
	

}
