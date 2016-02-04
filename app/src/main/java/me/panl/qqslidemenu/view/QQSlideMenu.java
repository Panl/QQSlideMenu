package me.panl.qqslidemenu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

import me.panl.qqslidemenu.R;

/**
 * Created by panl on 15/6/17.
 */
public class QQSlideMenu extends HorizontalScrollView {
    LinearLayout mWrapper;
    ViewGroup mMenu;
    ViewGroup mContent;
    int mScreenWidth;
    //单位是dp
    int mMenuRightMargin = 50;
    int mMenuWidth;
    boolean onMeasured = false;
    private boolean isOpen;


    public QQSlideMenu(Context context) {
        this(context,null);
    }
    /**
     * 未使用自定义属性时调用这个构造方法
     * @param context
     * @param attrs
     */
    public QQSlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    /**
     * 当使用了自定义属性时调用这个构造方法
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public QQSlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获得自定义属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.QQSlideMenu,defStyleAttr,0);
        int n = array.getIndexCount();
        for (int i = 0;i < n; i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.QQSlideMenu_menuRightMargin:
                    mMenuRightMargin = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                            context.getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }

        }


        array.recycle();
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
    }




    /**
     * 设置子view的宽高
     * 设置自己的宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!onMeasured){
            mWrapper = (LinearLayout)getChildAt(0);
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            mContent = (ViewGroup) mWrapper.getChildAt(1);
            mMenu.getLayoutParams().width = mScreenWidth - mMenuRightMargin;
            mMenuWidth = mMenu.getLayoutParams().width;
            mContent.getLayoutParams().width = mScreenWidth;
            onMeasured = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            this.scrollTo(mMenuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                    isOpen = false;
                }else {
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public void openMenu(){
        if (isOpen)
            return;
        this.smoothScrollTo(0,0);
        isOpen = true;
    }

    public void closeMenu(){
        if (!isOpen)
            return;
        this.smoothScrollTo(mMenuWidth,0);
        isOpen = false;
    }

    public void toggleMenu(){
        if (isOpen){
            closeMenu();
        }else {
            openMenu();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //设置属性动画
        float translateX = l*1.0f/mMenuWidth;
        float rightScale = 0.8f + 0.2f*translateX;
        float leftScale = 1.0f - 0.4f*translateX;
        float leftAlpha = 0.6f + 0.4f*(1-translateX);


        ViewHelper.setTranslationX(mMenu,mMenuWidth*translateX*0.7f);
        ViewHelper.setScaleX(mMenu,leftScale);
        ViewHelper.setScaleY(mMenu,leftScale);
        ViewHelper.setAlpha(mMenu,leftAlpha);

        ViewHelper.setPivotX(mContent,0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent,rightScale);
        ViewHelper.setScaleY(mContent, rightScale);



    }
}
