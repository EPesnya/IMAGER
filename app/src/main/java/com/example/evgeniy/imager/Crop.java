package com.example.evgeniy.imager;

/**
 * Created by Evgeniy on 8/6/2016.
 */
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import android.content.Context;
import android.graphics.Bitmap;

public class Crop extends BitmapTransformation {

    private BitmapPool mBitmapPool;
    private int mWidth;
    private int mHeight;

    public Crop(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
                               int outWidth, int outHeight) {
        Bitmap source = toTransform;
        int size = Math.min(source.getWidth(), source.getHeight());

        mWidth = (source.getWidth() - size) / 2;
        mHeight = (source.getHeight() - size) / 2;

        Bitmap bitmap = Bitmap.createBitmap(source, mWidth, mHeight, size, size);
        if (bitmap != source) {
            source.recycle();
        }

        return bitmap;
    }

    @Override
    public String getId() {
        return "CropSquareTransformation(width=" + mWidth + ", height=" + mHeight + ")";
    }
}
