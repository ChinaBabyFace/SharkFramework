/**
 * Copyright 2014  XCL-Charts
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
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
package com.soaring.widget.chart.xclchart.renderer.plot;

import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.Log;

import com.soaring.widget.chart.xclchart.chart.ArcLineData;
import com.soaring.widget.chart.xclchart.chart.BarData;
import com.soaring.widget.chart.xclchart.chart.BubbleData;
import com.soaring.widget.chart.xclchart.chart.LnData;
import com.soaring.widget.chart.xclchart.chart.PieData;
import com.soaring.widget.chart.xclchart.chart.RadarData;
import com.soaring.widget.chart.xclchart.chart.ScatterData;
import com.soaring.widget.chart.xclchart.common.DrawHelper;
import com.soaring.widget.chart.xclchart.common.MathHelper;
import com.soaring.widget.chart.xclchart.renderer.XChart;
import com.soaring.widget.chart.xclchart.renderer.XEnum;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDot;
import com.soaring.widget.chart.xclchart.renderer.line.PlotDotRender;
import com.soaring.widget.chart.xclchart.renderer.line.PlotLine;

import java.util.List;

/**
 * @ClassName PlotLegendRender
 * @Description 用于绘制图表的图例 (这块代码还需要整合优化及丰富特性)
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *
 */
public class PlotLegendRender extends PlotLegend {

    private static final String TAG = "PlotLegendRender";

    private PlotArea mPlotArea = null;
    private XChart mXChart = null;

    private float mKeyLabelX = 0.0f;
    private float mKeyLabelY = 0.0f;

    private float mTextHeight = 0.0f;
    private float mRectWidth = 0.0f;
    private float mTextWidth = 0.0f;
    private float mTotalTextWidth = 0.0f;

    public PlotLegendRender() {
        super();
    }


    public PlotLegendRender(XChart xChart) {
        super();
        mXChart = xChart;
    }

    public void setXChart(XChart xChart) {
        mXChart = xChart;
    }


    private boolean validateParams() {
        if (null == mXChart) {
            Log.e(TAG, "图基类没有传过来。");
            return false;
        }
        return true;
    }

    private void initEnv() {
        mKeyLabelX = mKeyLabelY = 0.0f;
        mTextWidth = mTextHeight = mRectWidth = mTotalTextWidth = 0.0f;
    }


    /**
     * 绘制柱形图的图例
     * @param canvas    画布
     * @param xChart    基类
     * @param dataSet    数据集
     */
    public void renderBarKey(Canvas canvas,
                             XChart xChart,
                             List<BarData> dataSet) {
        setXChart(xChart);
        renderBarKey(canvas, dataSet);
    }

    /**
     * 绘制柱形图的图例
     * @param canvas    画布
     * @param dataSet    数据集
     * @return 是否成功
     */
    public boolean renderBarKey(Canvas canvas, List<BarData> dataSet) {
        if (!isShowLegend())
            return true;

        if (null == dataSet)
            return false;
        if (!validateParams())
            return false;
        if (null == mPlotArea)
            mPlotArea = mXChart.getPlotArea();


        // 图表标题显示位置
        switch (mXChart.getPlotTitle().getTitleAlign()) {
            case MIDDLE:
            case RIGHT:
                renderBarKeyLeft(canvas, dataSet);
                break;
            case LEFT:
                renderBarKeyRight(canvas, dataSet);
                break;
        }

        return true;
    }


