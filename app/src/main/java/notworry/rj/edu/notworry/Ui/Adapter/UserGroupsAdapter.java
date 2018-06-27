package notworry.rj.edu.notworry.Ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import notworry.rj.edu.notworry.Entity.Groups;
import notworry.rj.edu.notworry.R;

/**
 * Created by 肖明臣 on 2018/6/20.
 */

public class UserGroupsAdapter extends BaseAdapter{
    private Context context;
    private List<Groups> usergroupsList;
    public  UserGroupsAdapter(Context context,List<Groups> usergroupsList){
        this.context = context;
        this.usergroupsList = usergroupsList;
    }
    @Override
    public int getCount() {
        return usergroupsList.size();
    }

    @Override
    public Object getItem(int position) {
        return usergroupsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.usergroups_layout,null);
        }
        TextView groupname = convertView.findViewById(R.id.group_name);
        TextView grouptip = convertView.findViewById(R.id.group_tip);
        TextView groupintro = convertView.findViewById(R.id.group_intro);

        groupname.setText("名称： "+usergroupsList.get(position).getGroupName());
        grouptip.setText("学校： "+usergroupsList.get(position).getUserIdList ().get (0).getSchool ().getSchoolName ());
        groupintro.setText("简介："+usergroupsList.get(position).getGroupIntro());

        return convertView;
    }
}
