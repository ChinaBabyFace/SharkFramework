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
 * @version 1.0
 */

package com.soaring.widget.chart.xclchart.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;

import com.soaring.widget.chart.xclchart.common.CurveHelper;
import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.event.click.PointPosition;

import java.util.List;

/**
 * @ClassName XChart
 * @Description 所有线类，如折线，曲线等图表类的基类,在此主要用于Key及坐标系的绘制。
 * 
 * @author XiongChuanLiang<br/>
 *         (xcl_168@aliyun.com) 
 *         
 */

public class LnChart extends AxisChart {
	
	//private static final String TAG="LnChart";

	// 是否显示顶轴
	private boolean mTopAxisVisible = true;
	// 是否显示底轴
	private boolean mRightAxisVisible = true;	
	
	private PointF[] BezierControls ;
	
	

	public LnChart() {
		super();
		initChart();
	}

	private void initChart() {
		//默认显示Key
		plotLegend.showLegend();
	}

	/**
	 * 竖向柱形图 Y轴的屏幕高度/数据轴的刻度标记总数 = 步长
	 * 
	 * @return Y轴步长
	 */
	private float getVerticalYSteps(int tickCount) {
		return MathHelper.getInstance().div(getAxisScreenHeight() , tickCount);
	}

	/**
	 * 竖向图 得到X轴的步长 X轴的屏幕宽度 / 刻度标记总数 = 步长
	 * 
	 * @param num
	 *            刻度标记总数
	 * @return X轴步长
	 */
	protected float getVerticalXSteps(int num) {
		//float XSteps = (float) Math.ceil(getAxisScreenWidth() / num);		
		return MathHelper.getInstance().div(getAxisScreenWidth(),num);
	}

	/**
	 * 是否显示顶上的轴线
	 * 
	 * @param visible 是否显示
	 */
	public void setTopAxisVisible(boolean visible) {
		mTopAxisVisible = visible;
	}

	/**
	 * 是否显示右边轴线
	 * 
	 * @param visible 是否显示
	 */
	public void setRightAxisVisible(boolean visible) {
		mRightAxisVisible = visible;
	}

	//
	/**
	 * 绘制左边竖轴(对线图而言坐标轴默认都是封闭的)
	 */
	protected void renderVerticalDataAxis(Canvas canvas) {
		// 数据轴数据刻度总个数
		int tickCount = dataAxis.getAixTickCount();
		// 数据轴高度步长
		float YSteps = getVerticalYSteps(tickCount);

		float plotLeft = plotArea.getLeft();
		float plotTop = plotArea.getTop();
		float plotRight = plotArea.getRight();
		float plotBottom = plotArea.getBottom();
		float currentY = plotBottom;
		double currentTickLabel = 0d;

		// 数据轴(Y 轴)
		for (int i = 0; i <= tickCount; i++) {
			
			//将当前为第几个tick传递轴，用以区分是否为主明tick
			dataAxis.setAxisTickCurrentID(i);
			
			// 依起始数据坐标与数据刻度间距算出上移高度			
			currentY =  MathHelper.getInstance().sub(plotBottom, i * YSteps);
			
			// 标签
			currentTickLabel = MathHelper.getInstance().add(
								dataAxis.getAxisMin(),i * dataAxis.getAxisSteps());

			if (i > 0) {
				// 从左到右的横向网格线
				if (i % 2 != 0) {
					plotGrid.renderOddRowsFill(canvas,plotLeft, 
							MathHelper.getInstance().add(currentY , YSteps),
							plotRight, currentY);
				} else {
					plotGrid.renderEvenRowsFill(canvas,plotLeft, 
							MathHelper.getInstance().add(currentY , YSteps),
							plotRight, currentY);
				}

				if (i > 0 && i < tickCount){
					plotGrid.setPrimaryTickLine(dataAxis.isPrimaryTick());
					plotGrid.renderGridLinesHorizontal(canvas,plotLeft, currentY,
							plotRight, currentY);
				}
			}
			dataAxis.renderAxisHorizontalTick(this,canvas,plotLeft, currentY,
					Double.toString(currentTickLabel));

		}

		// top X轴线
		if (mTopAxisVisible)
		{		
			dataAxis.renderAxis(canvas,plotLeft, plotTop, plotRight, plotTop);
		}else{
			//即如果顶轴不显示的话，补上一条网格线
			plotGrid.renderGridLinesHorizontal(canvas,plotLeft, plotTop,plotRight, plotTop);
		}

		// 左Y轴 线
		dataAxis.renderAxis(canvas,plotLeft, plotBottom, plotLeft, plotTop);
		
		//如底线不显示，则补上一条网格线
		if(!dataAxis.getAxisLineVisible() || !dataAxis.getVisible())
		{
			plotGrid.renderGridLinesHorizontal(
					canvas,plotLeft, plotBottom,plotRight, plotBottom);
		}
	}

