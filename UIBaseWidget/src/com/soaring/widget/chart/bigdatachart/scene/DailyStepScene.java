package com.soaring.widget.chart.bigdatachart.scene;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.soaring.widget.R;
import com.soaring.widget.chart.bigdatachart.point.ChartDatePoint;
import com.soaringcloud.kit.box.LogKit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by renyuxiang on 2015/9/21.
 */
public class DailyStepScene extends DailyScene {
    //底部日历
    private Paint calendarPaint;
    private Paint calendarTextPaint;
    private int calenderDayTextSize = SpToPx(15);
    private int calendarTextColor = Color.WHITE;
    private int calendarBackgroundColor = Color.parseColor("#E64C66");
    private int calendarTextStrokeWidth = DpToPx(1);
    //主函数线
    private Paint dashedLinePaint;
    private int dashedLineColor = Color.rgb(125, 125, 125);
    private int dashedLineStrokeWidth = DpToPx(0.5f);
    //主函数节点
    private Paint dashedLineDotePaint;
    private int dashedLineDoteColor = Color.parseColor("#E64C66");
    private int dashedLineDoteBorderColor = Color.GRAY;
    private int dashedLineDoteRadius = DpToPx(8);
    //上一次Touch时的坐标
    private float oldX = 0.0f;
    //手机滑动相对距离缓存器，长度超过X轴一个格时，清零
    private float moveX = 0.0f;
    private float centerX = 0.0f;
    private List<ChartDatePoint> dataCache;
    private float minValue = 0.0f;
    private float maxValue = 15000.0f;
    //private float yAxisGraduationHeight = 150;
    private float xAxisGraduationWidth = DpToPx(80);
    private int moveDayCount = 0;
    private boolean isStart=true;

    public DailyStepScene(Context context) {
        super(context);
        this.initScene();
        this.initCanvas();
    }

    public void initCanvas() {
        calendarPaint = new Paint();
        calendarTextPaint = new Paint();
        dashedLinePaint = new Paint();
        dashedLineDotePaint = new Paint();
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
        //初始化主函数图画笔
        dashedLinePaint.setStyle(Paint.Style.FILL); // 设置填充
        dashedLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        dashedLinePaint.setColor(dashedLineColor);
        dashedLinePaint.setStrokeWidth(dashedLineStrokeWidth);
        PathEffect effects = new DashPathEffect(new float[]{DpToPx(2), DpToPx(2), DpToPx(2), DpToPx(2)}, 1);
        dashedLinePaint.setPathEffect(effects);
        //主函数节点画笔
        dashedLineDotePaint.setStyle(Paint.Style.FILL);
        dashedLineDotePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        dashedLineDotePaint.setColor(Color.rgb(125, 125, 125));
        Log.e("============","density==="+getContext().getResources().getDisplayMetrics().density);
    }

