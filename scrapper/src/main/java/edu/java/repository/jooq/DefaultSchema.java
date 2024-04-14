/*
 * This file is generated by jOOQ.
 */
package edu.java.repository.jooq;


import edu.java.repository.jooq.tables.Chat;
import edu.java.repository.jooq.tables.ChatAndLink;
import edu.java.repository.jooq.tables.Link;

import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.13"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>CHAT</code>.
     */
    public final Chat CHAT = Chat.CHAT;

    /**
     * The table <code>CHAT_AND_LINK</code>.
     */
    public final ChatAndLink CHAT_AND_LINK = ChatAndLink.CHAT_AND_LINK;

    /**
     * The table <code>LINK</code>.
     */
    public final Link LINK = Link.LINK;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    @Override
    @NotNull
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    @NotNull
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Chat.CHAT,
            ChatAndLink.CHAT_AND_LINK,
            Link.LINK
        );
    }
}
