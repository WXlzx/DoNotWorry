package notworry.rj.edu.notworry.Ui.Adapter;

/**
 * Created by 石灵明 on 2018/5/27.
 */

import java.util.List;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import notworry.rj.edu.notworry.Entity.ChatMsgEntity;
import notworry.rj.edu.notworry.R;

public class ChatMsgListViewAdapter extends BaseAdapter {
    private Activity context;
    private List<ChatMsgEntity> list;//存放所有消息对象
    private LayoutInflater layoutInflater;//用于加载layout

    public ChatMsgListViewAdapter(Activity context,List<ChatMsgEntity> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMsgEntity chatMsgEntity = list.get(position);
        ViewHolder viewHolder;
        viewHolder = new ViewHolder();
//        viewHolder.leftLayout.setVisibility(View.VISIBLE);
//        viewHolder.rightLayout.setVisibility(View.GONE);
        //if(convertView == null) {
        if(chatMsgEntity.getMsgType()) {

            convertView = layoutInflater.inflate(R.layout.chatting_item_msg_text_left, null);

        }
        else {
            convertView = layoutInflater.inflate(R.layout.chatting_item_msg_text_right, null);
        }



        viewHolder.tvSendTime = (TextView)convertView.findViewById(R.id.tv_sendtime);
        viewHolder.tvUserName = (TextView)convertView.findViewById(R.id.tv_username);
        viewHolder.tvContent = (TextView)convertView.findViewById(R.id.tv_chatcontent);

        //convertView.setTag(viewHolder);
        //}else {
        //viewHolder = (ViewHolder)convertView.getTag();
        //}

        viewHolder.tvSendTime.setText(list.get(position).getDate());
        viewHolder.tvUserName.setText(list.get(position).getName());
        viewHolder.tvContent.setText(list.get(position).getMessage());
        return convertView;
    }

    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
//        LinearLayout leftLayout;
//        LinearLayout rightLayout;
//        public ViewHolder(View view){
//            super();
//            leftLayout=(LinearLayout)view.findViewById(R.id.chatting_item_msg_text_left);
//            rightLayout=(LinearLayout)view.findViewById(R.id.chatting_item_msg_text_right);
//            }
    }
}
