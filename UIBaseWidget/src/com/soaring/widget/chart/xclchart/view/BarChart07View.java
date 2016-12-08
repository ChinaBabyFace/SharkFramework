package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.BarChart;
import com.soaring.widget.chart.xclchart.chart.BarData;
import com.soaring.widget.chart.xclchart.chart.CustomLineData;
import com.soaring.widget.chart.xclchart.common.IFormatterDoubleCallBack;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BarChart07View  extends GraphicalView {
	
	private String TAG = "BarChart07View";	
	private BarChart chart = new BarChart();
	
	//轴数据源
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();
	
	//private float mChartX = 0.0f;
	//private float mChartY = 0.0f;
	
	public BarChart07View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		 initView();
	}
	
	 public BarChart07View(Context context, AttributeSet attrs){   
	        super(context, attrs);   
	        initView();
	 }
	 			 
		 public BarChart07View(Context context, AttributeSet attrs, int defStyle) {
				super(context, attrs, defStyle);
				initView();
		 }
		 
		 private void initView()
		 {
				chartLabels();
				chartDataSet();
				chartDesireLines();
				chartRender();
		 }
		 
	
		 
		private void chartRender()
		{
			try {
			
				//数据源
				chart.setDataSource(chartData);
				chart.setCategories(chartLabels);	
				chart.setCustomLines(mCustomLineDataset);
				
							
				//数据轴
				chart.getDataAxis().setAxisMax(40);
				chart.getDataAxis().setAxisMin(0);
				chart.getDataAxis().setAxisSteps(5);		
				//指隔多少个轴刻度(即细刻度)后为主刻度
				chart.getDataAxis().setDetailModeSteps(2);
				
				//背景网格
				chart.getPlotGrid().showHorizontalLines();
				
				
				//标签
				chart.getCategoryAxis().getTickLabelPaint().setTextSize(15);
		
				
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
				
				//隐藏图例
				chart.getPlotLegend().hideLegend();
				
				 //让柱子间近似没空白
				 chart.getBar().setBarInnerMargin(0.1d); //可尝试0.1或0.5各有啥效果噢
		
				 
				 //背景网格颜色
				chart.getPlotGrid().showEvenRowBgColor();
				chart.getPlotGrid().getEvenRowsBgColorPaint().setColor(Color.rgb(225, 230, 246));
				 
				
				chart.getDataAxis().setVisible(false);
				chart.getCategoryAxis().setTickMarksVisible(false);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private void chartDataSet()
		{
			//标签对应的柱形数据集
			List<Double> dataSeriesA= new LinkedList<Double>();	
			//依数据值确定对应的柱形颜色.
			List<Integer> dataColorA= new LinkedList<Integer>();	
			
			int max = 35;
		    int min = 15;
		        
			for(int i=1;i<35;i++)
			{
				Random random = new Random();
				int v = random.nextInt(max)%(max-min+1) + min;			 
				dataSeriesA.add((double) v);
				
				if(v <= 18.5d ) //适中
				{
					dataColorA.add(Color.rgb(77, 184, 73));
				}else if(v <= 24d){ //超重
					dataColorA.add(Color.rgb(252, 210, 9));
				}else if(v <= 27.9d){ //偏胖
					dataColorA.add(Color.rgb(171, 42, 96));
				}else{  //肥胖
					dataColorA.add(Color.RED);
				}
			}  
			//此地的颜色为Key值颜色及柱形的默认颜色
			BarData BarDataA = new BarData("",dataSeriesA,dataColorA,
					Color.rgb(53, 169, 239));
			
			chartData.add(BarDataA);
		}
		
		private void chartLabels()
		{		
			for(Integer i=1;i<35;i++)
			{
				if(i%5 == 0)
				{
					chartLabels.add(Integer.toString(i));
				}else
					chartLabels.add("");
			}
		}	
		
		/**
		 * 期望线/分界线
		 */
		private void chartDesireLines()
		{							
			mCustomLineDataset.add(new CustomLineData("适中",18.5d, Color.rgb(77, 184, 73),3));
			mCustomLineDataset.add(new CustomLineData("超重",24d, Color.rgb(252, 210, 9),4));
			mCustomLineDataset.add(new CustomLineData("偏胖",27.9d, Color.rgb(171, 42, 96),5));
			mCustomLineDataset.add(new CustomLineData("肥胖",30d, Color.RED,6));
									
		}
		
		@Override  
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	       //图所占范围大小
	       // chart.setChartRange(w,h);
	    }  
		
	
	@Override
    public void render(Canvas canvas) {
        try{        	
        	//设置图表大小
	        chart.setChartRange(0,0,
	        		this.getLayoutParams().width - 10,
	        		 this.getLayoutParams().height - 10);
	        //设置绘图区内边距	(px),left,top,bottom保持与左边图要一致 
	        chart.setPadding( 0,120, 100,180 );	
	        
            chart.render(canvas);
            
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

	
	
	@Override
	 public void onDraw(Canvas canvas){  
	        //绘制
	        super.onDraw(canvas); 
	 }
	

		
}
