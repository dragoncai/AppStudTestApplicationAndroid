package com.example.api;


import com.example.model.Contact;
import com.example.model.IContact;

import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class ApplicationTest {
    @Test
    public void applicationMarkAContactAsAFavoriteShouldAddInFavoritesAndShouldBePartOfTheContacts() {
        Application application = new Application();
        Contact contact = new Contact();
        application.getContacts().add(contact);

        Application.ActionResult actionResult = application.markAsFavorite(contact);
        assertThat(actionResult).isEqualTo(Application.ActionResult.SUCCESS);

        Set<IContact> favorites = application.getFavoritesContacts();
        assertThat(favorites).containsOnly(contact);
    }

    @Test
    public void applicationMarkAContactNotPresentInContactsAsAFavoriteShouldReturnAFailure() {
        Application application = new Application();
        Contact contact = new Contact();

        Application.ActionResult actionResult = application.markAsFavorite(contact);
        assertThat(actionResult).isEqualTo(Application.ActionResult.FAIL);

        Set<IContact> favorites = application.getFavoritesContacts();
        assertThat(favorites).isEmpty();
    }

    @Test
    public void applicationUnmarkAContactAsAFavoriteShouldReturnSuccessAndDeleteItFromTheFavorites() {
        Application application = new Application();
        Contact contact = new Contact();
        application.getContacts().add(contact);
        application.markAsFavorite(contact);

        assertThat(application.unmarkAsFavorite(contact)).isEqualTo(Application.ActionResult.SUCCESS);
        assertThat(application.getFavoritesContacts()).hasSize(0);
    }
}