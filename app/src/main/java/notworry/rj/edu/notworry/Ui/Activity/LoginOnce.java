package notworry.rj.edu.notworry.Ui.Activity;

//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageInfo;
//import android.os.Bundle;
//import android.os.Handler;
//import android.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import notworry.rj.edu.notworry.R;


/**
 * Created by 小波 on 2018/5/23.
 */

public class LoginOnce extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);

        // 初始化页面
        initViews();

        // 初始化底部小点
        initDots();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.what_new_one, null));
        views.add(inflater.inflate(R.layout.what_new_two, null));
        views.add(inflater.inflate(R.layout.what_new_three, null));
        //views.add(inflater.inflate(R.layout.what_new_four, null));

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);

        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }
    public class ViewPagerAdapter extends PagerAdapter {

        // 界面列表
        private List<View> views;
        private Activity activity;

        private static final String SHAREDPREFERENCES_NAME = "first_pref";

        public ViewPagerAdapter(List<View> views, Activity activity) {
            this.views = views;
            this.activity = activity;
        }

        // 销毁arg1位置的界面
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        // 获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        // 初始化arg1位置的界面
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            if (arg1 == views.size() - 1) {
                ImageView mStartWeiboImageButton = (ImageView) arg0
                        .findViewById(R.id.iv_start);
                mStartWeiboImageButton.setOnClickListener(new View.OnClickListener() {


                    public void onClick(View v) {
                        // 设置已经引导
                        setGuided();
                        goHome();

                    }

                });
            }
            return views.get(arg1);
        }

        private void goHome() {
            // 跳转
            Intent intent = new Intent(LoginOnce.this, LoginMainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }

        /**
         *
         * method desc：设置已经引导过了，下次启动不用再次引导
         */
        private void setGuided() {
            SharedPreferences preferences = activity.getSharedPreferences(
                    SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            // 存入数据
            editor.putBoolean("isFirstIn", false);
            // 提交修改
            editor.commit();
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }


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
}


//public class Welcome extends Activity {
//    private final long SPLASH_LENGTH = 2000;
//    Handler handler = new Handler();
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.farst_img);
//
//        //定义一个setting记录APP是几次启动！！！
//        SharedPreferences setting = getSharedPreferences("com.example.myapplication", 0);
//        Boolean user_first = setting.getBoolean("FIRST", true);
//        if (user_first) {// 第一次则跳转到欢迎页面
//            setting.edit().putBoolean("FIRST", false).commit();
//            tiaozhuanzhu();
//        } else {//如果是第二次启动则直接跳转到主页面
//            tiaozhuanfu();
//        }
//    }
//
//    public void tiaozhuanzhu(){
//        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转
//
//            public void run() {
//                Intent intent = new Intent(Welcome.this, LoginFirst.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_LENGTH);//2秒后跳转至应用主界面MainActivity
//    }
//
//    public void tiaozhuanfu(){
//        handler.postDelayed(new Runnable() {//使用handler的postDelayed实现延时跳转
//
//            public void run() {
//                Intent intent = new Intent(Welcome.this, LoginMainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_LENGTH);//2秒后跳转至应用欢迎界面
//    }
//}