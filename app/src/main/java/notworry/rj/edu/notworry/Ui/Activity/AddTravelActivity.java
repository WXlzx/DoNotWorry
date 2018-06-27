package notworry.rj.edu.notworry.Ui.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import notworry.rj.edu.notworry.R;

/**
 * Created by xiuqiaoqi on 2018/6/7.
 */

public class AddTravelActivity extends Activity {
    private EditText travel_name;
    private EditText travel_destination;
    private EditText travel_date;
    private EditText travel_days;
    private EditText travel_spot;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtravel);
        travel_name=findViewById(R.id.addtravel_name);
        travel_destination=findViewById(R.id.addtravel_destination);
        travel_date=findViewById(R.id.addtravel_date);
        travel_days=findViewById(R.id.addtravel_days);
        travel_spot=findViewById(R.id.addtravel_spot);

        Button travel_submit = findViewById(R.id.addtravel_submit);
        ImageView travel_back = findViewById(R.id.addtravel_back);



        //输入时，灰色提示文字消失
        travel_name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                travel_name.setHint(null);
            }
        });
        travel_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travel_destination.setHint(null);
            }
        });
        travel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travel_date.setHint(null);
            }
        });
        travel_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travel_days.setHint(null);
            }
        });
        travel_spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travel_spot.setHint(null);
            }
        });



        travel_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //接收组队信息提交数据库
            }
        });
        travel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

}
