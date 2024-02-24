package edu.java.bot.updateHandlers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.UpdateHandlers.CommandHandler;
import edu.java.bot.UpdateHandlers.HelpHandler;
import edu.java.bot.UpdateHandlers.StartHandler;
import edu.java.bot.UpdateHandlers.UntrackHandler;
import edu.java.bot.dataBase.UserAndLinksDataBase;
import edu.java.bot.dataBase.UsersDataBase;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

public class UntrackHandlerTest {

    @Test
    public void handleTest() {
        String exampleCommand = "/untrack link";
        String exampleLink = "link";
        User user = new User(1L);
        UsersDataBase.addUser(user);
        UserAndLinksDataBase.addLinkToUser(1L, exampleLink);
        Update update = Mockito.mock(Update.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(1L);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.text()).thenReturn(exampleCommand);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.from()).thenReturn(user);
        Mockito.when(update.message()).thenReturn(message);
        CommandHandler untrackHandler = new UntrackHandler();
        SendMessage result = untrackHandler.handle(update);
        assertThat(UserAndLinksDataBase.getAllLinksById(1L)).isEmpty();
    }

    static Arguments[] exampleAndExpected() {
        return new Arguments[] {
            Arguments.of("/untrack", false),
            Arguments.of("/untrack .", true),
            Arguments.of("untrack", false)
        };
    }

    @ParameterizedTest
    @MethodSource("exampleAndExpected")
    public void supportsTest(String command, Boolean expected) {
        CommandHandler untrackHandler = new UntrackHandler();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.text()).thenReturn(command);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        assertThat(untrackHandler.supports(update)).isEqualTo(expected);
    }

}
