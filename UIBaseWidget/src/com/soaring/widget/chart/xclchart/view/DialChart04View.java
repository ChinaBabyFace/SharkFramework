package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.DialChart;
import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.XEnum;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotAttrInfo;
import com.soaring.widget.chart.xclchart.renderer.plot.Pointer;

import java.util.ArrayList;
import java.util.List;

public class DialChart04View extends GraphicalView {
	
	private String TAG = "DialChart04View";	
	private DialChart chart = new DialChart();
	private float mPercentage = 0.1f;
	
	float mP1 = 0.0f;
	float mP2 =  0.0f;
	
	public DialChart04View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public DialChart04View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public DialChart04View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		chartRender();
	 }
	 
	 @Override  
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	        chart.setChartRange(w ,h ); 
	    }  		
			
	 public void chartRender()
		{
			try {								
							
				//设置标题背景			
				chart.setApplyBackgroundColor(true);
				chart.setBackgroundColor(Color.rgb(28, 129, 243));
				//绘制边框
				chart.showRoundBorder();
						
				//设置当前百分比
				chart.getPointer().setPercentage(mPercentage);
				
				//设置指针长度
				chart.getPointer().setLength(0.5f,0.3f);
				
				//增加轴承
				addAxis();						
				/////////////////////////////////////////////////////////////
				//增加指针
				addPointer();
				//设置附加信息
				addAttrInfo();
				/////////////////////////////////////////////////////////////								
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.toString());
			}
			
		}
		
		public void addAxis()
		{
		
			List<String> rlabels  = new ArrayList<String>();
			int j=0;
			for(int i=0;i<=150;)
			{
				if(0 == i || j == 4)
				{
					rlabels.add(Integer.toString(i));
					j = 0;
				}else{
					rlabels.add("");
					j++;
				}
										
				i+=5;
			}
			chart.addInnerTicksAxis(0.9f, rlabels);
			
			chart.getPlotAxis().get(0).setDetailModeSteps(4);
			chart.getPlotAxis().get(0).getTickLabelPaint().setColor(Color.WHITE);
			chart.getPlotAxis().get(0).getTickMarksPaint().setColor(Color.WHITE);
			chart.getPlotAxis().get(0).setAxisLineVisible(false);			
			chart.getPlotAxis().get(0).setTickLabelVisible(true);			
			
			chart.getPointer().setPointerStyle(XEnum.PointerStyle.TRIANGLE);
			chart.getPointer().setBaseRadius(15);
			chart.getPointer().getPointerPaint().setStrokeWidth(7);			
			chart.getPointer().getBaseCirclePaint().setColor(Color.WHITE );
			
		}
		
		
		private void addAttrInfo()
		{
			/////////////////////////////////////////////////////////////
			PlotAttrInfo plotAttrInfo = chart.getPlotAttrInfo();
			
			//设置附加信息
			Paint paintTB = new Paint();
			paintTB.setColor(Color.WHITE);
			paintTB.setTextAlign(Align.CENTER);
			paintTB.setTextSize(30);	
			paintTB.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.TOP, Float.toString(
									MathHelper.getInstance().round(mPercentage * 100,2))+"%", 0.3f, paintTB);
			
			Paint paintBT = new Paint();
			paintBT.setColor(Color.BLUE);
			paintBT.setTextAlign(Align.CENTER);
			paintBT.setTextSize(22);

			paintBT.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, "蓝:"+Float.toString(
							MathHelper.getInstance().round(mP1 * 100,2))+"%", 0.4f, paintBT);
			
			Paint paintBT2 = new Paint();
			paintBT2.setColor(Color.RED);
			paintBT2.setTextAlign(Align.CENTER);
			paintBT2.setTextSize(22);
		
			paintBT2.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, "红:"+Float.toString(
					MathHelper.getInstance().round(mP2 * 100,2))+"%", 0.5f, paintBT2);
		}
		
		public void addPointer()
		{				
		
			
			chart.addPointer();
			chart.addPointer();
			
			List<Pointer> mp = chart.getPlotPointer();
									
			mp.get(0).setLength(0.85f,0.2f);	
			mp.get(0).getPointerPaint().setColor(Color.BLUE);
			mP1 = MathHelper.getInstance().round(mPercentage * 0.3f ,2);
			if(mP1 < 0 || mP1 > 1) mP1 = 0.0f;
			mp.get(0).setPercentage(mP1 );
			
			
			mp.get(1).setLength(0.8f,0.15f);
			//mp.get(1).setPointerStyle(XEnum.PointerStyle.TRIANGLE);		
			mp.get(1).getPointerPaint().setColor(Color.RED);
			mP2 = MathHelper.getInstance().round(mPercentage + 0.1f ,2);
			if(mP2 < 0 || mP2 > 1) mP2 = 1f;			
			mp.get(1).setPercentage( mP2);		
			
			
			mp.get(0).setPointerStyle(XEnum.PointerStyle.TRIANGLE);
			mp.get(0).setBaseRadius(15);
			mp.get(1).setPointerStyle(XEnum.PointerStyle.TRIANGLE);
			mp.get(1).setBaseRadius(15);
			
			
		}
		public void setCurrentStatus(float percentage)
		{
			//清理
			chart.clearAll();
					
			mPercentage =  percentage;
			//设置当前百分比
			chart.getPointer().setPercentage(mPercentage);
			addAxis();						
			addPointer();
			addAttrInfo();
		}


		@Override
		public void render(Canvas canvas) {
			// TODO Auto-generated method stub
			 try{
		            chart.render(canvas);
		        } catch (Exception e){
		        	Log.e(TAG, e.toString());
		        }
		}

}
