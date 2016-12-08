package com.soaring.widget.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.soaring.widget.R;

public class RulerNoScorllView extends View{

	Context context;
	
	public RulerNoScorllView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public RulerNoScorllView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public RulerNoScorllView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	int lineWidth;		
	int lineHeight;
	int horizonWidth;
	int veticalHeight;
	int keduHeight ;
	float spaceWidth;
	int textHeight;
	
	int[] data ;
	private int height;
	private int width;
	int step;
	
	
	/**
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
		invalidate();
	}

	public void setData(int[] data) {
		this.data = data;
		invalidate();
	}
	
	float ans;
	public void setAns(float ans) {
		this.ans = ans;
		invalidate();
	}
	
	int space = 1;
//	public void setSpace(int space) {
//		this.space = space;
//		if (this.space <1) {
//			this.space = 1;
//		}
//		if (this.space>10) {
//			this.space = 10;
//		}
//	}
//	
	
	
	
	private void drawText(String text, float x, float y, Canvas canvas) {
		Paint p = new Paint();
		p.setTextSize(30);
		p.setColor(context.getResources().getColor(R.color.red_light));
		p.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(text, x, y, p);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		height = getHeight();
		width = getWidth();
		
		lineHeight = height/2;
		horizonWidth = dip2px(context, 15);
		veticalHeight = dip2px(context, 12);
		keduHeight = dip2px(context, 15);
		textHeight = dip2px(context, 15);
//		Log.e("", "绘制  ");    
		if (data != null) {
			int length = data[data.length-1]-data[0];
			float s = (width-2*horizonWidth)/length;
			spaceWidth = step*s;
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(context.getResources().getColor(R.color.red_light));
		paint.setAntiAlias(true);
		paint.setStrokeWidth(4);
		paint.setStyle(Style.STROKE);
		canvas.drawLine(horizonWidth, lineHeight, width-horizonWidth, lineHeight, paint);
		
		
		for (int i = 0; i < data.length; i++) {
			drawText(data[i]+"", horizonWidth+spaceWidth*i+10, lineHeight+textHeight+10, canvas);
			if (data[i] == 18) {
//				Log.e("", "18对应位置  "+(horizonWidth+spaceWidth*i+10));
			}
		}
//		Log.e("", "水平间距  "+horizonWidth);
		paint.setColor(Color.WHITE);
		
		for (int i = 0; i < length/space; i++) {
			canvas.drawLine(horizonWidth+s*i+10, lineHeight-2, horizonWidth+s*i+10, lineHeight-keduHeight, paint);
		}
//		Log.e("", "ans = "+ans);
		
		if (ans>0) {
			paint.setColor(context.getResources().getColor(R.color.red_light));
			int sa = (int) Math.floor(ans);
			float distance = (sa - data[0])*s+(ans-sa)*s+horizonWidth+10;
//			Log.e("", "sa = "+sa+" distance  = "+distance+" "+(ans*s+10+horizonWidth)); 
			
			if(ans<data[0]){
				canvas.drawLine(horizonWidth+10, lineHeight-2, horizonWidth+10, lineHeight-keduHeight-15, paint);
			}else if(ans>data[data.length-1]){
				canvas.drawLine(horizonWidth+s*(length/space-1)+10, lineHeight-2, horizonWidth+s*(length/space-1)+10, lineHeight-keduHeight-15, paint);
			}else{
				canvas.drawLine(distance, lineHeight, distance, lineHeight-keduHeight-15, paint);
			}
		}
		
		}
	}
	
	
	
	
}
