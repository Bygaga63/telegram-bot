package com.job.telegram;


import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;


public class ExampleBot extends TelegramLongPollingBot {

    public ExampleBot(String botToken, String botName, DefaultBotOptions options) {
        super(options);
        this.BOT_TOKEN = botToken;
        this.BOT_NAME = botName;
    }

    private final String BOT_TOKEN;
    private final String BOT_NAME;
    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            if (  update.getMessage().hasText()){
                String message = update.getMessage().getText();
                long chatId = update.getMessage().getChatId();
                sendMsg(update.getMessage().getChatId().toString(), message);
            } else if (update.getMessage().hasPhoto()) {
                long chatId = update.getMessage().getChatId();
                List<PhotoSize> photos = update.getMessage().getPhoto();

                String photoId = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getFileId();

                int photoWidth = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getWidth();

                int photoHeight = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getHeight();

                String caption = "file_id: " + photoId + "\nwidth: " + photoWidth + "\nheight: " + photoHeight;

                SendPhoto msg = new SendPhoto()
                        .setChatId(chatId)
                        .setPhoto(photoId)
                        .setCaption(caption);
                try {
                    execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public synchronized void sendMsg(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText("Keke");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}