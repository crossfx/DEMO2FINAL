package com.example.schen.camera.Adapter;


import android.graphics.Bitmap;
import android.os.Environment;
import java.io.File;


/**
    Data class which contains the information for the cell. In your case, it has title, description
    and a Url pointed to the image. I assume all the cell would have all these info correctly, so I
    didn't handle the null part (i.e. one cell might just have an image and its title.)
 */

public class MyData {

    private String mImageTitle;
    private String mImageUrl;
    private String mImageDescription;
    //File blah = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


    public MyData(String imageUrl, String imageDescription) {

        this.mImageUrl = imageUrl;
        this.mImageDescription = imageDescription;
    }


   /* public String getImageTitle() {
        return mImageTitle;
    }*/

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getImageDescription() {
        return mImageDescription;
    }

/*    public void setImageTitle(String mImageTitle) {
        this.mImageTitle = mImageTitle;
    }*/

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setImageDescription(String imageDescription) {
        this.mImageDescription = imageDescription;
    }
}
