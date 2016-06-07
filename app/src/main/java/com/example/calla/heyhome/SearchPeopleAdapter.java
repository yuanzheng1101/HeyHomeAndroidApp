package com.example.calla.heyhome;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yuan on 6/6/16.
 */
public class SearchPeopleAdapter extends ArrayAdapter<SearchPeopleCardInfo> {

    private final List<SearchPeopleCardInfo> cardInfo;
    private Context context;
    FragmentManager fm;

    public SearchPeopleAdapter(Context context, int resource, List<SearchPeopleCardInfo> cardInfo) {
        super(context, resource, cardInfo);
        this.cardInfo = cardInfo;
        this.context = context;
        fm = ((Activity) context).getFragmentManager();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ScrapViewHolder holder;
        View row = convertView;

        if (row == null) {
            Log.i("yzheng", "Row is null; Need to be inflated.");

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.search_people_card_layout, parent, false);
            holder = new ScrapViewHolder();
            holder.vUserProfileImg = (ImageView) row.findViewById(R.id.result_image);
            holder.vUserName = (TextView) row.findViewById(R.id.user_name);
            row.setTag(holder);
        } else {
            Log.i("yzheng", "Row is NOT null; reusing it!");
            holder = (ScrapViewHolder) row.getTag();
        }

        // set profile image
        // todo set profile image

        // set user name
        holder.vUserName.setText(cardInfo.get(position).getUserName());
        return row;
    }

    public class ScrapViewHolder {
        ImageView vUserProfileImg;
        TextView vUserName;
    }

}
