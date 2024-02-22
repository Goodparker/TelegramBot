package io.proj3ct.SpringDemoBot.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.model.Document;



import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleDocsService {

    private static final String APPLICATION_NAME = "/credentials.json";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    protected Docs docsService;

    public GoogleDocsService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        docsService = new Docs.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void getDocuments() throws IOException {
        // Возможно, нужно вместо docsService указать docsServiceService
        Document document =  docsService.documents().get(APPLICATION_NAME).execute();
    }

}
