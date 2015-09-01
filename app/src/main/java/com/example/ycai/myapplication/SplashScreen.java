package com.example.ycai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.api.Application;
import com.example.model.ContactsContainer;
import com.example.model.IContact;
import com.example.ycai.myapplication.helper.DownloadContactsTask;
import com.example.ycai.myapplication.helper.FileHelperClass;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Splash screen used as the opening of the application
 */
public class SplashScreen extends Activity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    /**
     * static member to be used in the whole application context
     */
    static Application application;

    public SplashScreen() {
        application = new Application();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            init();
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        /* New Handler to start the HomeActivity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(getRunnableToStartHomeActivity(), SPLASH_DISPLAY_LENGTH);
    }

    /**
     * @return a runnable to start the home activity after the splash screen
     */
    @NonNull
    private Runnable getRunnableToStartHomeActivity() {
        return new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this, HomeActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        };
    }

    /**
     * Retrieve the contacts and mark the favorites
     *
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void init() throws IOException, ExecutionException, InterruptedException {
        application.setContactsContainer(retrieveContacts());
        Set<IContact> favoriteContacts = FileHelperClass.retrieveContactsFromFile(openFileInput("favoriteContacts"));
        markFavorites(favoriteContacts);
    }

    /**
     * mark all the contacts that need to be
     *
     * @param favoriteContacts the contact to mark
     * @throws IOException
     */
    private void markFavorites(Set<IContact> favoriteContacts) throws IOException {
        for (IContact contact : favoriteContacts) {
            application.markAsFavorite(contact);
        }
    }

    /**
     * Retrieve the contacts from the URL and affec
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private ContactsContainer retrieveContacts() throws ExecutionException, InterruptedException {
        AsyncTask<String, Void, ContactsContainer> execute = new DownloadContactsTask().execute("http://54.72.181.8/yolo/contacts.json");
        return execute.get();
    }

}
