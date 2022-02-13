package com.botconfigs;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Create an instance of this object for manage your bot
 */
public class MyTelegramBot extends TelegramLongPollingBot {

    /**
     * This method receives a message from telegram server
     * 
     * @param update This contains an update (like a messsage) from the user.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();

        // Echo
        sendMsg(chatId, message);
    }

    /**
     * Method that creates a message and sends it to the user
     * 
     * @param chatId The user's chat id
     * @param msg    The message that we want to send
     */
    public synchronized void sendMsg(String chatId, String msg) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(msg);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the bot's name from the BotConfig class
     */
    @Override
    public String getBotUsername() {
        return BotConfig.NAME;
    }

    /**
     * This method returns the bot's token from the BotConfig class
     */
    @Override
    public String getBotToken() {
        return BotConfig.TOKEN;
    }

}
