package com.example.calla.heyhome;


import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


public class Page_Homepage extends Fragment implements AdapterView.OnItemClickListener {

    MyRecyclerAdapter ca;
    myDbHelper dbHelper;
    Cursor cursor;


    // creates and returns the view hierarchy associated with the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_page_homepage, container, false);

        //show the whole page here
        /*getActivity().deleteDatabase("users");
        getActivity().deleteDatabase("relations");
        getActivity().deleteDatabase("records");
        getActivity().deleteDatabase("galleries");
        getActivity().deleteDatabase("favorites");*/


        dbHelper = new myDbHelper(getActivity());
        cursor = dbHelper.fetchAllRecords();
        ca = new MyRecyclerAdapter(getActivity(), cursor, dbHelper, onItemClickCallback);
        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recList.setAdapter(ca);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String imageUri = "drawable://" + R.drawable.comment_icon;
                Log.i("yzheng", "clicked");
                Record rd = new Record();
                rd.caption = "test";
                dbHelper.addRecord(rd);
                cursor.requery();
                ca.notifyDataSetChanged();
                Log.i("yzheng", cursor.getCount() + "");
            }
        });

        return rootView;
    }

    @Override
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

}