package com.example.calla.heyhome;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Page_Homepage extends Fragment implements AdapterView.OnItemClickListener {

    List<CardInfo> cardInfo;
    private MyArrayAdapter recordAdapter;

    private FirebaseDatabase firebaseDatabase;

    private final StringBuilder userID = new StringBuilder();
    private final StringBuilder userName = new StringBuilder();
    private final StringBuilder recordLocation = new StringBuilder();
    private final StringBuilder recordCaption = new StringBuilder();
    private final StringBuilder recordImage = new StringBuilder();
    private final StringBuilder recordTime = new StringBuilder();
    private final StringBuilder userPhoto = new StringBuilder();


    // creates and returns the view hierarchy associated with the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_page_homepage, container, false);

        //initialize firebase
        firebaseDatabase = FirebaseDatabase.getInstance();

        // todo only two parameter here, need to change
        cardInfo = new ArrayList<>();
//        cardInfo.add(new CardInfo(R.drawable.homedec_1, "caption1"));
//        cardInfo.add(new CardInfo(R.drawable.homedec_2, "caption2"));
//        cardInfo.add(new CardInfo(R.drawable.homedec_3, "caption3"));
//        cardInfo.add(new CardInfo(R.drawable.homedec_4, "caption4"));
//        cardInfo.add(new CardInfo(R.drawable.homedec_5, "caption5"));
//        cardInfo.add(new CardInfo(R.drawable.homedec_6, "caption6"));
//        cardInfo.add(new CardInfo(R.drawable.homedec_7, "caption7"));


        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        recordAdapter = new MyArrayAdapter(getActivity(), R.layout.home_card_layout, cardInfo);
        lv.setAdapter(recordAdapter);
        lv.setOnItemClickListener(this);

        showRecords();

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CardInfo oneCard = cardInfo.get(position);
        Toast.makeText(getActivity(), "clicked : " + oneCard.getUserPostedCaption(), Toast.LENGTH_SHORT).show();
        CardInfoHolder cardInfoHolder = CardInfoHolder.getInstance();
        cardInfoHolder.setCaption(oneCard.getUserPostedCaption());
        cardInfoHolder.setPhoto(oneCard.getUserPostedPhoto());
//        openPageViewPhoto();
    }


    private void openPageViewPhoto() {
        Bundle bundle = new Bundle();
        ViewPhotoFragment page = new ViewPhotoFragment();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }

    // calla: for load data from db
    public void showRecords() {

        recordAdapter.clear();
        DatabaseReference userRef = firebaseDatabase.getReference("RecordList");

        Log.d("position", "in showRecords()");

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Log.d("position", "in onChildAdded()");

                Record record = snapshot.getValue(Record.class);

                String uid = "Dh3yqkxgcPTtirb5VMyajBQvQwA3";

                if (record.getUser().equals(uid)) {
                    System.out.println(record.getImage().length());

                    userID.delete(0, userID.length());
                    userID.append(record.getUser());

                    userName.delete(0, userName.length());
                    userPhoto.delete(0, userPhoto.length());
                    recordCaption.delete(0, recordCaption.length());
                    recordImage.delete(0, recordImage.length());
                    recordLocation.delete(0, recordLocation.length());
                    recordTime.delete(0, recordTime.length());

                    getUserInfo();

                    System.out.println(userName.toString() + "here!!!!!");

                    CardInfo card = new CardInfo(userPhoto.toString(), userName.toString(), record.getLocation(),
                            record.getCaption(), record.getImage(), false, record.getTime());

//                    recordAdapter.addCardInfo(card);

                } else {
                    Log.d("position", "fail to add to list");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            public void getUserInfo() {
                Log.d("position", "in getUserInfo()");
                DatabaseReference userRef = firebaseDatabase.getReference("UserList");
                System.out.println(userID.toString());
                Query query = userRef.orderByKey().equalTo(userID.toString());

                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                        String uid = userID.toString();
                        if (snapshot.getKey().equals(uid)) {
                            User user = snapshot.getValue(User.class);

                            userName.append(user.getName());
                            userPhoto.append(user.getPhoto());

                            CardInfo card = new CardInfo(userPhoto.toString(), userName.toString(), recordLocation.toString(),
                                    recordCaption.toString(), recordImage.toString(), false, recordTime.toString());
                            recordAdapter.addCardInfo(card);

                            userName.delete(0, userName.length());
                            userPhoto.delete(0, userPhoto.length());


                            System.out.println("getUserInfo: " + user.getName());
                            System.out.println("getUserInfo: " + user.getPhoto().length());


                        } else {
                            Log.d("position", "wrong user");
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }


        });

    }
}
