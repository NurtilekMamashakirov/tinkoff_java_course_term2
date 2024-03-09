create table chat
(
    id         bigint generated always as identity primary key,
    tg_chat_id bigint not null unique
);

create table link
(
    id         bigint generated always as identity primary key,
    link       varchar(200) unique not null,
    updated_at timestamp           not null
);

create table chat_and_link
(
    id      bigint generated always as identity primary key,
    chat_id bigint not null,
    link_id bigint not null,
    FOREIGN KEY (chat_id) REFERENCES chat (id),
    FOREIGN KEY (link_id) REFERENCES link (id)
);
