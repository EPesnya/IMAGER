package com.example.evgeniy.imager;

/**
 * Created by Evgeniy on 7/31/2016.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.evgeniy.imager.filters.BlurMatrixFilter;
import com.example.evgeniy.imager.filters.ContrastCorrection;
import com.example.evgeniy.imager.filters.MatrixFilter;
import com.example.evgeniy.imager.filters.GBFilter;
import com.example.evgeniy.imager.filters.RBfilter;
import com.example.evgeniy.imager.filters.RGfilter;
import com.example.evgeniy.imager.filters.SharpnessMatrixFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FullImageActivity extends Activity {

    static Context context;
    public static void ShowToast(String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        final ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.button);
        LinearLayout buttonLayout1 = (LinearLayout) findViewById(R.id.button1);
        LinearLayout buttonLayout2 = (LinearLayout) findViewById(R.id.button2);
        LinearLayout buttonLayout3 = (LinearLayout) findViewById(R.id.button3);
        LinearLayout buttonLayout4 = (LinearLayout) findViewById(R.id.button4);
        LinearLayout buttonLayout5 = (LinearLayout) findViewById(R.id.button5);
        LinearLayout buttonLayoutContrast = (LinearLayout) findViewById(R.id.buttonContrast);
        LinearLayout buttonLayoutSharpness = (LinearLayout) findViewById(R.id.buttonSharpness);
        LinearLayout buttonLayoutBlur = (LinearLayout) findViewById(R.id.buttonBlur);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabdelete);
        FloatingActionButton fabReset = (FloatingActionButton) findViewById(R.id.fabreset);
        FloatingActionButton fabSave = (FloatingActionButton) findViewById(R.id.fabsave);
        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fabshare);

        // get intent data
        final Intent intent = getIntent();

        // Selected image id
        final String position = intent.getStringExtra("id");
        final File imageFile = new File(Uri.parse(position).getPath());

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.colorsTab);
        tabSpec.setIndicator("Colors");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.noisesTab);
        tabSpec.setIndicator("Noises");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.correctionTab);
        tabSpec.setIndicator("Corrections");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
        tabHost.bringToFront();

        FrameLayout imgLayout = (FrameLayout)findViewById(R.id.imgLayout);
        FrameLayout tabLayout = (FrameLayout)findViewById(R.id.tabLayout);
        FrameLayout mainImgLayout = (FrameLayout)findViewById(R.id.tabLayout);
        imgLayout.setMinimumHeight(mainImgLayout.getHeight() - tabLayout.getHeight());

        Glide.with(context).load(position).into(imageView);

        /////////////////////////////////////////////////////////////////////////////
        View.OnClickListener btnOneClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new MatrixFilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnTwoClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new SecondFilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnThreeClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new NegativFilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnFourClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new GBFilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnFiveClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new RBfilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener btnSixClk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new RGfilter( context ) )
                        .into(imageView);
            }
        };

        View.OnClickListener buttonSharpnessClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(position)
                        .transform( new SharpnessMatrixFilter(context))
                        .into(imageView);
            }
        };
        View.OnClickListener buttonBlurClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContrastCorrection.correction = 2;
                Glide.with(context)
                        .load(position)
                        .transform( new BlurMatrixFilter(context))
                        .into(imageView);
            }
        };
        View.OnClickListener buttonContrastClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContrastCorrection.correction = 2;
                Glide.with(context)
                        .load(position)
                        .transform( new ContrastCorrection(context))
                        .into(imageView);
            }
        };

        /////////////////////////////////////////////////////////////////////

        Glide.with(context)
                .load(position)
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .centerCrop()
                .transform( new Crop(context), new MatrixFilter(context) )
                .into((ImageView) buttonLayout.getChildAt(0));
        buttonLayout.setOnClickListener(btnOneClk);
        Glide.with(context)
                .load(position)
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .centerCrop()
                .transform( new Crop(context), new SecondFilter(context) )
                .into((ImageView) buttonLayout1.getChildAt(0));
        buttonLayout1.setOnClickListener(btnTwoClk);
        Glide.with(context)
                .load(position)
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .centerCrop()
                .transform( new Crop(context), new NegativFilter(context) )
                .into((ImageView) buttonLayout2.getChildAt(0));
        buttonLayout2.setOnClickListener(btnThreeClk);
        Glide.with(context)
                .load(position)
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .centerCrop()
                .transform( new Crop(context), new GBFilter(context) )
                .into((ImageView) buttonLayout3.getChildAt(0));
        buttonLayout3.setOnClickListener(btnFourClk);
        Glide.with(context)
                .load(position)
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .centerCrop()
                .transform( new Crop(context), new RBfilter(context) )
                .into((ImageView) buttonLayout4.getChildAt(0));
        buttonLayout4.setOnClickListener(btnFiveClk);
        Glide.with(context)
                .load(position)
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .centerCrop()
                .transform( new Crop(context), new RGfilter(context) )
                .into((ImageView) buttonLayout5.getChildAt(0));
        buttonLayout5.setOnClickListener(btnSixClk);

        buttonLayoutBlur.setOnClickListener(buttonBlurClick);
        buttonLayoutSharpness.setOnClickListener(buttonSharpnessClick);
        buttonLayoutContrast.setOnClickListener(buttonContrastClick);

////////////////////////////////////////////////////////////
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad;
                Context context;
                context = FullImageActivity.this;
                String title = "Deleting";
                String message = "Are you shure?";
                String button1String = "Delete";
                String button2String = "Cancel";

                ad = new AlertDialog.Builder(context);
                ad.setTitle(title);
                ad.setMessage(message);
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        boolean isDeleted = imageFile.delete();
                        if(isDeleted)
                            Toast.makeText(getApplicationContext(), "Deleted successfully!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Deleting denied!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        setResult(MainActivity.IMAGE_DELETED_CODE);
                        finish();
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                ad.setCancelable(true);
                ad.show();
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            OutputStream fOut = null;
            @Override
            public void onClick(View v) {
                try {

                    Glide.with(context).load(position).into(imageView);
                    findViewById(R.id.imgLayout).setMinimumHeight(findViewById(R.id.mainImgLayout).getHeight() - findViewById(R.id.tabLayout).getHeight());
                    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    File file = new File("/sdcard/CameraExample/", timeStamp+".jpg");
                    fOut = new FileOutputStream(file);

                    Bitmap bitmap = ((GlideBitmapDrawable) imageView.getDrawable()).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                    Toast.makeText(getApplicationContext(), "Saved successfully!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){Toast.makeText(getApplicationContext(), "Save denied!", Toast.LENGTH_SHORT).show();}
            }
        });

        fabReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context).load(position).into(imageView);
            }
        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutputStream fOut = null;
                try {
                    File file = new File("/sdcard/CameraExample/", "temp.jpg");
                    fOut = new FileOutputStream(file);

                    Bitmap bitmap = ((GlideBitmapDrawable) imageView.getDrawable()).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.chooser_title)));
                }
                catch (Exception e){}
            }
        });
    }

}