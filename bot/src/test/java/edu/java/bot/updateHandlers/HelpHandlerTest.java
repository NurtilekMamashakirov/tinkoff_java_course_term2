package edu.java.bot.updateHandlers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

public class HelpHandlerTest {

    @Test
    public void handleTest() {
        Update update = Mockito.mock(Update.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(1L);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(update.message()).thenReturn(message);
        HelpHandler helpHandler = new HelpHandler();
        SendMessage result = helpHandler.handle(update);
        assertThat(result).isNotNull();
    }

    static Arguments[] exampleAndExpected() {
        return new Arguments[] {
            Arguments.of("/help", true),
            Arguments.of("/helps", false),
            Arguments.of("help", false)
        };
    }

    @ParameterizedTest
    @MethodSource("exampleAndExpected")
    public void supportsTest(String command, Boolean expected) {
        HelpHandler helpHandler = new HelpHandler();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.text()).thenReturn(command);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        assertThat(helpHandler.supports(update)).isEqualTo(expected);
    }
}
