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

public class ContactDAO {

    public static ContactsContainer getContactsContainer(URL url) throws IOException {
        return getContactsContainer(new InputStreamReader(url.openStream()));
    }

    public static ContactsContainer getContactsContainer(FileInputStream fileInputStream) throws IOException {
        return getContactsContainer(new InputStreamReader(fileInputStream));
    }

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

    public static ContactsContainer getFavorites(Path path) throws IOException {
        String input = new String(Files.readAllBytes(path));
        return jsonToContactsContainer(input);
    }

    public static IContact jsonToContact(String input) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(input, Contact.class);
    }

    public static String contactToJson(IContact contact) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(contact);
    }


    public static ContactsContainer jsonToContactsContainer(String input) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(input, ContactsContainer.class);
    }

    public static String contactsContainerToJson(ContactsContainer contactsContainer) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(contactsContainer);
    }

}
