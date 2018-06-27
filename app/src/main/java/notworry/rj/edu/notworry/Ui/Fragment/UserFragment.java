package notworry.rj.edu.notworry.Ui.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import notworry.rj.edu.notworry.Entity.Users;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Activity.OrdersActivity;
import notworry.rj.edu.notworry.Ui.Activity.UserAddGroupMsg;
import notworry.rj.edu.notworry.Ui.Activity.User_DetailActivity;
import notworry.rj.edu.notworry.Utils.CircleImageView;
import notworry.rj.edu.notworry.Utils.Constant;
import notworry.rj.edu.notworry.Utils.usermsg;


/**
 * Created by www15 on 2018/5/16.
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.userfragment_page,
                container, false);

        CircleImageView userPic = view.findViewById (R.id.user_pic);
        TextView username = view.findViewById(R.id.username);
        TextView realname = view.findViewById(R.id.realname);

        Users user = usermsg.getUser();

        username.setText(user.getUserName());
        realname.setText(user.getRealName());
        Glide.with(view.getContext ()).load(Constant.BASE_URL +user.getPicture ()).into(userPic);

        LinearLayout information = view.findViewById(R.id.information);
        LinearLayout orders = view.findViewById(R.id.orders);
        LinearLayout groups = view.findViewById(R.id.groups);
        LinearLayout setting = view.findViewById(R.id.setting);

        information.setOnClickListener(this);
        orders.setOnClickListener(this);
        groups.setOnClickListener (this);



        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.information:
                toInformation();
                break;
            case R.id.orders:
                toOrders();
                break;
            case R.id.groups:
                toGroups ();
                break;
            case R.id.setting:

                break;

        }
    }


    public void toOrders() {
        Intent intent = new Intent();
        intent.setClass(view.getContext(), OrdersActivity.class);
        startActivity(intent);
    }

    public void toInformation() {
        Intent intent = new Intent();
        intent.setClass(view.getContext(), User_DetailActivity.class);
        startActivity(intent);
    }

    public void toGroups(){
        Intent intent = new Intent ();
        intent.setClass (view.getContext (), UserAddGroupMsg.class);
        startActivity (intent);
    }
}
