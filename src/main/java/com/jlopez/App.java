package com.jlopez;

import com.botconfigs.MyTelegramBot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * This main method runs the bot
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        try {
            TelegramBotsApi myBot = new TelegramBotsApi(DefaultBotSession.class);
            myBot.registerBot(new MyTelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
