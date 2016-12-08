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

/**
 *  version	 	date
 *  0.1 		2014-06-12
 *  1.0 		2014-07-04
 *  1.2			2014-07-13
 */

package com.soaring.widget.chart.xclchart.renderer;

/**
 * @ClassName XChart
 * @Description 所有图表类的基类,定义了图表区，标题，背景等
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * 
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.plot.Border;
import com.soaring.widget.chart.xclchart.renderer.plot.BorderRender;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotArea;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotAreaRender;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotGrid;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotGridRender;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotLegend;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotLegendRender;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotTitle;
import com.soaring.widget.chart.xclchart.renderer.plot.PlotTitleRender;

public class XChart implements IRender {

	// 开放主图表区
	protected PlotAreaRender plotArea = null;
	// 开放主图表区网格
	protected PlotGridRender plotGrid = null;
	// 标题栏
	private PlotTitleRender plotTitle = null;
	// 图大小范围
	private float mLeft = 0.0f;
	private float mTop = 0.0f;
	private float mRight = 0.0f;
	private float mBottom = 0.0f; //5f;
	// 图宽高
	private float mWidth = 0.0f;
	private float mHeight = 0.0f;

	// 图的内边距属性
	private float mPaddingTop = 0f;
	private float mPaddingBottom = 0f;
	private float mPaddingLeft = 0f;
	private float mPaddingRight = 0f;
	// 是否画背景色
	private boolean mBackgroundColorVisible = false;
	
	//坐标系原点坐标
	private float[] mTranslateXY = new float[2];		
		
	//是否显示边框
	private boolean mShowBorder = false;
	private BorderRender mBorder = null;
	
	//图例类
	protected PlotLegendRender plotLegend = null;
	
		
	public XChart() {
		initChart();
	}

	private void initChart() {
		//默认的原点坐标
		mTranslateXY[0] = 0.0f;
		mTranslateXY[1] = 0.0f;
		
		//图例
		plotLegend = new PlotLegendRender(this);				
		
		// 图表
		plotArea = new PlotAreaRender();
		plotGrid = new PlotGridRender();		
		plotTitle = new PlotTitleRender();
		plotTitle.setVerticalAlign(XEnum.VerticalAlign.MIDDLE);
		plotTitle.setTitleAlign(XEnum.ChartTitleAlign.MIDDLE);		
	}
	
	/**
	 * 开放图例基类
	 * @return	基类
	 */
	public PlotLegend getPlotLegend()
	{
		return plotLegend;
	}	
	


	/**
	 * 用于指定绘图区与图范围的内边距。单位为PX值. 即用于确定plotArea范围
	 * 
	 * @param left
	 *            绘图区与图左边的保留宽度，用于显示左边轴及轴标题之类
	 * @param top
	 *            绘图区与图顶部的保留距离，用于显示标题及图例之类
	 * @param right
	 *            绘图区与图右边的保留宽度，用于显示右边轴及轴标题之类
	 * @param bottom
	 *            绘图区与图底部的保留距离，用于显示底轴及轴标题之类	
	 */
	public void setPadding(float left, float top, float right,float bottom  ) {
		if (top > 0)
			mPaddingTop = top;
		if (bottom > 0)
			mPaddingBottom = bottom;
		if (left > 0)
			mPaddingLeft = left;
		if (right > 0)
			mPaddingRight = right;
	}
			

	/**
	 * 返回主图表区基类
	 * 
	 * @return 主图表区基类
	 */
	public PlotArea getPlotArea() {
		return plotArea;
	}

	/**
	 * 返回主图表区网格基类
	 * 
	 * @return 网格基类
	 */
	public PlotGrid getPlotGrid() {
		return plotGrid;
	}

	/**
	 * 返回图的标题基类
	 * 
	 * @return 标题基类
	 */
	public PlotTitle getPlotTitle() {
		return plotTitle;
	}
	
	/**
	 * 设置图表绘制范围.
	 * @param width
	 *            图表宽度
	 * @param height
	 *            图表高度
	 */
	public void setChartRange( float width,float height) {	
		setChartRange(0.0f,0.0f,width,height);
	}
	

	/**
	 * 设置图表绘制范围,以指定起始点及长度方式确定图表大小.
	 * 
	 * @param startX
	 *            图表起点X坐标
	 * @param startY
	 *            图表起点Y坐标
	 * @param width
	 *            图表宽度
	 * @param height
	 *            图表高度
	 */
	public void setChartRange(float startX, float startY, float width,
			float height) {
		
		if (startX > 0)
			mLeft = startX;
		if (startY > 0)
			mTop = startY;
				
		mRight = add(startX , width);
		mBottom = add(startY , height);

		if (Float.compare(width, 0.0f) > 0)mWidth = width;
		if (Float.compare(height, 0.0f) > 0)mHeight = height;
	}

	/**
	 * 设置图表绘制范围,以指定上下左右范围方式确定图表大小.
	 * 
	 * @param left
	 *            图表左上X坐标
	 * @param top
	 *            图表左上Y坐标
	 * @param right
	 *            图表右下X坐标
	 * @param bottom
	 *            图表右上Y坐标
	 */
	public void setChartRect(float left, float top, float right, float bottom) {

		if (left > 0)
			mLeft = left;
		if (top > 0)
			mTop = top;
		if (right > 0)
			mRight = right;
		if (bottom > 0)
			mBottom = bottom;
		
		mWidth = Math.abs(right - left);
		mHeight = Math.abs(bottom - top);
	}
			

	/**
	 * 是否为竖屏显示
	 * 
	 * @return 是否为竖屏
	 */
	public boolean isVerticalScreen() {
		return( (Float.compare(mWidth , mHeight) == -1)?true:false);
	}


	/**
	 * 设置标题
	 * 
	 * @param title 标题
	 */
	public void setTitle(String title) {
		plotTitle.setTitle(title);
	}

	/**
	 * 设置子标题
	 * 
	 * @param subtitle 子标题
	 */
	public void addSubtitle(String subtitle) {
		plotTitle.setSubtitle(subtitle);
	}

	/**
	 * 设置标题上下显示位置,即图上边距与绘图区间哪个位置(靠上，居中，靠下).
	 * @param position 显示位置
	 */
	public void setTitleVerticalAlign(XEnum.VerticalAlign position) {
		plotTitle.setVerticalAlign(position);
	}

	/**
	 * 设置标题横向显示位置(靠左，居中，靠右)
	 * 
	 * @param align 显示位置
	 */
	public void setTitleAlign(XEnum.ChartTitleAlign align) {
		plotTitle.setTitleAlign(align);
	}
	

	/**
	 * 返回图表左边X坐标
	 * 
	 * @return 左边X坐标
	 */
	public float getLeft() {		
		return mLeft;
	}

	/**
	 * 返回图表上方Y坐标
	 * 
	 * @return 上方Y坐标
	 */
	public float getTop() {
		return mTop;
	}

	/**
	 * 返回图表右边X坐标
	 * 
	 * @return 右边X坐标
	 */
	public float getRight() {
		return mRight;
	}

	/**
	 * 返回图表底部Y坐标
	 * 
	 * @return 底部Y坐标
	 */
	public float getBottom() {
		return mBottom;
	}

	/**
	 * 返回图表宽度
	 * 
	 * @return 宽度
	 */
	public float getWidth() {
		
		return mWidth;
	}

	/**
	 * 返回图表高度
	 * 
	 * @return 高度
	 */
	public float getHeight() {
		return mHeight;
	}

	/**
	 * 返回图绘制区相对图顶部边距的高度
	 * 
	 * @return 绘图区与图边距间的PX值
	 */
	public float getPaddingTop() {
		return this.mPaddingTop;
	}
	
	/**
	 * 返回图绘制区相对图底部边距的高度
	 * 
	 * @return 绘图区与图边距间的PX值
	 */
	public float getPaddingBottom() {
		return mPaddingBottom;
	}

	/**
	 * 图绘制区相对图左边边距的宽度
	 * 
	 * @return 绘图区与图边距间的PX值
	 */
	public float getPaddingLeft() {
		return mPaddingLeft;
	}

	/**
	 * 图绘制区相对图右边边距的宽度
	 * 
	 * @return 绘图区与图边距间的PX值
	 */
	public float getPaddingRight() {
		return mPaddingRight;
	}
	
	/**
	 * 返回图中心点坐标
	 * @return 坐标
	 */	
	public PointF getCenterXY()
	{
		PointF point = new PointF();
		point.x = this.getLeft() + div(this.getWidth() , 2f) ;
		point.y = this.getTop() + div(this.getHeight() , 2f) ;		
		return point;
	}
	
	
	/**
	 * 设置绘画时的坐标系原点位置
	 * @param x 原点x位置
	 * @param y 原点y位置
	 */
	public void setTranslateXY(float x,float y)
	{
		mTranslateXY[0] = x;
		mTranslateXY[1] = y;
	}
	
	/**
	 * 返回坐标系原点坐标
	 * @return 原点坐标
	 */
	public float[] getTranslateXY()
	{
		return mTranslateXY;
	}
		
	/**
	 * 计算图的显示范围,依屏幕px值来计算.
	 */
	protected void calcPlotRange() {	
		
		int borderWidth = getBorderWidth();		
		plotArea.setBottom(sub(this.getBottom() - borderWidth/2 , mPaddingBottom) );
		plotArea.setLeft(add(this.getLeft() + borderWidth/2 , mPaddingLeft));
		plotArea.setRight(sub(this.getRight() - borderWidth/2 , mPaddingRight));		
		plotArea.setTop(add(this.getTop() + borderWidth/2 , mPaddingTop));
	}
	

	// 导出成文件,待实现
	// public void exportAsBmpfile(String fileName)
	// {

	// }
	


	/**
	 * 绘制标题
	 */
	protected void renderTitle(Canvas canvas) {				
		int borderWidth = getBorderWidth();
		this.plotTitle.renderTitle(
				mLeft + borderWidth, mRight - borderWidth, mTop + borderWidth,
				mWidth, this.plotArea.getTop(), canvas);
	}
	
	
	/**
	 * 设置是否绘制背景
	 * 
	 * @param visible 是否绘制背景
	 */
	public void setApplyBackgroundColor(boolean visible) {
		mBackgroundColorVisible = visible;
	}

	/**
	 * 设置图的背景色
	 * 
	 * @param color   背景色
	 */
	public void setBackgroundColor(int color) {
		
		getBackgroundPaint().setColor(color);
		getPlotArea().getBackgroundPaint().setColor(color);
		
		//?? 
		if(null == mBorder)mBorder = new BorderRender();
		mBorder.getChartBackgroundPaint().setColor(color);		
	}
	
	/**
	 * 开放背景画笔
	 * 
	 * @return 画笔
	 */
	public Paint getBackgroundPaint() {
		if(null == mBorder)mBorder = new BorderRender();
		return mBorder.getChartBackgroundPaint();			
	}
	
	/**
	 * 显示矩形边框
	 */
	public void showBorder()
	{
		 mShowBorder = true;
		 if(null == mBorder)mBorder = new BorderRender();
		 mBorder.setBorderRectType(XEnum.RectType.RECT);
	}
	
	/**
	 * 显示圆矩形边框
	 */
	public void showRoundBorder()
	{
		mShowBorder = true;
		if(null == mBorder)mBorder = new BorderRender();
		mBorder.setBorderRectType(XEnum.RectType.ROUNDRECT);
	}
	
	/**
	 * 开放边框绘制类
	 * @return 边框绘制类
	 */
	public Border getBorder()
	{
		if(null == mBorder)mBorder = new BorderRender();
		return mBorder; 
	}
	
	/**
	 * 是否显示边框
	 * @return 是否显示
	 */
	public boolean isShowBorder()
	{
		return mShowBorder;
	}
	
	/**
	 * 得到边框宽度,默认为5px
	 * @return 边框宽度
	 */
	public int getBorderWidth()
	{
		int borderWidth = 0;
		if(mShowBorder)
		{
			 if(null == mBorder)mBorder = new BorderRender();
			 borderWidth = mBorder.getBorderWidth();
		}
		return borderWidth;
	}
	
	/**
	 * 设置边框宽度
	 * @param width 边框宽度
	 */
	public void setBorderWidth(int width)
	{
		 if(0 >= width) return;
		 if(null == mBorder)mBorder = new BorderRender();
		 mBorder.setRoundRadius(width);
	}
	
	/**
	 * 绘制边框
	 * @param canvas
	 */
	private void renderBorder(Canvas canvas)
	{
		if(mShowBorder)
		{
			if(null == mBorder) mBorder = new BorderRender();				
			mBorder.renderBorder("BORDER",canvas, 
								 mLeft  , mTop  , mRight , mBottom  ); 
		}
	}
	
	/**
	 * 绘制图的背景
	 */
	protected void renderChartBackground(Canvas canvas) {
		
		if(this.mBackgroundColorVisible)
		{		
			if(null == mBorder) mBorder = new BorderRender();				
			if(mShowBorder)
			{
				mBorder.renderBorder("CHART",canvas, 
									 mLeft  , mTop  , mRight , mBottom  ); 		
			}else{ //要清掉 border的默认间距				
				int borderSpadding = mBorder.getBorderSpadding();				 
				mBorder.renderBorder("CHART",canvas, 
						mLeft - borderSpadding , mTop - borderSpadding , 
						mRight + borderSpadding, mBottom + borderSpadding  ); 
			}
		}
	}
	
	

	/**
	 * 用于延迟绘制
	 * @param canvas	画布
	 * @return	是否成功
	 * @throws Exception 例外
	 */
	protected boolean postRender(Canvas canvas)  throws Exception
	{
		try{
			// 绘制图背景
			renderChartBackground(canvas);						
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	@Override
	public boolean render(Canvas canvas) throws Exception {
		// TODO Auto-generated method stubcalcPlotRange
		boolean ret = true;
		try {
				if (null == canvas)
						return false;
				
				canvas.save();
				//设置原点位置
				canvas.translate(mTranslateXY[0],mTranslateXY[1]);
				//绘制图表							
				ret = postRender(canvas);					
				//绘制边框
				renderBorder(canvas);
				//还原								
				canvas.restore();
		} catch (Exception e) {
			throw e;
		}
		return ret;
	}

	
	//math计算类函数 ----------------------------------------------------------------
	/**
	 * 加法运算
	 * @param v1 参数1
	 * @param v2 参数2
	 * @return
	 */
	 protected float add(float v1, float v2) 
	 {
		 return MathHelper.getInstance().add(v1, v2);
	 }
		 
	 /**
	  * 减法运算
	  * @param v1 参数1
	  * @param v2 参数2
	  * @return 运算结果
	  */
	 protected float sub(float v1, float v2) 
	 {
		 return MathHelper.getInstance().sub(v1, v2);
	 }
		 
	 /**
	  * 乘法运算
	  * @param v1 参数1
	  * @param v2 参数2
	  * @return 运算结果
	  */
	 protected float mul(float v1, float v2) 
	 {
		return MathHelper.getInstance().mul(v1, v2);
	 }
		 
	 /**
	  * 除法运算,当除不尽时，精确到小数点后10位
	  * @param v1 参数1
	  * @param v2 参数2
	  * @return 运算结果
	  */
	 protected float div(float v1, float v2)
	 {
		 return MathHelper.getInstance().div(v1, v2);
	 }
	 
	 /**
	  * double转float
	  * @param d double参数
	  * @return float
	  */
	 protected float dtof(double d)
	 {
		 return MathHelper.getInstance().dtof(d);
	 }
	 
	//math计算类函数 ----------------------------------------------------------------
	 
}
