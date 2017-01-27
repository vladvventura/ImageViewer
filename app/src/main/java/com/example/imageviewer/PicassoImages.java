package com.example.imageviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.input;

/**
 * Created by VLAD on 1/26/2017.
 */
public class PicassoImages extends AppCompatActivity {

    ///won't  work -- PicassoImages requires internet connection done via Asynchronous thread (see commented portion of MainActivity.java).
    //Can be  done, but internet  connection needs to be done on MainActivity; otherwise becomes pointless.
    // This requires  further work.
    // As it is, I already spent more than 8 hours on this project.

    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private boolean fail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picasso_images);
        if(!isConnected()) {
            fail=true;
        }
        else {
            fail = false;
            List<String> paths = getRandomImgurls();//concat Imgur and URLS --> imgurls.

            adapter = new MyAdapter(getApplicationContext(), getCreateList(paths));
        }
        recyclerView = (RecyclerView) findViewById(R.id.picassoimagesgallery);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);//adapter needs to be set onCreate, else it'll be skipped
        if(fail) { Toast.makeText(getApplicationContext(),"Image",Toast.LENGTH_SHORT).show(); }

    }

    private ArrayList<CreateList> getCreateList(List<String> paths) {
        ArrayList<CreateList> allImages = new ArrayList<>();
        for (int i = 0; i <paths.size(); i++) {// i<paths.size(); i++)  { //1000 too many to do. let's go simple.
            File imgPath = new File(paths.get(i));
            CreateList singleImage = new CreateList();
//            Log.d("CreateList", paths.get(i)); path is good.
            Bitmap bmp = BitmapFactory.decodeFile(imgPath.getAbsolutePath());
            ImageView img = new ImageView(getApplicationContext());
            img.setImageBitmap(bmp);
            img.setId(i);
            Log.d("CreateListImage", img.toString());
            Log.d("CreateListImageID", Integer.toString(img.getId()));
            singleImage.setImageURL("");
            singleImage.setImageTitle(paths.get(i));
            allImages.add(singleImage);
            //   }
        }


        return allImages;
    }

    public List<String> getRandomImgurls() {
        List<String> randomImgurls = new ArrayList<>();
        try {
            //URL url =new URL("http://imgur.com/");
            //HttpURLConnection c = (HttpURLConnection) url.openConnection();
            //c.connect();
            Document doc = Jsoup.connect("http://imgur.com").get();
            Element content = doc.getElementById("content");
            Elements links = content.getElementsByTag("img");
            for (Element link: links) {
                String linkText = link.text();
                randomImgurls.add(link.text());
                Log.d("url?",linkText);
            }

        }
        catch (MalformedURLException ex) {
            Log.d("Maflormed URL", ex.toString());

        }
        catch (IOException ie) {
            Log.d("IOException", ie.toString());
        }
        catch (Exception e) {
            Log.d("General Exception", e.toString());
        }
        finally {

        }

        return randomImgurls;
    }
    private boolean isConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Log.d("networkinfo", Boolean.toString(networkInfo!=null));
        Log.d("networkinfoIsConnected",Boolean.toString(networkInfo.isConnected()));
        return (networkInfo!=null && networkInfo.isConnected());

    }


}