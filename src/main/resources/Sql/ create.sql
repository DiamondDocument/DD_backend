create table folder
(
    folder_id   char(7)     not null    primary key,
    folder_name varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime    not null,
    parent_id   char(7)     null,
    self_auth   int         not null,
    now_auth    int         not null,
    constraint fk_folder_user foreign key (creator_id) references user(user_id),
    constraint fk_folder_parent foreign key (parent_id) references folder(parent_id)
);

create table space_file
(
    space_id    char(7)     not null,
    file_id     char(7)     not null,
    primary key (space_id, file_id)
);

create table userspace
(
    userspace_id    char(7)     not null    primary key,
    userspace_name  varchar(20) not null,
    creator_id      varchar(20) not null,
    create_time     datetime    not null,
    constraint fk_userspace_user foreign key (creator_id) references user(user_id)
);

create table teamspace
(
    teamspace_id    char(7)     not null    primary key,
    teamspace_name  varchar(20) not null,
    team_id         char(5) not null,
    create_time     datetime,
    constraint fk_teamspace_team foreign key (team_id) references team(team_id)
);

create table message
(
    msg_id          char(7)     not null    primary key,
    sender_id       char(7)     not null,
    receiver_id     char(7)     not null,
    send_time       datetime    not null,
    receive_time    datetime    null,
    msg_type        int         not null,
    msg_content     varchar(255) null,
    msg_status      int         not null,
    msg_doc_id      char(7)     null,
    constraint fk_message_sender foreign key (sender_id) references user(user_id),
    constraint fk_message_receiver foreign key (receiver_id) references user(user_id)
);