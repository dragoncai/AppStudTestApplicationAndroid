package com.example.ycai.myapplication.helper;

import android.os.AsyncTask;
import android.util.Log;

import com.example.api.ContactDAO;
import com.example.model.ContactsContainer;

import java.net.URL;

/**
 * Asynchrnous task to download the contacts from an URL
 */
public class DownloadContactsTask extends AsyncTask<String, Void, ContactsContainer> {
    /**
     * Retrieve the contacts from the url
     * @param urls the url to get from
     * @return the contacts container
     */
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

