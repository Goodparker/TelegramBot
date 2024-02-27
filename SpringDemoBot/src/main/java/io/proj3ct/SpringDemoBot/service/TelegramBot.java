package io.proj3ct.SpringDemoBot.service;


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

    public TelegramBot(BotConfig config)
    {
        this.config = config;
    }

    @Override
    public String getBotUsername() { return config.getBotName(); }

    @Override
    public String getBotToken() { return config.getBotToken(); }



    @Override
    public void onUpdateReceived(Update update)
    {
        if (update.hasMessage() && update.getMessage().hasText())
        {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();
            Date mytime = new Date();

            try {
                switch (messageText)
                {
                    case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName(),mytime);
                        break;


                    default: sendMessage(chatId , "Sorry,there is no such command: " + messageText );
                        System.out.println("Name: " + update.getMessage().getChat().getFirstName() + " " +update.getMessage().getChat().getLastName()+System.lineSeparator() +"Time: " + mytime+System.lineSeparator()+"Text: " +messageText );

                }
            } catch (IOException e) {
                sendMessage(chatId, "An error occurred while fetching data from the website");
            }


        }
    }
    private void startCommandReceived(long chatId, String name, Date time)
    {
        String smile = EmojiParser.parseToUnicode(":heart:");
        String answer = "Hi, " + name + " " + smile;

        sendMessage(chatId, answer);
    }
    






    private void sendMessage(long chatId, String textToSend)
    {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);   
    }
}
