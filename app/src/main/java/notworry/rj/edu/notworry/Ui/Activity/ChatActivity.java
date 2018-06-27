package notworry.rj.edu.notworry.Ui.Activity;
/**
 * Created by 石灵明 on 2018/5/28.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.sql.Connection;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import notworry.rj.edu.notworry.Entity.ChatMsgEntity;
import notworry.rj.edu.notworry.Entity.Chatdetails;
import notworry.rj.edu.notworry.Entity.Users;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Service.SocketService;
import notworry.rj.edu.notworry.Ui.Adapter.ChatMsgListViewAdapter;
import notworry.rj.edu.notworry.Utils.Constant;
import notworry.rj.edu.notworry.Utils.usermsg;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static notworry.rj.edu.notworry.Utils.JDBCip.ip;


public class ChatActivity extends Activity {
    private String chatgroup_id;
    private String user_id;
    private String chat_time;

    private SharedPreferences sp1;
    private SharedPreferences sp3;
    String ChatListStr = null;
    private EditText editText;
    private Button sendButton;
    private String chatListJsonme;
    private ListView listView;
    private ChatMsgListViewAdapter adapter;
    private List<ChatMsgEntity> list;
    private MessageReceiver messageReceiver;
    String usersstr;
    private static Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main);

        //从上一级获取此用户user的信息，如userid groupid等关键信息
        getuserthing();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        editText = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.btn_send);
//        backButton = (Button)findViewById(R.id.btn_back);
        listView = (ListView) findViewById(R.id.listview);
        //他人发信息的监听
        initMessageReceiver();
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        list = new ArrayList<ChatMsgEntity>();
        adapter = new ChatMsgListViewAdapter(this, list);
        listView.setAdapter(adapter);
        //获取聊天历史

        getchat();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
        stopService(new Intent(this,SocketService.class));
    }

    //根据getuserthing中获取的userid查询数据库users，获取username等信息
//    private Users getUsers(String id) {
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
//        Gson gson1 = new Gson();
//        String jsonStr = gson1.toJson(id);//json数据
//        RequestBody body = RequestBody.create(JSON, jsonStr);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request1 = new Request.Builder()
//                .url(Constant.BASE_URL + "GetUsers2")
//                .post(body)
//                .build();
//        Call call1 = okHttpClient.newCall(request1);
//       try {
//            Response response1 = call1.execute();
//            usersstr = response1.body().string();
//        } catch (IOException e) {
//            Log.e("ChatActivity", "getusers2赋值出错");
//            e.printStackTrace();
//        }
//        Log.e("ChatActivity", "=======================usersstr========================"+usersstr);
//        Gson gson = new Gson();
//
//        Type type1 = new TypeToken<Users>() {}.getType();
//        Users user = gson.fromJson(usersstr, type1);
//        return  user;
//
//    }
    //查询数据库chatdetail，获取聊天历史
    private  void getchat()
    {
        Log.e("ChatActivity","===============getchar==============");
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        Gson gson1 = new Gson();
        String jsonStr = gson1.toJson(chatgroup_id);//json数据
        RequestBody body = RequestBody.create(JSON, jsonStr);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "GetChatList")
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        Log.e("ChatActivity","===============call==============");

        try {
            Response response = call.execute();
            String Str = response.body().string();
            ChatListStr = Str;
            Gson gson = new Gson();
            Log.e("ChatActivity","ChatList：11111："+Str);
            Type type = new TypeToken<List<Chatdetails>>(){}.getType();
            List<Chatdetails> ChatList = gson.fromJson(Str, type);
            Log.e("ChatActivity","ChatList：222222："+ChatList.toString());

            for (Chatdetails one : ChatList) {

                ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
                Log.e("ChatActivity","=================one.getUser_id()=======================:"+one.getUser_id());
                //Log.e("ChatActivity","=================聊天历史姓名=======================:"+getUsers(one.getUser_id()+"").getUserName());

                chatMsgEntity.setName(usermsg.getUser ().getUserName());
                chatMsgEntity.setDate(one.getChat_time());
                chatMsgEntity.setMessage(one.getChat_things());
                if (user_id.equals(one.getUser_id()+"")){
                    chatMsgEntity.setMsgType(true);
                }else{
                    chatMsgEntity.setMsgType(false);
                }

                list.add(chatMsgEntity);
            }
            list.addAll(SharedPreferencesgetme()) ;
            ListSort(list);
            Removeduplicate(list);
            adapter.notifyDataSetChanged();//通知ListView，数据已发生改变
        } catch (IOException e) {
            Log.e("ChatActivity", "getchat赋值出错");
            Toast.makeText(ChatActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();
            list.addAll(SharedPreferencesgetme()) ;
            ListSort(list);
            Removeduplicate(list);
            adapter.notifyDataSetChanged();//通知ListView，数据已发生改变
        }
    }
    //发送
    private void send()
    {
        String content = editText.getText().toString();
        Log.e("ChatActivity","================content=======================:"+content);
        if(content.length() > 0 &&usermsg.getUser ()!=null) {
            ChatMsgEntity entity = new ChatMsgEntity();
            Log.e("ChatActivity","================user_id=======================:"+user_id);
            entity.setName(usermsg.getUser ().getUserName());
            entity.setDate(getDate());
            entity.setMessage(content);
            entity.setMsgType(true);

            list.add(entity);
            adapter.notifyDataSetChanged();//通知ListView，数据已发生改变
            listView.setSelection(listView.getCount() - 1);//发送一条消息时，ListView显示选择最后一项

            editText.setText("");
            Log.e("ChatActivity","editText.setTex");
            try {
                JSONObject root = new JSONObject();
                root.put("content", content);
                Log.e("ChatActivity","root.toString()content："+root.toString());
                root.put("chatgroup_id", chatgroup_id);

                Log.e("ChatActivity","root.toString()content+chatgroup_id："+root.toString());
                root.put("user_id", user_id);
                Log.e("ChatActivity","root.toString()content+chatgroup_id+user_id："+root.toString());
                root.put("chat_time", chat_time);

                SharedPreferencessetme(content,user_id);
                if (root.toString()==null){
                    Log.e("ChatActivity","root.toString()为空");
                }else{
                    Log.e("ChatActivity","root.toString()非空："+root.toString());
                }
                SocketService.Send(root.toString());
            } catch (JSONException e) {
                if (content==null){
                    Log.e("ChatActivity","content为空");
                }

                Log.e("ChatActivity","发送信息失败");
                e.printStackTrace();
            }
        }else{
            Toast.makeText(ChatActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();
        }
    }
    //获取时间
    private String getDate()
    {
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date curDate =new Date(System.currentTimeMillis());//获取当前时间
        String str=formatter.format(curDate);

        return str;
    }
    //他人发信息的监听
    private void initMessageReceiver()
    {
        Log.e("ChatActivity","接收他人信息");
        messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SocketClient");
        registerReceiver(messageReceiver,filter);

        Intent i = new Intent(ChatActivity.this,SocketService.class);
        startService(i);
    }


    //接收他人发来的信息
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String content = intent.getStringExtra("jsonString");
            Log.e("ChatActivity","==========content"+content);

            try {
                JSONObject root = new JSONObject(content);
                Log.e("ChatActivity","==========otheruser_id"+root.getString("user_id"));
                if(content.length() > 0&& usermsg.getUser ()!=null) {

                    ChatMsgEntity entity = new ChatMsgEntity();
                    Log.e("ChatActivity","==========他人姓名"+usermsg.getUser ().getUserName());

                    entity.setName(usermsg.getUser ().getUserName());
                    entity.setDate(getDate());
                    entity.setMessage(root.getString("content"));
                    entity.setMsgType(false);
                    Log.e("ChatActivity","==========执行打印他人对话");
                    list.add(entity);
                    adapter.notifyDataSetChanged();//通知ListView，数据已发生改变
                    listView.setSelection(listView.getCount() - 1);//发送一条消息时，ListView显示选择最后一项
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    //获取本地存储的聊天记录
    public List<ChatMsgEntity> SharedPreferencesgetme(){
        Log.e("ChatActivity","SharedPreferencesgetme执行");
        sp1 = getSharedPreferences("me",MODE_PRIVATE);
        chatListJsonme = sp1.getString("getmechat","");
        List<ChatMsgEntity> chatList = new ArrayList<>();
        if(chatListJsonme!="")  //防空判断
        {
//            obj.get("name")
            Gson gson = new Gson();
            chatList = gson.fromJson(chatListJsonme, new TypeToken<List<ChatMsgEntity>>() {
            }.getType()); //取出

        }
        return chatList;
    }
    //聊天写入本地
    public void SharedPreferencessetme(String content,String id){
        Log.e("ChatActivity","SharedPreferencessetme执行");
        sp3 = getSharedPreferences("me",MODE_PRIVATE);
        chatListJsonme = sp3.getString("getmechat","");
        if(chatListJsonme!="" && usermsg.getUser ()!=null)  //防空判断
        {

            Gson gson = new Gson();
            List<ChatMsgEntity> chatList = gson.fromJson(chatListJsonme, new TypeToken<List<ChatMsgEntity>>() {
            }.getType()); //取出
            ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
            chatMsgEntity.setName(usermsg.getUser ().getUserName());
            chatMsgEntity.setDate(getDate());
            chatMsgEntity.setMessage(content);
            if (user_id.equals(id)) {
                chatMsgEntity.setMsgType(true);
            }else{
                chatMsgEntity.setMsgType(false);
            }
            chatList.add(chatMsgEntity);
            Gson gson2 = new Gson();
            String chatdetailsListStr = gson2.toJson(chatList);

            sp3.edit()
                    .putString("getmechat", chatdetailsListStr+"")//存入json串
                    .commit();//提交

        }
        //首次调用出错
        else if(chatListJsonme=="" && usermsg.getUser ()!=null){
            Log.e("ChatActivity","第一次写入SharedPreferencessetme");

            Gson gson = new Gson();
            List<ChatMsgEntity> chatList = new ArrayList<>();
            ChatMsgEntity chatMsgEntity = new ChatMsgEntity();

            chatMsgEntity.setName(usermsg.getUser ().getUserName());
            chatMsgEntity.setDate(getDate());
            chatMsgEntity.setMessage(content);
            if (user_id.equals(id)) {
                chatMsgEntity.setMsgType(true);
            }else{
                chatMsgEntity.setMsgType(false);
            }
            chatList.add(chatMsgEntity);
            Gson gson2 = new Gson();
            String chatdetailsListStr = gson2.toJson(chatList);
            Log.e("ChatActivity","chatdetailsListStr"+chatdetailsListStr);
            sp3.edit()
                    .putString("getmechat", chatdetailsListStr+"")//存入json串
                    .commit();//提交

        }

    }
    //对聊天记录进行按时间顺序的排序
    private static void ListSort(List<ChatMsgEntity> list) {
        Collections.sort(list, new Comparator<ChatMsgEntity>() {
            @Override
            public int compare(ChatMsgEntity o1, ChatMsgEntity o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getDate());
                    Date dt2 = format.parse(o2.getDate());
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
    //从上一级获取此用户user的信息，如userid groupid等关键信息
    private void getuserthing(){
        chatgroup_id = "0";
        user_id = "1";
        chat_time = getDate().toString();
    }
    //去除重复的聊天记录（如本地的聊天记录和数据库中都存储了某一条记录）
    private void Removeduplicate(List<ChatMsgEntity> list){
        for (int i=0;i<list.size()-1;i++){
            if (list.get(i).getDate()==list.get(i+1).getDate()){
                list.remove(i);
            }
        }

    }


}