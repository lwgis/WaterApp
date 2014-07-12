package cn.bjeastearth.waterapp.myview;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FiTextView extends TextView {
	Context mContext;
	public FiTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext=context;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	    Layout layout = getLayout();
	    if (layout != null) {
	        int height = (int)FloatMath.ceil(getMaxLineHeight(this.getText().toString()))
	                + getCompoundPaddingTop() + getCompoundPaddingBottom();
	        int width = getMeasuredWidth();            
	        setMeasuredDimension(width, height);
	    }
	}

	private float getMaxLineHeight(String str) {
		float height = 0.0f;
		float screenW = ((Activity) mContext).getWindowManager()
				.getDefaultDisplay().getWidth();
		float paddingLeft = ((LinearLayout) this.getParent()).getPaddingLeft();
		float paddingReft = ((LinearLayout) this.getParent()).getPaddingRight();
		// 这里具体this.getPaint()要注意使用，要看你的TextView在什么位置，这个是拿TextView父控件的Padding的，为了更准确的算出换行
		int line = (int) Math.ceil((this.getPaint().measureText(str) / (screenW
				- paddingLeft - paddingReft)));
		height = (this.getPaint().getFontMetrics().descent - this.getPaint()
				.getFontMetrics().ascent) * line;
		return height;
	}
}
