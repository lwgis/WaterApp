package cn.bjeastearth.waterapp.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TextProgressBar extends ProgressBar {
	  String text;
      Paint mPaint;
	public TextProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initText();
		// TODO Auto-generated constructor stub
	}
	 @Override
     public synchronized void setProgress(int progress) {
         // TODO Auto-generated method stub
         setText(progress);
         super.setProgress(progress);
          
     }
  
     @Override
     protected synchronized void onDraw(Canvas canvas) {
         // TODO Auto-generated method stub
         super.onDraw(canvas);
         this.setText();
         Rect rect = new Rect();
         this.mPaint.setTextSize(6*getWidth()/100);
         this.mPaint.getTextBounds(this.text, 0, this.text.length()-1, rect);
         int x = (getWidth() / 2)*getProgress()/100 - rect.centerX();  
         int y = (getHeight() / 2) - rect.centerY();  
         canvas.drawText(this.text, x, y, this.mPaint);  
     }
      
     //初始化，画笔
     private void initText(){
         this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
         this.mPaint.setColor(Color.WHITE);
          
     }
      
     private void setText(){
         setText(this.getProgress());
     }
      
     //设置文字内容
     private void setText(int progress){
         int i = (progress * 100)/this.getMax();
         this.text = String.valueOf(i) + "%";
     }
      
}
