package notworry.rj.edu.notworry.Ui.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import notworry.rj.edu.notworry.Entity.Spots;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Adapter.SearchAdapter;
import notworry.rj.edu.notworry.Utils.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by www15 on 2018/5/23.
 */

public class SpotSearch extends Activity {

    private OkHttpClient okHttpClient;
    private String BASE_URL = Constant.BASE_URL;
    private List<Spots> spots = new ArrayList<Spots> ();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.spot_search);

        okHttpClient = new OkHttpClient ();
        dlt ();
        back ();
        delete_history ();

        final LinearLayout searchMsg = findViewById (R.id.searchMsg);
        ImageView search = findViewById (R.id.search);
        search.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                searchMsg.setVisibility (View.GONE);
                EditText editText = findViewById (R.id.search_text);
                search (editText.getText ().toString ());

                TextView history1 = findViewById (R.id.history_search1);
                history1.setText (editText.getText ().toString ());
                history1.setVisibility (View.VISIBLE);

            }
        });

        final TextView history1 = findViewById (R.id.history_search1);
        history1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                searchMsg.setVisibility (View.GONE);
                EditText editText = findViewById (R.id.search_text);
                editText.setText (history1.getText ().toString ());
                search(history1.getText ().toString ());
            }
        });


        final TextView hot_search1 = findViewById (R.id.hot_search1);
        hot_search1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                searchMsg.setVisibility (View.GONE);
                EditText editText = findViewById (R.id.search_text);
                editText.setText (hot_search1.getText ().toString ());
                search(hot_search1.getText ().toString ());
            }
        });
    }

    public void dlt() {
        final EditText search_text = findViewById (R.id.search_text);
        final ImageView delete = findViewById (R.id.delete);
        final LinearLayout searchMsg = findViewById (R.id.searchMsg);
        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                search_text.setText ("");
            }
        });

        search_text.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length () == 0) {
                    ListView listView = findViewById (R.id.search_reslt);
                    delete.setVisibility (View.GONE);
                    listView.setVisibility (View.GONE);
                    searchMsg.setVisibility (View.VISIBLE);

                } else {
                    delete.setVisibility (View.VISIBLE);
                    searchMsg.setVisibility (View.GONE);
                }
            }
        });
    }

    public void back() {
        ImageView back = findViewById (R.id.back);
        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish ();
            }
        });
    }

    public void search(String name) {
        final LinearLayout searchMsg = findViewById (R.id.searchMsg);
        ImageView search = findViewById (R.id.search);

                Request request = new Request.Builder ()
                        .url (BASE_URL + "SearchSpotsByName?name="+name)
                        .build ();
                Call call = okHttpClient.newCall (request);
                call.enqueue (new Callback () {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace ();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body ().string ();
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Spots>>(){}.getType();
                        spots = gson.fromJson(str,type);
                        Log.e ("sssssssssssssssss",spots.size ()+"....");
                        final SearchAdapter searchAdapter = new SearchAdapter (SpotSearch.this,R.layout.search_result,spots);
                        final ListView listView = findViewById (R.id.search_reslt);
                        listView.post (new Runnable () {
                            @Override
                            public void run() {
                                listView.setAdapter (searchAdapter);
                                listView.setVisibility (View.VISIBLE);
                            }
                        });

                        listView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("SpotId",spots.get(position).getSpotId());
                                intent.putExtra("SpotName",spots.get(position).getSpotName ());
                                intent.setClass(SpotSearch.this, DetailPage.class);
                                startActivity(intent);
                            }
                        });

                    }
                });

    }

    public void delete_history() {
        ImageView delete_history = findViewById (R.id.delete_history);
        delete_history.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               TextView history1 = findViewById (R.id.history_search1);
               history1.setText ("");
               history1.setVisibility (View.GONE);
            }
        });
    }

}