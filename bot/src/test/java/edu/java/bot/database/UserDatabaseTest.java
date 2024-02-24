package edu.java.bot.database;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.dataBase.UsersDataBase;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserDatabaseTest {
    @Test
    public void addAndGetUserTest() {
        User myUser = new User(1L);
        UsersDataBase.addUser(myUser);
        assertThat(UsersDataBase.getUserById(1L)).isNotNull();
    }

    @Test
    public void removeUserTest() {
        User myUser = new User(1L);
        UsersDataBase.addUser(myUser);
        UsersDataBase.removeUser(myUser);
        assertThat(UsersDataBase.getUserById(1L)).isNull();
    }
}
