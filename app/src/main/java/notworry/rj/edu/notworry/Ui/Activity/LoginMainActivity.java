package notworry.rj.edu.notworry.Ui.Activity;


import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
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

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener {

    private String isLogin = "";
    private OkHttpClient okHttpClient;
    private String BASE_URL = Constant.BASE_URL;

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.sms)
    FloatingActionButton sms;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        //去掉标题栏
        requestWindowFeature (Window.FEATURE_NO_TITLE);

        setContentView (R.layout.login_activity_main);
        ButterKnife.bind (this);
        okHttpClient = new OkHttpClient ();
        final Button btnnum = findViewById (R.id.bt_go);

        btnnum.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Explode explode = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    explode = new Explode ();
                    explode.setDuration (500);

                    getWindow ().setExitTransition (explode);
                    getWindow ().setEnterTransition (explode);
                }

                //初始化监听器
                final EditText editname = findViewById (R.id.et_username);
                final EditText editpass = findViewById (R.id.et_password);
                //获取监听器内容
                String username = editname.getText ().toString ();
                String password = editpass.getText ().toString ();

                if (username.equals ("") || password.equals ("")) {
                    Toast.makeText (LoginMainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show ();
                } else {
                    //获取数据库内容进行判断
                    Login (username, password);
                    if (isLogin.equals ("登录失败")) {
                        Toast.makeText (LoginMainActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show ();
                        isLogin = "";
                    }
                }
            }
        });
    }


    @OnClick({R.id.fab, R.id.sms})
    public void onClick(View view) {
        switch (view.getId ()) {
            case R.id.fab:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow ().setExitTransition (null);
                    getWindow ().setEnterTransition (null);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation (this, fab, fab.getTransitionName ());
                    startActivity (new Intent (this, RegisterActivity.class), options.toBundle ());
                } else {
                    startActivity (new Intent (this, RegisterActivity.class));
                }
                break;
//            case R.id.bt_go:
//
//
//                break;
            case R.id.sms:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow ().setExitTransition (null);
                    getWindow ().setEnterTransition (null);
                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation (this, sms, sms.getTransitionName ());
                    startActivity (new Intent (this, SMSActivity.class), options.toBundle ());
                } else {
                    startActivity (new Intent (this, SMSActivity.class));
                }
                break;
        }
    }

    public void Login(String username, String password) {
        RequestBody requestBody = new FormBody.Builder ()
                .add ("username", username)
                .add ("password", password)
                .build ();
        Request request = new Request.Builder ()
                .post (requestBody)
                .url (BASE_URL + "Login")
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
                    Intent i2 = new Intent (LoginMainActivity.this, MainActivity.class);
                    startActivity (i2);

                } else {
                    isLogin = "登录失败";
                }
            }
        });

    }
}
