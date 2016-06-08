package com.example.calla.heyhome;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yuan on 5/24/16.
 */
public class MyArrayAdapter extends ArrayAdapter<CardInfo> {

    private final List<CardInfo> cardInfo;
    private Context context;
    FragmentManager fm;
    ScrapViewHolder holder;

    // create Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    StorageReference storageRef;

    public MyArrayAdapter(Context context, int resource, List<CardInfo> cardInfo) {
        super(context, resource, cardInfo);
        this.cardInfo = cardInfo;
        this.context = context;
        fm = ((Activity) context).getFragmentManager();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

//        final ScrapViewHolder holder;
        View row = convertView;
        // initialize database and storage
        firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://intense-inferno-3371.appspot.com");

        if (row == null) {
            Log.i("yzheng", "Row is null; Need to be inflated.");

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.home_card_layout, parent, false);
            holder = new ScrapViewHolder();

            holder.vUserProfileImg = (CircleImageView) row.findViewById(R.id.user_profile_img);
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
        // set photo
        String imageString = cardInfo.get(position).getUserPostedPhoto();
        holder.vUserPostedPhoto.setImageBitmap(convertStringToBitmap(imageString));
        holder.vUserPostedPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // set profile image
        String profileImgString = cardInfo.get(position).getUserProfileImgPath();
        StorageReference fileRef = storageRef.child("pics/" + profileImgString);

        final long ONE_MEGABYTE = 1024 * 1024;
        fileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.vUserProfileImg.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
                Log.d("error", "Failed to load image from Firebase storage!!!");
            }
        });

        holder.vUserProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageMe();
            }
        });

        // set user name
        holder.vUserName.setText(cardInfo.get(position).getUserName());

        // set location
        String location = cardInfo.get(position).getLocation();
        String[] strs = location.split(",");
        final String latitude = strs[0];
        final String longitude = strs[1];
        double lat = Double.parseDouble(latitude);
        double longit = Double.parseDouble(longitude);
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        String city = "Santa Clara";
        try {
            city = gcd.getFromLocation(lat, longit, 1).get(0).getLocality();
        } catch (IOException e) {
        }
        holder.vLocation.setText(city);

        holder.vLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLHolder llHolder = LLHolder.getInstance();
                // todo change getLocation() to getLatitude;
                llHolder.setLatitude(latitude);
                llHolder.setLongitude(longitude);
                context.startActivity(new Intent(context, ViewMapActivity.class));
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
        }

        );

        // set time
        String timeStamp = cardInfo.get(position).getUserPostedTime();
        StringBuilder sb = new StringBuilder();
//        sb.append(timeStamp)
        holder.vUserPostedTime.setText(cardInfo.get(position).getUserPostedTime());

        // forward to other app
        holder.vUserForwardIcon.setOnClickListener(new View.OnClickListener()

                                                   {
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
                                                   }

        );

        return row;
    }

    public class ScrapViewHolder {
        CircleImageView vUserProfileImg;
        TextView vUserName;
        TextView vLocation;
        TextView vUserPostedCaption;
        ImageView vUserPostedPhoto;
        ImageView vFavIcon;
        ImageView vUerPostCommentIcon;
        ImageView vUserForwardIcon;
        TextView vUserPostedTime;
    }

    private void openPageMe() {
        Bundle bundle = new Bundle();
        Page_Me page = new Page_Me();
        page.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }

    // calla: for load data from db
    public void addCardInfo(CardInfo card) {
        cardInfo.add(card);
        notifyDataSetChanged();
    }

    public Bitmap convertStringToBitmap(String imageString) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // Experiment with different sizes

        byte[] decodedImage = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImageByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        return decodedImageByte;
    }
}