package com.example.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

/**
 * Created by ycai on 27/08/2015.
 */
@JsonDeserialize(as = Contact.class)
public interface IContact extends Serializable{
    String getFirstName();
    String getLastName();
    String getStatus();
    String getPhotoId();
    boolean isFavorite();
    void setIsFavorite(boolean isFavorite);
}
