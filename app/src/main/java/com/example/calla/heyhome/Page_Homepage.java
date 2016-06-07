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

        // todo only two parameter here, need to change
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


    private void openPageViewPhoto() {
        Bundle bundle = new Bundle();
        ViewPhotoFragment page = new ViewPhotoFragment();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }
}
