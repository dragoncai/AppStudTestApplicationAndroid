package com.example.ycai.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.api.ContactDAO;
import com.example.model.ContactsContainer;
import com.example.model.IContact;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        FileOutputStream outputStream = null;
        ContactsContainer contactsContainer = new ContactsContainer();
        contactsContainer.getContacts().addAll(SplashActivity.application.getFavoritesContacts());
        try {
            String json = ContactDAO.contactsContainerToJson(contactsContainer);
            String fileName = "favoriteContacts";
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        FileOutputStream outputStream = null;
        ContactsContainer contactsContainer = new ContactsContainer();
        contactsContainer.getContacts().addAll(SplashActivity.application.getFavoritesContacts());
        try {
            String json = ContactDAO.contactsContainerToJson(contactsContainer);
            String fileName = "favoriteContacts";
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    public void openContactsActivity(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void openFavoriteActivity(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

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

    private void init() {
        AsyncTask<String, Void, ContactsContainer> execute = new DownloadContactsTask().execute("http://54.72.181.8/yolo/contacts.json");
        try {
            SplashActivity.application.setContactsContainer(execute.get());
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
                SplashActivity.application.markAsFavorite(contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
