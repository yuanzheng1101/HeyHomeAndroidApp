package com.example.calla.heyhome;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Page_Publish extends Fragment {
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_page_publish, container, false);
        //show the whole page here

        imageView = (ImageView) rootView.findViewById(R.id.image);
        // photo from camera
        if (MainActivity.imageUri != null) {
//            imageView.setImageBitmap(null);
            imageView.setImageURI(MainActivity.imageUri);
            MainActivity.imageUri = null;

        }
//        // photo from library
//        if (MainActivity.imageFromLibrary != null) {
////            imageView.setImageURI(null);
//            imageView.setImageBitmap(MainActivity.imageFromLibrary);
////            MainActivity.imageFromLibrary = null;
//        }


        // re-take photo
        Button buttom = (Button) rootView.findViewById(R.id.button);
//        buttom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                publishChoicesDialog();
//            }
//        });

        return rootView;
    }

//    private Bitmap convertToBitmap(){
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 8; // Experiment with different sizes
//        Bitmap pic_bitmap = BitmapFactory.decodeFile(pictureImagePath, options);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        pic_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] bytes = baos.toByteArray();
//        pic = Base64.encodeToString(bytes, Base64.DEFAULT);
//        Log.d("base64", pic);
//        return pic_bitmap;
//    }

//    String userChoosenTask;
//    String fileName;
//
//    private void publishChoicesDialog() {
//        final CharSequence[] items = { "From Camera", "From Library", "Cancel" };
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Publish a Photo");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                Log.v("calla", "position is: " + item);
//                boolean permit = Utility.checkPermission(getActivity());
//                if (items[item].equals("From Camera")) {
//                    userChoosenTask="From Camera";
//                    if(permit)
//                        fromCamera();    // call function below
//                } else if (items[item].equals("From Library")) {
//                    userChoosenTask="From Library";
//                    if(permit)
//                        fromLibrary();   // call function below
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//
//    private void fromCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(getActivity().getPackageManager()) == null) {
//            Toast.makeText(getActivity().getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        fileName = getOutputFileName();
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fileName));
//
//        startActivityForResult(intent, 1234);
//
//    }
//
//    private void fromLibrary() {
//        Intent intent = new Intent();
//        intent.setType("image/*");  // "image/* video/*" for both images and videos
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select File"), 4321);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        if (requestCode != 1234 || requestCode != 4321 || resultCode != getActivity().RESULT_OK) return;
//
//        switch (requestCode) {
//            case 1234:   // from camera
//                ImageView imageView = (ImageView) getView().findViewById(R.id.image);
//                imageView.setImageURI(Uri.parse(fileName));
//                System.out.println(Uri.parse(fileName));
//                break;
//            case 4321:   // from library
//                saveImageFromLibrary(data);
//                break;
//        }
//
//    }
//
//    private void saveImageFromLibrary(Intent data) {
//        Bitmap bm=null;
//        if (data != null) {
//            try {
//                bm = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), data.getData());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        ImageView imageView = (ImageView) getView().findViewById(R.id.image);
//        imageView.setImageBitmap(bm);
//
//    }
//
//
//    private String getOutputFileName() {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String filename =
//                "file://"
//                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                        + "/JPEG_"
//                        + timeStamp
//                        + ".jpg";
//        return filename;
//    }



}
