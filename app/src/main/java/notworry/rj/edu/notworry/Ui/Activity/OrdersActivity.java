package notworry.rj.edu.notworry.Ui.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Adapter.OrderAdapter;
import notworry.rj.edu.notworry.Ui.Fragment.AllOrder;
import notworry.rj.edu.notworry.Ui.Fragment.PendTrip;
import notworry.rj.edu.notworry.Ui.Fragment.Pending;

/**
 * Created by www15 on 2018/5/27.
 */

public class OrdersActivity extends AppCompatActivity {

    private FragmentTabHost myTabHost;
    private Map<String, View> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        map = new HashMap<>();
        initTabHost();
        myTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (String mapId : map.keySet()) {
                    if (tabId == mapId) {
                        TextView textView = map.get(mapId).findViewById(R.id.tabhost_tv);
                        textView.setTextColor(Color.BLUE);
                    } else {
                        TextView textView = map.get(mapId).findViewById(R.id.tabhost_tv);
                        textView.setTextColor(Color.BLACK);
                    }
                }
            }
        });
    }

    private void initTabHost() {
        myTabHost = findViewById(android.R.id.tabhost);
        myTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabhost);
        getTabView("tab1", "待处理", Pending.class);
        getTabView("tab2", "待出游", PendTrip.class);
        getTabView("tab3", "全部订单", AllOrder.class);
        myTabHost.setCurrentTab(0);
        TextView textView = map.get("tab1").findViewById(R.id.tabhost_tv);
        textView.setTextColor(Color.BLUE);
    }

    private void getTabView(String id, String wenzi, Class<?> fragment) {
        View view = getTabView(wenzi);
        TabHost.TabSpec tabSpec = myTabHost.newTabSpec(id).setIndicator(view);
        myTabHost.addTab(tabSpec, fragment, null);
        map.put(id, view);
    }

    private View getTabView(String a) {
        View view = getLayoutInflater().inflate(R.layout.layout_tabhost, null);
        TextView textView = view.findViewById(R.id.tabhost_tv);
        textView.setText(a);
        return view;
    }
}
