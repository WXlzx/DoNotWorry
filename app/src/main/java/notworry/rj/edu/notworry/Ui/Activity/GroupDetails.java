package notworry.rj.edu.notworry.Ui.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import notworry.rj.edu.notworry.Entity.Groups;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Utils.CircleImageView;
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

/**
 * Created by xiuqiaoqi on 2018/5/24.
 */

public class GroupDetails extends Activity {
    private CircleImageView group_pic;
    private EditText group_name;
    private EditText group_intro;
    private EditText group_num;
    private EditText group_neednum;
    private OkHttpClient okHttpClient;
    private Groups group;
    private String pic;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.addgroup);

        okHttpClient = new OkHttpClient ();
        group = usermsg.getGroups ();
        group_pic = findViewById (R.id.group_pic);

        Glide.with (GroupDetails.this)
                .load (Constant.BASE_URL + group.getPicture ())
                .into (group_pic);

        group_name = findViewById (R.id.addgroup_name);
        group_name.setText (group.getGroupName ());

        group_intro = findViewById (R.id.addgroup_intro);
        group_intro.setText (group.getGroupIntro ());

        group_num = findViewById (R.id.addgroup_realnum);
        group_num.setText (group.getRealNum ()+"");

        group_neednum = findViewById (R.id.addgroup_neednum);
        group_neednum.setText (group.getNeedNum ()+"");

        Button group_submit = findViewById (R.id.addgroup_submit);
        ImageView group_back = findViewById (R.id.addgroup_back);

        group_pic.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions (
                        GroupDetails.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        });

        group_submit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String name = group_name.getText ().toString ();
                String num = group_num.getText ().toString ();
                String needNum = group_neednum.getText ().toString ();
                String intro = group_intro.getText ().toString ();
                updateGroup (name, num, name + ".jpg", needNum, intro);
                finish ();
            }
        });
        group_back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish ();
            }
        });


    }


    //相册界面返回之后的回调方法

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2) {
            //获取照片
            Uri uri = data.getData ();
            Cursor cursor = getContentResolver ().query (uri, null, null,
                    null, null);
            cursor.moveToFirst ();
            String column = MediaStore.Images.Media.DATA;
            int columnIndex = cursor.getColumnIndex (column);
            String path = cursor.getString (columnIndex);
            File file = new File (path);
            doUploadFile (file);
            Glide.with (GroupDetails.this).load (file).into (group_pic);
        }
    }

    private void doUploadFile(File file) {

        Toast.makeText (this, "上传成功", Toast.LENGTH_SHORT).show ();
        //构建请求体
        RequestBody requestBody = RequestBody.create (
                MediaType.parse ("image/*"), file);
        Request request = new Request.Builder ()
                .url (Constant.BASE_URL + "GroupUploadFile?name=" + group_name.getText ().toString ())
                .post (requestBody)
                .build ();
        Call call = okHttpClient.newCall (request);
        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace ();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String p = response.body ().string ();
                pic = p;

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        //打开手机相册

        Intent intent = new Intent ();
        intent.setAction (Intent.ACTION_PICK);
        intent.setType ("image/*");
        startActivityForResult (intent, 2);
    }

    public void updateGroup(String name, String num, String pic, String needNum, String intro) {
        Gson gson = new Gson ();
        String userStr = gson.toJson (usermsg.getUser ());
        RequestBody requestBody = new FormBody.Builder ()
                .add ("name", name)
                .add ("num", num)
                .add ("needNum", needNum)
                .add ("jieshao", intro)
                .add ("picture", pic)
                .add ("user", userStr)
                .add ("group",usermsg.getGroups ().getGroupId ()+"")
                .build ();
        Request request = new Request.Builder ()
                .url (Constant.BASE_URL + "UpdateGroup")
                .post (requestBody)
                .build ();
        Call call = okHttpClient.newCall (request);
        call.enqueue (new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace ();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
