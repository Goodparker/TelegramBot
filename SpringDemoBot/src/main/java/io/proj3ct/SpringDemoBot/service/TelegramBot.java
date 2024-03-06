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
import com.google.api.services.docs.v1.model.*;
import org.springframework.stereotype.Component;


import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.*;

import io.proj3ct.SpringDemoBot.config.BotConfig;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.ChatInviteLink;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBot extends TelegramLongPollingBot{
    final BotConfig config;
    //---------------------------------------------------------------------------------------------
    public TelegramBot(BotConfig config, DocsQuickstart docsQuickstart)
    {
        this.config = config;
    }
    @Override
    public String getBotUsername() { return config.getBotName(); }
    @Override
    public String getBotToken() { return config.getBotToken(); }
     private String text;
    //---------------------------------------------------------------------------------------------
    @Override
    public void onUpdateReceived(Update update)  {
        ChatInviteLink();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            if (update.getMessage().getChat().isGroupChat()) {
                String chatId = String.valueOf(update.getMessage().getChatId());
                long UserId = update.getMessage().getFrom().getId();
                try {
                    ChatMember chatMember =  execute(new GetChatMember(chatId,UserId));
                    String username = chatMember.getUser().getFirstName();
                    String lastName = chatMember.getUser().getLastName();

                String groupName = update.getMessage().getChat().getTitle();
                Date mytime = new Date();
                text = "\nGroup: " + groupName + System.lineSeparator() +
                        "Time: " + mytime + System.lineSeparator() +
                        "User: " + username + " " + lastName + System.lineSeparator() +
                        "Text: " + messageText + "\n";

                System.out.println(text);
                try {
                    start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }
                }
                catch (TelegramApiException e)
                {
                    e.printStackTrace();
                }

            }
        }
    }
    //---------------------------------------------------------------------------------------------
    //--------------------------------------------DOCS---------------------------------------------
    //---------------------------------------------------------------------------------------------
    private static final String APPLICATION_NAME = "Test";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "AeaYSHAiDWvgxky1LkI4EZpWdqUWBgB8EBaqtHt_H2ouwn_4UHD_OjepHlK9HrNM57k72g";
    private static final String DOCUMENT_ID = "1ea-nRoP4aNBSrAJ8Cm26gdcqq9fu_ltqAd7vV7gqHqk";
    private static final List<String> SCOPES = Collections.singletonList(DocsScopes.DOCUMENTS);
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
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH))).setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    //---------------------------------------------------------------------------------------------
    public void start() throws IOException, GeneralSecurityException {
        // Создаем новую авторизованную клиентскую службу API.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Docs service = new Docs.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();

        List<Request> requests = Arrays.asList(
                new Request().setInsertText(new InsertTextRequest()
                        .setText(text)
                        .setLocation(new Location().setIndex(1))
                )
        );
        BatchUpdateDocumentRequest batchUpdateDocumentRequest = new BatchUpdateDocumentRequest().setRequests(requests);
        service.documents().batchUpdate(DOCUMENT_ID, batchUpdateDocumentRequest).execute();
    }
    //---------------------------------------------------------------------------------------------
    public  void  test()
    {
        client = TelegramClient 
    }
}
