package com.example.calla.heyhome;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Page_Homepage extends Fragment implements AdapterView.OnItemClickListener {

    List<CardInfo> cardInfo;


    // creates and returns the view hierarchy associated with the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_page_homepage, container, false);


        cardInfo = new ArrayList<>();
        cardInfo.add(new CardInfo(R.drawable.homedec_1, "caption1"));
        cardInfo.add(new CardInfo(R.drawable.homedec_2, "caption2"));
        cardInfo.add(new CardInfo(R.drawable.homedec_3, "caption3"));
        cardInfo.add(new CardInfo(R.drawable.homedec_4, "caption4"));
        cardInfo.add(new CardInfo(R.drawable.homedec_5, "caption5"));
        cardInfo.add(new CardInfo(R.drawable.homedec_6, "caption6"));
        cardInfo.add(new CardInfo(R.drawable.homedec_7, "caption7"));


        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        lv.setAdapter(new MyArrayAdapter(getActivity(), R.layout.home_card_layout, cardInfo));
        lv.setOnItemClickListener(this);

        //show the whole page here
        /*getActivity().deleteDatabase("users");
        getActivity().deleteDatabase("relations");
        getActivity().deleteDatabase("records");
        getActivity().deleteDatabase("galleries");
        getActivity().deleteDatabase("favorites");*/


        /*dbHelper = new myDbHelper(getActivity());
        cursor = dbHelper.fetchAllRecords();
        cursor.moveToFirst();
        ca = new MyArrayAdapter(getActivity(), cursor, dbHelper, onItemClickCallback);
        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recList.setAdapter(ca);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);*/

        /*FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageUri = "drawable://" + R.drawable.comment_icon;
                Record rd = new Record(1, "test", imageUri, "05/24/2016");
                rd.caption = "test";
                dbHelper.addRecord(rd);
                cursor = dbHelper.fetchAllRecords();
                boolean boo = cursor.moveToFirst();
//                boolean boo = cursor.requery();
                Log.i("yzheng", boo + "");
                cursor = dbHelper.fetchAllRecords();
//                ca.notifyDataSetChanged();
                Log.i("yzheng", cursor.getCount() + "");
            }
        });*/

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CardInfo oneCard = cardInfo.get(position);
        Toast.makeText(getActivity(), "clicked : " + oneCard.getUserPostedCaption(), Toast.LENGTH_SHORT).show();
    }
}

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Yes!!!!", Toast.LENGTH_SHORT).show();
    }

    //goes to view photo
    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            Intent intent = new Intent(getActivity(), ViewPhoto.class);

            long pid = ca.mCursorAdapter.getItemId(position);

            Cursor c = dbHelper.getCardObject((int) pid);
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String caption = cursor.getString(cursor.getColumnIndex("caption"));

            intent.putExtra("viewPhoto", image);
            intent.putExtra("caption", caption);
            startActivity(intent);
        }
    };

    //mark fav
    // todo mark fav*/

