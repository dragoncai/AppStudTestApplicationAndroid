package com.example.api;

import com.example.model.ContactsContainer;
import com.example.model.IContact;

import java.util.HashSet;
import java.util.Set;

public class Application {

    public enum ActionResult {
        SUCCESS,
        FAIL;
    }

    private ContactsContainer contactsContainer;

    public Application() {
        contactsContainer = new ContactsContainer();
    }

    public ActionResult markAsFavorite(IContact contact) {
        IContact iContact = contactsContainer.getContact(contact);
        if (iContact != null) {
            iContact.setIsFavorite(true);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    public ActionResult unmarkAsFavorite(IContact contact) {
        IContact iContact = contactsContainer.getContact(contact);
        if (iContact != null && isFavorite(iContact)) {
            iContact.setIsFavorite(false);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    private boolean isFavorite(IContact contact) {
        return getFavoritesContacts().contains(contact);
    }

    private boolean isPresentInContacts(IContact contact) {
        return getContacts().contains(contact);
    }

    public ContactsContainer getContactsContainer() {
        return contactsContainer;
    }

    public Set<IContact> getContacts() {
        return contactsContainer.getContacts();
    }

    public Set<IContact> getFavoritesContacts() {
        Set<IContact> contacts = contactsContainer.getContacts();
        Set<IContact> toReturn = new HashSet<>();
        for (IContact contact : contacts) {
            if (contact.isFavorite()) {
                toReturn.add(contact);
            }
        }
        return toReturn;
    }

    public void setContactsContainer(ContactsContainer contactsContainer) {
        this.contactsContainer = contactsContainer;
    }
}
