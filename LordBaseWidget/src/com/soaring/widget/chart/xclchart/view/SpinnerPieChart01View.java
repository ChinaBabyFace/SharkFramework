package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.DountChart;
import com.soaring.widget.chart.xclchart.chart.PieChart;
import com.soaring.widget.chart.xclchart.chart.PieChart3D;
import com.soaring.widget.chart.xclchart.chart.PieData;
import com.soaring.widget.chart.xclchart.chart.RoseChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

import java.util.LinkedList;

public class SpinnerPieChart01View extends GraphicalView {
	
	private String TAG = "SpinnerPieChart01View";

	private PieChart mChart = null;
	private int mChartStyle = 0;
	private int mOffsetHeight = 0;
	
	private LinkedList<PieData> chartData = new LinkedList<PieData>();

	public SpinnerPieChart01View(Context context,int chartStyle,int moveHeight) {
		super(context);
		// TODO Auto-generated constructor stub
		mChartStyle = chartStyle;
		mOffsetHeight = moveHeight;
		chartDataSet();	
		chartRender();
	}
	
     private void initChart(int chartStyle)
 	{
 		switch(chartStyle)
 		{
 		case 0: //饼图
 			mChart = new PieChart();
 			mChart.setLabelPosition(XEnum.SliceLabelPosition.OUTSIDE);
 			
 			break;
 		case 1:	//3D饼图
 			mChart = new PieChart3D();
 			mChart.setLabelPosition(XEnum.SliceLabelPosition.INSIDE);
 			mChart.getLabelPaint().setColor(Color.WHITE); 
 			
 			break;
 		case 2:	//环形图
 			mChart = new DountChart();
 			break;
 		case 3:	//南丁格尔玫瑰图
 			mChart = new RoseChart();
 			//mChart.setBackgroundColor(true, (int)Color.rgb(115, 153, 0)); 			
 			mChart.setApplyBackgroundColor(true);
 			mChart.setBackgroundColor(Color.rgb(115, 153, 0));
			
 			((RoseChart) mChart).getInnerPaint().setColor(Color.rgb(153, 204, 0));
 			mChart.getLabelPaint().setColor(Color.WHITE);
 			mChart.setLabelPosition(XEnum.SliceLabelPosition.INSIDE);
 			break;		
 		}
 		
 	}
     
     @Override  
     protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
         super.onSizeChanged(w, h, oldw, oldh);  
        //图所占范围大小
         mChart.setChartRange(w,h);
     }  		
     
     private void chartRender()
 	{
 		try {					
 			initChart(mChartStyle);
 		
 			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			mChart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
 			
 			//设定数据源
 			mChart.setDataSource(chartData);			
 			
 			//设置起始偏移角度(即第一个扇区从哪个角度开始绘制)
 			mChart.setInitialAngle(90);	
 			//显示Key值
 			mChart.getPlotLegend().hideLegend();
 			
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			Log.e(TAG, e.toString());
 		}
	}
	private void chartDataSet()
	{
		//设置图表数据源		
		chartData.add(new PieData("User1","15%",15, Color.rgb(203, 183, 60)));
		chartData.add(new PieData("User2","25%",25, Color.rgb(214, 222, 207),false));
		chartData.add(new PieData("User3","10%",10, Color.rgb(164, 202, 81)));
		//将此比例块突出显示
		chartData.add(new PieData("User4","18%",18, Color.rgb(1, 172, 241),true));
		chartData.add(new PieData("User5","22%",22, Color.rgb(99, 179, 150),true));
		chartData.add(new PieData("User6","10%",10, Color.rgb(52, 97, 138)));
	}
	
	@Override
    public void render(Canvas canvas) {
        try{
        	
        	//mChart.setChartRange(this.getMeasuredWidth(), this.getMeasuredHeight());
        	
        	//mChart.setChartRange(0.0f, mOffsetHeight, this.getWidth(),this.getHeight() - mOffsetHeight);
    		
        	
        	mChart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }
}
