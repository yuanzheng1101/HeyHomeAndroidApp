package com.example.calla.heyhome;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.firebase.client.Firebase;


public class MainActivity extends AppCompatActivity {

    public static BottomBar mBottomBar;

    // for publish
    public static String imageFromCamera;
    public static Bitmap imageFromLibrary;
    public static Uri imageUri;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // check user logged in or not
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
        }

        // set bottom bar
        createButtomBar(savedInstanceState);

        // set Firebase
//        Firebase.setAndroidContext(this);
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();



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
//                        Intent intent = new Intent(MainActivity.this, SignIn.class);
//                        startActivity(intent);
                        break;
                    case R.id.bb_gallery:
                        openPageGallery();
                        break;
                    case R.id.bb_publish:
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
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.pink_0));
        mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.pink_1));
        mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.pink_2));
        mBottomBar.mapColorForTab(3, ContextCompat.getColor(this, R.color.pink_3));
        mBottomBar.mapColorForTab(4, ContextCompat.getColor(this, R.color.pink_4));
    }


    ///////////////////////// invoke five main fragments /////////////////////////

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




    ///////////////////////// publish a record /////////////////////////

    private void publishChoicesDialog() {
        final CharSequence[] items = { "From Camera", "From Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Publish a Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean permit = Utility.checkPermission(MainActivity.this);
                if (items[item].equals("From Camera")) {
//                    userChoosenTask="From Camera";
                    if(permit)
                        fromCamera();    // call function below
                } else if (items[item].equals("From Library")) {
//                    userChoosenTask="From Library";
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


}
