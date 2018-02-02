# ZEditText
不一样的EditText（一）DrawableRight清空输入值功能。不一样的EditText（二）DrawableRight显示和隐藏输入密码。不一样的EditText（三）DrawableLeft和DrawableRight的点击事件监听。（四）限制字数，显示剩余字数和输入字数。使用工具Android Studio

引入Android Studio：

1、在build.gradle文件中添加以下代码：
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
dependencies {
	compile 'com.github.zrunker:ZEditText:v1.0.1'
}
```
2、在maven文件中添加以下代码：
```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
<dependency>
	<groupId>com.github.zrunker</groupId>
	<artifactId>ZEditText</artifactId>
	<version>v1.0.1</version>
</dependency>
```
用法：

#### 一、DrawableRight清空输入值功能。

引入布局文件
```
<cc.ibooker.zedittextlib.ClearEditText
        android:id="@+id/clearEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="text" />
```
功能实现
```
// 带清空功能的EditText
ClearEditText clearEditText = (ClearEditText) findViewById(R.id.clearEditText);
// 输入内容改变事件监听
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
```

#### 二、DrawableRight显示和隐藏输入密码。

引入布局文件
```
<cc.ibooker.zedittextlib.PasswdEditText
        android:id="@+id/passwdEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textPassword" />
```

#### 三、DrawableLeft和DrawableRight的点击事件监听。

引入布局文件
```
<cc.ibooker.zedittextlib.DrawableEditText
        android:id="@+id/drawableEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:drawableEnd="@mipmap/icon_reduce"
        android:drawableLeft="@mipmap/icon_add"
        android:drawableRight="@mipmap/icon_reduce"
        android:drawableStart="@mipmap/icon_add"
        android:inputType="number" />
```
功能实现
```
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
```
#### 四、限制字数，显示剩余字数和输入字数。

引入布局文件
```
<cc.ibooker.zedittextlib.LimitNumEditText
        android:id="@+id/limitNumEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp" />
```
功能实现
```
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
```
