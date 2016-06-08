package com.example.calla.heyhome;

import android.app.Fragment;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewPhotoFragment extends Fragment {

    // create Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_view_photo, container, false);
        CardInfoHolder cardInfoHolder = CardInfoHolder.getInstance();

        // initialize database and storage
        firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://intense-inferno-3371.appspot.com");

        String caption = cardInfoHolder.getCaption();
        String photo = cardInfoHolder.getPhoto();
        String userProfileImgPath = cardInfoHolder.getUserProfileImgPath();
        String userName = cardInfoHolder.getUserName();
        String location = cardInfoHolder.getLocation();
        final boolean favIcon = cardInfoHolder.getFavIcon();
        String userPostedTime = cardInfoHolder.getUserPostedTime();
        String recordId = cardInfoHolder.getRecordId();
        String userId = cardInfoHolder.getUserId();

        // set caption
        TextView captionTextView = (TextView) rootView.findViewById(R.id.user_post_caption);
        captionTextView.setText(caption);

        // set photo
        ImageView photoImageView = (ImageView) rootView.findViewById(R.id.user_post_picture);
        photoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photoImageView.setImageBitmap(convertStringToBitmap(photo));

        // set profile img
        final CircleImageView profileImage = (CircleImageView) rootView.findViewById(R.id.user_profile_img);
        StorageReference fileRef = storageRef.child("pics/" + userProfileImgPath);
        final long ONE_MEGABYTE = 1024 * 1024;
        fileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // image return as byte[] and convert to bitmap
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // dynamically add to adapter and shown in gridview
                profileImage.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                exception.printStackTrace();
                Log.d("error", "Failed to load image from Firebase storage!!!");
            }
        });
        // set username
        TextView userNameTextView = (TextView) rootView.findViewById(R.id.user_name);
        userNameTextView.setText(userName);
        // set location
        TextView locationTextView = (TextView) rootView.findViewById(R.id.post_location);
        Log.i("view photo", location);
        String[] strs = location.split(",");
        final String latitude = strs[0];
        final String longitude = strs[1];
        double lat = Double.parseDouble(latitude);
        double longit = Double.parseDouble(longitude);
        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
        String city = "Santa Clara";
        try {
            city = gcd.getFromLocation(lat, longit, 1).get(0).getLocality();
            Log.i("location", city);
        } catch (IOException e) {
            Log.i("location", e.toString());
        }
        locationTextView.setText(city);

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

    public Bitmap convertStringToBitmap(String imageString) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // Experiment with different sizes

        byte[] decodedImage = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImageByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        return decodedImageByte;
    }
}
