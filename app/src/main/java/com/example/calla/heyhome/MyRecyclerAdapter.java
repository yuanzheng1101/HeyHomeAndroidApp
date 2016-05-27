package com.example.calla.heyhome;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yuan on 5/24/16.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    CursorAdapter mCursorAdapter;
    Context mContext;
    Cursor cursor;
    myDbHelper dbHelper;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;

    public MyRecyclerAdapter(Context context, Cursor c, myDbHelper dbHelper, OnItemClickListener.OnItemClickCallback onItemClickCallback) {

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
    }
}
