package com.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
@JsonPropertyOrder({ContactInfo.FIRSTNAME, ContactInfo.LASTNAME, ContactInfo.STATUS, ContactInfo.HISFACE })
public class Contact implements IContact {

    @JsonProperty(ContactInfo.FIRSTNAME)
    private final String firstName;
    @JsonProperty(ContactInfo.LASTNAME)
    private final String lastName;
    @JsonProperty(ContactInfo.STATUS)
    private final String status;
    @JsonProperty(ContactInfo.HISFACE)
    private final String photoId;
    private boolean isFavorite;

    public Contact() {
        this(null, null, null, null);
    }

    @JsonCreator
    public Contact(@JsonProperty(ContactInfo.FIRSTNAME) String firstName, @JsonProperty(ContactInfo.LASTNAME) String lastName, @JsonProperty(ContactInfo.STATUS) String status, @JsonProperty(ContactInfo.HISFACE) String photoId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.photoId = photoId;
        isFavorite = false;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getPhotoId() {
        return photoId;
    }

    @JsonIgnore
    @Override
    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", photoId='" + photoId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null)
            return false;
        if (status != null ? !status.equals(contact.status) : contact.status != null) return false;
        return !(photoId != null ? !photoId.equals(contact.photoId) : contact.photoId != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (photoId != null ? photoId.hashCode() : 0);
        return result;
    }
}
