package edu.java.bot.updateHandlers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class ListHandlerTest {
//    @Test
//    public void handleTest() {
//        ScrapperClient scrapperClient =
//            new ScrapperClient(new RetryTemplate(), "https://localhost:8080", "/links", "/tg-chat");
//        System.out.println(scrapperClient);
//        Update update = Mockito.mock(Update.class);
//        Chat chat = Mockito.mock(Chat.class);
//        Mockito.when(chat.id()).thenReturn(1L);
//        Message message = Mockito.mock(Message.class);
//        Mockito.when(message.chat()).thenReturn(chat);
//        Mockito.when(update.message()).thenReturn(message);
//        CommandHandler listHandler = new ListHandler(scrapperClient);
//        SendMessage result = listHandler.handle(update);
//        assertThat(result).isNotNull();
//    }

    static Arguments[] exampleAndExpected() {
        return new Arguments[] {
            Arguments.of("/list", true),
            Arguments.of("/lists", false),
            Arguments.of("list", false)
        };
    }

    @ParameterizedTest
    @MethodSource("exampleAndExpected")
    public void supportsTest(String command, Boolean expected) {
        CommandHandler listHandler = new ListHandler();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.text()).thenReturn(command);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        assertThat(listHandler.supports(update)).isEqualTo(expected);
    }

}
