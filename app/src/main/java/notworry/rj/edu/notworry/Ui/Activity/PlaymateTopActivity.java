/**
 * 添加队伍下拉菜单页面
 */
package notworry.rj.edu.notworry.Ui.Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import notworry.rj.edu.notworry.R;

public class PlaymateTopActivity extends Activity implements OnClickListener{
    //定义两个按钮区域 组团队伍 旅游队伍
    private LinearLayout addGroup;
    private LinearLayout addTravel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmate_top);

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView(){
        //得到布局组件对象并设置监听事件
        addGroup = (LinearLayout)findViewById(R.id.playmate_addgroup);
        addTravel = (LinearLayout)findViewById(R.id.playmate_addtravel);

        addGroup.setOnClickListener(this);
        addTravel.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.playmate_addgroup:
                startActivity(new Intent(PlaymateTopActivity.this,AddGroupActivity.class));
                break;
            case R.id.playmate_addtravel:
                startActivity(new Intent(PlaymateTopActivity.this,AddTravelActivity.class));
                break;
        }
    }
}
