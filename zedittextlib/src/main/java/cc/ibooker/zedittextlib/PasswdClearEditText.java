package cc.ibooker.zedittextlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 带有清空按钮的密码输入框
 */
public class PasswdClearEditText extends FrameLayout {
    private ClearEditText clearEditText;
    private ImageView imageView;
    /**
     * 右侧Drawable引入
     */
    private int openPasswdRes, closePasswdRes;
    private int clearRes;
    /**
     * 判断当前密码打开状态，默认关闭状态
     */
    private boolean isOpen = false;

    public PasswdClearEditText(@NonNull Context context) {
        this(context, null);
    }

    public PasswdClearEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswdClearEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswdClearEditText);
        openPasswdRes = typedArray.getResourceId(R.styleable.PasswdClearEditText_openPasswdCeRes, R.mipmap.icon_open_eye);
        closePasswdRes = typedArray.getResourceId(R.styleable.PasswdClearEditText_closePasswdCeRes, R.mipmap.icon_close_eye);
        clearRes = typedArray.getResourceId(R.styleable.PasswdClearEditText_clearCeRes, R.mipmap.icon_clear);
        int eyeDistanceRight = typedArray.getInteger(R.styleable.PasswdClearEditText_eyeDistanceRight, 25);
        typedArray.recycle();

        clearEditText = new ClearEditText(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START | Gravity.CENTER_VERTICAL);
        clearEditText.setLayoutParams(params);
        clearEditText.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        clearEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        clearEditText.setClearRes(clearRes);
        clearEditText.setOnDrawRightVisible(new ClearEditText.OnDrawRightVisible() {
            @Override
            public void isDrawableRightVisible(boolean visible) {
                imageView.setVisibility(visible ? VISIBLE : GONE);
            }
        });
        clearEditText.setBackgroundResource(android.R.color.transparent);
        this.addView(clearEditText);

        imageView = new ImageView(context);
        imageView.setImageResource(openPasswdRes);
        LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL);
        params1.rightMargin = clearEditText.getClearDrawable().getIntrinsicWidth() + eyeDistanceRight;
        imageView.setLayoutParams(params1);
        imageView.setPadding(5, 5, 5, 5);
        imageView.setVisibility(GONE);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setmDrawableRight(!isOpen);
            }
        });
        this.addView(imageView);
    }

    // 初始化DrawableRight
    public void setmDrawableRight(boolean bool) {
        isOpen = bool;
        int drawableId;
        // 通过状态设置DrawableRight的样式
        if (!isOpen)
            drawableId = openPasswdRes;
        else
            drawableId = closePasswdRes;
        if (imageView != null)
            imageView.setImageResource(drawableId);
        // 执行点击事件-隐藏或显示
        if (isOpen)
            clearEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            clearEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        // 设置光标位置
        Editable text = clearEditText.getText();
        clearEditText.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
    }

    public ClearEditText getEditText() {
        return clearEditText;
    }

    public ImageView getEyeView() {
        return imageView;
    }

}
