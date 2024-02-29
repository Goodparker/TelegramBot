package io.proj3ct.SpringDemoBot.config;

import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.model.Document;
import io.proj3ct.SpringDemoBot.service.DocsQuickstart;
import io.proj3ct.SpringDemoBot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class BotInicializer {

    @Autowired
    TelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException{
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try{
            telegramBotsApi.registerBot(bot);
            DocsQuickstart.start();

        }
        catch (TelegramApiException e)
        {

        } catch (GeneralSecurityException e) {
           // throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
