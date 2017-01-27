package com.example.imageviewer;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.path;


public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE =0x00123;
    private boolean fail=true;
    private View progressView;
    private RecyclerView recyclerView;
    //private AsyncThread loadImages;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sampleImagesView = (Button) findViewById(R.id.sample_images_button);
        Button phoneImagesView  = (Button) findViewById(R.id.phone_images_button);
        Button picassoImagesView  = (Button) findViewById(R.id.picasso_images_button);

        sampleImagesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSampleImages();
            }
        });
        phoneImagesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPhoneImages();
            }
        });
        picassoImagesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPicassoImages();
            }
        });

    }

    private void goToSampleImages() {
        Intent intent = new  Intent(this, SampleImages.class);
        //ask for permissions

        startActivity(intent);
    }
    private void goToPhoneImages() {
        //progressView=findViewById(R.id.progress_bar);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //forget the explanation for now. TODO:explanation for Permissions
            //            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
            //                    Manifest.permission.READ_CONTACTS)) {
            //
            //                // Show an explanation to the user *asynchronously* -- don't block
            //                // this thread waiting for the user's response! After the user
            //                // sees the explanation, try again to request the permission.
            //
            //            }
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);

        }
        else {// already has permissions
            setRecycler();
        }
        //look below for permission handling

    }
    private void goToPicassoImages() {
        //TODO: implement Picasso Images
        Intent intent = new Intent(this, PicassoImages.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permissions granted
                    setRecycler();
                    //showProgress(true);
                    //kickoff asynchronous task.
                    //loadImages = new AsyncThread();
///////////////////////////////////////////////
/////////////////////////////////////////////
                } else {

                    // permission denied, nothing happens.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    //set RecyclerView stuff.
    public void setRecycler(){
//        ArrayList<CreateList> createLists = prepareData();
        List<String> paths = getImagePaths(getApplicationContext()); //get all image pathways

        Intent intent = new Intent(MainActivity.this, PhoneImages.class);
        Bundle  bundle  = new Bundle();
        bundle.putSerializable("paths", (Serializable) paths); //put image pathways into bundle.
        intent.putExtras(bundle);
        startActivity(intent);
    }
   //image directory querying via the android database (that hosts  all mediaStore.Images data)
    public List<String> getImagePaths(Context context) {

        // The list of columns we're interested in:
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED};

        final Cursor cursor = context.getContentResolver(). //query phone's database
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // Specify the provider
                columns, // The columns we're interested in
                null, // A WHERE-filter query
                null, // The arguments for the filter-query
                MediaStore.Images.Media.DATE_ADDED + " DESC" // Order the results, newest first
        );

        List<String> result = new ArrayList<String>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int image_path_col = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                result.add(cursor.getString(image_path_col));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return result;
    }//*// confirmed works.

    /*public Boolean addAdapter() { //this really has no value/merit. I could've moved showProgress(false) to parent
        //method; or into the asynchronous task thread.

        if(fail) { //it failed, but we should've had permissions, so...try#2
            ArrayList<CreateList> createLists = prepareData();
            adapter = new MyAdapter(getApplicationContext(), createLists);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.phoneimagegallery);
            recyclerView.setAdapter(adapter);
        }
        //showProgress(false);
        return true;
    }


    /**
     * Shows the progress UI and hides the gallery.
     */
   /* @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class AsyncThread extends AsyncTask<Void, Void, Boolean> {


        AsyncThread() {

        }
        @Override
        protected Boolean doInBackground(Void... params) {
            return setRecycler();

        }
        @Override
        protected void onPostExecute(final Boolean success) {
            Intent intent = new Intent(MainActivity.this, PhoneImages.class);
            Bundle  bundle  = new Bundle();
            bundle.putSerializable("adapter",adapter);
            startActivity(intent);
            //nothing needs to be done onPostExecute.
            //if I had some if-failed things to do, this would have meaning. but nope.
        }
    }*/
}

