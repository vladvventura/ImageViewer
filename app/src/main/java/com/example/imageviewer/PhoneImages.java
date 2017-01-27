package com.example.imageviewer;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VLAD on 1/26/2017.
 */
public class PhoneImages extends AppCompatActivity {


    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private boolean fail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_images);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        List<String> paths = (List<String>) bundle.getSerializable("paths");

        adapter = new MyAdapter(getApplicationContext(), getCreateList(paths));
        recyclerView = (RecyclerView)findViewById(R.id.phoneimagegallery);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);//adapter needs to be set onCreate, else it'll be skipped

    }

    private ArrayList<CreateList> getCreateList(List<String> paths) {
        ArrayList<CreateList> allImages = new ArrayList<>();
        //int size = 10;
        int size = paths.size(); // paths.size() for my phone is 10000 photos... use some smaller number (e.g.  above)
        for(int i=0; i<size; i++){// i<paths.size(); i++)  {
            File imgPath = new File(paths.get(i));
            CreateList singleImage = new CreateList();
//            Log.d("CreateList", paths.get(i)); path is good.
            Bitmap bmp= BitmapFactory.decodeFile(imgPath.getAbsolutePath());
            ImageView img= new ImageView(getApplicationContext());
            img.setImageBitmap(bmp);
            img.setId(i);
            Log.d("CreateListImage",img.toString());
            Log.d("CreateListImageID",Integer.toString(img.getId()));
            singleImage.setImageLocation(imgPath);
            singleImage.setImageTitle(paths.get(i));
            allImages.add(singleImage);
            //   }
        }


        return allImages;
    }
/*
    //add images to arrayList
    private ArrayList<CreateList> prepareData(){
        ArrayList<CreateList> theimage = new ArrayList<>();
        try {
            theimage.addAll(getPhoneLists());
            fail=false;
        }
        catch (Exception e) {
            Log.d("Exception (prepdata)","images not added proeprly.");
        }
        return theimage;
    }

    public ArrayList<CreateList> getPhoneLists() {
        ArrayList<CreateList> phoneLists = new ArrayList<>();
        //directory images
        List<String> paths = getImagePaths(getApplicationContext());
        //Log.d("path is",Integer.toString(paths.size())); it's over 9000!...
        for(int i=0; i<10; i++){// i<paths.size(); i++)  { //1000 too many to do. let's go simple.
            File imgPath = new File(paths.get(i));
            CreateList createList = new CreateList();
//            Log.d("CreateList", paths.get(i)); path is good.
            Bitmap bmp= BitmapFactory.decodeFile(imgPath.getAbsolutePath());
            ImageView img= new ImageView(getApplicationContext());
            img.setImageBitmap(bmp);
            Log.d("CreateListImage",img.toString());
            Log.d("CreateListImageID",Integer.toString(img.getId()));
            createList.setImageID(img.getId());
            createList.setImageTitle(paths.get(i));
            phoneLists.add(createList);
            //   }
        }
        return phoneLists;
    }

//*/


}

