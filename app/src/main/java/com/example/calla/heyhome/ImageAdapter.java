package com.example.calla.heyhome;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Calla on 5/26/16.
 */
public class ImageAdapter extends BaseAdapter{

    private Context context;
    private List<Bitmap> smallImages;

    public ImageAdapter(Context callingActivityContext, List<Bitmap> thumbnails) {
        context = callingActivityContext;
        smallImages = new ArrayList<>();
        smallImages.addAll(thumbnails);
    }

    // how many entries are there in the data set
    public int getCount() {
        return smallImages.size();
    }

    // what is in a given 'position' in the data set
    public Object getItem(int position) {
        return smallImages.get(position);
    }

    // what is the ID of data item in given 'position'
    public long getItemId(int position) {
        return position;
    }

    // create a view for each thumbnail in the data set
    public View getView(int position, View convertView, ViewGroup parent) {
        SquareImageView imageView;
        // if possible, reuse (convertView) image already held in cache
        if (convertView == null) {
            // new image in GridView formatted to:
            // match parent dimension suggestions
            // using SquareImageView to force highet matching width
            // center-cropped, and 0dp padding/margin around
            imageView = new SquareImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (SquareImageView) convertView;
        }
        imageView.setImageBitmap(smallImages.get(position));

        return imageView;
    }

    public void addBitmap(Bitmap bitmap) {
        smallImages.add(bitmap);
        notifyDataSetChanged();
    }

    public void clear() {
        for (int i = 0; i < smallImages.size(); i++) {
            smallImages.get(i).recycle();
        }
        smallImages.clear();
    }

    public class SquareImageView extends ImageView {
        public SquareImageView(Context context) {
            super(context);
        }
        @Override
        public void onMeasure(int width, int height) {
            super.onMeasure(width, width);
        }
    }
}
