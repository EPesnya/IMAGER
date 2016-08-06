package com.example.evgeniy.imager.filters;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Evgeniy on 8/3/2016.
 */
public class GBFilter extends BitmapTransformation {

    public GBFilter(Context context) {
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
/*
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++)
            {
                int index = y * width + x;
                int R = (srcPixels[index] >> 16) & 0xff;     //bitwise shifting
                int G = (srcPixels[index] >> 8) & 0xff;
                int B = srcPixels[index] & 0xff;


                destPixels[index] = 0xff000000 | ((255 - R) << 16) | ((255 - G) << 8) | (255 - B);
            }}
*/
        for (int i = 0; i < srcPixels.length; i++) {
            destPixels[i] = (srcPixels[i]  & 0xffff0000) | ((srcPixels[i] & 0x000000ff) << 8)
                    | ((srcPixels[i] & 0x0000ff00) >> 8);
        }

        myTransformedBitmap.setPixels(destPixels, 0, width, 0, 0, width, height);

        return myTransformedBitmap;
    }

    @Override
    public String getId() {
        // Return some id that uniquely identifies your transformation.
        return "com.example.myapp.MyTransformation4";
    }
}