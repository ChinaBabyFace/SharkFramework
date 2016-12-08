package com.soaring.widget.segmentindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.soaring.widget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 本控件注释中提到的指针不是软件工程中的指针（pointer）而是实物仪表上的指针（indicator），特此声明
 * @author renyuxiang
 * 
 */
public class SegmentIndicatorBar
		extends View {

	/** 线段的描述字体尺寸 */
	private float segmentLabelTextSize = 35;
	/** 线段高度 */
	private float segmentHeight = 40;
	/** 线段对象集合 */
	private List<SegmentView> segmentList;
	/** 刻度字体尺寸 */
	private float graduationTextSize = 40;
	/** 刻度字体颜色 */
	private int graduationColor = Color.BLACK;
	/** 指针数值字体尺寸 */
	private float valueTextSize = 35;
	/** 指针数值字体颜色 */
	private int valueTextColor = Color.BLACK;
	/** 指针值 */
	private float value = 6.0f;
	/** 指针尺寸 */
	private float indicatorTriangleSideLength = 35;
	/** 指针颜色 */
	private int indicatorColor = Color.YELLOW;
	/** 是否显示刻度 */
	private boolean isShowGraduation = true;
	/** 是否显示两端的刻度 */
	private boolean isShowTerminalGaduation = true;
	/** 是否显示指针 */
	private boolean isShowIndicator = true;
    /** 是否显示指针的值*/
	private boolean isShowIndicatorValue = true;
    /** 是否显示指针动画 */
	private boolean isIndicatorAimation = true;
	private boolean isStart = false;
	private boolean isMoving = false;
	private IndicatorMoveRunnable indicatorMoveRunnable;
	private float indicatorMoveSpeed = 5.0f;
	/**线段分割线的颜色*/
	private int dividerColor = Color.WHITE;
	/**线段分割线的宽度*/
	private float dividerSpaceWidth = 10;
	/** =================以下为内部变量不对外提供接口===================== */
	/** 主画笔 */
	private Paint mPaint;
	/** 用于绘制指针形状 */
	private Path indicatorPath;
	/** 每一个线段的长度 */
	private float barSegmentWidth = 0.0f;
	/** 指针偏移总量 */
	private float indicatorTranslateDistance = 0.0f;
	/** 指针偏移累加量 */
	private float indicatorTranslateDelta = 0.0f;
	/** 指针数字的显示区域 */
	private float valueWidth = 0.0f;
	private float valueTop = 0.0f;
	private float valueLeft = 0.0f;
	private float valueRight = 0.0f;
	private float valueBottom = 0.0f;
	/** 图形指针的高度，等边三角形的高 */
	private float cursorHeight = 0.0f;
	/** 图形指针的显示区域 */
	private float cursorTop = 0.0f;
	private float cursorLeft = 0.0f;
	private float cursorBottom = 0.0f;
	/** 总体进度条的显示区域 */
	private float barTop = 0.0f;
	private float barLeft = 0.0f;
	private float barBottom = 0.0f;

	public SegmentIndicatorBar(Context context) {
		super(context);


	}

	/**
	 * TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组,
	 * 在使用完成后，一定要调用recycle方法, 属性的名称是styleable中的名称+“_”+属性名称
	 * 
	 * @param context
	 * @param attrs
	 */
	public SegmentIndicatorBar(Context context, AttributeSet attrs) {
		super(context, attrs);
//        segmentHeight= DisplayKit.getScaleValue((Activity)context,30);
		mPaint = new Paint();
		indicatorPath = new Path();
		segmentList = getBmiSegmentViewList();
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SegmentIndicatorBar);
		for (int i = 0; i < array.getIndexCount(); i++) {
			int attr = array.getIndex(i);
			if (attr == R.styleable.SegmentIndicatorBar_segmentHeight) {
				segmentHeight = array.getDimension(attr, segmentHeight);
			} else if (attr == R.styleable.SegmentIndicatorBar_segmentLabelTextSize) {
				segmentLabelTextSize = array.getDimensionPixelSize(attr, getTextSize((int) segmentLabelTextSize));
			} else if (attr == R.styleable.SegmentIndicatorBar_graduationTextSize) {
				graduationTextSize = array.getDimensionPixelSize(attr, getTextSize((int) graduationTextSize));
			} else if (attr == R.styleable.SegmentIndicatorBar_graduationColor) {
				graduationColor = array.getColor(attr, graduationColor);
			} else if (attr == R.styleable.SegmentIndicatorBar_valueTextSize) {
				valueTextSize = array.getDimensionPixelSize(attr, getTextSize((int) valueTextSize));
			} else if (attr == R.styleable.SegmentIndicatorBar_valueTextColor) {
				valueTextColor = array.getColor(attr, valueTextColor);
			} else if (attr == R.styleable.SegmentIndicatorBar_value) {
				value = array.getFloat(attr, 0);
			} else if (attr == R.styleable.SegmentIndicatorBar_indicatorTriangleSideLength) {
				indicatorTriangleSideLength = array.getDimension(attr, indicatorTriangleSideLength);
			} else if (attr == R.styleable.SegmentIndicatorBar_indicatorColor) {
				indicatorColor = array.getColor(attr, indicatorColor);
			} else if (attr == R.styleable.SegmentIndicatorBar_isShowGraduation) {
				isShowGraduation = array.getBoolean(attr, isShowGraduation);
			} else if (attr == R.styleable.SegmentIndicatorBar_isShowIndicator) {
				isShowIndicator = array.getBoolean(attr, isShowIndicator);
			} else if (attr == R.styleable.SegmentIndicatorBar_isIndicatorAimation) {
				isIndicatorAimation = array.getBoolean(attr, isIndicatorAimation);
			} else if (attr == R.styleable.SegmentIndicatorBar_isShowTerminalGaduation) {
				isShowTerminalGaduation = array.getBoolean(attr, isShowTerminalGaduation);
			} else if (attr == R.styleable.SegmentIndicatorBar_dividerColor) {
				dividerColor = array.getColor(attr, dividerColor);
			} else if (attr == R.styleable.SegmentIndicatorBar_dividerSpaceWidth) {
				dividerSpaceWidth = array.getDimension(attr, dividerSpaceWidth);
			}
		}
		// 一定要调用，否则这次的设定会对下次的使用造成影响
		array.recycle();
		isStart = true;
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 确定指示值显示的区域
		valueWidth = mPaint.measureText("0") + mPaint.measureText("" + value) + mPaint.measureText("0");
		valueTop = getPaddingTop();
		valueLeft = 0;
		valueBottom = valueTop + valueTextSize;

		// 确定指针图形对应的等边三角形的大小及区域
		cursorHeight = (float) Math.sqrt(Math.pow(indicatorTriangleSideLength, 2) - Math.pow(indicatorTriangleSideLength * 0.5f, 2));
		cursorTop = valueBottom;
		cursorLeft = 0;
		cursorBottom = cursorTop + cursorHeight;

		// 比较数值长度和三角形指针边长谁更长
		if (valueWidth > indicatorTriangleSideLength) {
			valueLeft = getPaddingLeft();
			cursorLeft = getPaddingLeft() + (valueWidth - indicatorTriangleSideLength) * 0.5f;
		} else {
			cursorLeft = getPaddingLeft();
			valueLeft = getPaddingLeft() + (indicatorTriangleSideLength - valueWidth) * 0.5f;
		}

		// 确定每条线段的长度
		barSegmentWidth = (canvas.getWidth() - getPaddingLeft() - getPaddingRight() - Math.max(valueWidth, indicatorTriangleSideLength))
				/ segmentList.size();

		// 确定Bar整体的显示位置及区域
		barTop = cursorBottom;
		barLeft = valueWidth > indicatorTriangleSideLength ? (valueLeft + valueWidth * 0.5f) : (cursorLeft + indicatorTriangleSideLength * 0.5f);
		barBottom = barTop + segmentHeight;

		// 准备画笔
		mPaint.setStyle(Style.FILL); // 设置填充
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		// 开始根据输入值计算指针相对于起点的偏移量indicatorTranslateDelta
		if (segmentList.size() > 0) {
			indicatorTranslateDistance = 0;
			for (int i = 0; i < segmentList.size(); i++) {
				if (value - segmentList.get(i).getEndX() >= 0) {
					indicatorTranslateDistance += barSegmentWidth;
				} else {
					indicatorTranslateDistance += (value - segmentList.get(i).getStartX())
							/ (segmentList.get(i).getEndX() - segmentList.get(i).getStartX()) * barSegmentWidth;
					break;
				}
			}
		}
		if (!isIndicatorAimation) {
			indicatorTranslateDelta = indicatorTranslateDistance;
		}
		// 绘制指针上的值
        if (isShowIndicatorValue){
            mPaint.setTextSize(valueTextSize);
            mPaint.setColor(valueTextColor);
            valueLeft += indicatorTranslateDelta;
            valueRight = valueLeft + valueWidth;
            drawTextInCenter(canvas, valueLeft, valueTop, valueRight, valueBottom, mPaint, "" + value);
        }

		// 绘制指针图形
		if (isShowIndicator) {
			cursorLeft += indicatorTranslateDelta;
			mPaint.setColor(indicatorColor);
			indicatorPath.moveTo(cursorLeft, cursorTop);// 此点为多边形的起点
			indicatorPath.lineTo(cursorLeft + indicatorTriangleSideLength, cursorTop);
			indicatorPath.lineTo(barLeft + indicatorTranslateDelta, cursorBottom);
			indicatorPath.close(); // 使这些点构成封闭的多边形
			canvas.drawPath(indicatorPath, mPaint);
			indicatorPath.reset();

		}
		// Log.e("DIY", "V:" + valueLeft + "C" + cursorLeft);

		// 绘制线段及刻度
		for (int i = 0; i < segmentList.size(); i++) {
			// 绘制线段
			mPaint.setColor(segmentList.get(i).getBackgroundColor());
			canvas.drawRect(barLeft + i * barSegmentWidth, barTop, (barLeft + (i + 1) * barSegmentWidth)
					- (i == segmentList.size() - 1 ? 0 : dividerSpaceWidth * 0.5f), barBottom, mPaint);
			// 绘制线段标签
			mPaint.setColor(segmentList.get(i).getLabelColor());
			mPaint.setTextSize(segmentLabelTextSize);
			drawTextInCenter(canvas, barLeft + i * barSegmentWidth, barTop, barLeft + (i + 1) * barSegmentWidth, barBottom, mPaint, segmentList
					.get(i).getName());
			// 绘制刻度
			if (isShowGraduation) {
				mPaint.setColor(graduationColor);
				mPaint.setTextSize(graduationTextSize);
				// 如果要显示首端点则画出来
				if (i == 0 && isShowTerminalGaduation) {
					drawTextInCenter(canvas, barLeft - mPaint.measureText("" + segmentList.get(i).getEndX()) * 0.5f, barBottom,
							barLeft + mPaint.measureText("" + segmentList.get(i).getEndX()) * 0.5f, barBottom + segmentHeight, mPaint, ""
									+ segmentList.get(i).getEndX());
				}

				float graduationLeft = barLeft + (i + 1) * barSegmentWidth - mPaint.measureText("" + segmentList.get(i).getEndX()) * 0.5f;
				float graduationRight = barLeft + (i + 1) * barSegmentWidth + mPaint.measureText("" + segmentList.get(i).getEndX()) * 0.5f;
				// 如果不需要显示末端点则不画出来
				if (i == segmentList.size() - 1 && !isShowTerminalGaduation) {
					break;
				}
				drawTextInCenter(canvas, graduationLeft, barBottom, graduationRight, barBottom + segmentHeight, mPaint, ""
						+ segmentList.get(i).getEndX());
				
				// 绘制线段分割线
				mPaint.setColor(dividerColor);
				canvas.drawRect((barLeft + (i + 1) * barSegmentWidth) - dividerSpaceWidth * 0.5f, barTop, (barLeft + (i + 1) * barSegmentWidth)+(i == segmentList.size() - 1?0:dividerSpaceWidth), barBottom, mPaint);
			}
		}
		if (isIndicatorAimation && !isMoving && isStart) {
			isMoving = true;
			post(indicatorMoveRunnable = new IndicatorMoveRunnable(indicatorMoveSpeed));
		}
		Log.v("DIY", "DS:" + indicatorTranslateDistance + ",DT:" + indicatorTranslateDelta + ",M" + isMoving + ",S:" + isStart);
	}

	public void drawTextInCenter(Canvas canvas, RectF targetRect, Paint mPaint, String content) {
		FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
		float baseline = targetRect.top + (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) * 0.5f - fontMetrics.top;
		mPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(content, targetRect.centerX(), baseline, mPaint);
	}

	public void drawTextInCenter(Canvas canvas, float left, float top, float right, float bottom, Paint mPaint, String content) {
		FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
		float baseline = top + (bottom - top - fontMetrics.bottom + fontMetrics.top) * 0.5f - fontMetrics.top;
		mPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(content, (left + right) * 0.5f, baseline, mPaint);
	}

	private class IndicatorMoveRunnable
			implements Runnable {

		private float moveSpeed;
		private boolean isStop = false;

		public IndicatorMoveRunnable(float velocity) {
			this.moveSpeed = velocity;
		}

		public void run() {
			if (!isStop) {
				if (isMoving) {
					if (indicatorTranslateDelta < indicatorTranslateDistance) {
						// moveSpeed = moveSpeed * (1 + 0.1f);
						// c*((t=t/d-1)*t*t + 1) + b
						indicatorTranslateDelta += moveSpeed;
					} else if (indicatorTranslateDelta == indicatorTranslateDistance) {
						isMoving = false;
						isStart = false;
					} else {
						indicatorTranslateDelta = indicatorTranslateDistance;
					}
					postDelayed(this, 30);
					postInvalidate();
				}
			}
		}

		/**
		 * @return the isStop
		 */
		public boolean isStop() {
			return isStop;
		}

		/**
		 * @param isStop the isStop to set
		 */
		public void setStop(boolean isStop) {
			this.isStop = isStop;
		}
	}

	public List<SegmentView> getBmiSegmentViewList() {
		List<SegmentView> list = new ArrayList<SegmentView>();
		SegmentView segment1 = new SegmentView();
		segment1.setName("偏瘦");
		segment1.setStartX(0);
		segment1.setStartY(0);
		segment1.setEndX(18.5f);
		segment1.setEndY(0);
		segment1.setLabelColor(Color.WHITE);
		segment1.setBackgroundColor(Color.BLUE);
		SegmentView segment2 = new SegmentView();
		segment2.setName("标准");
		segment2.setStartX(18.5f);
		segment2.setStartY(0);
		segment2.setEndX(24.0f);
		segment2.setEndY(0);
		segment2.setLabelColor(Color.WHITE);
		segment2.setBackgroundColor(Color.RED);
		SegmentView segment3 = new SegmentView();
		segment3.setName("偏胖");
		segment3.setStartX(24.0f);
		segment3.setStartY(0);
		segment3.setEndX(28.0f);
		segment3.setEndY(0);
		segment3.setLabelColor(Color.WHITE);
		segment3.setBackgroundColor(Color.GREEN);
		SegmentView segment4 = new SegmentView();
		segment4.setName("肥胖");
		segment4.setStartX(28.0f);
		segment4.setStartY(0);
		segment4.setEndX(100.0f);
		segment4.setEndY(0);
		segment4.setLabelColor(Color.WHITE);
		segment4.setBackgroundColor(Color.MAGENTA);
		list.add(segment1);
		list.add(segment2);
		list.add(segment3);
		list.add(segment4);
		return list;
	}

	public int getTextSize(int size) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, getResources().getDisplayMetrics());
	}

	public float getSegmentLabelTextSize() {
		return segmentLabelTextSize;
	}

	public void setSegmentLabelTextSize(float segmentLabelTextSize) {
		this.segmentLabelTextSize = segmentLabelTextSize;
		invalidate();
	}

	public float getSegmentHeight() {
		return segmentHeight;
	}

	public void setSegmentHeight(float segmentHeight) {
		this.segmentHeight = segmentHeight;
		invalidate();
	}

	public List<SegmentView> getSegmentList() {
		return segmentList;
	}

	public void setSegmentList(List<SegmentView> segmentList) {
		this.segmentList = segmentList;
		invalidate();
	}

	public float getGraduationTextSize() {
		return graduationTextSize;
	}

	public void setGraduationTextSize(float graduationTextSize) {
		this.graduationTextSize = graduationTextSize;
		invalidate();
	}

	public int getGraduationColor() {
		return graduationColor;
	}

	public void setGraduationColor(int graduationColor) {
		this.graduationColor = graduationColor;
		invalidate();
	}

	public float getValueTextSize() {
		return valueTextSize;
	}

	public void setValueTextSize(float valueTextSize) {
		this.valueTextSize = valueTextSize;
		invalidate();
	}

	public int getValueTextColor() {
		return valueTextColor;
	}

	public void setValueTextColor(int valueTextColor) {
		this.valueTextColor = valueTextColor;
		invalidate();
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
		isMoving = false;
		isStart = true;
		indicatorTranslateDelta = 0;
		if (indicatorMoveRunnable != null) {
			indicatorMoveRunnable.setStop(true);
		}
		invalidate();
	}

	public float getIndicatorTriangleSideLength() {
		return indicatorTriangleSideLength;
	}

	public void setIndicatorTriangleSideLength(float indicatorTriangleSideLength) {
		this.indicatorTriangleSideLength = indicatorTriangleSideLength;
		invalidate();
	}

	public int getIndicatorColor() {
		return indicatorColor;
	}

	public void setIndicatorColor(int indicatorColor) {
		this.indicatorColor = indicatorColor;
		invalidate();
	}

	public boolean isShowGraduation() {
		return isShowGraduation;
	}

	public void setShowGraduation(boolean isShowGraduation) {
		this.isShowGraduation = isShowGraduation;
		invalidate();
	}

	public boolean isShowIndicator() {
		return isShowIndicator;
	}

	public void setShowIndicator(boolean isShowIndicator) {
		this.isShowIndicator = isShowIndicator;
		invalidate();
	}

	public boolean isIndicatorAimation() {
		return isIndicatorAimation;
	}

	public void setIndicatorAimation(boolean isIndicatorAimation) {
		this.isIndicatorAimation = isIndicatorAimation;
		invalidate();
	}

	public boolean isShowTerminalGaduation() {
		return isShowTerminalGaduation;
	}

	public void setShowTerminalGaduation(boolean isShowTerminalGaduation) {
		this.isShowTerminalGaduation = isShowTerminalGaduation;
	}

	public int getDividerColor() {
		return dividerColor;
	}

	public void setDividerColor(int dividerColor) {
		this.dividerColor = dividerColor;
	}

	public float getDividerSpaceWidth() {
		return dividerSpaceWidth;
	}

	public void setDividerSpaceWidth(float dividerSpaceWidth) {
		this.dividerSpaceWidth = dividerSpaceWidth;
	}

    public boolean isShowIndicatorValue() {
        return isShowIndicatorValue;
    }

    public void setIsShowIndicatorValue(boolean isShowIndicatorValue) {
        this.isShowIndicatorValue = isShowIndicatorValue;
    }
}
