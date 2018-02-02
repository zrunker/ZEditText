package cc.ibooker.zedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Gravity;
import android.widget.Toast;

import cc.ibooker.zedittextlib.ClearEditText;
import cc.ibooker.zedittextlib.DrawableEditText;
import cc.ibooker.zedittextlib.LimitNumEditText;
import cc.ibooker.zedittextlib.PasswdEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 带清空功能的EditText
        ClearEditText clearEditText = (ClearEditText) findViewById(R.id.clearEditText);
        clearEditText.setOnTextChangedListener(new ClearEditText.OnTextChangedListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 带显示和隐藏功能的EditText
        PasswdEditText passwdEditText = (PasswdEditText) findViewById(R.id.passwdEditText);

        // 带Drawable监听的EditText
        final DrawableEditText drawableEditText = (DrawableEditText) findViewById(R.id.drawableEditText);
        drawableEditText.setGravity(Gravity.CENTER);
        drawableEditText.setOnDrawableLeftListener(new DrawableEditText.OnDrawableLeftListener() {
            @Override
            public void onDrawableLeftClick() {
                Toast.makeText(MainActivity.this, "左侧点击", Toast.LENGTH_SHORT).show();
                drawableEditText.setText(Integer.parseInt(drawableEditText.getText().toString()) - 1);
            }
        });
        drawableEditText.setOnDrawableRightListener(new DrawableEditText.OnDrawableRightListener() {
            @Override
            public void onDrawableRightClick() {
                Toast.makeText(MainActivity.this, "右侧点击", Toast.LENGTH_SHORT).show();
                drawableEditText.setText(Integer.parseInt(drawableEditText.getText().toString()) + 1);
            }
        });

        // 带输入字数限制和显示输入字数的EditText
        LimitNumEditText limitNumEditText = (LimitNumEditText) findViewById(R.id.limitNumEditText);
        limitNumEditText.setEditTextSize(16)
                .setEditTextMargin(20, 20, 20, 20)
                .setEditTextColor("#FF0Fad")
                .setEditTextLineNum(5)
                .setEditTextMaxLines(10)
                .setEditTextHint("你好啊")
                .setEditTextHintColor("#405ff2")
                .setEditTextMaxWordsNum(10)
                .setTextViewSize(16)
                .setTextViewColor("#894563")
                .setTextViewMargin(15, 15, 15, 15)
                .setOnMoreMaxWordsNumListener(new LimitNumEditText.OnMoreMaxWordsNumListener() {
                    @Override
                    public void onMoreMaxWordsNum(int maxNum) {
                        Toast.makeText(MainActivity.this, "你已经超过最大字数", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
