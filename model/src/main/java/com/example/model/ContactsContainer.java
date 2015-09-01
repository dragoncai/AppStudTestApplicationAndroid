package com.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ContactsContainer implements Serializable {
    private Set<IContact> contacts;

    public ContactsContainer() {
        this(new HashSet<IContact>());
    }

    @JsonCreator
    public ContactsContainer(@JsonProperty(value = "contacts") Set<IContact> contacts) {
        this.contacts = contacts;
    }

    /**
     *
     * @return all the contacts
     */
    public Set<IContact> getContacts() {
        return contacts;
    }

    /**
     * Retrieve a contact from the list when we already got all its information (except its isFavorite)
     *
     * @param contact the contact we want to retrieve
     * @return
     */
    public IContact getContact(IContact contact) {
        for (IContact iContact : contacts) {
            if (iContact.equals(contact)){
                return iContact;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ContactsContainer{" +
                "contacts=" + contacts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactsContainer that = (ContactsContainer) o;

        return !(contacts != null ? !contacts.equals(that.contacts) : that.contacts != null);

    }

    @Override
    public int hashCode() {
        return contacts != null ? contacts.hashCode() : 0;
    }
}
