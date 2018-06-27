package notworry.rj.edu.notworry.Ui.Activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import notworry.rj.edu.notworry.R;

public class Welcome extends Activity {
    private final long SPLASH_LENGTH = 2000;
    Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farst_img);

        //定义一个setting记录APP是几次启动
        SharedPreferences setting = getSharedPreferences("com.example.myapplication", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {// 第一次则跳转到欢迎页面
            setting.edit().putBoolean("FIRST", false).commit();
            tiaozhuanzhu();
        } else {//如果是第二次启动则直接跳转到主页面
            tiaozhuanfu();
        }
    }

    public void tiaozhuanzhu(){
        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

            public void run() {
                Intent intent = new Intent(Welcome.this, LoginOnce.class);
                startActivity(intent);
                finish();
            }
        }, 0);//2秒后跳转至应用主界面MainActivity
    }

    public void tiaozhuanfu(){
        handler.postDelayed(new Runnable() {//使用handler的postDelayed实现延时跳转

            public void run() {
                Intent intent = new Intent(Welcome.this, SpActivity.class);
                startActivity(intent);
                finish();
            }
        }, 0);
    }
}