package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.LineChart;
import com.soaring.widget.chart.xclchart.chart.LineData;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

import java.util.LinkedList;

public class LineChart03View_left extends GraphicalView {
	
	private String TAG = "LineChart03View_left";
	private LineChart chart = new LineChart();
	
	//标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private LinkedList<LineData> chartData = new LinkedList<LineData>();

	public LineChart03View_left(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			chartLabels();
			chartDataSet();	
			chartRender();
	}
	
	 public LineChart03View_left(Context context, AttributeSet attrs){   
	        super(context, attrs);   
	        chartLabels();
			chartDataSet();	
			chartRender();
	 }
	 
	 
	 public LineChart03View_left(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		 	chartLabels();
			chartDataSet();	
			chartRender();
	 }		 
	 

	private void chartRender()
	{
		try {				
			
			chart.setTitle("折线图 (scroll view)");
			chart.addSubtitle("(XCL-Charts Demo)");
			
			
			//设定数据源
			chart.setCategories(labels);								
			chart.setDataSource(chartData);
			
			//数据轴最大值
			chart.getDataAxis().setAxisMax(100);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(10);
			
			
		//chart.getCategoryAxis().setHorizontalTickAlign(align)
			chart.getCategoryAxis().setVisible(false);		
			chart.setTopAxisVisible(false);
			chart.setRightAxisVisible(false);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
	
		LinkedList<Double> dataSeries1= new LinkedList<Double>();	
		dataSeries1.add(0d); 
		LineData lineData1 = new LineData("方块",dataSeries1, Color.rgb(234, 83, 71));
		//chartData.add(lineData1);
		
		
		
		//Line 2
				LinkedList<Double> dataSeries2= new LinkedList<Double>();	
				dataSeries2.add((double)0);
				LineData lineData2 = new LineData("圆环",dataSeries2, Color.rgb(75, 166, 51));
				lineData2.setDotStyle(XEnum.DotStyle.RING);
				lineData2.getPlotLine().getDotPaint().setColor(Color.BLACK);
				lineData2.setLabelVisible(true);		
				//Line 3
				LinkedList<Double> dataSeries3= new LinkedList<Double>();	
				dataSeries3.add(0d);
				LineData lineData3 = new LineData("圆点",dataSeries3, Color.rgb(123, 89, 168));
				lineData3.setDotStyle(XEnum.DotStyle.DOT);
				//Line 4
				LinkedList<Double> dataSeries4= new LinkedList<Double>();	
				dataSeries4.add(0d);
				LineData lineData4 = new LineData("棱形",dataSeries4, Color.rgb(84, 206, 231));
				lineData4.setDotStyle(XEnum.DotStyle.PRISMATIC);
				//Line 5
				LinkedList<Double> valuesE= new LinkedList<Double>();	
				valuesE.add(0d);
				LineData lineData5 = new LineData("定制",valuesE, Color.rgb(234, 142, 43));
				lineData5.setDotRadius(15);
				
				chartData.add(lineData1);
				chartData.add(lineData2);
				chartData.add(lineData3);
				chartData.add(lineData4);
				chartData.add(lineData5);
	}
	
	private void chartLabels()
	{
		labels.add("2010");
		labels.add("2011");
		labels.add("2012");
		labels.add("2013");
		labels.add("2014");
	}
	

	
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  
	
	
	
	@Override
    public void render(Canvas canvas) {
        try{
        	
        	 //设置图表大小
	        chart.setChartRange(0,0, //10f, 10f,  
	        		this.getLayoutParams().width - 10,
	        		 this.getLayoutParams().height - 10);
	        
	        chart.setPadding(70,120,0, 180);	//70是轴所点总宽度，在右边轴绘图时，偏移这个宽度就对好了
        	
        	
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

	@Override
	 public void onDraw(Canvas canvas){   
		
		 //canvas.drawColor(Color.BLUE);
	        super.onDraw(canvas);  
	        
	 }
	
		
}
