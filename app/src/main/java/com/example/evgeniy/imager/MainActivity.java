package com.example.evgeniy.imager;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.provider.MediaStore;
import android.widget.GridView;
import android.graphics.Bitmap;
import android.provider.MediaStore.Images.Media;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public static final int IMAGE_DELETED_CODE = 228;
    final int CAMERA_CAPTURE = 1;
    final int FULL_IMAGE = 0;
    final int REQUEST = 1;
    Bitmap img = null;
    public ArrayList<File> photos; //asdasdasdasds
    int c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        photos = imageReader(Environment.getExternalStorageDirectory());

        Collections.sort(photos, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f2.lastModified()).compareTo(Long.valueOf(f1.lastModified()));
            }
        });

        setPhotos();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // название из даты

                File file = new File("/sdcard/CameraExample/",timeStamp+".jpg");
                Uri photodir1 = Uri.fromFile(file);

                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photodir1);
                startActivityForResult(captureIntent, CAMERA_CAPTURE);

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popup_menu); // Для Android 4.0
        // для версии Android 3.0 нужно использовать длинный вариант
        // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
        // popupMenu.getMenu());

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Toast.makeText(PopupMenuDemoActivity.this,
                        // item.toString(), Toast.LENGTH_LONG).show();
                        // return true;
                        switch (item.getItemId()) {

                            case R.id.menu1:
                                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // название из даты

                                File file = new File("/sdcard/CameraExample/",timeStamp+".jpg");
                                Uri photodir1 = Uri.fromFile(file);

                                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photodir1);
                                startActivityForResult(captureIntent, CAMERA_CAPTURE);
                                return true;
                            case R.id.menu2:
                                /*Intent i = new Intent(Intent.ACTION_PICK);
                                File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                                String pictureDirPath = pictureDir.getPath();
                                Uri data = Uri.parse(pictureDirPath);
                                i.setDataAndType(data, "image/*");
                                startActivityForResult(i, REQUEST);*/
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }

    ArrayList<File> imageReader(File root){
        ArrayList<File> a = new ArrayList<>();

        File[] files = root.listFiles();
        for (File ff : root.listFiles()) {
            if (ff.isDirectory()) {
                if(!(ff.isHidden() || ff.getName().endsWith("Android")))
                    a.addAll(imageReader(ff));
            } else {
                if (ff.getName().endsWith(".jpg") || ff.getName().endsWith(".png")) {
                    a.add(ff);
                    //Toast.makeText(this, ff.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }
        return a;
    }

    void setPhotos()
    {
        GridView gridview = (GridView) findViewById(R.id.gridView1);
        if (gridview != null) {
            final ImageAdapter adapter = new ImageAdapter(this, photos.toArray(new File[photos.size()]));
            gridview.setAdapter(adapter);
            gridview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Intent i = new Intent(getApplicationContext(),
                            FullImageActivity.class);
                    // передаем индекс массива
                    i.putExtra("id", photos.get(position).toString());
                    startActivityForResult(i, FULL_IMAGE);
                }
            });
        }
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        img = null;
        if (requestCode == CAMERA_CAPTURE && resultCode == RESULT_OK) {
            Intent i = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(i);
        }
        if(requestCode == FULL_IMAGE && resultCode == IMAGE_DELETED_CODE)
        {
            photos = imageReader(Environment.getExternalStorageDirectory());
            setPhotos();
            Toast.makeText(getApplicationContext(), "Refreshed!", Toast.LENGTH_SHORT).show();
        }

            /*
            Uri selectedImage = data.getData();
            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(selectedImage);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }*/
        //super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.evgeniy.imager/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.evgeniy.imager/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
