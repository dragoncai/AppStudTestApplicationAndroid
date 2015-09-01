package com.example.api;


import com.example.model.Contact;
import com.example.model.ContactInfo;
import com.example.model.ContactsContainer;
import com.example.model.IContact;

import org.junit.Test;

import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by ycai on 28/08/2015.
 */
public class ContactDAOTest {

    public static final Contact CONTACT = new Contact("Antoine", "Pemeja", "Redmine Addict", "antoine.jpg");

    @Test
    public void retrievingFromUrlTheListOfContactsShouldReturnTheJson() throws Exception {
        URL url = new URL("http://54.72.181.8/yolo/contacts.json");
        ContactsContainer contactsContainer = com.example.api.ContactDAO.getContactsContainer(url);
        assertThat(contactsContainer.getContacts()).isNotEmpty();
    }

    @Test
    public void clientReadFromJsonShouldReturnACompleteClientInstance() throws Exception {
        IContact expected = CONTACT;
        String input = contactInJson(expected);
        IContact actual = ContactDAO.jsonToContact(input);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void listOfClientsReadFromJsonShouldReturnACompleteListOfClientsInstance() throws Exception {
        String input = contactsContainerInJson();
        ContactsContainer expected = new ContactsContainer();
        expected.getContacts().add(CONTACT);
        ContactsContainer actual = ContactDAO.jsonToContactsContainer(input);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void contactWriteToJsonShouldReturnTheExpectedJsonFormat() throws Exception {
        String actual = ContactDAO.contactToJson(CONTACT);
        assertThat(actual).isEqualTo(contactInJson(CONTACT));
    }

    @Test
    public void contactsContainerWriteToJsonShouldReturnTheExpectedJsonFormat() throws Exception {
        ContactsContainer contactsContainer = new ContactsContainer();
        contactsContainer.getContacts().add(CONTACT);
        String actual = ContactDAO.contactsContainerToJson(contactsContainer);
        assertThat(actual).isEqualTo(contactsContainerInJson());
    }

    private String contactInJson(IContact contact) {
        return "{\""+ ContactInfo.FIRSTNAME +"\":\"" + contact.getFirstName() + "\"," +
                "\""+ ContactInfo.LASTNAME +"\":\"" + contact.getLastName() + "\"," +
                "\""+ ContactInfo.STATUS +"\":\"" + contact.getStatus() + "\"," +
                "\""+ ContactInfo.HISFACE +"\":\"" + contact.getPhotoId() + "\"}";
    }

    private String contactsContainerInJson() {
        return "{\"contacts\":["+ contactInJson(CONTACT)+"]}";
    }
}
