package com.example.evgeniy.imager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.util.Random;

/**
 * Created by Evgeniy on 8/2/2016.
 */
public class SecondFilter extends BitmapTransformation {

    public SecondFilter(Context context) {
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
        Random rand = new Random();
        int f = 90;

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++)
            {
                int index = y * width + x;
                int R = (srcPixels[index] >> 16) & 0xff;     //bitwise shifting
                int G = (srcPixels[index] >> 8) & 0xff;
                int B = srcPixels[index] & 0xff;

                int random = rand.nextInt((int)Math.ceil(1.5 * f)) - f;
                R = R + random;
                G = G + random;
                B = B + random;

                if (R < 0)
                    R = 0;
                if (G < 0)
                    G = 0;
                if (B < 0)
                    B = 0;
                if (R > 255)
                    R = 255;
                if (G > 255)
                    G = 255;
                if (B > 255)
                    B = 255;
                //R,G.B - Red, Green, Blue
                //to restore the values after RGB modification, use
                //next statement
                destPixels[index] = 0xff000000 | (R << 16) | (G << 8) | B;
            }}

        myTransformedBitmap.setPixels(destPixels, 0, width, 0, 0, width, height);
        return myTransformedBitmap;
    }

    @Override
    public String getId() {
        // Return some id that uniquely identifies your transformation.
        return "com.example.myapp.MyTransformation2";
    }
}
