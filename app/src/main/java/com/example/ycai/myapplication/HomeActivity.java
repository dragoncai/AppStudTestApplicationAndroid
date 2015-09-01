package com.example.ycai.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.model.IContact;
import com.example.ycai.myapplication.helper.FileHelperClass;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

/**
 * The home activity of the application
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * save the favorites whenever we are pausing the activity
     */
    @Override
    protected void onPause() {
        try {
            FileOutputStream stream = openFileOutput("favoriteContacts", Context.MODE_PRIVATE);
            Set<IContact> favoritesContacts = SplashScreen.application.getFavoritesContacts();
            FileHelperClass.saveContactsInAFile(stream, favoritesContacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    /**
     * Open the contacts activity
     * @param view
     */
    public void openContactsActivity(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    /**
     * Open the favorites activity
     * @param view
     */
    public void openFavoriteActivity(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }
}
