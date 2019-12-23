package com.fs.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import com.fs.lib.R;


/**
 * Created by Fan on 2018/12/29.
 * Fighting!!!
 */
public class PasswordEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {

    private Context context;
    private boolean hasFocus;
    private boolean isPasswdVisible = false;
    private boolean isShowDrawable;
    private CharSequence charSequence;
    private int visibleDrawableId;
    private int invisibleDrawableId;
    private Drawable visibleDrawable;
    private Drawable invisibleDrawable;
    private Drawable passwdDrawable;

    public PasswordEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        visibleDrawableId = typedArray.getResourceId(R.styleable.PasswordEditText_eyeVisibleDrawable, R.drawable.ic_eye_visible);
        invisibleDrawableId = typedArray.getResourceId(R.styleable.PasswordEditText_eyeInvisibleDrawable, R.drawable.ic_eye_invisible);
        typedArray.recycle();
        init();
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        visibleDrawableId = typedArray.getResourceId(R.styleable.PasswordEditText_eyeVisibleDrawable, R.drawable.ic_eye_visible);
        invisibleDrawableId = typedArray.getResourceId(R.styleable.PasswordEditText_eyeInvisibleDrawable, R.drawable.ic_eye_invisible);
        typedArray.recycle();
        init();

    }

    private void init() {
        passwdDrawable = getCompoundDrawablesRelative()[0];
        visibleDrawable = ContextCompat.getDrawable(context, visibleDrawableId);
        invisibleDrawable = ContextCompat.getDrawable(context, invisibleDrawableId);
        visibleDrawable.setBounds(0, 0, visibleDrawable.getIntrinsicWidth(), visibleDrawable.getIntrinsicHeight());
        invisibleDrawable.setBounds(0, 0, invisibleDrawable.getIntrinsicWidth(), invisibleDrawable.getIntrinsicHeight());
        addTextChangedListener(this);
        setOnFocusChangeListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        Drawable drawable = getCompoundDrawables()[2];
        if (event.getAction() == MotionEvent.ACTION_UP && drawable != null) {
            int padding = getCompoundDrawablePadding();
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            if (x > getWidth() - drawableWidth && x < getWidth() && y > padding && y < padding + drawableHeight && getText() != null) {
                if (!isPasswdVisible) {
                    setCompoundDrawables(passwdDrawable, null, visibleDrawable, null);
                    isPasswdVisible = true;
                    // 密码可见
                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    // setInputType(0x90);
                } else {
                    setCompoundDrawablesRelative(passwdDrawable, null, invisibleDrawable, null);
                    isPasswdVisible = false;
                    // 使密码不可见
                    setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // setInputType(0x80);
                }
                setSelection(getText().toString().length());
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0 && !isShowDrawable) {
            setDrawableVisible(true);
        }
        if (s.length() == 0) {
            setDrawableVisible(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (!hasFocus) {
            setDrawableVisible(false);
            isShowDrawable = false;
        }
        if (hasFocus && !(getText().toString().isEmpty())) {
            setDrawableVisible(true);
            isShowDrawable = true;
        }
    }

    private void setDrawableVisible(boolean isShowDrawable) {

        if (isShowDrawable) {
            setCompoundDrawables(passwdDrawable, null, invisibleDrawable, null);
            this.isShowDrawable = true;
        } else {
            setCompoundDrawables(passwdDrawable, null, null, null);
            this.isShowDrawable = false;
        }
    }
}
