package io.proj3ct.SpringDemoBot.service;
import io.proj3ct.SpringDemoBot.config.BotConfig;



import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;


import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;

@Component
public class TelegramBot extends TelegramLongPollingBot{
    final BotConfig config;
    private  DocsQuickstart docsQuickstart;
    //---------------------------------------------------------------------------------------------
    public TelegramBot(BotConfig config, DocsQuickstart docsQuickstart)
    {
        this.config = config;
        this.docsQuickstart = docsQuickstart;
    }
    @Override
    public String getBotUsername() { return config.getBotName(); }
    @Override
    public String getBotToken() { return config.getBotToken(); }
     public String text;
    //---------------------------------------------------------------------------------------------
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            if (update.getMessage().getChat().isGroupChat()) {

                String groupName = update.getMessage().getChat().getTitle();
                String firstName = update.getMessage().getChat().getFirstName();
                String lastName = update.getMessage().getChat().getLastName();
                Date mytime = new Date();
                text = "Group: " + groupName + System.lineSeparator() +
                        "Time: " + mytime + System.lineSeparator() +
                        "User: " + firstName + " " + lastName + System.lineSeparator() +
                        "Text: " + messageText;

                System.out.println(text);
                docsQuickstart.receivedTextFromTelegramBot(text);

            }
        }
    }
    //---------------------------------------------------------------------------------------------
}
