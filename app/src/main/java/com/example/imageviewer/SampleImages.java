package com.example.imageviewer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VLAD on 1/26/2017.
 */

public class SampleImages extends AppCompatActivity {

    //sample images
    private final String image_titles[] = {
            "Img1",
            "Img2",
            "Img3",
            "Img4",
            "Img5",
            "Img6",
            "Img7",
            "Img8",
            "Img9",
            "Img10",
            "Img11",
            "Img12",
            "Img13",
    };

    private final Integer image_ids[] = {
            //TODO: make these  images  load images from different sources (the web and  the phone)
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
            R.drawable.img9,
            R.drawable.img10,
            R.drawable.img11,
            R.drawable.img12,
            R.drawable.img13,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_images);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sampleimagegallery);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<CreateList> prepareData() {

        ArrayList<CreateList> theimage = new ArrayList<>();
        //sample images
        for (int i = 0; i < image_titles.length; i++) {
            CreateList createList = new CreateList();
            createList.setImageTitle(image_titles[i]);
            createList.setImageID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }

}