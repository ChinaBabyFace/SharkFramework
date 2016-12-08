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
import android.graphics.RectF;

import com.soaring.widget.chart.xclchart.event.click.ArcPosition;
import com.soaring.widget.chart.xclchart.event.click.BarPosition;
import com.soaring.widget.chart.xclchart.event.click.PlotArcPosition;
import com.soaring.widget.chart.xclchart.event.click.PlotBarPosition;
import com.soaring.widget.chart.xclchart.event.click.PlotPointPosition;
import com.soaring.widget.chart.xclchart.event.click.PointPosition;
import com.soaring.widget.chart.xclchart.event.click.PositionRecord;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @ClassName EventChart
 * @Description 处理类似Clicked的事件图基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class EventChart extends XChart {
	
	private boolean mListenClick = false;
	private int mClickRangeExtValue = 0;	
	private ArrayList mRecordset = null;	
	private int mSelectID = -1;
	private boolean mShowClikedFocus = false;
	
		
	public EventChart() {
		// TODO Auto-generated constructor stub		
		super();		
		initPositionRecord();
	}
	
	/**
	 * 激活点击事件
	 */
	public void ActiveListenItemClick()
	{
		mListenClick = true;	
	}

	/**
	 * 禁用点击事件
	 */
	public void DeactiveListenItemClick()
	{
		mListenClick = false;
	}
	
	/**
	 * 返回事件处理状态
	 * @return 是否激活
	 */
	protected boolean getListenItemClickStatus()
	{
		return mListenClick;
	}
	
	/**
	 * 返回记录集
	 * @return 记录集
	 */
	public ArrayList<PositionRecord> getPositionRecordset()
	{
		return  mRecordset;
	}
	
	/**
	 * 是点对点击选中对象显示相关焦点标识
	 */
	public void showClikedFocus()
	{
		mShowClikedFocus = true;
	}
	
	/**
	 * 设置标识形状
	 */
	public void setClikedFocusShape()
	{
		
	}
	
	private void clearSelected()
	{
		 mSelectID = -1;
	}
	
	private void saveSelected(int recordID)
	{
		mSelectID = recordID;
	}
	
	protected void savePointRecord(final int dataID,final int childID,final float x,final float y,final RectF r)
	{
		if(null == mRecordset)mRecordset =  new ArrayList<PlotPointPosition>();
	
		if(this.getListenItemClickStatus())
		{
			PlotPointPosition pRecord = new PlotPointPosition();
			pRecord.savePlotDataID(dataID);
			pRecord.savePlotDataChildID(childID);			
			pRecord.savePlotPosition(x, y);							
			pRecord.savePlotRectF(r.left,r.top,r.right,r.bottom);			
			pRecord.extPointClickRange(mClickRangeExtValue);			
			mRecordset.add(pRecord);				
		}
	}
	
	
	protected void saveBarRectFRecord(int dataID,int childID,
			   float left,float top,float right,float bottom)
	{
		if(null == mRecordset)mRecordset =  new ArrayList<PlotBarPosition>();
		
		if(this.getListenItemClickStatus())
		{
				PlotBarPosition pRecord = new PlotBarPosition();
				pRecord.savePlotDataID(dataID);
				pRecord.savePlotDataChildID(childID);
				pRecord.savePlotRectF(left, top, right, bottom);			
				pRecord.extPointClickRange(mClickRangeExtValue);
				mRecordset.add(pRecord);															
		}
	}
	
	
	
	protected void saveBarRecord(int dataID,int childID,float x,float y,RectF r)
	{
		if(null == mRecordset)mRecordset =  new ArrayList<PlotBarPosition>();
	
		if(this.getListenItemClickStatus())
		{
			PlotBarPosition pRecord = new PlotBarPosition();
			pRecord.savePlotDataID(dataID);
			pRecord.savePlotDataChildID(childID);						
			pRecord.savePlotRectF(r);			
			pRecord.extPointClickRange(mClickRangeExtValue);
			mRecordset.add(pRecord);
		}
	}
	
	//保存角度 (半径)
	protected void saveArcRecord(int dataID,float centerX,float centerY,
								 float radius,float offsetAngle,float Angle)
	{
		if(null == mRecordset)mRecordset =  new ArrayList<PlotArcPosition>();
	
		if(this.getListenItemClickStatus())
		{
			PlotArcPosition pRecord = new PlotArcPosition();
			pRecord.savePlotDataID(dataID);		
			pRecord.savePlotCirXY(centerX,centerY);
			pRecord.saveAngle(radius,offsetAngle,Angle);	
			mRecordset.add(pRecord);
		}
	}


	/**
	 * 为了让触发更灵敏，可以扩大指定px的点击监听范围
	 * @param value 扩大多少点击监听范围
	 */
	public void extPointClickRange(int value)
	{	
		mClickRangeExtValue = value;
	}			
	

	/**
	 * 检查是否点击在处理范围内
	 * @param x	当前点击点X坐标
	 * @param y 当前点击点Y坐标
	 * @return 是否需处理
	 */
	public boolean isPlotClickArea(float x,float y)
	{				
		if(!getListenItemClickStatus())return false;			
		
		if(Float.compare(x , getPlotArea().getLeft()) == -1 ) return false;
		if(Float.compare(x, getPlotArea().getRight() ) == 1 ) return false;	
		
		if(Float.compare( y , getPlotArea().getTop() ) == -1  ) return false;
		return Float.compare(y, getPlotArea().getBottom()) != 1;

	}
	
	/**
	 * 返回对应的记录
	 * @param x 当前点击点X坐标
	 * @param y 当前点击点Y坐标
	 * @return 记录类
	 */
	protected ArcPosition getArcRecord(float x,float y)
	{			
		if(!isPlotClickArea(x,y))return null;		
		if(null == mRecordset) return null;
			
		Iterator it = mRecordset.iterator();
		while(it.hasNext())
		{		
			PlotArcPosition record = (PlotArcPosition)it.next();
			if(record.compareF(x, y))
			{
				saveSelected(record.getRecordID());
				return record;
			}
		}		
		clearSelected();
		return null;
	}
	
	protected BarPosition getBarRecord(float x,float y)
	{		
		if(!isPlotClickArea(x,y))return null;		
		if(null == mRecordset) return null;
			
		Iterator it = mRecordset.iterator();
		while(it.hasNext())
		{		
			PlotBarPosition record = (PlotBarPosition)it.next();
			if(record.compareF(x, y))
			{
				saveSelected(record.getRecordID());
				return record;			
			}
		}	
		clearSelected();
		return null;
	}
	
	protected PointPosition getPointRecord(final float x,final float y)
	{			
		if(!isPlotClickArea(x,y))return null;		
		if(null == mRecordset) return null;
						
		Iterator it = mRecordset.iterator();
		while(it.hasNext())
		{		
			PlotPointPosition record = (PlotPointPosition)it.next();
			if(record.compareF(x, y))
			{
				saveSelected(record.getRecordID());
				return record;			
			}
		}			
		clearSelected();
		return null;
	}
	
	private void initPositionRecord()
	{
		if(null != mRecordset) mRecordset.clear();
	}
	
	private boolean renderFocusShape(Canvas canvas)
	{				
		if(!mShowClikedFocus) return true;
		
		//是否显示焦点(lnchart显示rect框，bar显示rect,pie 则分裂跳出)
		//...
		return true;
	}
		
	@Override
	protected boolean postRender(Canvas canvas) throws Exception 
	{
		// 绘制图表
		try {
			super.postRender(canvas);
			initPositionRecord();
			//绘制选中点
			return renderFocusShape(canvas);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
}
