package notworry.rj.edu.notworry.Ui.Activity;


import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import notworry.rj.edu.notworry.Entity.Users;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Service.SocketService;
import notworry.rj.edu.notworry.Utils.Constant;
import notworry.rj.edu.notworry.Utils.usermsg;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class User_DetailActivity extends AppCompatActivity {

    private String isUpdate = "";
    private ImageView imageView;
    private OkHttpClient okHttpClient;
    private String BASE_URL = Constant.BASE_URL;
    private Users user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_detail);

        user = usermsg.getUser ();
        okHttpClient = new OkHttpClient();
        imageView = findViewById(R.id.Detail_Image);
        final Button postImage = findViewById(R.id.Detail_UploadImage);
        final Button next = findViewById(R.id.Detail_Next);
        final Button back=findViewById(R.id.Detail_Back);
        EditText editname = findViewById(R.id.Detail_Username);
        EditText editpass = findViewById(R.id.Detail_Password);
        EditText editphone = findViewById(R.id.Detail_Phone);
        EditText editrealname = findViewById(R.id.Detail_RealName);
        EditText editid = findViewById(R.id.Detail_IdCard);
        EditText editsch = findViewById(R.id.Detail_School);

        if (user.getPicture ()==null){

        }else{
            Glide.with(User_DetailActivity.this).load(Constant.BASE_URL +user.getPicture ()).into(imageView);
        }
        editname.setText(user.getUserName());
        editpass.setText(user.getPassWord());
        editphone.setText(user.getPhoneNum());
        editrealname.setText(user.getRealName());
        editid.setText(user.getIdNumber());
        if(user.getSchool ()!=null) {
            editsch.setText (user.getSchool ().getSchoolName ());
        }else{
            editsch.setText ("");
        }
        postImage.setFocusable(true);
        postImage.setFocusableInTouchMode(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 提交
                EditText editname = findViewById(R.id.Detail_Username);
                EditText editpass = findViewById(R.id.Detail_Password);
                EditText editphone = findViewById(R.id.Detail_Phone);
                EditText editrealname = findViewById(R.id.Detail_RealName);
                EditText editid = findViewById(R.id.Detail_IdCard);
                EditText editsch = findViewById(R.id.Detail_School);

                user.setUserName(editname.getText().toString());
                user.setPassWord(editpass.getText().toString());
                user.setPicture (editname.getText ().toString ()+".jpg");
                user.setPhoneNum(editphone.getText().toString());
                user.setRealName(editrealname.getText().toString());
                user.setIdNumber(editid.getText().toString());

                update(user,editsch.getText ().toString ());

                finish ();


            }
        });

        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
             Intent i = new Intent(User_DetailActivity.this,MainActivity.class);
             i.putExtra ("id",2);
             startActivity (i);
            }
        });

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editname = findViewById(R.id.Detail_Username);
                editname.setFocusableInTouchMode(false);

                ActivityCompat.requestPermissions(
                       User_DetailActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1
                );
                postImage.requestFocus();
                postImage.findFocus();
            }
        });
    }


    //相册界面返回之后的回调方法

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2) {
            //获取照片
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null,
                    null, null);
            cursor.moveToFirst();
            String column = MediaStore.Images.Media.DATA;
            int columnIndex = cursor.getColumnIndex(column);
            String path = cursor.getString(columnIndex);
            File file = new File(path);
            doUploadFile(file);
            Glide.with(User_DetailActivity.this).load(file).into(imageView);
        }
    }

    private void doUploadFile(File file) {
        EditText editname = findViewById(R.id.Detail_Username);
        user.setPicture (editname.getText ().toString ()+".jpg");
        usermsg.getUser ().setPicture (editname.getText ().toString ()+".jpg");
        Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
        //构建请求体
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("image/*"), file);
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "UserUploadFile?name=" + editname.getText().toString ())
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //打开手机相册

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    private void update(Users user,String schoolstr) {
        Gson gson = new Gson();
        String userStr = gson.toJson(user);
        RequestBody requestBody = new FormBody.Builder()
                .add("userstr", userStr)
                .add("schoolstr",schoolstr)
                .build();
        final Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL + "UpdateUser")
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();

                Gson gson = new Gson();

                Users user = gson.fromJson (str,Users.class);
                Log.e ("useruseruser",str+"...");
                usermsg.setUser (user);
            }
        });

    }

}
