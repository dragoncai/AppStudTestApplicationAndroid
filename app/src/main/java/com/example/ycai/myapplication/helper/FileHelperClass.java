package com.example.ycai.myapplication.helper;

import com.example.api.Application;
import com.example.api.ContactDAO;
import com.example.model.ContactsContainer;
import com.example.model.IContact;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

/**
 * Helper class to handle file saving and loading
 */
public class FileHelperClass {

    /**
     * Save the contacts in a file
     * @param fileOutputStream the file output stream to save in
     * @param contacts the contacts to save
     * @throws IOException
     */
    public static void saveContactsInAFile(FileOutputStream fileOutputStream, Set<IContact> contacts) throws IOException {
        ContactsContainer contactsContainer = new ContactsContainer();
        contactsContainer.getContacts().addAll(contacts);
        String json = ContactDAO.contactsContainerToJson(contactsContainer);
        fileOutputStream.write(json.getBytes());
        fileOutputStream.close();
    }

    /**
     * Read the contacts from a file
     * @param fileInputStream file to read from
     * @return the contacts from the file
     * @throws IOException
     */
    public static Set<IContact> retrieveContactsFromFile(FileInputStream fileInputStream) throws IOException {
        ContactsContainer contactsContainer = ContactDAO.getContactsContainer(fileInputStream);
        fileInputStream.close();
        return contactsContainer.getContacts();
    }
}
