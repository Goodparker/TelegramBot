package io.proj3ct.SpringDemoBot.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.DocsScopes;
import com.google.api.services.docs.v1.model.Document;
import com.google.api.services.docs.v1.model.*;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp.Browser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DocsQuickstart {
    //---------------------------------------------------------------------------------------------
    private static final String APPLICATION_NAME = "Test";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "d";
    private static final String DOCUMENT_ID = "1ea-nRoP4aNBSrAJ8Cm26gdcqq9fu_ltqAd7vV7gqHqk";
    private static final List<String> SCOPES = Collections.singletonList(DocsScopes.DOCUMENTS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    //---------------------------------------------------------------------------------------------
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Загрузка данных пользователя.
        InputStream in = DocsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Создаем поток и инициируем запрос авторизации пользователя.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH))).setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    //---------------------------------------------------------------------------------------------

    private static void insertText(Docs service, String documentId, String text) throws IOException {
        // Создаем текстовый абзац
        //  Paragraph paragraph = new Paragraph().setElements(Collections.singletonList(new TextRun().setText(text)));

        // Создаем запрос на вставку абзаца в начало документа
        InsertTextRequest insertTextRequest = new InsertTextRequest().setText(text).setEndOfSegmentLocation(new EndOfSegmentLocation());

        // Выполняем запрос на вставку абзаца
        BatchUpdateDocumentRequest batchUpdateDocumentRequest = new BatchUpdateDocumentRequest().setRequests(
                Collections.singletonList(new Request().setInsertText(insertTextRequest)));
        service.documents().batchUpdate(documentId, batchUpdateDocumentRequest).execute();
    }
    //---------------------------------------------------------------------------------------------
    public static void start() throws IOException, GeneralSecurityException {
        // Создаем новую авторизованную клиентскую службу API.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Docs service = new Docs.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();

        // Печатает заголовок запрошенного документа:
        Document response = service.documents().get(DOCUMENT_ID).execute();
        String title = response.getTitle();
        String body = (response.getBody().getContent().toString());
        System.out.printf("The title of the doc is: %s\n", title);

    //---------------------------------------------------------------------------------------------
        List<StructuralElement> elements = response.getBody().getContent();
        StringBuilder documentTextBuilder = new StringBuilder();

        for (StructuralElement element : elements) {
            if (element.getParagraph() != null) {
                List<ParagraphElement> paragraphElements = element.getParagraph().getElements();
                for (ParagraphElement paragraphElement : paragraphElements) {
                    if (paragraphElement.getTextRun() != null) {
                        documentTextBuilder.append(paragraphElement.getTextRun().getContent());
                    }
                }
            }
        }
    //---------------------------------------------------------------------------------------------
        List<Request> requests = Arrays.asList(new Request().setInsertText(new InsertTextRequest()
                        .setText("Hello, World!\n")
                        .setLocation(new Location().setIndex(1))
                )
        );
        BatchUpdateDocumentRequest batchUpdateDocumentRequest = new BatchUpdateDocumentRequest().setRequests(requests);
        service.documents().batchUpdate(DOCUMENT_ID, batchUpdateDocumentRequest).execute();



        String documentText = documentTextBuilder.toString();
        System.out.println("Текст документа: " + documentText);
    }
    //---------------------------------------------------------------------------------------------
}