    /**
     * 在左边绘制图例. <br/>单行可以显示多个图例y说明，当一行显示不下时，会自动转到新行
     * @param canvas 画布
     */
    private void renderBarKeyLeft(Canvas canvas, List<BarData> dataSet) {

        initEnv();
        mTextHeight = this.getLabelTextHeight();
        mKeyLabelX = mPlotArea.getLeft() + mOffsetX;
        mKeyLabelY = mPlotArea.getTop() - mTextHeight - mOffsetY;

        mRectWidth = this.getRectWidth();
        float rectOffset = getLegendLabelMargin();
        float rectHeight = mTextHeight;

        String key = "";
        getLegendLabelPaint().setTextAlign(Align.LEFT);
        for (BarData cData : dataSet) {
            key = cData.getKey();
            if ("" == key)
                continue;

            getLegendLabelPaint().setColor(cData.getColor());
            float strWidth = this.getLabelTextWidth(key);

            if (mKeyLabelX + 2 * mRectWidth + strWidth > mXChart.getRight()) {
                mKeyLabelX = mPlotArea.getLeft();
                mKeyLabelY = mKeyLabelY + rectHeight * 2;
            }

            canvas.drawRect(mKeyLabelX, mKeyLabelY, mKeyLabelX + mRectWidth,
                    mKeyLabelY - rectHeight, getLegendLabelPaint());

            getLegendLabelPaint().setTextAlign(Align.LEFT);
            DrawHelper.getInstance().drawRotateText(
                    key, mKeyLabelX + mRectWidth + rectOffset,
                    mKeyLabelY, 0, canvas, getLegendLabelPaint());

            mKeyLabelX += mRectWidth + strWidth + 2 * rectOffset;
        }
    }


    /**
     *  在右边绘制图例. <br/>显示在右边时，采用单条说明占一行的方式显示
     * @param canvas
     */
    private void renderBarKeyRight(Canvas canvas, List<BarData> dataSet) {
        if (false == isShowLegend())
            return;

        initEnv();
        mTextHeight = this.getLabelTextHeight();
        mRectWidth = this.getRectWidth();

        mKeyLabelX = mPlotArea.getRight() - mOffsetX;
        mKeyLabelY = mXChart.getTop() + mTextHeight + mOffsetY;

        // 宽度是个小约定，两倍文字高度即可
        float rectHeight = mTextHeight;

        getLegendLabelPaint().setTextAlign(Align.RIGHT);
        for (BarData cData : dataSet) {
            String key = cData.getKey();
            if ("" == key)
                continue;
            getLegendLabelPaint().setColor(cData.getColor());

            canvas.drawRect(mKeyLabelX, mKeyLabelY, mKeyLabelX - mRectWidth,
                    mKeyLabelY + rectHeight, getLegendLabelPaint());

            DrawHelper.getInstance().drawRotateText(
                    key, mKeyLabelX - mRectWidth - getLegendLabelMargin(),
                    mKeyLabelY + rectHeight, 0, canvas,
                    getLegendLabelPaint());
            mKeyLabelY = MathHelper.getInstance().add(mKeyLabelY, mTextHeight);
        }

    }


    /**
     * 绘制线图的图例
     * @param canvas    画布
     * @param dataSet    数据集
     */
    public void renderLineKey(Canvas canvas, List<LnData> dataSet) {
        if (isShowLegend() == false)
            return;
        if (null == dataSet)
            return;
        if (null == mPlotArea)
            mPlotArea = mXChart.getPlotArea();

        initEnv();
        mTextHeight = this.getLabelTextHeight();
        mRectWidth = this.getRectWidth();

        mKeyLabelX = mPlotArea.getLeft() + mOffsetX;
        mKeyLabelY = mPlotArea.getTop() - mOffsetY - 5;
        getLegendLabelPaint().setTextAlign(Align.LEFT);

        String key = "";
        for (LnData cData : dataSet) {
            key = cData.getLineKey();
            if ("" == key)
                continue;
            //颜色
            getLegendLabelPaint().setColor(cData.getLineColor());

            // 竖屏
            mTextWidth = this.getLabelTextWidth(key);
            mTotalTextWidth = MathHelper.getInstance().add(mTotalTextWidth, mTextWidth);

            if (mTotalTextWidth > mPlotArea.getWidth()) {
                mKeyLabelY -= mTextHeight;
                mKeyLabelX = mPlotArea.getLeft();
                mTotalTextWidth = 0.0f;
            }

            canvas.drawLine(mKeyLabelX, mKeyLabelY - mTextHeight / 2, mKeyLabelX
                    + mRectWidth, mKeyLabelY - mTextHeight / 2, getLegendLabelPaint());

            canvas.drawText(cData.getLineKey(), mKeyLabelX + mRectWidth, mKeyLabelY
                    - mTextHeight / 3, getLegendLabelPaint());

            float dotLeft = mKeyLabelX + mRectWidth / 4;
            float dotRight = mKeyLabelX + 2 * (mRectWidth / 4);

            PlotLine pLine = cData.getPlotLine();

            if (!pLine.getDotStyle().equals(XEnum.DotStyle.HIDE)) {
                PlotDot pDot = pLine.getPlotDot();
                PlotDotRender.getInstance().renderDot(canvas, pDot,
                        dotLeft, mKeyLabelY, dotRight,
                        mKeyLabelY - mTextHeight / 2, pLine.getDotPaint()); // 标识图形
            }

            mKeyLabelX = MathHelper.getInstance().add(mKeyLabelX, mRectWidth + 10);
            mKeyLabelX = MathHelper.getInstance().add(mKeyLabelX, mTextWidth);
        }
    }


