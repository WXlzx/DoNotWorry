package notworry.rj.edu.notworry.Ui.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Fragment.PlayMateFragment;
import notworry.rj.edu.notworry.Ui.Fragment.SpotFragment;
import notworry.rj.edu.notworry.Ui.Fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost fragmentTabHost;
    private Map<String, View> map;
    private Map<String, Integer> img_old;
    private Map<String, Integer> img_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initFragmentTabHost ();
        fragmentTabHost.setOnTabChangedListener (new TabHost.OnTabChangeListener () {
            @Override
            public void onTabChanged(String tabId) {
                for (String mapId : map.keySet ()) {
                    if (mapId == tabId) {
                        ((ImageView) map.get (mapId).findViewById (R.id.bottom_img))
                                .setImageResource (img_new.get (mapId));
                    } else {
                        ((ImageView) map.get (mapId).findViewById (R.id.bottom_img))
                                .setImageResource (img_old.get (mapId));
                    }
                }
            }
        });
    }


    @Override
    protected void onResume() {
        int id = getIntent ().getIntExtra ("id", 0);
        if (id == 0) {
            fragmentTabHost.setCurrentTab (id);
            id = 0;
        }
        if (id == 1) {
            fragmentTabHost.setCurrentTab (id);
            id = 0;
        }
        if (id == 2) {
            fragmentTabHost.setCurrentTab (id);
            id = 0;
        }
        super.onResume ();
    }

    private void initFragmentTabHost() {
        map = new HashMap<String, View> ();
        img_old = new HashMap<String, Integer> ();
        img_new = new HashMap<String, Integer> ();

        fragmentTabHost = findViewById (android.R.id.tabhost);
        fragmentTabHost.setup (this, getSupportFragmentManager (), android.R.id.tabhost);

        addTabSpc ("景区介绍", R.drawable.spot, "景区介绍", R.drawable.spot_selected, SpotFragment.class);
        addTabSpc ("找玩伴", R.drawable.yiqi, "找玩伴", R.drawable.yiqi_selected, PlayMateFragment.class);
        addTabSpc ("个人中心", R.drawable.user, "个人中心", R.drawable.user_selected, UserFragment.class);

        ((ImageView) map.get ("景区介绍").findViewById (R.id.bottom_img))
                .setImageResource (img_new.get ("景区介绍"));

    }

    private void addTabSpc(String strId, int id, String str, int newId, Class<?> fragment) {
        View view = getBottomView (id, str);
        TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec (strId).setIndicator (view);
        fragmentTabHost.addTab (tabSpec, fragment, null);
        map.put (strId, view);
        img_old.put (strId, id);
        img_new.put (strId, newId);

        fragmentTabHost.setCurrentTab (0);

    }

    private View getBottomView(int id, String str) {

        View view = getLayoutInflater ().inflate (R.layout.layout_tab, null);

        ImageView imageView = view.findViewById (R.id.bottom_img);
        imageView.setImageResource (id);

        TextView textView = view.findViewById (R.id.bottom_text);
        textView.setText (str);

        return view;
    }


}

