package com.example.calla.heyhome;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPhotoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_view_photo, container, false);
        CardInfoHolder cardInfoHolder = CardInfoHolder.getInstance();

        String caption = cardInfoHolder.getCaption();
        int photo = cardInfoHolder.getPhoto();

        String userProfileImgPath = cardInfoHolder.getUserProfileImgPath();
        String userName = cardInfoHolder.getUserName();
        String location = cardInfoHolder.getLocation();
        final boolean favIcon = cardInfoHolder.getFavIcon();
        String userPostedTime = cardInfoHolder.getUserPostedTime();


        TextView captionTextView = (TextView) rootView.findViewById(R.id.user_post_caption);
        captionTextView.setText(caption);

        ImageView photoImageView = (ImageView) rootView.findViewById(R.id.user_post_picture);
        photoImageView.setImageResource(photo);

        // todo set profile img

        TextView userNameTextView = (TextView) rootView.findViewById(R.id.user_name);
        userNameTextView.setText(userName);

        TextView locationTextView = (TextView) rootView.findViewById(R.id.post_location);
        locationTextView.setText(location);

        // set favicon
        final ImageView faviconImageView = (ImageView) rootView.findViewById(R.id.user_post_fav_icon);
        if (favIcon) {
            faviconImageView.setImageResource(R.drawable.heart_icon_filled);
        } else {
            faviconImageView.setImageResource(R.drawable.heart_icon);
        }

        faviconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favIcon) {
                    faviconImageView.setImageResource(R.drawable.heart_icon);
                } else {
                    faviconImageView.setImageResource(R.drawable.heart_icon_filled);
                }
                // todo change the mark status in database
            }
        });

        TextView userPostTimeTextView = (TextView) rootView.findViewById(R.id.user_post_time);
        userPostTimeTextView.setText(userPostedTime);

        // forward to other app
        ImageView forwardImageView = (ImageView) rootView.findViewById(R.id.user_post_forward_icon);
        forwardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                // todo set this to the picture uri
                Uri uri = Uri.parse("android.resource://com.example.calla.heyhome/drawable/homedec_1.jpeg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);// should use image uri
                shareIntent.setType("image/jpeg");
                getActivity().startActivity(Intent.createChooser(shareIntent, getActivity().getResources().getText(R.string.send_to)));
            }
        });


        return rootView;
    }
}
