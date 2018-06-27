package notworry.rj.edu.notworry.Ui.Activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.BindView;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Utils.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.cv_add)
    CardView cvAdd;

    private int recLen = 31;
    private boolean yanzhengma = false;
    private String isRegister = "";
    private String sms="";
    private OkHttpClient okHttpClient;
    private String BASE_URL = Constant.BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        //去掉标题栏
        requestWindowFeature (Window.FEATURE_NO_TITLE);

        setContentView (R.layout.activity_register);
        ButterKnife.bind (this);
        RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.regist_main);
        mainLayout.setBackgroundResource(R.drawable.bg);
        //初始化监听器
        okHttpClient = new OkHttpClient ();
        final EditText editname = findViewById (R.id.et_username);
        final EditText editpass = findViewById (R.id.et_password);
        final EditText editrepa = findViewById (R.id.et_repeatpassword);
        final EditText editphone = findViewById (R.id.et_phone);
        final EditText editver = findViewById (R.id.et_verification);
        final Button btnnum = findViewById (R.id.bt_num);
        Button btnnext = findViewById (R.id.bt_go);
        //设置条件限制
        //用户名判长度
        editname.setOnFocusChangeListener (new View.
                OnFocusChangeListener () {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // 此处为失去焦点时的处理内容
                    String username = editname.getText ().toString ();
                    if (username.length () > 8) {
                        Toast.makeText (RegisterActivity.this, "用户名不可超过8位", Toast.LENGTH_SHORT).show ();
                        editname.setText ("");
                    }
                }
            }
        });
        //确认密码判相等
        editrepa.setOnFocusChangeListener (new View.
                OnFocusChangeListener () {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // 此处为失去焦点时的处理内容
                    String password = editpass.getText ().toString ();
                    String repeatpassword = editrepa.getText ().toString ();
                    if (!password.equals (repeatpassword)) {
                        Toast.makeText (RegisterActivity.this, "两次密码不同，请确认", Toast.LENGTH_SHORT).show ();
                        editrepa.setText ("");
                    }
                }
            }
        });
        //密码判长度
        editpass.setOnFocusChangeListener (new View.
                OnFocusChangeListener () {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // 此处为失去焦点时的处理内容
                    String password = editpass.getText ().toString ();
                    if (password.length () > 8) {
                        Toast.makeText (RegisterActivity.this, "密码不可超过8位", Toast.LENGTH_SHORT).show ();
                        editpass.setText ("");
                    }
                }
            }
        });
        //电话判长度
        editphone.setOnFocusChangeListener (new View.
                OnFocusChangeListener () {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // 此处为失去焦点时的处理内容
                    String phone = editphone.getText ().toString ();
                    if (phone.length () != 11) {
                        Toast.makeText (RegisterActivity.this, "手机号码长度为11位", Toast.LENGTH_SHORT).show ();
                        editphone.setText ("");
                    }
                }
            }
        });
//        //验证码判断
//        editver.setOnFocusChangeListener (new View.
//                OnFocusChangeListener () {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    // 此处为失去焦点时的处理内容
//                    String verification = editver.getText ().toString ();
//                    //获取验证码，暂时用123代替
//
//
//                    String num = "123";
//                    if (verification .equals (num)) {
//                        Toast.makeText (RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show ();
//                        editver.setText ("");
//                    } else {
//                        yanzhengma = true;
//                    }
//
//                }
//            }
//        });
        //发送验证码
        btnnum.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                //发送验证码

                sms(editphone.getText ().toString ());
                Toast.makeText (RegisterActivity.this, "验证码已发送，注意查收", Toast.LENGTH_SHORT).show ();
                // 设置一分钟内按钮不可点击
                btnnum.setEnabled (false);
                Log.e ("RegisterActivity1", "设置不可点击");

                final Timer timer = new Timer ();
                Log.e ("RegisterActivity1", "新建timer线程");
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
                                    Log.e ("RegisterActivity1", "线程结束");
                                }
                            }
                        });
                    }

                }, 1000, 1000);       // timeTask

            }
        });
        //提交
        btnnext.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //获取监听器内容
                String username = editname.getText ().toString ();
                String password = editpass.getText ().toString ();
                String erpeatpassword = editrepa.getText ().toString ();
                String phone = editphone.getText ().toString ();
                String verification = editver.getText ().toString ();

                if (username.equals ("") || password.equals ("") || erpeatpassword.equals ("")
                        || phone.equals ("") || verification.equals ("")) {
                    Toast.makeText (RegisterActivity.this, "请继续完善信息", Toast.LENGTH_SHORT).show ();
                }
                if(!verification.equals (sms)){
                    Toast.makeText (RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show ();
                }
                else {
                    Register (username, password, phone);
                    if (isRegister.equals ("注册失败")) {
                        Toast.makeText (RegisterActivity.this, "用户名不可用", Toast.LENGTH_SHORT).show ();
                    }
                }
            }
        });
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Transition transition = null;
            transition = TransitionInflater.from (this).inflateTransition (R.transition.fabtransition);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow ().setSharedElementEnterTransition (transition);
                transition.addListener (new Transition.TransitionListener () {
                    @Override
                    public void onTransitionStart(Transition transition) {
                        cvAdd.setVisibility (View.GONE);
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
    }

    public void animateRevealShow() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal (cvAdd, cvAdd.getWidth () / 2, 0, fab.getWidth () / 2, cvAdd.getHeight ());
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
                cvAdd.setVisibility (View.VISIBLE);
                super.onAnimationStart (animation);
            }
        });
        mAnimator.start ();
    }

    public void animateRevealClose() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal (cvAdd, cvAdd.getWidth () / 2, 0, cvAdd.getHeight (), fab.getWidth () / 2);
        }
        mAnimator.setDuration (500);
        mAnimator.setInterpolator (new AccelerateInterpolator ());
        mAnimator.addListener (new AnimatorListenerAdapter () {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility (View.INVISIBLE);
                super.onAnimationEnd (animation);
                fab.setImageResource (R.drawable.plus);
                RegisterActivity.super.onBackPressed ();
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

    public void Register(String username, String password, String phonenum) {
        RequestBody requestBody = new FormBody.Builder ()
                .add ("username", username)
                .add ("password", password)
                .add ("phonenum", phonenum)
                .build ();
        final Request request = new Request.Builder ()
                .post (requestBody)
                .url (BASE_URL + "Register")
                .build ();
        Call call = okHttpClient.newCall (request);

        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace ();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String registerStr = response.body ().string ();
                isRegister = registerStr;
                if (registerStr.equals ("注册成功")) {
                    Intent intent = new Intent ();
                    intent.setClass (RegisterActivity.this, LoginMainActivity.class);
                    startActivity (intent);
                    animateRevealClose ();
                }
            }
        });
    }

    public void sms(String phone){
        RequestBody requestBody = new FormBody.Builder ()
                .add ("phone",phone)
                .build ();
        final Request request = new Request.Builder ()
                .post (requestBody)
                .url (BASE_URL + "Sms")
                .build ();
        Call call = okHttpClient.newCall (request);

        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace ();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sms = response.body ().string ();
            }
        });
    }
}