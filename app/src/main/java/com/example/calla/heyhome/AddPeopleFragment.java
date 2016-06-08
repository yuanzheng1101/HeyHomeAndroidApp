package com.example.calla.heyhome;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuan on 6/5/16.
 */
public class AddPeopleFragment extends Fragment implements AdapterView.OnItemClickListener {

    // todo set listener to open Me

    List<SearchPeopleCardInfo> cardInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_people, container, false);
        cardInfo = new ArrayList<>();
        cardInfo.add(new SearchPeopleCardInfo(R.drawable.homedec_1, "name1"));
        cardInfo.add(new SearchPeopleCardInfo(R.drawable.homedec_2, "name2"));
        cardInfo.add(new SearchPeopleCardInfo(R.drawable.homedec_3, "name3"));

        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        lv.setAdapter(new SearchPeopleAdapter(getActivity(), R.layout.search_people_card_layout, cardInfo));
        lv.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchPeopleCardInfo oneCard = cardInfo.get(position);

        SearchPeopleHolder cardInfoHolder = SearchPeopleHolder.getInstance();
        cardInfoHolder.setProfilePic(oneCard.getProfileImg());
        cardInfoHolder.setUserName(oneCard.getUserName());
        openPageViewPhoto();
    }

    private void openPageViewPhoto() {
        Bundle bundle = new Bundle();
        Page_Me page = new Page_Me();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, page)
                .commit();
    }

}
