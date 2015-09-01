package com.example.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

/**
 * Represent a contact
 */
@JsonDeserialize(as = Contact.class)
public interface IContact extends Serializable{
    /**
     * @return contact's first name
     */
    String getFirstName();

    /**
     * @return contact's last name
     */
    String getLastName();

    /**
     * @return contact's status
     */
    String getStatus();

    /**
     * @return contact's file's name containing the photo
     */
    String getPhotoId();

    /**
     * @return true if it is a favorite, else false
     */
    boolean isFavorite();

    /**
     * @param isFavorite is a favorite or not
     */
    void setIsFavorite(boolean isFavorite);
}
