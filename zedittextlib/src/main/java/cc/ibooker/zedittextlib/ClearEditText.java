package cc.ibooker.zedittextlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 带清空功能的EditText-DrawableRight，监听焦点和输入值改变状态
 * Created by 邹峰立 on 2017/3/4 0004.
 */
public class ClearEditText extends android.support.v7.widget.AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    /**
     * 删除Drawable引用
     */
    private Drawable mClearDrawable;
    private int clearRes;
    /**
     * 控件是否有焦点
     */
    private boolean hasFocus;

    // 构造方法 1->2->3
    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
        clearRes = typedArray.getResourceId(R.styleable.ClearEditText_clearRes, R.mipmap.icon_clear);
        typedArray.recycle();

        init();
    }

    // 初始化方法
    private void init() {
        // Drawable顺序左上右下，0123
        // 获取DrawRight内容
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            // 未设置默认DrawableRight
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mClearDrawable = getResources().getDrawable(clearRes, null);
            } else {
                mClearDrawable = getResources().getDrawable(clearRes);
            }
        }
        // 设置Drawable大小和位置
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        // 添加焦点改变监听
        setOnFocusChangeListener(this);
        // 添加输入内容改变监听
        addTextChangedListener(this);
    }

    public Drawable getClearDrawable() {
        return mClearDrawable;
    }

    public void setClearDrawable(Drawable mClearDrawable) {
        this.mClearDrawable = mClearDrawable;
    }

    public int getClearRes() {
        return clearRes;
    }

    public void setClearRes(int clearRes) {
        this.clearRes = clearRes;
        init();
    }

    // 焦点改变事件
    // 当焦点在时，通过判断输入值，实现隐藏和现实DrawableRight
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus && !TextUtils.isEmpty(getText())) {
            // 焦点存在，而且有输入值
            setDrawableRightVisible(getText().length() > 0);
        } else {
            // 焦点不存在时候，隐藏DrawableRight
            setDrawableRightVisible(false);
        }
        // 监听焦点事件
        if (onFocusChangeListener != null)
            onFocusChangeListener.onFocusChange(v, hasFocus);
    }

    /**
     * 隐藏或显示DrawableRight
     *
     * @param visible true=显示，false=隐藏
     */
    private void setDrawableRightVisible(boolean visible) {
        // 控件setCompoundDrawables设置Drawable，隐藏则设置null，左上右下
        Drawable drawableRight = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], drawableRight, getCompoundDrawables()[3]);

        // 监听事件
        if (onDrawRightVisible != null)
            onDrawRightVisible.isDrawableRightVisible(visible);
    }

    // 触摸事件-实现ClearDrawable清空功能
    // 1、如何判断Drawable被点击？——通过判断点击的位置
    // 2、什么时候实现清空？——当ClearDrawable不为空且被点击
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 判断手指是否触摸
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // 判断ClearDrawable(DrawableRight)是否为空
            if (getCompoundDrawables()[2] != null) {
                // 当按下的位置 在EditText的宽度 - 图标到控件右边的间距 - 图标的宽度 与 EditText的宽度 - 图标到控件右边的间距之间，算点击了图标，竖直方向不用考虑
                // getTotalPaddingRight获取右侧图标以及右侧Padding和
                // getPaddingRight获取右侧Padding值
                boolean isTouchRight = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (isTouchRight) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    // 输入内容变化前
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (onTextChangedListener != null)
            onTextChangedListener.beforeTextChanged(s, start, count, after);
    }

    // 输入内容变化时，判断是否有输入值=有 并且焦点=存在 则显示ClearDrawable
    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (hasFocus) {
            setDrawableRightVisible(text.length() > 0);
        }
        if (onTextChangedListener != null)
            onTextChangedListener.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    // 输入内容变化后
    @Override
    public void afterTextChanged(Editable s) {
        if (onTextChangedListener != null)
            onTextChangedListener.afterTextChanged(s);
    }

    // 文本改变接口监听
    public interface OnTextChangedListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter);

        void afterTextChanged(Editable s);
    }

    private OnTextChangedListener onTextChangedListener;

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }

    // 焦点事件
    public interface OnFocusChangeListener {
        void onFocusChange(View v, boolean hasFocus);
    }

    private OnFocusChangeListener onFocusChangeListener;

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    // DrawRight显示或隐藏事件
    public interface OnDrawRightVisible {
        void isDrawableRightVisible(boolean visible);
    }

    private OnDrawRightVisible onDrawRightVisible;

    public void setOnDrawRightVisible(OnDrawRightVisible onDrawRightVisible) {
        this.onDrawRightVisible = onDrawRightVisible;
    }
}
