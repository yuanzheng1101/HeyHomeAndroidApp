package com.example.calla.heyhome;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;


public class Page_Me extends Fragment {

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

    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_page_me, container, false);
        //show the whole page here

        sessionManager = new SessionManager(getActivity().getApplicationContext());

        //initialize firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        ImageView addPeople = (ImageView) rootView.findViewById(R.id.menu_add_people);
        addPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageAddPeople();
            }
        });
        final TextView followingOrEdit = (TextView) rootView.findViewById(R.id.edit_profile);
        if (userID.toString().equals("11111")) { // todo if it's current user
            followingOrEdit.setText("Edit page");
        } else {
            if (false) { // todo if not follow
                followingOrEdit.setText("Click to follow");
            } else {
                followingOrEdit.setText("following");
            }
        }
        followingOrEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (userID.toString().equals("11111")) { // todo if it's current user
                    return;
                } else {
                    if (false) { // if not follow
                        followingOrEdit.setText("following");
                        // todo set following as true
                    } else {
                        followingOrEdit.setText("Click to follow");
                        // todo set following as false;
                    }
                }

            }

        });

        // todo only two parameter here, need to change
        cardInfo = new ArrayList<>();


        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        recordAdapter = new MyArrayAdapter(getActivity(), R.layout.home_card_layout, cardInfo);
        lv.setAdapter(recordAdapter);
        showRecords();

        return rootView;
    }

    private void openPageAddPeople() {
        Bundle bundle = new Bundle();
        AddPeopleFragment page = new AddPeopleFragment();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.mainFragment, page).commit();

    }


    // calla: for load data from db
    public void showRecords() {
        recordAdapter.clear();
        DatabaseReference recordRef = firebaseDatabase.getReference("RecordList");

        recordRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Record record = snapshot.getValue(Record.class);


                String uid = sessionManager.getCurrentUserId();
//                String uid = "FIfBOs96HHhmITijarvjx5MDGnI2";
                System.out.println("current user id: " + uid);
                if (record.getUserId().equals(uid)) {

                    CardInfo card = new CardInfo(record.getUserImage(), record.getUserName(), record.getLocation(),
                            record.getCaption(), record.getImage(), false, record.getTime(), snapshot.getKey());

                    recordAdapter.addCardInfo(card);

                } else {
                    Log.d("position", "wrong record");
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
}
