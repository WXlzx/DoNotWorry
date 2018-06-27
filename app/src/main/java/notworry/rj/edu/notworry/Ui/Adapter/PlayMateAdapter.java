package notworry.rj.edu.notworry.Ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import notworry.rj.edu.notworry.Entity.Groups;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Utils.Constant;

/**
 * Created by xiuqiaoqi on 2018/5/24.
 */

public class PlayMateAdapter extends BaseAdapter {

    private List<Groups> groupList = new ArrayList<Groups>();
    private Context context;
    private int item_layout_id;

    public PlayMateAdapter(Context context, int item_layout_id, List<Groups> data) {
        this.context = context;
        this.item_layout_id = item_layout_id;
        this.groupList = data;
    }


    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(item_layout_id, null);
        }

        ImageView grouppic = convertView.findViewById(R.id.iv_grouppic);
        TextView groupname = convertView.findViewById(R.id.groupname);
        //TextView grouptip = convertView.findViewById(R.id.grouptip);
        TextView groupintro = convertView.findViewById(R.id.groupintro);

        Glide.with (convertView.getContext ()).load (Constant.BASE_URL+groupList.get (position).getPicture ()).into (grouppic);
        groupname.setText(groupList.get(position).getGroupName());
        //grouptip.setText(groupList.get(position).getGroupShool());
        groupintro.setText(groupList.get(position).getGroupIntro());
        //点击打开组队具体内容，组内聊天等
        // LinearLayout layout=convertView.findViewById(R.id.group);

        return convertView;
    }

}
