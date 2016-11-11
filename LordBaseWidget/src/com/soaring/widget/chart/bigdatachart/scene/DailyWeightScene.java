package com.soaring.widget.chart.bigdatachart.scene;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.soaring.widget.R;
import com.soaring.widget.chart.bigdatachart.point.ChartDatePoint;
import com.soaringcloud.kit.box.DateKit;
import com.soaringcloud.kit.box.DisplayKit;
import com.soaringcloud.kit.box.LogKit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by renyuxiang on 2015/9/9.
 */
public class DailyWeightScene extends DailyScene {
    public static final int HAS_DATA = 1;
    public static final int HAS_NO_DATA = 2;
    public static final int DATA_NULL = 3;
    //底部日历
    private Paint calendarPaint;
    private Paint calendarTextPaint;
    private int calenderDayTextSize = DisplayKit.sp2px(getContext(), 15);
    private int dashedLineDotePaintTextSize = DisplayKit.sp2px(getContext(), 10);
    private int calendarTextColor = Color.WHITE;
    private int calendarBackgroundColor = Color.rgb(227, 77, 102);
    private int calendarTextStrokeWidth = DisplayKit.dip2px(getContext(), 1);
    private float calendarGap = 5;
    private float calendarHeight = 3 * calenderDayTextSize;
    //辅助线
    private Paint assistLinePaint;
    /* 体重率 红色  体脂率 桔色*/
    private int assistLineColor = Color.rgb(227, 77, 102);  //红色
    private int assistLineStrokeWidth = DisplayKit.dip2px(getContext(), 1);
    //主函数线
    private Paint dashedLinePaint;
    private int dashedLineColor = Color.rgb(227, 77, 102);
    private int dashedLineStrokeWidth = DisplayKit.dip2px(getContext(), 2);
    //主函数节点
    private Paint dashedLineDotePaint;
    private int dashedLineDoteColor = Color.parseColor("#FAFAFA");
    private int dashedLineDoteBorderColor = Color.GRAY;
    private int dashedLineDoteRadius = DisplayKit.dip2px(getContext(), 8);
    //    主函数节点的值
    private Paint dashedLineDotePaintText;
    //折线图Y轴
    private Paint yAxisGraduationTextPaint;
    private int yAxisGraduationTextSize = DisplayKit.sp2px(getContext(), 15);
    private int yAxisGraduationTextColor = Color.rgb(170, 170, 170);
    private int yAxisGraduationStrokeWidth = DisplayKit.dip2px(getContext(), 5);
    private float yAxisTopGap = 1;
    private float yAxisBottomGap = 1;
    private float yAxisGraduationHeight = DisplayKit.dip2px(getContext(), 80);
    private float xAxisGraduationWidth = DisplayKit.dip2px(getContext(), 80);
    private int yAxisGraduationHalfCount = 10;
    private float yAxisLength;
    //上一次Touch时的坐标
    private float oldX = 0.0f;
    private float oldY = 0.0f;
    //手机滑动相对距离缓存器，长度超过X轴一个格时，清零
    private float moveX = 0.0f;
    private float moveY = 0.0f;
    private float centerX = 0.0f;
    private List<ChartDatePoint> dataCache;
    private boolean isLongPressing = false;
    private boolean isWaiteLongPress = false;
    private int longPressTimeCounter = 0;
    private float minValue = 58.0f;
    private float maxValue = 60.0f;
    private boolean isNeedResizeChart = false;
    private Vibrator vib;
    private OnChartDatePointChangedListener onChartDatePointChangedListener;
    private ChartDatePoint touchPoint;
    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID
    private Bitmap todayTip;
    private Bitmap calendarIcon;
    private boolean isAutoScroll = false;

    public DailyWeightScene(Context context, float preWeight) {
        super(context);
        minValue = Math.round(preWeight) - 1;
        maxValue = Math.round(preWeight) + 1;
        this.initCanvas();
    }

