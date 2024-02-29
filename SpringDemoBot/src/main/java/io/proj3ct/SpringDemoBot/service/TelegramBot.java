package io.proj3ct.SpringDemoBot.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.model.BatchUpdateDocumentRequest;
import com.google.api.services.docs.v1.model.InsertTextRequest;
import com.google.api.services.docs.v1.model.Location;
import com.google.api.services.docs.v1.model.Request;
import com.vdurmont.emoji.EmojiParser;
import io.proj3ct.SpringDemoBot.config.BotConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;

@Component
public class TelegramBot extends TelegramLongPollingBot{
    final BotConfig config;
    //---------------------------------------------------------------------------------------------
    public TelegramBot(BotConfig config)
    {
        this.config = config;
    }
    @Override
    public String getBotUsername() { return config.getBotName(); }
    @Override
    public String getBotToken() { return config.getBotToken(); }
    String text;
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
            }
        }
    }
    //---------------------------------------------------------------------------------------------
}
//---------------------------------------------------------------------------------------------
