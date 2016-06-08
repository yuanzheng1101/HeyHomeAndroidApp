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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Page_Homepage extends Fragment implements AdapterView.OnItemClickListener {

    List<CardInfo> cardInfo;
    private MyArrayAdapter recordAdapter;

    private FirebaseDatabase firebaseDatabase;


    // creates and returns the view hierarchy associated with the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_page_homepage, container, false);

        //initialize firebase
        firebaseDatabase = FirebaseDatabase.getInstance();

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
        CardInfoHolder cardInfoHolder = CardInfoHolder.getInstance();
        cardInfoHolder.setCaption(oneCard.getUserPostedCaption());
        cardInfoHolder.setPhoto(oneCard.getUserPostedPhoto());
        cardInfoHolder.setUserProfileImgPath(oneCard.getUserProfileImgPath());
        cardInfoHolder.setUserName(oneCard.getUserName());
        cardInfoHolder.setLocation(oneCard.getLocation());
        cardInfoHolder.setFavIcon(oneCard.getFavIcon());
        cardInfoHolder.setUserPostedTime(oneCard.getUserPostedTime());
        cardInfoHolder.setRecordId(oneCard.getRecordId());
        cardInfoHolder.setUserId(oneCard.getUserId());

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

                String uid = "FIfBOs96HHhmITijarvjx5MDGnI2";
                if (record.getUserId().equals(uid)) {

                    CardInfo card = new CardInfo(record.getUserImage(), record.getUserName(), record.getLocation(),
                            record.getCaption(), record.getImage(), false, record.getTime(), snapshot.getKey(), record.getUserId());

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