    /**
     * 绘制线图的图例
     * @param canvas    画布
     * @param dataSet    数据集
     */
    public void renderPointKey(Canvas canvas, List<ScatterData> dataSet) {
        if (isShowLegend() == false)
            return;
        if (null == dataSet)
            return;
        if (null == mPlotArea)
            mPlotArea = mXChart.getPlotArea();

        initEnv();
        mTextHeight = this.getLabelTextHeight();
        mRectWidth = this.getRectWidth();

        mKeyLabelX = mPlotArea.getLeft() + mOffsetX;
        mKeyLabelY = mPlotArea.getTop() - mOffsetY - 5;
        getLegendLabelPaint().setTextAlign(Align.LEFT);

        String key = "";
        for (ScatterData cData : dataSet) {
            key = cData.getKey();
            if ("" == key)
                continue;
            //颜色
            //getLegendLabelPaint().setColor(cData.getColor());

            // 竖屏
            mTextWidth = this.getLabelTextWidth(key);
            mTotalTextWidth = MathHelper.getInstance().add(mTotalTextWidth, mTextWidth);

            if (mTotalTextWidth > mPlotArea.getWidth()) {
                mKeyLabelY -= mTextHeight;
                mKeyLabelX = mPlotArea.getLeft();
                mTotalTextWidth = 0.0f;
            }

            getLegendLabelPaint().setColor(cData.getPlotDot().getColor());

            canvas.drawText(cData.getKey(), mKeyLabelX + mRectWidth, mKeyLabelY
                    - mTextHeight / 3, getLegendLabelPaint());

            float dotLeft = mKeyLabelX + mRectWidth / 4;
            float dotRight = mKeyLabelX + 2 * (mRectWidth / 4);

            if (!cData.getDotStyle().equals(XEnum.DotStyle.HIDE)) {
                PlotDot pDot = cData.getPlotDot();
                PlotDotRender.getInstance().renderDot(canvas, pDot,
                        dotLeft, mKeyLabelY, dotRight,
                        mKeyLabelY - mTextHeight / 2, getLegendLabelPaint()); // 标识图形
            }

            mKeyLabelX = MathHelper.getInstance().add(mKeyLabelX, mRectWidth + 10);
            mKeyLabelX = MathHelper.getInstance().add(mKeyLabelX, mTextWidth);
        }
    }


