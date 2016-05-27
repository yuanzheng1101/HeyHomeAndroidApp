package com.example.calla.heyhome;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

//import com.firebase.client.Firebase;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private BottomBar mBottomBar;
    private DBFirebase dbFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set bottom bar
        createButtomBar(savedInstanceState);

        //create Firebase
        Firebase.setAndroidContext(this);
        dbFirebase = new DBFirebase();


        //test firebase button
//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                User user1 = new User("", "abc", "jim@scu", "aaaaaa", "path", 0, 0);
//                User user2 = new User("LiLei", "dddd", "dave@scu", "bbbbbbbb", "oooo", 1, 2);
//                dbFirebase.addUser(user1);
//                dbFirebase.addUser(user2);
//
//            }
//        });
//
//        Button button2 = (Button) findViewById(R.id.button2);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = "Zhang";
//                dbFirebase.searchUser(name);
//
//            }
//        });






        FirebaseStorage storage = FirebaseStorage.getInstance();

        ImageView image = (ImageView) findViewById(R.id.imageView);

//        String path = "http://www.bachmanbuilders.com/img/Anna%20Mae%20Drive.jpg";


        // hard coed test for image
//        try {
//            InputStream inputStream = getApplicationContext().getAssets().open("icon_dog.png");
//            Drawable drawable = Drawable.createFromStream(inputStream, null);
//            image.setImageDrawable(drawable);
//            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        StorageReference storageRef = storage.getReferenceFromUrl("gs://intense-inferno-3371.appspot.com");
        StorageReference fileRef = storageRef.child("images/image4.jpg");
        String path = fileRef.getPath();
        String name = fileRef.getName();
        System.out.println(path);
        System.out.println(name);
//
//
        final long ONE_MEGABYTE = 512 * 512;
        fileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/image1.png" is returns, use this as needed
                System.out.println("Imgae has been stored into byte[] bytes");

//                image.setImageURI(Uri.parse("ddd"));
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ImageView view = (ImageView) findViewById(R.id.imageView);
                view.setImageBitmap(bm);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                System.out.println("Failed to load image from Firebase!!!");
            }
        });


//        image.setImageURI(storageRef.child("images/image1.png").getDownloadUrl().getResult());


        //////// upload files //////////////////
        // Get the data from an ImageView as bytes
//        image.setDrawingCacheEnabled(true);
//        image.buildDrawingCache();
//        Bitmap bitmap = image.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//        System.out.println(data.length);
//
//        StorageReference mountainsRef = storageRef.child("images/mountains.jpg");
//
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//                System.out.println("Upload image error!!!");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//            }
//        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    public void createButtomBar(Bundle savedInstanceState) {
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.bb_homepage:
                        openPageHomepage();
                        break;
                    case R.id.bb_gallery:
                        openPageGallery();
                        break;
                    case R.id.bb_publish:
//                        openPagePublish();
                        publishChoicesDialog();
                        break;
                    case R.id.bb_favorite:
                        openPageFavorite();
                        break;
                    case R.id.bb_me:
                        openPageMe();
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                //todo reselect

            }


        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
        mBottomBar.mapColorForTab(4, "#FF9800");
    }


    // invoke five main fragments

    private void openPageHomepage() {
        Bundle bundle = new Bundle();
        Page_Homepage page = new Page_Homepage();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }

    private void openPageGallery() {
        Bundle bundle = new Bundle();
        Page_Gallery page = new Page_Gallery();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }

    private void openPagePublish() {
        Bundle bundle = new Bundle();
        Page_Publish page = new Page_Publish();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }

    private void openPageFavorite() {
        Bundle bundle = new Bundle();
        Page_Favorite page = new Page_Favorite();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }

    private void openPageMe() {
        Bundle bundle = new Bundle();
        Page_Me page = new Page_Me();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }


    ///////////////////////// publish /////////////////////////

    String userChoosenTask;
    public static String imageFromCamera;
    public static Bitmap imageFromLibrary;
    public static Uri imageUri;

    private void publishChoicesDialog() {
        final CharSequence[] items = { "From Camera", "From Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Publish a Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                Log.v("calla", "position is: " + item);
                boolean permit = Utility.checkPermission(MainActivity.this);
                if (items[item].equals("From Camera")) {
                    userChoosenTask="From Camera";
                    if(permit)
                        fromCamera();    // call function below
                } else if (items[item].equals("From Library")) {
                    userChoosenTask="From Library";
                    if(permit)
                        fromLibrary();   // call function below
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void fromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
            return;
        }

        imageFromCamera = getOutputFileName();
        imageUri = Uri.parse(imageFromCamera);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(intent, 1234);
    }

    private void fromLibrary() {
        Intent intent = new Intent();
        intent.setType("image/*");  // "image/* video/*" for both images and videos
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select pictures"), 4321);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("request code: " + requestCode + "; result code: " + resultCode);
//        if (requestCode != 1234 || requestCode != 4321 || resultCode != RESULT_OK) return;

        switch (requestCode) {
            case 1234:   // from camera
                openPagePublish();
                break;
            case 4321:   // from library
//                saveImageFromLibrary(data);

                imageUri = data.getData();
                System.out.println(imageUri);

                openPagePublish();
                break;
        }

    }


    private void saveImageFromLibrary(Intent data) {
        if (data != null) {
            try {
                imageFromLibrary = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getOutputFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename =
                "file://"
                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/JPEG_"
                        + timeStamp
                        + ".jpg";
        return filename;
    }




    //////////////////////////////////////////////////
    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}