    public void initCanvas() {
        yAxisGraduationTextPaint = new Paint();
        calendarPaint = new Paint();
        calendarTextPaint = new Paint();
        assistLinePaint = new Paint();
        dashedLinePaint = new Paint();
        dashedLineDotePaint = new Paint();
        dashedLineDotePaintText = new Paint();
        //初始化垂直刻度画笔
        yAxisGraduationTextPaint.setStyle(Paint.Style.FILL); // 设置填充
        yAxisGraduationTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        yAxisGraduationTextPaint.setColor(yAxisGraduationTextColor);
        yAxisGraduationTextPaint.setTextSize(yAxisGraduationTextSize);
        yAxisGraduationTextPaint.setTextScaleX(0.8f);
        yAxisGraduationTextPaint.setFakeBoldText(true);
        yAxisGraduationTextPaint.setStrokeWidth(yAxisGraduationStrokeWidth);
        //初始化日历文字画笔
        calendarTextPaint.setStyle(Paint.Style.FILL); // 设置填充
        calendarTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        calendarTextPaint.setColor(calendarTextColor);
        calendarTextPaint.setTextSize(calenderDayTextSize);
        calendarTextPaint.setTextScaleX(0.8f);
        calendarTextPaint.setFakeBoldText(true);
        calendarTextPaint.setStrokeWidth(calendarTextStrokeWidth);
        //初始化日历画笔
        calendarPaint.setStyle(Paint.Style.FILL); // 设置填充
        calendarPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        calendarPaint.setColor(calendarBackgroundColor);
        //初始化辅助线画笔
        assistLinePaint.setStyle(Paint.Style.FILL); // 设置填充
        assistLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        assistLinePaint.setColor(assistLineColor);
        assistLinePaint.setStrokeWidth(assistLineStrokeWidth);
        int dashGap = DisplayKit.dip2px(getContext(), 2);
        PathEffect assisEffects = new DashPathEffect(new float[]{dashGap, dashGap, dashGap, dashGap}, 1);
        assistLinePaint.setPathEffect(assisEffects);
        //初始化主函数图画笔
        dashedLinePaint.setStyle(Paint.Style.FILL); // 设置填充
        dashedLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        dashedLinePaint.setColor(dashedLineColor);
        dashedLinePaint.setStrokeWidth(dashedLineStrokeWidth);
        PathEffect effects = new DashPathEffect(new float[]{dashGap, dashGap, dashGap, dashGap}, 1);
        dashedLinePaint.setPathEffect(effects);
        //主函数节点画笔
        dashedLineDotePaint.setStyle(Paint.Style.FILL);
        dashedLineDotePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        dashedLineDotePaint.setColor(Color.rgb(125, 125, 125));
        dashedLineDotePaintText.setStyle(Paint.Style.FILL);
        dashedLineDotePaintText.setFlags(Paint.ANTI_ALIAS_FLAG);
        dashedLineDotePaintText.setColor(Color.parseColor("#CCCCCC"));
        dashedLineDotePaintText.setTextSize(dashedLineDotePaintTextSize);
        dashedLineDotePaintText.setTextScaleX(0.8f);
        dashedLineDotePaintText.setFakeBoldText(true);
        dashedLineDotePaintText.setStrokeWidth(calendarTextStrokeWidth);
        vib = (Vibrator) getContext().getSystemService(Service.VIBRATOR_SERVICE);
        todayTip = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.weight_today);
        calendarIcon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.step_calendar);
    }

    @Override
    public void onDrawScene(Canvas canvas) {
        /*----------------基础数据计算----------------*/
        //Y轴底部除日历后的起点     画布总高度   底部日历高度
        float yAxisBottom = canvas.getHeight() - calendarHeight;
        //Y轴可显示区域长度      画布总高度        底部日历高度    底部节点圆形半径
        yAxisLength = yAxisBottom - dashedLineDoteRadius;
        //计算当前混存的数据中的平均值是多少
        int centerValue = (int) ((minValue + maxValue) * 0.5f);
        //Y轴有限个刻度值在画布分布上的中心点
        float centerY = yAxisBottom - yAxisLength * 0.5f;
        /*----------------基础数据计算----------------*/

        //检查是否有初始数据可供使用,若没有则载入初始数据
        if (dataCache.isEmpty()) {
            dataCache.addAll(getDataLoader().getData(Calendar.getInstance(), DayType.RECENT_DAY, DAY_CACHE_COUNT * 3));
            resetChart(canvas);
        }

        //绘制日历区域背景
        canvas.drawRect(0, yAxisBottom, canvas.getWidth(), canvas.getHeight(), calendarPaint);

        //绘制日历内容
        centerX = canvas.getWidth() * 0.5f;
        centerX += moveX;
//        if (isAutoScroll) {
//            moveX *= (1 + 0.2 * 0.8);
//        }
        if (Math.abs(moveX) > xAxisGraduationWidth * DAY_CACHE_COUNT) {
            LogKit.e(this, "Loading data");
            if (getDataLoader() != null) {
                List<ChartDatePoint> list = getDataLoader().getData(
                        dataCache.get(moveX > 0 ? 0 : dataCache.size() - 1).getCalendar(),
                        moveX > 0 ? DayType.HISTORY_DAY : DayType.FUTURE_DAY,
                        DAY_CACHE_COUNT);
                if (moveX > 0) {
                    dataCache.addAll(0, list);
                    for (int i = 0; i < list.size(); i++) {
                        dataCache.remove(dataCache.size() - 1);
                    }
                } else {
                    dataCache.addAll(list);
                    for (int i = 0; i < list.size(); i++) {
                        dataCache.remove(0);
                    }
                }
            }
            centerX = canvas.getWidth() * 0.5f;
            moveX = 0;
        }

        for (int i = 0; i < dataCache.size(); i++) {
            float startX = centerX + (i - dataCache.size() / 2) * xAxisGraduationWidth;
            float startY = centerY + (centerValue - dataCache.get(i).getY()) * yAxisGraduationHeight;
            if (isPointCanDrag(startX, startY)) {
                LogKit.e(this, "Touch moveY:" + moveY);
                LogKit.e(this, "Touch getY:" + dataCache.get(i).getY());
                if (Math.abs(moveY) > 0 && centerY + (centerValue - dataCache.get(i).getY()) * yAxisGraduationHeight < yAxisBottom) {
                    dataCache.get(i).setY(dataCache.get(i).getY() - moveY / yAxisGraduationHeight);
                    touchPoint = dataCache.get(i);
                    dataCache.get(i).setDataType(HAS_DATA);
                    startY = centerY + (centerValue - dataCache.get(i).getY()) * yAxisGraduationHeight;
                }
                moveY = 0;
                dashedLinePaint.setTextSize(35);
                drawTextInCenter(canvas,
                        startX - dashedLinePaint.measureText("" + dataCache.get(i).getY()) * 0.5f,
                        startY - 100,
                        startX + dashedLinePaint.measureText("" + dataCache.get(i).getY()) * 0.5f,
                        startY - 50,
                        dashedLinePaint, String.format("%.1f", dataCache.get(i).getY()));


            }
            //每帧只需绘制一次
            if (i == 0) {
                //绘制日历图标
                canvas.drawBitmap(calendarIcon, calendarGap,
                        canvas.getHeight() - 3 * calenderDayTextSize + 5, calendarPaint);
                //绘制日历的年
                drawTextInCenter(canvas,
                        calendarGap + 150,
                        canvas.getHeight() - 3 * calenderDayTextSize,
                        calendarTextPaint.measureText("" + dataCache.get(dataCache.size() / 2).getCalendar().get(Calendar.YEAR)),
                        canvas.getHeight() - calendarGap - calenderDayTextSize - 2 * calendarGap - calendarGap,
                        calendarTextPaint, "" + dataCache.get(dataCache.size() / 2).getCalendar().get(Calendar.YEAR));
                //绘制日历的月
                drawTextInCenter(canvas,
                        2 * +calendarGap + calendarTextPaint.measureText("" + dataCache.get(dataCache.size() / 2).getCalendar().get(Calendar.YEAR)) + 150,
                        canvas.getHeight() - 3 * calenderDayTextSize,
                        2 * +calendarGap + calendarTextPaint.measureText("" + dataCache.get(dataCache.size() / 2).getCalendar().get(Calendar.YEAR)) + calendarTextPaint.measureText("" + (dataCache.get(dataCache.size() / 2).getCalendar().get(Calendar.MONTH) + 1)),
                        canvas.getHeight() - calendarGap - calenderDayTextSize - 2 * calendarGap - calendarGap,
                        calendarTextPaint, "/" + (dataCache.get(dataCache.size() / 2).getCalendar().get(Calendar.MONTH) + 1));
                //绘制日历的分割线
                canvas.drawLine(0, canvas.getHeight() - calenderDayTextSize - 2 * calendarGap - calendarGap, canvas.getWidth(), canvas.getHeight() - calenderDayTextSize - 2 * calendarGap - calendarGap, calendarTextPaint);
            }
            //绘制日历的日
            drawTextInCenter(canvas,
                    startX - calendarTextPaint.measureText(dataCache.get(i).getXAxisPoint()) * 0.5f,
                    canvas.getHeight() - calenderDayTextSize - calendarGap,
                    startX + calendarTextPaint.measureText(dataCache.get(i).getXAxisPoint()) * 0.5f,
                    canvas.getHeight() - calendarGap,
                    calendarTextPaint, dataCache.get(i).getXAxisPoint());
            //绘制辅助线
            if (DateKit.dateConvertStringByPattern(dataCache.get(i).getCalendar().getTime(), DateKit.PATTERN3).
                    equals(DateKit.dateConvertStringByPattern(new Date(), DateKit.PATTERN3))) {
                assistLinePaint.setColor(Color.GRAY);
                canvas.drawLine(startX, 0, startX, yAxisBottom, assistLinePaint);
                canvas.drawBitmap(todayTip, startX - todayTip.getWidth() * 0.5f,
                        canvas.getHeight() - calendarHeight - todayTip.getHeight() - 30, calendarPaint);
                canvas.drawText("体重", startX, startY - 30, dashedLineDotePaintText);
            }
            if (dataCache.get(i).isHasData()) {
                if (!DateKit.dateConvertStringByPattern(dataCache.get(i).getCalendar().getTime(), DateKit.PATTERN3).
                        equals(DateKit.dateConvertStringByPattern(new Date(), DateKit.PATTERN3))) {
                    //绘制体重函数线
                    if (i != dataCache.size() - 1) {
                        dashedLinePaint.setColor(dashedLineColor);
                        float endX = startX + xAxisGraduationWidth;
                        float endY = centerY + (centerValue - dataCache.get(i + 1).getY()) * yAxisGraduationHeight;
                        canvas.drawLine(startX, startY, endX, endY, dashedLinePaint);
                    }
                }
            }

            //绘制函数节点
            dashedLineDotePaint.setColor(Color.WHITE);
            dashedLineDotePaint.setStyle(Paint.Style.FILL);

            if (dataCache.get(i).isHasData()) {
                //                设置节点画笔
                //                    dashedLineDotePaint.setColor(Color.GREEN);
                dashedLineDotePaint.setStyle(Paint.Style.FILL);
                //                体重的节点
                if (dataCache.get(i).getCalendar().get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) && dataCache.get(i).getCalendar().get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
                        && dataCache.get(i).getCalendar().get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                    dashedLineDotePaint.setColor(assistLineColor);
                }
                /*判断是否有数据*/
                if (dataCache.get(i).getDataType() == HAS_DATA) {
                    dashedLineDotePaint.setColor(assistLineColor);
                } else if (dataCache.get(i).getDataType() == HAS_NO_DATA) {
                    dashedLineDotePaint.setColor(Color.WHITE);
                }
                /*画里面的红色填充背景，有数据为红色，无数据为白色*/
                canvas.drawCircle(startX, startY, dashedLineDoteRadius - 5, dashedLineDotePaint);
                //                节点的灰色边框画笔
                dashedLineDotePaint.setColor(dashedLineDoteBorderColor);
                dashedLineDotePaint.setStyle(Paint.Style.STROKE);
                dashedLineDotePaint.setStrokeWidth(2);
                //                体重的红色边框
                dashedLineDotePaint.setColor(dashedLineColor);
                canvas.drawCircle(startX, startY, dashedLineDoteRadius-3, dashedLineDotePaint);
                //                体重的节点值
                canvas.drawText(String.format("%.1f", dataCache.get(i).getY()), startX + 25, startY + 25, dashedLineDotePaintText);

                if (dataCache.get(i).getCalendar().get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) && dataCache.get(i).getCalendar().get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
                        && dataCache.get(i).getCalendar().get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                    canvas.drawLine(startX-dashedLineDoteRadius+3,startY,startX+dashedLineDoteRadius-3,startY,dashedLineDotePaint);
                    canvas.drawLine(startX,startY-dashedLineDoteRadius+3,startX,startY+dashedLineDoteRadius-3,dashedLineDotePaint);
                }
            }
        }
        float centerValueYAxisTop = centerY - 35 * 0.5f;
        float centerValueYAxisBottom = centerY + 35 * 0.5f;
        //绘制左边纵坐标轴
        for (int i = 0; i < 2 * yAxisGraduationHalfCount; i++) {
            drawTextInCenter(canvas,
                    0,
                    centerValueYAxisTop + (i - yAxisGraduationHalfCount) * yAxisGraduationHeight,
                    yAxisGraduationTextPaint.measureText("" + (centerValue + yAxisGraduationHalfCount - i)),
                    centerValueYAxisBottom + (i - yAxisGraduationHalfCount) * yAxisGraduationHeight,
                    yAxisGraduationTextPaint, "" + (centerValue + yAxisGraduationHalfCount - i));
        }
        if (isNeedResizeChart && !isLongPressing) {
            isNeedResizeChart = false;
            resetChart(canvas);
        }
    }

    public void drawScene(Canvas canvas) {

    }

    public void resetChart(Canvas canvas) {
        minValue = dataCache.get(0).getY();
        maxValue = dataCache.get(0).getY();
        for (int j = 0; j < dataCache.size(); j++) {
            if (dataCache.get(j).getY() > maxValue) {
                maxValue = dataCache.get(j).getY();
            }
            if (dataCache.get(j).getY() < minValue) {
                minValue = dataCache.get(j).getY();
            }
        }
        if ((maxValue - minValue) * yAxisGraduationHeight != canvas.getHeight() * 0.5) {
            yAxisGraduationHeight = canvas.getHeight() * 0.5f / (maxValue - minValue);
            yAxisGraduationHalfCount = (int) (yAxisLength / yAxisGraduationHeight * 0.5f);
            if (yAxisGraduationHalfCount < yAxisLength / xAxisGraduationWidth * 0.5f) {
                yAxisGraduationHalfCount = (int) (yAxisLength * 0.5f / xAxisGraduationWidth);
            }
            if (yAxisGraduationHeight > xAxisGraduationWidth) {
                yAxisGraduationHeight = xAxisGraduationWidth;
            }
            LogKit.e(this, "resetChart:" + yAxisGraduationHeight + ",Count:" + yAxisGraduationHalfCount);
        }
    }

    public boolean isPointCanDrag(float x, float y) {
        if (isWaiteLongPress && Math.abs(x - oldX) < 50 && Math.abs(y - oldY) < 50 && longPressTimeCounter < 500) {
            longPressTimeCounter += 15;
            LogKit.e(this, "Long pressing...");
            if (longPressTimeCounter >= 350) {
                LogKit.e(this, "Long press ok!");
                isWaiteLongPress = false;
                isLongPressing = true;
                longPressTimeCounter = 0;
                vib.vibrate(100);
            }
        }
        return isLongPressing && Math.abs(x - oldX) < 50;
    }

    @Override
    public void initScene() {
        dataCache = new ArrayList<>();
    }

    @Override
    public void onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("Touch", "Down");
                Log.e("Touch", "yAxisGraduationHalfCount：" + yAxisGraduationHalfCount);
                Log.e("Touch", "CenterValue：" + (minValue + maxValue) * 0.5f);
                oldX = event.getX();
                oldY = event.getY();
                //用户每次点击屏幕进入监控长按状态，离开屏幕结束监控
                isWaiteLongPress = true;
                isAutoScroll = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //当进入节点长按模式时，只计算Y轴数值变化，否则只有X轴变化
                if (isLongPressing) {
                    Log.e("Touch", "Y Axis Move");
                    moveY += event.getY() - oldY;
                    oldY = event.getY();
                } else {
                    Log.e("Touch", "X Axis Move");
                    moveX += event.getX() - oldX;
                    oldX = event.getX();
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("Touch", "Up");
                // 当处于长按状态，Touch结束时清空位移量，防止画面抖动
                if (isLongPressing) {
                    moveY = 0;
                }
                isLongPressing = false;
                isWaiteLongPress = false;
                longPressTimeCounter = 0;
                isNeedResizeChart = true;
                if (touchPoint != null && getOnChartDatePointChangedListener() != null) {
                    ChartDatePoint tempPoint = new ChartDatePoint();
                    tempPoint.setCalendar(touchPoint.getCalendar());
                    tempPoint.setY(touchPoint.getY());
                    getOnChartDatePointChangedListener().onChartDatePointChanged(tempPoint);
                    touchPoint = null;
                }
                isAutoScroll = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                isAutoScroll = false;
                LogKit.e(this, "Touch Up");
                //当长按激活，Touch取消时清空位移量缓存，防止画面抖动
                if (isLongPressing) {
                    moveX = 0;
                    moveY = 0;
                }
                //重置长按状态
                isLongPressing = false;
                //重置等待进入长按状态
                isWaiteLongPress = false;
                //重置长按判断计时器
                longPressTimeCounter = 0;
                //请求刷新页面，根据局部点，重绘Y轴线段长度
                isNeedResizeChart = true;
                break;
        }
    }

    @Override
    public void recycleScene() {
        //回收整个Canvas用到的图片
        if (todayTip != null && !todayTip.isRecycled()) {
            todayTip.recycle();
        }
        if (calendarIcon != null && !calendarIcon.isRecycled()) {
            calendarIcon.recycle();
        }
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {

    }

    @Override
    public void notifyDataSetChanged() {

    }

    public OnChartDatePointChangedListener getOnChartDatePointChangedListener() {
        return onChartDatePointChangedListener;
    }

    public void setOnChartDatePointChangedListener(OnChartDatePointChangedListener onChartDatePointChangedListener) {
        this.onChartDatePointChangedListener = onChartDatePointChangedListener;
    }

    public interface OnChartDatePointChangedListener {
        void onChartDatePointChanged(ChartDatePoint point);
    }
}
