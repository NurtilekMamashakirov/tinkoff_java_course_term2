package edu.java.bot.updateHandlers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.UpdateHandlers.CommandHandler;
import edu.java.bot.UpdateHandlers.TrackHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

public class TrackHandlerTest {

    static Arguments[] exampleCommandAndExpected() {
        return new Arguments[] {
            Arguments.of("/track https://github.com", "https://github.com", true),
            Arguments.of("/track eqffew", "eqffew", false),
            Arguments.of("/track lol", "lol", false)
        };
    }

//    @ParameterizedTest
//    @MethodSource("exampleCommandAndExpected")
//    public void handleTest(String command, String link, Boolean expected) {
//        Update update = Mockito.mock(Update.class);
//        Chat chat = Mockito.mock(Chat.class);
//        Mockito.when(chat.id()).thenReturn(1L);
//        Message message = Mockito.mock(Message.class);
//        User user = new User(1L);
//        UsersDataBase.addUser(user);
//        Mockito.when(message.from()).thenReturn(user);
//        Mockito.when(message.chat()).thenReturn(chat);
//        Mockito.when(message.text()).thenReturn(command);
//        Mockito.when(update.message()).thenReturn(message);
//        CommandHandler trackHandler = new TrackHandler();
//        trackHandler.handle(update);
//        assertThat(UserAndLinksDataBase.getAllLinksById(1L).contains(link)).isEqualTo(expected);
//    }

    static Arguments[] exampleAndExpected() {
        return new Arguments[] {
            Arguments.of("/track https://github.com", true),
            Arguments.of("/track eqffew", true),
            Arguments.of("trackk", false)
        };
    }

    @ParameterizedTest
    @MethodSource("exampleAndExpected")
    public void supportsTest(String command, Boolean expected) {
        CommandHandler trackHandler = new TrackHandler();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.text()).thenReturn(command);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        assertThat(trackHandler.supports(update)).isEqualTo(expected);
    }
}
