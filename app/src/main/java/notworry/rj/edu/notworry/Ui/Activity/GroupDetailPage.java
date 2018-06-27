package notworry.rj.edu.notworry.Ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import notworry.rj.edu.notworry.Entity.Groups;
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

/**
 * Created by 肖明臣 on 2018/6/7.
 */

public class GroupDetailPage extends Activity {
    private int neededCount;//改变后的  所需人数的值
    private int realCount;
    private int groupId;
    private int userId;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.group_detail);
        Intent intent = getIntent ();
        groupId = intent.getIntExtra ("groupId", 0);

        ImageView img = findViewById (R.id.group_pic);

        TextView groupdetail_name = findViewById (R.id.groupdetail_name);//队伍名称
        final TextView groupdetail_realNum = findViewById (R.id.groupdetail_realNum); //队伍总人数
        final TextView groupdetail_neededNum = findViewById (R.id.groupdetail_neededNum); //所需人数
        final Button login_group = findViewById (R.id.login_group);
        Button groupdetail_back = findViewById (R.id.groupdetail_back);
        ImageView imageView = findViewById (R.id.back);
        ImageView msg = findViewById (R.id.msg);

        imageView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GroupDetailPage.this,MainActivity.class);
                i.putExtra ("id",1);
                startActivity (i);
            }
        });

        msg.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
                intent.setClass (GroupDetailPage.this,ChatActivity.class);
                startActivity (intent);
            }
        });
        final int realNum = usermsg.getGroups ().getRealNum ();
        String groupDetailName = usermsg.getGroups ().getGroupName ();
        final int neededNum = usermsg.getGroups ().getNeedNum ();

        //获取当前User的ID
        Users user = usermsg.getUser ();
        userId = user.getUserId ();


        Glide.with (GroupDetailPage.this).load (Constant.BASE_URL+usermsg.getGroups ().getPicture ()).into (img);
        groupdetail_name.setText (groupDetailName);
        groupdetail_realNum.setText (realNum + "");
        groupdetail_neededNum.setText (neededNum + "");
        neededCount = neededNum;
        realCount = realNum;
        groupdetail_neededNum.setText (neededCount + "");
        //加入队伍
        login_group.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (login_group.getText ().equals ("加入队伍")) {
                    login_group.setText ("退出队伍");
                } else {
                    login_group.setText ("加入队伍");
                }

                String text = login_group.getText ().toString ();
                if (text.equals ("退出队伍")) {
                    if (neededCount > 0) {
                        neededCount--;
                        realCount++;
                        Toast.makeText (groupdetail_neededNum.getContext (), "加入成功!"
                                , Toast.LENGTH_SHORT).show ();
                        groupdetail_neededNum.setText (neededCount + "");
                        groupdetail_realNum.setText (realCount+"");
                        addGroupUser (userId + "");
                    } else {
                        Toast.makeText (groupdetail_neededNum.getContext (), "队伍成员已满,请选择其他队伍!"
                                , Toast.LENGTH_SHORT).show ();
                    }
                } else {
                    if (neededCount < realNum+neededCount) {
                        neededCount++;
                        realCount--;
                        Toast.makeText (groupdetail_neededNum.getContext (), "退出成功!"
                                , Toast.LENGTH_SHORT).show ();
                        removeGroupUser (userId + "");
                        groupdetail_neededNum.setText (neededCount + "");
                        groupdetail_realNum.setText (realCount+"");
                    }
                }
                String sendnum = groupdetail_neededNum.getText ().toString ();
                String realnum = groupdetail_realNum.getText ().toString ();
                sendNeedNum (sendnum,realnum);
            }
        });
        groupdetail_back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish ();
            }
        });
    }


    //将加入队伍的用户ID传到后台
    private void addGroupUser(String id) {
        OkHttpClient okHttpClient = new OkHttpClient ();
        RequestBody requestBody = new FormBody.Builder ()
                .add ("userId", id)
                .add ("groupId", groupId + "")
                .build ();
        Request request = new Request.Builder ()
                .url (Constant.BASE_URL + "AddGroupUser")
                .post (requestBody)
                .build ();
        Call call = okHttpClient.newCall (request);
        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body ().string ();
            }
        });
    }


//将退出队伍的用户ID传到后台

    private void removeGroupUser(String id) {
        OkHttpClient okHttpClient = new OkHttpClient ();
        RequestBody requestBody = new FormBody.Builder ()
                .add ("userId", id)
                .add ("groupId", groupId + "")
                .build ();
        Request request = new Request.Builder ()
                .url (Constant.BASE_URL + "RemoveGroupUser")
                .post (requestBody)
                .build ();
        Call call = okHttpClient.newCall (request);
        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body ().string ();
            }
        });
    }


    private void sendNeedNum(String needNum,String realNum) {
        OkHttpClient okHttpClient = new OkHttpClient ();
        RequestBody requestBody = new FormBody.Builder ()
                .add ("groupId",groupId+"")
                .add ("neededNum", needNum)
                .add ("realNum",realNum)
                .build ();
        Request request = new Request.Builder ()
                .url (Constant.BASE_URL + "ChangeNeededNum")
                .post (requestBody)
                .build ();
        Call call = okHttpClient.newCall (request);
        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body ().string ();
            }
        });
    }

}
