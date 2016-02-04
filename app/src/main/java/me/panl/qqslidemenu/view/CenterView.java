package me.panl.qqslidemenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by panl on 15/7/6.
 */
public class CenterView extends ScrollView {

    LinearLayout mWrapper;
    ViewGroup headView;
    ViewGroup contentView;
    int headViewHeight;
    int screenHeight;
    boolean onMeasured = false;

    public CenterView(Context context) {
        this(context, null, 0);
    }

    public CenterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!onMeasured) {
            mWrapper = (LinearLayout) getChildAt(0);
            headView = (ViewGroup) mWrapper.getChildAt(0);
            contentView = (ViewGroup) mWrapper.getChildAt(1);
            contentView.getLayoutParams().height = screenHeight;
            onMeasured = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            headViewHeight = headView.getLayoutParams().height;
        }
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        float translationX = t*0.75f;
        Log.i("L+T","l:"+l+" t:"+t);
        ViewHelper.setTranslationY(headView,translationX);
        super.onScrollChanged(l, t, oldl, oldt);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
