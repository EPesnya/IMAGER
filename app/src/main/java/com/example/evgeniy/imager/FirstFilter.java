package com.example.evgeniy.imager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Evgeniy on 8/2/2016.
 */
public class FirstFilter extends BitmapTransformation {

    public FirstFilter(Context context) {
        super(context);
    }



    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap src,
                               int outWidth, int outHeight) {
        //Bitmap myTransformedBitmap = toTransform.copy( Bitmap.Config.ARGB_8888, true );
        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int pixelColor = src.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);

                int pixelRed = Color.red(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelBlue = Color.blue(pixelColor);

                int newPixel= Color.argb(
                        pixelAlpha, 255 - pixelRed, 255 - pixelGreen, 255 - pixelBlue);

                dest.setPixel(x, y, newPixel);
            }
        }

        return dest;
    }

    @Override
    public String getId() {
        // Return some id that uniquely identifies your transformation.
        return "com.example.myapp.MyTransformation";
    }
}
