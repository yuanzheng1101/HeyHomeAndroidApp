package com.example.calla.heyhome;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;


public class Page_Homepage extends Fragment implements AdapterView.OnItemClickListener {

    List<CardInfo> cardInfo;
    private MyArrayAdapter recordAdapter;

    private FirebaseDatabase firebaseDatabase;
    private SessionManager sessionManager;

    final private ArrayList<String> followings = new ArrayList<>();


    // creates and returns the view hierarchy associated with the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_page_homepage, container, false);

        // initialize firebase
        firebaseDatabase = FirebaseDatabase.getInstance();

        // instantiate session
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        // get followings
        getFollowings();

        // todo only two parameter here, need to change
        cardInfo = new ArrayList<>();

        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        recordAdapter = new MyArrayAdapter(getActivity(), R.layout.home_card_layout, cardInfo);
        lv.setAdapter(recordAdapter);
        lv.setOnItemClickListener(this);

        ImageView addPeople = (ImageView) rootView.findViewById(R.id.menu_add_people);
        addPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageAddPeople();
            }
        });

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
        openPageViewPhoto();
    }

    private void openPageAddPeople() {
        Bundle bundle = new Bundle();
        AddPeopleFragment page = new AddPeopleFragment();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.mainFragment, page).commit();

    }


    private void openPageViewPhoto() {
        Bundle bundle = new Bundle();
        ViewPhotoFragment page = new ViewPhotoFragment();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }


    public void showRecords() {
        recordAdapter.clear();
        DatabaseReference recordRef = firebaseDatabase.getReference("RecordList");

        recordRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Record record = snapshot.getValue(Record.class);

//                String uid = "FIfBOs96HHhmITijarvjx5MDGnI2";
                if (followings.contains(record.getUserId())) {

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

    public void getFollowings() {
        followings.clear();
        final String uid = sessionManager.getCurrentUserId();

        DatabaseReference userRef = firebaseDatabase.getReference("UserList");
        Query query = userRef.orderByKey().equalTo(uid);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                String[] strs = user.getFollowings().split(",");
                for (String s : strs) {
                    followings.add(s);
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

    public void addFollowings(String followingId) {
        final String uid = sessionManager.getCurrentUserId();
        final String id = followingId;

        final DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query = userRef.orderByKey().equalTo(uid);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                userRef.child(uid).child("followings").setValue(user.getFollowings() + id  + ",");
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