    @Override
    public void onDrawScene(Canvas canvas) {
        if (dataCache.isEmpty()) {
            if (isStart){
                dataCache.addAll(getDataLoader().getData(Calendar.getInstance(), DayType.RECENT_DAY, DAY_CACHE_COUNT * 3));
                resetChart(canvas);
                isStart=false;
            }
        }else{
            resetChart(canvas);
        }
        float topGap = DpToPx(5);
        float bottomGap = DpToPx(5);
        float calendarHeight = 3 * calenderDayTextSize;
        float calendarGap = 5;
        //Y轴底部起点      画布总高度        底部日历高度        节点圆形半径       底部间距
        float bottomY = canvas.getHeight() - calendarHeight - dashedLineDoteRadius - bottomGap;
        //Y轴可显示区域长度      画布总高度        底部日历高度    底部节点圆形半径     底部间距   顶部间距    顶部节点圆形半径
        float yAxisLength = canvas.getHeight() - calendarHeight - dashedLineDoteRadius - bottomGap - topGap - dashedLineDoteRadius;
        //绘制日历区域背景
        canvas.drawRect(0, canvas.getHeight() - calendarHeight, canvas.getWidth(), canvas.getHeight(), calendarPaint);
        //绘制日历内容
        centerX = canvas.getWidth() * 0.5f;
        centerX += moveX;
        if (Math.abs(moveX) > xAxisGraduationWidth ) {
            resetChart(canvas);
        }

        if (Math.abs(moveX) > xAxisGraduationWidth * DAY_CACHE_COUNT) {
            LogKit.e(this, "Loading data");
            if (getDataLoader() != null) {
                List<ChartDatePoint> list = getDataLoader().getData(dataCache.get(moveX > 0 ? 0 : dataCache.size() - 1).getCalendar(), moveX > 0 ? DayType.HISTORY_DAY : DayType.FUTURE_DAY, DAY_CACHE_COUNT);
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
            float startY = bottomY - ((maxValue - minValue) > 0 ? ((dataCache.get(i).getY() - minValue) / (maxValue - minValue)) : (maxValue>0?0.5f:0)) * yAxisLength;

            //每帧只需绘制一次
            if (i == 0) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.step_calendar),calendarGap,
                        canvas.getHeight() - 3 * calenderDayTextSize+5,calendarPaint);
                //绘制日历的年
                drawTextInCenter(canvas,
                        calendarGap+150,
                        canvas.getHeight() - 3 * calenderDayTextSize,
                        calendarTextPaint.measureText("" + dataCache.get(dataCache.size() / 2).getCalendar().get(Calendar.YEAR)),
                        canvas.getHeight() - calendarGap - calenderDayTextSize - 2 * calendarGap - calendarGap,
                        calendarTextPaint, "" + dataCache.get(dataCache.size() / 2).getCalendar().get(Calendar.YEAR));
                //绘制日历的月
                drawTextInCenter(canvas,
                        2 * +calendarGap + calendarTextPaint.measureText("" + dataCache.get(dataCache.size() / 2).getCalendar().get(Calendar.YEAR))+150,
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

            //绘制函数线
            if (i != dataCache.size() - 1) {
                float endX = startX + xAxisGraduationWidth;
                float endY = bottomY - ((maxValue - minValue) > 0 ? ((dataCache.get(i + 1).getY() - minValue) / (maxValue - minValue)) :(maxValue>0?0.5f:0)) * yAxisLength;
                canvas.drawLine(startX, startY, endX, endY, dashedLinePaint);
            }
            //绘制函数节点

            if (dataCache.get(i).isHasData()) {
                dashedLineDotePaint.setColor(dashedLineDoteColor);
                dashedLineDotePaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(startX, startY, dashedLineDoteRadius, dashedLineDotePaint);
                dashedLineDotePaint.setColor(dashedLineDoteBorderColor);
//                dashedLineDotePaint.setStyle(Paint.Style.STROKE);
//                dashedLineDotePaint.setStrokeWidth(2);
//                canvas.drawCircle(startX, startY, dashedLineDoteRadius, dashedLineDotePaint);
            } else {
                dashedLineDotePaint.setColor(Color.WHITE);
                dashedLineDotePaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(startX, startY, dashedLineDoteRadius, dashedLineDotePaint);
                dashedLineDotePaint.setColor(dashedLineDoteBorderColor);
                dashedLineDotePaint.setStyle(Paint.Style.STROKE);
                dashedLineDotePaint.setStrokeWidth(2);
                canvas.drawCircle(startX, startY, dashedLineDoteRadius, dashedLineDotePaint);
            }
        }
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
                oldX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //当进入节点长按模式时，只计算Y轴数值变化
                moveX += event.getX() - oldX;
                oldX = event.getX();
                break;
        }
    }

    @Override
    public void recycleScene() {

    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {

    }

    @Override
    public void notifyDataSetChanged() {
        if (!dataCache.isEmpty()&&Math.abs(moveX)==0){
            Calendar centerDay= (Calendar) (dataCache.get(dataCache.size()/2).getCalendar()).clone();
            dataCache.clear();
            Log.e("===============",+dataCache.size()+"dataCache.size(");
            dataCache.addAll(getDataLoader().getData(centerDay, DayType.RECENT_DAY, DAY_CACHE_COUNT * 3));
        }

    }

    public void drawTextInCenter(Canvas canvas, float left, float top, float right, float bottom, Paint mPaint, String content) {
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        float baseline = top + (bottom - top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(content, (left + right) * 0.5f, baseline, mPaint);
    }
    private int DpToPx(float value){
        if (getContext().getResources().getDisplayMetrics().density==3.0){
            return  (int)(value*2.4+0.5f);
        }
        return  (int)(value*getContext().getResources().getDisplayMetrics().density+0.5f);
    }
    private int SpToPx(int value){
        return (int)(value*getContext().getResources().getDisplayMetrics().scaledDensity+0.5f);
    }
}

