create table users
(
    user_id  varchar(20) primary key not null,
    nickname varchar(20)             not null,
    password varchar(20)             not null,
    gender   char(5)                 ,
    intro    varchar(255)            ,
    email    varchar(50)
);
create table teams
(
    team_id    char(5) primary key not null,
    name       varchar(20)         not null,
    intro      varchar(255)        ,
    captain_id varchar(20)         not null,
    foreign key (captain_id) references users (user_id)
);
create table user_team
(
    team_id   char(5)     not null,
    user_id   varchar(20) not null,
    user_rank char(2)     not null,
    foreign key (user_id) references users (user_id),
    foreign key (team_id) references teams (team_id),
    primary key (team_id, user_id)
);
create table documents
(
    doc_id      char(7) primary key,
    name        varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime default now(),
    modifier_id varchar(20),
    modify_time datetime,
    self_auth   int         not null,
    now_auth    int         not null,
    is_editing  int         not null default 0,
    parent_id   char(7)     ,
    foreign key (creator_id) references users (user_id),
    foreign key (modifier_id) references users (user_id)
);
create table user_recent
(
    doc_id      char(7)     not null,
    user_id     varchar(20) not null,
    browse_time datetime    not null,
    foreign key (user_id) references users (user_id),
    foreign key (doc_id) references documents (doc_id),
    primary key (doc_id, user_id)
);

create table team_apply
(
    id         int primary key,
    team_id    char(5)     not null,
    user_id    varchar(20) not null,
    apply_time datetime    not null,
    apply_stu  varchar(20) not null,
    foreign key (user_id) references users (user_id),
    foreign key (team_id) references teams (team_id)
);
create table team_invite
(
    id          int primary key,
    team_id     char(5)     not null,
    user_id     varchar(20) not null,
    invite_time datetime    not null,
    invite_stu  varchar(20) not null,
    foreign key (user_id) references users (user_id),
    foreign key (team_id) references teams (team_id)
);

create table folder
(
    folder_id   char(7)     not null primary key,
    folder_name varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime    not null,
    parent_id   char(7)     null,
    self_auth   int         not null,
    now_auth    int         not null,
    foreign key (creator_id) references users (user_id)

);
alter table folder
    add foreign key (parent_id) references folder (folder_id)
;


create table space_file
(
    space_id char(7) not null,
    file_id  char(7) not null,
    primary key (space_id, file_id)
);

create table userspace
(
    userspace_id   char(7)     not null primary key,
    userspace_name varchar(20) not null,
    creator_id     varchar(20) not null,
    create_time    datetime    not null,
    constraint fk_userspace_user foreign key (creator_id) references users (user_id)
);

create table teamspace
(
    teamspace_id   char(7)     not null primary key,
    teamspace_name varchar(20) not null,
    team_id        char(5)     not null,
    create_time    datetime,
    constraint fk_teamspace_team foreign key (team_id) references teams (team_id)
);

create table message
(
    msg_id       char(7)      not null primary key,
    sender_id    char(7)      not null,
    receiver_id  char(7)      not null,
    send_time    datetime     not null,
    receive_time datetime     null,
    msg_type     int          not null,
    msg_content  varchar(255) null,
    msg_status   int          not null,
    msg_doc_id   char(7)      null,
    team_id      char(5)      null,
    deal_status  int          null,
    constraint fk_message_sender foreign key (sender_id) references users (user_id),
    constraint fk_message_receiver foreign key (receiver_id) references users (user_id),
    constraint fk_message_doc foreign key (msg_doc_id) references documents (doc_id),
    constraint fk_message_team foreign key (team_id) references teams (team_id)
);

create table userrecycle
(
    file_id       char(7)     not null primary key,
    del_id        varchar(20) not null,
    del_time      datetime    not null,
    pre_folder_id char(7)     null,
    user_id       varchar(20) not null,
    constraint fk_userrecycle_del foreign key (del_id) references users (user_id),
    constraint fk_userrecycle_folder foreign key (pre_folder_id) references folder (folder_id),
    constraint fk_userrecycle_user foreign key (user_id) references users (user_id)
);

create table teamrecycle
(
    file_id       char(7)     not null primary key,
    del_id        varchar(20) not null,
    del_time      datetime    not null,
    pre_folder_id char(7)     null,
    team_id       char(7)     not null,
    constraint fk_teamrecycle_del foreign key (del_id) references users (user_id),
    constraint fk_teamrecycle_folder foreign key (pre_folder_id) references folder (folder_id),
    constraint fk_teamrecycle_team foreign key (team_id) references teams (team_id)
);

create table templates
(
    temp_id     char(7) primary key,
    name        varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime default now(),
    intro       varchar(255),
    foreign key (creator_id) references users (user_id)
);
create table document_collector
(
    doc_id       char(7) not null,
    collector_id varchar(20) not null,
    foreign key (collector_id) references users (user_id),
    foreign key (doc_id) references documents (doc_id),
    primary key (doc_id, collector_id)
);
create table template_collector
(
    temp_id      char(7) not null,
    collector_id varchar(20) not null,
    foreign key (collector_id) references users (user_id),
    foreign key (temp_id) references templates (temp_id),
    primary key (temp_id, collector_id)
);
