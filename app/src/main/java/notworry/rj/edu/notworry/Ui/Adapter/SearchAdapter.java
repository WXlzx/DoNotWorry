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
import notworry.rj.edu.notworry.Entity.Spots;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Utils.Constant;


/**
 * Created by www15 on 2018/6/19.
 */

public class SearchAdapter extends BaseAdapter {

    private List<Spots> list = new ArrayList<Spots> ();
    private Context context;
    private int item_layout_id;

    public SearchAdapter(Context context, int item_layout_id, List list) {
        this.context = context;
        this.list = list;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        return list.size ();
    }

    @Override
    public Object getItem(int position) {
        return list.get (position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(item_layout_id, null);
        }
        ImageView spotImg = convertView.findViewById (R.id.spotImg);
        TextView spotName = convertView.findViewById (R.id.spotName);
        TextView spotIntor = convertView.findViewById (R.id.spotIntor);
        TextView spotPosition = convertView.findViewById (R.id.spotPosition);

        spotName.setText (list.get (position).getSpotName ());
        spotPosition.setText (list.get (position).getSpotDetail ().getSpotsPosition ());
        spotIntor.setText (list.get (position).getSpotDetail ().getIntroduction ());
        Glide.with (convertView).load (Constant.BASE_URL+list.get (position).getSpotImg1 ()).into (spotImg);

        return convertView;
    }

}
