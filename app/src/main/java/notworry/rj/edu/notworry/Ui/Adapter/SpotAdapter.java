package notworry.rj.edu.notworry.Ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

import notworry.rj.edu.notworry.Entity.Spots;
import notworry.rj.edu.notworry.R;
import notworry.rj.edu.notworry.Ui.Activity.DetailPage;
import notworry.rj.edu.notworry.Utils.Constant;

/**
 * Created by www15 on 2018/5/22.
 */

public class SpotAdapter extends BaseAdapter {
    private List<Spots> spotsList;
    private Context context;
    private int item_layout_id;
    private String BASE_URL = Constant.BASE_URL;

    public SpotAdapter(Context context, int item_layout_id, List<Spots> spotsList) {
        this.context = context;
        this.item_layout_id = item_layout_id;
        this.spotsList = spotsList;
    }


    @Override
    public int getCount() {
        return spotsList.size()/4;
    }

    @Override
    public Object getItem(int position) {
        return spotsList.get(position);
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

        //Map<String, Object> mItemData = spotsList.get(position);

            final ImageButton spot_Img1 = convertView.findViewById(R.id.spot_img1);
            final ImageButton spot_Img2 = convertView.findViewById(R.id.spot_img2);
            final ImageButton spot_Img3 = convertView.findViewById(R.id.spot_img3);
            final ImageButton spot_Img4 = convertView.findViewById(R.id.spot_img4);

            Glide.with(context)
                    .asBitmap ()
                    .load(BASE_URL + spotsList.get(position*4).getSpotImg1())
                    .into(new SimpleTarget<Bitmap>(150,80) {
                    @Override //括号里的是图片宽高
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable (resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            spot_Img1.setBackground(drawable);//设置背景
                        }
                    }
                });

        Glide.with(context)
                .asBitmap ()
                .load(BASE_URL + spotsList.get(position*4+1).getSpotImg1())
                .into(new SimpleTarget<Bitmap>(150,80) {
                    @Override //括号里的是图片宽高
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable (resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            spot_Img2.setBackground(drawable);//设置背景
                        }
                    }
                });

        Glide.with(context)
                .asBitmap ()
                .load(BASE_URL + spotsList.get(position*4+2).getSpotImg1())
                .into(new SimpleTarget<Bitmap>(150,80) {
                    @Override //括号里的是图片宽高
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable (resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            spot_Img3.setBackground(drawable);//设置背景
                        }
                    }
                });
        Glide.with(context)
                .asBitmap ()
                .load(BASE_URL + spotsList.get(position*4+3).getSpotImg1())
                .into(new SimpleTarget<Bitmap>(150,80) {
                    @Override //括号里的是图片宽高
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable (resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            spot_Img4.setBackground(drawable);//设置背景
                        }
                    }
                });

        spot_Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("SpotId",spotsList.get(position*4).getSpotId());
                intent.putExtra("SpotName",spotsList.get(position*4).getSpotName ());
                intent.setClass(context, DetailPage.class);
                context.startActivity(intent);
            }
        });

        spot_Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("SpotId",spotsList.get(position*4+1).getSpotId());
                intent.putExtra("SpotName",spotsList.get(position*4+1).getSpotName ());
                intent.setClass(context, DetailPage.class);
                context.startActivity(intent);
            }
        });

        spot_Img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("SpotId",spotsList.get(position*4+2).getSpotId());
                intent.putExtra("SpotName",spotsList.get(position*4+2).getSpotName ());
                intent.setClass(context, DetailPage.class);
                context.startActivity(intent);
            }
        });

        spot_Img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("SpotId",spotsList.get(position*4+3).getSpotId());
                intent.putExtra("SpotName",spotsList.get(position*4+3).getSpotName ());
                intent.setClass(context, DetailPage.class);
                context.startActivity(intent);
            }
        });

        TextView spot_Text1 = convertView.findViewById(R.id.spot_text1);
            TextView spot_Text2 = convertView.findViewById(R.id.sopt_text2);
            TextView spot_Text3 = convertView.findViewById(R.id.spot_text3);
            TextView spot_Text4 = convertView.findViewById(R.id.spot_text4);

            spot_Text1.setText(spotsList.get(position*4).getSpotName());
            spot_Text2.setText(spotsList.get(position*4+1).getSpotName());
            spot_Text3.setText(spotsList.get(position*4+2).getSpotName());
            spot_Text4.setText(spotsList.get(position*4+3).getSpotName());

        return convertView;
    }
}
