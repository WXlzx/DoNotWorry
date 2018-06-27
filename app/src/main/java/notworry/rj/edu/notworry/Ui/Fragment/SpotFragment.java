package notworry.rj.edu.notworry.Ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import notworry.rj.edu.notworry.Entity.Spots;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Activity.DetailPage;
import notworry.rj.edu.notworry.Ui.Activity.SpotSearch;
import notworry.rj.edu.notworry.Ui.Adapter.SpotAdapter;
import notworry.rj.edu.notworry.Utils.Constant;
import notworry.rj.edu.notworry.Utils.GlideImageLoader;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by www15 on 2018/5/22.
 */

public class SpotFragment extends Fragment {
    ListView spot_List = null;
    private List<Spots> data = new ArrayList<>();
    private List<String> images = new ArrayList<String>();
    private View rootView = null;
    private OkHttpClient okHttpClient;
    private String BASE_URL = Constant.BASE_URL;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //防止多次加载
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {

            rootView = inflater.inflate(R.layout.spotfragment_page,
                    container, false);

            //初始化
            images.add(BASE_URL+"1.jpg");
            images.add(BASE_URL+"2.jpg");
            images.add(BASE_URL+"3.jpg");
            images.add(BASE_URL+"4.jpg");

            Banner banner = (Banner) rootView.findViewById(R.id.banner);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(images);
            //banner设置方法全部调用完毕时最后调用
            banner.start();

            //加载景点
            load();
            toSearch();
            return rootView;
        }
        RefreshLayout refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshFooter(new BallPulseFooter(rootView.getContext()).setSpinnerStyle(SpinnerStyle.Scale));

        refresh();


        return rootView;
    }

    public void refresh() {
        RefreshLayout refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    public void toSearch() {
        ImageView search = rootView.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rootView.getContext(), SpotSearch.class);
                startActivity(intent);
            }
        });
    }
    public void load(){
        okHttpClient = new OkHttpClient();
        //从服务器获取数据
        Request request = new Request.Builder()
                .url(BASE_URL+"SearchSpots")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String spotsStr = response.body().string();
                Log.e("aaaaaaaaaaa",spotsStr);
                Gson gson = new Gson();
                Type type = new TypeToken<List<Spots>>(){}.getType();

                 data = gson.fromJson(spotsStr,type);
                final SpotAdapter adapter = new SpotAdapter(rootView.getContext(),
                        R.layout.spot_item,data);
                spot_List = rootView.findViewById(R.id.spot_view);
                spot_List.post(new Runnable() {
                    @Override
                    public void run() {
                        spot_List.setAdapter(adapter);
                    }
                });
            }
        });
    }
}
