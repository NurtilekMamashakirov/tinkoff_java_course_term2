package edu.java.bot.database;

import edu.java.bot.dataBase.UserAndLinksDataBase;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserAndLinksDataBaseTest {
    @Test
    public void addAndGetLinkToUserTest() {
        String url = "https://github.com";
        UserAndLinksDataBase.addLinkToUser(1L, url);
        assertThat(UserAndLinksDataBase.getAllLinksById(1L)).contains(url);
    }

    @Test
    public void deleteLinkTest() {
        String url = "https://github.com";
        UserAndLinksDataBase.addLinkToUser(1L, url);
        UserAndLinksDataBase.deleteLinkToUser(1L, url);
        assertThat(UserAndLinksDataBase.getAllLinksById(1L)).isEmpty();
    }
}
