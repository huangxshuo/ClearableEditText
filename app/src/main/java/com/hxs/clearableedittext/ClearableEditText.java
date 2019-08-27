package com.hxs.clearableedittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ClearableEditText extends AppCompatEditText {


	/**
	 * clearable drawable
	 */
	private Drawable mClearDrawable;

	/**
	 * Right Drawable 是否可见
	 */
	private boolean mIsClearVisible;

	public ClearableEditText(Context context) {
		super(context);
		init();
	}

	public ClearableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {

		Drawable[] drawables = getCompoundDrawables();
		// Right Drawable;
		mClearDrawable = drawables[2];

		// 初始化将drawable隐藏
		setClearDrawableVisible(false);
		// 添加TextChangedListener
		addTextChangedListener(mTextWatcher);
		setOnFocusChangeListener(mOnFocusChangeListener);

	}

	private TextWatcher mTextWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			setClearDrawableVisible(s.length() > 0);
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	/**
	 * 输入框焦点变化事件
	 */
	private OnFocusChangeListener mOnFocusChangeListener = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (getError() == null) {
				if (hasFocus) {
					if (getText().length() > 0) {
						setClearDrawableVisible(true);
					}
				} else {
					setClearDrawableVisible(false);
				}
			}
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getError() == null && mIsClearVisible) {
				float x = event.getX();
				//判断有效点击区域
				if (x >= getWidth() - getTotalPaddingRight() && x <= getWidth() - getPaddingRight()) {
					clearText();
				}
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 清空输入框
	 */
	private void clearText() {
		if (getText().length() > 0) {
			setText("");
		}
	}

	/**
	 * 设置Right Drawable是否可见
	 */
	public void setClearDrawableVisible(boolean isVisible) {

		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1],
				isVisible ? mClearDrawable : null,
				getCompoundDrawables()[3]);

		mIsClearVisible = isVisible;
	}

	@Override
	public void setError(CharSequence error, Drawable icon) {
		if (error != null) {
			setClearDrawableVisible(true);
		}
		super.setError(error, icon);
	}
}
