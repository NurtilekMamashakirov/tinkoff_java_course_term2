/*
 * This file is generated by jOOQ.
 */

package edu.java.repository.jooq;

import edu.java.repository.jooq.tables.Chat;
import edu.java.repository.jooq.tables.ChatAndLink;
import edu.java.repository.jooq.tables.Link;
import edu.java.repository.jooq.tables.records.ChatAndLinkRecord;
import edu.java.repository.jooq.tables.records.ChatRecord;
import edu.java.repository.jooq.tables.records.LinkRecord;
import javax.annotation.processing.Generated;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.13"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatRecord> CONSTRAINT_1 =
        Internal.createUniqueKey(Chat.CHAT, DSL.name("CONSTRAINT_1"), new TableField[] {Chat.CHAT.ID}, true);
    public static final UniqueKey<ChatRecord> CONSTRAINT_1F =
        Internal.createUniqueKey(Chat.CHAT, DSL.name("CONSTRAINT_1F"), new TableField[] {Chat.CHAT.ID}, true);
    public static final UniqueKey<LinkRecord> CONSTRAINT_2 =
        Internal.createUniqueKey(Link.LINK, DSL.name("CONSTRAINT_2"), new TableField[] {Link.LINK.ID}, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ChatAndLinkRecord, ChatRecord> CONSTRAINT_8 =
        Internal.createForeignKey(ChatAndLink.CHAT_AND_LINK,
            DSL.name("CONSTRAINT_8"),
            new TableField[] {ChatAndLink.CHAT_AND_LINK.CHAT_ID},
            Keys.CONSTRAINT_1,
            new TableField[] {Chat.CHAT.ID},
            true
        );
    public static final ForeignKey<ChatAndLinkRecord, LinkRecord> CONSTRAINT_89 =
        Internal.createForeignKey(ChatAndLink.CHAT_AND_LINK,
            DSL.name("CONSTRAINT_89"),
            new TableField[] {ChatAndLink.CHAT_AND_LINK.LINK_ID},
            Keys.CONSTRAINT_2,
            new TableField[] {Link.LINK.ID},
            true
        );
}
