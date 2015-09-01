package com.example.ycai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.api.Application;
import com.example.api.ContactDAO;
import com.example.model.ContactsContainer;
import com.example.model.IContact;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class SplashActivity extends Activity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    static Application application;

    public SplashActivity() {
        application = new Application();

    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void init() {
        AsyncTask<String, Void, ContactsContainer> execute = new DownloadContactsTask().execute("http://54.72.181.8/yolo/contacts.json");
        try {
            application.setContactsContainer(execute.get());
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        } catch (ExecutionException e) {
            Log.e("ExecutionException", e.getMessage());
        }

        try {
            FileInputStream fis = openFileInput("favoriteContacts");
            ContactsContainer contactsContainer = ContactDAO.getContactsContainer(fis);
            Set<IContact> contacts = contactsContainer.getContacts();
            for (IContact contact : contacts) {
                application.markAsFavorite(contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        init();
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        init();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        init();
//    }

    private class DownloadContactsTask extends AsyncTask<String, Void, ContactsContainer> {
        @Override
        protected ContactsContainer doInBackground(String... urls) {
            String url = urls[0];
            ContactsContainer contactsContainer = null;
            try {
                contactsContainer = ContactDAO.getContactsContainer(new URL(url));
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return contactsContainer;
        }
    }
}
