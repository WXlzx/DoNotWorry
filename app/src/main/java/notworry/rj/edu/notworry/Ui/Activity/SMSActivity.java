package notworry.rj.edu.notworry.Ui.Activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import notworry.rj.edu.notworry.Entity.Users;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Utils.Constant;
import notworry.rj.edu.notworry.Utils.usermsg;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SMSActivity extends AppCompatActivity {
    private int recLen = 31;
    private String isSend = "";
    private String isLogin = "";
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.sms)
    CardView sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        //去掉标题栏
        requestWindowFeature (Window.FEATURE_NO_TITLE);

        setContentView (R.layout.activity_sms);
        ButterKnife.bind (this);
        RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.sms_main);
        mainLayout.setBackgroundResource(R.drawable.bg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation ();
        }
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                animateRevealClose ();
            }
        });
    }

    private void ShowEnterAnimation() {
        Transition transition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition = TransitionInflater.from (this).inflateTransition (R.transition.fabtransition);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow ().setSharedElementEnterTransition (transition);
            }

            transition.addListener (new Transition.TransitionListener () {
                @Override
                public void onTransitionStart(Transition transition) {
                    sms.setVisibility (View.GONE);
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        transition.removeListener (this);
                    }
                    animateRevealShow ();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }


            });
        }
    }

    public void animateRevealShow() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal (sms, sms.getWidth () / 2, 0, fab.getWidth () / 2, sms.getHeight ());
        }
        mAnimator.setDuration (500);
        mAnimator.setInterpolator (new AccelerateInterpolator ());
        mAnimator.addListener (new AnimatorListenerAdapter () {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd (animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                sms.setVisibility (View.VISIBLE);
                super.onAnimationStart (animation);
                //初始化监听器
                final EditText editphone = findViewById (R.id.et_phone);
                final EditText editver = findViewById (R.id.et_num);
                final Button btnnum = findViewById (R.id.bt_num);
                Button btnnext = findViewById (R.id.bt_sms_go);
                //发送验证码
                btnnum.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        //发送验证码
                        String phone = editphone.getText ().toString ();
                        sms (phone);
                        Toast.makeText (SMSActivity.this, "验证码已发送，注意查收", Toast.LENGTH_SHORT).show ();
                        // 设置一分钟内按钮不可点击
                        btnnum.setEnabled (false);
                        Log.e ("SMSActivity1", "设置不可点击");
                        final Timer timer = new Timer ();
                        Log.e ("SMSActivity1", "新建timer线程");
                        timer.schedule (new TimerTask () {
                            @Override
                            public void run() {
                                final Button btnnum = findViewById (R.id.bt_num);
                                runOnUiThread (new Runnable () {      // UI thread
                                    @Override
                                    public void run() {
                                        recLen--;
                                        btnnum.setText ("重新发送:" + recLen + "s");
                                        if (recLen <= 0) {
                                            btnnum.setText ("获取验证码");
                                            btnnum.setEnabled (true);
                                            recLen = 31;
                                            timer.cancel ();
                                            Log.e ("SMSActivity1", "线程结束");
                                        }
                                    }
                                });
                            }

                        }, 1000, 1000);       // timeTask

                    }
                });
                //发送验证码
                btnnext.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {

                        String num = editver.getText ().toString ();
                        //判断验证码是否为该手机号验证码phone.num是否等于num
                        if (num.equals (isSend)) {
                            Toast.makeText (SMSActivity.this, "通过验证", Toast.LENGTH_SHORT).show ();
                            String phone = editphone.getText ().toString ();
                            Login (phone);
                            if (isLogin.equals ("登录失败")) {
                                isLogin = "";
                            }

                            animateRevealClose ();

                        } else {
                            Toast.makeText (SMSActivity.this, "验证码错误", Toast.LENGTH_SHORT).show ();
                            editver.setText ("");
                        }
                    }
                });
            }
        });
        mAnimator.start ();
    }

    public void animateRevealClose() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal (sms, sms.getWidth () / 2, 0, sms.getHeight (), fab.getWidth () / 2);
        }
        mAnimator.setDuration (500);
        mAnimator.setInterpolator (new AccelerateInterpolator ());
        mAnimator.addListener (new AnimatorListenerAdapter () {
            @Override
            public void onAnimationEnd(Animator animation) {
                sms.setVisibility (View.INVISIBLE);
                super.onAnimationEnd (animation);
                fab.setImageResource (R.drawable.plus);
                SMSActivity.super.onBackPressed ();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart (animation);
            }
        });
        mAnimator.start ();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose ();
    }

    public void sms(String phone) {
        OkHttpClient okHttpClient = new OkHttpClient ();
        RequestBody requestBody = new FormBody.Builder ()
                .add ("phone", phone)
                .build ();
        final Request request = new Request.Builder ()
                .post (requestBody)
                .url (Constant.BASE_URL + "Sms")
                .build ();

        Call call = okHttpClient.newCall (request);

        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace ();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                isSend = response.body ().string ();
            }
        });
    }

    public void Login(String phone) {
        OkHttpClient okHttpClient = new OkHttpClient ();
        RequestBody requestBody = new FormBody.Builder ()
                .add ("phone", phone)
                .build ();
        Request request = new Request.Builder ()
                .post (requestBody)
                .url (Constant.BASE_URL + "SelsectUser")
                .build ();
        Call call = okHttpClient.newCall (request);

        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace ();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String loginStr = response.body ().string ();
                Gson gson = new Gson ();
                Users user = gson.fromJson (loginStr, Users.class);
                if (user != null) {
                    usermsg.setUser (user);
                    Intent i2 = new Intent (SMSActivity.this, MainActivity.class);
                    startActivity (i2);

                } else {
                    isLogin = "登录失败";
                }
            }
        });

    }
}