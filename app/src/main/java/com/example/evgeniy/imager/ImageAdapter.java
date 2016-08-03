package com.example.evgeniy.imager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.File;

/**
 * Created by Evgeniy on 7/31/2016.
 */
public class ImageAdapter extends ArrayAdapter<File> {

    LayoutInflater mInflater;
    RequestManager mPicasso;

    public ImageAdapter(Context context, File[] objects) {
        super(context, R.layout.grid_item, objects);
        mInflater = LayoutInflater.from(context);
        mPicasso = Glide.with(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.grid_item, parent, false);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.picture);
        mPicasso.load(getItem(position))
                .centerCrop()
                .error(R.drawable.ic_action_name)
                .into(imageView);
        return view;
    }
}
