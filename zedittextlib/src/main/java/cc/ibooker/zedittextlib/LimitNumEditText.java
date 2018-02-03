package cc.ibooker.zedittextlib;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 限制字数，显示已输入字数和限制输入字数
 * Created by 邹峰立 on 2017/7/17.
 */
public class LimitNumEditText extends LinearLayout {
    private Context context;
    private EditText editText;
    private TextView textView;
    private LinearLayout.LayoutParams layoutParams;
    private int maxWordsNum = 0;// 默认为0

    public LimitNumEditText(Context context) {
        this(context, null);
    }

    public LimitNumEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitNumEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    // 初始化
    private void init() {
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundResource(R.drawable.bg_limitnum_edittext);
        layoutParams.setMargins(0, 0, 0, 0);
        this.setLayoutParams(layoutParams);
        this.setPadding(0, 0, 0, 0);

        editText = new EditText(context);
        layoutParams.setMargins(10, 10, 10, 10);
        editText.setLayoutParams(layoutParams);
        editText.setPadding(0, 0, 0, 0);
        editText.setTextSize(14);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setBackgroundResource(android.R.color.transparent);
        editText.setGravity(Gravity.TOP | Gravity.START);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (onTextChangedListener != null)
                    onTextChangedListener.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onTextChangedListener != null)
                    onTextChangedListener.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                int num = s.length();
                if (textView != null)
                    textView.setText(num + "/" + maxWordsNum);
                if (num >= maxWordsNum) {
                    if (onMoreMaxWordsNumListener != null)
                        onMoreMaxWordsNumListener.onMoreMaxWordsNum(maxWordsNum);
                }
                if (onTextChangedListener != null)
                    onTextChangedListener.afterTextChanged(s);
            }
        });
        this.addView(editText);

        textView = new TextView(context);
        layoutParams.setMargins(12, 12, 12, 10);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(0, 0, 0, 0);
        textView.setGravity(Gravity.END);
        textView.setBackgroundResource(android.R.color.transparent);
        this.addView(textView);
    }

    // 设置输入框文本大小
    public LimitNumEditText setEditTextSize(float size) {
        if (editText != null)
            editText.setTextSize(size);
        return this;
    }

    // 设置输入框文本颜色
    public LimitNumEditText setEditTextColor(String color) {
        try {
            if (editText != null)
                editText.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    // 设置输入框显示行数
    public LimitNumEditText setEditTextLineNum(int lineNum) {
        if (editText != null)
            editText.setLines(lineNum);
        return this;
    }

    // 设置输入框显示最大行数
    public LimitNumEditText setEditTextMaxLines(int maxLines) {
        if (editText != null)
            editText.setMaxLines(maxLines);
        return this;
    }

    // 设置输入框默认文字
    public LimitNumEditText setEditTextHint(String hint) {
        if (editText != null)
            editText.setHint(hint);
        return this;
    }

    // 设置输入框默认文字颜色
    public LimitNumEditText setEditTextHintColor(String color) {
        try {
            if (editText != null)
                editText.setHintTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    // 设置输入框最大字数
    public LimitNumEditText setEditTextMaxWordsNum(int maxWordsNum) {
        this.maxWordsNum = maxWordsNum;
        if (editText != null) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxWordsNum)});
            if (textView != null)
                textView.setText(editText.getText().length() + "/" + maxWordsNum);
        }
        return this;
    }

    // 设置输入框Margin
    public LimitNumEditText setEditTextMargin(int left, int top, int right, int bottom) {
        if (editText != null && layoutParams != null) {
            layoutParams.setMargins(left, top, right, bottom);
            editText.setLayoutParams(layoutParams);
        }
        return this;
    }

    // 设置标记控件文字大小
    public LimitNumEditText setTextViewSize(float size) {
        if (textView != null)
            textView.setTextSize(size);
        return this;
    }

    // 设置标记控件文字颜色
    public LimitNumEditText setTextViewColor(String color) {
        try {
            if (textView != null)
                textView.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    // 设置标记控件Margin
    public LimitNumEditText setTextViewMargin(int left, int top, int right, int bottom) {
        if (textView != null && layoutParams != null) {
            layoutParams.setMargins(left, top, right, bottom);
            textView.setLayoutParams(layoutParams);
        }
        return this;
    }

    // 暴露EditText
    public EditText getEditText() {
        return editText;
    }

    // 暴露TextView
    public TextView getTextView() {
        return textView;
    }

    // 暴露最大字数
    public int getMaxWordsNum() {
        return maxWordsNum;
    }

    // 当输入字数多于最大字数事件接口
    public interface OnMoreMaxWordsNumListener {
        void onMoreMaxWordsNum(int maxNum);
    }

    private OnMoreMaxWordsNumListener onMoreMaxWordsNumListener;

    public void setOnMoreMaxWordsNumListener(OnMoreMaxWordsNumListener onMoreMaxWordsNumListener) {
        this.onMoreMaxWordsNumListener = onMoreMaxWordsNumListener;
    }

    // 输入框内容变化事件
    public interface OnTextChangedListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

    private OnTextChangedListener onTextChangedListener;

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }
}
