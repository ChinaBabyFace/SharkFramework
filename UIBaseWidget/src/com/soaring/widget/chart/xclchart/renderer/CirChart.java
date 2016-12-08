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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.util.Log;

import com.soaring.widget.chart.xclchart.common.MathHelper;

/**
 * @ClassName CirChart
 * @Description 圆形类图表，如饼图，刻度盘...类的图表的基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class CirChart extends EventChart {
	
	private static final String TAG = "CirChart";
	
	//半径
	private float mRadius=0.0f;		
	
	//标签注释显示位置 [隐藏,Default,Inside,Ouside,Line]
	private XEnum.SliceLabelPosition mLabelPosition;
	
	//开放标签画笔让用户设置
	private Paint mPaintLabel = null;
	//当标签为Line类型时使用
	private Paint mPaintLabelLine = null;
	
	//初始偏移角度
	protected float mOffsetAngle = 0.0f;//180;

	
	//标签与点的转折线长度
	private int mLabelBrokenLineLength = 10;
	
		
	public CirChart()
	{
		initChart();
	}
	
	private void initChart()
	{
		//标签显示位置
		mLabelPosition = XEnum.SliceLabelPosition.INSIDE;
				
		mPaintLabel = new Paint();
		mPaintLabel.setColor(Color.BLACK);
		mPaintLabel.setTextSize(18);
		mPaintLabel.setAntiAlias(true);
		mPaintLabel.setTextAlign(Align.CENTER);	
	
	}
	
	private void initLabelLinePaint()
	{
		if(null == mPaintLabelLine)
		{
			mPaintLabelLine = new Paint();
			mPaintLabelLine.setColor(Color.BLACK);
			mPaintLabelLine.setAntiAlias(true);
			mPaintLabelLine.setStrokeWidth(2);
		}
	}
	
	
	@Override
	protected void calcPlotRange()
	{
		super.calcPlotRange();		
		
		this.mRadius = Math.min( div(this.plotArea.getWidth() ,2f) , 
				 				 div(this.plotArea.getHeight(),2f) );	
	}
	
	
	/**
	 * 返回半径
	 * @return 半径
	 */
	public float getRadius()
	{
		return mRadius;
	}
	
	/**
	 * 设置饼图(pie chart)起始偏移角度
	 * @param Angle 偏移角度
	 */
	public void setInitialAngle(final int Angle)
	{
		mOffsetAngle = Angle;
	}
	
	/**
	 * 返回图的起始偏移角度
	 * @return 偏移角度
	 */
	public float getInitialAngle()
	{
		return mOffsetAngle;
	}

	/**
	 * 设置标签显示在扇区的哪个位置(里面，外面，隐藏)
	 * @param position 显示位置
	 */
	public void setLabelPosition(XEnum.SliceLabelPosition position)
	{
		mLabelPosition = position;
		//INNER,OUTSIDE,HIDE
		switch(position)
		{
		case INSIDE :
			mPaintLabel.setTextAlign(Align.CENTER);
			break;
		case OUTSIDE :
			break;
		case HIDE :
			break;
		case LINE :
			break;
		default:			
		}				
	}
	
	/**
	 * 返回标签位置设置
	 * @return	标签位置
	 */
	public XEnum.SliceLabelPosition getLabelPosition()
	{
		return mLabelPosition;
	}
	
	/**
	 * 开放标签画笔
	 * @return 画笔
	 */
	public Paint getLabelPaint()
	{
		return mPaintLabel;
	}
	
	/**
	 * 开放标签线画笔(当标签为Line类型时有效)
	 * @return 画笔
	 */
	public Paint getLabelLinePaint()
	{
		initLabelLinePaint();
		return mPaintLabelLine;
	}
	
	protected void renderLabelInside(Canvas canvas,String text,
									 float cirX,float cirY,float radius,float calcAngle)
	{
		//显示在扇形的中心
		float calcRadius = MathHelper.getInstance().sub(radius , radius/2f);
		
		//计算百分比标签
		PointF point = MathHelper.getInstance().calcArcEndPointXY(
										cirX, cirY, calcRadius, calcAngle); 						 
		//标识
		canvas.drawText( text ,point.x, point.y ,mPaintLabel);
		
	}
	
	protected void renderLabelOutside(Canvas canvas,String text,
							float cirX,float cirY,float radius,float calcAngle)
	{
		//显示在扇形的外部
		float calcRadius = MathHelper.getInstance().add(radius  , radius/10f);
		//计算百分比标签
		PointF point = MathHelper.getInstance().calcArcEndPointXY(
										cirX, cirY, calcRadius, calcAngle); 	
			 
		//标识
		canvas.drawText(text,point.x, point.y,mPaintLabel);  
	
	}
	
	protected void renderLabelLine(Canvas canvas,String text,
									float cirX,float cirY,float radius,float calcAngle)
	{
		initLabelLinePaint();
		
		//显示在扇形的外部
		//1/4处为起始点
		float calcRadius = MathHelper.getInstance().sub(radius  , radius / 4f);
		MathHelper.getInstance().calcArcEndPointXY(
										cirX, cirY, calcRadius, calcAngle);	
		
		float startX = MathHelper.getInstance().getPosX();
		float startY = MathHelper.getInstance().getPosY();
			    
	    //延长原来半径的一半在外面
	    calcRadius =  radius / 2f;		
	    MathHelper.getInstance().calcArcEndPointXY(startX, startY, calcRadius, calcAngle);
		float stopX = MathHelper.getInstance().getPosX();
	    float stopY = MathHelper.getInstance().getPosY();
	    //连接线
	    canvas.drawLine(startX, startY,stopX, stopY, mPaintLabelLine);		    		    
	    		    
	    float endX = 0.0f;			    
	    if(Float.compare(stopX, cirX) == 0){ //位于中间竖线上				    			    	
	    	if(Float.compare(stopY, cirY) == 1 ) //中点上方,左折线
	    	{
	    		mPaintLabel.setTextAlign(Align.LEFT);
	    		endX = stopX + mLabelBrokenLineLength;	
	    	}else{ //中点下方,右折线		    		
	    		endX = stopX - mLabelBrokenLineLength;	
	    		mPaintLabel.setTextAlign(Align.RIGHT);
	    	}
	    }else if(Float.compare(stopY, cirY) == 0 ){ //中线横向两端
	    	
	    	if(Float.compare(stopX, cirX) == 0 ||
	    			Float.compare(stopX, cirX) == -1) //左边
	    	{
	    		mPaintLabel.setTextAlign(Align.RIGHT);
	    	}else{
	    		mPaintLabel.setTextAlign(Align.LEFT);
	    	}		    	
	    	endX = stopX;		    
	    }else if(Float.compare(stopX + mLabelBrokenLineLength, cirX) == 1 ) //右边
	    {
	    	mPaintLabel.setTextAlign(Align.LEFT);
	    	endX = stopX + mLabelBrokenLineLength;		    		    	
	    }else if(Float.compare(stopX - mLabelBrokenLineLength,cirX) == -1  ) //左边
	    {
	    	mPaintLabel.setTextAlign(Align.RIGHT);
	    	endX = stopX - mLabelBrokenLineLength;		    			    	    
	    }else {
	    	endX = stopX;
	    	mPaintLabel.setTextAlign(Align.CENTER);
	    }		    
	 
	    //转折线
	    canvas.drawLine(stopX, stopY, endX, stopY, mPaintLabelLine);
	    //标签
	    canvas.drawText(text,endX, stopY,mPaintLabel);     	
	
	}
		
	
	/**
	 * 绘制标签
	 * @param text	内容
	 * @param cirX	x坐标
	 * @param cirY	y坐标
	 * @param radius	半径
	 * @param offsetAngle	偏移角度
	 * @param curretAnglet	当前角度
	 */
	protected boolean renderLabel(Canvas canvas, String text,
			final float cirX,
			final float cirY,
			final float radius,		
			final double offsetAngle,
			final double curretAnglet)
	{
		if(XEnum.SliceLabelPosition.HIDE == mLabelPosition) return true;
		if(""==text||text.length()==0)return true;
				
		float calcAngle = 0.0f;
				
		calcAngle =  (float) MathHelper.getInstance().add(offsetAngle , curretAnglet/2);
		if(Float.compare(calcAngle,0.0f) == 0 
				|| Float.compare(calcAngle,0.0f) == -1 )
		{
			Log.e(TAG,"计算出来的圆心角等于0.");
			return false;
		}
				
		if(XEnum.SliceLabelPosition.INSIDE  == mLabelPosition)
		{			 
			//显示在扇形的内部
			renderLabelInside(canvas,text,cirX,cirY,radius,calcAngle);
		}else if(XEnum.SliceLabelPosition.OUTSIDE == mLabelPosition){
			//显示在扇形的外部
			renderLabelOutside(canvas,text,cirX,cirY,radius,calcAngle);		
		}else if(XEnum.SliceLabelPosition.LINE == mLabelPosition){
			//显示在扇形的外部
			//1/4处为起始点
			renderLabelLine(canvas,text,cirX,cirY,radius,calcAngle);
		}else{
			Log.e(TAG,"未知的标签处理类型.");
			return false;
		}		
		return true;
	}
		
	@Override
	protected boolean postRender(Canvas canvas) throws Exception 
	{	
		try {
			super.postRender(canvas);
			
			//计算主图表区范围
			 calcPlotRange();
			//画Plot Area背景			
			 plotArea.render(canvas);			 
			//绘制标题
			renderTitle(canvas);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	

}
