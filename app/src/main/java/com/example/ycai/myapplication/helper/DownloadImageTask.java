package com.example.ycai.myapplication.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Asynchronous task to download an image from an URL and add to a {@link ImageView}
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView photoView;

    public DownloadImageTask(ImageView photoView) {
        this.photoView = photoView;
    }

    /**
     * Get the image
     * @param urls the url to get from
     * @return the image
     */
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    /**
     * place the image in the {@link ImageView}
     * @param result the image to place
     */
    protected void onPostExecute(Bitmap result) {
        photoView.setImageBitmap(result);
    }
}