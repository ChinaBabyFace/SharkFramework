package com.soaring.widget.chart.xclchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.DialChart;
import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.XEnum;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotAttrInfo;

import java.util.ArrayList;
import java.util.List;

public class DialChart06View extends GraphicalView {

	private String TAG = "DialChart06View";	
	
	private DialChart chart = new DialChart();
	private float mPercentage = 0.9f;
	
	public DialChart06View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public DialChart06View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public DialChart06View(Context context, AttributeSet attrs, int defStyle) {
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
				
				chart.setTotalAngle(360f);
						
				//设置当前百分比
				chart.getPointer().setPercentage(mPercentage);
				
				//设置指针长度
				chart.getPointer().setLength(0.65f,0.2f);	
				
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
			List<Float> ringPercentage = new ArrayList<Float>();			
			float rper = MathHelper.getInstance().div(1, 4); //相当于40%	//270, 4
			ringPercentage.add(rper);
			ringPercentage.add(rper);
			ringPercentage.add(rper);
			ringPercentage.add(rper);
			
			List<Integer> rcolor  = new ArrayList<Integer>();			
			rcolor.add(Color.rgb(242, 110, 131));
			rcolor.add(Color.rgb(238, 204, 71));
			rcolor.add(Color.rgb(42, 231, 250));
			rcolor.add(Color.rgb(140, 196, 27));
			chart.addStrokeRingAxis(0.85f,0.7f, ringPercentage, rcolor);
					
			chart.addCircleAxis(0.6f, Color.WHITE);
			chart.getPlotAxis().get(1).getAxisPaint().setStyle(Style.STROKE);						
			chart.getPlotAxis().get(0).getFillAxisPaint().setColor(Color.rgb(28, 129, 243));
	
	
			chart.getPointer().setPointerStyle(XEnum.PointerStyle.TRIANGLE);
			chart.getPointer().getPointerPaint().setStrokeWidth(3);			
			chart.getPointer().getPointerPaint().setStyle(Style.FILL);		
					
			if( Float.compare(mPercentage, 0.25f) == -1 )
			{
				chart.getPointer().getPointerPaint().setColor(Color.rgb(242, 110, 131));
			}else if(Float.compare(mPercentage,0.5f) == -1 
					|| Float.compare(mPercentage, 0.5f) == 0  ) {
				chart.getPointer().getPointerPaint().setColor(Color.rgb(238, 204, 71));
			}else if(Float.compare(mPercentage, 0.75f) == -1 
					|| Float.compare(mPercentage, 0.75f) == 0 ) {
				chart.getPointer().getPointerPaint().setColor(Color.rgb(42, 231, 250));
			}else if(Float.compare(mPercentage, 1f) == -1 
					|| Float.compare(mPercentage, 1f) == 0    ) {
				chart.getPointer().getPointerPaint().setColor(Color.rgb(140, 196, 27));
			}else{
				chart.getPointer().getPointerPaint().setColor(Color.YELLOW);
			}
			
			chart.getPointer().getBaseCirclePaint().setColor(Color.GREEN);
			chart.getPointer().setBaseRadius(10f);
			
		}
		
		//增加指针
		public void addPointer()
		{					
		
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
			plotAttrInfo.addAttributeInfo(XEnum.Location.TOP, "当前网速", 0.3f, paintTB);
			
			Paint paintBT = new Paint();
			paintBT.setColor(Color.WHITE);
			paintBT.setTextAlign(Align.CENTER);
			paintBT.setTextSize(38);
			paintBT.setFakeBoldText(true);
			paintBT.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM,
					Float.toString(MathHelper.getInstance().round(mPercentage * 100,2)), 0.3f, paintBT);
			
			Paint paintBT2 = new Paint();
			paintBT2.setColor(Color.WHITE);
			paintBT2.setTextAlign(Align.CENTER);
			paintBT2.setTextSize(30);
			paintBT2.setFakeBoldText(true);
			paintBT2.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, "MB/S", 0.4f, paintBT2);
			
			Paint paintDesc = new Paint();
			paintDesc.setColor(Color.WHITE);
			paintDesc.setTextAlign(Align.CENTER);
			paintDesc.setTextSize(22);
			paintDesc.setFakeBoldText(true);
			paintDesc.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.LEFT, "普通", 0.9f, paintDesc);
			plotAttrInfo.addAttributeInfo(XEnum.Location.TOP, "快速", 0.9f, paintDesc);
			plotAttrInfo.addAttributeInfo(XEnum.Location.RIGHT, "高速", 0.9f, paintDesc);
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, "超速", 0.9f, paintDesc);
			
		}
		
		public void setCurrentStatus(float percentage)
		{								
			mPercentage =  percentage;
			//清理
			chart.clearAll();
			
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
