package com.example.api;

import com.example.model.Contact;
import com.example.model.ContactsContainer;
import com.example.model.IContact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * DAO class to access to the "database"
 */
public class ContactDAO {

    /**
     * Retrieve the contacts container from an URL
     * @param url
     * @return the contacts container
     * @throws IOException
     */
    public static ContactsContainer getContactsContainer(URL url) throws IOException {
        return getContactsContainer(new InputStreamReader(url.openStream()));
    }

    /**
     * Retrive the contacts from a file
     * @param fileInputStream
     * @return the contacts container
     * @throws IOException
     */
    public static ContactsContainer getContactsContainer(FileInputStream fileInputStream) throws IOException {
        return getContactsContainer(new InputStreamReader(fileInputStream));
    }

    /**
     * Retrieve the file from any input stream
     * @param inputStreamReader
     * @return the contacts container
     * @throws IOException
     */
    private static ContactsContainer getContactsContainer(InputStreamReader inputStreamReader) throws IOException {
        BufferedReader in = new BufferedReader(inputStreamReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = in.readLine()) != null) {
            stringBuilder.append(line);
        }
        String input = stringBuilder.toString();
        return jsonToContactsContainer(input);
    }

    /**
     * Deserialize a json format string into a contact
     * @param input string input in json
     * @return the contact
     * @throws IOException
     */
    public static IContact jsonToContact(String input) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(input, Contact.class);
    }

    /**
     * Serialize a contact into a json format string
     * @param contact contact to serialize
     * @return the json output string
     * @throws JsonProcessingException
     */
    public static String contactToJson(IContact contact) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(contact);
    }


    /**
     * Deserialize a json format string into a contacts container
     * @param input string input in json
     * @return the contacts container
     * @throws IOException
     */
    public static ContactsContainer jsonToContactsContainer(String input) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(input, ContactsContainer.class);
    }

    /**
     * Serialize a contacts container into a json format string
     * @param contactsContainer contactsContainer to serialize
     * @return the json output string
     * @throws JsonProcessingException
     */

    public static String contactsContainerToJson(ContactsContainer contactsContainer) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(contactsContainer);
    }

}
