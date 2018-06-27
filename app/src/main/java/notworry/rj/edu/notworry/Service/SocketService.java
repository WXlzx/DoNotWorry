package notworry.rj.edu.notworry.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import notworry.rj.edu.notworry.Entity.ChatMsgEntity;
import notworry.rj.edu.notworry.Utils.JDBCip;

import static notworry.rj.edu.notworry.Utils.JDBCip.ip;


public class SocketService extends Service {
    private String chatListJsonme;
    private SharedPreferences sp;
    private static Socket socket;
    private static BufferedWriter bWriter;//输出流，发送、写入信息
    private static BufferedReader bReader;//输入流，接受、读取信息

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(SocketService.this, "connect success", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    socket = new Socket(JDBCip.ip,1980);
                    bWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Log.e("SocketService","111111111111111111");
                    //readLine()是一个阻塞函数，当没有数据读取时，就一直会阻塞在那，而不是返回null
                    String s;
                    while ((s = bReader.readLine()) != null) {
                        SharedPreferencessetother(s);
                        Intent intent = new Intent();
                        intent.putExtra("jsonString", s);
                        intent.setAction("SocketClient");
                        sendBroadcast(intent);
                    }
                } catch (Exception e) {
                    Log.e ("22222222222","22222222222");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void Send(String jsonString)
    {

        jsonString += "\n";
        Log.e("SocketService","===========bWriter===========");
        try {
            Log.e("SocketService","==========="+jsonString+"===========");
            Log.e("SocketService","===========bWriter finish===========");
            bWriter.write(jsonString);
            bWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SharedPreferencessetother(String json){
        Log.e("SocketService","SharedPreferencessetother执行");
        sp = getSharedPreferences("me",MODE_PRIVATE);
        chatListJsonme = sp.getString("getmechat","");

        JSONObject root = null;
        try {
            root = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(chatListJsonme!="")  //防空判断
        {

            Gson gson = new Gson();
            List<ChatMsgEntity> chatList = gson.fromJson(chatListJsonme, new TypeToken<List<ChatMsgEntity>>() {
            }.getType()); //取出
            ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
            chatMsgEntity.setName("other");
            chatMsgEntity.setDate(getDate());

            try {
                Log.e("111111111111","1111111111111111setMessage"+root.getString("content"));
                chatMsgEntity.setMessage(root.getString("content"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            chatMsgEntity.setMsgType(false);
            chatList.add(chatMsgEntity);
            Gson gson2 = new Gson();
            String chatdetailsListStr = gson2.toJson(chatList);
            sp.edit()
                    .putString("getmechat", chatdetailsListStr+"")//存入json串
                    .commit();//提交

        }else{
            Log.e("ChatActivity","第一次写入SharedPreferencessetme");

            Gson gson = new Gson();
            List<ChatMsgEntity> chatList = new ArrayList<>();
            ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
            chatMsgEntity.setName("other");
            chatMsgEntity.setDate(getDate());
            try {
                Log.e("111111111111","1111111111111111setMessagefirst"+root.getString("content"));
                chatMsgEntity.setMessage(root.getString("content"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            chatMsgEntity.setMsgType(false);
            chatList.add(chatMsgEntity);
            Gson gson2 = new Gson();
            String chatdetailsListStr = gson2.toJson(chatList);
            Log.e("ChatActivity","chatdetailsListStr"+chatdetailsListStr);
            sp.edit()
                    .putString("getmechat", chatdetailsListStr+"")//存入json串
                    .commit();//提交

        }

    }
    private String getDate()
    {
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date curDate =new Date(System.currentTimeMillis());//获取当前时间
        String str=formatter.format(curDate);

        return str;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

}