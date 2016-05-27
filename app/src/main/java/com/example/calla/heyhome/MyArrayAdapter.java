package com.example.calla.heyhome;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by yuan on 5/24/16.
 */
public class MyArrayAdapter extends ArrayAdapter<CardInfo> {

    private final List<CardInfo> cardInfo;
    private Context ctxt;

    public MyArrayAdapter(Context context, int resource, List<CardInfo> cardInfo) {
        super(context, resource, cardInfo);
        this.cardInfo = cardInfo;
        ctxt = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ScrapViewHolder holder;
        View row = convertView;

        if (row == null) {
            Log.i("yzheng", "Row is null; Need to be inflated.");

            //row = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_layout, parent, false);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.home_card_layout, parent, false);
            holder = new ScrapViewHolder();

            holder.vUserProfileImg = (ImageView) row.findViewById(R.id.user_profile_img);
            holder.vUserName = (TextView) row.findViewById(R.id.user_name);
            holder.vLocation = (TextView) row.findViewById(R.id.post_location);
            holder.vUserPostedCaption = (TextView) row.findViewById(R.id.user_post_caption);
            holder.vUserPostedPhoto = (ImageView) row.findViewById(R.id.user_post_picture);
            holder.vFavIcon = (ImageView) row.findViewById(R.id.user_post_fav_icon);
            holder.vUserPostedTime = (TextView) row.findViewById(R.id.user_post_time);
            holder.vFriendName = (TextView) row.findViewById(R.id.friend_comment_friend_name);
            holder.vFriendComment = (TextView) row.findViewById(R.id.friend_comment_friend_comment);
            row.setTag(holder);
        } else {
            Log.i("yzheng", "Row is NOT null; reusing it!");
            holder = (ScrapViewHolder) row.getTag();
        }

        holder.vUserPostedCaption.setText(cardInfo.get(position).getUserPostedCaption());

        int fileName = cardInfo.get(position).getUserPostedPhoto();
        holder.vUserPostedPhoto.setImageResource(fileName);
        holder.vUserPostedPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // holder.menu.setImageResource(R.mipmap.ic_launcher);

        holder.vUserPostedPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "MENU clicked for " + cardInfo.get(position).getUserPostedCaption();
                Toast.makeText(ctxt, msg, Toast.LENGTH_SHORT).show();
            }
        });

        return row;
    }

    public class ScrapViewHolder {
        ImageView vUserProfileImg;
        TextView vUserName;
        TextView vLocation;
        TextView vUserPostedCaption;
        ImageView vUserPostedPhoto;
        ImageView vFavIcon;
        TextView vUserPostedTime;
        TextView vFriendName;
        TextView vFriendComment;
    }
}


    /*public ArrayAdapter(Context context, Cursor c, myDbHelper dbHelper, OnItemClickListener.OnItemClickCallback onItemClickCallback) {

        this.mContext = context;
        this.cursor = c;
        this.dbHelper = dbHelper;
        this.onItemClickCallback = onItemClickCallback;
        this.dbHelper = dbHelper;


        mCursorAdapter = new CursorAdapter(mContext, c, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.home_card_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                String description = cursor.getString(cursor.getColumnIndex("caption"));
                ((TextView) view.findViewById(R.id.user_post_caption)).setText(description);
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView vUserProfileImg;
        TextView vUserName;
        TextView vLocation;
        TextView vUserPostedCaption;
        ImageView vUserPostedPhoto;
        ImageView vFavIcon;
        TextView vUserPostedTime;
        TextView vFriendName;
        TextView vFriendComment;

        public ViewHolder(View itemView) {
            super(itemView);
            vUserProfileImg = (ImageView) itemView.findViewById(R.id.user_profile_img);
            vUserName = (TextView) itemView.findViewById(R.id.user_name);
            vLocation = (TextView) itemView.findViewById(R.id.post_location);
            vUserPostedCaption = (TextView) itemView.findViewById(R.id.user_post_caption);
            vUserPostedPhoto = (ImageView) itemView.findViewById(R.id.user_post_picture);
            vFavIcon = (ImageView) itemView.findViewById(R.id.user_post_fav_icon);
            vUserPostedTime = (TextView) itemView.findViewById(R.id.user_post_time);
            vFriendName = (TextView) itemView.findViewById(R.id.friend_comment_friend_name);
            vFriendComment = (TextView) itemView.findViewById(R.id.friend_comment_friend_comment);
        }
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        long l = mCursorAdapter.getItemId(position);
        Cursor c = dbHelper.getCard((int) l);


        //set user profile picture
        String profilePic = cursor.getString(cursor.getColumnIndexOrThrow("photo"));
        holder.vUserProfileImg.setImageURI(Uri.parse(profilePic));

        //set user name
        String userName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        holder.vUserName.setText(userName);

        //set location
        //todo set location

        //set user posted caption
        String userPostedCaption = cursor.getString(cursor.getColumnIndexOrThrow("caption"));
        holder.vUserPostedCaption.setText(userPostedCaption);

        //set user posted photo
        String userPostedPhoto = cursor.getString(cursor.getColumnIndexOrThrow("image"));
        if (userPostedPhoto == null || userPostedPhoto.length() == 0) {
            userPostedPhoto = "drawable://" + R.drawable.comment_icon;
        }
        holder.vUserPostedPhoto.setImageURI(Uri.parse(userPostedPhoto));


        // set user posted time
        // todo get vUserPostedTime

        //todo get photo comment


        //view photo
        holder.vUserPostedPhoto.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));

        //mark favorite
        holder.vFavIcon.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_layout, parent, false);
        return new ViewHolder(v);
    }*/

