package com.example.evgeniy.imager.filters;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Evgeniy on 8/3/2016.
 */
public class RBfilter extends BitmapTransformation {

    public RBfilter(Context context) {
        super(context);
    }



    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
                               int outWidth, int outHeight) {
        Bitmap myTransformedBitmap = toTransform.copy( Bitmap.Config.ARGB_8888, true );
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int[] srcPixels = new int[width * height];
        int[] destPixels = new int[width * height];
        myTransformedBitmap.getPixels(srcPixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < srcPixels.length; i++) {
            destPixels[i] = (srcPixels[i] & 0xff00ff00) | ((srcPixels[i] & 0x000000ff) << 16)
                    | ((srcPixels[i] & 0x00ff0000) >> 16);
        }

        myTransformedBitmap.setPixels(destPixels, 0, width, 0, 0, width, height);

        return myTransformedBitmap;
    }

    @Override
    public String getId() {
        // Return some id that uniquely identifies your transformation.
        return "com.example.myapp.MyTransformation5";
    }
}