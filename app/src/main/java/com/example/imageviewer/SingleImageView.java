package com.example.imageviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by VLAD on 1/27/2017.
 */
public class SingleImageView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image_view);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int ids = bundle.getInt("id");
        ImageView img = (ImageView) findViewById(ids);
        ImageView present = (ImageView) findViewById(R.id.imageView);
        present.setId(ids);
    }
}