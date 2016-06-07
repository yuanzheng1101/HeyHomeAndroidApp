package com.example.calla.heyhome;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yuan on 5/24/16.
 */
public class MyArrayAdapter extends ArrayAdapter<CardInfo> {

    private final List<CardInfo> cardInfo;
    private Context context;
    FragmentManager fm;

    public MyArrayAdapter(Context context, int resource, List<CardInfo> cardInfo) {
        super(context, resource, cardInfo);
        this.cardInfo = cardInfo;
        this.context = context;
        fm = ((Activity) context).getFragmentManager();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ScrapViewHolder holder;
        View row = convertView;

        if (row == null) {
            Log.i("yzheng", "Row is null; Need to be inflated.");

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
            /*holder.vFriendName = (TextView) row.findViewById(R.id.friend_comment_friend_name);
            holder.vFriendComment = (TextView) row.findViewById(R.id.friend_comment_friend_comment);*/
            holder.vUserForwardIcon = (ImageView) row.findViewById(R.id.user_post_forward_icon);
            holder.vUerPostCommentIcon = (ImageView) row.findViewById(R.id.user_post_comment_icon);

            row.setTag(holder);
        } else {
            Log.i("yzheng", "Row is NOT null; reusing it!");
            holder = (ScrapViewHolder) row.getTag();
        }

        // set caption
        holder.vUserPostedCaption.setText(cardInfo.get(position).getUserPostedCaption());

        // set picture todo need change this to String
        int fileName = cardInfo.get(position).getUserPostedPhoto();
        holder.vUserPostedPhoto.setImageResource(fileName);
        holder.vUserPostedPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // set profile image
        // todo set profile image

        // set user name
        holder.vUserName.setText(cardInfo.get(position).getUserName());

//        //set location
//        holder.vLocation.setText(cardInfo.get(position).getLocation());

        holder.vLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewMapPage();
            }
        });



        // set favIcon
        if (cardInfo.get(position).getFavIcon()) {
            holder.vFavIcon.setImageResource(R.drawable.heart_icon_filled);
        } else {
            holder.vFavIcon.setImageResource(R.drawable.heart_icon);
        }

        holder.vFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardInfo.get(position).getFavIcon()) {
                    holder.vFavIcon.setImageResource(R.drawable.heart_icon);
                } else {
                    holder.vFavIcon.setImageResource(R.drawable.heart_icon_filled);
                }
                // todo change the mark status in database
            }
        });

        // set time
        holder.vUserPostedTime.setText(cardInfo.get(position).getUserPostedTime());

        /*// set friend name of the latest comment
        holder.vFriendName.setText(cardInfo.get(position).getFriendName());

        // set latest friend comment
        holder.vFriendComment.setText(cardInfo.get(position).getFriendComment());*/


        // forward to other app
        holder.vUserForwardIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                // todo set this to the picture uri
                Uri uri = Uri.parse("android.resource://com.example.calla.heyhome/drawable/homedec_1.jpeg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);// should use image uri
                shareIntent.setType("image/jpeg");
                context.startActivity(Intent.createChooser(shareIntent, context.getResources().getText(R.string.send_to)));
            }
        });

        return row;
    }

    private void openViewMapPage() {
        Bundle bundle = new Bundle();
        ViewMapFragment page = new ViewMapFragment();
        page.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }

    public class ScrapViewHolder {
        ImageView vUserProfileImg;
        TextView vUserName;
        TextView vLocation;
        TextView vUserPostedCaption;
        ImageView vUserPostedPhoto;
        ImageView vFavIcon;
        ImageView vUerPostCommentIcon;
        ImageView vUserForwardIcon;
        TextView vUserPostedTime;
        /*TextView vFriendName;
        TextView vFriendComment;*/
    }
}