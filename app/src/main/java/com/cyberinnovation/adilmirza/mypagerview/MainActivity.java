package com.cyberinnovation.adilmirza.mypagerview;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyberinnovation.adilmirza.mypagerview.fragmets.FragmetOne;
import com.cyberinnovation.adilmirza.mypagerview.fragmets.FragmetThree;
import com.cyberinnovation.adilmirza.mypagerview.fragmets.FragmetTwo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    FragmetOne fragmetOne = new FragmetOne();
    FragmetTwo fragmetTwo = new FragmetTwo();
    FragmetThree fragmetThree = new FragmetThree();

    private ViewPager viewPager;
    private ArrayList<Fragment> listview;
    private ImageView imageView;
    private TextView t1, t2, t3;
    private int offset = 0;
    private int current_Index = 0;
    private int bmpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniitTextview();
        InitViewPager();
        InitImageView();
    }

    private void iniitTextview() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);

        t1.setOnClickListener(new MyClickListner(0));
        t2.setOnClickListener(new MyClickListner(1));
        t3.setOnClickListener(new MyClickListner(2));
    }

    private void InitViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vPager);
        listview = new ArrayList<Fragment>();

        listview.add(fragmetOne);
        listview.add(fragmetTwo);
        listview.add(fragmetThree);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        viewPager.setAdapter(new MainFragmentPagerAdapter(fragmentManager, listview));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.cursor);
        bmpw = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 3 - bmpw) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);
    }

    public class MyClickListner implements View.OnClickListener {
        private int index = 0;

        public MyClickListner(int index) {
            this.index = index;
        }

        //
        @Override
        public void onClick(View v) {

            viewPager.setCurrentItem(index);
        }


    }

    public class MyPagerAdapter extends PagerAdapter {

        public List<View> myListView;

        public MyPagerAdapter(List<View> myListView) {
            this.myListView = myListView;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(myListView.get(position));
        }

        @Override
        public void finishUpdate(ViewGroup container) {

        }

        @Override
        public int getCount() {
            return myListView.size();
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(myListView.get(position), 0);

            return myListView.get(position);
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        int one, two;

        @Override
        public void onPageSelected(int arg0) {
            one = offset * 2 + bmpw;
            two = one * 2;
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (current_Index == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (current_Index == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (current_Index == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (current_Index == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (current_Index == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (current_Index == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            current_Index = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            imageView.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

}



