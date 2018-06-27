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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import notworry.rj.edu.notworry.Entity.Groups;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Activity.AddGroupActivity;
import notworry.rj.edu.notworry.Ui.Activity.GroupDetailPage;
import notworry.rj.edu.notworry.Ui.Activity.PlaymateTopActivity;
import notworry.rj.edu.notworry.Ui.Adapter.PlayMateAdapter;
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
 * Created by www15 on 2018/5/22.
 */

public class PlayMateFragment extends Fragment {
    ListView palymates = null;
    private View rootView = null;
    private OkHttpClient okHttpClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        okHttpClient = new OkHttpClient();
        //防止多次加载
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.playmatefragment_page,
                    container, false);
            palymates = rootView.findViewById(R.id.lv_playmates);
        }


        ImageView addgroup = rootView.findViewById(R.id.im_addgroup);
        //加载信息
        getGroupData();

        //从队伍信息页面跳转到创建队伍页面
        addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rootView.getContext(), PlaymateTopActivity.class);
                startActivity(intent);
            }
        });

        //点击跳转到队伍详情页面
         palymates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent();
                 intent.setClass (rootView.getContext (), GroupDetailPage.class);
                 Groups group = (Groups)parent.getItemAtPosition(position);
                 intent.putExtra("groupId",group.getGroupId());
                 selectGroups (group.getGroupId()+"");
                 Log.e ("ssssssssssssssss",group.getNeedNum ()+"");
                 startActivityForResult (intent,1);
             }
         }) ;

        final ImageView search = rootView.findViewById (R.id.search);
        search.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                TextView search_text = rootView.findViewById (R.id.search_text);
                search(search_text.getText ().toString ());
            }
        });
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

    //获取从服务器传过来的数据
    public void getGroupData(){

        //从服务器获取数据
        Request request = new Request.Builder()
                .url(Constant.BASE_URL+"SearchGroup")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String spotsStr = response.body().string();
                Log.e("aaaaaaaaaaa",spotsStr+"............");
                Gson gson = new Gson();
                Type type = new TypeToken<List<Groups>>(){}.getType();

                List<Groups> groupList = gson.fromJson(spotsStr,type);
                final PlayMateAdapter adapter = new PlayMateAdapter(rootView.getContext(),
                        R.layout.playmate_item, groupList);
                palymates.post(new Runnable() {
                    @Override
                    public void run() {
                        palymates.setAdapter(adapter);
                    }
                });
            }
        });
    }

    public void selectGroups(String groupId) {
        OkHttpClient okHttpClient = new OkHttpClient ();
        RequestBody requestBody = new FormBody.Builder ()
                .add ("groupId", groupId + "")
                .build ();
        Request request = new Request.Builder ()
                .url (Constant.BASE_URL + "SelectGroups")
                .post (requestBody)
                .build ();
        Call call = okHttpClient.newCall (request);
        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String Str = response.body ().string ();
                Log.e ("sssssss", Str);
                Gson gson = new Gson ();
                Groups groups = gson.fromJson (Str, Groups.class);
                usermsg.setGroups (groups);
                Log.e ("bbbbbbbbb", usermsg.getGroups ().getGroupName ());
            }
        });
    }

    public  void search(String name){
        RequestBody requestBody = new FormBody.Builder ()
                .add ("name",name)
                .build ();
        Request request = new Request.Builder ()
                .url (Constant.BASE_URL + "SearchGroupByName")
                .post (requestBody)
                .build ();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String spotsStr = response.body().string();
                Log.e("aaaaaaaaaaa",spotsStr+"............");
                Gson gson = new Gson();
                Type type = new TypeToken<List<Groups>>(){}.getType();

                List<Groups> groupList = gson.fromJson(spotsStr,type);
                final PlayMateAdapter adapter = new PlayMateAdapter(rootView.getContext(),
                        R.layout.playmate_item, groupList);
                palymates.post(new Runnable() {
                    @Override
                    public void run() {
                        palymates.setAdapter(adapter);
                    }
                });
            }
        });
    }
}