    /**
     * 绘制线图的图例
     * @param canvas    画布
     * @param dataSet    数据集
     */
    public void renderBubbleKey(Canvas canvas, List<BubbleData> dataSet) {
        if (isShowLegend() == false)
            return;
        if (null == dataSet)
            return;
        if (null == mPlotArea)
            mPlotArea = mXChart.getPlotArea();

        initEnv();
        mTextHeight = this.getLabelTextHeight();
        mRectWidth = this.getRectWidth();

        mKeyLabelX = mPlotArea.getLeft() + mOffsetX;
        mKeyLabelY = mPlotArea.getTop() - mOffsetY - 5;
        getLegendLabelPaint().setTextAlign(Align.LEFT);

        PlotDot pDot = new PlotDot();
        pDot.setDotStyle(XEnum.DotStyle.DOT);

        float dotLeft = 0.0f;
        float dotRight = 0.0f;

        String key = "";
        for (BubbleData cData : dataSet) {
            key = cData.getKey();
            if ("" == key)
                continue;
            //颜色
            //getLegendLabelPaint().setColor(cData.getColor());

            // 竖屏
            mTextWidth = this.getLabelTextWidth(key);
            mTotalTextWidth = MathHelper.getInstance().add(mTotalTextWidth, mTextWidth);

            if (mTotalTextWidth > mPlotArea.getWidth()) {
                mKeyLabelY -= mTextHeight;
                mKeyLabelX = mPlotArea.getLeft();
                mTotalTextWidth = 0.0f;
            }


            canvas.drawText(cData.getKey(), mKeyLabelX + mRectWidth, mKeyLabelY
                    - mTextHeight / 3, getLegendLabelPaint());

            dotLeft = mKeyLabelX + mRectWidth / 4;
            dotRight = mKeyLabelX + 2 * (mRectWidth / 4);

            getLegendLabelPaint().setColor(cData.getColor());

            PlotDotRender.getInstance().renderDot(canvas, pDot,
                    dotLeft, mKeyLabelY, dotRight,
                    mKeyLabelY - mTextHeight / 2, getLegendLabelPaint()); // 标识图形


            mKeyLabelX = MathHelper.getInstance().add(mKeyLabelX, mRectWidth + 10);
            mKeyLabelX = MathHelper.getInstance().add(mKeyLabelX, mTextWidth);
        }
    }


    private boolean initRoundChartKey() {
        if (null == mPlotArea)
            mPlotArea = mXChart.getPlotArea();

        initEnv();
        mTextHeight = this.getLabelTextHeight();
        mRectWidth = this.getRectWidth();

        if (!mXChart.isVerticalScreen()) //横屏
        {
            getLegendLabelPaint().setTextAlign(Align.RIGHT);
            mKeyLabelX = mPlotArea.getRight() - mOffsetX;
            mKeyLabelY = this.mPlotArea.getTop() + mTextHeight + mOffsetY;
        } else {
            getLegendLabelPaint().setTextAlign(Align.LEFT);
            mKeyLabelX = mPlotArea.getLeft() + mOffsetX;
            mKeyLabelY = this.mPlotArea.getBottom() - mOffsetY;
        }
        return true;
    }


    /**
     * 绘制圆形柱形图的图例
     * @param canvas    画布
     * @param dataset    数据集
     */
    public void renderRoundBarKey(Canvas canvas, List<ArcLineData> dataset) {
        if (isShowLegend() == false)
            return;
        if (null == dataset)
            return;

        initRoundChartKey();
        for (ArcLineData cData : dataset) {
            renderCirChartKey(canvas, cData.getKey(), cData.getBarColor());
        }
    }


    /**
     * 绘制pie图的图例
     * @param canvas    画布
     * @param dataset    数据集
     */
    public void renderPieKey(Canvas canvas, List<PieData> dataset) {
        if (isShowLegend() == false)
            return;
        if (null == dataset)
            return;

        initRoundChartKey();
        for (PieData cData : dataset) {
            renderCirChartKey(canvas, cData.getKey(), cData.getSliceColor());
        }
    }


    /**
     * 绘制key
     */
    public void renderRdKey(Canvas canvas, List<RadarData> dataset) {
        if (isShowLegend() == false)
            return;
        if (null == dataset)
            return;
        initRoundChartKey();
        for (RadarData cData : dataset) {
            renderCirChartKey(canvas, cData.getLineKey(), cData.getLineColor());
        }
    }

