package notworry.rj.edu.notworry.Ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import notworry.rj.edu.notworry.Entity.Orderdetails;
import notworry.rj.edu.notworry.Entity.Orders;
import notworry.rj.edu.notworry.Entity.Spots;
import notworry.rj.edu.notworry.Entity.Users;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Utils.Constant;

/**
 * Created by 庆伟 on 2018/5/23.
 */

public class OrderAdapter extends BaseAdapter {
    private List<Orders> data;
    private Context context;
    private int id;

    public OrderAdapter(Context context, int id, List<Orders> data) {
        this.context = context;
        this.id = id;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(id, null);
        }

        ImageView img = convertView.findViewById (R.id.item_image);
        TextView tv = convertView.findViewById(R.id.item_tv);
        TextView price = convertView.findViewById(R.id.item_price);
        TextView date = convertView.findViewById(R.id.item_date);
        TextView condition = convertView.findViewById(R.id.item_condition);

        Orders orders =  data.get(position);
        Users users = orders.getUsers();
        Spots spots = orders.getSpot();
        Orderdetails orderdetails = orders.getOrderdetails();

        Glide.with(context).load(Constant.BASE_URL +spots.getSpotImg1 ()).into(img);
        tv.setText(users.getRealName());
        price.setText(orderdetails.getPrice()+"");
        date.setText(orders.getRiseTime()+"");
        if (orders.getIspay() == 0){
            condition.setText("未付款");
        }else {
            condition.setText("已付款");
        }
        return convertView;
    }
}
