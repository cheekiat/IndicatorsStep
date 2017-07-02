package com.cheekiat.indicatorsteplib;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chee Kiat on 15/05/2017.
 */

public class StepProgress extends LinearLayout {

    Context mContext;
    List<String> storeData = new ArrayList<>();
    int dotDefaultSize;
    float dotSelectedSize;
    int itemMargins;
    int mode;
    int barHeight;
    int textSize;
    private Integer selectedTextColor, unselectTextColor, selectedColor, unselectColor;
    DotOnClickListener onClickListener;
    float unselectSize;
    View v;
    LinearLayout mDotLayout;
    View mSelectedBar;
    boolean hideBar;

    public StepProgress(Context context) {
        super(context);
        initView(context);
    }

    public void setDotsOnClickListener(DotOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public StepProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public StepProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        this.mContext = context;

        int storeSelectedSize = 0;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.StepUi,
                    0, 0);

            try {
                mode = a.getInteger(R.styleable.StepUi_indicator_mode, 0);
                barHeight = a.getInteger(R.styleable.StepUi_barHeight, 20);
                selectedTextColor = a.getColor(R.styleable.StepUi_selectedTextColor, Color.WHITE);
                unselectTextColor = a.getColor(R.styleable.StepUi_unselectTextColor, Color.BLACK);
                selectedColor = a.getColor(R.styleable.StepUi_selectedColor, Color.parseColor("#F3AD33"));
                unselectColor = a.getColor(R.styleable.StepUi_unselectColor, Color.parseColor("#D6D6D6"));
                itemMargins = a.getDimensionPixelSize(R.styleable.StepUi_itemMargins, 15);
                dotDefaultSize = a.getDimensionPixelSize(R.styleable.StepUi_dotDefaultSize, 60);
                storeSelectedSize = a.getDimensionPixelSize(R.styleable.StepUi_dotSelectedSize, 90);
                textSize = a.getDimensionPixelSize(R.styleable.StepUi_textSize, 10);
                hideBar = a.getBoolean(R.styleable.StepUi_hideBar,false);
            } finally {
                a.recycle();
            }
        }

        if (mode == 1) {

            if (barHeight > dotDefaultSize) {
                barHeight = dotDefaultSize;
            }
        }

        int checkItemMargins = (storeSelectedSize - dotDefaultSize) / 2;
        if (checkItemMargins > itemMargins) {
            itemMargins = checkItemMargins;
        }
        dotSelectedSize = (float) (1.0 / dotDefaultSize) * storeSelectedSize;
        unselectSize = (float) (1.0 / dotDefaultSize) * dotDefaultSize;
    }

    private void initView(Context context) {

        this.mContext = context;

        initView(context, null);

    }

    public void selected(int position) {
        if (mDotLayout == null) {
            return;
        }
        if (mode == 0) {
            for (int j = 0; j < mDotLayout.getChildCount(); j++) {
                View mView = mDotLayout.getChildAt(j);
                if (position == j) {
                    mView.setBackgroundResource(R.drawable.selected);
                    mView.animate().scaleX(dotSelectedSize);
                    mView.animate().scaleY(dotSelectedSize);
                    changeColor(mView.getBackground(), selectedColor);
                } else {
                    mView.setBackgroundResource(R.drawable.unselect);
                    mView.animate().scaleX(unselectSize);
                    mView.animate().scaleY(unselectSize);
                    changeColor(mView.getBackground(), unselectColor);
                }
                mView.invalidate();
            }
        } else {

            for (int j = 0; j < mDotLayout.getChildCount(); j++) {
                TextView mView = (TextView) mDotLayout.getChildAt(j);
                if (position + 1 > j) {
                    mView.setBackgroundResource(R.drawable.selected);
                    mView.setTextColor(selectedTextColor);
                    changeColor(mView.getBackground(), selectedColor);
                } else {
                    mView.setBackgroundResource(R.drawable.unselect);
                    mView.setTextColor(unselectTextColor);
                    changeColor(mView.getBackground(), unselectColor);
                }
                if (position == j) {
                    mView.animate().scaleX(dotSelectedSize);
                    mView.animate().scaleY(dotSelectedSize);
                } else {
                    mView.animate().scaleX(unselectSize);
                    mView.animate().scaleY(unselectSize);
                }
            }

            int sum = mDotLayout.getChildCount();
            sum = sum - 1;
            int vw = mDotLayout.getWidth() - dotDefaultSize - (itemMargins * 2);
            if (sum > 0) {
                animationViewSize(mSelectedBar, position * (vw / sum));
            }
        }
    }


    public void addDot() {
        removeAllViews();

        storeData.add(null);
        initData(storeData);
    }

    public void setDotCount(int count) {
        for (int i = 0; i < count; i++) {

            storeData.add(null);
        }
        initData(storeData);
    }

    public void setDotText(String data) {
        removeAllViews();
//        if(storeData.get(position))
        storeData.add(data);
        initData(storeData);
    }

    private void initData(List<String> storeData) {

        LayoutParams mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        v = LayoutInflater.from(getContext()).inflate(R.layout.layout, null);
        mDotLayout = (LinearLayout) v.findViewById(R.id.dot_layout);
        View mBar = (View) v.findViewById(R.id.bar);
        mSelectedBar = (View) v.findViewById(R.id.selected_bar);
        mSelectedBar.setBackgroundColor(selectedColor);
        mBar.setBackgroundColor(unselectColor);
        RelativeLayout.LayoutParams mViewParams = (RelativeLayout.LayoutParams) mBar.getLayoutParams();
        mViewParams.height = barHeight;
        mViewParams.setMargins(itemMargins + (dotDefaultSize / 2), 0, itemMargins + (dotDefaultSize / 2), 0);
        mBar.setLayoutParams(mViewParams);
        mBar.requestLayout();
        RelativeLayout.LayoutParams mSelectedViewParams = (RelativeLayout.LayoutParams) mSelectedBar.getLayoutParams();
        mSelectedViewParams.height = barHeight;
        mSelectedViewParams.setMargins(itemMargins + (dotDefaultSize / 2), 0, itemMargins + (dotDefaultSize / 2), 0);
        mSelectedBar.setLayoutParams(mSelectedViewParams);
        mSelectedBar.requestLayout();

        if(hideBar){
            mSelectedBar.setVisibility(GONE);
            mBar.setVisibility(GONE);
        }else{
            mSelectedBar.setVisibility(VISIBLE);
            mBar.setVisibility(VISIBLE);
        }
        for (int i = 0; i < storeData.size(); i++) {

            final TextView text = new TextView(mContext);
            if (storeData.get(i) != null) {
                text.setText(storeData.get(i));
            }
            text.setBackgroundResource(R.drawable.unselect);
            mParams.setMargins(itemMargins, itemMargins, itemMargins, itemMargins);
            text.setLayoutParams(mParams);
            text.setGravity(Gravity.CENTER);
            text.setTextSize(textSize);
            text.setWidth(dotDefaultSize);
            text.setHeight(dotDefaultSize);
            final int finalI = i;
            text.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onClick(finalI);
                    }
                }
            });
            mDotLayout.addView(text);
        }

        addView(v);
        selected(0);
    }

    void animationViewSize(final View view, int width) {
        ValueAnimator anim = ValueAnimator.ofInt(view.getMeasuredWidth(), width);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = val;
                view.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(300);
        anim.start();
    }

    void changeColor(Drawable background, Integer color) {

        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background.mutate()).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background.mutate()).setColor(color);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background.mutate()).setColor(color);
        } else {
//            Log.w(TAG,"Not a valid background type");
        }
    }
}
