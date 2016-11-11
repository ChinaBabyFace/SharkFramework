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
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.ArcLineChart;
import com.soaring.widget.chart.xclchart.chart.ArcLineData;
import com.soaring.widget.chart.xclchart.renderer.XChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @ClassName ArcLineChart01View
 * @Description  弧线比较图的例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class ArcLineChart01View extends TouchView {
	
	private String TAG = "ArcLineChart01View";
	private ArcLineChart chart = new ArcLineChart();
	private LinkedList<ArcLineData> chartData = new LinkedList<ArcLineData>();

	public ArcLineChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public ArcLineChart01View(Context context, AttributeSet attrs){   
        	super(context, attrs);   
        	initView();
	 }
	 
	 public ArcLineChart01View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
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
			
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
			//背景
			chart.setApplyBackgroundColor(true);
			chart.setBackgroundColor(Color.WHITE);
			//边框
			chart.getBorder().getLinePaint().setColor(Color.rgb(83, 178, 50));
			chart.showRoundBorder();	
				
			//标题
			chart.setTitle("弧线比较图");
			chart.addSubtitle("(XCL-Charts Demo)");
			chart.setTitleVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			
			//显示图例
			chart.getPlotLegend().showLegend();
									
			//绑定数据源
			chart.setDataSource(chartData);
			
			//标签偏移
			chart.setLabelOffsetX(30f);
			//内环半径所占比例
			//chart.setInnerRadius(0.6f);		
			
			//设置附加信息
			addAttrInfo();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	private void chartDataSet()
	{
		
		//设置图表数据源			
		chartData.add(new ArcLineData("closed","29% - closed" ,   (0.29*100), Color.rgb(155, 187, 90)));
		chartData.add(new ArcLineData("inspect","53% - inspect" ,   (0.53*100), Color.rgb(191, 79, 75)));
		chartData.add(new ArcLineData("open","76%" ,  (0.76*100), Color.rgb(242, 167, 69)));
		chartData.add(new ArcLineData("workdone","86%" , (0.86*100), Color.rgb(60, 173, 213)));
		chartData.add(new ArcLineData("dispute","36%" ,  (0.36*100), Color.rgb(90, 79, 88)));
	}
	
	private void addAttrInfo()
	{
		/////////////////////////////////////////////////////////////
		//设置附加信息
		Paint paintLib = new Paint();
		paintLib.setColor(Color.rgb(46, 164, 212)); 
		paintLib.setTextAlign(Align.CENTER);
		paintLib.setTextSize(30);		
		paintLib.setAntiAlias(true);
		chart.getPlotAttrInfo().addAttributeInfo(XEnum.Location.TOP, "圆弧式条形图", 0.1f, paintLib);
		chart.getPlotAttrInfo().addAttributeInfo(XEnum.Location.BOTTOM, "XCL-Charts", 0.4f, paintLib);
			
		Paint paintSrc = new Paint();		
		paintSrc.setTextAlign(Align.CENTER);
		paintSrc.setTextSize(25);
		paintSrc.setAntiAlias(true);
		paintSrc.setColor(Color.rgb(41, 34, 102));
		chart.getPlotAttrInfo().addAttributeInfo(XEnum.Location.BOTTOM,
								"ExcelPro", 0.2f, paintSrc);		
		/////////////////////////////////////////////////////////////
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


}