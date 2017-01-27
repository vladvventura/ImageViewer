package com.example.imageviewer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by VLAD on 1/25/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Serializable {
    private ArrayList<CreateList> galleryList;
    private Context context;

    public MyAdapter(Context context, ArrayList<CreateList> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_images, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(galleryList.get(i).getImage_title());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP); //may decide  go to with FIT_CENTER or CENTER_INSIDE.
        if(galleryList.get(i).getImageID()!=null) {
            viewHolder.img.setImageResource((galleryList.get(i).getImageID()));
        }
        else if(galleryList.get(i).getImageLocation()!=null){
            viewHolder.img.setImageURI(Uri.fromFile(galleryList.get(i).getImageLocation()));
        }
        else {
            Picasso.with(context).load(galleryList.get(i).getImageURL()).resize(240, 120).into(viewHolder.img);
        }
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSingleImageView((ImageView)v);
                //Toast.makeText(context,"Image",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void goToSingleImageView(ImageView v){
        Intent intent  = new Intent(v.getContext(), SingleImageView.class);
        Bundle bundle =  new Bundle();
        bundle.putInt("id",v.getId());
        intent.putExtras(bundle);
        v.getContext().startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}
