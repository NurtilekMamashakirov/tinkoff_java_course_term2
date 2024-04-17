package edu.java.repository.jooq.impls;

import edu.java.dto.models.Chat;
import edu.java.repository.jdbc.TgChatRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.repository.jooq.Tables.CHAT;
import static edu.java.repository.jooq.Tables.CHAT_AND_LINK;

@AllArgsConstructor
@Repository
public class JooqTgChatRepository implements TgChatRepository {
    private final DSLContext context;

    @Override
    public boolean deleteChat(Long id) {
        if (!chatExist(id)) {
            return false; //chat doesnt exist
        }
        context.update(CHAT)
            .set(CHAT.STATUS, 0)
            .where(CHAT.ID.eq(id))
            .execute();
        return true; //chat successfully deleted
    }

    @Override
    public boolean addChat(Long id) {
        if (chatExist(id)) {
            return false; //already exist
        }
        context.insertInto(CHAT, CHAT.ID)
            .values(id)
            .execute();
        return true; //chat created
    }

    @Override
    public List<Chat> getChatsByLink(Long linkId) {
        return context
            .select()
            .from(CHAT_AND_LINK)
            .join(CHAT)
            .on(CHAT_AND_LINK.CHAT_ID.eq(CHAT.ID))
            .where(CHAT_AND_LINK.LINK_ID.eq(linkId))
            .fetchInto(Chat.class);
    }

    @Override
    public boolean chatExist(Long id) {
        List<Chat> chats = context.selectFrom(CHAT)
            .where(CHAT.ID.eq(id))
            .and(CHAT.STATUS.eq(1))
            .fetchInto(Chat.class);
        return !chats.isEmpty();
    }
}
