package notworry.rj.edu.notworry.Ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import notworry.rj.edu.notworry.Entity.Groups;
import notworry.rj.edu.notworry.Entity.Spots;
import notworry.rj.edu.notworry.Entity.Users;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Adapter.UserGroupsAdapter;
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
 * Created by 肖明臣 on 2018/6/20.
 */

public class UserAddGroupMsg extends AppCompatActivity {
    private ListView listView;
    private List<Groups> groupList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.user_add_group_msg_listview);

        listView = findViewById (R.id.user_add_group_msg_listview);
        Users user = usermsg.getUser ();
        int userId = user.getUserId ();

        OkHttpClient okHttpClient = new OkHttpClient ();
        RequestBody requestBody = new FormBody.Builder ().add ("userId", userId + "").build ();
        final Request request = new Request.Builder ().post (requestBody).
                url (Constant.BASE_URL + "UserAddGroupMsg").build ();
        Call call = okHttpClient.newCall (request);
        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String groupListStr = response.body ().string ();
                Log.e ("strstrstrstr",groupListStr+"....");
                Gson gson = new Gson ();
                Type type = new TypeToken<List<Groups>> () {
                }.getType ();
                groupList = gson.fromJson (groupListStr, type);
                final UserGroupsAdapter adapter = new UserGroupsAdapter (UserAddGroupMsg.this, groupList);
                listView.post (new Runnable () {
                    @Override
                    public void run() {
                        listView.setAdapter (adapter);
                    }
                });
            }
        });

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent ();
                intent.setClass (UserAddGroupMsg.this,GroupDetails.class);
                getGroup (groupList.get (position).getGroupId ()+"");
                startActivity (intent);
            }
        });
    }

    public  void getGroup(String groupId){
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
}
