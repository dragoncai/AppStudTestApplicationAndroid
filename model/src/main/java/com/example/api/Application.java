package com.example.api;

import com.example.model.ContactsContainer;
import com.example.model.IContact;

import java.util.HashSet;
import java.util.Set;

/**
 * Application API class to manipulate all the contacts
 */
public class Application {

    /**
     * SUCCESS If an action has been successfully done,  else FAIL
     */
    public enum ActionResult {
        SUCCESS,
        FAIL;
    }

    private ContactsContainer contactsContainer;

    public Application() {
        contactsContainer = new ContactsContainer();
    }

    public ContactsContainer getContactsContainer() {
        return contactsContainer;
    }

    public void setContactsContainer(ContactsContainer contactsContainer) {
        this.contactsContainer = contactsContainer;
    }

    /**
     * mark a contact as a favorite
     * @param contact the contact information
     * @return {@link com.example.api.Application.ActionResult}
     */
    public ActionResult markAsFavorite(IContact contact) {
        IContact iContact = contactsContainer.getContact(contact);
        if (iContact != null) {
            iContact.setIsFavorite(true);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    /**
     * unmark a contact as a favorite
     * @param contact the contact information
     * @return {@link com.example.api.Application.ActionResult}
     */
    public ActionResult unmarkAsFavorite(IContact contact) {
        IContact iContact = contactsContainer.getContact(contact);
        if (iContact != null && isFavorite(iContact)) {
            iContact.setIsFavorite(false);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    /**
     *
     * @param contact contact
     * @return TRUE if is a favorite
     */
    private boolean isFavorite(IContact contact) {
        return getFavoritesContacts().contains(contact);
    }

    /**
     *
     * @return contacts as a list
     */
    public Set<IContact> getContacts() {
        return contactsContainer.getContacts();
    }

    /**
     *
     * @return favorites as a list
     */
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
}
