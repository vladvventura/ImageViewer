package com.example.imageviewer;

import java.io.File;
import java.io.Serializable;

/**
 * Created by VLAD on 1/25/2017.
 */

public class CreateList implements Serializable {
    private String imageTitle;
    private Integer imageID;
    private File imageLocation;
    private String imageURL;

    public String getImage_title() {
        return imageTitle;
    }
    public void setImageTitle(String name) {
        this.imageTitle=name;
    }
    public Integer getImageID() {
        return imageID;
    }
    public void setImageID(Integer url) {
        this.imageID=url;
    }
    public void setImageLocation(File imageLocation) {
        this.imageLocation = imageLocation;
    }
    public File getImageLocation() { return imageLocation; }
    public void setImageURL(String url) { this.imageURL = url; }
    public String getImageURL() {
        return imageURL;
    }
}
