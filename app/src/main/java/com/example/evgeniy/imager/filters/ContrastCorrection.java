package com.example.evgeniy.imager.filters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.Toast;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by SanQri on 07.08.2016.
 */

public class ContrastCorrection extends BitmapTransformation {

    public static double correction = 2;

    public ContrastCorrection(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
                               int outWidth, int outHeight) {
        double k = 1 + correction / 100.0;

        Bitmap myTransformedBitmap = toTransform.copy( Bitmap.Config.ARGB_8888, true );
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int[] srcPixels = new int[width * height];
        int[] destPixels = new int[width * height];
        myTransformedBitmap.getPixels(srcPixels, 0, width, 0, 0, width, height);

        int ab = 0;
        for (int i = 0; i < srcPixels.length; i++) {
            int R = (srcPixels[i] >> 16) & 0xff;     //bitwise shifting
            int G = (srcPixels[i] >> 8) & 0xff;
            int B = srcPixels[i] & 0xff;
            ab += R+G+B;
        }
        ab /= 3.0;
        ab /= srcPixels.length;

        int[] arr = new int[256];

        for (int i = 0; i < 255; i++) {
            int delta = i - ab;
            int tmp = (int)(ab + k * delta);

            if(tmp > 255)
                arr[i] = 255;
            else if(tmp < 0)
                arr[i] = 0;
            else
                arr[i] = tmp;
        }

        for (int i = 0; i < srcPixels.length; i++) {
            int red = arr[(srcPixels[i] >> 16) & 0xff];
            int green = arr[(srcPixels[i] >> 8) & 0xff];
            int blue = arr[srcPixels[i] & 0xff];
            destPixels[i] = 0xff000000 | (red << 16) | (green << 8) | blue;
        }
        myTransformedBitmap.setPixels(destPixels, 0, width, 0, 0, width, height);

        return myTransformedBitmap;
    }

    @Override
    public String getId() {
        // Return some id that uniquely identifies your transformation.
        return "com.example.myapp.MyTransformation228";
    }
}