	// 坐标轴是封闭的
	/**
	 * 绘制右边数据轴
	 */
	protected void renderVerticalDataAxisRight(Canvas canvas) {
		// 数据轴数据刻度总个数
		int tickCount = dataAxis.getAixTickCount();
		// 数据轴高度步长
		float YSteps = getVerticalYSteps(tickCount);
		float currentY = plotArea.getBottom();

		float markHeight = MathHelper.getInstance().div(
				  dataAxis.getTickMarksPaint().getStrokeWidth() , 2f);

		// 数据轴(Y 轴)
		for (int i = 0; i <= tickCount; i++) {
			if (i == 0)
				continue;
			
			//将当前为第几个tick传递轴，用以区分是否为主明tick
			dataAxis.setAxisTickCurrentID(i);					
			currentY = MathHelper.getInstance().sub(plotArea.getBottom() , i * YSteps);
			
			// 标签
			Double currentTickLabel = MathHelper.getInstance().add(
								dataAxis.getAxisMin() , (i * dataAxis.getAxisSteps()));

			if (i == tickCount) {
				dataAxis.renderAxisHorizontalTick(this,canvas,plotArea.getRight(),
						plotArea.getTop(), Double.toString(currentTickLabel));
			} else {
				this.dataAxis
						.renderAxisHorizontalTick(this,canvas,plotArea.getRight(),
								MathHelper.getInstance().add(currentY , markHeight),
								Double.toString(currentTickLabel));
			}
			// 右边轴默认不显示网格,所以在此忽略不作处理
		}
		// 轴 线
		float paintWidth = MathHelper.getInstance().div(
				  		    dataAxis.getAxisPaint().getStrokeWidth() , 2f);
		dataAxis.renderAxis(canvas,plotArea.getRight() + paintWidth,
				plotArea.getBottom(), plotArea.getRight() + paintWidth,
				plotArea.getTop());
	}

