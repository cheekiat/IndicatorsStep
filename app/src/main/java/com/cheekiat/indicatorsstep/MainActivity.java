package com.cheekiat.indicatorsstep;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.cheekiat.indicatorsteplib.DotOnClickListener;
import com.cheekiat.indicatorsteplib.StepProgress;

public class MainActivity extends AppCompatActivity implements TestFragment.OnFragmentInteractionListener {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    StepProgress layoutProgress1, layoutProgress2, layoutProgress3, layoutProgress4;
    private StepProgress layoutProgress5,layoutProgress6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layoutProgress1 = (StepProgress) findViewById(R.id.indicators_progress);
        layoutProgress2 = (StepProgress) findViewById(R.id.step_progress);
        layoutProgress3 = (StepProgress) findViewById(R.id.step_progress_1);
        layoutProgress4 = (StepProgress) findViewById(R.id.step_progress_4);
        layoutProgress5 = (StepProgress) findViewById(R.id.step_progress_5);
        layoutProgress6 = (StepProgress) findViewById(R.id.step_progress_6);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        layoutProgress1.setupWithViewPager(mPager);
        layoutProgress2.setupWithViewPager(mPager);
        layoutProgress3.setupWithViewPager(mPager);
        layoutProgress4.setupWithViewPager(mPager);
        layoutProgress5.setupWithViewPager(mPager);
        layoutProgress6.setupWithViewPager(mPager);

        for (int i = 0; i < layoutProgress3.getDotTextViews().size(); i++) {
            layoutProgress3.getDotTextViews().get(i).setText("" + (i + 1));
        }

        layoutProgress3.setDotsOnClickListener(new DotOnClickListener() {
            @Override
            public void onClick(int position) {
                mPager.setCurrentItem(position);
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new TestFragment().newInstance(position, null);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
