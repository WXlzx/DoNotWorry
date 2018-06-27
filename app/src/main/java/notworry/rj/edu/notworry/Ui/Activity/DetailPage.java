package notworry.rj.edu.notworry.Ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import notworry.rj.edu.notworry.Entity.RotateBean;
import notworry.rj.edu.notworry.Entity.SpotsDetails;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Adapter.RotateVpAdapter;
import notworry.rj.edu.notworry.Ui.Fragment.PlayMateFragment;
import notworry.rj.edu.notworry.Utils.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 肖明臣 on 2018/5/23.
 */
public class DetailPage extends AppCompatActivity {
    private int spotId;
    private String spotName;
    private String BASE_URL = Constant.BASE_URL;
    private static final int TIME = 3000;
    private ViewPager viewPager;
    private LinearLayout pointLl;// 轮播状态改变的小圆点容器
    private List<RotateBean> datas;
    private RotateVpAdapter vpAdapter;
    TextView detail_page_ticket_price;  // 门票价格
    TextView detail_page_travel_budget; //开始时间
    TextView detail_page_suggested_itinerary; //游玩时间
    Button detail_page_heel_tour;  //跟团游
    Button detail_page_team_tour; //组队游
    TextView detail_page_tv;  //景点详情

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpages_layout);
        viewPager = (ViewPager) findViewById(R.id.rotate_vp);
        pointLl = (LinearLayout) findViewById(R.id.rotate_point_container);
        detail_page_tv = findViewById(R.id.detail_page_tv);
        detail_page_ticket_price = findViewById(R.id.detail_page_ticket_price);
        detail_page_travel_budget = findViewById(R.id.detail_page_travel_budget);
        detail_page_suggested_itinerary = findViewById(R.id.detail_page_suggested_itinerary);



        detail_page_heel_tour = findViewById(R.id.detail_page_heel_tour);
        detail_page_team_tour = findViewById(R.id.detail_page_team_tour);

        //获取景点Id，发送到服务器端以用它来向数据库获取信息
        Intent intent = getIntent();
        spotId = intent.getIntExtra("SpotId",0);
        spotName = intent.getStringExtra ("SpotName");

        TextView sName = findViewById (R.id.detail_page_name);
        sName.setText (spotName);
        Log.e("LoginMainActivity22222", spotId+",,,,");

        //利用OkHttp传输spotId时  使用String类型
        addSpotsDetails(spotId);

        buildDatas();// 构造数据
        vpAdapter = new RotateVpAdapter(datas, this);
        viewPager.setAdapter(vpAdapter);
        // ViewPager的页数为int最大值,设置当前页多一些,可以上来就向前滑动
        // 为了保证第一页始终为数据的第0条 取余要为0,因此设置数据集合大小的倍数
        viewPager.setCurrentItem(datas.size() * 100);

        // 开始轮播
        handler = new Handler();
        startRotate();
        // 添加轮播小点
        addPoints();
        // 随着轮播改变小点
        changePoints();

        ImageView back=findViewById(R.id.detail_page_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailPage.this,MainActivity.class);
                i.putExtra ("id",0);
                startActivity (i);
            }
        });

        detail_page_heel_tour.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(DetailPage.this, MainActivity.class);
                mIntent.putExtra ("id",1);
                startActivity(mIntent);
            }
        });

        detail_page_team_tour.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(DetailPage.this, MainActivity.class);
                mIntent.putExtra ("id",1);
                startActivity(mIntent);
            }
        });

    }

    private void changePoints() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (isRotate) {
                    // 把所有小点设置为白色
                    for (int i = 0; i < datas.size(); i++) {
                        ImageView pointIv = (ImageView) pointLl.getChildAt(i);
                        pointIv.setImageResource(R.drawable.point_white);
                    }
                    // 设置当前位置小点为灰色
                    ImageView iv = (ImageView) pointLl.getChildAt(position % datas.size());
                    iv.setImageResource(R.drawable.point_grey);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 添加轮播切换小点
     */
    private void addPoints() {
        // 有多少张图加载多少个小点
        for (int i = 0; i < datas.size(); i++) {
            ImageView pointIv = new ImageView(this);
            pointIv.setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            pointIv.setLayoutParams(params);

            // 设置第0页小点的为灰色
            if (i == 0) {
                pointIv.setImageResource(R.drawable.point_grey);
            } else {
                pointIv.setImageResource(R.drawable.point_white);
            }
            pointLl.addView(pointIv);
        }
    }


    private Handler handler;
    private boolean isRotate = false;
    private Runnable rotateRunnable;

    /**
     * 开始轮播
     */
    private void startRotate() {
        rotateRunnable = new Runnable() {
            @Override
            public void run() {
                int nowIndex = viewPager.getCurrentItem();
                viewPager.setCurrentItem(++nowIndex);
                if (isRotate) {
                    handler.postDelayed(rotateRunnable, TIME);
                }
            }
        };
        handler.postDelayed(rotateRunnable, TIME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRotate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRotate = false;
    }

    private void buildDatas() {
        datas = new ArrayList<>();
        datas.add(new RotateBean(R.drawable.lunbo_image1));
        datas.add(new RotateBean(R.drawable.lunbo_image2));
        datas.add(new RotateBean(R.drawable.lunbo_image3));
        datas.add(new RotateBean(R.drawable.lunbo_image4));
    }

    //向服务器发送景点Id 用来获取景点详情内的数据
    public void addSpotsDetails(int sid){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("spotId",sid+"")
                .build();
        final Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL+"SearchSpotDetails")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String spotsDetailsStr = response.body().string();
                Gson gson = new Gson();

                SpotsDetails spotsDetails = gson.fromJson(spotsDetailsStr,SpotsDetails.class);

                detail_page_tv.setText(spotsDetails.getIntroduction());
                detail_page_ticket_price.setText(String.valueOf(spotsDetails.getPrice()));
                detail_page_travel_budget.setText(String.valueOf(spotsDetails.getOpeningTime()));
                detail_page_suggested_itinerary.setText(String.valueOf(spotsDetails.getPlayTime()));
            }
        });
    }

}