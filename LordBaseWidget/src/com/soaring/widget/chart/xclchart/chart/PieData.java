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
package com.soaring.widget.chart.xclchart.chart;

import android.util.Log;

import com.soaring.widget.chart.xclchart.common.MathHelper;

/**
 * @ClassName PieData
 * @Description 数据类, 饼图,rose图,环形图等都用这个传数据
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class PieData {
	
	private final String TAG ="PieData";

	private String mPieKey = "";
	private String mPieLabel = "";
	private double mPieValue = 0.0f;
	private int mPieColor = 0 ;
	//private int mSliceAngle = 0;
	
	//是否突出饼图
	private boolean mSelected = false;
	
	public PieData() {
		// TODO Auto-generated constructor stub
		//super();
	}
		
	/**
	 * 
	 * @param label		标签
	 * @param percent	百分比
	 * @param color		显示颜色
	 */
	public PieData(String label,double percent,int color)
	{
		setLabel(label);
		setPercentage(percent);
		setSliceColor(color);
	}
	
	/**
	 * 
	 * @param label		标签
	 * @param percent	百分比
	 * @param color		显示颜色
	 * @param selected	是否突出显示
	 */
	public PieData(String label,double percent,int color,boolean selected)
	{
		setLabel(label);
		setPercentage(percent);
		setSliceColor(color);
		setSelected(selected);
	}
	
	/**
	 * 
	 * @param key		键值
	 * @param label		标签
	 * @param percent	百分比
	 * @param color		显示颜色
	 */
	public PieData(String key,String label,double percent,int color)
	{
		setLabel(label);
		setPercentage(percent);
		setSliceColor(color);
		setKey(key);
	}
	
	/**
	 * 
	 * @param key		键值
	 * @param label		标签
	 * @param percent	百分比
	 * @param color		显示颜色
	 * @param selected	是否突出显示
	 */
	public PieData(String key,String label,double percent,int color,boolean selected)
	{
		setLabel(label);
		setPercentage(percent);
		setSliceColor(color);
		setKey(key);
		setSelected(selected);
	}
	
	/**
	 * 设置Key值
	 * @param key Key值
	 */
	public void setKey(String key)
	{
		mPieKey = key;
	}	
	
	/**
	 * 返回Key值
	 * @return Key值
	 */
	public String getKey()
	{
		return mPieKey;
	}
	
	/**
	 * 设置标签
	 * @param label 标签
	 */
	public void setLabel(String label)
	{
		mPieLabel = label;
	}	
		
	/**
	 * 设置百分比,绘制时，会将其转换为对应的圆心角
	 * @param value 百分比
	 */
	public void setPercentage(double value)
	{
		mPieValue = value;
	}
	
	/**
	 * 设置扇区颜色
	 * @param color 颜色
	 */
	public void setSliceColor(int color)
	{
		mPieColor = color;
	}
	/**
	 * 设置当前块是否突出，true为突出
	 * @param selected 是否突出
	 */
	public void setSelected(boolean selected)
	{
		mSelected = selected;
	}

	/**
	 * 返回标签
	 * @return 标签
	 */
	public String getLabel()
	{
		return mPieLabel;
	}
	
	/**
	 * 返回当前百分比
	 * @return 百分比
	 */
	public double getPercentage()
	{
		return mPieValue;
	}

	/**
	 * 是否突出显示
	 * @return 是否突出
	 */
	public boolean getSelected()
	{
		return mSelected;
	}
	
	/**
	 * 返回扇区颜色
	 * @return 颜色
	 */
	public int getSliceColor()
	{
		return mPieColor;
	}
	
	/**
	 * 将百分比转换为饼图显示角度
	 * @return 圆心角度
	 */
	public float getSliceAngle() 
	{			
		float Angle = 0.0f;
		try{
			float currentValue = (float) this.getPercentage();
			if(currentValue >= 101f || currentValue < 0.0f)
			{
				Log.e(TAG,"输入的百分比不合规范.须在0~100之间.");			
			}else{						
				Angle =  MathHelper.getInstance().round(
								MathHelper.getInstance().mul( 360f,
									MathHelper.getInstance().div(currentValue,100f) ) ,2);												
				//Angle =  MathHelper.getInstance().round(360f *  (currentValue / 100f),2) ; //rint
			}
		}catch(Exception ex)
		{
			Angle = -1f;
		}finally{
			
		}
		return  Angle;
	}
}
