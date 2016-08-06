package com.example.evgeniy.imager;

/**
 * Created by Evgeniy on 7/31/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.evgeniy.imager.filters.MatrixFilter;
import com.example.evgeniy.imager.filters.GBFilter;
import com.example.evgeniy.imager.filters.RBfilter;
import com.example.evgeniy.imager.filters.RGfilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FullImageActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        final ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        final Context context = this;

        // get intent data
        Intent intent = getIntent();

        // Selected image id
        final String position = intent.getStringExtra("id");
        final File imageFile = new File(Uri.parse(position).getPath());

        Glide.with(context).load(position).into(imageView);

        View.OnClickListener btnOneClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new MatrixFilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnFourClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new NegativFilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnFiveClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new GBFilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnSixClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new RBfilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnSevenClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new RGfilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnTwoClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutputStream fOut = null;
                //Bitmap bitmap = ((GlideBitmapDrawable) imageView.getDrawable()).getBitmap();

                //String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // название из даты
                try {
                    File file = new File("/sdcard/CameraExample/", "temp.jpg");
                    fOut = new FileOutputStream(file);

                    Bitmap bitmap = ((GlideBitmapDrawable) imageView.getDrawable()).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                    //Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.evgeniy.imager", imageFile);
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.chooser_title)));
                }
                catch (Exception e){}
            }
        };

        View.OnClickListener btnThreeClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new SecondFilter( context ) )
                        .into(imageView);
            }
        };

        button.setOnClickListener(btnOneClk);
        button2.setOnClickListener(btnTwoClk);
        button3.setOnClickListener(btnThreeClk);
        button4.setOnClickListener(btnFourClk);
        button5.setOnClickListener(btnFiveClk);
        button6.setOnClickListener(btnSixClk);
        button7.setOnClickListener(btnSevenClk);

    }


}