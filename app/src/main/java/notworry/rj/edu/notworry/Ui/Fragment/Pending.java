package notworry.rj.edu.notworry.Ui.Fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;


import notworry.rj.edu.notworry.Entity.Orderdetails;
import notworry.rj.edu.notworry.Entity.Orders;
import notworry.rj.edu.notworry.Entity.Users;
import notworry.rj.edu.notworry.R;

import notworry.rj.edu.notworry.Ui.Activity.Order_DetailActivity;
import notworry.rj.edu.notworry.Ui.Adapter.PendingAdapter;
import notworry.rj.edu.notworry.Utils.Constant;
import notworry.rj.edu.notworry.Utils.usermsg;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 庆伟 on 2018/5/23.
 */

public class Pending extends android.support.v4.app.Fragment {
    private View rootView = null;
    private ListView listView;
    private OkHttpClient okHttpClient;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.order_pending, container, false);

        load();
        listView = rootView.findViewById(R.id.lv_pending);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                Orders orders = (Orders) listView.getItemAtPosition(position);
                Users users = orders.getUsers();
                Orderdetails orderdetails = orders.getOrderdetails();

                Intent intent = new Intent(rootView.getContext(), Order_DetailActivity.class);
                intent.putExtra("riseTime",orders.getRiseTime()+"");
                intent.putExtra("userName",users.getRealName()+"");
                intent.putExtra("phoneNum",users.getPhoneNum()+"");
                intent.putExtra("price",orderdetails.getPrice()+"");
                intent.putExtra("spotId",orders.getSpot ().getSpotId()+"");
                startActivity(intent);
            }
        });




        return rootView;

    }
    public void load() {

        okHttpClient = new OkHttpClient();
        String userId = usermsg.getUser().getUserId()+"";
        Log.e("userId", userId);
        RequestBody requestBody = new FormBody.Builder()
                .add("userId", userId).build();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(Constant.BASE_URL + "GetOrders")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String orderStr = response.body().string();
                Log.e("userId",orderStr);
                Gson gson = new Gson();
                Type type = new TypeToken<List<Orders>>() {
                }.getType();
                List<Orders> orderList = gson.fromJson(orderStr, type);
                List<Orders> list = new ArrayList<>();
                for (Orders orders:orderList){
                    if (orders.getIspay()==0){
                        list.add(orders);
                    }
                }
                final PendingAdapter adapter = new PendingAdapter(rootView.getContext(),R.layout.order_item,list);

                listView = rootView.findViewById(R.id.lv_pending);
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(adapter);
                    }
                });
            }
        });
    }

}
