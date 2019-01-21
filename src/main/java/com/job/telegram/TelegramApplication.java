package com.job.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@SpringBootApplication
public class TelegramApplication implements CommandLineRunner {
    @Value("${bot.token}")
    private String BOT_TOKEN;
    @Value("${bot.name}")
    private String BOT_NAME ;
    @Value("${proxy.host}")
    private  String PROXY_HOST;
    @Value("${proxy.port}")
    private  Integer PROXY_PORT;
    public static void main(String[] args) {
        SpringApplication.run(TelegramApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ApiContextInitializer.init();

        // Create the TelegramBotsApi object to register your bots
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Set up Http proxy
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        // Select proxy type: [HTTP|SOCKS4|SOCKS5] (default: NO_PROXY)
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        // Register your newly created AbilityBot
        ExampleBot bot = new ExampleBot(BOT_TOKEN, BOT_NAME, botOptions);

        botsApi.registerBot(bot);

    }
}

