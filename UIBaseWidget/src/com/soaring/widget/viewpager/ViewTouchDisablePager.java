/**
 * ViewTouchDisablePager.java 2015-3-3
 * <p/>
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 */
package com.soaring.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <b>ViewTouchDisablePager。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-3-3 上午11:02:06</td><td>建立类型</td></tr>
 * <p/>
 * </table>
 *
 * @author Renyuxiang
 * @version 1.0
 * @since 1.0
 */
public class ViewTouchDisablePager
        extends ViewPager {
    private boolean isCanTouchMove = false;

    public ViewTouchDisablePager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanTouchMove()) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanTouchMove()) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    public boolean isCanTouchMove() {
        return isCanTouchMove;
    }

    public void setCanTouchMove(boolean canTouchMove) {
        isCanTouchMove = canTouchMove;
    }
}
