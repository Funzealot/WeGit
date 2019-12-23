package com.fs.lib.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by Fan on 2019/1/23.
 * Fighting!!!
 */
public class ClearEditText extends AppCompatEditText {

    private boolean hasFocus;
    private Drawable clearDrawable;
    private Drawable startDrawable;
    private Context mContext;
    private boolean isClearDrawableShow;
    private static String TAG = "ClearEditText";

    public ClearEditText(Context context) {
        this(context, null);
        mContext = context;
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        clearDrawable = getCompoundDrawablesRelative()[2];
        startDrawable = getCompoundDrawablesRelative()[0];
        setClearDrawableVisible(false);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        Log.d(TAG, "TextChanged");
        if (text.length() > 0 && !isClearDrawableShow) {
            setClearDrawableVisible(true);
        }
        if (text.length() == 0) setClearDrawableVisible(false);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {

        if (!focused) setClearDrawableVisible(false);
        if (focused && !getText().toString().isEmpty())setClearDrawableVisible(true);
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
/**
 * 判断点击的位置
* */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Drawable drawable = getCompoundDrawables()[2];
        if (event.getAction() == MotionEvent.ACTION_UP && drawable != null) {
            Log.d(TAG, "点击EditText");
            // 获得图片的padding
            int padding = getCompoundDrawablePadding();
            // 获取图片的固有高度  返回的
            int drawableHeight = drawable.getIntrinsicHeight();
            int drawableWidth = drawable.getIntrinsicWidth();
            if (x > getWidth() - drawableWidth && x < getWidth() && y > padding && y < padding + drawableHeight) {
                if (getText() != null) {
                    getText().clear();
                    setClearDrawableVisible(false);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void setClearDrawableVisible(boolean isShow) {
        if (isShow) {
            setCompoundDrawablesWithIntrinsicBounds(startDrawable, null, clearDrawable, null);
            isClearDrawableShow = true;
        } else {
            setCompoundDrawables(startDrawable, null, null, null);
            isClearDrawableShow = false;
        }
    }

    public Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setDuration(1000);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        return translateAnimation;
    }
}