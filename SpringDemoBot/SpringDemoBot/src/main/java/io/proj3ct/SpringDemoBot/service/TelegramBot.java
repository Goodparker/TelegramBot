package io.proj3ct.SpringDemoBot.service;


import com.vdurmont.emoji.EmojiParser;
import io.proj3ct.SpringDemoBot.config.BotConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;
import java.util.logging.SimpleFormatter;


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
                    case "/My love":
                        firstCommandReceived(chatId);
                        break;
                    case "Oвен":
                        secondCommandReceived(chatId);
                        break;
                    case "Рыбы":
                        thirdCommandReceived(chatId);
                        break;
                    case "Tелец":
                        telecCommandReceived(chatId);
                        break;
                    case "Близнецы":
                        bliznecCommandReceived(chatId);
                        break;
                    case "Рак":
                        racCommandReceived(chatId);
                        break;
                    case "Лев":
                        levCommandReceived(chatId);
                        break;
                    case "Дева":
                        devaCommandReceived(chatId);
                        break;
                    case "Весы":
                        vesiCommandReceived(chatId);
                        break;
                    case "Скорпион":
                        scorpionCommandReceived(chatId);
                        break;
                    case "Стрелец":
                        strelecCommandReceived(chatId);
                        break;
                    case "Козерог":
                        kozerogCommandReceived(chatId);
                        break;
                    case "Водолей":
                        vodoleyCommandReceived(chatId);
                        break;
                    case "/joke":
                        anecdotCommandReceived(chatId);
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
    private void firstCommandReceived(long chatId)
    {
        String answer = "I love Anton";

        sendMessage(chatId, answer);
    }
    private void secondCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/aries/?updated").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void thirdCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/pisces/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void telecCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/taurus/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void bliznecCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/gemini/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void racCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/cancer/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void levCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/leo/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void devaCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/virgo/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void vesiCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/libra/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void scorpionCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/scorpio/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void strelecCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/sagittarius/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void kozerogCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/capricorn/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void vodoleyCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://horoscopes.rambler.ru/aquarius/").get();
        var titleEl =  doc.selectFirst("p");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }
    private void anecdotCommandReceived(long chatId) throws IOException {

        Document doc = Jsoup.connect("https://www.anekdot.ru/release/anekdot/day/").get();
        var titleEl =  doc.selectFirst("div.text");
        //System.out.println(titleEl.text());
        String rez = titleEl.text();
        sendMessage(chatId, rez);
    }









    private void sendMessage(long chatId, String textToSend)
    {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Oвен");
        row.add("Tелец");
        row.add("Близнецы");
        row.add("Рак");
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add("Лев");
        row.add("Дева");
        row.add("Весы");
        row.add("Скорпион");
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add("Стрелец");
        row.add("Козерог");
        row.add("Водолей");
        row.add("Рыбы");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);

        try{
            execute(message);
        }
        catch (TelegramApiException e)
        {

        }
    }
}
