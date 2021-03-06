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
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.cheekiat.indicatorsteplib.Mode.INDICATORS;
import static com.cheekiat.indicatorsteplib.Mode.STEP;

/**
 * Created by Chee Kiat on 15/05/2017.
 */

public class StepProgress extends LinearLayout {

    private Context mContext;
    private List<String> storeData = new ArrayList<>();
    private int dotDefaultSize;
    private float dotSelectedSize;
    private int itemMargins;
    private int mode;
    private int barHeight;
    private int textSize;
    private Integer selectedTextColor, unselectTextColor;//, selectedColor, unselectColor;
    private Drawable mSelected, mUnselect;
    private DotOnClickListener onClickListener;
    private float unselectSize;
    private View v;
    private LinearLayout mDotLayout;
    private View mSelectedBar;
    private int unselectBarColor;
    private int selectedBarColor;
    private List<TextView> mDotTexts = new ArrayList<>();

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
                mSelected = a.getDrawable(R.styleable.StepUi_selected);
                mUnselect = a.getDrawable(R.styleable.StepUi_unselect);
                selectedBarColor = a.getColor(R.styleable.StepUi_selectedBarColor, Color.parseColor("#F3AD33"));
                unselectBarColor = a.getColor(R.styleable.StepUi_unselectBarColor, Color.parseColor("#D6D6D6"));
                itemMargins = a.getDimensionPixelSize(R.styleable.StepUi_itemMargins, 15);
                dotDefaultSize = a.getDimensionPixelSize(R.styleable.StepUi_dotDefaultSize, 60);
                storeSelectedSize = a.getDimensionPixelSize(R.styleable.StepUi_dotSelectedSize, 90);
                textSize = a.getDimensionPixelSize(R.styleable.StepUi_textSize, 10);
            } finally {
                a.recycle();
            }
        }

        if (mode == Mode.STEP_WITH_BAR.getValue()) {

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
        if (mode == Mode.INDICATORS.getValue()) {
            for (int j = 0; j < mDotLayout.getChildCount(); j++) {
                View mView = mDotLayout.getChildAt(j);
                if (position == j) {
                    mView.setBackgroundResource(R.drawable.selected);
                    mView.animate().scaleX(dotSelectedSize);
                    mView.animate().scaleY(dotSelectedSize);
                    if (mSelected != null) {
                        mView.setBackground(mSelected);
                    } else {
                        changeColor(mView.getBackground(), selectedBarColor);
                    }
                } else {
                    mView.setBackgroundResource(R.drawable.unselect);
                    mView.animate().scaleX(unselectSize);
                    mView.animate().scaleY(unselectSize);
                    if (mUnselect != null) {
                        mView.setBackground(mUnselect);
                    } else {
                        changeColor(mView.getBackground(), unselectBarColor);
                    }

                }
                mView.invalidate();
            }
        } else {

            for (int j = 0; j < mDotLayout.getChildCount(); j++) {
                TextView mView = (TextView) mDotLayout.getChildAt(j);
                if (position + 1 > j) {
                    mView.setBackgroundResource(R.drawable.selected);
                    mView.setTextColor(selectedTextColor);
                    if (mSelected != null) {
                        mView.setBackground(mSelected);
                    } else {
                        changeColor(mView.getBackground(), selectedBarColor);
                    }
                } else {
                    mView.setBackgroundResource(R.drawable.unselect);
                    mView.setTextColor(unselectTextColor);
                    if (mUnselect != null) {
                        mView.setBackground(mUnselect);
                    } else {
                        changeColor(mView.getBackground(), unselectBarColor);
                    }
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

    public List<TextView> getDotTextViews() {
        if (mDotLayout == null) {
            return new ArrayList<>();
        }
        mDotTexts.clear();
        for (int j = 0; j < mDotLayout.getChildCount(); j++) {
            TextView mView = (TextView) mDotLayout.getChildAt(j);
            mDotTexts.add(mView);
        }

        return mDotTexts;
    }

    public void setDotCount(int count) {

        if (storeData != null) {
            removeAllViews();
            storeData.clear();
        }
        for (int i = 0; i < count; i++) {

            storeData.add(null);
        }
        initData(storeData);
    }

    private void initData(List<String> storeData) {

        LayoutParams mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        v = LayoutInflater.from(getContext()).inflate(R.layout.layout, null);
        mDotLayout = (LinearLayout) v.findViewById(R.id.dot_layout);
        View mBar = (View) v.findViewById(R.id.bar);
        mSelectedBar = (View) v.findViewById(R.id.selected_bar);
        mSelectedBar.setBackgroundColor(selectedBarColor);
        mBar.setBackgroundColor(unselectBarColor);
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

        if (mode == Mode.STEP_WITH_BAR.getValue()) {
            mSelectedBar.setVisibility(VISIBLE);
            mBar.setVisibility(VISIBLE);
        } else {
            mSelectedBar.setVisibility(GONE);
            mBar.setVisibility(GONE);
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

    public void setupWithViewPager(ViewPager mViewPager) {
        if (mViewPager != null) {
            if (mViewPager.getAdapter() != null) {
                setDotCount(mViewPager.getAdapter().getCount());
            }

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    selected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }
}
