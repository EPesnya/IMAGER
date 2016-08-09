package com.example.evgeniy.imager.filters;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by SanQri on 07.08.2016.
 */

public class BlurMatrixFilter extends BitmapTransformation {
    double correction = 1;

    public BlurMatrixFilter(Context context, double number) {
        super(context);
        correction = number + 1;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
                               int outWidth, int outHeight) {
        double[][] kernel = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };/*
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                kernel[i][j] /= correction;*/
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int kernelWidth = kernel.length;
        int kernelHeight = kernel[0].length;
        int[] srcPixels = new int[width * height];
        int[] destPixels = new int[width * height];
        toTransform.getPixels(srcPixels, 0, width, 0, 0, width, height);


        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                double rSum = 0, gSum = 0, bSum = 0, kSum = 0;

                for (int i = 0; i < kernelWidth; i++)
                {
                    for (int j = 0; j < kernelHeight; j++)
                    {
                        int pixelPosX = x + (i - 1);
                        int pixelPosY = y + (j - 1);
                        if ((pixelPosX < 0) ||
                                (pixelPosX >= width) ||
                                (pixelPosY < 0) ||
                                (pixelPosY >= height)) continue;
/*
                        int r = (srcPixels[width * pixelPosY + pixelPosX] >> 16) & 0xff;
                        int g = (srcPixels[width * pixelPosY + pixelPosX] >> 8) & 0xff;
                        int b = srcPixels[width * pixelPosY + pixelPosX] & 0xff;
*/
                        double kernelVal = kernel[i][j];
/*
                        rSum += r * kernelVal;
                        gSum += g * kernelVal;
                        bSum += b * kernelVal;
*/
                        kSum += srcPixels[width * pixelPosY + pixelPosX] * kernelVal;
                    }
                }
/*
                if (kSum <= 0) kSum = 1;

                //Контролируем переполнения переменных
                rSum /= kSum;
                if (rSum < 0) rSum = 0;
                if (rSum > 255) rSum = 255;

                gSum /= kSum;
                if (gSum < 0) gSum = 0;
                if (gSum > 255) gSum = 255;

                bSum /= kSum;
                if (bSum < 0) bSum = 0;
                if (bSum > 255) bSum = 255;
*/
                //Записываем значения в результирующее изображение
                //destPixels[width * y + x] = 0xff000000 | (((int) Math.ceil(rSum)) << 16) | (((int) Math.ceil(gSum)) << 8) | ((int) Math.ceil(bSum));
                destPixels[width * y + x] = (int) Math.ceil(rSum/10);
            }
        }

        toTransform.setPixels(destPixels, 0, width, 0, 0, width, height);
        return toTransform;
    }

    @Override
    public String getId() {
        // Return some id that uniquely identifies your transformation.
        return "com.example.myapp.MyTransformation3228";
    }
}
