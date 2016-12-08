package com.soaring.widget.abslistview;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by lipeng on 2015/11/23.
 */
public class NoScrollExpandableListView extends ExpandableListView{

    public NoScrollExpandableListView(Context context,android.util.AttributeSet attrs){
            super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
