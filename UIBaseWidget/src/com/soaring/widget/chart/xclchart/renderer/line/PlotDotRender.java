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
package com.soaring.widget.chart.xclchart.renderer.line;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;

import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.XEnum;

/**
 * @ClassName PlotDotRender
 * @Description  绘制交叉点的形状
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class PlotDotRender {
	
	private static PlotDotRender instance = null;
	
	protected Paint mPaintFill = null;
	
	private Path mPath = null;
	private RectF mRect = new RectF();

	public PlotDotRender()
	{

	}
	
	public static synchronized PlotDotRender getInstance()
	{
		if(instance == null)
		{
			instance = new PlotDotRender();
		}
		return instance;
	}
	
	private void initPath()
	{
		if(null == mPath)
		{
			mPath = new Path();
		}else{
			mPath.reset();
		}		
	}	

	
	/**
	 * 开放填充内部环形的画笔
	 */
	public Paint getInnerFillPaint()
	{
		if(null == mPaintFill)
		{
			mPaintFill = new Paint();
			mPaintFill.setColor(Color.WHITE); 
			mPaintFill.setStyle(Style.FILL);
			mPaintFill.setAntiAlias(true);
		}
		return mPaintFill;
	}
	
	/**
	 * 绘制线上的坐标点
	 * 
	 * @param pDot	点类型
	 * @param left	左边x坐标
	 * @param top	左边Y坐标
	 * @param right	右边x坐标
	 * @param bottom 右边Y坐标
	 * @param paint	画笔
	 */
	public RectF renderDot(Canvas canvas, PlotDot pDot, 
						  float left, float top, float right,float bottom, Paint paint) {
				
		float radius = pDot.getDotRadius();		
		//mRect.setEmpty();		
		mRect.left =  0.0f;
		mRect.top =  0.0f;
		mRect.right =  0.0f;
		mRect.bottom = 0.0f;		
		
		if(Float.compare(radius, 0.0f) == 0 
				|| Float.compare(radius, 0.0f) == -1){			
			return mRect;
		}						
		
		float cX = 0.0f;
		if(XEnum.DotStyle.DOT == pDot.getDotStyle() ||
				XEnum.DotStyle.RING == pDot.getDotStyle() ||
						XEnum.DotStyle.X == pDot.getDotStyle()	)
		{
			//cX = MathHelper.getInstance().add(left,
			//				MathHelper.getInstance().sub(right, left));				
			cX =  left + Math.abs(right - left);
			
			mRect.left =  (cX - radius);
			mRect.top =   (bottom - radius);
			mRect.right =  (cX + radius);
			mRect.bottom =  (bottom + radius);			
		}
		
		switch (pDot.getDotStyle()) {
		case DOT:										
			canvas.drawCircle(cX, bottom,radius, paint);			
			break;
		case RING:					
			renderRing(canvas,paint,radius,pDot,cX ,bottom);
			break;
		case TRIANGLE: // 等腰三角形
			renderTriangle(canvas,paint,radius,right,bottom);			
			break;
		case PRISMATIC: // 棱形 Prismatic			
			renderPrismatic(canvas,paint,radius,right,bottom,left );            
			break;
		case RECT:
			renderRect(canvas,paint,radius,right,bottom );			
			break;
		case X:
			renderX(canvas,paint);			    
			break;
		case HIDE:
		default:
		}						
		return mRect;
	}
	
	
	private void renderRing(Canvas canvas,Paint paint,float radius,PlotDot pDot,float cX ,float bottom)
	{
		float ringRadius = radius * 0.7f; // MathHelper.getInstance().mul(radius, 0.7f);		
        canvas.drawCircle(cX, bottom, radius, paint);

        getInnerFillPaint().setColor(pDot.getRingInnerColor());
        canvas.drawCircle(cX, bottom,ringRadius, getInnerFillPaint()); 
	}
	
	
	private void renderTriangle(Canvas canvas,Paint paint,float radius,float right,float bottom)
	{
		
		float halfRadius = MathHelper.getInstance().div(radius , 2f);
		float triganaleHeight = radius + radius / 2;
		
		initPath();
		mPath.moveTo(right - radius, bottom + halfRadius);
		mPath.lineTo(right, bottom - triganaleHeight);
		mPath.lineTo(right + radius, bottom + halfRadius);
		mPath.close();
        canvas.drawPath(mPath, paint);
        mPath.reset();
                    
        mRect.left =  (right - radius);
		mRect.top = ( bottom - triganaleHeight);
		mRect.right =  ( right + radius);
		mRect.bottom =  ( bottom + halfRadius);	
	}
	
	
	private void renderPrismatic(Canvas canvas,Paint paint,float radius,float right,float bottom,
								 float left )
	{
		initPath();
		mPath.moveTo(right - radius, bottom);
		mPath.lineTo(right, bottom - radius);
		mPath.lineTo(right + radius, bottom);
		mPath.lineTo(left + (right - left), bottom + radius);
		mPath.close();
        canvas.drawPath(mPath, paint);
        mPath.reset();
        
    	mRect.left = ( right -  radius  );
		mRect.top =  ( bottom - radius);
		mRect.right =  ( right + radius);
		mRect.bottom =  (bottom + radius);
	}
	
	
	private void renderRect(Canvas canvas,Paint paint,float radius,float right,float bottom )
	{
		paint.setStyle(Style.FILL);	
		
		mRect.left =  (right - radius);
		mRect.top =   (bottom - radius); 
		mRect.right =  (right + radius);
		mRect.bottom = (bottom + radius);
		canvas.drawRect(mRect,paint);
	}
	
	private void renderX(Canvas canvas,Paint paint)
	{
		//float pWidth = paint.getStrokeWidth();
		//paint.setStrokeWidth(2);
		canvas.drawLine(mRect.left, mRect.top, mRect.right, mRect.bottom , paint);
	    canvas.drawLine(mRect.left, mRect.bottom, mRect.right, mRect.top, paint);
	    //paint.setStrokeWidth(pWidth);
	}
	
}
