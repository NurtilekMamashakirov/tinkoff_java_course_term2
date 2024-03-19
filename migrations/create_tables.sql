create table chat
(
    id     bigint not null unique primary key,
    status int    not null default 1
);

create table link
(
    id         bigint generated always as identity primary key,
    link       varchar(200) unique      not null,
    updated_at timestamp with time zone not null,
    checked_at timestamp with time zone,
    status     int                      not null default 1
);

create table chat_and_link
(
    chat_id bigint not null,
    link_id bigint not null,
    status  int    not null default 1,
    FOREIGN KEY (chat_id) REFERENCES chat (id),
    FOREIGN KEY (link_id) REFERENCES link (id)
);
