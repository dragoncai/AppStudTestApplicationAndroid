package com.example.ycai.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.IContact;

import java.io.InputStream;

public class ContactView extends GridLayout {
    private final IContact contact;

    public ContactView(IContact contact, Context context) {
        this(contact, context, null);
    }

    public ContactView(IContact contact, Context context, AttributeSet attrs) {
        this(contact, context, attrs, 0);
    }

    public ContactView(IContact contact, Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.contact = contact;

        setColumnCount(3);
        setRowCount(2);
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        setLayoutParams(layoutParams);

        addView(getPhotoView(context));
        addView(getContactNameView(context));
        addView(getFavoriteButton(context));
        addView(getContactStatusView(context));
    }

    public ImageView getPhotoView(Context context) {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.height = dpToPixel(80);
        layoutParams.width = dpToPixel(80);
        layoutParams.rowSpec = spec(0, 2);
        layoutParams.columnSpec = spec(0);
        ImageView photoImageView = new ImageView(context);
        photoImageView.setLayoutParams(layoutParams);
        new DownloadImageTask(photoImageView).execute("http://54.72.181.8/yolo/"+contact.getPhotoId());
        return photoImageView;
    }

    public TextView getContactNameView(Context context) {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.width = dpToPixel(200);
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.rowSpec = spec(0);
        layoutParams.columnSpec = spec(1);
        layoutParams.setGravity(Gravity.LEFT | Gravity.RIGHT);
        TextView contactNameView = new TextView(context);
        contactNameView.setLayoutParams(layoutParams);
        contactNameView.setText(contact.getFirstName() + " " + contact.getLastName());
        contactNameView.setTextSize(20);
        return contactNameView;
    }

    public TextView getContactStatusView(Context context) {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.width = dpToPixel(200);
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.rowSpec = spec(1);
        layoutParams.columnSpec = spec(1);
        layoutParams.setGravity(Gravity.LEFT | Gravity.RIGHT | Gravity.TOP | Gravity.BOTTOM);
        TextView contactStatusView = new TextView(context);
        contactStatusView.setLayoutParams(layoutParams);
        contactStatusView.setText(contact.getStatus());
        contactStatusView.setTextSize(10);
        return contactStatusView;
    }

    public ImageButton getFavoriteButton(Context context) {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.width = dpToPixel(50);
        layoutParams.height = dpToPixel(50);
        layoutParams.rowSpec = spec(0,2);
        layoutParams.columnSpec = spec(2);
        layoutParams.setGravity(Gravity.CENTER_VERTICAL);
        ImageButton favoriteButton = new ImageButton(context);
        if (contact.isFavorite()) {
            favoriteButton.setImageResource(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
        }else{
            favoriteButton.setImageResource(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
        }
        favoriteButton.setOnClickListener(new FavoriteButtonOnClickListener(contact));
        favoriteButton.setLayoutParams(layoutParams);
        return favoriteButton;
    }

    public int dpToPixel(float dp){
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public IContact getContact() {
        return contact;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView photoView;

        public DownloadImageTask(ImageView photoView) {
            this.photoView = photoView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            photoView.setImageBitmap(result);
        }
    }

    private class FavoriteButtonOnClickListener implements OnClickListener {
        private IContact contact;

        public FavoriteButtonOnClickListener(IContact contact) {
            this.contact = contact;
        }

        @Override
        public void onClick(View v) {
            if (!contact.isFavorite()) {
                SplashActivity.application.markAsFavorite(contact);
                ((ImageButton)v).setImageResource(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
            }else{
                SplashActivity.application.unmarkAsFavorite(contact);
                ((ImageButton)v).setImageResource(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
            }
        }
    }
}
