package com.botconfigs;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
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

        SendMessage sendMessage = new SendMessage();

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();

            String data = callbackQuery.getData();
            var msj = callbackQuery.getMessage();

            switch (data) {
                case "tareasPendientes":
                    sendMessage.setText("No hay tareas pendientes por el momento.");
                    sendMessage.setChatId(msj.getChatId().toString());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    break;
                case "anunciosClase":
                    sendMessage.setText("Próxima clase 19/02/2022");
                    sendMessage.setChatId(msj.getChatId().toString());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    break;
            }

        } else {
            Message message = update.getMessage();
            sendMessage.setChatId(message.getChatId().toString());

            String text = message.getText();

            if (text.equals("/start@"+getBotUsername()) || text.equals("/start")) {
                sendMessage.setText("Hola!");
                try {
                    execute(sendMessage);
                    sendInlineKeyboard(message.getChatId().toString());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            // TODO Add functionality to /saludar command.

        }

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
        setButtons(sendMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that creates a keyboard
     * 
     * @param sendMessage the message returned to the user
     */
    public synchronized void setButtons(SendMessage sendMessage) {
        // Creating the keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Create a list of the keyboard's buttons row
        List<KeyboardRow> keyboard = new ArrayList<>();

        // First row of keyboard
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Add buttons to the first row of the keyboard
        keyboardFirstRow.add(new KeyboardButton("Hi !!!"));

        // ------------------------------------------

        // Second row of the keyboard
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Adding buttons to the second row
        keyboardSecondRow.add(new KeyboardButton("Help! plz"));

        // -------------------------------------------

        // Adding all the buttons to the keyboard list
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // Assing list to the replyKeyboardMarkup
        replyKeyboardMarkup.setKeyboard(keyboard);

    }

    /**
     * Similar to replyKeyboardMarkup.
     * This type of keyboard is attached to a specific message and exists only for
     * it.
     */
    public void setInLine(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Elige una de las opciones :)");

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();

        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText("First");
        btn.setCallbackData("cmd1");

        buttons1.add(btn);
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeybard = new InlineKeyboardMarkup();
        markupKeybard.setKeyboard(buttons);

    }

    public void sendInlineKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Elige una de las opciones..");

        // Create InlineKeyboardMarkup object
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        
        // Create the keyboard (list of InlineKeyboardButton list)
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        // Create a list for buttons
        List<InlineKeyboardButton> Buttons = new ArrayList<InlineKeyboardButton>();
        // Initialize each button, the text must be written
        InlineKeyboardButton tareas = new InlineKeyboardButton("Tareas");
        // Also must use exactly one of the optional fields,it can edit by set method
        tareas.setText("Tareas Pendientes");
        tareas.setCallbackData("tareasPendientes");
        // Add button to the list
        Buttons.add(tareas);
        // Initialize each button, the text must be written
        InlineKeyboardButton anuncios = new InlineKeyboardButton("Anuncios");
        // Also must use exactly one of the optional fields,it can edit by set method
        // github.setUrl("https://github.com");
        anuncios.setText("Anuncios");
        anuncios.setCallbackData("anunciosClase");
        // Add button to the list
        Buttons.add(anuncios);
        keyboard.add(Buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(inlineKeyboardMarkup);
        

        try {
            // Send the message
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void answerCallback(String callbackQueryId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQueryId);
        answer.setText(message);
        answer.setShowAlert(true);
        try {
            execute(answer);
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
