package com.example.ycai.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.model.IContact;
import com.example.ycai.myapplication.helper.FileHelperClass;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

/**
 * Display the contacts
 */
public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        init();
    }

    /**
     * Display the contacts within the {@link ContactView}
     */
    private void init() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.contactsLayout);
        Set<IContact> contacts = SplashScreen.application.getContactsContainer().getContacts();
        for (IContact contact : contacts) {
            linearLayout.addView(new ContactView(contact, this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            /*
             * finish the activity when the top action bar back button is pressed in order to not call HomeActivity.onCreate()
             */
            case R.id.home:
                return terminateTheActivity();
            case R.id.action_home:
                return terminateTheActivity();
            case R.id.action_favorites:
                startActivity(new Intent(this, FavoritesActivity.class));
                return terminateTheActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Terminate the activity
     * @return true
     */
    private boolean terminateTheActivity() {
        finish();
        return true;
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
}