    private void renderCirChartKey(Canvas canvas, String key, int textColor) {
        if ("" == key)
            return;

        getLegendLabelPaint().setColor(textColor);
        if (!this.mXChart.isVerticalScreen()) //横屏 [这个到时改成让用户选类型更合适些]
        {
            canvas.drawRect(mKeyLabelX, mKeyLabelY,
                    mKeyLabelX - mRectWidth, mKeyLabelY - mTextHeight,
                    getLegendLabelPaint());

            canvas.drawText(key, mKeyLabelX - mRectWidth,
                    mKeyLabelY, getLegendLabelPaint());
            mKeyLabelY = MathHelper.getInstance().add(mKeyLabelY, mTextHeight);

            //到底了还显示不下，在左边接着显示
            if (Float.compare(mKeyLabelY, mPlotArea.getBottom()) == 1
                    || Float.compare(mKeyLabelY, mPlotArea.getBottom()) == 0) {
                mKeyLabelX = mPlotArea.getLeft();
                mKeyLabelX = mPlotArea.getTop() + mTextHeight;
            }
        } else { //竖屏
            float keyTextWidth = this.getLabelTextWidth(key);

            float tmpX = MathHelper.getInstance().add(mKeyLabelX, mRectWidth);
            tmpX = MathHelper.getInstance().add(mKeyLabelX, keyTextWidth);
            if (Float.compare(tmpX, mPlotArea.getRight()) == 1 ||
                    Float.compare(tmpX, mPlotArea.getRight()) == 0) {
                mKeyLabelY = MathHelper.getInstance().add(mKeyLabelY, mTextHeight);
                mKeyLabelX = mPlotArea.getLeft();
                mTotalTextWidth = 0.0f;
            } else {
                mTotalTextWidth = MathHelper.getInstance().add(mTotalTextWidth, keyTextWidth);
            }
            canvas.drawRect(mKeyLabelX, mKeyLabelY,
                    mKeyLabelX + mRectWidth, mKeyLabelY - mTextHeight,
                    getLegendLabelPaint());
            canvas.drawText(key, mKeyLabelX + mRectWidth,
                    mKeyLabelY, getLegendLabelPaint());
            mKeyLabelX = MathHelper.getInstance().add(mKeyLabelX, mRectWidth);
            mKeyLabelX = MathHelper.getInstance().add(mKeyLabelX, keyTextWidth + 5);
        }
    }


    public void renderRangeBarKey(Canvas canvas, String key, int textColor) {
        if ("" == key)
            return;
        if (null == mPlotArea)
            mPlotArea = mXChart.getPlotArea();

        initEnv();
        mTextHeight = this.getLabelTextHeight();
        mRectWidth = this.getRectWidth();

        mKeyLabelX = mPlotArea.getLeft() + mOffsetX + 10;
        mKeyLabelY = mPlotArea.getTop() - mOffsetY - 5;
        getLegendLabelPaint().setTextAlign(Align.LEFT);
        getLegendLabelPaint().setColor(textColor);
        getLegendLabelPaint().setStyle(Style.FILL);

        canvas.drawRect(mKeyLabelX, mKeyLabelY,
                mKeyLabelX + mRectWidth, mKeyLabelY - mTextHeight,
                getLegendLabelPaint());

        canvas.drawText(key, mKeyLabelX + mRectWidth,
                mKeyLabelY, getLegendLabelPaint());
    }

    private float getLabelTextWidth(String key) {
        return DrawHelper.getInstance().getTextWidth(getLegendLabelPaint(), key);
    }

    private float getLabelTextHeight() {
        return DrawHelper.getInstance().getPaintFontHeight(getLegendLabelPaint());
    }

    private float getRectWidth() {
        return 2 * getLabelTextHeight();
    }


}