	/**
	 * 绘制底部标签轴
	 */
	protected void renderVerticalCategoryAxis(Canvas canvas) {
		// 标签轴(X 轴)
		float currentX = plotArea.getLeft();

		// 得到标签轴数据集
		List<String> dataSet = categoryAxis.getDataSet();
		// 与柱形图不同，无须多弄一个
		float XSteps = 0.0f;
		if (dataSet.size() == 1)  //label仅一个时右移
		{
			XSteps = div( getAxisScreenWidth(),(dataSet.size()));
		}else{
			XSteps = div( getAxisScreenWidth(),(dataSet.size() - 1));
		}		

		for (int i = 0; i < dataSet.size(); i++) {

			// 依初超始X坐标与标签间距算出当前刻度的X坐标
			currentX = MathHelper.getInstance().add(plotArea.getLeft() , (i) * XSteps);
			
			// 绘制竖向网格线
			if (plotGrid.isShowVerticalLines()) {
				if (i > 0 && i + 1 < dataSet.size())
					plotGrid.renderGridLinesVertical(canvas,currentX,
							plotArea.getBottom(), currentX,
							plotArea.getTop());
			}

			if (dataSet.size() == i + 1) {
				categoryAxis.renderAxisVerticalTick(canvas,plotArea.getRight(),
						plotArea.getBottom(), dataSet.get(i));
			} else {
				// 画上标签/刻度线
				categoryAxis.renderAxisVerticalTick(canvas,currentX,
						plotArea.getBottom(), dataSet.get(i));
			}

		}
		// 右边轴线
	if (mRightAxisVisible)
	{
		categoryAxis.renderAxis(canvas,plotArea.getRight(),
				plotArea.getBottom(), plotArea.getRight(),
				plotArea.getTop());
	}else{
		//即如果右轴不显示的话，补上一条网格线
		plotGrid.renderGridLinesVertical(canvas,plotArea.getRight(),
				plotArea.getBottom(), plotArea.getRight(),plotArea.getTop());
	}

		// bottom轴 线		
		categoryAxis.renderAxis(canvas,plotArea.getLeft(),
				plotArea.getBottom(), plotArea.getRight(),
				plotArea.getBottom());
		if(!categoryAxis.getAxisLineVisible() || !categoryAxis.getVisible())
		{
			//补上一条网格线
			plotGrid.renderGridLinesVertical(canvas,plotArea.getLeft(),
					plotArea.getBottom(), plotArea.getLeft(),plotArea.getTop());
			
		}
	}
	
	
	@Override
	public boolean isPlotClickArea(float x,float y)
	{				
		if(!getListenItemClickStatus())return false;	
		
		if(Float.compare(x , getLeft()) == -1 ) return false;
		if(Float.compare(x,  getRight() ) == 1 ) return false;	
		
		if(Float.compare( y , getPlotArea().getTop() ) == -1  ) return false;
		return Float.compare(y, getPlotArea().getBottom()) != 1;
	}
	
	/**
	 * 返回当前点击点的信息
	 * @param x 点击点X坐标
	 * @param y	点击点Y坐标
	 * @return 返回对应的位置记录
	 */
	public PointPosition getPositionRecord(float x,float y)
	{		
		return getPointRecord(x,y);
	}
		
	
	
	
	//遍历曲线
	protected void renderBezierCurveLine(Canvas canvas,Paint paint,Path bezierPath ,
										List<PointF> lstPoints )
	{		
		if(null == BezierControls ) BezierControls = new PointF[2];				
		paint.setStyle(Style.STROKE);
		
			for(int i = 0;i<lstPoints.size();i++)
			{
				if(i<3) continue;
				
				CurveHelper.curve3(lstPoints.get(i - 2),
						lstPoints.get(i - 1),
						lstPoints.get(i - 3),
						lstPoints.get(i),
						BezierControls);
				renderBezierCurvePath(canvas,paint,bezierPath,
								lstPoints.get(i-2), lstPoints.get(i -1 ), 
								BezierControls );
			}			
		
		
			if(lstPoints.size()> 3)
			{			
				PointF stop  = lstPoints.get(lstPoints.size()-1);
				//PointF start = lstPoints.get(lstPoints.size()-2);						
				CurveHelper.curve3(lstPoints.get(lstPoints.size() - 2),
						stop,
						lstPoints.get(lstPoints.size() - 3),
						stop,
						BezierControls);
				renderBezierCurvePath(canvas,paint,bezierPath,
								lstPoints.get(lstPoints.size()-2), 
								lstPoints.get(lstPoints.size() -1 ), 
								BezierControls );
			}					
	}


	//绘制曲线
	private void renderBezierCurvePath(Canvas canvas,Paint paint,Path bezierPath,
					PointF start,PointF stop,PointF[] bezierControls)
	{		
		if(null == bezierPath)bezierPath = new Path();
		bezierPath.reset();					
		bezierPath.moveTo(start.x, start.y);
		bezierPath.cubicTo( bezierControls[0].x, bezierControls[0].y, 
				bezierControls[1].x, bezierControls[1].y, 
				stop.x, stop.y);	
				
		canvas.drawPath(bezierPath, paint);		
		bezierPath.reset();
	}
	

}
