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

        // initialize session
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        // initialize firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        ImageView addPeople = (ImageView) rootView.findViewById(R.id.menu_add_people);
        addPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageAddPeople();
            }
        });
        TextView followingCountTextView = (TextView) rootView.findViewById(R.id.following);
        TextView followerCountTextView = (TextView) rootView.findViewById(R.id.followers);
        getFollowingAndFollowerCount();
        FFHolder ffHolder = FFHolder.getInstance();
//        ffHolder.getFollower();
//        ffHolder.getFollowing();
        followingCountTextView.setText("Following: " + ffHolder.getFollowing());
        followerCountTextView.setText("Following: " + ffHolder.getFollower());
        final TextView followingOrEdit = (TextView) rootView.findViewById(R.id.edit_profile);
        if (userID.toString().equals(sessionManager.getCurrentUserId())) {
            followingOrEdit.setText("Edit page");
        } else {
            ffHolder = FFHolder.getInstance();
            if (ffHolder.getWhetherFollowing().equals("false")) { // if not follow
                followingOrEdit.setText("Click to follow");
            } else {
                followingOrEdit.setText("following");
            }
        }
        followingOrEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (userID.toString().equals("sessionManager.getCurrentUserId()")) {
                    return;
                } else {
                    FFHolder ffHolder = FFHolder.getInstance();
                    if (ffHolder.getWhetherFollowing().equals("false")) { // if not follow
                        followingOrEdit.setText("following");
                        ffHolder.setWhetherFollowing("true");
                        addFollowings(userID.toString());
                    } else {
                        followingOrEdit.setText("Click to follow");
                        ffHolder.setWhetherFollowing("false");
                    }
                }

            }

        });

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


    public void showRecords() {
        recordAdapter.clear();
        DatabaseReference recordRef = firebaseDatabase.getReference("RecordList");
        final RecordUserIdHolder ruih = RecordUserIdHolder.getInstance();

        recordRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {

                Record record = snapshot.getValue(Record.class);
                //String uid = sessionManager.getCurrentUserId();
                String currentRecordUserId = ruih.getUserId();
                ruih.setUserId(null);
                if (currentRecordUserId == null) {
                    currentRecordUserId = sessionManager.getCurrentUserId();
                }


                if (record.getUserId().equals(currentRecordUserId)) {

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

    public void checkFollowing(String id) {
        final String uid = sessionManager.getCurrentUserId();
        final String followingId = id;
        final DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query = userRef.orderByKey().equalTo(uid);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                String[] strs = user.getFollowings().split(",");
                for (String s : strs) {
                    if (followingId.equals(s)) {
                        // current user is following this record user
                        FFHolder ffHolder = FFHolder.getInstance();
                        ffHolder.setWhetherFollowing("true");
                    } else {
                        FFHolder ffHolder = FFHolder.getInstance();
                        ffHolder.setWhetherFollowing("false");
                    }
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

    public void addFollowings(String id) {
        final String uid = sessionManager.getCurrentUserId();
        final String followingId = id;

        final DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query1 = userRef.orderByKey().equalTo(uid);
        query1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                userRef.child(uid).child("followings").setValue(user.getFollowings() + followingId  + ",");
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

        Query query2 = userRef.orderByKey().equalTo(followingId);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                userRef.child(followingId).child("followers").setValue(user.getFollowers() + uid  + ",");
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

    public void getFollowingAndFollowerCount() {
        final String uid = sessionManager.getCurrentUserId();
        final DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query = userRef.orderByKey().equalTo(uid);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                String[] followings = user.getFollowings().split(",");
                String[] followers = user.getFollowers().split(",");

                int followingCount = 0;
                int followerCount = 0;
                if (followings.length == 1 && followings[0].equals("")) {
                    followingCount = 0;
                } else {
                    followingCount = followings.length;
                }

                if (followers.length == 1 && followers[0].equals("")) {
                    followerCount = 0;
                } else {
                    followerCount = followers.length;
                }

                FFHolder ffHolder = FFHolder.getInstance();
                ffHolder.setFollower(followerCount);
                ffHolder.setFollowing(followingCount);

                System.out.println("getFollowingAndFollowerCount" + followingCount + " & " + followerCount);

                // set Page_Me UI with followingCount and followerCount

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
