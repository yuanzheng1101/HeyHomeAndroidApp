package com.example.calla.heyhome;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Page_Publish extends Fragment {
    private DBFirebase dbFirebase;

    ImageView imageView;
    EditText caption;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_page_publish, container, false);

        // instantiate databse
        dbFirebase = new DBFirebase();

        // instantiate components
        imageView = (ImageView) rootView.findViewById(R.id.image);
        caption = (EditText) rootView.findViewById(R.id.caption);

        // set preview photo from camera
        if (MainActivity.imageUri != null) {
            imageView.setImageURI(MainActivity.imageUri);
        }


//        if (MainActivity.imageFromCamera != null) {
//            Uri uri = Uri.parse(MainActivity.imageFromCamera);
//            System.out.println(MainActivity.imageFromCamera);
//            System.out.println(uri.toString());
//            imageView.setImageURI(uri);
//            MainActivity.imageFromCamera = null;
//        }
//        // photo from library
//        if (MainActivity.imageFromLibrary != null) {
////            imageView.setImageURI(null);
//            imageView.setImageBitmap(MainActivity.imageFromLibrary);
////            MainActivity.imageFromLibrary = null;
//        }


        // publish button
        Button buttom = (Button) rootView.findViewById(R.id.save);
        buttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get time
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                // get caption
                EditText tv = (EditText) getActivity().findViewById(R.id.caption);
                String caption = tv.getText().toString();

                // get image
                String picString = imageToString();
                Record record = new Record(89, caption, picString, timeStamp);
                dbFirebase.addRecord(record);

                // jump to homepage
                Bundle bundle = new Bundle();
                Page_Homepage page = new Page_Homepage();
                page.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.mainFragment, page)
                        .commit();
                MainActivity.mBottomBar.selectTabAtPosition(0, true);
            }
        });

        return rootView;
    }


    private String imageToString(){
        String fileName = MainActivity.imageUri.toString();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // Experiment with different sizes
        Bitmap pic_bitmap = BitmapFactory.decodeFile(fileName.substring(7), options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pic_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String picString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return picString;
    }



}
