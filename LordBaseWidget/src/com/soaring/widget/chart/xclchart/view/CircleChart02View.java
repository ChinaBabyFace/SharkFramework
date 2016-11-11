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

import com.soaring.widget.chart.xclchart.chart.CircleChart;
import com.soaring.widget.chart.xclchart.chart.PieData;

import java.util.LinkedList;

/**
 * @ClassName CircleChart02View
 * @Description  图形图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class CircleChart02View extends GraphicalView {
	
	private String TAG = "CircleChart02View";
	private CircleChart chart = new CircleChart();
	
	//设置图表数据源
	private LinkedList<PieData> mlPieData = new LinkedList<PieData>();
	private String mDataInfo = "";

	public CircleChart02View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setPercentage(0);
		chartRender();
	}
	
	public CircleChart02View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        setPercentage(0);
		chartRender();
	 }
	 
	 public CircleChart02View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			setPercentage(0);
			chartRender();
	 }
	
	 
	 @Override  
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	       //图所占范围大小
	        chart.setChartRange(w,h);
	    }  
	 
	 
	public void chartRender()
	{
		try {							
			//设置信息			
			chart.setAttributeInfo(mDataInfo); 	
			//数据源
			chart.setDataSource(mlPieData);
			
			//背景色
			chart.getPaintBgCircle().setColor(Color.rgb(117, 197, 141));
			//深色
			chart.getPaintFillCircle().setColor(Color.rgb(77, 180, 123));
			//信息颜色
			chart.getPaintDataInfo().setColor(Color.rgb(243, 75, 125));
			//显示边框
			chart.showRoundBorder();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	//百分比
	public void setPercentage(int per)
	{					
		//PieData(标签，百分比，在饼图中对应的颜色)
		mlPieData.clear();	
		int color = Color.rgb(72, 201, 176);
		if(per < 40)
		{
			mDataInfo = "容易容易";
		}else if(per < 60){
			mDataInfo = "严肃认真";
			color = Color.rgb(246, 202, 13);
		}else{
			mDataInfo = "压力山大";
			color = Color.rgb(243, 75, 125);
		}
		mlPieData.add(new PieData(Integer.toString(per)+"%",per,color));
			
	}

	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

	/*
	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub		
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);		
		return lst;
	}
	*/
}
