package notworry.rj.edu.notworry.Ui.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import notworry.rj.edu.notworry.Entity.Orderdetails;
import notworry.rj.edu.notworry.Entity.Orders;
import notworry.rj.edu.notworry.Entity.Spots;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Adapter.OrderAdapter;
import notworry.rj.edu.notworry.Utils.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Order_DetailActivity extends AppCompatActivity {
private OkHttpClient okHttpClient;
private Spots spots = new Spots();
    private Handler handler;
private TextView tv1=null;
    private TextView tv2=null;
    private TextView tv3=null;
    private TextView tv4=null;
    private TextView tv5=null;
    private TextView tv6=null;
    private TextView tv7=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_detail);
        Intent intent = getIntent();
        String riseTime = intent.getStringExtra("riseTime");
        String userName = intent.getStringExtra("userName");
        String phoneNum = intent.getStringExtra("phoneNum");
        String price = intent.getStringExtra("price");
        String spotId = intent.getStringExtra("spotId");
        tv1 = findViewById(R.id.order_detail_detail_spotname);

        tv2 = findViewById(R.id.order_detail_detail_opentime);

        tv3 = findViewById(R.id.order_detail_detail_playtime);

        tv4 = findViewById(R.id.order_detail_detail_price);
        tv4.setText(price);
        tv5 = findViewById(R.id.order_detail_detail_username);
        tv5.setText(userName);
        tv6 = findViewById(R.id.order_detail_detail_phonenum);
        tv6.setText(phoneNum);
        tv7 = findViewById(R.id.order_detail_detail_time);
        tv7.setText(riseTime);

        ImageView back = findViewById (R.id.back);
        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish ();
            }
        });
        handler = new Handler();
        load(spotId);
    }
    public void load(String id){
        okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("spotId", id).build();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(Constant.BASE_URL + "GetOrderDetail")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String spotStr = response.body().string();
                Log.e("orderStr",spotStr);
                Gson gson = new Gson();

                spots = gson.fromJson(spotStr, Spots.class);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText(spots.getSpotName());
                        tv2.setText(spots.getSpotDetail().getOpeningTime()+"");
                        tv3.setText(spots.getSpotDetail().getPlayTime()+"");
                    }
                });

            }
        });
    }

}
