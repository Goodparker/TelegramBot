package io.proj3ct.SpringDemoBot.service;

import com.google.api.services.docs.v1.model.Document;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Docss{

    public Docss() throws IOException, GeneralSecurityException {
        super();
    }

    public void printDocumentTitles() throws IOException {

    }

    public static void main(String[] args) {
        try {
            Docss myGoogleDocsService = new Docss();
            myGoogleDocsService.printDocumentTitles();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
