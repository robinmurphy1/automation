package za.co.pifarm.automate.powerchecker.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.ParseMode;

@Component
public class TelegramCommunicator {

    @Value("${telegram.api.uri}")
    private String apiUrl;

    @Value("${telegram.bot.api-key}")
    private String authToken;

    @Value("${telegram.parse.mode}")
    private String parseMode;

    @Value("${telegram.chat.id}")
    private String chatId;

    @Value("${telegram.notify.enabled}")
    private String notifyEnabled;

    @Autowired
    private RestTemplate restTemplate;

    public void sendMessage(String message) {
        restTemplate.getForObject(buildUrl(message), Void.class);
    }

    String buildUrl(String message) {
        return String.format("%s/bot%s/sendmessage?chat_id=%s&text=%s&parse_mode=%s&disable_notification=%s"
                , apiUrl, authToken, chatId, message, parseMode, notifyEnabled);
    }

}
