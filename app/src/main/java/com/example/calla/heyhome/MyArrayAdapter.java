package com.example.calla.heyhome;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by yuan on 5/24/16.
 */
public class MyArrayAdapter extends ArrayAdapter<CardInfo> {

    private final List<CardInfo> cardInfo;
    private Context ctxt;
    boolean marked = false;

    public MyArrayAdapter(Context context, int resource, List<CardInfo> cardInfo) {
        super(context, resource, cardInfo);
        this.cardInfo = cardInfo;
        ctxt = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ScrapViewHolder holder;
        View row = convertView;

        if (row == null) {
            Log.i("yzheng", "Row is null; Need to be inflated.");

            //row = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_layout, parent, false);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.home_card_layout, parent, false);
            holder = new ScrapViewHolder();

            holder.vUserProfileImg = (ImageView) row.findViewById(R.id.user_profile_img);
            holder.vUserName = (TextView) row.findViewById(R.id.user_name);
            holder.vLocation = (TextView) row.findViewById(R.id.post_location);
            holder.vUserPostedCaption = (TextView) row.findViewById(R.id.user_post_caption);
            holder.vUserPostedPhoto = (ImageView) row.findViewById(R.id.user_post_picture);
            holder.vFavIcon = (ImageView) row.findViewById(R.id.user_post_fav_icon);
            holder.vUserPostedTime = (TextView) row.findViewById(R.id.user_post_time);
            holder.vFriendName = (TextView) row.findViewById(R.id.friend_comment_friend_name);
            holder.vFriendComment = (TextView) row.findViewById(R.id.friend_comment_friend_comment);
            row.setTag(holder);
        } else {
            Log.i("yzheng", "Row is NOT null; reusing it!");
            holder = (ScrapViewHolder) row.getTag();
        }

        holder.vUserPostedCaption.setText(cardInfo.get(position).getUserPostedCaption());

        int fileName = cardInfo.get(position).getUserPostedPhoto();
        holder.vUserPostedPhoto.setImageResource(fileName);
        holder.vUserPostedPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // holder.menu.setImageResource(R.mipmap.ic_launcher);

        holder.vUserPostedPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "MENU clicked for " + cardInfo.get(position).getUserPostedCaption();
                Toast.makeText(ctxt, msg, Toast.LENGTH_SHORT).show();
            }
        });

        holder.vFavIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(marked) {
                    marked = false;
                    holder.vFavIcon.setImageResource(R.drawable.heart_icon);
                } else {
                    marked = true;
                    holder.vFavIcon.setImageResource(R.drawable.heart_icon_filled);
                }
            }
        });

        return row;
    }

    public class ScrapViewHolder {
        ImageView vUserProfileImg;
        TextView vUserName;
        TextView vLocation;
        TextView vUserPostedCaption;
        ImageView vUserPostedPhoto;
        ImageView vFavIcon;
        TextView vUserPostedTime;
        TextView vFriendName;
        TextView vFriendComment;
    }
}